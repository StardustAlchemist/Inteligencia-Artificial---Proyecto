import java.io.*;


public class LecturaArchivo 
{
	
	public LecturaArchivo() //Constructor de la clase. 
	{
		
	}
	
	
	public String LeerTexto(String NombreArchivo) // Método para leer el archivo de entrenamiento.
	{
		String texto = "";
		try
		{
			BufferedReader bf = new BufferedReader(new FileReader(NombreArchivo));
			String temp = "";
			String bfRead;
			
			while((bfRead = bf.readLine()) != null)
			{
				temp = temp + bfRead;
			}
			
			bf.close();
			texto = temp;
			
		}
		catch(Exception e)
		{
			System.out.println("No se encontro el archivo");
		}
		
		return texto;
	}
}
