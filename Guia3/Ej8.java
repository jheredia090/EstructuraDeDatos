/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Quiero implementar en Java el algoritmo MergeSort sobre un arreglo de números enteros, 
 * mostrando paso a paso el proceso de división recursiva, la condición de corte y la fusión 
 * ordenada (merge).
 * 
 * Este problema se resuelve mediante MergeSort porque aplica estrictamente el paradigma 
 * Divide y Vencerás con una garantía temporal de O(n log n):
 * - Dividir: Calcula el punto medio (inicio + fin) / 2 y parte recursivamente el arreglo en dos 
 *   mitades simétricas (izquierda y derecha). En esta fase no se ordena nada, solo se reduce el 
 *   tamaño del problema.
 * - Condición de corte: Si inicio >= fin, el subarreglo tiene 1 solo elemento y se considera 
 *   ordenado por definición trivial, deteniendo el descenso en el call stack.
 * - Fusionar (Merge): Es la fase activa de ordenamiento. Compara los elementos menores entre ambas 
 *   mitades ya ordenadas y los combina de forma intercalada en un arreglo auxiliar antes de 
 *   copiarlos de regreso al arreglo principal.
 * 
 * Los datos que recibe el programa son un arreglo de enteros desordenados, por ejemplo: 
 * {38, 27, 43, 3, 9, 82, 10}.
 * 
 * El programa debe mostrar por pantalla:
 * 1. El arreglo original.
 * 2. Cada paso de división: indicando el subarreglo actual y cómo se parte en [inicio..medio] 
 *    y [medio+1..fin].
 * 3. La condición de corte cuando se llega a subarreglos de 1 solo elemento.
 * 4. Cada paso de fusión (merge): mostrando los dos subarreglos que se fusionan y el tramo 
 *    resultante ya ordenado.
 * 5. El arreglo final completamente ordenado.
 * 
 * Quiero que el código tenga comentarios explicando:
 * - El cálculo del índice medio: int medio = inicio + (fin - inicio) / 2 para evitar overflow.
 * - Las dos llamadas recursivas sobre la mitad izquierda y derecha.
 * - El método merge con los punteros i, j, k y el arreglo auxiliar.
 * - La distinción teórica: "Dividir desciende sin ordenar; Fusionar asciende ordenando".
 * 
 * La verificación del resultado correcto debe comprobar programáticamente que arr[k] <= arr[k+1] 
 * para todos los índices.
 * 
 * También quiero probarlo con estos casos:
 * - Caso 1: Arreglo del enunciado {38, 27, 43, 3, 9, 82, 10} (longitud impar, 7 elementos).
 * - Caso 2: Arreglo en orden inverso {8, 7, 6, 5, 4, 3, 2, 1} (longitud potencia de 2, 8 elementos).
 * - Caso 3: Arreglo de 2 elementos {99, 11}.
 * =========================================================================================
 */

import java.util.Arrays;

/**
 * Ejercicio 8: MergeSort paso a paso.
 * Demuestra de forma visual e intuitiva la separación de fases del algoritmo:
 * 1. Fase Top-Down (Dividir hasta la condición de corte).
 * 2. Fase Bottom-Up (Fusionar e intercalar ordenadamente).
 */
public class Ej8 {

