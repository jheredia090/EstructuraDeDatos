/*
Prompt inicial utilizado (prompt_ej3.md):
Ejercicio 3


Implementá en Java un algoritmo de búsqueda binaria que dado un vector de
enteros ORDENADO de forma ascendente y un elemento a buscar, encuentre el
elemento dividiendo repetidamente el intervalo de búsqueda a la mitad.

Restricciones:
- El vector está ORDENADO de forma ascendente (requisito indispensable).
- Se debe usar búsqueda binaria iterativa con las variables low, high y mid.
- No se permite usar Arrays.binarySearch() ni estructuras auxiliares.

Requisitos del programa:
1. Mostrar paso a paso cómo evolucionan las variables de búsqueda:
   - En cada iteración imprimir: low, high, mid, el valor en la posición mid
     y si ese valor es menor, mayor o igual al elemento buscado.
2. Informar si el elemento fue encontrado y en qué posición, o si no existe.
3. Incluir un análisis justificando por qué la búsqueda binaria es más
   eficiente que la lineal cuando los datos están ordenados.
4. Analizar la complejidad de los tres casos:
   - Mejor caso (elemento en el medio en la primera iteración) → O(1)
   - Peor caso (elemento no existe o está en un extremo) → O(log n)
   - Caso promedio → O(log n)

Estructura esperada:
- Método que reciba el vector ordenado y el elemento, devuelva la posición
  encontrada o -1 si no existe, e imprima el paso a paso de las variables.
- Método main que pruebe con al menos 3 casos: encontrado a la izquierda,
  encontrado a la derecha, y no encontrado.
- Justificación del método y análisis de complejidad en comentarios o aparte.
*/

/**
 * Ejercicio 3 - Guía 1: Búsqueda Binaria en Vector Ordenado
 *
 * JUSTIFICACIÓN:
 * El vector está ordenado ascendentemente. La búsqueda binaria aprovecha esa
 * propiedad: en cada iteración compara el elemento del medio (mid) con el
 * buscado y descarta la mitad del intervalo que no puede contenerlo.
 * La búsqueda lineal (O(n)) desperdiciaría esa información recorriendo todo.
 *
 * ANÁLISIS DE COMPLEJIDAD:
 * - Mejor caso:    el elemento está en mid en la primera iteración → O(1).
 * - Peor caso:     el elemento no existe o está en un extremo → O(log n).
 * - Caso promedio: → O(log n).
 */
public class Ejercicio3 {

    /**
     * Búsqueda binaria iterativa con seguimiento paso a paso.
     *
     * @param vector   Arreglo de enteros ORDENADO ascendentemente.
     * @param elemento Valor a buscar.
     * @return Posición del elemento (0 a n-1), o -1 si no existe.
     * @throws IllegalArgumentException si el vector recibido es nulo.
     */
    public static int busquedaBinaria(int[] vector, int elemento) {
        if (vector == null) {
            throw new IllegalArgumentException("El vector no puede ser nulo.");
        }

        int low = 0;
        int high = vector.length - 1;
        int iteracion = 0;

        while (low <= high) {
            int mid = low + (high - low) / 2; // Evita desbordamiento aritmético frente a (low + high) / 2
            iteracion++;

            System.out.println("Iteración " + iteracion
                    + " → low=" + low + ", high=" + high
                    + ", mid=" + mid + ", valor=" + vector[mid]);

            if (vector[mid] == elemento) {
                System.out.println("  → " + vector[mid] + " == " + elemento
                        + " → ENCONTRADO en posición " + mid);
                return mid;
            } else if (vector[mid] < elemento) {
                System.out.println("  → " + vector[mid] + " < " + elemento
                        + " → descarto mitad izquierda, low = " + (mid + 1));
                low = mid + 1;
            } else {
                System.out.println("  → " + vector[mid] + " > " + elemento
                        + " → descarto mitad derecha, high = " + (mid - 1));
                high = mid - 1;
            }
        }

        System.out.println("  → No encontrado (low > high)");
        return -1;
    }

    public static void main(String[] args) {
        System.out.println("====================================================================");
        System.out.println("   DEMOSTRACIÓN: BÚSQUEDA BINARIA EN VECTOR ORDENADO (EJ 3)         ");
        System.out.println("====================================================================\n");

        int[] vector = {2, 5, 8, 12, 16, 23, 38, 56, 72, 91};

        System.out.println("=== Caso 1: encontrado a la izquierda (5) ===");
        busquedaBinaria(vector, 5);

        System.out.println("\n=== Caso 2: encontrado a la derecha (91) ===");
        busquedaBinaria(vector, 91);

        System.out.println("\n=== Caso 3: encontrado en el medio (16) ===");
        busquedaBinaria(vector, 16);

        System.out.println("\n=== Caso 4: no encontrado (50) ===");
        busquedaBinaria(vector, 50);

        System.out.println("\n=== Caso 5: vector vacío ===");
        busquedaBinaria(new int[]{}, 10);

        System.out.println("\n=== Caso 6: validación de vector nulo ===");
        try {
            busquedaBinaria(null, 10);
        } catch (IllegalArgumentException e) {
            System.out.println("Excepción capturada con éxito: " + e.getMessage());
        }

        System.out.println("\n====================================================================");
    }
}
