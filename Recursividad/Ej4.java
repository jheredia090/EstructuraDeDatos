/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Actúa como un desarrollador Java experto e implementa una función recursiva para calcular 
 * la potencia de un número sin utilizar Math.pow:
 * 
 * Fundamentación conceptual recursiva:
 * - Multiplicaciones sucesivas: Explica por qué b^e puede formularse recursivamente a partir 
 *   de la relación inductiva: potencia(b, e) = b * potencia(b, e - 1).
 * - Caso base (exponente 0): Justifica matemáticamente por qué cualquier número elevado a la 0 
 *   es igual a 1 (elemento neutro del producto), actuando como condición de corte definitiva 
 *   sin nuevas llamadas a la pila de ejecución.
 * - Reducción del exponente: Detalla cómo la transición de 'e' a 'e - 1' reduce progresivamente 
 *   la escala del problema hasta alcanzar el caso base e == 0.
 * - Soporte para exponentes negativos: Explica cómo resolver exponentes negativos (e < 0) 
 *   mediante la identidad matemática b^e = 1 / (b^-e), transformando el exponente en positivo 
 *   para asegurar la convergencia hacia 0.
 * 
 * Especificaciones técnicas:
 * - Firma requerida: public static double potencia(double base, int exponente)
 * - Tipo de retorno: 'double' para permitir resultados con precisión decimal ante exponentes negativos.
 * - Restricción estricta: Prohibido utilizar Math.pow o cualquier librería externa de potenciación.
 * - Validaciones: lanza ArithmeticException si base == 0 y exponente < 0 (indeterminación por división por cero).
 * 
 * Batería de pruebas requerida:
 * - Casos base: potencia(5, 0) -> 1.0; potencia(-3, 0) -> 1.0.
 * - Caso neutro: potencia(7, 1) -> 7.0.
 * - Casos positivos típicos: potencia(2, 3) -> 8.0; potencia(3, 4) -> 81.0.
 * - Base negativa con paridad: potencia(-2, 2) -> 4.0; potencia(-2, 3) -> -8.0.
 * - Exponente negativo: potencia(2, -2) -> 0.25; potencia(4, -1) -> 0.25.
 * - Caso de error: potencia(0, -2) -> ArithmeticException.
 * =========================================================================================
 */

/**
 * Implementación recursiva del cálculo de potenciación numérica sin Math.pow.
 * Demuestra la inducción sobre productos sucesivos y la resolución de exponentes negativos.
 */
public class Ej4 {

    /**
     * Calcula de forma recursiva la potencia de una base elevada a un exponente entero.
     *
     * Fundamentación recursiva:
     * - Caso base: Para exponente == 0, cualquier número no nulo elevado a la 0 es 1.0.
     *   Detiene el apilamiento de llamadas en el call stack y retorna 1.0 directamente.
     * - Manejo de exponente negativo: Aplica la identidad b^e = 1.0 / b^(-e). Al transformar
     *   el exponente a positivo, la resta recursiva posterior converge hacia 0 sin riesgo de bucle infinito.
     * - Caso recursivo: base * potencia(base, exponente - 1).
     * - Reducción del problema: El exponente disminuye en 1 unidad por invocación (e -> e - 1),
     *   asegurando que cualquier entero positivo alcance 0 en exactamente 'e' pasos.
     *
     * @param base Número real que actúa como factor repetido.
     * @param exponente Número entero que determina la cantidad de multiplicaciones o divisiones.
     * @return El resultado de elevar la base al exponente dado.
     * @throws ArithmeticException Si la base es 0 y el exponente es negativo (división por cero).
     */
    public static double potencia(double base, int exponente) {
        // Precondición: 0 elevado a un exponente negativo equivale a 1 / (0^|e|), una indeterminación por división por cero
        if (base == 0.0 && exponente < 0) {
            throw new ArithmeticException("División por cero: 0 no puede elevarse a un exponente negativo.");
        }

        // CASO BASE: Cualquier número elevado a la 0 es 1 (elemento neutro del producto)
        if (exponente == 0) {
            return 1.0;
        }

        // CASO DE EXPONENTE NEGATIVO: Se reescribe como 1 / b^(-e) para garantizar convergencia hacia 0
        if (exponente < 0) {
            return 1.0 / potencia(base, -exponente);
        }

        // CASO RECURSIVO: Multiplica la base y reduce el exponente en 1 unidad
        return base * potencia(base, exponente - 1);
    }

    /**
     * Batería de pruebas que evalúa casos base, signos, exponentes fraccionarios implícitos y excepciones.
     */
    public static void main(String[] args) {
        System.out.println("=== BATERÍA DE PRUEBAS: POTENCIA RECURSIVA SIN Math.pow ===");

        // Casos base (exponente 0)
        verificar(5.0, 0, 1.0);
        verificar(-3.0, 0, 1.0);

        // Caso neutro (exponente 1)
        verificar(7.0, 1, 7.0);

        // Casos positivos típicos
        verificar(2.0, 3, 8.0);
        verificar(3.0, 4, 81.0);

        // Base negativa con paridad par e impar
        verificar(-2.0, 2, 4.0);
        verificar(-2.0, 3, -8.0);

        // Exponentes negativos
        verificar(2.0, -2, 0.25);
        verificar(4.0, -1, 0.25);

        // Caso de error esperado (0 elevado a exponente negativo)
        probarExcepcion(0.0, -2);
    }

    private static void verificar(double base, int exponente, double esperado) {
        double obtenido = potencia(base, exponente);
        // Margen de tolerancia para comparaciones con coma flotante
        boolean correcto = Math.abs(obtenido - esperado) < 1e-9;
        System.out.printf("(%5.1f)^(%2d) -> Obtenido: %8.4f | Esperado: %8.4f | Estado: %s%n",
                base, exponente, obtenido, esperado, correcto ? "CORRECTO" : "FALLÓ");
    }

    private static void probarExcepcion(double base, int exponente) {
        try {
            potencia(base, exponente);
            System.out.printf("(%5.1f)^(%2d) -> ERROR: Debería haber lanzado ArithmeticException.%n", base, exponente);
        } catch (ArithmeticException e) {
            System.out.printf("(%5.1f)^(%2d) -> CORRECTO: Excepción capturada con éxito (%s)%n", base, exponente, e.getMessage());
        }
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación realizada:
 *    Se definió el tipo de retorno como 'double' y el parámetro base como 'double', además
 *    de incorporar explícitamente el caso 'exponente < 0' con la regla '1.0 / potencia(base, -exponente)'.
 * 
 * 2. Justificación técnica:
 *    Si la función se hubiera tipado únicamente con enteros ('long' o 'int'), cualquier
 *    exponente negativo (como 2^-2 = 0.25) habría sufrido truncamiento a 0 o generado un
 *    desbordamiento de pila por restar infinitamente sobre enteros negativos.
 *    La inclusión del caso de inversión aritmética permite que la función opere correctamente
 *    sobre todo el conjunto de los enteros Z en el exponente y números reales R en la base.
 * =========================================================================================
 */