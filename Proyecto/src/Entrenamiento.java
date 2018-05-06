import java.io.*;
import java.util.ArrayList;




public class Entrenamiento 
{
	/**
	 * Variables Globales
	 */
 
	public ArrayList<String> list = new ArrayList<String>(); // Listado de las lineas del archivo de texto (palabras + etiqueta)
	public ArrayList<String> palabras = new ArrayList<String>(); // Se guardan palabras de cada oracion (No estan separadas)
	public ArrayList<String> etiquetas = new ArrayList<String>(); // Se guardan etiquetas de cada oracion.
	public ArrayList<String> listadoPalabras = new ArrayList<String>(); // Aqui se guardan las palabras individualmente
	
	public Entrenamiento(String NombreArchivo)
	{
		LeerTexto(NombreArchivo);
		DividirPalabras(list);
		SepararPalabras(palabras);
		
		
	}
	
	/**
	 * Metodo que lee el archivo de entrenamiento
	 */
	
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
	
	/**
	 * Metodo que divide palabras y etiqueta. 
	 */
	
	private void DividirPalabras(ArrayList<String> listadoLineas)
	{
		String linea;
		
		String [] palabraEtiqueta;
		
		for(int i = 0; i < listadoLineas.size(); i++)
		{
			linea = listadoLineas.get(i);
			palabraEtiqueta = linea.split("\\|");
			
			palabras.add(palabraEtiqueta[0]); // Se almacena la linea de palabras de la linea completa.
			etiquetas.add(palabraEtiqueta[1]); //Se almacena la etiqueta de la linea completa.
		}
		
	
		
	}
	
	
	/**
	 *  Separar las palabras individualmente
	 * @param lineaDePalabras
	 */
	
	private void SepararPalabras(ArrayList<String> lineaDePalabras)
	{
		String [] lineaSeparar;
		
		for(int i = 0; i < lineaDePalabras.size(); i++)
		{
			lineaSeparar = lineaDePalabras.get(i).split(" ");
			
			for(int j = 0; j < lineaSeparar.length; j++)
			{
				if(!listadoPalabras.contains(lineaSeparar[j])) // Se evalua si la palabra se encuentra o no en la lista.
				{
					listadoPalabras.add(lineaSeparar[j]);
				}
			}
		}
		
	}
	
}
