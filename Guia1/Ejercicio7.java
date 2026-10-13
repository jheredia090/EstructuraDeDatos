/**
 * Proveedor de algoritmos para la inversión de arreglos unidimensionales
 * mediante enfoques no destructivo (inmutable) y destructivo (in-place).
 */
public class Ejercicio7 {

    /**
     * Genera una nueva instancia con los elementos en orden invertido,
     * garantizando que el arreglo original permanezca inmutable.
     *
     * @param arr Arreglo de enteros a invertir. Puede ser nulo o vacío.
     * @return Nueva instancia con el orden revertido, o {@code null} si el arreglo recibido es nulo.
     */
    public static int[] invertirConCopia(int[] arr) {
        // Manejo de caso borde: preserva la ausencia de referencia
        if (arr == null) {
            return null;
        }

        int n = arr.length;
        int[] resultado = new int[n];
        // Asignación simétrica complementaria: cada posición i se asigna a n - 1 - i en una única pasada
        for (int i = 0; i < n; i++) {
            resultado[i] = arr[n - 1 - i];
        }
        return resultado;
    }

    /**
     * Invierte los elementos directamente sobre el arreglo original mediante intercambio simétrico.
     * Modifica el estado de la referencia recibida utilizando memoria auxiliar constante O(1).
     *
     * @param arr Arreglo de enteros a modificar. No realiza operaciones si es nulo o de longitud menor a 2.
     */
    public static void invertirInPlace(int[] arr) {
        // Un arreglo nulo, vacío o con un único elemento ya se encuentra en su disposición final
        if (arr == null || arr.length < 2) {
            return;
        }

        int izquierda = 0;
        int derecha = arr.length - 1;

        // Convergencia de dos punteros: intercambia pares simétricos de los extremos hacia el centro
        while (izquierda < derecha) {
            int temporal = arr[izquierda];
            arr[izquierda] = arr[derecha];
            arr[derecha] = temporal;
            izquierda++;
            derecha--;
        }
    }
}