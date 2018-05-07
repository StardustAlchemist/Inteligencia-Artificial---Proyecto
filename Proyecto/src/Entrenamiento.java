import java.io.*;
import java.util.ArrayList;
import java.util.Collections;


public class Entrenamiento 
{
	/**
	 * Variables Globales
	 */
 
	 ArrayList<String> list = new ArrayList<String>(); // Listado de las lineas del archivo de texto (palabras + etiqueta)
	 ArrayList<String> palabras = new ArrayList<String>(); // Se guardan palabras de cada oracion (No estan separadas)
	 ArrayList<String> etiquetas = new ArrayList<String>(); // Se guardan etiquetas de cada oracion.
	 ArrayList<String> listadoPalabras = new ArrayList<String>(); // Aqui se guardan las palabras individualmente
	 ArrayList<TablaFrecuencias> tabla = new ArrayList<TablaFrecuencias>();
	 
	
	/**
	 * Constructor de la Clase
	 * @param NombreArchivo
	 */
	public Entrenamiento(String NombreArchivo)
	{
		LeerTexto(NombreArchivo);
		DividirPalabras(list);
		SepararPalabras(palabras);
		CrearTablaFrecuencias();
		
	}
	
	/**
	 * Metodo que lee el archivo de entrenamiento
	 */
	
	public void LeerTexto(String NombreArchivo) // M�todo para leer el archivo de entrenamiento.
	{
		//String texto = "";
		try
		{
			BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(NombreArchivo), "UTF-8"));
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
			
			palabras.add(StringUtils.stripAccents(palabraEtiqueta[0]).trim()); // Se almacena la linea de palabras de la linea completa.
			etiquetas.add(palabraEtiqueta[1].trim()); //Se almacena la etiqueta de la linea completa.
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
			lineaSeparar = lineaDePalabras.get(i).split("\\s");
			
			for(int j = 0; j < lineaSeparar.length; j++)
			{
				if(lineaSeparar[j].length() != 0)
				{
                                    if(lineaSeparar[j].substring(lineaSeparar[j].length()-2, lineaSeparar[j].length()-1).matches("\\W"))
                                    {
                                      lineaSeparar[j] = lineaSeparar[j].substring(0, lineaSeparar[j].length()-1);
                                    }
                                    if(!IgnoreCase(listadoPalabras,lineaSeparar[j])) // Se evalua si la palabra se encuentra o no en la lista.
                                    {
					listadoPalabras.add(lineaSeparar[j]);
                               	    }
				}
			}
		}
		
	}
	
	private boolean IgnoreCase(ArrayList<String> lineaDePalabras, String palabra)
	{
		for(int i = 0; i<lineaDePalabras.size(); i++)
		{
			if(lineaDePalabras.get(i).equalsIgnoreCase(palabra))
				return true;
		}
		return false;
	}
	
	private void CrearTablaFrecuencias()
	{
		String etiqueta;
		String oracion;
		String [] fraseCompleta;
		String [] separarOracion;
		int frecuenciaPalabra = 0;
		ArrayList<String> palabrasRevisadas = new ArrayList<String>();
		
		for(int i = 0; i < list.size(); i++)
		{
			fraseCompleta = list.get(i).split("\\|");
		
			oracion = fraseCompleta[0];
			etiqueta = fraseCompleta[1];
			
			separarOracion = oracion.split(" "); // Separa por palabras la oracion
			
			for(int j = 0; j < separarOracion.length; j++) // Se recorre el arreglo de las palabras de la frase que se esta revisando
			{
			
				if(!palabrasRevisadas.contains(separarOracion[j]))
				{
					frecuenciaPalabra = DeterminarFrecuencia(separarOracion[j], separarOracion);
					palabrasRevisadas.add(separarOracion[j]);
					tabla.add(new TablaFrecuencias(separarOracion[j], etiqueta, i + 1, frecuenciaPalabra));
					frecuenciaPalabra = 0;
				}
			
				
			}
			
			palabrasRevisadas.clear();
		}
	}
	
	
	private int DeterminarFrecuencia(String palabraRevisada,String [] oracionSeparada)
	{
		int repetido = 0;
		
		
		for(int i = 0; i < oracionSeparada.length; i++)
		{
			if(palabraRevisada.equals(oracionSeparada[i]))
			{
				repetido++;
			}
		}
		
		return repetido;
	}
	
}
