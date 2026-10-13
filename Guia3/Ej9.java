/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Quiero implementar en Java un programa que compare exhaustivamente los tres algoritmos 
 * elementales de ordenamiento: Bubble Sort, Selection Sort e Insertion Sort, sobre exactamente 
 * el mismo conjunto de datos.
 * 
 * Este problema requiere analizar y contrastar cómo tres estrategias distintas de ordenamiento 
 * cuadrático abordan el problema:
 * - Bubble Sort compara vecinos adyacentes e intercambia inmediatamente ante desorden local 
 *   (alta cantidad de swaps).
 * - Selection Sort busca el mínimo global de la zona desordenada y realiza a lo sumo un swap 
 *   por pasada (mínima cantidad de escrituras en memoria).
 * - Insertion Sort toma cada elemento y lo inserta en su posición relativa correcta desplazando 
 *   elementos mayores hacia atrás (adaptativo y de alta eficiencia con datos casi ordenados).
 * 
 * Los datos que recibe el programa son un arreglo de enteros desordenados, por ejemplo: 
 * {64, 25, 12, 22, 11}. Los tres métodos deben operar sobre clones independientes para que la 
 * prueba sea estrictamente equitativa.
 * 
 * El programa debe implementar una estructura unificada de métricas (record ResultadoComparacion) 
 * y mostrar por pantalla:
 * 1. El arreglo original.
 * 2. Una tabla comparativa con:
 *    - Nombre del algoritmo.
 *    - Arreglo final ordenado.
 *    - Cantidad de comparaciones realizadas.
 *    - Cantidad de intercambios o desplazamientos (swaps/shifts) realizados.
 * 3. Un análisis conceptual justificando las diferencias encontradas en cada métrica.
 * 
 * Quiero que el código tenga comentarios explicando:
 * - La instrumentación precisa de comparaciones y movimientos en cada uno de los tres métodos.
 * - La diferencia fundamental entre un swap completo (3 escrituras en Bubble/Selection) y un 
 *   desplazamiento unitario (1 escritura en Insertion).
 * - Por qué evaluar solo el resultado final oculta las diferencias de costo operativo de cada estrategia.
 * 
 * La verificación del resultado correcto debe comprobar que los tres arreglos resultantes sean 
 * idénticos entre sí y estén ordenados (arr[k] <= arr[k+1]).
 * 
 * También quiero probarlo con estos casos:
 * - Caso 1: Arreglo desordenado {64, 25, 12, 22, 11}.
 * - Caso 2: Arreglo casi ordenado {10, 20, 40, 30, 50, 60} (para evidenciar la ventaja de Insertion Sort).
 * - Caso 3: Arreglo en orden inverso {50, 40, 30, 20, 10} (peor caso para Bubble e Insertion).
 * =========================================================================================
 */

import java.util.Arrays;

/**
 * Ejercicio 9: Comparación de algoritmos elementales (Bubble, Selection e Insertion Sort).
 * Contrasta el costo operativo interno de cada estrategia (comparaciones vs. movimientos de datos)
 * más allá de la coincidencia del resultado final ordenado.
 */
public class Ej9 {

    /**
     * Registro inmutable que almacena los resultados de cada ejecución.
     */
    public record ResultadoComparacion(String algoritmo, int[] arregloOrdenado, long comparaciones, long movimientos) {}

