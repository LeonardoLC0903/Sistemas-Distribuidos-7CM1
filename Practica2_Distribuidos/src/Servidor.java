import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Servidor extends JFrame {
    private JTextField campoIntroducir; 
    private JTextArea areaPantalla; 
    private ObjectOutputStream salida;
    private ObjectInputStream entrada; 
    private ServerSocket servidor; 
    private Socket conexion; 
    private int contador = 1; 

    public Servidor() {
        super("Servidor");

        campoIntroducir = new JTextField();
        campoIntroducir.setEditable(false);
        campoIntroducir.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evento) {
                    enviarDatos(evento.getActionCommand());
                    campoIntroducir.setText("");
                }
            }
        );

        add(campoIntroducir, BorderLayout.NORTH);

        areaPantalla = new JTextArea();
        add(new JScrollPane(areaPantalla), BorderLayout.CENTER);

        setSize(300, 150); 
        setVisible(true); 
    }

    public void ejecutarServidor() {
        try {
            servidor = new ServerSocket(12345, 100); 

            while (true) {
                try {
                    esperarConexion(); 
                    obtenerFlujos(); 
                    procesarConexion(); 
                } catch (EOFException excepcionEOF) {
                    mostrarMensaje("\nServidor termino la conexion");
                } finally {
                    cerrarConexion(); 
                    contador++;
                }
            }
        } catch (IOException exepcionES) {
            exepcionES.printStackTrace();
        }
    }

 
    private void esperarConexion() throws IOException {
        mostrarMensaje("Esperando una conexion\n");
        conexion = servidor.accept();
        mostrarMensaje("Conexion " + contador + " recibida de: " +
            conexion.getInetAddress().getHostName());
    }


    private void obtenerFlujos() throws IOException {
        salida = new ObjectOutputStream(conexion.getOutputStream());
        salida.flush();
        entrada = new ObjectInputStream(conexion.getInputStream());
        mostrarMensaje("\nSe obtuvieron los flujos de E/S\n");
    }


    private void procesarConexion() throws IOException {
        String mensaje = "Conexion exitosa";
        enviarDatos(mensaje);
        setTextFieldEditable(true);

        do {
            try {
                mensaje = (String) entrada.readObject();
                mostrarMensaje("\n" + mensaje);
            } catch (ClassNotFoundException excepcionClaseNoEncontrada) {
                mostrarMensaje("\nSe recibio un tipo de objeto desconocido");
            }
        } while (!mensaje.equals("CLIENTE>>> TERMINAR"));
    }

    private void cerrarConexion() {
        mostrarMensaje("\nTerminando conexion\n");
        setTextFieldEditable(false);

        try {
            salida.close();
            entrada.close();
            conexion.close();
        } catch (IOException exepcionES) {
            exepcionES.printStackTrace();
        }
    }


    private void enviarDatos(String mensaje) {
        try {
            salida.writeObject("SERVIDOR>>> " + mensaje);
            salida.flush();
            mostrarMensaje("\nSERVIDOR>>> " + mensaje);
        } catch (IOException exepcionES) {
            areaPantalla.append("\nError al escribir objeto");
        }
    }

  
    private void mostrarMensaje(final String mensajeAMostrar) {
        SwingUtilities.invokeLater(
            new Runnable() {
                public void run() {
                    areaPantalla.append(mensajeAMostrar);
                }
            }
        );
    }


    private void setTextFieldEditable(final boolean editable) {
        SwingUtilities.invokeLater(
            new Runnable() {
                public void run() {
                    campoIntroducir.setEditable(editable);
                }
            }
        );
    }
}