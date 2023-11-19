import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static java.lang.System.currentTimeMillis;

public class Tester {
    private final int[] valoresN = {5, 7, 9};
    private final int[] valoresT = {1, 16, 256};
    private final int[] valoresE = {0, 40};


//    public void executarTestes() throws IOException {
//        for (int n: valoresN
//        ) {
//            for (int t: valoresT
//            ) {
//                System.out.println("Iniciando teste N"+n+"-T"+t);
//                ParallelArraySumary parallelArraySumary = new ParallelArraySumary();
//                parallelArraySumary.Carregamento(n);
//                System.out.println("Carregamento do teste N"+n+"-T"+t+" finalizado");
//                System.out.println("Começando processamento");
//                long startTime = currentTimeMillis();
//                Resultados resultados = parallelArraySumary.Processamento(t);
//
//                salvarResultados(n,t,currentTimeMillis() - startTime, resultados);
//            }
//        }
//    }
//
//    public void salvarResultados(int n, int t, long tempo, Resultados resultados) throws IOException {
//        File dir = new File("resultados");
//        dir.mkdirs();
//        File file = new File(dir,"N"+n+"-T"+t+".txt");
//
//        if(!file.exists()){
//            file.createNewFile();
//        }
//
//        PrintWriter printWriter = new PrintWriter(file);
//
//        printWriter.println("Numero de objetos: 10^" + n);
//        printWriter.println("Threads: " + t);
//        printWriter.println("Executou em: " + tempo+ " milisegundos");
//        printWriter.println(resultados.getQuantidadeMaiores());
//        printWriter.println(resultados.getQuantidadeMenores());
//        printWriter.println(resultados.getSomaGrupos());
//        printWriter.println(resultados.getSomaTotais());
//        printWriter.close();
//
//        System.out.println("Teste N"+n+"-T"+t+" finalizado, tempo de porcessamento: "+tempo+" milisegundos\n");
//    }
}