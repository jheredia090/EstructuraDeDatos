/*
Prompt inicial utilizado (prompt_ej9.md):
Actúa como un desarrollador Java experto y proporciona la implementación de un método estático para encontrar el valor máximo dentro de una matriz de enteros:

Especificaciones técnicas:
- Firma requerida: public static int encontrarMaximo(int[][] matriz)
- Manejo estricto de casos límite: lanza IllegalArgumentException si la matriz es nula, está vacía (0 filas) o si ninguna de sus filas contiene elementos válidos.
- Inicialización rigurosa: inicializa el valor máximo candidato con el primer elemento real disponible de la estructura (no utilizar 0 ni Integer.MIN_VALUE para no corromper la lógica ante matrices con enteros negativos).
- Tolerancia a matrices irregulares (jagged arrays) y omisión segura de filas nulas o de longitud 0.

Justificación teórica del recorrido exhaustivo:
- Explica de forma concisa por qué en una estructura bidimensional no ordenada es teóricamente imposible aplicar una salida anticipada (early exit) y por qué cualquier elemento no inspeccionado podría ser estrictamente mayor al máximo provisional.

Análisis asintótico:
- Especifica la complejidad temporal en notación Big-O para:
  * Mejor caso
  * Caso promedio
  * Peor caso
  (Explica por qué los tres escenarios resultan idénticos).
- Especifica la complejidad espacial auxiliar en notación Big-O.
*/

import java.util.Arrays;

/**
 * Utilidad para la búsqueda y extracción de valores extremos en matrices bidimensionales.
 */
public class Ejercicio9 {

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

    public static void main(String[] args) {
        System.out.println("====================================================================");
        System.out.println("   DEMOSTRACIÓN: VALOR MÁXIMO EN MATRICES (EJERCICIO 9)             ");
        System.out.println("====================================================================\n");

        // 1. Matriz con valores mixtos
        int[][] matriz = {
            {3, 18, 5},
            {42, 9, 21},
            {7, 14, 2}
        };
        System.out.println("1. Matriz normal 3x3: " + Arrays.deepToString(matriz));
        System.out.println("   Valor máximo encontrado: " + encontrarMaximo(matriz) + "\n");

        // 2. Matriz con exclusivamente números negativos
        int[][] negativos = {
            {-45, -12, -80},
            {-5, -99, -17}
        };
        System.out.println("2. Matriz de números negativos: " + Arrays.deepToString(negativos));
        System.out.println("   Valor máximo encontrado: " + encontrarMaximo(negativos) + " (el menos negativo)\n");

        // 3. Matriz irregular con filas vacías o nulas
        int[][] irregular = {
            null,
            {},
            {-100, -2, -50}
        };
        System.out.println("3. Matriz irregular: " + Arrays.deepToString(irregular));
        System.out.println("   Valor máximo encontrado: " + encontrarMaximo(irregular) + "\n");

        // 4. Casos límite con excepciones
        System.out.println("4. Casos límite:");
        try {
            encontrarMaximo(new int[][]{{}, {}});
        } catch (IllegalArgumentException e) {
            System.out.println("   Excepción con matriz sin elementos válidos: " + e.getMessage());
        }

        try {
            encontrarMaximo(null);
        } catch (IllegalArgumentException e) {
            System.out.println("   Excepción con matriz nula: " + e.getMessage());
        }

        System.out.println("\n====================================================================");
    }
}