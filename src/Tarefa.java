public class Tarefa {
    private int id;
    private byte custo;
    private boolean tipo;
    private byte valor;

    public Tarefa(int id, byte custo, boolean tipo, byte valor) {
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

    public byte getCusto() {
        return custo;
    }

    public void setCusto(byte custo) {
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
