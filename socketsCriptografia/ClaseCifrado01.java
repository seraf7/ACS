import java.security.*;

public class ClaseCifrado01{
    public static void main( String args[] ) throws Exception{

        //Verifica argumentos de linea de comandos
        if (args.length !=1){
            System.exit(1);
        }

        System.out.println( "El argumento de la linea de comandos es: " + args[0] );
        //Convierte cadena a arreglo de bytes
        byte[] textoPlano = args[0].getBytes("UTF8");
        System.out.println( "El argumento de la linea de comandos pasado a bytes y a bits es:" );
        bytesToBits( textoPlano );
        //Crea objeto hash para usar MD5
        MessageDigest md1 = MessageDigest.getInstance("MD5");
        //Imprime informacion del proveedor del algoritmo
        System.out.println( "md1.getProvider().getInfo() da el siguiente resultado:" );
        System.out.println( "\n" + md1.getProvider().getInfo() );
        //Se genera el hash del mensaje
        System.out.println( "Ahora con messageDigest MD5 actualizamos el texto plano:" );
        md1.update( textoPlano);
        //Obtiene el hash del mensaje en codificacion UTF-8
        System.out.println( "\nresumen: " );
        System.out.println( new String( md1.digest(), "UTF8") );
        System.out.println( "El mensaje cifrado con MD5 pasado a bytes y a bits es:" );
        //Imprime byte como bits
        bytesToBits( md1.digest() );
        System.out.println("\n ------------------------------------\n");

        //Crea objeto hash para usar SHA1
        MessageDigest md2= MessageDigest.getInstance("SHA");
        //Obtiene informacion del proveedor del algoritmo
        System.out.println( "md2.getProvider().getInfo() da el siguiente resultado:" );
        System.out.println( "\n" + md2.getProvider().getInfo() );
        //Generacion del hash del mensaje
        System.out.println( "Ahora con messageDigest SHA actualizamos el texto plano:" );
        md2.update( textoPlano);
        //Obtencion del hash generado como String
        System.out.println( "\nresumen:" );
        System.out.println( new String( md2.digest(), "UTF8") );
        System.out.println( "El mensaje cifrado con SHA pasado a bytes y a bits es:" );
        //Impresion de bytes como bits
        bytesToBits( md2.digest() );
    }

    public static void bytesToBits( byte[] texto ){
        //Objeto para almacenar cadenas mutables
        StringBuilder stringToBits = new StringBuilder();

        for( int i=0; i < texto.length; i++ ){
            StringBuilder binary = new StringBuilder();
            //Conversion de caracter a byte
            byte b = texto[i];
            //Conversion de byte a entero
            int val = b;
            for( int j = 0; j < 8; j++ ){
                //Realiza operacion AND a nivel de bits entre val y 128
                //Si el resultado es 0, agrega un 0 a binary
                //Caso contrario agrega un 1
                binary.append( (val & 128) == 0 ? 0 : 1 );
                //Realiza un corrimiento de un bit a la izquierda
                val <<= 1;
            }
            //Impresion de elemento como caracter, entero y binario
            System.out.println( (char)b + " \t " + b + " \t " + binary );
            //Añade al final cadena de bits obtenida
            stringToBits.append( binary );
        }
        //Impresion de la representacion binaria
        System.out.println( "El mensaje completo en bits es:" + stringToBits );
    }
}
