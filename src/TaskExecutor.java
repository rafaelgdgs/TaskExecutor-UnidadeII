import java.util.*;

import static java.lang.System.currentTimeMillis;

public class TaskExecutor {

    FilaTarefas filaTarefas;
    FilaResultados filaResultados;

    private int limit;
    public void Carregamento(int n, int e){
        this.limit = (int) Math.pow(10.0, n);

        float[] custos = new float[limit];
        boolean[] tipos = new boolean[limit];
        byte[] valores = new byte[limit];

        long startTime = currentTimeMillis();

        //List<Boolean> flags = new ArrayList<Boolean>();
        double quantidadeEscrita = ((double)limit/100)*(double)e;
        System.out.println("quantidade total: " + limit);
        System.out.println("quantidade escrita: "+quantidadeEscrita);

        for(int i = 0; i < (int)quantidadeEscrita; i++) tipos[i] = true;
        for(int i = 0; i < limit - (int)quantidadeEscrita; i++) tipos[i] = false;

        Random rnd = new Random();
        for (int i = tipos.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            boolean a = tipos[index];
            tipos[index] = tipos[i];
            tipos[i] = a;
        }


        int totalCarregadores = 10;
        Carregador[] carregadores = new Carregador[totalCarregadores];
        int startIndex = 0;
        int load = limit / totalCarregadores;
        for (int i = 0; i < totalCarregadores; i++) {

            //para divisões com resto != 0, adiciona o resto ao load da ultima threrad
            if (i == totalCarregadores - 1) {
                load += limit % totalCarregadores;
            }
            carregadores[i] = new Carregador("Carregador " + (i + 1), custos, valores, startIndex,load,e);
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
            }
            finally {
                //System.out.println(carregadores[i].getName() + " finalizou");
            }
        }
        filaTarefas = new FilaTarefas(custos,tipos,valores);
        System.out.println("tempo de carregamento: " + (currentTimeMillis()-startTime));
        for (int i=0;i<limit;i+=(limit/10)){
            Tarefa tarefa = filaTarefas.getTarefa(i);
            System.out.println(tarefa.getCusto() + ", " + tarefa.isEscrita() + ", " + tarefa.getValor());
        }

    }

    public void Processamento(int t){

    }
}
