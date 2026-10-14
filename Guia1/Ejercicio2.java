/*
Prompt inicial utilizado (prompt_ej2.md):
Ejercicio 2

Implementá en Java un algoritmo de búsqueda secuencial que dado un vector
de enteros desordenado y un elemento a buscar, recorra el vector desde la
primera posición hasta encontrar el elemento o determinar que no existe.

Restricciones:
- El vector es de enteros y NO está ordenado.
- Se debe usar búsqueda secuencial (recorrido linea de frente, posición por posición).
- No se permite reordenar el vector ni usar estructuras auxiliares (hash, árboles, etc.).

Requisitos del programa:
1. Mostrar por pantalla cuántas posiciones se recorrieron hasta encontrar
   el elemento o hasta determinar que no existe.
2. Informar si el elemento fue encontrado o no.
3. Incluir un análisis justificando por qué la búsqueda secuencial es el método
   adecuado para un vector desordenado (vs búsqueda binaria u otros).
4. Analizar la complejidad de los tres casos:
   - Mejor caso (elemento en la primera posición) → O(1)
   - Peor caso (elemento no existe o está en la última posición) → O(n)
   - Caso promedio (posición equiprobable) → O(n/2) ≈ O(n)

Estructura esperada:
- Método que reciba el vector y el elemento a buscar, devolviendo la cantidad
  de posiciones recorridas.
- Método main que pruebe con al menos 3 casos: encontrado al inicio, encontrado
  al final, y no encontrado.
- Justificación del método y análisis de complejidad en comentarios o aparte.
*/

/**
 * Ejercicio 2 - Guía 1: Búsqueda Secuencial en Vector Desordenado
 *
 * JUSTIFICACIÓN:
 * En un vector desordenado sin estructura auxiliar, la búsqueda secuencial
 * es el único método válido. La búsqueda binaria requiere orden (O(log n)
 * pero no aplica aquí). O(1) directo requiere hash o índice = valor.
 * La búsqueda secuencial recorre posición por posición desde el inicio.
 *
 * ANÁLISIS DE COMPLEJIDAD:
 * - Mejor caso:  el elemento está en la posición 0 → O(1) comparación.
 * - Peor caso:   el elemento no existe o está al final → O(n) comparaciones.
 * - Caso promedio: posición equiprobable → (n+1)/2 ≈ O(n) comparaciones.
 */
public class Ejercicio2 {

    /**
     * Representación inmutable del resultado de la búsqueda secuencial.
     *
     * @param encontrado Indica si el elemento está presente en el vector.
     * @param posicion Índice donde se encontró (-1 si no existe).
     * @param posicionesRecorridas Cantidad de posiciones inspeccionadas.
     */
    public record ResultadoBusqueda(boolean encontrado, int posicion, int posicionesRecorridas) {}

    /**
     * Realiza la búsqueda secuencial retornando un objeto detallado con el diagnóstico.
     *
     * @param vector Arreglo de enteros desordenado.
     * @param elemento Valor a buscar.
     * @return Instancia de {@link ResultadoBusqueda}.
     * @throws IllegalArgumentException si el vector recibido es nulo.
     */
    public static ResultadoBusqueda buscar(int[] vector, int elemento) {
        if (vector == null) {
            throw new IllegalArgumentException("El vector no puede ser nulo.");
        }

        int posicionesRecorridas = 0;

        for (int i = 0; i < vector.length; i++) {
            posicionesRecorridas++;
            if (vector[i] == elemento) {
                return new ResultadoBusqueda(true, i, posicionesRecorridas);
            }
        }

        return new ResultadoBusqueda(false, -1, posicionesRecorridas);
    }

    /**
     * Búsqueda secuencial en vector desordenado que informa el resultado y retorna
     * la cantidad de posiciones recorridas.
     *
     * @param vector arreglo de enteros (desordenado)
     * @param elemento valor a buscar
     * @return cantidad de posiciones recorridas
     * @throws IllegalArgumentException si el vector recibido es nulo
     */
    public static int busquedaSecuencial(int[] vector, int elemento) {
        ResultadoBusqueda resultado = buscar(vector, elemento);

        if (resultado.encontrado()) {
            System.out.println("Elemento " + elemento + " → ENCONTRADO en posición "
                    + resultado.posicion() + ". Posiciones recorridas: " + resultado.posicionesRecorridas());
        } else {
            System.out.println("Elemento " + elemento + " → NO ENCONTRADO. Posiciones recorridas: "
                    + resultado.posicionesRecorridas());
        }

        return resultado.posicionesRecorridas();
    }

    public static void main(String[] args) {
        System.out.println("====================================================================");
        System.out.println("   DEMOSTRACIÓN: BÚSQUEDA SECUENCIAL EN VECTOR DESORDENADO (EJ 2)   ");
        System.out.println("====================================================================\n");

        int[] vector = {15, 3, 8, 42, 7, 21, 9, 33};

        // Caso 1: encontrado al inicio (Mejor caso: O(1))
        System.out.println("1. Caso encontrado al inicio (Mejor caso):");
        busquedaSecuencial(vector, 15);

        // Caso 2: encontrado en el medio
        System.out.println("\n2. Caso encontrado en el medio:");
        busquedaSecuencial(vector, 42);

        // Caso 3: encontrado al final (Peor caso existente: O(n))
        System.out.println("\n3. Caso encontrado al final:");
        busquedaSecuencial(vector, 33);

        // Caso 4: no encontrado (Peor caso absoluto: O(n))
        System.out.println("\n4. Caso no encontrado:");
        busquedaSecuencial(vector, 100);

        // Caso 5: vector vacío
        System.out.println("\n5. Caso vector vacío:");
        busquedaSecuencial(new int[]{}, 5);

        // Caso 6: validación de caso límite con vector nulo
        System.out.println("\n6. Validación de vector nulo:");
        try {
            busquedaSecuencial(null, 10);
        } catch (IllegalArgumentException e) {
            System.out.println("Excepción capturada con éxito: " + e.getMessage());
        }

        System.out.println("\n====================================================================");
    }
}