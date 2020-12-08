//Requiere parametros de ejecucion
//Ejemplo: java Ejecutable numeroPuerto

import java.net.*;
import java.io.*;

public class ServidorSSH extends Thread{

    public static void main(String a[]){
        //Declaracion de cadenas
        String peticion;
        String respuesta;

        //Declaracion de sockets
        ServerSocket servidor = null;
        Socket cliente = null;

        try{
            System.out.println("Escuchando por el puerto " + a[0]);
            //Instancia del socket del servidor con el puerto dado
            servidor = new ServerSocket(Integer.parseInt(a[0]));
        }catch(IOException e){
            //Cachar excepcion generada por lectura
            System.out.println("java.io.IOException generada");
            e.printStackTrace();
        }

        System.out.println("Esperando a que un cliente se conecte...");

        //Bloque para aceptar conexiones
        try{
            //Socket de cliente alamcena conexion aceptada
            cliente = servidor.accept();
            //Obtiene el nombre del cliente
            System.out.println("Se conectó el cliente: " + cliente.getInetAddress().getHostName());
            //Instancia de un flujo de entrada para recibir mensaje del cliente
            DataInputStream dis = new DataInputStream(cliente.getInputStream());
            //Se les el flujo recibido
            peticion = dis.readUTF();
            //Imprime el mensaje recibido
            System.out.println("El mensaje que envio el cliente es: " + peticion);

            //Ejecucion del comando en un hilo independiente
            Process cmd = Runtime.getRuntime().exec(peticion);
            //Obtiene el flujo de datos devuelto por el comando
            InputStream resultado = cmd.getInputStream();
            BufferedInputStream lector = new BufferedInputStream(resultado);
            //Lee el buffer almacenado
            respuesta = lectura(lector);
            //Crea un flujo de salida dirijido al cliente
            DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
            //Envio de la respuesta al cliente
            System.out.println("Enviando respuesta al cliente");
            dos.writeUTF(respuesta);

            //Se cierran los flujos
            dos.close();
            dis.close();
            //Se librea el socket
            cliente = null;
        }
        catch (IOException e) {
            System.out.println("java.io.IOException generada");
            e.printStackTrace();
        }
    }

    public static String lectura(BufferedInputStream l){
        String lectura = "";
        int c = 1;
        //Lee todos los bits del buffer y los almacena en una cadena
        while(c != -1){
            try{
                c = l.read();
                lectura += (char) c;
            }catch(IOException e){
                System.out.println("Error al leer el buffer");
                e.printStackTrace();
            }
        }
        return lectura;
    }
}
