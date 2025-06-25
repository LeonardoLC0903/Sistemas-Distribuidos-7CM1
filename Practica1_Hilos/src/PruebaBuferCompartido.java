import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PruebaBuferCompartido {
	
	public static void main(String args[]) {
		ExecutorService aplicacion=Executors.newCachedThreadPool();
		Bufer ubicacionCompartida=new BuferSincronizado();
		System.out.printf("%-40s%s\t\t%s\n%-40s%s\n\n","Operacion","Bufer","Ocupado","---------","------\t\t--------");
		aplicacion.execute(new Productor(ubicacionCompartida));
		aplicacion.execute(new Consumidor(ubicacionCompartida));
	}

}
