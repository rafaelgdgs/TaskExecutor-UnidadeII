public class Executor {
    private int ultimaTarefa;
    private int quantTarefas;
    private FilaResultados filaResultados;
    private FilaTarefas filaTarefas;

    public Executor(FilaResultados filaResultados, int quantTarefas, FilaTarefas filaTarefas) {
        this.filaResultados = filaResultados;
        this.quantTarefas = quantTarefas;
        this.ultimaTarefa = 0;
        this.filaTarefas = filaTarefas;
    }

    public synchronized void receberResultado(Resultado resultado) {
        filaResultados.addResultado(resultado);
        ultimaTarefa++;

    }

    public synchronized Tarefa enviarTarefa(int id) {


        return filaTarefas.getTarefa(id);
    }
}