    /**
     * 1. Bubble Sort:
     * Compara vecinos adyacentes y realiza intercambios locales inmediatos.
     */
    public static ResultadoComparacion bubbleSort(int[] arr) {
        int[] copia = arr.clone();
        int n = copia.length;
        long comparaciones = 0;
        long intercambios = 0;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                comparaciones++;
                if (copia[j] > copia[j + 1]) {
                    // Intercambio completo (swap: 3 escrituras en memoria)
                    int temp = copia[j];
                    copia[j] = copia[j + 1];
                    copia[j + 1] = temp;
                    intercambios++;
                }
            }
        }
        return new ResultadoComparacion("Bubble Sort", copia, comparaciones, intercambios);
    }

    /**
     * 2. Selection Sort:
     * Busca el menor elemento de la zona no ordenada y realiza a lo sumo un intercambio por pasada.
     */
    public static ResultadoComparacion selectionSort(int[] arr) {
        int[] copia = arr.clone();
        int n = copia.length;
        long comparaciones = 0;
        long intercambios = 0;

        for (int i = 0; i < n - 1; i++) {
            int indiceMinimo = i;
            for (int j = i + 1; j < n; j++) {
                comparaciones++;
                if (copia[j] < copia[indiceMinimo]) {
                    indiceMinimo = j;
                }
            }
            // Realiza el intercambio únicamente si el mínimo no estaba ya en la posición 'i'
            if (indiceMinimo != i) {
                int temp = copia[i];
                copia[i] = copia[indiceMinimo];
                copia[indiceMinimo] = temp;
                intercambios++;
            }
        }
        return new ResultadoComparacion("Selection Sort", copia, comparaciones, intercambios);
    }

    /**
     * 3. Insertion Sort:
     * Inserta cada nuevo elemento en la sublista ordenada realizando desplazamientos hacia atrás.
     */
    public static ResultadoComparacion insertionSort(int[] arr) {
        int[] copia = arr.clone();
        int n = copia.length;
        long comparaciones = 0;
        long desplazamientos = 0;

        for (int i = 1; i < n; i++) {
            int clave = copia[i];
            int j = i - 1;

            // Se evalúa la condición de corte: arr[j] > clave
            while (j >= 0) {
                comparaciones++;
                if (copia[j] > clave) {
                    copia[j + 1] = copia[j]; // Desplazamiento unitario (1 escritura)
                    desplazamientos++;
                    j--;
                } else {
                    break; // Corte temprano al encontrar la posición correcta
                }
            }
            copia[j + 1] = clave;
        }
        return new ResultadoComparacion("Insertion Sort", copia, comparaciones, desplazamientos);
    }

    /**
     * Verifica que un arreglo esté ordenado ascendentemente.
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
     * Ejecuta y compara los tres algoritmos sobre exactamente el mismo conjunto de datos.
     */
    public static void ejecutarComparativa(String titulo, int[] datos) {
        System.out.println("=========================================================================================");
        System.out.println(titulo);
        System.out.println("Arreglo original: " + Arrays.toString(datos));
        System.out.println("-----------------------------------------------------------------------------------------");

        ResultadoComparacion resBurbuja = bubbleSort(datos);
        ResultadoComparacion resSeleccion = selectionSort(datos);
        ResultadoComparacion resInsercion = insertionSort(datos);

        System.out.printf("%-16s | %-24s | %-14s | %-16s%n",
                "Algoritmo", "Resultado Ordenado", "Comparaciones", "Movimientos (Swaps/Shifts)");
        System.out.println("-----------------------------------------------------------------------------------------");

        imprimirFila(resBurbuja);
        imprimirFila(resSeleccion);
        imprimirFila(resInsercion);

        System.out.println("-----------------------------------------------------------------------------------------");

        boolean todosOrdenados = estaOrdenado(resBurbuja.arregloOrdenado())
                && estaOrdenado(resSeleccion.arregloOrdenado())
                && estaOrdenado(resInsercion.arregloOrdenado());

        boolean todosIguales = Arrays.equals(resBurbuja.arregloOrdenado(), resSeleccion.arregloOrdenado())
                && Arrays.equals(resSeleccion.arregloOrdenado(), resInsercion.arregloOrdenado());

        System.out.println("Verificación de exactitud: " + (todosOrdenados && todosIguales ? "CORRECTA (Idénticos y ordenados)" : "FALLÓ"));
        System.out.println();
    }

    private static void imprimirFila(ResultadoComparacion res) {
        System.out.printf("%-16s | %-24s | %-14d | %-16d%n",
                res.algoritmo(),
                Arrays.toString(res.arregloOrdenado()),
                res.comparaciones(),
                res.movimientos());
    }

    public static void main(String[] args) {
        // Caso 1: Arreglo desordenado típico
        ejecutarComparativa("CASO 1: Arreglo desordenado {64, 25, 12, 22, 11}",
                new int[]{64, 25, 12, 22, 11});

        // Caso 2: Arreglo casi ordenado (muestra la superioridad de Insertion Sort)
        ejecutarComparativa("CASO 2: Arreglo casi ordenado {10, 20, 40, 30, 50, 60}",
                new int[]{10, 20, 40, 30, 50, 60});

        // Caso 3: Arreglo en orden inverso (peor caso para Bubble e Insertion)
        ejecutarComparativa("CASO 3: Arreglo en orden inverso {50, 40, 30, 20, 10}",
                new int[]{50, 40, 30, 20, 10});
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación realizada:
 *    Se desacopló la métrica 'movimientos', especificando conceptualmente que para Bubble Sort
 *    y Selection Sort corresponde a 'swaps' (intercambios completos), mientras que para
 *    Insertion Sort corresponde a 'shifts' (desplazamientos de una sola posición).
 * 
 * 2. Justificación técnica:
 *    Si solo se observara el resultado final, los tres algoritmos producen exactamente la
 *    misma salida. Sin embargo, el análisis operativo revela diferencias críticas:
 *    - En el Caso 2 (casi ordenado), Insertion Sort solo realiza 6 comparaciones y 1 solo
 *      desplazamiento, superando ampliamente a Selection Sort (15 comparaciones) y Bubble Sort.
 *    - En el Caso 3 (orden inverso), Selection Sort mantiene un costo mínimo de escrituras
 *      (solo 2 intercambios), mientras que Bubble Sort dispara 10 intercambios y 30 escrituras
 *      en memoria.
 *    Esta distinción empírica demuestra por qué cada algoritmo posee un nicho de aplicación
 *    distinto dependiendo de la naturaleza de los datos y del costo de escritura del hardware.
 * =========================================================================================
 */