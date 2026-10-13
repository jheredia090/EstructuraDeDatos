/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Actúa como un desarrollador Java experto e implementa una función recursiva para calcular 
 * el factorial de un número entero no negativo, considerando las siguientes directivas 
 * didácticas y técnicas:
 * 
 * Fundamentación recursiva obligatoria:
 * - Justifica por qué el cálculo del factorial es intrínsecamente recursivo a partir de su 
 *   definición matemática inductiva: n! = n * (n - 1)!.
 * - Define explícitamente el caso base: especifica qué ocurre cuando n es igual a 0 o 1, 
 *   justificando por qué en ambos escenarios se retorna 1 sin generar nuevas llamadas a la 
 *   pila de ejecución.
 * - Explica cómo se reduce el problema: detalla cómo la transición de n a (n - 1) garantiza 
 *   la convergencia finita y evita una recursión infinita (StackOverflowError).
 * 
 * Especificaciones técnicas:
 * - Firma sugerida: public static long factorial(int n)
 * - Precondiciones y validaciones: lanza IllegalArgumentException si n < 0, indicando que el 
 *   factorial no está definido para números negativos.
 * - Control de desbordamiento: documenta que con tipo primitivo 'long' el valor máximo 
 *   soportado sin overflow es n = 20; lanza IllegalArgumentException si n > 20 para evitar 
 *   resultados erróneos silenciosos.
 * 
 * Batería de pruebas sugerida:
 * - Casos base y límites: n = 0 (esperado: 1), n = 1 (esperado: 1).
 * - Casos típicos: n = 5 (esperado: 120), n = 7 (esperado: 5040).
 * - Caso límite superior seguro: n = 20 (esperado: 2432902008176640000L).
 * - Casos de error/excepción: n = -1 y n = 21.
 * =========================================================================================
 */

/**
 * Clase que implementa el cálculo recursivo del factorial de un número entero no negativo.
 * Demuestra la formulación de casos base, reducción inductiva y prevención de desbordamiento.
 */
public class Ej1{

    /**
     * Calcula de forma recursiva el factorial de un número entero n (n!).
     *
     * Fundamentación recursiva:
     * - Caso base: Para n = 0 o n = 1, por definición matemática factorial(0) = 1 y factorial(1) = 1.
     *   Retorna inmediatamente sin emitir nuevas llamadas al call stack.
     * - Caso recursivo: n * factorial(n - 1).
     * - Reducción del problema: El argumento decrece monótonamente en una unidad en cada llamada (n -> n - 1),
     *   garantizando la convergencia finita hacia el caso base.
     *
     * @param n Número entero no negativo (0 <= n <= 20).
     * @return El valor de n! expresado como un entero largo de 64 bits (long).
     * @throws IllegalArgumentException Si n es negativo (no definido) o si n > 20 (desbordamiento de long).
     */
    public static long factorial(int n) {
        // Precondición 1: El factorial no está definido para enteros negativos
        if (n < 0) {
            throw new IllegalArgumentException("El factorial no está definido para números negativos: " + n);
        }

        // Precondición 2: Prevención de desbordamiento de 64 bits (21! excede Long.MAX_VALUE)
        if (n > 20) {
            throw new IllegalArgumentException("Desbordamiento aritmético: n = " + n + " supera el límite seguro de 20 para tipo 'long'.");
        }

        // CASO BASE: Detiene la recursión cuando n es 0 o 1, retornando 1 directamente
        if (n == 0 || n == 1) {
            return 1L;
        }

        // CASO RECURSIVO: Reduce el tamaño del problema invocando factorial(n - 1)
        return n * factorial(n - 1);
    }

    /**
     * Método principal para ejecutar y verificar la batería de pruebas requerida.
     */
    public static void main(String[] args) {
        System.out.println("=== BATERÍA DE PRUEBAS: FACTORIAL RECURSIVO ===");

        // Casos base
        probarFactorial(0, 1L);
        probarFactorial(1, 1L);

        // Casos típicos
        probarFactorial(5, 120L);
        probarFactorial(7, 5040L);

        // Caso límite superior seguro en tipo primitivo long (64 bits)
        probarFactorial(20, 2432902008176640000L);

        // Casos de validación de error esperado
        probarExcepcion(-1);
        probarExcepcion(21);
    }

    private static void probarFactorial(int n, long esperado) {
        long obtenido = factorial(n);
        boolean correcto = (obtenido == esperado);
        System.out.printf("n = %2d -> Obtenido: %20d | Esperado: %20d | Estado: %s%n",
                n, obtenido, esperado, correcto ? "CORRECTO" : "FALLÓ");
    }

    private static void probarExcepcion(int n) {
        try {
            factorial(n);
            System.out.printf("n = %2d -> ERROR: Debería haber lanzado IllegalArgumentException.%n", n);
        } catch (IllegalArgumentException e) {
            System.out.printf("n = %2d -> CORRECTO: Excepción capturada con éxito (%s)%n", n, e.getMessage());
        }
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación realizada:
 *    Se incorporó explícitamente la validación de cota superior 'n > 20' con lanzamiento de
 *    IllegalArgumentException.
 * 
 * 2. Justificación técnica:
 *    En Java, el tipo de datos primitivo 'long' es un entero con signo de 64 bits cuyo valor
 *    máximo representable es Long.MAX_VALUE = 9,223,372,036,854,775,807.
 *    Mientras que 20! = 2,432,902,008,176,640,000 cabe holgadamente en 64 bits, 21! arroja
 *    51,090,942,171,709,440,000, lo que produce un desbordamiento aritmético silencioso
 *    (overflow) arrojando números negativos erróneos en tiempo de ejecución. La validación
 *    garantiza la robustez numérica de la función.
 * =========================================================================================
 */