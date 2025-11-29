/**
 * 
 */
package repCono;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ines
 * @version 2021
 */
public class TesterRC {


	/**
	 * @param args: tiene un unico argumento, el nombre del fichero con la base de conocimiento
	 */
	public static void main(String[] args) {
		
		BaseCono bc = new BaseCono(); // base de conocimiento inicialmente vacia
		String obj = "*"; // objetivo, inicialmente "*", representando que no hay objetivo
		// Intentamos leer la base de conocimiento del fichero
		try{
			obj=Util.leeBCDeFichero(args[0], bc );
			System.out.println("\nBase de conocimiento leida, hay objetivo = "+ !obj.contains("*"));
			System.out.println("Reglas:");
			for( ClausHorn c: bc.getBaseReglas())
				System.out.println(c);
			System.out.println("Hechos:");
			for( ClausHorn c: bc.getBaseHechos())
				System.out.println(c);
			System.out.println("Objetivo");
			System.out.println(obj);
		}
		catch(Exception e) {
			System.out.println("Algo fue mal leyendo la base de conocimiento");
		}
		
		
		
		// lanzamos el proceso de inferencia hacia delante
		Inferencia inf = new Inferencia(bc, obj);
		Set<String> conoFinal = new HashSet<String>();
		System.out.println("\nLanzamos encadenamiento hacia delante");
		inf.encDelante();
		System.out.println("Conocimiento tras el proceso de inferencia");
		conoFinal = inf.getConocimientoTrasInferencia();
		for( String hecho: conoFinal )
			System.out.println(hecho);
		
		// lanzamos el proceso de inferencia hacia atras
		conoFinal = new HashSet<String>();
		System.out.println("\nLanzamos encadenamiento hacia atras");
		inf.encAtras();
		System.out.println("Conocimiento tras el proceso de inferencia");
		conoFinal = inf.getConocimientoTrasInferencia();
		for( String hecho: conoFinal )
			System.out.println(hecho);

	}

}
