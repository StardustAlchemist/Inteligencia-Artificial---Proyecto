import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;

public class Inicio 
{

	private JFrame frame;
	String texto = "";
	String NombreArchivo = "";
	
	
	Entrenamiento entrenar; // Instancia de la clase Entrenamiento
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try
				{
					Inicio window = new Inicio();
					window.frame.setVisible(true);
				} 
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Inicio()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnEntrenar = new JButton("Entrenar");
		
		btnEntrenar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				/**
				 * Codigo para el JFileChooser
				 */
				JFileChooser fc = new JFileChooser();
				//fc.setCurrentDirectory(new java.io.File("C:/Users/"));
				fc.setDialogTitle("Carga de Archivo");
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if(fc.showOpenDialog(btnEntrenar) == JFileChooser.APPROVE_OPTION)
				{
				}
				NombreArchivo = fc.getSelectedFile().getAbsolutePath();
				
				
				entrenar = new Entrenamiento(NombreArchivo); // Se inicializa la clase entrenar.
				
				
			
				
				
			}
		});
		btnEntrenar.setBounds(1+69, 110, 89, 23);
		frame.getContentPane().add(btnEntrenar);
	}
}
