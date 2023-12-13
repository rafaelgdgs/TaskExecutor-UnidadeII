import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
public class Carregador extends Thread{

    private byte custos[];
    private boolean tipos[];
    private byte valores[];

    private int startIndex;
    private int load;
    private int e;
    public Carregador(String name, byte custos[], boolean tipos[], byte valores[], int startIndex, int load, int e){
        super(name);
        this.custos = custos;
        this.tipos = tipos;
        this.valores = valores;
        this.startIndex = startIndex;
        this.load = load;
        this.e = e;

    }
    @Override
    public void run() {
        Random random = new Random();
        double quantidadeEscrita = ((double)load/100)*(double)e;
        System.out.println("quantidade de tarefas de escrita em "+ getName()+" : "+ quantidadeEscrita);

        for(int i = startIndex; i < startIndex + (int)quantidadeEscrita; i++) tipos[i] = true;
        for(int i = startIndex + (int)quantidadeEscrita; i < startIndex + load; i++) tipos[i] = false;
        System.out.println("fim da escrita dos booleans da thread: " + getName());


        for (int i = startIndex; i < startIndex + load ; i++) {


            custos[i] = (byte) random.nextInt(10);
            valores[i] = (byte) random.nextInt(10);

            int index = random.nextInt(load) + startIndex;
            // Simple swap
            boolean a = tipos[index];
            tipos[index] = tipos[i];
            tipos[i] = a;
        }
    }
}
