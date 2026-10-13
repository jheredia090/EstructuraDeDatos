/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Actúa como un desarrollador Java experto e implementa una función recursiva para calcular 
 * la suma de los números enteros consecutivos desde n descendiendo hasta 1:
 * 
 * Fundamentación conceptual recursiva:
 * - Caso más simple (caso base): Define explícitamente qué ocurre cuando se alcanza el valor 
 *   mínimo del recorrido y justifica por qué si n == 0 el resultado debe ser 0 (elemento 
 *   neutro de la suma), deteniendo la recursión sin nuevas llamadas a la pila de ejecución.
 * - Descomposición inductiva: Explica detalladamente por qué la función satisface la relación 
 *   de recurrencia suma(n) = n + suma(n - 1), separando el término actual del resultado del 
 *   subproblema de menor escala.
 * - Reducción del problema y convergencia: Detalla cómo la resta monótona de 1 en cada paso 
 *   asegura que cualquier entero positivo n converja de forma finita hacia el caso base.
 * 
 * Especificaciones técnicas:
 * - Firma requerida: public static long sumarHastaUno(int n)
 * - Validación de precondiciones: lanza IllegalArgumentException si n < 0, explicando que la 
 *   suma hacia 1 no está definida para enteros negativos y prevendría la convergencia.
 * - Manejo de tipos: utiliza 'long' como tipo de retorno para evitar overflow aritmético ante 
 *   valores de n moderadamente grandes.
 * 
 * Estrategia de validación y pruebas:
 * - Demuestra la corrección del algoritmo comparando los resultados devueltos contra la 
 *   fórmula cerrada de Gauss: n * (n + 1) / 2.
 * - Incluye casos de prueba:
 *   * Caso n = 0 (esperado: 0).
 *   * Caso n = 1 (esperado: 1).
 *   * Casos estándar: n = 5 (esperado: 15), n = 10 (esperado: 55), n = 100 (esperado: 5050).
 *   * Caso de error: n = -5 (esperado: IllegalArgumentException).
 * =========================================================================================
 */

/**
 * Clase que implementa la suma recursiva de los números naturales desde n hasta 1.
 * Ilustra la relación de recurrencia lineal y su validación mediante la serie aritmética de Gauss.
 */
public class Ej2 {

    /**
     * Calcula de forma recursiva la suma de los números enteros consecutivos desde n hasta 1.
     *
     * Fundamentación recursiva:
     * - Caso base: Para n == 0, la suma de cero elementos corresponde al elemento neutro de la adición (0).
     *   Detiene el apilamiento de llamadas en el call stack y retorna 0L directamente.
     * - Caso recursivo: n + sumarHastaUno(n - 1).
     * - Reducción del problema: El valor de n se decrementa en 1 en cada invocación,
     *   asegurando que cualquier n >= 0 converja de forma determinista hacia el caso base n == 0.
     *
     * @param n Límite superior del rango de enteros a sumar (n >= 0).
     * @return La sumatoria acumulada de 1 hasta n como entero largo de 64 bits (long).
     * @throws IllegalArgumentException Si n es negativo (impide convergencia hacia 1).
     */
    public static long sumarHastaUno(int n) {
        // Precondición: Si n es negativo, restar sucesivamente 1 alejaría el valor de 0 hacia -infinito
        if (n < 0) {
            throw new IllegalArgumentException("El número debe ser no negativo (n >= 0). Valor recibido: " + n);
        }

        // CASO BASE: Límite inferior alcanzado (elemento neutro de la suma)
        if (n == 0) {
            return 0L;
        }

        // CASO RECURSIVO: Suma el valor actual 'n' con la sumatoria del subconjunto restante (n - 1)
        return n + sumarHastaUno(n - 1);
    }

    /**
     * Calcula la suma de 1 a n mediante la fórmula matemática de Gauss (O(1)).
     * Utilizada como oráculo de verificación para contrastar la salida de la función recursiva.
     */
    public static long formulaGauss(int n) {
        if (n < 0) {
            return 0L;
        }
        return ((long) n * (n + 1)) / 2L;
    }

    /**
     * Batería de pruebas y validación formal contra la fórmula matemática cerrada.
     */
    public static void main(String[] args) {
        System.out.println("=== BATERÍA DE PRUEBAS: SUMA RECURSIVA DE n HASTA 1 ===");

        // Pruebas estándar verificadas contra la fórmula de Gauss
        int[] casos = {0, 1, 5, 10, 100};
        for (int n : casos) {
            long obtenidoRecursivo = sumarHastaUno(n);
            long esperadoGauss = formulaGauss(n);
            boolean coincide = (obtenidoRecursivo == esperadoGauss);

            System.out.printf("n = %3d -> Recursivo: %5d | Gauss: %5d | Estado: %s%n",
                    n, obtenidoRecursivo, esperadoGauss, coincide ? "CORRECTO" : "FALLÓ");
        }

        // Caso de verificación de error ante entradas negativas
        probarExcepcion(-5);
    }

    private static void probarExcepcion(int n) {
        try {
            sumarHastaUno(n);
            System.out.printf("n = %3d -> ERROR: Debería haber lanzado IllegalArgumentException.%n", n);
        } catch (IllegalArgumentException e) {
            System.out.printf("n = %3d -> CORRECTO: Excepción capturada con éxito (%s)%n", n, e.getMessage());
        }
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación realizada:
 *    Se fijó el caso base en 'n == 0' retornando 0L en lugar de cortar en 'n == 1'.
 * 
 * 2. Justificación técnica:
 *    Si bien el enunciado solicita la suma "desde n hasta 1", definir el caso base en n == 1
 *    obligaría a tratar a n == 0 como un caso especial separado en la validación inicial.
 *    Al establecer como caso base universal el elemento neutro de la suma (n == 0 -> 0L),
 *    la recursión unifica de forma matemáticamente elegante tanto la entrada inicial n = 0
 *    como la terminación natural de cualquier entrada positiva (n = 1 invoca suma(0) que da 0,
 *    produciendo 1 + 0 = 1).
 * =========================================================================================
 */