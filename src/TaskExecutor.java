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

        int totalCarregadores = 10;
        Carregador[] carregadores = new Carregador[totalCarregadores];
        int startIndex = 0;
        int load = limit / totalCarregadores;
        for (int i = 0; i < totalCarregadores; i++) {

            //para divisões com resto != 0, adiciona o resto ao load da ultima threrad
            if (i == totalCarregadores - 1) {
                load += limit % totalCarregadores;
            }
            carregadores[i] = new Carregador("Carregador " + (i + 1), custos, tipos, valores, startIndex,load,e);
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
        for (int i=0;i<limit;i++){
            Tarefa tarefa = filaTarefas.getTarefa(i);
            System.out.println(tarefa.getCusto() + ", " + tarefa.isEscrita() + ", " + tarefa.getValor());
        }

    }

    public void Processamento(int t){

    }
}
