/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Quiero implementar en Java el algoritmo Insertion Sort (Ordenamiento por Inserción) sobre 
 * un arreglo de números enteros casi ordenado, midiendo los desplazamientos realizados.
 * 
 * Este problema se resuelve mediante Insertion Sort porque el algoritmo construye de izquierda 
 * a derecha una sublista ordenada. Para cada nuevo elemento ('clave'), se compara hacia atrás 
 * y se desplazan los elementos mayores un lugar hacia la derecha para hacerle espacio.
 * En un arreglo casi ordenado, la condición del ciclo interno se interrumpe de forma casi inmediata 
 * (corte temprano natural), ya que la mayoría de los elementos ya son mayores que su predecesor. 
 * Esto evita desplazamientos masivos y permite que el algoritmo opere en tiempo casi lineal O(n + d), 
 * donde d es la cantidad de inversiones.
 * 
 * Los datos que recibe el programa son un arreglo casi ordenado provisto en la consigna: 
 * {1, 2, 3, 5, 4, 6, 7}.
 * 
 * El programa debe mostrar por pantalla:
 * 1. El arreglo original antes de ordenar.
 * 2. El proceso de inserción paso a paso indicando qué elemento actúa como 'clave' y cuántos 
 *    desplazamientos demandó acomodarlo.
 * 3. El arreglo final ordenado.
 * 4. El total de desplazamientos realizados, explicando por qué esa cifra es mínima frente a 
 *    un arreglo completamente desordenado del mismo tamaño.
 * 
 * Quiero que el código tenga comentarios explicando:
 * - La extracción de la variable 'clave' (arr[i]).
 * - La condición de parada del ciclo interno (j >= 0 && arr[j] > clave) que otorga la eficiencia lineal.
 * - El desplazamiento de elementos mayores arr[j + 1] = arr[j] y el incremento del contador de desplazamientos.
 * - La inserción final de la clave en su hueco definitivo arr[j + 1] = clave.
 * 
 * La verificación del resultado correcto debe comprobar que el arreglo final esté estrictamente 
 * ordenado (arr[k] <= arr[k+1]).
 * 
 * También quiero probarlo con estos casos:
 * - Caso 1: Arreglo casi ordenado del enunciado {1, 2, 3, 5, 4, 6, 7} (solo una inversión local).
 * - Caso 2: Arreglo perfectamente ordenado {1, 2, 3, 4, 5, 6, 7} (0 desplazamientos, mejor caso puro O(n)).
 * - Caso 3: Arreglo en orden inverso {7, 6, 5, 4, 3, 2, 1} (peor caso, para contrastar la explosión de desplazamientos).
 * =========================================================================================
 */

import java.util.Arrays;

/**
 * Ejercicio 3: Ordenamiento por Inserción sobre arreglos casi ordenados.
 * Demuestra la alta eficiencia adaptativa de Insertion Sort (cercana a O(n))
 * ante colecciones con bajo número de inversiones.
 */
public class Ej3 {

    /**
     * Ordena un arreglo in-place mediante Insertion Sort, imprimiendo el detalle de cada paso
     * y contabilizando la cantidad exacta de desplazamientos de elementos.
     *
     * @param arr Arreglo de enteros a ordenar.
     * @return Cantidad total de desplazamientos efectuados.
     */
    public static long insertionSortDetallado(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        int n = arr.length;
        long totalDesplazamientos = 0;

        System.out.println("Arreglo inicial: " + Arrays.toString(arr));
        System.out.println("----------------------------------------------------------------------");

        // El primer elemento (índice 0) ya constituye una sublista ordenada trivial.
        // Se itera desde el índice 1 hasta el final para insertar cada nuevo elemento en su lugar.
        for (int i = 1; i < n; i++) {
            // Extracción de la 'clave' a posicionar
            int clave = arr[i];
            int j = i - 1;
            int desplazamientosPaso = 0;

            // Ciclo interno: busca la posición correcta desplazando los elementos mayores que 'clave'.
            // Si arr[j] <= clave, la condición se quiebra de inmediato (corte temprano natural).
            while (j >= 0 && arr[j] > clave) {
                // Desplaza el elemento mayor un lugar hacia la derecha
                arr[j + 1] = arr[j];
                j--;
                desplazamientosPaso++;
                totalDesplazamientos++;
            }

            // Inserción de la clave en el hueco generado
            arr[j + 1] = clave;

            System.out.printf("Paso %d (Clave = %d): %-28s -> Desplazamientos en este paso: %d%n",
                    i, clave, Arrays.toString(arr), desplazamientosPaso);
        }

        System.out.println("----------------------------------------------------------------------");
        System.out.println("Arreglo final ordenado: " + Arrays.toString(arr));
        return totalDesplazamientos;
    }

    /**
     * Valida si el arreglo se encuentra ordenado de menor a mayor.
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
     * Ejecuta y compara los tres escenarios propuestos en el enunciado.
     */
    public static void probarCaso(String titulo, int[] entrada) {
        System.out.println("======================================================================");
        System.out.println(titulo);
        System.out.println("======================================================================");

        int[] copia = entrada.clone();
        long desplazamientos = insertionSortDetallado(copia);

        System.out.println("Total de desplazamientos realizados: " + desplazamientos);
        System.out.println("Verificación de orden: " + (estaOrdenado(copia) ? "CORRECTO" : "FALLÓ"));
        System.out.println();
    }

    public static void main(String[] args) {
        // Caso 1: Caso específico del enunciado (casi ordenado, solo el 5 y el 4 invertidos)
        probarCaso("CASO 1: Arreglo casi ordenado del enunciado", new int[]{1, 2, 3, 5, 4, 6, 7});

        // Caso 2: Totalmente ordenado (mejor caso puro)
        probarCaso("CASO 2: Arreglo ya ordenado (Mejor caso)", new int[]{1, 2, 3, 4, 5, 6, 7});

        // Caso 3: Invertido (peor caso, para contraste empírico)
        probarCaso("CASO 3: Arreglo en orden inverso (Peor caso)", new int[]{7, 6, 5, 4, 3, 2, 1});
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación realizada:
 *    Se distinguió y contabilizó el concepto de 'desplazamiento' (shift: arr[j+1] = arr[j]) en
 *    lugar de 'intercambio' (swap).
 * 
 * 2. Justificación técnica:
 *    En Insertion Sort tradicional no se realizan swaps completos (que costarían 3 escrituras
 *    en memoria por par); en su lugar, se realiza un solo guardado de la variable 'clave',
 *    se desplazan los elementos mayores hacia la derecha (1 escritura por elemento) y se
 *    asienta la clave al final (1 escritura).
 *    En el Caso 1 ({1, 2, 3, 5, 4, 6, 7}), solo el 4 exige desplazar al 5, produciendo
 *    exactamente 1 desplazamiento en todo el proceso. Esto ilustra a la perfección por qué
 *    Insertion Sort es el algoritmo de elección cuando un arreglo ya está parcialmente ordenado.
 * =========================================================================================
 */