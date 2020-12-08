import java.io.*;

public class Comando{
    public static void main(String arg[]) throws Exception{
        int res;
        BufferedInputStream lector = null;

        System.out.println("Ingresa tu comando:");

        //Realizar lectura de teclado
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String cmd = br.readLine();

        //Ejecucion del comando leido en un hilo independiente
        try{
            Process proceso = Runtime.getRuntime().exec(cmd);
            //Obtiene el flujo devuelto por el comando
            InputStream resultado = proceso.getInputStream();
            lector = new BufferedInputStream(resultado);
            //res = lector.read();
        }catch(IOException ioe){
            System.out.println(ioe);
        }

        System.out.println("El resultado de tu comando es:");
        //Imprime bytes almacenados en el buffer
        while((res = lector.read()) != -1){
            System.out.print((char) res);
        }

        //Cierra buffers
        lector.close();
    }
}
