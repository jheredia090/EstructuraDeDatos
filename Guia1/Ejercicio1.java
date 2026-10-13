public class Ejercicio1 {

    /**
     * Encuentra el valor mínimo de un vector de enteros.
     * Estrategia: tomar el primer elemento como mínimo inicial
     * y recorrer el arreglo comparando cada valor con el mínimo actual.
     *
     * No hace falta ordenar el vector porque el mínimo se puede determinar
     * con comparaciones directas. Ordenarlo sería más costoso y no es necesario.
     *
     * Complejidad temporal: O(n)
     * Complejidad espacial: O(1)
     */
    public static int encontrarMinimo(int[] vector) {
        int minimo = vector[0];

        for (int i = 1; i < vector.length; i++) {
            if (vector[i] < minimo) {
                minimo = vector[i];
            }
        }

        return minimo;
    }

    public static void main(String[] args) {
        int[] vector = {12, 4, 7, 1, 9, 15, 3};

        System.out.println("Vector: {12, 4, 7, 1, 9, 15, 3}");
        System.out.println("El valor mínimo es: " + encontrarMinimo(vector));
    }
}