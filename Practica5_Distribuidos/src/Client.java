import java.rmi.Naming;

import javax.swing.JOptionPane;

// Clase principal del cliente RMI
public class Client {
    public static void main(String[] args) {
        try {
            // Buscar el objeto remoto registrado bajo el nombre "Hello"
            Busqueda stub = (Busqueda) Naming.lookup("rmi://localhost/Buscar");
                        
            String curp=JOptionPane.showInputDialog("Introduce tu curp para consultar tu informacion: ");
            
            // Invocar el método remoto
            String response = stub.busc(curp);
            System.out.println("Respuesta desde el servidor: " + response);
            Thread.sleep(100000);
        } catch (Exception e) {
            System.err.println("Excepción en el cliente RMI: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
