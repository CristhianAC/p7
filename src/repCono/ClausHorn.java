/**
 * Clase que representa una clausula de Horn con cabeza, es decir:
 * - una regla p1 && p2 && ... pn -> q
 * - un hecho q (que puede verse como una regla sin premisas)
 */
package repCono;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Ines
 *
 */
/**
 * @author Ines
 *
 */
public class ClausHorn {
	// ATRIBUTOS
	private Set<String> premisas; 	// premisas o cuerpo de la clausula, p1,...,pn (puede no haber ninguna)
	private String consecuente; 	// consecuente o cabeza de la clausula, q (obligatorio que haya)

	
	// CONSTRUCTORES
	/**
	 * Constructor a partir de un simbolo de proposicion p: construye la clausula "->p"
	 * @param p, una proposicion
	 */
	public ClausHorn( String p) {
		setConsecuente(p);
		setPremisas(new HashSet<String>()); // no hay premisas
	}
	
	/** Constructor a partir de un conjunto de premisas y de un consecuente
	 * @param prem el conjunto de premisas
	 * @param con el consecuente
	 */
	public ClausHorn( Set<String> prem, String con ) {
		setPremisas(prem);
		setConsecuente(con);
	}
	
	/** Constructor a partir de otra clausula
	 * @param otra, la clausula a copiar
	 * @param con el consecuente
	 */
	public ClausHorn( ClausHorn otra ) {
		setPremisas(otra.getPremisas());
		setConsecuente(otra.getConsecuente());
	}

	
	// OBSERVADORES Y MODIFICADORES
	/**
	 * @return las premisas
	 */
	public Set<String> getPremisas() {
		return premisas;
	}

	/**
	 * @param premisas el conjunto de premisas a copiar
	 */
	public void setPremisas(Set<String> premisas) {
		this.premisas = new HashSet<String>(premisas);
	}


	/**
	 * @return el consecuente
	 */
	public String getConsecuente() {
		return consecuente;
	}


	/**
	 * @param consecuente the consecuente a copiar
	 */
	public void setConsecuente(String consecuente) {
		this.consecuente = new String(consecuente);
	}
	
	// OTROS
	/**
	 *  metodo para conocer el numero de premisas de la clausula
	 *  @return el numero de premisas (puede ser 0)
	 */
	public int getNumPremisas() {
		return premisas.size();
	}
	
	/**
	 * metodo para saber si es un hecho
	 * @return true si es un hecho (no hay premisas)
	 */
	public boolean esUnHecho() {
		return premisas.isEmpty();
	}
	
	/**
	 * metodo para saber si es una regla
	 * @return true si es una regla (hay premisas)
	 */
	public boolean esUnaRegla() {
		return !premisas.isEmpty();
	}
	
	/**
	 * metodo para saber si una variable proposicional dada forma parte de las premisas de la regla
	 */
	public boolean tienePremisa( String p ) {
		return premisas.contains(p);
	}

	
	/**
	 * metodo toString()
	 * devuelve la regla como String con el siguiente formato:
	 * "q:-p1,p2,...,pn." si es una regla, "q." si es un hecho.
	 */
	@Override
	public String toString() {
		if(!esUnHecho()) {
			String cuerpo = new String();
			Iterator<String> iter = premisas.iterator();
			cuerpo = cuerpo + iter.next(); // habra al menos una premisa, asi que podemos hacer iter.next() al menos una vez
			while( iter.hasNext() ) {
				cuerpo = cuerpo + "," + iter.next(); // mostramos las premisas separadas por comas
			}
			return consecuente + ":-" + cuerpo +"."; // marcamos el final de la clausula con un punto
		}
		else
			return consecuente +".";
	}

	/**
	 * metodo hashCode(), basado en toString()
	 */
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	/**
	 * metodo equals(),basado en toString()
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ClausHorn)) {
			return false;
		}
		ClausHorn other = (ClausHorn) obj;
		return other.toString().equals(this.toString());
	}
	
	

}
