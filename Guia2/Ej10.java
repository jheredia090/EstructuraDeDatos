/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Quiero implementar en Java una función recursiva que resuelva la búsqueda lineal de un 
 * número dentro de un arreglo de enteros, sin utilizar ciclos (for, while o do-while).
 * 
 * Este problema puede resolverse recursivamente porque la inspección secuencial de una colección 
 * puede modelarse delegando el avance de posiciones a la pila de llamadas: en cada paso se evalúa 
 * el elemento en la posición actual y, si no coincide, el subproblema consiste en buscar en el 
 * resto del arreglo a partir del siguiente índice.
 * 
 * El caso base es doble:
 * 1. Caso base de éxito: si arr[indice] == objetivo, porque se encontró el elemento buscado, 
 *    deteniendo la recursión y retornando el índice actual.
 * 2. Caso base de fallo: si indice >= arr.length (o si el arreglo es nulo), porque se recorrió 
 *    todo el arreglo sin encontrar el valor, finalizando la recursión y retornando -1 para indicar 
 *    que el elemento no existe.
 * 
 * El caso recursivo es buscar(arr, objetivo, indice + 1), porque en cada llamada el problema se 
 * reduce incrementando el índice en 1 unidad, examinando la siguiente posición contigua y 
 * convergiendo de forma monótona hacia el final del arreglo (indice == arr.length).
 * 
 * La función debe devolver un valor int que represente la posición (índice base 0) de la primera 
 * aparición del número en el arreglo, o -1 si el número no está presente.
 * 
 * Quiero que el código tenga comentarios explicando el caso base, el caso recursivo y la pila de 
 * llamadas, e incluya un método envoltorio (wrapper) público que inicie la búsqueda desde el índice 0 
 * para ocultar el parámetro de control al usuario.
 * 
 * También quiero probarlo con estos casos:
 * - Caso 1: Elemento presente al inicio del arreglo (índice 0).
 * - Caso 2: Elemento presente en una posición intermedia.
 * - Caso 3: Elemento presente en la última posición (arr.length - 1).
 * - Caso 4: Elemento inexistente en el arreglo (retorno esperado: -1).
 * - Caso 5: Arreglo vacío o nulo (retorno esperado: -1).
 * =========================================================================================
 */

/**
 * Implementación de la búsqueda lineal recursiva sobre arreglos sin estructuras de control iterativas.
 * Ilustra la técnica de puntero de índice virtual asistido por la pila de llamadas (call stack).
 */
public class Ej10 {

    /**
     * Método público (wrapper) que expone una interfaz limpia al usuario.
     * Inicia la búsqueda recursiva a partir del índice inicial 0.
     *
     * @param arr Arreglo de enteros sobre el cual realizar la búsqueda.
     * @param objetivo Valor numérico a localizar.
     * @return Índice de la primera coincidencia (>= 0), o -1 si no existe o el arreglo es inválido.
     */
    public static int buscar(int[] arr, int objetivo) {
        // Guarda previa: un arreglo nulo o sin posiciones no puede contener el objetivo
        if (arr == null || arr.length == 0) {
            return -1;
        }
        // Inicia el rastreo recursivo en el extremo izquierdo (posición 0)
        return buscarRecursivo(arr, objetivo, 0);
    }

    /**
     * Función recursiva auxiliar que inspecciona el arreglo celda por celda.
     *
     * Fundamentación recursiva y pila de llamadas:
     * - CASO BASE DE FALLO (Límite alcanzado): Si indice >= arr.length, se ha recorrido la
     *   totalidad del arreglo sin éxito. Detiene el apilamiento de llamadas y retorna -1.
     * - CASO BASE DE ÉXITO (Coincidencia encontrada): Si arr[indice] == objetivo, se halló
     *   la primera aparición. Detiene la recursión con salida temprana y retorna 'indice'.
     * - CASO RECURSIVO: Si no coincide, invoca a buscarRecursivo(arr, objetivo, indice + 1).
     * - Reducción del problema: El puntero 'indice' se incrementa estrictamente de a 1,
     *   desplazando la ventana de búsqueda hacia la derecha y convergiendo hacia arr.length.
     *
     * @param arr Arreglo de enteros a examinar.
     * @param objetivo Valor buscado.
     * @param indice Posición actual a evaluar en el nivel de pila vigente.
     * @return Índice de coincidencia o -1 si no se encuentra.
     */
    private static int buscarRecursivo(int[] arr, int objetivo, int indice) {
        // CASO BASE 1 (Fallo): El índice superó el tamaño del arreglo sin hallar coincidencias
        if (indice >= arr.length) {
            return -1;
        }

        // CASO BASE 2 (Éxito): El elemento actual coincide con el objetivo buscado
        if (arr[indice] == objetivo) {
            return indice; // Retorna la posición de coincidencia de forma inmediata
        }

        // CASO RECURSIVO: Avanza a la siguiente posición en la pila de llamadas
        return buscarRecursivo(arr, objetivo, indice + 1);
    }

    /**
     * Batería de pruebas que verifica todas las variantes de ubicación y casos límite.
     */
    public static void main(String[] args) {
        System.out.println("=== BATERÍA DE PRUEBAS: BÚSQUEDA LINEAL RECURSIVA ===");

        int[] datos = {15, 42, 8, 99, 23, 7};

        // Caso 1: Elemento presente al inicio (índice 0)
        verificar(datos, 15, 0);

        // Caso 2: Elemento en posición intermedia
        verificar(datos, 99, 3);
        verificar(datos, 8, 2);

        // Caso 3: Elemento en la última posición (arr.length - 1)
        verificar(datos, 7, 5);

        // Caso 4: Elemento inexistente en el arreglo
        verificar(datos, 100, -1);
        verificar(datos, 0, -1);

        // Caso 5: Arreglos vacíos y nulos
        verificar(new int[]{}, 10, -1);
        verificar(null, 10, -1);
    }

    private static void verificar(int[] arr, int objetivo, int esperado) {
        int obtenido = buscar(arr, objetivo);
        boolean correcto = (obtenido == esperado);

        String arrayVisual;
        if (arr == null) {
            arrayVisual = "null";
        } else if (arr.length == 0) {
            arrayVisual = "[]";
        } else {
            arrayVisual = java.util.Arrays.toString(arr);
        }

        System.out.printf("Arreglo: %-22s | Buscado: %3d -> Obtenido: %2d | Esperado: %2d | Estado: %s%n",
                arrayVisual, objetivo, obtenido, esperado, correcto ? "CORRECTO" : "FALLÓ");
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación realizada:
 *    Se adoptó el patrón de diseño Wrapper (método público 'buscar' de 2 parámetros que delega
 *    en un método privado 'buscarRecursivo' de 3 parámetros con índice inicial 0).
 * 
 * 2. Justificación técnica:
 *    Exponer al cliente un parámetro 'int indice' rompería el principio de encapsulamiento,
 *    ya que un usuario externo podría suministrar un índice arbitrario distinto de 0 o un
 *    valor fuera de rango. El método wrapper garantiza que la precondición inicial (comenzar
 *    en el índice 0) sea siempre respetada, ofreciendo una API limpia y segura idéntica a
 *    las funciones estándar de búsqueda.
 * =========================================================================================
 */