/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Quiero implementar en Java el algoritmo Quicksort utilizando siempre el primer elemento 
 * del subarreglo como pivote, mostrando el proceso de partición y relacionándolo con el 
 * paradigma Divide y Vencerás.
 * 
 * Este problema se resuelve mediante Quicksort porque aplica recursión bajo la estrategia 
 * Divide y Vencerás:
 * - Divide: Se toma el primer elemento como pivote (arr[inicio]). Mediante un proceso de 
 *   partición, se ubica el pivote en su posición final, dejando a su izquierda los elementos 
 *   menores y a su derecha los elementos mayores.
 * - Vence: Se invocan recursivamente llamadas sobre el subarreglo izquierdo (inicio a indicePivote - 1) 
 *   y el subarreglo derecho (indicePivote + 1 a fin).
 * - Caso base: Si inicio >= fin, el subarreglo tiene 0 o 1 elementos y ya está ordenado 
 *   por definición, deteniendo la recursión.
 * 
 * Los datos que recibe el programa son un arreglo de números enteros desordenados, por ejemplo: 
 * {38, 27, 43, 3, 9, 82, 10}.
 * 
 * El programa debe mostrar por pantalla:
 * 1. El arreglo original.
 * 2. En cada paso de partición:
 *    - El pivote elegido (el primer elemento del tramo actual).
 *    - Los elementos que quedan en el subarreglo izquierdo (< pivote).
 *    - Los elementos que quedan en el subarreglo derecho (> pivote).
 *    - El estado del arreglo en ese punto.
 * 3. El arreglo final completamente ordenado.
 * 
 * Quiero que el código tenga comentarios explicando:
 * - La elección del primer elemento como pivote: int pivote = arr[inicio].
 * - El esquema de partición para reorganizar los elementos menores a la izquierda y mayores a la derecha.
 * - La colocación final del pivote en su índice definitivo.
 * - Las dos llamadas recursivas sobre las mitades izquierda y derecha (excluyendo el pivote ya fijado).
 * - La condición de corte de la recursión (inicio >= fin).
 * 
 * La verificación del resultado correcto debe comprobar que arr[k] <= arr[k+1] para todos los 
 * índices válidos.
 * 
 * También quiero probarlo con estos casos:
 * - Caso 1: Arreglo desordenado {38, 27, 43, 3, 9, 82, 10}.
 * - Caso 2: Arreglo con elementos negativos y positivos {12, -3, 7, 0, 15, -8, 4}.
 * - Caso 3: Arreglo pequeño {5, 2, 8, 1, 9}.
 * =========================================================================================
 */

import java.util.Arrays;

/**
 * Ejercicio 6: Quicksort con primer elemento como pivote.
 * Ejemplifica la estrategia Divide y Vencerás mediante partición en torno a un pivote
 * y llamadas recursivas independientes sobre los subarreglos resultantes.
 */
public class Ej6 {

