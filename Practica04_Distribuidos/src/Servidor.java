import java.io.*;
import java.net.*;

public class Servidor {
    private int puerto;

    public Servidor(int puerto) {
        this.puerto = puerto;
    }

    public void iniciar() {
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor iniciado en el puerto " + puerto);

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

                String mensaje = in.readLine();
                System.out.println("Solicitud recibida: " + mensaje);

                // Simulación de procesamiento
                Thread.sleep(2000);
                out.println("Respuesta del servidor: Procesado → " + mensaje);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int puerto = Integer.parseInt(args[0]); // Recibe el puerto por argumento
        new Servidor(puerto).iniciar();
    }
}
