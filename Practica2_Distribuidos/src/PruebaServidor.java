import javax.swing.JFrame;

public class PruebaServidor
{
	public static void main( String args[] )
	{
		Servidor aplicacion = new Servidor(); 
		aplicacion.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		aplicacion.ejecutarServidor(); 
	} 
}