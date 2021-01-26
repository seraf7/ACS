//Requiere parametros de ejecución
//Ejemplo: java ejecutable IPservidor puerto
import java.net.*;
import java.io.*;
import java.security.*;
import javax.crypto.*;

public class Cajero{
    public static void main(String[] args) throws Exception{
        //Valida que exista un parametro de ejecución
        if(args.length != 2){
            System.out.println("No se especificaron datos del Servidor bancario");
            System.exit(1);
        }

        //Inicializacion de objetos
        Socket banco = null;
        String peticion = "";

        try{
            //Lectura de la llave guardada en el archivo .ser
            ObjectInput in = new ObjectInputStream(new FileInputStream("llave.ser"));
            Key llave = (Key) in.readObject();
            System.out.println("Tarjeta reconocida: " + llave);
            in.close();

            System.out.println("Conectando al sistema bancario");
            //Generación del socket usando el puerto 8000
            banco = new Socket(args[0], Integer.parseInt(args[1]));

            //Generacion de los flujos del socket
            DataInputStream dis = new DataInputStream(banco.getInputStream());
            DataOutputStream dos = new DataOutputStream(banco.getOutputStream());

            //Ciclo para mantener la comunicación abierta
            while(!peticion.equalsIgnoreCase("Conexión cerrada")){
                //Peticion de la transaccion
                System.out.println("\n¿Qué transacción deseas realizar?");

                //Lectura de teclado
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                peticion = br.readLine();
                System.out.println("Realizando acción: " + peticion);

                //Cifrado del mensaje leido
                byte[] peticionCifrada = cifrar(peticion, llave);

                //Envio el tamaño del mensaje
                dos.writeInt(peticionCifrada.length);
                //Envio del mensaje cifrado
                dos.write(peticionCifrada);

                //Recibe la cantidad de bytes del mensaje
                int bytesRespuesta = dis.readInt();
                //Arreglo del tamaño requerido
                byte[] respuestaCifrada = new byte[bytesRespuesta];
                //Lectura del mensaje recibido
                dis.read(respuestaCifrada);

                //Proceso de descifrado del mensaje
                peticion = descifrar(respuestaCifrada, llave);
            }
            //Cierre de flujos
            dos.close();
            dis.close();
            banco.close();
        }
        catch(IOException e){
            System.out.println("java.io.IOException generada");
            e.printStackTrace();
        }
        catch (Exception e) {
            System.out.println("Ha ocurrido un fallo en el servicio");
        }
    }

    //Metodo para realizar el cifrado del mensaje
    public static byte[] cifrar(String mensaje, Key llave) throws Exception{
        //Conversión de la cadena a un arreglo de bytes
        byte[] bytesPeticion = mensaje.getBytes();
        try{
            //Cifrado del arreglo de bytes con el algoritmo DES
            Cipher cifrador = Cipher.getInstance("DES");
            cifrador.init(Cipher.ENCRYPT_MODE, llave);
            byte[] peticionCifrada = cifrador.doFinal(bytesPeticion);
            System.out.println("Tu transacción se está procesando: ");
            System.out.println(new String(peticionCifrada));

            return peticionCifrada;
        } catch (Exception e) {
            System.out.println("Error con Proceso de cifrado");
            throw e;
        }
    }

    //Metodo para realizar el proceso de descifrado
    public static String descifrar(byte[] transaccionCifrada, Key llave) throws Exception{
        try{
            //Proceso de descifrado del mensaje
            Cipher cifrador = Cipher.getInstance("DES");
            cifrador.init(Cipher.DECRYPT_MODE, llave);
            byte[] transaccion = cifrador.doFinal(transaccionCifrada);
            String mensajeClaro = (new String(transaccion, "UTF8"));
            System.out.println("Respuesta de la transacción: " + mensajeClaro);

            return mensajeClaro;
        } catch (Exception e) {
            System.out.println("Error con Proceso de cifrado");
            throw e;
        }
    }
}
