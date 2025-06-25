import java.io.*;
import java.net.*;
import java.util.*;

public class Coordinador {
    private static List<String> servidores = Arrays.asList("localhost:5001", "localhost:5002"); // Lista de servidores disponibles
    private static int index = 0; // Índice para balancear las solicitudes

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(4000)) {
            System.out.println("Coordinador en espera de conexiones en el puerto 4000...");

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                new Thread(new ManejadorCliente(clienteSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ManejadorCliente implements Runnable {
        private Socket socket;

        public ManejadorCliente(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                String solicitud = in.readLine();
                System.out.println("Cliente solicita acceso: " + solicitud);

                // Seleccionar un servidor disponible
                String servidorAsignado = servidores.get(index);
                index = (index + 1) % servidores.size(); // Balanceo circular

                out.println(servidorAsignado); // Enviar dirección del servidor al cliente
                System.out.println("Asignado a: " + servidorAsignado);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
