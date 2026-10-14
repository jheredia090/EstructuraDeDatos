/*
Prompt inicial utilizado (prompt_ej1.md):
Implementá en Java un algoritmo que encuentre el valor mínimo de un vector
de números enteros.

Antes de escribir el código, explicá la estrategia que considerás más
adecuada para resolver este problema y justificá por qué esa estrategia
es la correcta. Debés explicar que, para encontrar el mínimo, no hace falta
ordenar el vector ni recorrerlo en un orden específico; basta con comparar
cada elemento con el menor encontrado hasta el momento.

La estrategia recomendada es la siguiente:
- asumir que el primer elemento del vector es el menor actual;
- recorrer el vector desde la segunda posición;
- si un elemento es menor que el mínimo actual, actualizar el valor del mínimo;
- al finalizar el recorrido, devolver el valor mínimo.

Justificá por qué esta estrategia es eficiente para este problema:
- porque cada elemento se recorre una sola vez;
- porque no es necesario ordenar el arreglo;
- porque el valor mínimo se determina por comparación directa y no por
  reorganización de datos.

Indicá la complejidad esperada:
- complejidad temporal: O(n)
- complejidad espacial: O(1)

Requisitos del programa:
1. El código debe estar escrito en Java.
2. Debe recibir un vector de enteros.
3. Debe devolver o mostrar el valor mínimo del vector.
4. Debe estar correctamente comentado.
5. Debe incluir una explicación clara de por qué no es necesario ordenar
   el vector para encontrar el mínimo.
6. El programa debe mostrar el resultado en consola y también puede
   incluir una prueba con un vector de ejemplo.
7. Si se decide hacer una versión con un método separado, debe quedar
   bien estructurada y legible.
8. Debe contemplar el manejo de casos límite: validar si el vector es null
   o está vacío (length == 0), lanzando una excepción clara (IllegalArgumentException)
   para prevenir NullPointerException o ArrayIndexOutOfBoundsException.

Ejemplo de prueba:
int[] vector = {12, 4, 7, 1, 9, 15, 3};

Resultado esperado:
1
*/

import java.util.Arrays;

/**
 * Ejercicio 1 - Guía 1: Encontrar el Mínimo de un Vector de Números Enteros
 *
 * ESTRATEGIA:
 * 1. Asumir provisionalmente que el primer elemento del vector (índice 0) es el mínimo.
 * 2. Recorrer secuencialmente el arreglo a partir de la segunda posición (índice 1).
 * 3. En cada iteración, comparar el elemento actual con el mínimo acumulado.
 * 4. Si el elemento actual es menor, actualizar el valor del mínimo.
 * 5. Al concluir el recorrido completo, devolver el valor mínimo hallado.
 *
 * JUSTIFICACIÓN: ¿POR QUÉ NO ES NECESARIO ORDENAR EL VECTOR?
 * - Complejidad de Ordenamiento: Cualquier algoritmo de ordenamiento por comparación
 *   requiere en el mejor de los casos O(n log n) tiempo (MergeSort, QuickSort, TimSort)
 *   o O(n^2) (BubbleSort, InsertionSort), además de mutar el arreglo original o exigir
 *   memoria auxiliar O(n) para no alterarlo.
 * - Eficiencia de Inspección Directa: Para hallar el mínimo solo se necesita una única
 *   propiedad del conjunto (el valor extremo inferior), no una reorganización global.
 *   Una búsqueda lineal directa resuelve el problema en una sola pasada en tiempo O(n)
 *   con memoria auxiliar constante O(1).
 *
 * ANÁLISIS DE COMPLEJIDAD:
 * - Complejidad Temporal: O(n), donde n es la cantidad de elementos del vector.
 *   Se efectúan exactamente (n - 1) comparaciones independientes del orden de los datos.
 * - Complejidad Espacial: O(1), no requiere memoria auxiliar proporcional al tamaño del vector.
 */
public class Ejercicio1 {

    /**
     * Encuentra el valor mínimo presente en un vector de números enteros.
     *
     * @param vector Arreglo de enteros a evaluar.
     * @return El valor numérico mínimo encontrado.
     * @throws IllegalArgumentException Si el vector recibido es nulo o no contiene elementos.
     */
    public static int encontrarMinimo(int[] vector) {
        if (vector == null || vector.length == 0) {
            throw new IllegalArgumentException("El vector no puede ser nulo ni estar vacío.");
        }

        int minimo = vector[0];

        for (int i = 1; i < vector.length; i++) {
            if (vector[i] < minimo) {
                minimo = vector[i];
            }
        }

        return minimo;
    }

    /**
     * Encuentra el índice (posición 0 a n-1) de la primera aparición del valor mínimo.
     *
     * @param vector Arreglo de enteros a evaluar.
     * @return Índice de la posición del valor mínimo.
     * @throws IllegalArgumentException Si el vector recibido es nulo o está vacío.
     */
    public static int encontrarPosicionMinimo(int[] vector) {
        if (vector == null || vector.length == 0) {
            throw new IllegalArgumentException("El vector no puede ser nulo ni estar vacío.");
        }

        int indiceMinimo = 0;

        for (int i = 1; i < vector.length; i++) {
            if (vector[i] < vector[indiceMinimo]) {
                indiceMinimo = i;
            }
        }

        return indiceMinimo;
    }

    public static void main(String[] args) {
        System.out.println("====================================================================");
        System.out.println("   DEMOSTRACIÓN: ENCONTRAR EL MÍNIMO DE UN VECTOR (EJERCICIO 1)     ");
        System.out.println("====================================================================\n");

        // 1. Caso estándar de prueba
        int[] v1 = {12, 4, 7, 1, 9, 15, 3};
        System.out.println("1. Vector estándar: " + Arrays.toString(v1));
        System.out.println("   Valor mínimo : " + encontrarMinimo(v1));
        System.out.println("   Encontrado en índice: " + encontrarPosicionMinimo(v1) + "\n");

        // 2. Vector con números negativos (evidencia la independencia de centinelas como 0)
        int[] v2 = {-5, -20, 3, -1, -15, 8};
        System.out.println("2. Vector con negativos: " + Arrays.toString(v2));
        System.out.println("   Valor mínimo : " + encontrarMinimo(v2));
        System.out.println("   Encontrado en índice: " + encontrarPosicionMinimo(v2) + "\n");

        // 3. Vector con valores duplicados
        int[] v3 = {8, 3, 5, 3, 9, 12};
        System.out.println("3. Vector con duplicados: " + Arrays.toString(v3));
        System.out.println("   Valor mínimo : " + encontrarMinimo(v3));
        System.out.println("   Primera aparición en índice: " + encontrarPosicionMinimo(v3) + "\n");

        // 4. Caso unitario (un solo elemento)
        int[] v4 = {42};
        System.out.println("4. Vector unitario: " + Arrays.toString(v4));
        System.out.println("   Valor mínimo : " + encontrarMinimo(v4));
        System.out.println("   Encontrado en índice: " + encontrarPosicionMinimo(v4) + "\n");

        // 5. Casos límite con manejo de excepciones
        System.out.println("5. Casos límite:");

        try {
            encontrarMinimo(new int[]{});
        } catch (IllegalArgumentException e) {
            System.out.println("   Excepción capturada con vector vacío {}: " + e.getMessage());
        }

        try {
            encontrarMinimo(null);
        } catch (IllegalArgumentException e) {
            System.out.println("   Excepción capturada con vector nulo: " + e.getMessage());
        }

        System.out.println("\n====================================================================");
    }
}
