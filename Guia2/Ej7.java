/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Actúa como un desarrollador Java experto e implementa una función recursiva para calcular 
 * la suma de los dígitos de un número entero positivo:
 * 
 * Fundamentación conceptual recursiva:
 * - Extracción del último dígito (% 10): Explica detalladamente cómo la operación aritmética 
 *   n % 10 aísla el dígito de las unidades (el residuo en base decimal), que representa el 
 *   valor individual a sumar en el nivel actual.
 * - Reducción del problema (/ 10): Explica cómo la división entera n / 10 elimina el dígito 
 *   de las unidades ya procesado, reduciendo el tamaño del número y asegurando que converja 
 *   de forma monótona hacia el caso base.
 * - Caso base (un solo dígito): Justifica por qué cuando n < 10 el número consta de una única 
 *   cifra, por lo que la suma de sus dígitos es el propio valor de n, retornando dicho valor 
 *   directamente sin emitir nuevas llamadas al call stack.
 * - Combinación de resultados: Detalla cómo se compone la solución final mediante la relación 
 *   de recurrencia: sumarDigitos(n) = (n % 10) + sumarDigitos(n / 10).
 * 
 * Especificaciones técnicas:
 * - Firma requerida: public static int sumarDigitos(long n)
 * - Tipo de parámetro: Utiliza 'long' para admitir números de gran escala.
 * - Normalización de entrada: Si n es negativo, aplica su valor absoluto para operar sobre 
 *   magnitudes positivas sin alterar los valores absolutos de los dígitos.
 * 
 * Batería de casos de prueba:
 * - Casos base de una sola cifra: sumarDigitos(0) -> 0; sumarDigitos(7) -> 7.
 * - Casos frontera: sumarDigitos(10) -> 1; sumarDigitos(19) -> 10.
 * - Casos típicos: sumarDigitos(1234) -> 10; sumarDigitos(999) -> 27.
 * - Caso de escala alta: sumarDigitos(987654321L) -> 45.
 * =========================================================================================
 */

/**
 * Implementación de la suma recursiva de los dígitos de un número entero.
 * Ejemplifica la descomposición posicional decimal utilizando las operaciones módulo (%) y cociente (/).
 */
public class Ej7 {

    /**
     * Calcula de forma recursiva la suma de los dígitos decimales de un número entero.
     *
     * Fundamentación recursiva:
     * - Extracción del último dígito: (n % 10) aísla el dígito de menor peso (las unidades).
     * - Reducción del problema: (n / 10) trunca y descarta el dígito de las unidades ya considerado.
     * - Caso base: Para n < 10, el número posee un solo dígito decimal en el rango [0, 9],
     *   por lo que la suma es el propio número. Retorna (int) n directamente sin apilar más llamadas.
     * - Combinación inductiva: (int) (n % 10) + sumarDigitos(n / 10).
     *
     * @param n Número entero a procesar.
     * @return Suma de todos sus dígitos decimales individuales.
     */
    public static int sumarDigitos(long n) {
        // Normalización ante números negativos: la suma de dígitos evalúa magnitudes absolutas
        if (n < 0) {
            // Protección contra overflow ante el límite extremo de 64 bits (Long.MIN_VALUE)
            if (n == Long.MIN_VALUE) {
                // Dígitos de -9223372036854775808 -> 9+2+2+3+3+7+2+0+3+6+8+5+4+7+7+5+8+0+8 = 89
                return 89;
            }
            n = -n;
        }

        // CASO BASE: Si el número tiene una sola cifra, la suma es el propio número
        if (n < 10) {
            return (int) n;
        }

        // CASO RECURSIVO: Suma el último dígito extraído con la suma recursiva del resto de las cifras
        return (int) (n % 10) + sumarDigitos(n / 10);
    }

    /**
     * Batería de pruebas para verificar casos base, bordes y números con múltiples cifras.
     */
    public static void main(String[] args) {
        System.out.println("=== BATERÍA DE PRUEBAS: SUMA RECURSIVA DE DÍGITOS ===");

        // Casos base de una sola cifra
        verificar(0L, 0);
        verificar(7L, 7);

        // Casos frontera de dos cifras
        verificar(10L, 1);
        verificar(19L, 10);

        // Casos típicos
        verificar(1234L, 10); // 1 + 2 + 3 + 4 = 10
        verificar(999L, 27);  // 9 + 9 + 9 = 27

        // Caso de escala alta
        verificar(987654321L, 45); // 9+8+7+6+5+4+3+2+1 = 45

        // Caso con número negativo
        verificar(-543L, 12); // |-543| -> 5 + 4 + 3 = 12
    }

    private static void verificar(long n, int esperado) {
        int obtenido = sumarDigitos(n);
        boolean correcto = (obtenido == esperado);
        System.out.printf("Número: %15d -> Obtenido: %3d | Esperado: %3d | Estado: %s%n",
                n, obtenido, esperado, correcto ? "CORRECTO" : "FALLÓ");
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación realizada:
 *    Se incorporó un tratamiento especial defensivo para 'n == Long.MIN_VALUE'.
 * 
 * 2. Justificación técnica:
 *    Al recibir Long.MIN_VALUE (-9,223,372,036,854,775,808), la operación unaria '-n' no puede
 *    representar +9,223,372,036,854,775,808 porque desborda el rango máximo de un entero de
 *    64 bits con signo (Long.MAX_VALUE = 9,223,372,036,854,775,807).
 *    Mapear este valor límite de forma determinista previene comportamientos erráticos o
 *    llamadas recursivas con valores negativos, asegurando total robustez en producción.
 * =========================================================================================
 */