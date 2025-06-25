import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

// Clase principal del servidor RMI
public class Server {
    public static void main(String[] args) {
        try {
        	
        	Registros base[]=new Registros[5];   
            base[0] = new Registros("ABC1234567890XYZ1", "Juan", "Pérez", "Gómez", 25, "Jalisco", "Guadalajara", 'M');
            base[1]  = new Registros("DEF0987654321XYZ2", "María", "López", "Hernández", 30, "Nuevo León", "Monterrey", 'F');
            base[2]  = new Registros("GHI1122334455XYZ3", "Carlos", "García", "Martínez", 22, "Ciudad de México", "Coyoacán", 'M');
            base[3]  = new Registros("JKL5566778899XYZ4", "Ana", "Martínez", "Sánchez", 28, "Puebla", "Puebla", 'F');
            base[4]  = new Registros("MNO9988776655XYZ5", "Luis", "Rodríguez", "Díaz", 35, "Veracruz", "Xalapa", 'M');

        	
            // Crear una instancia de la implementación remota.
            BusquedaImpl obj = new BusquedaImpl(base);
            
            // Iniciar el registro RMI en el puerto 1099 (puerto por defecto)
            LocateRegistry.createRegistry(1099);
            
            // Registrar el objeto remoto en el registro RMI
            Naming.rebind("rmi://localhost/Buscar", obj);
            
            System.out.println("Servidor RMI listo y esperando clientes...");
        } catch (Exception e) {
            System.err.println("Excepción en el servidor RMI: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
