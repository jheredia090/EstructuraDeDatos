/**
 * Implementación instrumentada del algoritmo de ordenamiento por burbuja (Bubble Sort)
 * con preservación de inmutabilidad del arreglo original y medición de operaciones elementales.
 */
public class Ejercicio10 {

    /**
     * Contenedor inmutable que encapsula el resultado del ordenamiento y sus métricas de ejecución.
     *
     * @param arregloOrdenado Nueva instancia del arreglo con los elementos en orden ascendente.
     * @param comparaciones Cantidad total de evaluaciones de orden ejecutadas entre pares adyacentes.
     * @param intercambios Cantidad total de operaciones de intercambio (swaps) realizadas en memoria.
     */
    public record ResultadoBubbleSort(int[] arregloOrdenado, long comparaciones, long intercambios) {}

    /**
     * Ordena una copia del arreglo recibido aplicando Bubble Sort optimizado y contabiliza su costo operativo.
     *
     * @param arr Arreglo de enteros de entrada. Permanece inalterado tras la ejecución.
     * @return Instancia de {@link ResultadoBubbleSort} con el arreglo ordenado y las métricas computadas.
     */
    public static ResultadoBubbleSort ordenar(int[] arr) {
        // Manejo de caso borde: preserva la ausencia de referencia
        if (arr == null) {
            return new ResultadoBubbleSort(null, 0, 0);
        }

        // Clonación para asegurar inmutabilidad y evitar efectos colaterales sobre el arreglo de entrada
        int[] copia = arr.clone();
        int n = copia.length;

        // Arreglos con menos de dos elementos no requieren comparaciones ni intercambios
        if (n < 2) {
            return new ResultadoBubbleSort(copia, 0, 0);
        }

        long comparaciones = 0;
        long intercambios = 0;

        for (int i = 0; i < n - 1; i++) {
            boolean huboIntercambio = false;

            // El límite se reduce en 'i' porque los elementos mayores ya quedan en su posición final
            for (int j = 0; j < n - 1 - i; j++) {
                comparaciones++;
                if (copia[j] > copia[j + 1]) {
                    // Intercambio in-place de elementos adyacentes
                    int temporal = copia[j];
                    copia[j] = copia[j + 1];
                    copia[j + 1] = temporal;

                    intercambios++;
                    huboIntercambio = true;
                }
            }

            // Corte temprano (early exit): si una pasada completa no generó intercambios, el arreglo ya está ordenado
            if (!huboIntercambio) {
                break;
            }
        }

        return new ResultadoBubbleSort(copia, comparaciones, intercambios);
    }
}