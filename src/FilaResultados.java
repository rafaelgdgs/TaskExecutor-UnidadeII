public class FilaResultados {
    private int resultados[];
    private int tempos[];

    public FilaResultados(int n) {
        this.resultados = new int[n];
        this.tempos = new int[n];
    }

    public void addResultado(Resultado resultado) {
        resultados[resultado.getId()] = resultado.getResultado();
        tempos[resultado.getId()] = resultado.getTempo();
    }

    public Resultado getResultado(int id) {
        return new Resultado(id, resultados[id], tempos[id]);
    }
}
