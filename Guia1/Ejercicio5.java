/*
Prompt inicial utilizado (prompt_ej5.md):
Ejercicio 5

Actúa como un desarrollador Java experto y proporciona la implementación de un método estático para contar cuántas veces aparece un determinado valor dentro de un arreglo:

Especificaciones técnicas:
- Firma requerida: public static int contarOcurrencias(int[] arr, int objetivo)
- Manejo de casos límite: si el arreglo es nulo o de longitud 0, retorna 0.
- Implementación limpia y directa sin dependencias externas.

Justificación teórica obligatoria:
- Explica de forma concisa por qué en un arreglo desordenado es teóricamente imposible aplicar una salida anticipada (early exit) y por qué el algoritmo está obligado a examinar el 100% de los elementos para garantizar la exactitud del resultado.

Análisis asintótico:
- Especifica la complejidad temporal en notación Big-O para:
  * Mejor caso
  * Caso promedio
  * Peor caso
  (Explica por qué los tres casos son idénticos).
- Especifica la complejidad espacial auxiliar en notación Big-O.
*/

import java.util.Arrays;

/**
 * Utilidad para el cómputo de frecuencias de elementos dentro de arreglos unidimensionales.
 */
public class Ejercicio5 {

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

    public static void main(String[] args) {
        System.out.println("====================================================================");
        System.out.println("   DEMOSTRACIÓN: CONTAR OCURRENCIAS EN UN VECTOR (EJERCICIO 5)      ");
        System.out.println("====================================================================\n");

        int[] vector = {4, 2, 7, 2, 9, 2, 8, 2, 5};
        int[] repetidos = {3, 3, 3, 3};
        int[] unitario = {7};

        System.out.println("Vector base: " + Arrays.toString(vector));
        System.out.println("Ocurrencias del número 2 (aparece 4 veces) : " + contarOcurrencias(vector, 2));
        System.out.println("Ocurrencias del número 7 (aparece 1 vez)   : " + contarOcurrencias(vector, 7));
        System.out.println("Ocurrencias del número 99 (no aparece)     : " + contarOcurrencias(vector, 99));

        System.out.println("\nVector con todos idénticos: " + Arrays.toString(repetidos));
        System.out.println("Ocurrencias del número 3 : " + contarOcurrencias(repetidos, 3));

        System.out.println("\nCasos límite:");
        System.out.println("Vector unitario {7}, buscar 7 : " + contarOcurrencias(unitario, 7));
        System.out.println("Vector vacío {}, buscar 5     : " + contarOcurrencias(new int[]{}, 5));
        System.out.println("Vector nulo, buscar 10        : " + contarOcurrencias(null, 10));

        System.out.println("\n====================================================================");
    }
}