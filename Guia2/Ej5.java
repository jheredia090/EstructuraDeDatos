/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Actúa como un desarrollador Java experto e implementa una función recursiva de tipo void 
 * para imprimir una cuenta regresiva desde n hasta 0:
 * 
 * Fundamentación conceptual recursiva:
 * - Función con efectos colaterales (tipo void): Explica por qué una función recursiva no 
 *   siempre debe devolver un valor calculado y cómo en este caso su propósito principal es el 
 *   efecto secundario de impresión en la salida estándar, controlando la pila de llamadas 
 *   mediante una sentencia 'return;' vacía o por bifurcación condicional.
 * - Definición del caso base: Detalla cuál es la condición de parada (n == 0 o n < 0) y qué 
 *   acción final debe realizar antes de retornar el control al invocador sin generar nuevas 
 *   llamadas al call stack.
 * - Reducción y convergencia: Justifica cómo el paso n -> n - 1 garantiza que cualquier 
 *   entero no negativo converja de forma monótona y finita hacia 0.
 * - Análisis de fallo por divergencia: Explica formalmente qué ocurriría si por error se 
 *   utilizara n + 1 en lugar de n - 1, describiendo por qué se aleja del caso base y cómo 
 *   desemboca en un java.lang.StackOverflowError.
 * 
 * Especificaciones técnicas:
 * - Firma requerida: public static void cuentaRegresiva(int n)
 * - Orden de emisión: Imprime primero el valor actual de n y luego invoca recursivamente a 
 *   (n - 1), de modo que el flujo sea descendente (n, n-1, ..., 0).
 * - Manejo de precondiciones: Si n es negativo (n < 0), lanza IllegalArgumentException para 
 *   evitar llamadas inválidas hacia -infinito.
 * 
 * Estrategia de pruebas:
 * - Prueba estándar con n = 5 (verificando la secuencia: 5, 4, 3, 2, 1, 0).
 * - Caso base puro con n = 0 (verificando que imprima únicamente 0 y termine de inmediato).
 * - Caso de error con n = -1 (verificando el rechazo de la precondición).
 * =========================================================================================
 */

/**
 * Implementación de un procedimiento recursivo sin retorno de valor (void).
 * Ejemplifica la ejecución de efectos secundarios controlados mediante la pila de llamadas.
 */
public class Ej5 {

    /**
     * Imprime en la consola los números enteros desde n hasta 0 en orden descendente.
     *
     * Fundamentación recursiva:
     * - Tipo void y efectos secundarios: La función no retorna un valor computado; su propósito
     *   es el efecto colateral de imprimir en consola. La finalización de la recursión se efectúa
     *   retornando el control al invocador sin encolar nuevas llamadas al call stack.
     * - Caso base: Cuando n == 0, imprime el valor 0 y finaliza inmediatamente (return;).
     * - Caso recursivo: Imprime n y luego invoca a cuentaRegresiva(n - 1).
     * - Reducción del problema: El valor decrece monótonamente en una unidad (n -> n - 1),
     *   garantizando la convergencia hacia 0 en exactamente n pasos.
     *
     * @param n Valor inicial de la cuenta regresiva (debe ser n >= 0).
     * @throws IllegalArgumentException Si n es negativo (imposibilita converger hacia 0 restando 1).
     */
    public static void cuentaRegresiva(int n) {
        // Precondición: Se rechazan valores negativos para evitar llamadas indefinidas hacia -infinito
        if (n < 0) {
            throw new IllegalArgumentException("El valor inicial debe ser no negativo (n >= 0). Recibido: " + n);
        }

        // CASO BASE: Límite final del conteo alcanzado
        if (n == 0) {
            System.out.println(0);
            return; // Detiene la recursión y desapila la llamada
        }

        // CASO RECURSIVO: Acción inmediata previa a la llamada (orden descendente)
        System.out.print(n + " ");
        cuentaRegresiva(n - 1);
    }

    /**
     * Batería de pruebas para verificar el flujo de impresión en consola y el manejo de excepciones.
     */
    public static void main(String[] args) {
        System.out.println("=== BATERÍA DE PRUEBAS: CONTEO REGRESIVO (n HASTA 0) ===");

        // Prueba estándar: n = 5
        System.out.print("Prueba n = 5 -> Esperado: 5 4 3 2 1 0 | Obtenido: ");
        cuentaRegresiva(5);

        // Caso base puro: n = 0
        System.out.print("Prueba n = 0 -> Esperado: 0           | Obtenido: ");
        cuentaRegresiva(0);

        // Caso de error esperado: n = -1
        probarExcepcion(-1);
    }

    private static void probarExcepcion(int n) {
        try {
            System.out.printf("Prueba n = %2d -> ", n);
            cuentaRegresiva(n);
            System.out.println("ERROR: Debería haber lanzado IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            System.out.println("CORRECTO: Excepción capturada con éxito (" + e.getMessage() + ")");
        }
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación realizada:
 *    Se incluyó la validación de precondición 'if (n < 0) throw new IllegalArgumentException(...);'
 *    en la entrada pública y se analizó formalmente el riesgo de utilizar 'n + 1'.
 * 
 * 2. Justificación técnica:
 *    - Si se utilizara 'n + 1' en lugar de 'n - 1', el parámetro crecería indefinidamente
 *      (ej. 5 -> 6 -> 7 -> ...), divergiendo del caso base n == 0. Como la pila de ejecución
 *      (Call Stack) de la máquina virtual de Java (JVM) tiene un tamaño fijo de memoria,
 *      este crecimiento ininterrumpido agotaría la memoria asignada a los marcos de pila,
 *      disparando inevitablemente un error crítico no recuperable: java.lang.StackOverflowError.
 *    - La validación previa asegura que la condición de decremento siempre sea convergente
 *      hacia el caso base desde cualquier número válido n >= 0.
 * =========================================================================================
 */