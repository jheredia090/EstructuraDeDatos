/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Quiero implementar en Java un programa que compare empíricamente los algoritmos Bubble Sort 
 * y Selection Sort al ordenar el mismo arreglo de números enteros.
 * 
 * Este problema requiere analizar y contrastar dos estrategias distintas de ordenamiento cuadrático O(n^2):
 * - Bubble Sort compara pares de vecinos adyacentes e intercambia de inmediato cada vez que 
 *   encuentra una inversión local, lo que puede provocar una gran cantidad de escrituras en memoria 
 *   (hasta O(n^2) intercambios).
 * - Selection Sort divide el arreglo en una zona ordenada y otra desordenada; en cada pasada 
 *   busca linealmente el valor mínimo de la zona desordenada y realiza como máximo un único 
 *   intercambio por pasada para ubicarlo al inicio de dicha zona, garantizando un tope estricto 
 *   de n - 1 intercambios.
 * 
 * Los datos que recibe el programa son un arreglo de enteros desordenados, por ejemplo: 
 * {29, 10, 14, 37, 13}. Para que la comparación sea justa, ambos algoritmos deben ejecutarse 
 * sobre copias idénticas del mismo arreglo original.
 * 
 * El programa debe implementar dos métodos específicos:
 * - public static MetricasOrdenamiento bubbleSort(int[] array)
 * - public static MetricasOrdenamiento selectionSort(int[] array)
 * Donde MetricasOrdenamiento sea un record o clase que encapsule: nombre del algoritmo, 
 * comparaciones e intercambios.
 * 
 * El programa debe mostrar por pantalla:
 * 1. El arreglo original.
 * 2. El arreglo ordenado y las métricas (comparaciones e intercambios) de Bubble Sort.
 * 3. El arreglo ordenado y las métricas de Selection Sort.
 * 4. Una breve conclusión que destaque por qué Selection Sort realiza muchos menos intercambios 
 *    que Bubble Sort a pesar de realizar una cantidad similar de comparaciones.
 * 
 * Quiero que el código tenga comentarios explicando:
 * - La lógica de comparación vecina e intercambios múltiples de Bubble Sort.
 * - La lógica de búsqueda del índice mínimo y el intercambio único por pasada en Selection Sort.
 * - El conteo exacto de cada operación elemental.
 * 
 * La verificación del resultado correcto debe comprobar que ambos arreglos queden idénticos 
 * y en orden ascendente (arr[k] <= arr[k+1]).
 * 
 * También quiero probarlo con estos casos:
 * - Caso 1: Arreglo desordenado {29, 10, 14, 37, 13}.
 * - Caso 2: Arreglo en orden inverso {50, 40, 30, 20, 10} (para evidenciar la máxima diferencia de intercambios).
 * - Caso 3: Arreglo con elementos repetidos {5, 2, 8, 2, 5, 1}.
 * =========================================================================================
 */

import java.util.Arrays;

/**
 * Ejercicio 2: Comparación entre Bubble Sort y Selection Sort.
 * Contrasta la estrategia de comparación entre vecinos con intercambios continuos frente
 * a la búsqueda global del menor con a lo sumo un intercambio por pasada.
 */
public class Ej2 {

    /**
     * Registro inmutable que almacena las estadísticas operativas de cada algoritmo.
     */
    public record MetricasOrdenamiento(String algoritmo, long comparaciones, long intercambios) {}

