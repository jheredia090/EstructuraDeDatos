/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Actúa como un desarrollador Java experto e implementa una función recursiva para calcular 
 * la multiplicación de dos enteros (a y b) utilizando únicamente sumas sucesivas (sin 
 * emplear el operador de multiplicación '*'):
 * 
 * Fundamentación conceptual recursiva:
 * - Representación como suma repetida: Explica formalmente cómo a * b equivale a sumar el 
 *   valor de 'a' un total de 'b' veces, deduciendo la relación de recurrencia: 
 *   multiplicar(a, b) = a + multiplicar(a, b - 1).
 * - Parámetro de reducción y convergencia: Justifica por qué el segundo parámetro ('b') 
 *   actúa como contador de iteraciones y se reduce restando 1 en cada llamada, mientras que 
 *   'a' permanece constante como sumando.
 * - Caso base (factor cero): Justifica por qué el caso base debe evaluarse cuando el 
 *   multiplicador b == 0, retornando 0 (elemento neutro de la adición) para detener la recursión.
 * - Tratamiento de signos negativos: Explica cómo garantizar la convergencia cuando 'b' es 
 *   negativo (evitando recursión infinita hacia -infinito) mediante la identidad algebraica: 
 *   a * (-b) = -(a * b).
 * 
 * Especificaciones técnicas:
 * - Firma requerida: public static long multiplicar(int a, int b)
 * - Tipo de retorno: 'long' para evitar desbordamientos aritméticos al acumular sumas sucesivas.
 * - Prohibición estricta: No utilizar el operador aritmético de multiplicación '*' en ningún 
 *   punto de la lógica.
 * 
 * Batería de pruebas requerida:
 * - Casos con cero (caso base): multiplicar(7, 0) -> 0; multiplicar(0, 5) -> 0.
 * - Casos neutros (multiplicación por 1): multiplicar(9, 1) -> 9.
 * - Casos positivos típicos: multiplicar(6, 4) -> 24.
 * - Casos con signos negativos:
 *   * Positivo por negativo: multiplicar(4, -3) -> -12.
 *   * Negativo por positivo: multiplicar(-4, 3) -> -12.
 *   * Negativo por negativo: multiplicar(-4, -3) -> 12.
 * =========================================================================================
 */

/**
 * Implementación de la multiplicación aritmética mediante sumas recursivas sucesivas.
 * Modela la operación a * b sin recurrir al operador de producto '*'.
 */
public class Ej3 {

    /**
     * Calcula de forma recursiva el producto de dos números enteros utilizando exclusivamente sumas.
     *
     * Fundamentación recursiva:
     * - Caso base: Si b == 0 (o a == 0), no quedan sumandos por acumular; retorna 0L directamente.
     * - Manejo de signo: Si b < 0, se aplica la propiedad algebraica a * b = - (a * (-b)).
     *   Esto invierte el signo de b haciéndolo positivo para garantizar que al restar 1 converja hacia 0.
     * - Caso recursivo: a + multiplicar(a, b - 1).
     * - Reducción del problema: El parámetro b se reduce en 1 unidad en cada invocación,
     *   mientras que el parámetro a se conserva inalterado como sumando.
     *
     * @param a Primer factor (sumando base).
     * @param b Segundo factor (contador de repeticiones).
     * @return El producto a * b representado como un entero largo de 64 bits (long).
     */
    public static long multiplicar(int a, int b) {
        // Optimización del caso base: si cualquiera de los factores es 0, el producto es 0
        if (b == 0 || a == 0) {
            return 0L;
        }

        // CASO DE SIGNO NEGATIVO: Asegura que el contador de recursión decrezca hacia 0
        // Aplica a * b = -(a * (-b)) sin utilizar el operador '*'
        if (b < 0) {
            return -multiplicar(a, -b);
        }

        // CASO RECURSIVO: Suma 'a' y reduce el multiplicador 'b' en 1
        return a + multiplicar(a, b - 1);
    }

    /**
     * Batería de pruebas para verificar casos borde, neutros y combinaciones de signos.
     */
    public static void main(String[] args) {
        System.out.println("=== BATERÍA DE PRUEBAS: MULTIPLICACIÓN RECURSIVA SIN '*' ===");

        // Pruebas con factor cero
        verificar(7, 0, 0L);
        verificar(0, 5, 0L);

        // Pruebas con elemento neutro (1)
        verificar(9, 1, 9L);
        verificar(1, 9, 9L);

        // Pruebas con factores positivos
        verificar(6, 4, 24L);

        // Pruebas con signos negativos
        verificar(4, -3, -12L);
        verificar(-4, 3, -12L);
        verificar(-4, -3, 12L);
    }

    private static void verificar(int a, int b, long esperado) {
        long obtenido = multiplicar(a, b);
        boolean correcto = (obtenido == esperado);
        System.out.printf("%3d * %3d -> Obtenido: %4d | Esperado: %4d | Estado: %s%n",
                a, b, obtenido, esperado, correcto ? "CORRECTO" : "FALLÓ");
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación realizada:
 *    Se agregó el tratamiento condicional 'if (b < 0) return -multiplicar(a, -b);'.
 * 
 * 2. Justificación técnica:
 *    Si el factor 'b' es negativo (ej. b = -3), aplicar directamente la reducción recursiva
 *    (b - 1) provocaría que b tome los valores -4, -5, -6, alejándose indefinidamente de 0 y
 *    produciendo un desbordamiento de pila (StackOverflowError).
 *    Al invertir el signo mediante la negación unaria '-b' y negar el resultado devuelto,
 *    se transforma el problema en una cuenta regresiva sobre enteros positivos, garantizando
 *    la convergencia matemática en O(|b|) sin usar el operador de multiplicación '*'.
 * =========================================================================================
 */