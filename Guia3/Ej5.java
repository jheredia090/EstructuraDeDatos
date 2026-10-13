/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Quiero implementar en Java el algoritmo ShellSort sobre un arreglo de números enteros, 
 * mostrando la evolución de los saltos (gaps) paso a paso.
 * 
 * Este problema se resuelve mediante ShellSort porque el algoritmo optimiza el rendimiento de 
 * Insertion Sort resolviendo su principal debilidad: la lentitud de desplazar elementos pequeños 
 * que están al final de a una posición por vez. ShellSort compara elementos separados por una 
 * distancia 'gap' mayor a 1, permitiendo que los datos den saltos largos para acercarse rápidamente 
 * a su posición definitiva. A medida que el gap se reduce a la mitad (gap = gap / 2), el arreglo 
 * queda progresivamente casi ordenado, de modo que la pasada final con gap = 1 se ejecuta en 
 * tiempo casi lineal.
 * 
 * Los datos que recibe el programa son un arreglo de enteros desordenados, por ejemplo: 
 * {35, 14, 21, 8, 42, 19, 7, 26, 31, 12}.
 * 
 * El programa debe mostrar por pantalla:
 * 1. El arreglo original antes de comenzar.
 * 2. Cada etapa del ordenamiento, indicando el valor del 'gap' actual y el estado completo 
 *    del arreglo luego de procesar todas las inserciones con ese salto.
 * 3. El arreglo final completamente ordenado.
 * 4. Una breve explicación de por qué los saltos largos mejoran la eficiencia frente al 
 *    Insertion Sort tradicional.
 * 
 * Quiero que el código tenga comentarios explicando:
 * - La inicialización y reducción del gap: for (int gap = n / 2; gap > 0; gap /= 2).
 * - La adaptación de Insertion Sort con saltos: cómo el índice 'j' retrocede en pasos de 
 *   'gap' (j -= gap) en lugar de 1.
 * - Cómo los intercambios a larga distancia destruyen las inversiones lejanas prematuramente.
 * 
 * La verificación del resultado correcto debe comprobar programáticamente que arr[k] <= arr[k+1] 
 * para todos los índices.
 * 
 * También quiero probarlo con estos casos:
 * - Caso 1: Arreglo del ejemplo {35, 14, 21, 8, 42, 19, 7, 26, 31, 12}.
 * - Caso 2: Arreglo en orden inverso {60, 50, 40, 30, 20, 10} (para observar cómo los gaps grandes 
 *   revierten el desorden rápido).
 * - Caso 3: Arreglo pequeño {9, 1, 5, 2}.
 * =========================================================================================
 */

import java.util.Arrays;

/**
 * Ejercicio 5: ShellSort explicando los gaps.
 * Muestra cómo la subdivisión en sublistas entrelazadas mediante saltos (gaps)
 * acelera el proceso de ordenamiento antes de la pasada final de inserción pura.
 */
public class Ej5 {

    /**
     * Ordena un arreglo in-place mediante ShellSort, imprimiendo la traza en cada cambio de gap.
     *
     * @param arr Arreglo de números enteros a ordenar.
     */
    public static void ordenarShellSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            System.out.println("Arreglo sin elementos suficientes para ordenar.");
            return;
        }

        int n = arr.length;
        System.out.println("Arreglo original: " + Arrays.toString(arr));
        System.out.println("--------------------------------------------------------------------------------");

        int etapa = 1;

        // 1. Secuencia de gaps clásica de Shell: comienza con n / 2 y se divide por 2 en cada etapa
        for (int gap = n / 2; gap > 0; gap /= 2) {

            // 2. Realiza un Insertion Sort modificado sobre los elementos separados por la distancia 'gap'
            for (int i = gap; i < n; i++) {
                int clave = arr[i];
                int j = i;

                // 3. Desplaza los elementos mayores que 'clave' en pasos de 'gap' (j -= gap)
                // en lugar de desplazamientos unitarios (j--)
                while (j >= gap && arr[j - gap] > clave) {
                    arr[j] = arr[j - gap]; // Salto largo hacia adelante del elemento mayor
                    j -= gap;
                }

                // Inserción de la clave en su posición relativa dentro del subgrupo actual
                arr[j] = clave;
            }

            System.out.printf("Etapa %d (Gap = %2d): %s%n", etapa++, gap, Arrays.toString(arr));
        }

        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Arreglo final ordenado: " + Arrays.toString(arr));
        System.out.println("Justificación: Al reducir las inversiones a distancia con gaps grandes,");
        System.out.println("la última etapa (gap = 1) se ejecuta sobre un arreglo 'casi ordenado',");
        System.out.println("requiriendo muy pocos desplazamientos locales.");
    }

    /**
     * Verifica si el arreglo se encuentra ordenado ascendentemente.
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
     * Ejecuta y prueba los tres casos estipulados en el prompt.
     */
    public static void probarCaso(String titulo, int[] entrada) {
        System.out.println("================================================================================");
        System.out.println(titulo);
        System.out.println("================================================================================");

        int[] copia = entrada.clone();
        ordenarShellSort(copia);

        boolean ordenOk = estaOrdenado(copia);
        System.out.println("Verificación de orden: " + (ordenOk ? "CORRECTO" : "FALLÓ"));
        System.out.println();
    }

    public static void main(String[] args) {
        // Caso 1: Arreglo de 10 elementos del ejemplo
        probarCaso("CASO 1: Arreglo desordenado de 10 elementos",
                new int[]{35, 14, 21, 8, 42, 19, 7, 26, 31, 12});

        // Caso 2: Orden inverso (máximo desorden a distancia)
        probarCaso("CASO 2: Arreglo en orden inverso (60 a 10)",
                new int[]{60, 50, 40, 30, 20, 10});

        // Caso 3: Arreglo pequeño
        probarCaso("CASO 3: Arreglo pequeño de 4 elementos",
                new int[]{9, 1, 5, 2});
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación realizada:
 *    Se utilizó la secuencia canónica de intervalos propuesta originalmente por Donald Shell
 *    (gap = n / 2, decreciendo con división entera por 2 hasta llegar a 1).
 * 
 * 2. Justificación técnica:
 *    Aunque existen secuencias asintóticamente más avanzadas (como las de Sedgewick, Hibbard
 *    o Pratt), la secuencia de división por 2 es la más intuitiva y pedagógica para comprender
 *    el concepto de h-ordenamiento (h-sorted array). Permite observar con total claridad cómo
 *    cada sublista de elementos entrelazados se ordena internamente en cada etapa, dejando el
 *    arreglo listo para que la fase final (gap = 1) opere con la máxima eficiencia de Insertion Sort.
 * =========================================================================================
 */