import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    static AtomicInteger listos = new AtomicInteger(0);
    static AtomicInteger puestos = new AtomicInteger(2);
    static Semaphore Aux = new Semaphore(0,true);

    public static void main(String[] args) {
        Semaphore Primersemaforo = new Semaphore(10, true);
        Semaphore Segundosemaforo = Main.Aux;
        ReentrantLock Ganador = new ReentrantLock();

        for (int i = 1; i<=20; i++){
            Jugador jug = new Jugador(i, Primersemaforo, Segundosemaforo, Ganador);
            jug.start();
        }
    }
    public synchronized static void puestos(Jugador jugador){
        int puestos=Main.puestos.get();

        if (puestos<=5){
            System.out.println("El jugador "+jugador.NumJugador+" ha quedado en el puesto "+puestos);
        }else{
            System.out.println("El jugador "+jugador.NumJugador+ " no ha llegado a tiempo a la prueba 2 y ha sido descalificado");
        }
        Main.puestos.set(puestos+1);
    }

    public synchronized static void listos(){
        Main.listos.set(Main.listos.get()+1);
        if (Main.listos.get() == 10){
            Main.Aux.release(10);
        }
    }
}
