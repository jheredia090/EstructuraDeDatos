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
}