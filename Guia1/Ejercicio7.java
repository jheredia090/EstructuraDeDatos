/*
Prompt inicial utilizado (prompt_ej7.md):
Ejercicio 7

Actúa como un desarrollador Java experto y proporciona la implementación de dos métodos estáticos para invertir un arreglo de enteros, contrastando un enfoque inmutable con uno destructivo in-place:

1. Método 1 (Arreglo auxiliar / No destructivo):
   - Invierte el contenido instanciando un nuevo arreglo de igual longitud, preservando intacto el arreglo de entrada.
   - Firma requerida: public static int[] invertirConCopia(int[] arr)
   - Validación de casos límite: si el arreglo es nulo, retorna null; si tiene 0 o 1 elemento, retorna un clon o arreglo equivalente.

2. Método 2 (In-place / Destructivo con dos punteros):
   - Invierte los elementos sobre el mismo arreglo mediante intercambio simétrico (swap) de elementos opuestos convergiendo al centro, con memoria auxiliar O(1).
   - Firma requerida: public static void invertirInPlace(int[] arr)
   - Validación de casos límite: si el arreglo es nulo o de longitud menor a 2, no realiza operaciones.

Análisis comparativo:
- Incluye una tabla comparativa final con:
  * Complejidad temporal en notación Big-O (mejor, peor y promedio).
  * Complejidad espacial auxiliar en notación Big-O.
  * Ventajas de cada solución (inmutabilidad y seguridad de concurrencia vs. ahorro de memoria y localidad espacial en caché).
  * Desventajas de cada solución (costo de instanciación y Garbage Collection vs. efectos colaterales por mutabilidad del estado original).
*/

import java.util.Arrays;

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

    public static void main(String[] args) {
        System.out.println("====================================================================");
        System.out.println("   DEMOSTRACIÓN: INVERSIÓN DE VECTORES (CON COPIA vs IN-PLACE - EJ 7)");
        System.out.println("====================================================================\n");

        // 1. Enfoque inmutable (con copia auxiliar O(n))
        int[] original = {10, 20, 30, 40, 50};
        System.out.println("1. Enfoque No Destructivo (Invertir con copia auxiliar):");
        System.out.println("Arreglo original antes de invertir : " + Arrays.toString(original));
        int[] copiaInvertida = invertirConCopia(original);
        System.out.println("Arreglo invertido retornado        : " + Arrays.toString(copiaInvertida));
        System.out.println("Arreglo original después (intacto) : " + Arrays.toString(original));

        // 2. Enfoque destructivo in-place (O(1) memoria auxiliar)
        System.out.println("\n2. Enfoque Destructivo (Invertir in-place con 2 punteros):");
        int[] arrInPlace = {1, 2, 3, 4, 5, 6};
        System.out.println("Arreglo antes de invertirInPlace   : " + Arrays.toString(arrInPlace));
        invertirInPlace(arrInPlace);
        System.out.println("Arreglo después de invertirInPlace  : " + Arrays.toString(arrInPlace));

        // 3. Casos límite
        System.out.println("\n3. Casos límite:");
        int[] unitario = {42};
        invertirInPlace(unitario);
        System.out.println("Unitario {42} in-place : " + Arrays.toString(unitario));

        int[] vacio = {};
        invertirInPlace(vacio);
        System.out.println("Vacío {} in-place      : " + Arrays.toString(vacio));
        System.out.println("Nulo con copia         : " + invertirConCopia(null));

        System.out.println("\n====================================================================");
    }
}