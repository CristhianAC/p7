/**
 * clase con metodos estaticos que resultan utiles; esencialmente para leer la base de conocimiento de fichero
 */
package repCono;

import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Ines
 * @version 2019
 */
public class Util {

	
	/** Metodo algo chapuza para leer de fichero la base de conocimiento
	 * @param nomFich el nombre del fichero
	 * @return el objetivo (un String) si lo hay, o "*" si no lo hay
	 * @throws Exception
	 */
	public static String leeBCDeFichero( String nomFich, BaseCono bc ) throws Exception {
		try {
			//
			//bc = new BaseCono();
			String ob ="";
			Scanner dis = new Scanner(new FileInputStream(nomFich));
			String linea="";
			// leemos primera(s) lineas de comentario(s) hasta llegar a "#reglas"
			while (dis.hasNextLine() && !linea.contains("#reglas")) {
				linea = dis.nextLine();
			}
			// reglas
			boolean sigue = true;
			while (dis.hasNextLine() && sigue ) {
				linea = dis.nextLine();
				if( linea.contains("#hechos") )
					sigue = false;
				else {
					ClausHorn regla = Util.clausulaDeLinea(linea);
					bc.aniade(regla);
				}
			} // terminamos las reglas
			// hechos
			sigue = true;
			while (dis.hasNextLine() && sigue ) {
				linea = dis.nextLine();
				if( linea.contains("#objetivo") )
					sigue = false;
				else {
					ClausHorn hecho = Util.clausulaDeLinea(linea);
					bc.aniade(hecho);
				}
			}
			// terminamos los hechos
			// leemos el objetivo (o '*' si no lo hay)
			if( dis.hasNextLine() ) {
				linea=dis.nextLine();
				ob = linea;
			}
			dis.close();
			return ob;
		}// fin try
		catch(Exception e) {
			throw e;

		}// fin catch

	}// fin metodo leer de fichero


	/**
	 * metodo que "tokeniza" un String de la forma "q:-p1,p2,...,pn." y lo transforma en una clausula
	 * @return
	 */
	public static ClausHorn clausulaDeLinea( String s ) {
		if(s.contains("#")) return null; // es un comentario
		s=s.replace('.',' '); // quitamos el punto del final
		s=s.trim();// quitamos espacios en blanco		
		String[] cabezaCuerpo = s.split(":-");
		if( cabezaCuerpo.length == 1 ) { // no habia separador ":-" es un hecho
			return new ClausHorn(s);
		}
		else if( cabezaCuerpo.length == 2 ) {
			String[] arrayPremisas = cabezaCuerpo[1].split(",");
			Set<String> setPremisas = new HashSet<String>( );
			for( int i=0; i<arrayPremisas.length; i++ )
				setPremisas.add(arrayPremisas[i]);
			return new ClausHorn(setPremisas,cabezaCuerpo[0]);
		}
		else // algo va mal
			return null;
		}
}
