import java.util.ArrayList;

public class GuardarPalabras 
{
	public ArrayList<String> palabras = new ArrayList<String>(); // Se guardan palabras de cada oracion (No estan separadas)
	public ArrayList<String> etiquetas = new ArrayList<String>(); // Se guardan etiquetas de cada oracion.
	
	
	
	public GuardarPalabras()
	{
		
	}
	
	public void DividirPalabras(ArrayList<String> listadoLineas)
	{
		String linea;
		
		String [] palabraEtiqueta;
		
		for(int i = 0; i < listadoLineas.size(); i++)
		{
			linea = listadoLineas.get(i);
			palabraEtiqueta = linea.split("\\|");
			
			palabras.add(palabraEtiqueta[0]);
			etiquetas.add(palabraEtiqueta[1]);
		}
		
		
	}
}
