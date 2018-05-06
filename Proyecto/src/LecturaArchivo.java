import java.io.*;
import java.util.ArrayList;



public class LecturaArchivo 
{
	public ArrayList<String> list = new ArrayList<String>();
	
	public LecturaArchivo() //Constructor de la clase. 
	{
		
	}
	
	
	public void LeerTexto(String NombreArchivo) // Método para leer el archivo de entrenamiento.
	{
		String texto = "";
		try
		{
			BufferedReader bf = new BufferedReader(new FileReader(NombreArchivo));
			String temp = "";
			String bfRead;
			
			while((bfRead = bf.readLine()) != null)
			{
				list.add(bfRead); // Almacena en la lista todas las lineas que va leyendo.
			}
			
			bf.close(); // Cierra el Archivo, esto siempre se debe de hacer al finalizar de leerlo. 
			
			
		}
		catch(Exception e)
		{
			System.out.println("No se encontro el archivo");
		}
		
	
	}
}
