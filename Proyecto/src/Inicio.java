import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;

public class Inicio 
{

	private JFrame frame;
	String texto = "";
	String NombreArchivo = "";
	
	//private Database database = new Database();
	
	
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
		
		final JButton btnEntrenar = new JButton("Entrenar");
		
		
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
					NombreArchivo = fc.getSelectedFile().getAbsolutePath();
					entrenar = new Entrenamiento(NombreArchivo); // Se inicializa la clase entrenar.
				}
				
			}
		});
		btnEntrenar.setBounds(50, 110, 89, 23);
		frame.getContentPane().add(btnEntrenar);
		
		 final JTextField txtfieldFrase = new JTextField();
		 txtfieldFrase.setBounds(100, 15, 250, 21);
		 txtfieldFrase.setText("");
	     txtfieldFrase.setEditable(true);
	     txtfieldFrase.setHorizontalAlignment(JTextField.LEFT); 
	     frame.getContentPane().add(txtfieldFrase);
		
	     final JButton btnValidar = new JButton("Validar");
			
			
			btnValidar.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e)
				{
					/**
					 * Codigo para validar la frase
					 */
					String frase = txtfieldFrase.getText();
					if(frase.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Favor ingrese una frase");
					}else {
						String nueva_etiqueta = entrenar.Validar(frase);
						entrenar.recalcular(frase, nueva_etiqueta);
					}
				}
			});
			btnValidar.setBounds(300, 110, 89, 23);
			frame.getContentPane().add(btnValidar);
	     
	}
}
