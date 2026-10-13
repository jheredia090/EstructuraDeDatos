/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Quiero implementar en Java un programa que ordene un arreglo de nombres alfabéticamente 
 * utilizando el algoritmo Selection Sort.
 * 
 * Este problema requiere adaptar el algoritmo Selection Sort para operar sobre objetos de tipo 
 * String en lugar de tipos primitivos numéricos. 
 * La diferencia conceptual radica en que los textos no pueden compararse con operadores relacionales (< o >); 
 * en su lugar, se debe utilizar el método lexicográfico compareTo() de la clase String:
 * - Si textoA.compareTo(textoB) < 0, significa que textoA precede alfabéticamente a textoB en el diccionario.
 * - En cada pasada i, el algoritmo busca el índice del nombre que sea lexicográficamente menor 
 *   dentro de la región desordenada y realiza un único intercambio con la posición i.
 * 
 * Los datos que recibe el programa son el arreglo de nombres del enunciado: 
 * {"Lucia", "Ana", "Pedro", "Juan"}.
 * 
 * El programa debe mostrar por pantalla:
 * 1. El arreglo original de nombres.
 * 2. El detalle de cada pasada, indicando cuál fue el nombre lexicográficamente menor encontrado 
 *    y cómo queda el arreglo luego del intercambio.
 * 3. El arreglo final ordenado alfabéticamente.
 * 
 * Quiero que el código tenga comentarios explicando:
 * - Por qué no es posible usar operadores relacionales (<, >) en objetos String en Java.
 * - El funcionamiento y valor de retorno del método compareTo().
 * - La búsqueda del índice lexicográficamente menor en cada pasada.
 * - El intercambio de referencias de objetos String en memoria.
 * 
 * La verificación del resultado correcto debe comprobar que cada elemento preceda o sea igual 
 * al siguiente usando compareTo() <= 0.
 * 
 * También quiero probarlo con estos casos:
 * - Caso 1: Arreglo del enunciado {"Lucia", "Ana", "Pedro", "Juan"}.
 * - Caso 2: Nombres con letras iniciales compartidas {"Carlos", "Camila", "Cristian", "Carolina"}.
 * - Caso 3: Arreglo ya ordenado alfabéticamente {"Agustin", "Bernardo", "Cecilia", "Daniel"}.
 * =========================================================================================
 */

import java.util.Arrays;

/**
 * Ejercicio 4: Ordenamiento de nombres con Selection Sort.
 * Ilustra la adaptación del algoritmo de selección para ordenar objetos String
 * utilizando el orden lexicográfico definido por la interfaz Comparable (compareTo).
 */
public class Ej4 {

    /**
     * Ordena un arreglo de cadenas alfabéticamente utilizando Selection Sort.
     * Muestra el detalle del nombre menor hallado y el estado del arreglo en cada pasada.
     *
     * @param nombres Arreglo de objetos String a ordenar in-place.
     */
    public static void ordenarNombres(String[] nombres) {
        if (nombres == null || nombres.length < 2) {
            System.out.println("Arreglo sin elementos suficientes para ordenar.");
            return;
        }

        int n = nombres.length;
        System.out.println("Arreglo original: " + Arrays.toString(nombres));
        System.out.println("--------------------------------------------------------------------------------");

        for (int i = 0; i < n - 1; i++) {
            // Suponemos provisionalmente que el nombre menor está en la posición 'i'
            int indiceMinimo = i;

            // Búsqueda del menor lexicográfico en la zona desordenada [i + 1, n - 1]
            for (int j = i + 1; j < n; j++) {
                // En Java no se pueden usar '<' o '>' con objetos String.
                // compareTo() resta los códigos Unicode de los caracteres:
                // - Retorna < 0 si nombres[j] precede alfabéticamente a nombres[indiceMinimo].
                // - Retorna 0 si son idénticos.
                // - Retorna > 0 si nombres[j] es posterior en el abecedario.
                if (nombres[j].compareTo(nombres[indiceMinimo]) < 0) {
                    indiceMinimo = j; // Actualiza el puntero al menor alfabético
                }
            }

            // Intercambio de referencias de objetos String en memoria
            if (indiceMinimo != i) {
                String temp = nombres[i];
                nombres[i] = nombres[indiceMinimo];
                nombres[indiceMinimo] = temp;
            }

            System.out.printf("Pasada %d: Menor seleccionado = \"%-8s\" -> Estado: %s%n",
                    (i + 1), nombres[i], Arrays.toString(nombres));
        }

        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Arreglo final ordenado: " + Arrays.toString(nombres));
    }

    /**
     * Verifica programáticamente si el arreglo de nombres está ordenado alfabéticamente.
     *
     * @param arr Arreglo de cadenas a validar.
     * @return true si cada nombre precede o iguala al siguiente; false en caso contrario.
     */
    public static boolean estaOrdenadoAlfabeticamente(String[] arr) {
        if (arr == null || arr.length < 2) {
            return true;
        }
        for (int k = 0; k < arr.length - 1; k++) {
            // Si el actual es mayor lexicográficamente que el siguiente, el orden está roto
            if (arr[k].compareTo(arr[k + 1]) > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Ejecuta y prueba los tres casos estipulados en el prompt.
     */
    public static void probarCaso(String titulo, String[] entrada) {
        System.out.println("================================================================================");
        System.out.println(titulo);
        System.out.println("================================================================================");

        String[] copia = entrada.clone();
        ordenarNombres(copia);

        boolean ordenOk = estaOrdenadoAlfabeticamente(copia);
        System.out.println("Verificación de orden lexicográfico: " + (ordenOk ? "CORRECTO" : "FALLÓ"));
        System.out.println();
    }

    public static void main(String[] args) {
        // Caso 1: Nombres del enunciado
        probarCaso("CASO 1: Nombres del enunciado",
                new String[]{"Lucia", "Ana", "Pedro", "Juan"});

        // Caso 2: Nombres con prefijos comunes
        probarCaso("CASO 2: Nombres con prefijo común ('C')",
                new String[]{"Carlos", "Camila", "Cristian", "Carolina"});

        // Caso 3: Ya ordenados
        probarCaso("CASO 3: Nombres ya ordenados",
                new String[]{"Agustin", "Bernardo", "Cecilia", "Daniel"});
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación realizada:
 *    Se enfatizó el uso de 'compareTo()' estándar de String (orden lexicográfico basado en
 *    tabla ASCII/Unicode) y se conservó el intercambio de referencias puras.
 * 
 * 2. Justificación técnica:
 *    A diferencia de los tipos primitivos numéricos (como int o double), donde los valores se
 *    almacenan directamente en las celdas del arreglo, en un arreglo 'String[]' lo que se
 *    almacena e intercambia son referencias a objetos residentes en el Heap (o String Pool).
 *    El método 'compareTo()' realiza una comparación carácter por carácter hasta encontrar la
 *    primera discrepancia o determinar el fin de la cadena, haciendo que Selection Sort
 *    conserve su complejidad O(n^2) comparaciones multiplicada por la longitud promedio
 *    de las cadenas examinadas (O(L * n^2)).
 * =========================================================================================
 */