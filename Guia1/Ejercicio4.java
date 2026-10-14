/*
Prompt inicial utilizado (prompt_ej4.md):
Ejercicio 4


Actúa como un desarrollador Java experto y proporciona la implementación de dos métodos estáticos para determinar si un arreglo contiene elementos duplicados:

1. Solución 1 (Ciclos anidados): Recorre el arreglo comparando pares de elementos mediante dos bucles anidados.
2. Solución 2 (HashSet): Recorre el arreglo una sola vez insertando elementos en un java.util.HashSet para identificar duplicados.

Especificaciones técnicas:
- Firma de ambos métodos: public static boolean contieneDuplicados(int[] arr)
- Retorno: true ante el primer duplicado encontrado (early exit), false si todos son únicos.
- Validación de casos límite: arreglo nulo o con longitud menor a 2.
- Código limpio, idiomático y sin dependencias externas.

Análisis comparativo:
- Incluye una tabla comparativa final con:
  * Complejidad temporal en notación Big-O (mejor caso, caso promedio y peor caso).
  * Complejidad espacial auxiliar en notación Big-O.
  * Justificación técnica del trade-off de memoria frente a tiempo de ejecución.
*/

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Proveedor de algoritmos para la detección de elementos duplicados
 * dentro de arreglos de enteros mediante distintos enfoques algorítmicos.
 */
public class Ejercicio4 {

    /**
     * Determina si existen elementos duplicados utilizando fuerza bruta con ciclos anidados.
     * Complejidad temporal: O(n^2). Complejidad espacial auxiliar: O(1).
     *
     * @param arr Arreglo de enteros a inspeccionar.
     * @return {@code true} si se detecta al menos un valor repetido;
     *         {@code false} si todos son únicos o si el arreglo no contiene suficientes elementos.
     */
    public static boolean contieneDuplicadosCiclos(int[] arr) {
        // Un arreglo nulo o con menos de dos elementos no puede contener duplicados
        if (arr == null || arr.length < 2) {
            return false;
        }

        // Compara cada elemento con los restantes.
        // j inicia en i + 1 para evitar comparar un índice consigo mismo y omitir pares ya verificados.
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] == arr[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determina si existen elementos duplicados en un único recorrido empleando una tabla hash.
     * Complejidad temporal promedio: O(n). Complejidad espacial auxiliar: O(n).
     *
     * @param arr Arreglo de enteros a inspeccionar.
     * @return {@code true} si se detecta al menos un valor repetido;
     *         {@code false} si todos son únicos o si el arreglo no contiene suficientes elementos.
     */
    public static boolean contieneDuplicadosHashSet(int[] arr) {
        // Un arreglo nulo o con menos de dos elementos no puede contener duplicados
        if (arr == null || arr.length < 2) {
            return false;
        }

        Set<Integer> vistos = new HashSet<>();
        for (int num : arr) {
            // Set.add() retorna false si el elemento ya existía en la colección,
            // permitiendo consultar y registrar presencia en una única operación O(1) promedio.
            if (!vistos.add(num)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("====================================================================");
        System.out.println("   DEMOSTRACIÓN: DETECCIÓN DE DUPLICADOS (CICLOS vs HASHSET - EJ 4) ");
        System.out.println("====================================================================\n");

        int[][] casos = {
            {1, 2, 3, 4, 2},         // Duplicado al final
            {5, 1, 2, 3, 5},         // Duplicados en extremos
            {1, 2, 3, 4, 5, 6},      // Todos únicos
            {7, 7, 7, 7},            // Múltiples duplicados
            {42},                    // Un único elemento
            {},                      // Vacío
            null                     // Nulo
        };

        for (int i = 0; i < casos.length; i++) {
            int[] caso = casos[i];
            String representacion = (caso == null) ? "null" : Arrays.toString(caso);
            boolean resCiclos = contieneDuplicadosCiclos(caso);
            boolean resHash = contieneDuplicadosHashSet(caso);

            System.out.printf("Caso %d: %-25s -> Ciclos O(n^2): %-5s | HashSet O(n): %-5s\n",
                    (i + 1), representacion, resCiclos ? "SÍ" : "NO", resHash ? "SÍ" : "NO");
        }

        System.out.println("\n====================================================================");
    }
}