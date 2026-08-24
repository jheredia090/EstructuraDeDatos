/**
 * Ejercicio 1: Encontrar el valor mínimo de un vector de enteros.
 *
 * Estrategia: búsqueda lineal con un solo recorrido.
 * Complejidad temporal: O(n)
 * Complejidad espacial: O(1)
 */
public class Ejercicio1 {

    /**
     * Recorre el vector una sola vez y devuelve el valor mínimo.
     *
     * @param vector arreglo de enteros (al menos un elemento)
     * @return el menor valor del vector
     */
    public static int encontrarMinimo(int[] vector) {
        // Asumimos que el primer elemento es el mínimo
        int minimo = vector[0];

        // Recorremos desde el segundo elemento hasta el final
        for (int i = 1; i < vector.length; i++) {
            // Si encontramos un valor menor, actualizamos el mínimo
            if (vector[i] < minimo) {
                minimo = vector[i];
            }
        }

        return minimo;
    }

    public static void main(String[] args) {
        // Caso 1: valores negativos
        int[] vector1 = {-5, -3, -8, -1, -10};
        System.out.println("Vector 1: {-5, -3, -8, -1, -10}");
        System.out.println("Mínimo: " + encontrarMinimo(vector1));
        // Resultado esperado: -10

        // Caso 2: valores positivos
        int[] vector2 = {14, 7, 23, 2, 42, 9};
        System.out.println("Vector 2: {14, 7, 23, 2, 42, 9}");
        System.out.println("Mínimo: " + encontrarMinimo(vector2));
        // Resultado esperado: 2

        // Caso 3: un solo elemento
        int[] vector3 = {42};
        System.out.println("Vector 3: {42}");
        System.out.println("Mínimo: " + encontrarMinimo(vector3));
        // Resultado esperado: 42
    }
}