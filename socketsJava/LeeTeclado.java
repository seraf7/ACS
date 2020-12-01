import java.io.*;

public class LeeTeclado{
    //throws propaga la excepcion en vez de cacharla
    public static void main(String arg[]) throws Exception{
        //Realiza la lectura de un byte, se guarda como entero
        //int i = System.in.read();

        //Realiza la lectura de un byte, se guarda como char
        //char i = (char) System.in.read();

        //Realiza la lecturar de una linea de texto con buffer
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String i = br.readLine();

        //Imprime el byte leido
        System.out.println(i);
    }
}
