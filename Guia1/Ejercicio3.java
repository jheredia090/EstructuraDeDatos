/**
 * Ejercicio 3 - Guía 1: Búsqueda Binaria en Vector Ordenado
 *
 * JUSTIFICACIÓN:
 * El vector está ordenado ascendentemente. La búsqueda binaria aprovecha esa
 * propiedad: en cada iteración compara el elemento del medio (mid) con el
 * buscado y descarta la mitad del intervalo que no puede contenerlo.
 * La búsqueda lineal (O(n)) desperdiciaría esa información recorriendo todo.
 *
 * ANÁLISIS DE COMPLEJIDAD:
 * - Mejor caso:  el elemento está en mid en la primera iteración → O(1).
 * - Peor caso:   el elemento no existe o está en un extremo → O(log n).
 * - Caso promedio: → O(log n).
 */
public class Ejercicio3 {

    /**
     * Búsqueda binaria iterativa con seguimiento paso a paso.
     *
     * @param vector   arreglo de enteros ORDENADO ascendentemente
     * @param elemento valor a buscar
     * @return posición del elemento, o -1 si no existe
     */
    public static int busquedaBinaria(int[] vector, int elemento) {
        int low = 0;
        int high = vector.length - 1;
        int iteracion = 0;

        while (low <= high) {
            int mid = low + (high - low) / 2; // evita desbordamiento
            iteracion++;

            System.out.println("Iteración " + iteracion
                    + " → low=" + low + ", high=" + high
                    + ", mid=" + mid + ", valor=" + vector[mid]);

            if (vector[mid] == elemento) {
                System.out.println("  → " + vector[mid] + " == " + elemento
                        + " → ENCONTRADO en posición " + mid);
                return mid;
            } else if (vector[mid] < elemento) {
                System.out.println("  → " + vector[mid] + " < " + elemento
                        + " → descarto mitad izquierda, low = " + (mid + 1));
                low = mid + 1;
            } else {
                System.out.println("  → " + vector[mid] + " > " + elemento
                        + " → descarto mitad derecha, high = " + (mid - 1));
                high = mid - 1;
            }
        }

        System.out.println("  → No encontrado (low > high)");
        return -1;
    }

    public static void main(String[] args) {
        int[] vector = {2, 5, 8, 12, 16, 23, 38, 56, 72, 91};

        System.out.println("=== Caso 1: encontrado a la izquierda (5) ===");
        busquedaBinaria(vector, 5);

        System.out.println("\n=== Caso 2: encontrado a la derecha (91) ===");
        busquedaBinaria(vector, 91);

        System.out.println("\n=== Caso 3: no encontrado (50) ===");
        busquedaBinaria(vector, 50);
    }
}