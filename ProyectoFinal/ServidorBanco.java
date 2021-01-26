//Servidor cifrado, requiere que los Cuentahabiente y llave
//se encuentren en el mismo directorio que este archivo
import java.net.*;
import java.io.*;
import java.security.*;
import javax.crypto.*;

public class ServidorBanco{
    public static void main(String[] args) throws Exception{

        if(args.length != 1){
            System.out.println("No se especificó el puerto de conexión");
            System.exit(1);
        }

        //Declaracion de objetos
        ServerSocket banco = null;
        Socket cajero = null;
        String peticion = "";
        int puerto = Integer.parseInt(args[0]);

        try{
            //Lectura de la llave almacenada en el archivo llave.ser
            ObjectInput in = new ObjectInputStream(new FileInputStream("llave.ser"));
            Key llave = (Key) in.readObject();
            System.out.println("Tarjeta reconocida: " + llave);
            in.close();

            //Lectura del Cuentahabiente almacenado en Cuentahabiente.ser
            in = new ObjectInputStream(new FileInputStream("Cuentahabiente.ser"));
            Cuentahabiente cliente = (Cuentahabiente) in.readObject();
            System.out.println("Cliente reconocido");
            in.close();

            //Creacion del socket del servidor
            System.out.println("Escuchando al cajero en el puerto " + puerto);
            banco = new ServerSocket(puerto);

            while (true) {
                System.out.println("Esperando una conexión...");
                //Acepta conexión del cliente
                cajero = banco.accept();
                System.out.println("Se ha conectado el cliente: " + cajero.getInetAddress().getHostName());

                //Creacion de los flujos asociados al cliente
                DataInputStream dis = new DataInputStream(cajero.getInputStream());
                DataOutputStream dos = new DataOutputStream(cajero.getOutputStream());

                //Ciclo para mantener comunicación abierta mientras no se recoba SALIR
                while(!peticion.equalsIgnoreCase("Conexión cerrada")){
                    //Recibe la cantidad de bytes del mensaje
                    int bytesTransaccion = dis.readInt();
                    //Arreglo del tamaño requerido
                    byte[] transaccionCifrada = new byte[bytesTransaccion];
                    //Lectura del mensaje recibido
                    dis.read(transaccionCifrada);

                    //Proceso de descifrado del mensaje
                    peticion = descifrar(transaccionCifrada, llave);

                    //Procesamiento de la transaccion
                    peticion = procesar(peticion, cliente);

                    //Cifrado del mensaje leido
                    byte[] peticionCifrada = cifrar(peticion, llave);

                    //Envio el tamaño del mensaje
                    dos.writeInt(peticionCifrada.length);
                    //Envio del mensaje cifrado
                    dos.write(peticionCifrada);
                    System.out.println("Respuesta enviada\n");
                }
                System.out.println("Cliente desconectado");
                peticion = "";

                //Serializacion del cliente actualizado
                ObjectOutput out = new ObjectOutputStream(new FileOutputStream("Cuentahabiente.ser"));
                out.writeObject(cliente);
                out.close();

                //Cierre de los flujos
                dis.close();
                dos.close();
                cajero.close();
            }
        }
        catch (IOException e) {
            System.out.println("java.io.IOException generada");
            e.printStackTrace();
        }catch(Exception e){
            System.out.println("Ha ocurrido un fallo en el servidor");
            e.printStackTrace();
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
            System.out.println("Cifrando respuesta de transaccion: ");
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
            System.out.println("Transaccion descifrada: " + mensajeClaro);

            return mensajeClaro;
        } catch (Exception e) {
            System.out.println("Error con proceso de descifrado");
            throw e;
        }
    }

    public static String procesar(String peticion, Cuentahabiente cliente){
        String respuesta = "";
        //Divide la cadena quitando todos los espacios
        String[] comando = peticion.split("[ ]+");

        //Validacion del comando recibido
        if(comando[0].equalsIgnoreCase("CONSULTAR")){
            respuesta = cliente.getInfo();
        }else if(comando[0].equalsIgnoreCase("DEPOSITAR") && comando.length == 2){
            //Valida el reultado de la transaccion
            if(cliente.depositar(Float.parseFloat(comando[1]))){
                respuesta = "Deposito Exitoso\n";
                respuesta += "Saldo actual: $" + Float.toString(cliente.getSaldo());
            }else{
                //Ocurrio un error en el deposito
                respuesta = "Deposito fallido";
            }
        }else if(comando[0].equalsIgnoreCase("RETIRAR") && comando.length == 2){
            //Valida el resultado de la transaccion
            if(cliente.retirar(Float.parseFloat(comando[1]))){
                respuesta = "Retiro exitoso\n";
                respuesta += "Saldo actual: $" + Float.toString(cliente.getSaldo());
            }else{
                //Ocurrio un error en el retiro
                respuesta = "Retiro fallido. Sin fondos suficientes";
            }
        }else if(comando[0].equalsIgnoreCase("Salir")){
            respuesta = "Conexión cerrada";
        }else{
            respuesta = "Transaccion fallida";
        }
        return respuesta;
    }
}
