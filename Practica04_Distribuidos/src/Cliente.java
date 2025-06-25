import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        try (Socket socketCoordinador = new Socket("localhost", 4000);
             BufferedReader in = new BufferedReader(new InputStreamReader(socketCoordinador.getInputStream()));
             PrintWriter out = new PrintWriter(socketCoordinador.getOutputStream(), true)) {

            out.println("Solicitud de acceso");

            String servidor = in.readLine();
            System.out.println("Servidor asignado: " + servidor);

            // Conectar con el servidor asignado
            String[] datosServidor = servidor.split(":");
            String host = datosServidor[0];
            int puerto = Integer.parseInt(datosServidor[1]);

            try (Socket socketServidor = new Socket(host, puerto);
                 BufferedReader inServ = new BufferedReader(new InputStreamReader(socketServidor.getInputStream()));
                 PrintWriter outServ = new PrintWriter(socketServidor.getOutputStream(), true)) {

                outServ.println("Petición al servidor");
                System.out.println("Respuesta: " + inServ.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
