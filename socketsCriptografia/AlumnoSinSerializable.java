public class AlumnoSinSerializable{
    String nombre;
    int edad;

    public static void main(String[] args) {
        AlumnoSinSerializable alumno = new AlumnoSinSerializable();
        System.out.println("Antes de asignar valores a atributos");
        System.out.println("nombre = " + alumno.nombre);
        System.out.println("edad = " + alumno.edad);
        System.out.println("------------------------");

        //Asignacion de valores a atributos
        alumno.nombre = "Juan";
        alumno.edad = 23;

        System.out.println("Despues de asignar valores a atributos");
        System.out.println("nombre = " + alumno.nombre);
        System.out.println("edad = " + alumno.edad);
    }
}
