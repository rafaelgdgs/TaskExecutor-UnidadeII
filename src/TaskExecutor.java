import java.io.IOException;
import java.util.*;

import static java.lang.System.currentTimeMillis;

public class TaskExecutor {

    public FilaTarefas filaTarefas;
    public FilaResultados filaResultados;

    private int limit;

    public void Carregamento(int n, int e) {
        this.limit = (int) Math.pow(10.0, n);
        filaResultados = new FilaResultados(limit);

        float[] custos = new float[limit];
        boolean[] tipos = new boolean[limit];
        byte[] valores = new byte[limit];

        long startTime = currentTimeMillis();

        int totalCarregadores = 10;
        Carregador[] carregadores = new Carregador[totalCarregadores];
        int startIndex = 0;
        int load = limit / totalCarregadores;
        for (int i = 0; i < totalCarregadores; i++) {

            //para divisões com resto != 0, adiciona o resto ao load da ultima threrad
            if (i == totalCarregadores - 1) {
                load += limit % totalCarregadores;
            }
            carregadores[i] = new Carregador("Carregador " + (i + 1), custos, tipos, valores, startIndex, load, e);
            startIndex += load;
        }
        for (int i = 0; i < totalCarregadores; i++) {
            carregadores[i].start();
        }

        for (int i = 0; i < totalCarregadores; i++) {
            try {
                carregadores[i].join();
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            } finally {
                //System.out.println(carregadores[i].getName() + " finalizou");
            }
        }
        filaTarefas = new FilaTarefas(custos, tipos, valores);
        System.out.println("tempo de carregamento: " + (currentTimeMillis() - startTime) + " milisegundos");
        System.out.println("lista com 10 tarefas do total de " + limit + " tarefas criadas aleatoriamente:");
        for (int i = 0; i < limit; i += (limit / 10)) {
            Tarefa tarefa = filaTarefas.getTarefa(i);
            System.out.println(tarefa.getCusto() + ", " + tarefa.isEscrita() + ", " + tarefa.getValor());
        }

    }

    public void Processamento(int t) throws IOException {
        Acesso acesso = new Acesso(t);
        Executor executor = new Executor(filaResultados, limit, filaTarefas);
        Trabalhador[] trabalhadores = new Trabalhador[t];
        int startIndex = 0;
        int load = limit / t;
        for (int i = 0; i < t; i++) {

            //para divisões com resto != 0, adiciona o resto ao load da ultima threrad
            if (i == t - 1) {
                load += limit % t;
            }
            trabalhadores[i] = new Trabalhador(executor, acesso,startIndex,load);
            startIndex += load;
        }

        for (int i = 0; i < t; i++) {
            trabalhadores[i].start();
        }

        for (int i = 0; i < t; i++) {
            try {
                trabalhadores[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < limit; i += (limit / 10)) {
            Resultado resultado = filaResultados.getResultado(i);
            System.out.println(resultado.getId() + ", " + resultado.getResultado() + ", " + resultado.getTempo());
        }
        System.out.println("acabou");
    }
}
