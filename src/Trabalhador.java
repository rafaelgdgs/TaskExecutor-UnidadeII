import java.io.IOException;

import static java.lang.System.currentTimeMillis;

public class Trabalhador extends Thread{

    private Executor executor;
    private Acesso acesso;

    private long startTime;
    private int startIndex;
    private int load;

    public Trabalhador(Executor executor, Acesso acesso, int startIndex,int load){
        this.executor = executor;
        this.acesso = acesso;
        this.startIndex = startIndex;
        this.load = load;

    }

    @Override
    public void run() {
        for (int i = startIndex; i < startIndex + load; i++) {
            Tarefa tarefa = executor.enviarTarefa(i);
            try {
                fazerTarefa(tarefa);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(this.getName()+ " acabei");
    }

    private void fazerTarefa(Tarefa tarefa) throws IOException, InterruptedException {
        this.startTime = currentTimeMillis();
        wait((long)(tarefa.getCusto()*1000));
        if(tarefa.isEscrita()){
            executor.receberResultado(escrita(tarefa));
        }
        else {
            executor.receberResultado(leitura(tarefa));
        }
    }

    private Resultado leitura(Tarefa tarefa) throws IOException {
        int valor = acesso.read();
        Resultado resultado = new Resultado(tarefa.getId(),valor,(int) (currentTimeMillis()-startTime));
        System.out.println(resultado);
        return resultado;
    }
    private Resultado escrita(Tarefa tarefa) throws IOException {
        int valor = acesso.write(tarefa.getValor());
        Resultado resultado = new Resultado(tarefa.getId(),valor, (int) (currentTimeMillis()-startTime));
        System.out.println(resultado);
        return resultado;
    }
}
