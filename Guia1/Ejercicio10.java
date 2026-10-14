/*
Prompt inicial utilizado (prompt_ej10.md):
Actúa como un desarrollador Java experto y proporciona la implementación del algoritmo Bubble Sort (ordenamiento por burbuja) en Java, trabajando sobre una copia y midiendo su costo computacional:

Especificaciones técnicas:
- Enfoque no destructivo: no modifica el arreglo original; clona o instancia una copia del arreglo recibido para realizar el ordenamiento sobre ella.
- Encapsulación de salida: define un record inmutable para entregar el arreglo ordenado junto con sus métricas:
  public record ResultadoBubbleSort(int[] arregloOrdenado, long comparaciones, long intercambios) {}
- Optimización algorítmica:
  * Reduce el límite superior del ciclo interno en cada iteración (n - 1 - i).
  * Incluye una bandera de corte temprano (early exit) que termine el proceso si una pasada completa transcurre sin intercambios.
- Manejo de casos límite: si el arreglo es nulo, retorna null en el record; si tiene longitud 0 o 1, retorna una copia equivalente con 0 comparaciones y 0 intercambios.

Justificación teórica obligatoria:
- Explica de forma concisa por qué Bubble Sort es estrictamente didáctico y desaconsejado en sistemas reales frente a conjuntos medianos o grandes:
  * Su complejidad temporal O(N^2) frente al estándar O(N log N) de algoritmos modernos (Dual-Pivot Quicksort, TimSort).
  * La sobrecarga excesiva de escrituras en memoria (swap overhead), donde incluso dentro de los algoritmos O(N^2), Insertion Sort y Selection Sort realizan significativamente menos asignaciones.

Análisis asintótico de operaciones:
- Presenta una tabla con la complejidad temporal en notación Big-O detallando el conteo de comparaciones e intercambios para:
  * Mejor caso (arreglo ya ordenado).
  * Peor caso (arreglo invertido).
  * Caso promedio.
- Especifica la complejidad espacial auxiliar en notación Big-O (contrastando la memoria del algoritmo vs. la memoria de la copia retornada).
*/

import java.util.Arrays;

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

    public static void main(String[] args) {
        System.out.println("====================================================================");
        System.out.println("   DEMOSTRACIÓN: BUBBLE SORT INSTRUMENTADO (EJERCICIO 10)           ");
        System.out.println("====================================================================\n");

        // 1. Caso desordenado estándar
        int[] desordenado = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("1. Arreglo desordenado original: " + Arrays.toString(desordenado));
        ResultadoBubbleSort res1 = ordenar(desordenado);
        System.out.println("   Arreglo ordenado retornado  : " + Arrays.toString(res1.arregloOrdenado()));
        System.out.println("   Original intacto            : " + Arrays.toString(desordenado));
        System.out.println("   Comparaciones               : " + res1.comparaciones());
        System.out.println("   Intercambios (swaps)        : " + res1.intercambios() + "\n");

        // 2. Mejor caso: arreglo ya ordenado (early exit)
        int[] yaOrdenado = {1, 2, 3, 4, 5, 6};
        System.out.println("2. Arreglo ya ordenado (Mejor caso con early exit): " + Arrays.toString(yaOrdenado));
        ResultadoBubbleSort res2 = ordenar(yaOrdenado);
        System.out.println("   Comparaciones realizadas    : " + res2.comparaciones() + " (apenas n-1 comparaciones)");
        System.out.println("   Intercambios realizados     : " + res2.intercambios() + " (cero swaps)\n");

        // 3. Peor caso: arreglo invertido
        int[] invertido = {6, 5, 4, 3, 2, 1};
        System.out.println("3. Arreglo invertido (Peor caso O(n^2)): " + Arrays.toString(invertido));
        ResultadoBubbleSort res3 = ordenar(invertido);
        System.out.println("   Comparaciones realizadas    : " + res3.comparaciones());
        System.out.println("   Intercambios realizados     : " + res3.intercambios() + "\n");

        // 4. Casos límite
        System.out.println("4. Casos límite:");
        System.out.println("   Arreglo unitario {99} : " + Arrays.toString(ordenar(new int[]{99}).arregloOrdenado()));
        System.out.println("   Arreglo vacío {}      : " + Arrays.toString(ordenar(new int[]{}).arregloOrdenado()));
        System.out.println("   Arreglo nulo          : " + ordenar(null).arregloOrdenado());

        System.out.println("\n====================================================================");
    }
}