import java.util.ArrayList;

public class BagOfWords {

	String palabra;
	ArrayList<NodoBOW> ListaEtiquetas;
	
	public BagOfWords(String p, ArrayList<NodoBOW> LE)
	{
		palabra = p;
		ListaEtiquetas = LE;
	}
	
	public String Palabra ()
	{
		return palabra;
	}
	
	public ArrayList<NodoBOW> listaEtiquetas()
	{
		return ListaEtiquetas;
	}
	
	//Comentario de prueba....
}
