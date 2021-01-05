import javax.crypto.*;

public class MessageAuthenticationCodeExample{
    public static void main (String[] args) throws Exception{
        //Comprobacion del numero de argumentos
        if (args.length !=1){
            System.exit(1);
        }
        //Conversion a arreglo de bytes
        byte[] textoPlano = args[0].getBytes("UTF8");
        bytesToBits(textoPlano);
        //Generacion de una clave privada
        System.out.println( "\n Generando la clave.................." );
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacMD5");
        SecretKey key = keyGen.generateKey();
        System.out.print( "Clave generada" );
        //Generacion de objeto Mac
        Mac mac = Mac.getInstance("HmacMD5");
        //Inicializacion del Mac con la llave
        mac.init(key);
        //Procesa el texto plano para firmarlo
        mac.update(textoPlano);
        //Obtencion del arreglo de bytes del mensaje firmado
        System.out.println( "\nMAC: " );
        byte resultadoMD5[] = mac.doFinal();
        //Impresion del arreglo de bytes
        System.out.println( new String( resultadoMD5, "UTF8") );
        bytesToBits( resultadoMD5 );
        System.out.println( "\n-------------------------------\n" );

        //Uso de HMAC con SHA1
        System.out.println( "\n Generando la clave.................." );
        //Generacion del objeto Mac
        Mac mac2 = Mac.getInstance("HmacSHA1");
        //Inicializacion con la llave privada
        mac2.init( key );
        //Firma del mensaje
        mac2.update(textoPlano);
        //Devuelve el mensaje firmado
        System.out.println( "\nMAC: " );
        byte resultadoSHA1[] = mac2.doFinal();
        //Impresion del mensaje firmado
        System.out.println( new String( resultadoSHA1 , "UTF8") );
        bytesToBits( resultadoSHA1 );
    }

    public static void bytesToBits( byte[] texto ){
        //Objeto para almacenar cadenas mutables
        StringBuilder stringToBits = new StringBuilder();

        for( int i=0; i < texto.length; i++ ){
            StringBuilder binary = new StringBuilder();
            //Conversion del caracter a byte
            byte b = texto[i];
            //Conversion del byte a entero
            int val = b;
            for( int j = 0; j < 8; j++ ){
                //Realiza operacion AND a nivel de bits entre val y 128
                //Si el resultado es 0, agrega un 0 a binary
                //Caso contrario agrega un 1
                binary.append( (val & 128) == 0 ? 0 : 1 );
                val <<= 1;
            }
            //Impresion de representaciones del caracter
            System.out.println( (char)b + " \t " + b + " \t " + binary );
            //Agregar al final la cadena de bits
            stringToBits.append( binary );
        }
        //Imprime la cadena de bits obtenida
        System.out.println( "El mensaje completo en bits es:" + stringToBits );
    }
}
