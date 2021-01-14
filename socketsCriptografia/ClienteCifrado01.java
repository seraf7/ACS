//Requiere parametros de ejecucion
//Ejemplo: java executable IPservidor
import java.net.*;
import java.io.*;
import java.security.*;
import javax.crypto.*;

public class ClienteCifrado01{
    public static void main(String[] a) throws Exception{
        //Inicializacion del socket
        Socket socket = null;
        //Peticion es el mensaje enviado del cliente
        String peticion = "";

        try{
            //Lectura de la llave serializada
            ObjectInput in = new ObjectInputStream(new FileInputStream("llave.ser"));
            Key llave = (Key) in.readObject();
            System.out.println("llave = " + llave);
            in.close();

            //Asignacion de IP y puerto al socket, conexion al Servidor
            System.out.println("Me conecto al puerto 8000 del servidor");
            socket = new Socket(a[0], 8000);
            //Asignacion de los flujos del socket
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            //Lectura del teclado
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            peticion = br.readLine();
            System.out.println("Mi peticion es: " + peticion);

            System.out.println("Ahora ciframos la peticion...");
            //Convierte String a arreglo de bytes
            byte[] arrayPeticion = peticion.getBytes();
            //Creacion de objeto para cifrar con DES
            Cipher cifrar = Cipher.getInstance("DES");
            //Indica operacion de cifrado y llave a usar
            cifrar.init(Cipher.ENCRYPT_MODE, llave);
            //Cifrado del mensaje
            byte[] cipherText = cifrar.doFinal(arrayPeticion);
            System.out.println("El argumento encriptado es: ");
            System.out.println(new String(cipherText));

            //Envio del mensaje al servidor
            bytesToBits(cipherText);
            dos.write(cipherText, 0, cipherText.length);

            //Cierre de flujos y liberacion del socket
            dos.close();
            dis.close();
            socket.close();
        }
        catch(IOException e){
            System.out.println("java.io.IOException generada");
            e.printStackTrace();
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
