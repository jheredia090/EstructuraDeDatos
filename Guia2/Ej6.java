/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Actúa como un desarrollador Java experto e implementa una función recursiva para contar 
 * la cantidad de dígitos de un número entero positivo:
 * 
 * Fundamentación conceptual recursiva:
 * - Reducción mediante división entera: Explica detalladamente cómo la operación aritmética 
 *   de división entera entre diez (n / 10) descarta el dígito menos significativo en base 10, 
 *   reduciendo el tamaño del número de forma monótona hacia el caso base.
 * - Definición y justificación del caso base: Justifica formalmente por qué la condición n < 10 
 *   representa el caso elemental inquebrantable, dado que en el sistema decimal los enteros en 
 *   el intervalo [0, 9] están formados por una única cifra, retornando 1 directamente sin generar 
 *   nuevas llamadas al call stack.
 * - Composición del resultado: Detalla cómo cada nivel recursivo acumula el dígito descartado 
 *   mediante la relación de recurrencia: contarDigitos(n) = 1 + contarDigitos(n / 10).
 * 
 * Especificaciones técnicas:
 * - Firma requerida: public static int contarDigitos(long n)
 * - Tipo de parámetro: Utiliza 'long' para admitir números de gran magnitud y evitar 
 *   desbordamientos rápidos.
 * - Tratamiento de precondiciones: Si n es negativo, aplica su valor absoluto o normalización 
 *   (Math.abs(n)) para que el signo no interfiera en la división ni en la condición de corte.
 * 
 * Batería de casos de prueba:
 * - Casos de un solo dígito (caso base): contarDigitos(0) -> 1; contarDigitos(8) -> 1.
 * - Caso frontera de dos dígitos: contarDigitos(10) -> 2.
 * - Casos típicos: contarDigitos(456) -> 3; contarDigitos(98765) -> 5.
 * - Caso de gran escala: contarDigitos(10000000000L) -> 11.
 * =========================================================================================
 */

/**
 * Implementación del conteo recursivo de dígitos de un número entero.
 * Ilustra la reducción logarítmica de escala mediante división entera sucesiva en base 10.
 */
public class Ej6 {

    /**
     * Cuenta recursivamente la cantidad de dígitos de un número entero en base decimal.
     *
     * Fundamentación recursiva:
     * - Caso base: Si n < 10, el número pertenece al intervalo [0, 9], por lo que consta
     *   de exactamente un dígito. Retorna 1 directamente deteniendo la recursión.
     * - Caso recursivo: 1 + contarDigitos(n / 10).
     * - Reducción del problema: La división entera por 10 (n / 10) trunca y descarta el dígito
     *   de menor peso (las unidades), reduciendo la longitud del número en 1 cifra por llamada
     *   y garantizando convergencia hacia el caso base en O(log10 n) pasos.
     *
     * @param n Número entero a evaluar.
     * @return Cantidad de dígitos decimales que componen el número.
     */
    public static int contarDigitos(long n) {
        // Normalización ante números negativos: el signo no altera la cantidad de dígitos
        if (n < 0) {
            // Manejo del caso especial Long.MIN_VALUE para evitar overflow al aplicar Math.abs
            if (n == Long.MIN_VALUE) {
                return 19; // Cantidad fija de dígitos de -9223372036854775808
            }
            n = -n;
        }

        // CASO BASE: Cualquier número no negativo menor que 10 tiene un único dígito
        if (n < 10) {
            return 1;
        }

        // CASO RECURSIVO: Cuenta el dígito actual (1) y evalúa el cociente restante (n / 10)
        return 1 + contarDigitos(n / 10);
    }

    /**
     * Batería de pruebas para verificar casos base, fronteras y magnitudes grandes.
     */
    public static void main(String[] args) {
        System.out.println("=== BATERÍA DE PRUEBAS: CONTEO RECURSIVO DE DÍGITOS ===");

        // Casos base (un solo dígito)
        verificar(0L, 1);
        verificar(8L, 1);

        // Caso frontera (menor número de dos dígitos)
        verificar(10L, 2);

        // Casos típicos
        verificar(456L, 3);
        verificar(98765L, 5);

        // Casos de gran escala (long de 64 bits)
        verificar(10000000000L, 11);
        verificar(Long.MAX_VALUE, 19); // 9223372036854775807 tiene 19 dígitos
    }

    private static void verificar(long n, int esperado) {
        int obtenido = contarDigitos(n);
        boolean correcto = (obtenido == esperado);
        System.out.printf("Número: %20d -> Obtenido: %2d | Esperado: %2d | Estado: %s%n",
                n, obtenido, esperado, correcto ? "CORRECTO" : "FALLÓ");
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación realizada:
 *    Se utilizó el tipo 'long' en lugar de 'int' y se incorporó el tratamiento seguro del
 *    caso extremo 'n == Long.MIN_VALUE'.
 * 
 * 2. Justificación técnica:
 *    En el estándar IEEE de enteros con complemento a dos, el valor mínimo absoluto
 *    (Long.MIN_VALUE = -9,223,372,036,854,775,808) no tiene contraparte positiva en su tipo,
 *    ya que Long.MAX_VALUE es 9,223,372,036,854,775,807. Si se ejecutara Math.abs(Long.MIN_VALUE)
 *    o '-n' directamente, el valor desbordaría de forma silenciosa resultando nuevamente en
 *    un número negativo, lo que provocaría un ciclo erróneo. Manejarlo explícitamente
 *    garantiza que la función nunca falle ante ninguna entrada del dominio de 64 bits.
 * =========================================================================================
 */