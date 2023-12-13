package main

import (
	"fmt"
	"math/rand"
	"os"
	"sync"
	"time"
)

type Tarefa struct {
	ID    int
	Custo float64
	Tipo  string
	Valor float64
}

type Resultado struct {
	ID       int
	Resultado float64
	Tempo    time.Duration
}

type Executor struct {
	FilaTarefas   chan Tarefa
	FilaResultados chan Resultado
	Arquivo       *os.File
}

type Trabalhador struct {
	ID         int
	Executor   *Executor
	WorkerWG   *sync.WaitGroup
}

func (e *Executor) Carregamento(N int, E int) {
	for i := 0; i < 10^N; i++ {
		tarefa := Tarefa{
			ID:    i + 1,
			Custo: rand.Float64() * 0.01,
			Tipo:  geraTipoTarefa(E),
			Valor: rand.Float64() * 10,
		}
		e.FilaTarefas <- tarefa
	}
	close(e.FilaTarefas)
}

func (e *Executor) Processamento(T int) time.Duration {
	var wg sync.WaitGroup
	inicio := time.Now()

	for i := 0; i < T; i++ {
		wg.Add(1)
		trabalhador := &Trabalhador{
			ID:         i + 1,
			Executor:   e,
			WorkerWG:   &wg,
		}
		go trabalhador.ExecutarTarefas()
	}

	wg.Wait()
	return time.Since(inicio)
}

func (t *Trabalhador) ExecutarTarefas() {
	defer t.WorkerWG.Done()

	for tarefa := range t.Executor.FilaTarefas {
		inicioTarefa := time.Now()

		if tarefa.Tipo == "leitura" {
			time.Sleep(time.Duration(tarefa.Custo * float64(time.Second)))

			valorLido, err := lerArquivo(t.Executor.Arquivo)
			if err != nil {
				fmt.Printf("Erro ao ler arquivo: %v\n", err)
				return
			}
			tarefa.Valor = valorLido

		} else {
			time.Sleep(time.Duration(tarefa.Custo * float64(time.Second)))

			valorLido, err := lerArquivo(t.Executor.Arquivo)
			if err != nil {
				fmt.Printf("Erro ao ler arquivo: %v\n", err)
				return
			}

			novoValor := valorLido + tarefa.Valor
			err = escreverArquivo(t.Executor.Arquivo, novoValor)
			if err != nil {
				fmt.Printf("Erro ao escrever no arquivo: %v\n", err)
				return
			}
		}

		resultado := Resultado{
			ID:       tarefa.ID,
			Resultado: tarefa.Valor,
			Tempo:    time.Since(inicioTarefa),
		}

		t.Executor.FilaResultados <- resultado
	}
}

func geraTipoTarefa(E int) string {
	if rand.Intn(100) < E {
		return "escrita"
	}
	return "leitura"
}

func lerArquivo(arquivo *os.File) (float64, error) {
	var valor float64
	_, err := arquivo.Seek(0, 0)
	if err != nil {
		return 0, err
	}
	_, err = fmt.Fscanf(arquivo, "%f", &valor)
	if err != nil {
		return 0, err
	}
	return valor, nil
}

func escreverArquivo(arquivo *os.File, valor float64) error {
	_, err := arquivo.Seek(0, 0)
	if err != nil {
		return err
	}
	_, err = fmt.Fprintf(arquivo, "%.2f", valor)
	return err
}

func main() {
	rand.Seed(time.Now().UnixNano())

	valoresN := []int{5, 7, 9}
	valoresT := []int{1, 16, 256}
	valoresE := []int{0, 40}

	for _, N := range valoresN {
		for _, T := range valoresT {
			for _, E := range valoresE {
				arquivo, err := os.Create("arquivo_compartilhado.txt")
				if err != nil {
					fmt.Printf("Erro ao criar o arquivo compartilhado: %v\n", err)
					return
				}
				defer arquivo.Close()

				executor := Executor{
					FilaTarefas:   make(chan Tarefa, 10^N),
					FilaResultados: make(chan Resultado, 10^N),
					Arquivo:       arquivo,
				}

				go executor.Carregamento(N, E)

				tempo := executor.Processamento(T)
				fmt.Printf("Teste N=%d, T=%d, E=%d: Tempo de processamento: %v\n", N, T, E, tempo)
				close(executor.FilaResultados)
			}
		}
	}
}
