public class Resultado {
    private int id;
    private int resultado;
    private int tempo;

    public Resultado(int id, int resultado, int tempo) {
        this.id = id;
        this.resultado = resultado;
        this.tempo = tempo;
    }

    public int getId() {
        return id;
    }

    public int getResultado() {
        return resultado;
    }

    public int getTempo() {
        return tempo;
    }
}
