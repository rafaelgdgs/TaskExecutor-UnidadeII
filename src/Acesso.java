import java.io.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Acesso {

    private Semaphore escrita;
    private Semaphore leitura;
//    private PrintWriter printWriter;
//    private BufferedReader bufferedReader;
    private File file;
    private int threads;

    private int valorCache;

    public Acesso(int threads) throws IOException {
        this.threads = threads;
        escrita = new Semaphore(1, true);
        leitura = new Semaphore(threads, true);
        File dir = new File("assets");
        dir.mkdirs();
        file = new File(dir, "recurso-compartilhado.txt");

        if (!file.exists()) {
            file.createNewFile();
        }

        PrintWriter printWriter = new PrintWriter(file);
        //printWriter.print(0);

        //BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        //printWriter.flush();
        printWriter.println("0");
        printWriter.close();
        valorCache = 0;
    }

    public int read() throws IOException {

        try {
            escrita.acquire();
            //System.out.println("acquired escrita");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        } finally {
            escrita.release();
            try {
                leitura.acquire();
//                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
//                String s = bufferedReader.readLine();
//                bufferedReader.close();
                leitura.release();
//                return Integer.parseInt(s);
                return valorCache;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
    }

    public int write(int valor) throws IOException {
        try {
            escrita.acquire();
            //System.out.println("acquired escrita");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        } finally {
            int arquivo = 0, soma = 0;
            try {
                leitura.acquire(threads);
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String s = bufferedReader.readLine();
                bufferedReader.close();


                try{
                    arquivo = Integer.parseInt(s);
                    //System.out.println("valor de s: " + s);
                }
                catch (NumberFormatException e){
                    //System.out.println("valor de ss: " + s);
                    throw new RuntimeException("Erro ao converter valor do arquivo para inteiro", e);
                }

                soma =  arquivo + valor;
                try (PrintWriter printWriter = new PrintWriter(file)) {
                    printWriter.println(soma);
                    valorCache = soma;
                }


            } catch (InterruptedException | IOException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);

            } finally {
                leitura.release(threads);
                escrita.release(1);
                return soma;
            }
        }
    }
}
