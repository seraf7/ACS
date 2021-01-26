import java.io.FileOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Cuentahabiente implements Serializable{
    private String nombre;
    private String numCuenta;
    private float saldo;

    //Constructor de la clase
    public Cuentahabiente(String nombre, String numCuenta, float saldo){
        this.nombre = nombre;
        this.numCuenta = numCuenta;
        this.saldo = saldo;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNumCuenta(String numCuenta){
        this.numCuenta = numCuenta;
    }

    public String getNumCuenta(){
        return numCuenta;
    }

    public void setSaldo(float saldo){
        this.saldo = saldo;
    }

    public float getSaldo(){
        return saldo;
    }

    public String getInfo(){
        String info = "\n";
        info += this.getNombre() + "\n";
        info += this.getNumCuenta() + "\n";
        info += "$" + String.valueOf(this.getSaldo());

        return info;
    }

    //Metodo para realizar un depósito
    public boolean depositar(float monto){
        //Valida que el monto a depositar
        if(monto > 0){
            this.setSaldo(this.getSaldo() + monto);
            return true;
        } else{
            return false;
        }
    }

    //Metodo para realizar un retiro
    public boolean retirar(float monto){
        //Valida si es posible realizar el retiro con el saldo disponible
        if(monto > this.getSaldo()){
            return false;
        } else{
            this.setSaldo(this.getSaldo() - monto);
            return true;
        }
    }
}
