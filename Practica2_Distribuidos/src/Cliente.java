import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Cliente extends JFrame {
    private JTextField campoIntroducir; 
    private JTextArea areaPantalla; 
    private ObjectOutputStream salida; 
    private ObjectInputStream entrada; 
    private String mensaje = ""; 
    private String servidorChat; 
    private Socket cliente; 

    public Cliente(String host) {
        super("Cliente");
        servidorChat = host; 

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

    public void ejecutarCliente() {
        try {
            conectarAlServidor(); 
            obtenerFlujos(); 
            procesarConexion(); 
        } catch (EOFException excepcionEOF) {
            mostrarMensaje("\nCliente termino la conexion");
        } catch (IOException excepcionES) {
            excepcionES.printStackTrace();
        } finally {
            cerrarConexion(); 
        }
    }

    private void conectarAlServidor() throws IOException {
        mostrarMensaje("Intentando realizar conexion\n");
        cliente = new Socket(InetAddress.getByName(servidorChat), 12345);
        mostrarMensaje("Conectado a: " + cliente.getInetAddress().getHostName());
    }

    private void obtenerFlujos() throws IOException {
        salida = new ObjectOutputStream(cliente.getOutputStream());
        salida.flush();
        entrada = new ObjectInputStream(cliente.getInputStream());
        mostrarMensaje("\nSe obtuvieron los flujos de E/S\n");
    }

    private void procesarConexion() throws IOException {
        establecerCampoEditable(true);
        do {
            try {
                mensaje = (String) entrada.readObject();
                mostrarMensaje("\n" + mensaje);
            } catch (ClassNotFoundException excepcionClaseNoEncontrada) {
                mostrarMensaje("nSe recibio un tipo de objeto desconocido");
            }
        } while (!mensaje.equals("SERVIDOR>>> TERMINAR"));
    }

    private void cerrarConexion() {
        mostrarMensaje("\nCerrando conexion");
        establecerCampoEditable(false);
        try {
            salida.close();
            entrada.close();
            cliente.close();
        } catch (IOException excepcionES) {
            excepcionES.printStackTrace();
        }
    }

    private void enviarDatos(String mensaje) {
        try {
            salida.writeObject("CLIENTE>>> " + mensaje);
            salida.flush();
            mostrarMensaje("\nCLIENTE>>> " + mensaje);
        } catch (IOException excepcionES) {
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

    private void establecerCampoEditable(final boolean editable) {
        SwingUtilities.invokeLater(
            new Runnable() {
                public void run() {
                    campoIntroducir.setEditable(editable);
                }
            }
        );
    }
}
