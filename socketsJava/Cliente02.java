import java.net.*;
import java.io.*;

public class Cliente02{

    public static void main(String a[]){
        Socket socket = null;
        String peticion = null;
        String respuesta = null;

        try{
            System.out.println("Me conecto al puerto 8000 del servidor");
            socket = new Socket(a[0],8000);
            //Lectura de datos a traves de teclado
            //Usa Buffer de lectura, junto a el flujo de entrada System.in
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            //Lee datos hasta encontrar salto de linea
            peticion = br.readLine();
            System.out.println("Le envio mi peticion: " + peticion);
            DataOutputStream dos = new DataOutputStream( socket.getOutputStream());
            //Envia datos al servidor
            dos.writeUTF(peticion);
            DataInputStream dis = new DataInputStream( socket.getInputStream() );
            //Lectura de datos del servidor
            respuesta = dis.readUTF();
            System.out.println("El mensaje que me envio el servidor es: " + respuesta);
            //Cierra los flujos de datos
            dos.close();
            dis.close();
            //Cierra el socket
            socket.close();
        }
        catch(IOException e){
            System.out.println("java.io.IOException generada");
            e.printStackTrace();
        }
    }
}
