
public class BuferSincronizado implements Bufer{
	
	private int bufer=-1;
	private boolean ocupado=false;
	
	public synchronized void establecer(int valor) throws InterruptedException{
		while(ocupado) {
			System.out.println("Productor trata de escribir.");
			mostrarEstado("Bufer lleno. Productor espera.");
			wait();
		}
		bufer=valor;
		ocupado=true;
		mostrarEstado("Productor escribe: "+bufer);
		notifyAll();
	}
	
	public synchronized int obtener() throws InterruptedException{
		while(!ocupado) {
			System.out.println("Consumidor trata de leer.");
			mostrarEstado("Bufer vacio. Consumidor espera.");
			wait();
		}
		ocupado=false;
		mostrarEstado("Consumidor lee: "+bufer);
		notifyAll();
		
		return bufer;
	}
	
	public void mostrarEstado(String operacion) {
		System.out.printf("%-40s%d\t\t%b\n\n",operacion,bufer,ocupado);
	}

}