    /**
     * Método público de entrada (wrapper) para ordenar el arreglo completo.
     *
     * @param arr Arreglo de enteros a ordenar.
     */
    public static void quicksort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        System.out.println("Arreglo original: " + Arrays.toString(arr));
        System.out.println("--------------------------------------------------------------------------------");
        quicksortRecursivo(arr, 0, arr.length - 1);
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Arreglo final ordenado: " + Arrays.toString(arr));
    }

    /**
     * Función recursiva que implementa el flujo Divide y Vencerás.
     *
     * @param arr Arreglo compartido en memoria.
     * @param inicio Índice de inicio del subarreglo actual.
     * @param fin Índice de fin del subarreglo actual.
     */
    private static void quicksortRecursivo(int[] arr, int inicio, int fin) {
        // CASO BASE: Si el subarreglo tiene 0 o 1 elemento, ya está ordenado trivialmente
        if (inicio >= fin) {
            return; // Detiene la recursión y desapila la llamada
        }

        // FASE 1: DIVIDE (Partición)
        // Reorganiza el subarreglo en torno al primer elemento y obtiene su posición final
        int indicePivote = particionar(arr, inicio, fin);

        // FASE 2: VENCE (Recursión sobre los dos subproblemas independientes)
        // Subarreglo izquierdo (elementos menores al pivote)
        quicksortRecursivo(arr, inicio, indicePivote - 1);

        // Subarreglo derecho (elementos mayores al pivote)
        quicksortRecursivo(arr, indicePivote + 1, fin);

        // FASE 3: COMBINA (No requiere acciones adicionales; el arreglo se ordena in-place)
    }

    /**
     * Realiza la partición tomando arr[inicio] como pivote y acomoda los menores a la izquierda
     * y los mayores a la derecha.
     *
     * @param arr Arreglo a particionar.
     * @param inicio Índice inicial del tramo.
     * @param fin Índice final del tramo.
     * @return El índice definitivo donde quedó alojado el pivote.
     */
    private static int particionar(int[] arr, int inicio, int fin) {
        // Elección del primer elemento del tramo como pivote
        int pivote = arr[inicio];

        // 'limiteMenores' rastrea el límite superior de los elementos que son <= pivote
        int limiteMenores = inicio;

        // Recorre desde el elemento siguiente al pivote hasta el final del tramo
        for (int k = inicio + 1; k <= fin; k++) {
            if (arr[k] <= pivote) {
                limiteMenores++;
                // Intercambia el elemento menor hacia la izquierda
                int temp = arr[limiteMenores];
                arr[limiteMenores] = arr[k];
                arr[k] = temp;
            }
        }

        // Ubica el pivote en su posición definitiva intercambiándolo con arr[limiteMenores]
        int temp = arr[inicio];
        arr[inicio] = arr[limiteMenores];
        arr[limiteMenores] = temp;

        int indicePivote = limiteMenores;

        // Visualización del paso de partición
        int[] subIzq = Arrays.copyOfRange(arr, inicio, indicePivote);
        int[] subDer = Arrays.copyOfRange(arr, indicePivote + 1, fin + 1);

        System.out.printf("Pivote: %3d | Izq (<): %-15s | Der (>): %-15s | Estado: %s%n",
                pivote, Arrays.toString(subIzq), Arrays.toString(subDer), Arrays.toString(arr));

        return indicePivote;
    }

    /**
     * Verifica que el arreglo resultante esté ordenado ascendentemente.
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
        quicksort(copia);

        boolean ordenOk = estaOrdenado(copia);
        System.out.println("Verificación de orden: " + (ordenOk ? "CORRECTO" : "FALLÓ"));
        System.out.println();
    }

    public static void main(String[] args) {
        // Caso 1: Arreglo desordenado típico
        probarCaso("CASO 1: Arreglo desordenado", new int[]{38, 27, 43, 3, 9, 82, 10});

        // Caso 2: Arreglo con negativos y cero
        probarCaso("CASO 2: Arreglo con negativos y positivos", new int[]{12, -3, 7, 0, 15, -8, 4});

        // Caso 3: Arreglo pequeño
        probarCaso("CASO 3: Arreglo pequeño de 5 elementos", new int[]{5, 2, 8, 1, 9});
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación realizada:
 *    Se implementó el esquema de partición de Lomuto adaptado al primer elemento como pivote
 *    (recorriendo de inicio + 1 hasta fin e intercambiando el pivote a su posición final al concluir).
 * 
 * 2. Justificación técnica:
 *    El esquema de Lomuto con pivote en la posición inicial es especialmente transparente y
 *    didáctico para observar cómo se acumulan los elementos estrictamente menores inmediatamente
 *    después del pivote, permitiendo extraer e imprimir claramente los subarreglos izquierdo y
 *    derecho antes de lanzar las llamadas recursivas independientes.
 * =========================================================================================
 */