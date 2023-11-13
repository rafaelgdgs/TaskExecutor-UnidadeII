public class Tarefa {
    private int id;
    private float custo;
    private boolean tipo;
    private byte valor;

    public Tarefa(int id, float custo, boolean tipo, byte valor) {
        this.id = id;
        this.custo = custo;
        this.tipo = tipo;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getCusto() {
        return custo;
    }

    public void setCusto(float custo) {
        this.custo = custo;
    }

    public boolean isEscrita() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    public byte getValor() {
        return valor;
    }

    public void setValor(byte valor) {
        this.valor = valor;
    }
}
