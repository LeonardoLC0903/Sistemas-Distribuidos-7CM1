import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

// Implementación de la interfaz remota. Se extiende UnicastRemoteObject para facilitar la exportación del objeto.
public class BusquedaImpl extends UnicastRemoteObject implements Busqueda {
	
	Registros base[];

    // Constructor que lanza RemoteException
    protected BusquedaImpl(Registros basE[]) throws RemoteException {
        super();
        base=basE;
    }
    
    // Implementación del método remoto.
    public String busc(String curp) throws RemoteException {    	
        return busqueda(curp);
    }
    
    public String busqueda(String c) {
    	for(int i=0;i<base.length;i++) {
    		if(c.equals(base[i].CURP)) {
    			String salida="--------------------------------------\n";
    			salida+="CURP>>> "+base[i].CURP+"\n";
    			salida+="--------------------------------------\n\n";
    			salida+="Nombre Competo>>> "+base[i].nombre+" "+base[i].apellidoP+" "+base[i].apellidoM+"\n";
    			salida+="Edad: "+base[i].edad+"\n";
    			salida+="Entidad de nacimiento: "+base[i].municipioNacimiento+" - "+base[i].estadoNacimiento+"\n";
    			salida+="--------------------------------------\n";
    			return salida;
    		}
    	}
    	return "--------------------------------------\nNo se encontro una coincidencia con este CURP, lo sentimos.";
    }
}
