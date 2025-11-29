/**
 * Clase para representar una base de conocimiento
 */
package repCono;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Ines
 * @version 2025
 */
public class BaseCono {

  // ATRIBUTOS
  private Map<String, List<ClausHorn>> baseReglas; // optimiza para busquedas basadas en "consecuente"
  private Set<ClausHorn> baseHechos; // conocimiento especifico

  // METODOS
  /**
   * constructor por defecto
   */
  public BaseCono() {
    baseReglas = new HashSet<ClausHorn>();
    baseHechos = new HashSet<ClausHorn>();
  }

  /**
   * constructor copia
   * construye una BC a partir de otra haciendo copia DURA
   */
  public BaseCono(BaseCono otra) {
    baseReglas = new HashSet<ClausHorn>(otra.getBaseReglas());
    baseHechos = new HashSet<ClausHorn>(otra.getBaseHechos());
  }

  /**
   * metodo para aniadir un hecho a la base de conocimiento
   * 
   * @param h el String que representa el hecho a aniadir
   */
  public void aniade(String h) {
    ClausHorn hecho = new ClausHorn(h);
    baseHechos.add(hecho);
  }

  /**
   * metodo para aniadir un hecho o una regla a la base de conocimiento
   * 
   * @param c la clausula de Horn que representa el hecho o regla a aniadir
   */
  public void aniade(ClausHorn c) {
    if (c.esUnHecho())
      baseHechos.add(c);
    else
      baseReglas.add(c);
  }

  /**
   * metodo que devuelve el numero de reglas en la base de conocimiento
   * 
   * @return el numero de reglas
   */
  public int getNumReglas() {
    return baseReglas.size();
  }

  /**
   * metodo que devuelve el numero de hechos en la base de conocimiento
   * 
   * @return el numero de hechos
   */
  public int getNumHechos() {
    return baseHechos.size();
  }

  /**
   * metodo que devuelve todos los hechos como un conjunto de clausulas de Horn
   * (sin premisas)
   * 
   * @return el conjunto de clausulas que conforman la base de Hechos
   */
  // public List<ClausHorn> getBaseHechos(){
  // return new LinkedList<ClausHorn>(this.baseHechos);
  // }
  public Set<ClausHorn> getBaseHechos() {
    return this.baseHechos;
  }

  /**
   * metodo que devuelve todos los hechos como una lista de variables
   * proposicionales (String)
   * 
   * @return la lista de hechos en la base de conocimiento (cada hecho es una
   *         variable proposicional, un String)
   */
  public List<String> getListaHechos() {
    List<String> listaHechos = new LinkedList<String>();
    for (ClausHorn c : baseHechos) {
      listaHechos.add(c.getConsecuente());
    }
    return listaHechos;
  }

  /**
   * metodo que devuelve la base de reglas como un conjunto de clausulas de Horn
   * 
   * @return el conjunto de clausulas que conforman la base de reglas
   */
  public Set<ClausHorn> getBaseReglas() {
    return this.baseReglas;
  }

  public Set<ClausHorn> getTodasClausulas() {
    Set<ClausHorn> clausulas = new HashSet<ClausHorn>(this.baseReglas);
    clausulas.addAll(this.baseHechos);
    return clausulas;
  }

  /**
   * metodo que devuelve todas las reglas que tienen una variable dada como
   * consecuente
   * 
   * @param p una variable proposicional
   * @return la lista de reglas cuyo consecuente es p
   */
  public List<ClausHorn> reglasConConsecuente(String p) {
    List<ClausHorn> lista = new LinkedList<ClausHorn>();
    for (ClausHorn c : baseReglas) {
      if (c.getConsecuente().equals(p))
        lista.add(c);
    }
    return lista;
  }

  /**
   * metodo para saber si un hecho es cierto en la base de conocimiento
   * 
   * @param un String, el simbolo de variable proposicional que queremos saber si
   *           se cumple
   */
  public boolean esCierto(String p) {
    ClausHorn pclausula = new ClausHorn(p);
    return baseHechos.contains(pclausula);
  }

}
