/**
 * Utilidad para la búsqueda y extracción de valores extremos en matrices bidimensionales.
 */
public class Ejercicio9{

    /**
     * Encuentra el valor máximo dentro de una matriz de enteros desordenada.
     * Requiere un recorrido exhaustivo O(N * M) al no existir precondiciones de orden.
     *
     * @param matriz Matriz bidimensional de enteros a evaluar. Puede ser irregular (jagged array).
     * @return El valor entero más alto encontrado en la matriz.
     * @throws IllegalArgumentException Si la matriz es nula, no tiene filas o carece de elementos válidos.
     */
    public static int encontrarMaximo(int[][] matriz) {
        // Validación de estructura: descarta referencias nulas o matrices sin filas asignadas
        if (matriz == null || matriz.length == 0) {
            throw new IllegalArgumentException("La matriz no puede ser nula ni vacía.");
        }

        int maximo = 0;
        boolean inicializado = false;

        // Recorrido por filas (row-major order)
        for (int i = 0; i < matriz.length; i++) {
            int[] fila = matriz[i];
            // Tolerancia a matrices irregulares: omite filas nulas intermedias sin interrumpir el flujo
            if (fila == null) {
                continue;
            }

            for (int j = 0; j < fila.length; j++) {
                int valorActual = fila[j];
                // Asigna el primer elemento real disponible para evitar valores centinela arbitrarios
                if (!inicializado) {
                    maximo = valorActual;
                    inicializado = true;
                } else if (valorActual > maximo) {
                    maximo = valorActual;
                }
            }
        }

        // Validación de contenido: previene el retorno de valores ficticios si la matriz solo contenía filas vacías
        if (!inicializado) {
            throw new IllegalArgumentException("La matriz no contiene elementos válidos para determinar un máximo.");
        }

        return maximo;
    }
}