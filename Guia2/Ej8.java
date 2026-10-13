/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Actúa como un desarrollador Java experto e implementa una función recursiva para invertir 
 * una cadena de texto (String):
 * 
 * Fundamentación conceptual recursiva:
 * - Caso base (cadena vacía o unitaria): Justifica formalmente por qué si la longitud de la 
 *   cadena es menor o igual a 1 (s.length() <= 1) la cadena ya se encuentra invertida por 
 *   definición elemental, retornando el String directamente sin generar nuevas llamadas a 
 *   la pila de ejecución.
 * - Descomposición y reducción del problema: Explica cómo aislar el primer carácter mediante 
 *   s.charAt(0) y cómo la operación s.substring(1) reduce la longitud del problema en 
 *   exactamente un carácter por invocación, garantizando la convergencia hacia el caso base.
 * - Reconstrucción inductiva: Detalla el orden de concatenación al desapilar las llamadas en 
 *   el call stack, justificando la relación de recurrencia: 
 *   invertir(s) = invertir(s.substring(1)) + s.charAt(0), donde el primer carácter original 
 *   pasa a ocupar la última posición del resultado parcial.
 * 
 * Especificaciones técnicas:
 * - Firma requerida: public static String invertirCadena(String s)
 * - Manejo de casos borde: si s es null, retorna null.
 * - Código limpio, legible y sin utilizar bucles iterativos ni la clase StringBuilder.reverse().
 * 
 * Batería de casos de prueba:
 * - Casos base: cadena vacía "" -> ""; un solo carácter "X" -> "X".
 * - Palabras simétricas (palíndromos): "reconocer" -> "reconocer"; "neuquen" -> "neuquen".
 * - Palabras comunes: "hola" -> "aloh"; "recursivo" -> "ovisrucer"; "Java" -> "avaJ".
 * - Caso con espacios intermedios: "hola mundo" -> "odnum aloh".
 * =========================================================================================
 */

/**
 * Implementación de la inversión recursiva de cadenas de texto (String).
 * Ilustra la descomposición por subcadenas y la reconstrucción en orden inverso al desapilar llamadas.
 */
public class Ej8 {

    /**
     * Invierte una cadena de caracteres de manera recursiva.
     *
     * Fundamentación recursiva:
     * - Caso base: Si s.length() <= 1, una cadena vacía ("") o de un único carácter ("a")
     *   ya está invertida por definición. Retorna 's' directamente sin apilar más llamadas.
     * - Reducción del problema: s.substring(1) genera una nueva subcadena con longitud L - 1,
     *   garantizando la convergencia finita hacia el caso base en exactamente L llamadas.
     * - Reconstrucción inductiva: Al volver de la recursión, el primer carácter original s.charAt(0)
     *   se concatena a la derecha de la subcadena ya invertida: invertir(s.substring(1)) + s.charAt(0).
     *
     * @param s Cadena de texto a invertir.
     * @return Nueva cadena con los caracteres en orden inverso, o {@code null} si la entrada es nula.
     */
    public static String invertirCadena(String s) {
        // Manejo de caso borde: preserva la ausencia de referencia
        if (s == null) {
            return null;
        }

        // CASO BASE: Cadenas de longitud 0 o 1 ya están invertidas por naturaleza
        if (s.length() <= 1) {
            return s;
        }

        // CASO RECURSIVO: Invierte el resto de la cadena y ubica el primer carácter al final
        return invertirCadena(s.substring(1)) + s.charAt(0);
    }

    /**
     * Batería de pruebas para verificar casos base, palíndromos, palabras comunes y espacios.
     */
    public static void main(String[] args) {
        System.out.println("=== BATERÍA DE PRUEBAS: INVERSIÓN RECURSIVA DE STRINGS ===");

        // Casos base
        verificar("", "");
        verificar("X", "X");

        // Palíndromos (la inversión debe coincidir con el texto original)
        verificar("reconocer", "reconocer");
        verificar("neuquen", "neuquen");

        // Palabras asimétricas comunes
        verificar("hola", "aloh");
        verificar("recursivo", "ovisrucer");
        verificar("Java", "avaJ");

        // Casos con caracteres de espacio intermedios
        verificar("hola mundo", "odnum aloh");

        // Validación de caso nulo
        verificar(null, null);
    }

    private static void verificar(String entrada, String esperado) {
        String obtenido = invertirCadena(entrada);
        boolean correcto = (esperado == null && obtenido == null) || (esperado != null && esperado.equals(obtenido));

        String entradaVisual = (entrada == null) ? "null" : "\"" + entrada + "\"";
        String obtenidoVisual = (obtenido == null) ? "null" : "\"" + obtenido + "\"";
        String esperadoVisual = (esperado == null) ? "null" : "\"" + esperado + "\"";

        System.out.printf("Entrada: %-14s -> Obtenido: %-14s | Esperado: %-14s | Estado: %s%n",
                entradaVisual, obtenidoVisual, esperadoVisual, correcto ? "CORRECTO" : "FALLÓ");
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación realizada:
 *    Se integró la guarda previa 'if (s == null) return null;' antes de evaluar 's.length() <= 1'.
 * 
 * 2. Justificación técnica:
 *    En Java, invocar 's.length()' sobre una referencia nula dispara inmediatamente un
 *    NullPointerException. Añadir la comprobación de nulidad como guarda previa defensiva
 *    asegura que la función sea completamente segura ante referencias nulas antes de iniciar
 *    el ciclo recursivo sobre la longitud de la cadena.
 * =========================================================================================
 */