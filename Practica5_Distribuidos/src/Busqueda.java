import java.rmi.Remote;
import java.rmi.RemoteException;

// Esta interfaz define el método remoto que puede ser invocado a través de la red.
public interface Busqueda extends Remote {
    // Método remoto que retorna un saludo.
    String busc(String curp) throws RemoteException;
}
