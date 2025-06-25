import java.util.Random;

public class Consumidor implements Runnable{
	
	private final static Random generador=new Random();
	private final Bufer ubicacionCompartida;
	
	public Consumidor(Bufer compartido) {
		ubicacionCompartida=compartido;
	}
	
	public void run() {
		int suma=0;
		for(int cuenta=1;cuenta<=10;cuenta++) {
			try {
				Thread.sleep(generador.nextInt(3000));
				suma+=ubicacionCompartida.obtener();
				
			}
			catch(InterruptedException excepcion) {
				excepcion.printStackTrace();
			}
		}
		System.out.printf("\n%s %d\n%s\n", "Consumidor leyo valores, el total es: ",suma,"Terminando Consumidor.");
	}
	

}
