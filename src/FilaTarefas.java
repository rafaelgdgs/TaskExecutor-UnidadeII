public class FilaTarefas {
    private float custos[];
    private boolean tipos[];
    private byte valores[];

    public FilaTarefas(float[] custos, boolean[] tipos, byte[] valores) {
        this.custos = custos;
        this.tipos = tipos;
        this.valores = valores;
    }

    public Tarefa getTarefa(int id){
        return new Tarefa(id,custos[id],tipos[id],valores[id]);
    }
}
