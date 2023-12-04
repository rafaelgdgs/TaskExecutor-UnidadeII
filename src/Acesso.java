import java.io.*;
import java.util.concurrent.Semaphore;

public class Acesso {

    private Semaphore escrita;
    private Semaphore leitura;
//    private PrintWriter printWriter;
//    private BufferedReader bufferedReader;
    private File file;
    private int threads;

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
    }

    public int read() throws IOException {

        try {
            boolean escritaLock = escrita.tryAcquire(1);
            while(!escritaLock){
                escritaLock = escrita.tryAcquire(1);
                wait(10);
            }
            //System.out.println("acquired escrita");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            escrita.release();
            try {
                leitura.acquire(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);

            } finally {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String s = bufferedReader.readLine();
                bufferedReader.close();
                leitura.release(1);
                return Integer.parseInt(s);
            }
        }
    }

    public int write(int valor) throws IOException {
        try {
            boolean escritaLock = escrita.tryAcquire(1);
            while(!escritaLock){
                escritaLock = escrita.tryAcquire(1);
                wait(10);
            }
            //System.out.println("acquired escrita");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            try {
                boolean leituraLock = leitura.tryAcquire(threads);
                System.out.println("escrita tentando adiquirir leitura lock " + leitura.availablePermits());
                while (!leituraLock){
                    System.out.println("escrita tentando adiquirir leitura lock " + leitura.availablePermits());
                    leituraLock = leitura.tryAcquire(threads);
                    wait(10);
                }



            } catch (InterruptedException e) {
                throw new RuntimeException(e);

            } finally {

                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String s = bufferedReader.readLine();
                bufferedReader.close();

                //System.out.println(s);
                int arquivo = 0;
                try{
                    arquivo = Integer.parseInt(s);

                }
                catch (Exception e){
                    System.out.println("valor de s: " + s);
                    throw new RuntimeException(e);
                }




                int soma =  arquivo + valor;
                PrintWriter printWriter = new PrintWriter(file);
                //printWriter.flush();
                printWriter.println(soma);
                printWriter.close();

                leitura.release(threads);
                escrita.release(1);
                return soma;
            }
        }
    }
}
