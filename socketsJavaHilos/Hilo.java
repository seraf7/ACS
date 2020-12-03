public class Hilo extends Thread{
    public Hilo(String name){
        //Llamado al constructor del padres
        super(name);
    }

    public static void main(String arg[]){
        new Hilo("Primer Hilo").start();
        new Hilo("Segundo Hilo").start();
        System.out.println("Termina Hilo principal");
    }

    //Definicion del metodo run
    public void run(){
        for(int i=0; i<5; i++){
            System.out.println("Iteración "+(i+1)+" de "+getName()+" con ID "+getId());
        }
        System.out.println("Termina el "+getName());
    }
}//Fin del hilo principal
