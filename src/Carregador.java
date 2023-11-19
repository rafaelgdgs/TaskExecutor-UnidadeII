import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
public class Carregador extends Thread{

    private float custos[];
    private boolean tipos[];
    private byte valores[];

    private int startIndex;
    private int load;
    private int e;
    public Carregador(String name, float custos[], boolean tipos[],byte valores[], int startIndex, int load, int e){
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
        Random random = new Random(); //mover para fora do for
        List<Boolean> flags = new ArrayList<Boolean>();
        double quantidadeEscrita = ((double)load/100)*(double)e;
        System.out.println(quantidadeEscrita);

        for(int i = 0; i < (int)quantidadeEscrita; i++) flags.add(true);
        for(int i = 0; i < load - (int)quantidadeEscrita; i++) flags.add(false);
        Collections.shuffle(flags);

        for (int i = startIndex; i < startIndex + load; i++) {


            custos[i] = random.nextFloat(0.01f);
            tipos[i] = flags.get(i-startIndex);
            valores[i] = (byte) random.nextInt(10);
        }
    }
}
