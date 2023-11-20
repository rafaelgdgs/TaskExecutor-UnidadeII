import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static java.lang.System.currentTimeMillis;

public class Tester {
    private final int[] valoresN = {5, 7, 9};
    private final int[] valoresT = {1, 16, 256};
    private final int[] valoresE = {0, 40};


    public void executarTestes() throws IOException {
        for (int n: valoresN
        ) {
            for (int t: valoresT
            ) {
                for(int e: valoresE){
                    System.out.println("Iniciando teste N"+n+"-T"+t);
                    TaskExecutor taskExecutor = new TaskExecutor();
                    taskExecutor.Carregamento(n, e);
                    System.out.println("Carregamento do teste N"+n+"-T"+t+" finalizado");
                    System.out.println("Começando processamento");
                    long startTime = currentTimeMillis();
                    taskExecutor.Processamento(t);

                    salvarResultados(n,t,e,currentTimeMillis() - startTime, taskExecutor.filaResultados);
                }

            }
        }
    }

    public void salvarResultados(int n, int t, int e, long tempo, FilaResultados filaResultados) throws IOException {
        File dir = new File("resultados");
        dir.mkdirs();
        File file = new File(dir,"N"+n+"-T"+t+"-E"+e+"%.txt");

        if(!file.exists()){
            file.createNewFile();
        }

        PrintWriter printWriter = new PrintWriter(file);

        printWriter.println("Numero de objetos: 10^" + n);
        printWriter.println("Threads: " + t);
        printWriter.println("Executou em: " + tempo+ " milisegundos");

        printWriter.close();

        System.out.println("Teste N"+n+"-T"+t+" finalizado, tempo de porcessamento: "+tempo+" milisegundos\n");
    }
}