    /**
     * Método público (wrapper) para iniciar el ordenamiento de MergeSort con trazabilidad.
     *
     * @param arr Arreglo de enteros a ordenar.
     */
    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            System.out.println("Arreglo sin elementos suficientes para ordenar.");
            return;
        }

        System.out.println("Arreglo original: " + Arrays.toString(arr));
        System.out.println("--------------------------------------------------------------------------------");
        mergeSortRecursivo(arr, 0, arr.length - 1, 1);
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Arreglo final ordenado: " + Arrays.toString(arr));
    }

    /**
     * Función recursiva que ejecuta la división y convoca la fusión al volver de la recursión.
     *
     * @param arr Arreglo compartido en memoria.
     * @param inicio Índice inicial del tramo.
     * @param fin Índice final del tramo.
     * @param nivel Profundidad en el árbol de llamadas para fines visuales.
     */
    private static void mergeSortRecursivo(int[] arr, int inicio, int fin, int nivel) {
        // CONDICIÓN DE CORTE (Caso base):
        // Si el subarreglo tiene 1 solo elemento (inicio == fin), ya está ordenado trivialmente.
        if (inicio >= fin) {
            System.out.printf("%s[Corte Base] Subarreglo de 1 elemento: [%d]%n",
                    indentacion(nivel), arr[inicio]);
            return; // Se detiene la división y comienza el retorno en la pila
        }

        // FASE 1: DIVIDIR (No ordena nada; solo particiona a la mitad)
        // Cálculo seguro del punto medio para evitar desbordamiento aritmético de enteros
        int medio = inicio + (fin - inicio) / 2;

        int[] tramoActual = Arrays.copyOfRange(arr, inicio, fin + 1);
        int[] parteIzq = Arrays.copyOfRange(arr, inicio, medio + 1);
        int[] parteDer = Arrays.copyOfRange(arr, medio + 1, fin + 1);

        System.out.printf("%s(Dividir) %-18s -> Izq: %-12s | Der: %s%n",
                indentacion(nivel), Arrays.toString(tramoActual),
                Arrays.toString(parteIzq), Arrays.toString(parteDer));

        // Llamadas recursivas sobre ambas mitades
        mergeSortRecursivo(arr, inicio, medio, nivel + 1);      // Mitad izquierda
        mergeSortRecursivo(arr, medio + 1, fin, nivel + 1);  // Mitad derecha

        // FASE 2: FUSIONAR (Aquí ocurre todo el ordenamiento)
        // Al regresar de las llamadas recursivas, ambas mitades están ordenadas internamente
        fusionar(arr, inicio, medio, fin, nivel);
    }

    /**
     * Intercala y fusiona dos mitades contiguas ya ordenadas: [inicio..medio] y [medio+1..fin].
     */
    private static void fusionar(int[] arr, int inicio, int medio, int fin, int nivel) {
        int tamanoIzq = medio - inicio + 1;
        int tamanoDer = fin - medio;

        // Arreglos auxiliares temporales para alojar las dos mitades
        int[] izquierda = new int[tamanoIzq];
        int[] derecha = new int[tamanoDer];

        for (int i = 0; i < tamanoIzq; i++) {
            izquierda[i] = arr[inicio + i];
        }
        for (int j = 0; j < tamanoDer; j++) {
            derecha[j] = arr[medio + 1 + j];
        }

        // Punteros de intercalación:
        // i -> recorre 'izquierda'
        // j -> recorre 'derecha'
        // k -> apunta a la posición de inserción en el arreglo principal 'arr'
        int i = 0, j = 0, k = inicio;

        // Compara los elementos menores entre ambas mitades y los coloca en el arreglo principal
        while (i < tamanoIzq && j < tamanoDer) {
            if (izquierda[i] <= derecha[j]) {
                arr[k] = izquierda[i];
                i++;
            } else {
                arr[k] = derecha[j];
                j++;
            }
            k++;
        }

        // Vuelca los elementos remanentes de la mitad izquierda (si quedan)
        while (i < tamanoIzq) {
            arr[k] = izquierda[i];
            i++;
            k++;
        }

        // Vuelca los elementos remanentes de la mitad derecha (si quedan)
        while (j < tamanoDer) {
            arr[k] = derecha[j];
            j++;
            k++;
        }

        int[] fusionado = Arrays.copyOfRange(arr, inicio, fin + 1);
        System.out.printf("%s[Fusionar] %-10s + %-10s -> Resultante: %s%n",
                indentacion(nivel), Arrays.toString(izquierda),
                Arrays.toString(derecha), Arrays.toString(fusionado));
    }

    private static String indentacion(int nivel) {
        return "  ".repeat(Math.max(0, nivel - 1));
    }

    /**
     * Verifica que el arreglo esté ordenado ascendentemente.
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
        mergeSort(copia);

        boolean ordenOk = estaOrdenado(copia);
        System.out.println("Verificación de orden: " + (ordenOk ? "CORRECTO" : "FALLÓ"));
        System.out.println();
    }

    public static void main(String[] args) {
        // Caso 1: Arreglo de 7 elementos (longitud impar)
        probarCaso("CASO 1: Arreglo de longitud impar {38, 27, 43, 3, 9, 82, 10}",
                new int[]{38, 27, 43, 3, 9, 82, 10});

        // Caso 2: Potencia de 2 en orden inverso
        probarCaso("CASO 2: Potencia de 2 en orden inverso {8, 7, 6, 5, 4, 3, 2, 1}",
                new int[]{8, 7, 6, 5, 4, 3, 2, 1});

        // Caso 3: Dos elementos
        probarCaso("CASO 3: Arreglo de 2 elementos {99, 11}",
                new int[]{99, 11});
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación realizada:
 *    Se implementó un formato de traza indentado por niveles que diferencia explícitamente
 *    la fase de bajada ('Dividir') de la fase de subida ('Fusionar').
 * 
 * 2. Justificación técnica:
 *    Muchos estudiantes confunden conceptualmente en qué momento se produce el ordenamiento
 *    en MergeSort. La visualización escalonada demuestra que durante la partición no se altera
 *    el orden de los elementos; el ordenamiento ocurre estrictamente durante la fase de
 *    fusión (merge) cuando los subarreglos regresan desde las hojas del árbol recursivo hacia
 *    la raíz, justificando empíricamente su costo espacial O(n) de memoria auxiliar y su
 *    complejidad temporal invariante O(n log n).
 * =========================================================================================
 */