/**
 * Utilidad para el cómputo de frecuencias de elementos dentro de arreglos unidimensionales.
 */
public class Ejercicio5{

    /**
     * Cuenta la cantidad total de apariciones de un valor específico dentro de un arreglo.
     * Requiere un recorrido lineal exhaustivo O(n) al no asumir orden preestablecido en los elementos.
     *
     * @param arr Arreglo de enteros a examinar.
     * @param objetivo Valor entero cuya frecuencia se desea contabilizar.
     * @return Cantidad de coincidencias encontradas (>= 0). Retorna 0 si el arreglo es nulo o está vacío.
     */
    public static int contarOcurrencias(int[] arr, int objetivo) {
        // Validación de entrada: un arreglo nulo o sin posiciones no contiene ocurrencias
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int contador = 0;
        // Inspección completa de todas las celdas para asegurar la totalidad del recuento
        for (int elemento : arr) {
            if (elemento == objetivo) {
                contador++;
            }
        }
        return contador;
    }
}