import java.util.Random;

public class Productor implements Runnable{
	
	private final static Random generador=new Random();
	private final Bufer ubicacionCompartida;
	
	public Productor(Bufer compartido) {
		ubicacionCompartida=compartido;
	}
	
	public void run() {
		int suma=0;
		for(int cuenta=1;cuenta<=10;cuenta++) {
			try {
				Thread.sleep(generador.nextInt(3000));
				ubicacionCompartida.establecer(cuenta);
				suma+=cuenta;
				
			}
			catch(InterruptedException excepcion) {
				excepcion.printStackTrace();
			}
		}
		System.out.println("Productor termino de producir.\nTerminando productor.");
	}
	

}