    /**
     * Ordena el arreglo in-place mediante Bubble Sort y contabiliza sus operaciones.
     * Estrategia: compara vecinos contiguos e intercambia inmediatamente si están desordenados.
     */
    public static MetricasOrdenamiento bubbleSort(int[] array) {
        if (array == null || array.length < 2) {
            return new MetricasOrdenamiento("Bubble Sort", 0, 0);
        }

        int n = array.length;
        long comparaciones = 0;
        long intercambios = 0;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                comparaciones++;
                // Comparación entre vecinos adyacentes
                if (array[j] > array[j + 1]) {
                    // Intercambio inmediato local
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    intercambios++;
                }
            }
        }

        return new MetricasOrdenamiento("Bubble Sort", comparaciones, intercambios);
    }

    /**
     * Ordena el arreglo in-place mediante Selection Sort y contabiliza sus operaciones.
     * Estrategia: busca el índice del menor elemento de la zona desordenada y realiza
     * a lo sumo un único intercambio por pasada para colocarlo al inicio.
     */
    public static MetricasOrdenamiento selectionSort(int[] array) {
        if (array == null || array.length < 2) {
            return new MetricasOrdenamiento("Selection Sort", 0, 0);
        }

        int n = array.length;
        long comparaciones = 0;
        long intercambios = 0;

        for (int i = 0; i < n - 1; i++) {
            // Suponemos provisionalmente que el menor elemento está al inicio de la zona no ordenada
            int indiceMinimo = i;

            // Búsqueda exhaustiva del menor elemento en el resto del arreglo
            for (int j = i + 1; j < n; j++) {
                comparaciones++;
                if (array[j] < array[indiceMinimo]) {
                    indiceMinimo = j; // Actualiza el puntero del menor sin intercambiar aún
                }
            }

            // Un único intercambio por pasada si el menor no estaba ya en la posición 'i'
            if (indiceMinimo != i) {
                int temp = array[i];
                array[i] = array[indiceMinimo];
                array[indiceMinimo] = temp;
                intercambios++;
            }
        }

        return new MetricasOrdenamiento("Selection Sort", comparaciones, intercambios);
    }

    /**
     * Valida si el arreglo está ordenado de menor a mayor.
     */
    public static boolean estaOrdenado(int[] arr) {
        if (arr == null || arr.length < 2) {
            return true;
        }
        for (int k = 0; k < arr.length - 1; k++) {
            if (arr[k] > arr[k + 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Ejecuta ambos algoritmos sobre copias del mismo arreglo y presenta la comparación.
     */
    public static void ejecutarComparacion(String etiqueta, int[] original) {
        System.out.println("======================================================================");
        System.out.println(etiqueta);
        System.out.println("Arreglo original: " + Arrays.toString(original));
        System.out.println("----------------------------------------------------------------------");

        // Clones independientes para asegurar que ambos algoritmos partan de la misma base
        int[] copiaBurbuja = original.clone();
        int[] copiaSeleccion = original.clone();

        MetricasOrdenamiento metricasBurbuja = bubbleSort(copiaBurbuja);
        MetricasOrdenamiento metricasSeleccion = selectionSort(copiaSeleccion);

        System.out.printf("%-16s -> Resultado: %-22s | Comparaciones: %2d | Intercambios: %2d%n",
                metricasBurbuja.algoritmo(), Arrays.toString(copiaBurbuja),
                metricasBurbuja.comparaciones(), metricasBurbuja.intercambios());

        System.out.printf("%-16s -> Resultado: %-22s | Comparaciones: %2d | Intercambios: %2d%n",
                metricasSeleccion.algoritmo(), Arrays.toString(copiaSeleccion),
                metricasSeleccion.comparaciones(), metricasSeleccion.intercambios());

        boolean ordenCorrecto = estaOrdenado(copiaBurbuja) && estaOrdenado(copiaSeleccion);
        boolean arreglosIguales = Arrays.equals(copiaBurbuja, copiaSeleccion);
        System.out.println("Verificación: " + (ordenCorrecto && arreglosIguales ? "CORRECTA (Idénticos y ordenados)" : "FALLÓ"));

        System.out.println("Diferencia clave: Selection Sort realizó "
                + (metricasBurbuja.intercambios() - metricasSeleccion.intercambios())
                + " intercambios menos que Bubble Sort.");
    }

    public static void main(String[] args) {
        ejecutarComparacion("CASO 1: Arreglo desordenado típico", new int[]{29, 10, 14, 37, 13});
        ejecutarComparacion("CASO 2: Arreglo en orden inverso (peor caso)", new int[]{50, 40, 30, 20, 10});
        ejecutarComparacion("CASO 3: Arreglo con elementos duplicados", new int[]{5, 2, 8, 2, 5, 1});
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación realizada:
 *    Se introdujo la guarda 'if (indiceMinimo != i)' antes de ejecutar el swap en Selection Sort.
 * 
 * 2. Justificación técnica:
 *    Si el elemento que ya reside en la posición 'i' resulta ser efectivamente el menor de toda
 *    la zona no ordenada, realizar un intercambio consigo mismo sería una operación redundante
 *    en memoria. Esta guarda optimiza Selection Sort para que realice entre 0 y n - 1
 *    intercambios totales (en un arreglo ya ordenado realiza exactamente 0 intercambios).
 *    Esto pone aún más en evidencia la gran diferencia respecto a Bubble Sort, que sin corte
 *    temprano siempre evalúa y puede intercambiar vecinos en cada desorden local.
 * =========================================================================================
 */