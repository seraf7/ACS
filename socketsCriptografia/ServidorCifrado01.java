import java.net.*;
import java.io.*;
import java.security.*;
import javax.crypto.*;

public class ServidorCifrado01{
    public static void main(String[] args) throws Exception{
        //Inicializacion de sockets
        ServerSocket serverSocket = null;
        Socket socket = null;
        //Peticion es lo que envia el cliente
        byte arreglo[] = new byte[10000];

        //Generacion de la llave
        System.out.println("Generando la llave...");
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        keyGen.init(56);    //Indica una llave de tamaño 56
        Key llave = keyGen.generateKey();
        System.out.println("llave = " + llave);
        System.out.println("Llave generada!");

        //Guarda llave en un archivo, serializacion
        ObjectOutput out = new ObjectOutputStream(new FileOutputStream("llave.ser"));
        out.writeObject(llave);
        out.close();

        try{
            //Asignacion de puerto al socket
            System.out.println("Escuchando por el puerto 8000");
            serverSocket = new ServerSocket(8000);
        }
        catch (IOException e) {
            System.out.println("java.io.IOException generada");
            e.printStackTrace();
        }

        //Cliclo para esperar conexiones
        System.out.println("Esperando a que los clientes se conecten...");
        while (true) {
            try{
                //Acepta conexion de un cliente
                socket = serverSocket.accept();
                System.out.println("Se conectó un cliente: " + socket.getInetAddress().getHostName());
                //Asigna flujos asociados al socket
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                //Acuerdo del orden de escritura entre cliente y servidor
                //Lectura de teclado para el buffer
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                //Lectura de los bytes enviados por el cliente
                int bytesLeidos = dis.read(arreglo);
                System.out.println("bytes leido = " + bytesLeidos);
                byte arreglo2[] = new byte[bytesLeidos];
                for(int i = 0; i < bytesLeidos; i++){
                    arreglo2[i] = arreglo[i];
                }

                //Instancia del objeto para descifrar con DES
                Cipher cifrar = Cipher.getInstance("DES");
                //Indica descifrado y llave a usar
                cifrar.init(Cipher.DECRYPT_MODE, llave);
                //Conversion de los bytes a bits
                bytesToBits(arreglo2);
                //Obtiene texto descifrado
                byte[] newPlainText = cifrar.doFinal(arreglo2);
                System.out.println("El argumento DESENCRIPTADO es: ");
                System.out.println(new String(newPlainText, "UTF8"));
                //Cierre de flujos
                dos.close();
                dis.close();
                socket.close();
            }
            catch (IOException e) {
                System.out.println("java.io.IOException generada");
                e.printStackTrace();
            }
        }
    }

    //Metodo para convertir bytes a bits
    public static void bytesToBits(byte[] texto){
        //Objeto String mutable
        StringBuilder stringToBits = new StringBuilder();
        for(int i = 0; i<texto.length; i++){
            StringBuilder binary = new StringBuilder();
            byte b = texto[i];
            int val = b;
            for(int j = 0; j<8; j++){
                binary.append((val & 128) == 0 ? 0:1);
                val <<= 1;
            }
            System.out.println((char)b + "\t" + b + "\t" + binary);
            stringToBits.append(binary);
        }
        System.out.println("El mensaje completo en bits es: " + stringToBits);
    }
}
