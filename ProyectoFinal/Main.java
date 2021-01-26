import java.io.*;

public class Main{
    public static void main(String[] args) throws Exception{
        //Creacion de objeto Cuentahabiente
        Cuentahabiente c1 = new Cuentahabiente("Jorge Perez", "1732738", 673);

        //Imprime datos del Cuentahabiente
        System.out.println(c1.getInfo());
        // System.out.println(c1.getNombre());
        // System.out.println(c1.getNumCuenta());
        // System.out.println(c1.getSaldo());

        //Deposito en el cuenta
        if(c1.depositar(0)){
            System.out.println("\nDeposito exitoso");
            System.out.println("Saldo Actual: $" + c1.getSaldo());
        } else{
            System.out.println("\nDeposito no relizado");
        }

        //Retiro en la cuenta
        if(c1.retirar(700)){
            System.out.println("\nRetiro exitoso");
            System.out.println("Saldo Actual: $" + c1.getSaldo());
        } else{
            System.out.println("\nNo tienes el saldo suficiente");
        }

        //Serializacion del objeto
        ObjectOutput out = new ObjectOutputStream(new FileOutputStream("Cuentahabiente.ser"));
        out.writeObject(c1);
        out.close();
    }
}
