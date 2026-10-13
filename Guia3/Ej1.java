/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Quiero implementar en Java un programa que ordene un arreglo de números enteros utilizando 
 * el algoritmo Bubble Sort (Ordenamiento Burbuja) mostrando su evolución paso a paso.
 * 
 * Este problema se resuelve mediante Bubble Sort porque el algoritmo compara sucesivamente 
 * pares de elementos adyacentes e intercambia sus posiciones si están en el orden incorrecto. 
 * Como consecuencia directa de estas comparaciones locales, en cada pasada completa el elemento 
 * de mayor valor del tramo desordenado se va desplazando ("flota") de forma determinista hacia 
 * el final del arreglo, ocupando su posición definitiva.
 * 
 * Los datos que recibe el programa son un arreglo de enteros desordenados, por ejemplo: 
 * {64, 34, 25, 12, 22, 11, 90}.
 * 
 * El programa debe mostrar por pantalla:
 * 1. El arreglo original antes de comenzar el proceso.
 * 2. El estado del arreglo después de cada pasada del ciclo externo, indicando el número de 
 *    pasada y destacando cómo el elemento mayor de esa pasada quedó fijado al final.
 * 3. El arreglo final completamente ordenado.
 * 
 * Quiero que el código tenga comentarios explicando:
 * - La comparación de vecinos adyacentes arr[j] > arr[j+1].
 * - La operación de intercambio (swap) mediante una variable temporal.
 * - Por qué el límite del ciclo interno disminuye en cada pasada (n - 1 - i) al estar los 
 *   elementos finales ya ordenados.
 * 
 * La verificación del resultado correcto debe realizarse comprobando programáticamente que 
 * cada elemento sea menor o igual al siguiente (arr[k] <= arr[k+1]) para todos los índices válidos.
 * 
 * También quiero probarlo con estos casos:
 * - Caso 1: Arreglo desordenado estándar {64, 34, 25, 12, 22, 11, 90}.
 * - Caso 2: Arreglo ya ordenado {10, 20, 30, 40, 50} (para observar las pasadas).
 * - Caso 3: Arreglo en orden inverso {5, 4, 3, 2, 1} (peor caso).
 * =========================================================================================
 */

import java.util.Arrays;

/**
 * Ejercicio 1: Ordenamiento Burbuja paso a paso.
 * Demuestra la mecánica de propagación de elementos mayores hacia el extremo derecho
 * mediante comparaciones locales e intercambios adyacentes.
 */
public class Ej1 {

    /**
     * Ordena un arreglo de enteros utilizando Bubble Sort e imprime la traza de cada pasada.
     *
     * @param arr Arreglo de números enteros a ordenar.
     */
    public static void ordenarBurbujaConPasadas(int[] arr) {
        if (arr == null || arr.length < 2) {
            System.out.println("Arreglo sin elementos suficientes para ordenar.");
            return;
        }

        int n = arr.length;
        System.out.println("Estado inicial: " + Arrays.toString(arr));
        System.out.println("--------------------------------------------------");

        // Ciclo externo: cada iteración 'i' asegura que un elemento mayor queda fijado al final
        for (int i = 0; i < n - 1; i++) {

            // Ciclo interno: compara vecinos adyacentes.
            // Límite (n - 1 - i): los últimos 'i' elementos ya están en su posición definitiva,
            // por lo que no es necesario volver a compararlos.
            for (int j = 0; j < n - 1 - i; j++) {

                // Comparación de pares adyacentes: si el elemento de la izquierda supera al derecho
                if (arr[j] > arr[j + 1]) {
                    // Intercambio (swap) utilizando una variable temporal
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }

            // Muestra el estado del arreglo al concluir la pasada i + 1
            // El elemento en la posición (n - 1 - i) ya quedó definitivamente ubicado
            System.out.printf("Pasada %d: %-30s -> Fijado al final: %d%n",
                    (i + 1), Arrays.toString(arr), arr[n - 1 - i]);
        }

        System.out.println("--------------------------------------------------");
        System.out.println("Arreglo final ordenado: " + Arrays.toString(arr));
    }

    /**
     * Verifica programáticamente si el arreglo se encuentra ordenado ascendentemente.
     *
     * @param arr Arreglo a verificar.
     * @return true si arr[k] <= arr[k+1] para todo índice k; false en caso contrario.
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
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("CASO 1: Arreglo desordenado estándar");
        System.out.println("==================================================");
        int[] caso1 = {64, 34, 25, 12, 22, 11, 90};
        ordenarBurbujaConPasadas(caso1);
        System.out.println("Verificación de orden: " + (estaOrdenado(caso1) ? "CORRECTO" : "FALLÓ") + "\n");

        System.out.println("==================================================");
        System.out.println("CASO 2: Arreglo ya ordenado");
        System.out.println("==================================================");
        int[] caso2 = {10, 20, 30, 40, 50};
        ordenarBurbujaConPasadas(caso2);
        System.out.println("Verificación de orden: " + (estaOrdenado(caso2) ? "CORRECTO" : "FALLÓ") + "\n");

        System.out.println("==================================================");
        System.out.println("CASO 3: Arreglo en orden inverso (peor caso)");
        System.out.println("==================================================");
        int[] caso3 = {5, 4, 3, 2, 1};
        ordenarBurbujaConPasadas(caso3);
        System.out.println("Verificación de orden: " + (estaOrdenado(caso3) ? "CORRECTO" : "FALLÓ"));
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación realizada:
 *    Se implementó la versión canónica estricta de Bubble Sort sin bandera de corte temprano
 *    (early exit) en esta primera entrega.
 * 
 * 2. Justificación técnica:
 *    El objetivo pedagógico central del Ejercicio 1 es evidenciar visualmente cómo en cada una
 *    de las (n - 1) pasadas el elemento mayor de la región no ordenada se desplaza de forma
 *    continua hasta su posición terminal en 'arr[n - 1 - i]'. Mantener las pasadas completas
 *    permite contrastar con claridad la cantidad fija de etapas antes de introducir
 *    optimizaciones en los ejercicios comparativos posteriores.
 * =========================================================================================
 */