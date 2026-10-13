/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Quiero implementar en Java una función recursiva que resuelva si una palabra (String) es un palíndromo.
 * 
 * Este problema puede resolverse recursivamente porque una palabra es palíndroma si y solo si 
 * su primer carácter coincide con el último y, simultáneamente, la subcadena intermedia resultante 
 * de quitar ambos extremos también es un palíndromo.
 * 
 * El caso base es cuando la longitud de la cadena es menor o igual a 1 (s.length() <= 1), porque 
 * una cadena vacía o de una sola letra es simétrica por definición trivial, por lo que la función 
 * no se divide más y retorna true directamente.
 * 
 * El caso recursivo es esPalindromo(s.substring(1, s.length() - 1)), porque en cada llamada se 
 * verifica si s.charAt(0) == s.charAt(s.length() - 1); si coinciden, el problema se reduce 
 * eliminando los dos caracteres extremos en cada paso (disminuyendo la longitud en 2), 
 * convergiendo hacia el caso base; si no coinciden, se corta inmediatamente retornando false.
 * 
 * La función debe devolver un valor boolean: true si la palabra se lee igual en ambos sentidos, 
 * o false si no lo es o ante diferencias en los extremos.
 * 
 * Quiero que el código tenga comentarios explicando el caso base, el caso recursivo y la pila de llamadas.
 * 
 * También quiero probarlo con estos casos:
 * - Caso 1: Cadena vacía "" y de un solo carácter "a" (esperado: true).
 * - Caso 2: Palíndromo de longitud impar "reconocer" y "radar" (esperado: true).
 * - Caso 3: Palíndromo de longitud par "neuquen" y "ana" (esperado: true).
 * - Caso 4: Palabras que no son palíndromos "computadora" y "hola" (esperado: false).
 * - Caso 5: Sensibilidad a mayúsculas/minúsculas y espacios (ej. "Radar" normalizado a minúsculas).
 * =========================================================================================
 */

/**
 * Implementación de la verificación recursiva de palíndromos sobre cadenas de texto.
 * Evalúa la simetría bilateral descartando pares simétricos de caracteres en los extremos.
 */
public class Ej9 {

    /**
     * Determina de forma recursiva si una cadena de texto es un palíndromo.
     *
     * Fundamentación recursiva y pila de llamadas:
     * - Guarda de nulidad: Si s == null, retorna false por precondición.
     * - CASO BASE: Si s.length() <= 1, la cadena restante (vacía para cadenas de longitud par,
     *   o de 1 carácter para cadenas de longitud impar) es simétrica por naturaleza.
     *   Detiene el apilamiento de llamadas en el call stack y retorna true.
     * - CONDICIÓN DE CORTE TEMPRANO: Si s.charAt(0) != s.charAt(s.length() - 1), la simetría
     *   se rompe en el nivel actual; retorna false inmediatamente sin explorar el centro.
     * - CASO RECURSIVO: Si los extremos coinciden, invoca a esPalindromo(s.substring(1, s.length() - 1)).
     * - Reducción del problema: En cada llamada se eliminan simultáneamente 2 caracteres (los extremos),
     *   reduciendo la longitud en L - 2 y garantizando la convergencia finita hacia el caso base.
     *
     * @param s Cadena de texto a evaluar.
     * @return {@code true} si la cadena es palíndroma; {@code false} en caso contrario.
     */
    public static boolean esPalindromo(String s) {
        // Guarda previa ante referencias nulas
        if (s == null) {
            return false;
        }

        // CASO BASE: Cadenas vacías o de longitud 1 son siempre palíndromos
        if (s.length() <= 1) {
            return true;
        }

        // VERIFICACIÓN DE EXTREMOS: Si el primer y el último carácter difieren, no es palíndromo
        if (s.charAt(0) != s.charAt(s.length() - 1)) {
            return false; // Salida temprana sin apilar más llamadas
        }

        // CASO RECURSIVO: Evalúa la subcadena interna omitiendo ambos extremos
        return esPalindromo(s.substring(1, s.length() - 1));
    }

    /**
     * Versión auxiliar que normaliza el texto (convierte a minúsculas y elimina espacios)
     * antes de ejecutar la verificación recursiva.
     */
    public static boolean esPalindromoNormalizado(String s) {
        if (s == null) {
            return false;
        }
        String limpia = s.replaceAll("\\s+", "").toLowerCase();
        return esPalindromo(limpia);
    }

    /**
     * Batería de pruebas que verifica la totalidad de los casos solicitados.
     */
    public static void main(String[] args) {
        System.out.println("=== BATERÍA DE PRUEBAS: VERIFICACIÓN RECURSIVA DE PALÍNDROMOS ===");

        // Caso 1: Cadena vacía y un solo carácter
        verificar("", true);
        verificar("a", true);

        // Caso 2: Palíndromos de longitud impar
        verificar("reconocer", true);
        verificar("radar", true);

        // Caso 3: Palíndromos adicionales
        verificar("neuquen", true);
        verificar("ana", true);
        verificar("somos", true);

        // Caso 4: Palabras no palíndromas
        verificar("computadora", false);
        verificar("hola", false);
        verificar("recursivo", false);

        // Caso 5: Prueba con normalización (mayúsculas y frases)
        System.out.println("\n--- Pruebas con normalización (case-insensitive y espacios) ---");
        verificarNormalizado("Radar", true);
        verificarNormalizado("Neuquen", true);
        verificarNormalizado("anita lava la tina", true);
        verificarNormalizado("programacion", false);
    }

    private static void verificar(String texto, boolean esperado) {
        boolean obtenido = esPalindromo(texto);
        boolean correcto = (obtenido == esperado);
        System.out.printf("Palabra: %-15s -> Obtenido: %-5b | Esperado: %-5b | Estado: %s%n",
                "\"" + texto + "\"", obtenido, esperado, correcto ? "CORRECTO" : "FALLÓ");
    }

    private static void verificarNormalizado(String texto, boolean esperado) {
        boolean obtenido = esPalindromoNormalizado(texto);
        boolean correcto = (obtenido == esperado);
        System.out.printf("Frase:   %-20s -> Obtenido: %-5b | Esperado: %-5b | Estado: %s%n",
                "\"" + texto + "\"", obtenido, esperado, correcto ? "CORRECTO" : "FALLÓ");
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación realizada:
 *    Se agregó una función sobrecargada/auxiliar 'esPalindromoNormalizado' junto al método
 *    recursivo base puro.
 * 
 * 2. Justificación técnica:
 *    El método recursivo 'esPalindromo' opera estrictamente carácter por carácter sobre el
 *    String provisto. Sin embargo, en el mundo real cadenas como "Radar" o "Anita lava la tina"
 *    son semánticamente palíndromos pero fallarían ante comparaciones ASCII/Unicode directas
 *    debido a diferencias entre mayúsculas y minúsculas o espacios intermedios.
 *    La inclusión del método normalizador permite soportar frases y variantes tipográficas
 *    sin contaminar la pureza didáctica de la función recursiva base.
 * =========================================================================================
 */