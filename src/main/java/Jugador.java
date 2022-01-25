import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

class Jugador extends Thread{

    int NumJugador;
    Semaphore Primersemaforo;
    Semaphore Segundosemaforo;
    ReentrantLock Ganador;

    public Jugador(int numJugador, Semaphore primersemaforo, Semaphore segundosemaforo, ReentrantLock ganador) {
        this.NumJugador = numJugador;
        this.Primersemaforo = primersemaforo;
        this.Segundosemaforo = segundosemaforo;
        this.Ganador = ganador;
    }

    @Override
    public void run(){
        try {
            Thread.sleep((long) ((Math.random()*3+1)*1000));
            int PosibilidadDeMuerte1 = (int) (Math.random()*10+1);
            if (PosibilidadDeMuerte1 > 1){
                System.out.println("El jugador "+this.NumJugador+ " ha superado la prueba 1");
                if (Primersemaforo.tryAcquire()){
                    System.out.println("El jugador "+this.NumJugador+ " ha completado a tiempo la prueba 1");
                    Main.listos();
                    Segundosemaforo.acquire();
                    Thread.sleep((long) ((Math.random()*3+1)*1000));
                    int PosibilidadDeMuerte2 = (int) (Math.random()*10+1);
                    if (PosibilidadDeMuerte2 > 1){
                        System.out.println("El jugador "+this.NumJugador+ " ha superado la prueba 2");
                        if (Ganador.tryLock()){
                            System.out.println("El jugador "+this.NumJugador+ " ha ganado ");
                        }else{
                            Main.puestos(this);
                        }
                    }else{
                        System.out.println("El jugador "+this.NumJugador+ " ha sido descalificado en la prueba 2");
                    }
                }else{
                    System.out.println("El jugador "+this.NumJugador+ " no ha completado a tiempo la prueba 1 y ha sido descalificado");
                }
            }else{
                System.out.println("El jugador "+this.NumJugador+ " ha sido descalificado en la prueba 1");
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
