//Requiere parametros de ejecucion
//Ejemplo: java Ejecutable IPservidor numeroPuerto

import java.net.*;
import java.io.*;

public class ClienteSSH{
    public static void main(String a[]){
        //Declaracion de cadenas
        String peticion = null;
        String respuesta = null;

        //Declaracion de socket
        Socket socket = null;

        try{
            //Conexion del socket al servidor
            System.out.println("Conectando al puerto " + a[1] + " del servidor");
            socket = new Socket(a[0], Integer.parseInt(a[1]));

            //Creacion de un buffer de lectura
            BufferedReader comando = new BufferedReader(new InputStreamReader(System.in));
            //Lectura del comando
            peticion = comando.readLine();

            System.out.println("Enviando petición: " + peticion);
            //Cracion de flujo de salida para el socket
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            //Envio de peticion al servidor
            dos.writeUTF(peticion);

            //Creacion de un flujo de entrada asociado al socket
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            //Lectura del mensaje recibido del servidor
            respuesta = dis.readUTF();
            //Impresion del mensaje
            System.out.printf("La respuesta recibida es: %n%s", respuesta);

            //Cierre de flujos y socket
            dos.close();
            dis.close();
            socket.close();
        }
        catch(IOException e){
            System.out.println("java.io.IOException generada");
            e.printStackTrace();
        }
    }
}
