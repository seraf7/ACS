import java.io.FileOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class AlumnoSerializable1 implements Serializable{
    String nombre;
    int edad;

    public static void main(String[] args) throws Exception{
        AlumnoSerializable1 alumno = new AlumnoSerializable1();
        System.out.println("Antes de asignar valores a atributos");
        System.out.println("Nombre = " + alumno.nombre);
        System.out.println("Edad = " + alumno.edad);
        System.out.println("----------------------------");

        //Asignacion de valores
        alumno.nombre = "Juan";
        alumno.edad = 23;

        System.out.println("Despues de asignar valores a atributos");
        System.out.println("Nombre = " + alumno.nombre);
        System.out.println("Edad = " + alumno.edad);
        System.out.println("----------------------------");

        //Serializacion del objeto creado
        System.out.println("Ahora se guarda el obajeto en un archivo SER");
        ObjectOutput out = new ObjectOutputStream(new FileOutputStream("alumno.ser"));
        out.writeObject(alumno);
        out.close();
    }
}
