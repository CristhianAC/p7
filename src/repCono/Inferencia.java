/**
 * Clase para realizar inferencia en L0
 */
package repCono;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ines y alumnos
 * @version 2025
 */
public class Inferencia {

    // ATRIBUTOS
    BaseCono BC; 			// base de conocimiento
    String ob;  			// objetivo (puede no haber)
    Set<String> conoFinal; 	// conocimiento (hechos) tras lanzar el proceso de inferencia

    // pueden aniadirse atributos auxiliares si se estima oportuno
    /**
     * Constructor a partir de una base de conocimiento; no hay objetivo
     *
     * @param BC, la base de conocimiento
     */
    public Inferencia(BaseCono BC) {
        this.BC = BC;
        this.ob = "*"; // convenio para indicar que no hay objetivo
        this.conoFinal = new HashSet<String>(BC.getListaHechos()); // antes de la inferencia, solo se sabe los hechos iniciales
    }

    /**
     * Constructor a partir de una base de conocimiento y de un objetivo
     *
     * @param BC, la base de conocimiento
     * @param ob, el objetivo (String)
     */
    public Inferencia(BaseCono BC, String ob) {
        this.BC = BC;
        this.ob = ob;
        this.conoFinal = new HashSet<String>(BC.getListaHechos()); // antes de la inferencia, solo se sabe los hechos iniciales
    }

    /**
     * observador que nos dice si hay objetivo para realizar la inferencia o no
     *
     * @return cierto si hay un objetivo
     */
    public boolean hayObjetivo() {
        return (ob.equals("*") ? false : true);
    }

    /**
     * observador para consultar el conocimiento inferido tras un proceso de
     * inferencia: esto incluye los hechos iniciales mas los que se han ido
     * concluyendo durante el encadenamiento al aplicar Modus Ponens
     *
     * @return el conjunto de variables proposicionales que sabemos que son
     * ciertas (hechos)
     */
    public Set<String> getConocimientoTrasInferencia() {
        return conoFinal;
    }

    // ENCADENAMIENTO HACIA DELANTE
    /**
     * metodo que lanza un proceso de encadenamiento hacia delante
     *
     * @return cierto si se alcanza el objetivo, falso si no (o si no habia
     * objetivo)
     */
    public boolean encDelante() {
        // Si hay un objetivo definido y ya forma parte de los hechos conocidos, retornamos true
        if (!ob.equals("*") && conoFinal.contains(ob)) {
            return true;
        }

        boolean nuevoHecho = true;
        while (nuevoHecho) {
            nuevoHecho = false;
            // Iteramos sobre todas las reglas de la base de conocimiento
            for (ClausHorn regla : BC.getBaseReglas()) {
                String consecuente = regla.getConsecuente();

                // Si el consecuente ya es conocido, no necesitamos volver a inferirlo
                if (conoFinal.contains(consecuente)) {
                    continue;
                }

                // Comprobamos si todas las premisas de la regla se cumplen con el conocimiento actual
                boolean premisasCumplidas = true;
                for (String premisa : regla.getPremisas()) {
                    if (!conoFinal.contains(premisa)) {
                        premisasCumplidas = false;
                        break;
                    }
                }

                // Si todas las premisas se cumplen, inferimos el consecuente (Modus Ponens)
                if (premisasCumplidas) {
                    conoFinal.add(consecuente);
                    nuevoHecho = true; // Marcamos que hubo un cambio para seguir iterando

                    // Si el nuevo hecho es el objetivo, terminamos con éxito
                    if (!ob.equals("*") && consecuente.equals(ob)) {
                        return true;
                    }
                }
            }
        }

        // Si el bucle termina sin encontrar el objetivo, devolvemos false
        return false;
    }

    // ENCADENAMIENTO HACIA ATRAS
    /**
     * Metodo que lanza un proceso de encadenamiento hacia atras a partir del
     * objetivo
     *
     * @return cierto si el objetivo es consecuencia de la base de conocimiento
     */
    public boolean encAtras() {
        // Si no hay objetivo definido, no se puede encadenar hacia atrás
        if (ob == null || ob.equals("*")) {
            return false;
        }

        // Verificar si ya conocemos el objetivo como un hecho
        if (conoFinal.contains(ob)) {
            return true;
        }

        // Obtener las reglas de la base de conocimiento (Clausulas de Horn)
        Set<ClausHorn> reglas = BC.getBaseReglas();

        // Iterar sobre las reglas para buscar si alguna puede derivar el objetivo
        for (ClausHorn regla : reglas) {
            // Si el consecuente de la regla es el objetivo
            if (regla.getConsecuente().equals(ob)) {
                boolean todosLosAntecedentesCumplidos = true;

                // Verificar si todas las premisas de la regla se cumplen (recursivamente)
                for (String antecedente : regla.getPremisas()) {
                    Inferencia subInferencia = new Inferencia(BC, antecedente);
                    if (!subInferencia.encAtras()) {
                        todosLosAntecedentesCumplidos = false;
                        break;
                    }
                }

                // Si todos los antecedentes se cumplen, el objetivo se cumple
                if (todosLosAntecedentesCumplidos) {
                    conoFinal.add(ob); // Añadir el objetivo a los hechos conocidos
                    return true;
                }
            }
        }

        // Si no se puede deducir el objetivo, devolver falso
        return false;
        // Se puede (incluso puede que se necesite) aniadir metodos auxiliares

    }

    // Se puede (incluso puede que se necesite) aniadir metodos auxiliares
}
