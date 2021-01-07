import java.io.FileInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class AlumnoSerializable2 implements Serializable{
    public static void main(String[] args) throws Exception{
        System.out.println("Ahora se leerá el objetos desde el archivo SER");
        System.out.println("---------------------");

        //Lectura del archivo Serializable
        ObjectInput in = new ObjectInputStream(new FileInputStream("alumno.ser"));
        AlumnoSerializable1 alumno = (AlumnoSerializable1) in.readObject();
        in.close();

        //Impresion de valores
        System.out.println("Despues de asignar valores a atributos");
        System.out.println("Nombre = " + alumno.nombre);
        System.out.println("Edad = " + alumno.edad);
    }
}
