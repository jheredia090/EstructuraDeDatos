/**
 * Ejercicio 2 - Guía 1: Búsqueda Secuencial en Vector Desordenado
 *
 * JUSTIFICACIÓN:
 * En un vector desordenado sin estructura auxiliar, la búsqueda secuencial
 * es el único método válido. La búsqueda binaria requiere orden (O(log n)
 * pero no aplica aquí). O(1) directo requiere hash o indice = valor.
 * La secuencial recorre posición por posición desde el inicio.
 *
 * ANÁLISIS DE COMPLEJIDAD:
 * - Mejor caso:  el elemento está en la posición 0 → O(1) comparación.
 * - Peor caso:   el elemento no existe o está al final → O(n) comparaciones.
 * - Caso promedio: posición equiprobable → (n+1)/2 ≈ O(n) comparaciones.
 */
public class Ejercicio2 {

    /**
     * Búsqueda secuencial en vector desordenado.
     *
     * @param vector   arreglo de enteros (desordenado)
     * @param elemento valor a buscar
     * @return cantidad de posiciones recorridas
     */
    public static int busquedaSecuencial(int[] vector, int elemento) {
        int posicionesRecorridas = 0;

        for (int i = 0; i < vector.length; i++) {
            posicionesRecorridas++;
            if (vector[i] == elemento) {
                return posicionesRecorridas;
            }
        }

        return posicionesRecorridas;
    }

    public static void main(String[] args) {
        int[] vector = {15, 3, 8, 42, 7, 21, 9, 33};

        // Caso 1: encontrado al inicio
        int buscar1 = 15;
        int recorridas1 = busquedaSecuencial(vector, buscar1);
        System.out.println("Elemento " + buscar1 + " → Encontrado. Posiciones recorridas: " + recorridas1);

        // Caso 2: encontrado al final
        int buscar2 = 33;
        int recorridas2 = busquedaSecuencial(vector, buscar2);
        System.out.println("Elemento " + buscar2 + " → Encontrado. Posiciones recorridas: " + recorridas2);

        // Caso 3: no encontrado
        int buscar3 = 100;
        int recorridas3 = busquedaSecuencial(vector, buscar3);
        System.out.println("Elemento " + buscar3 + " → No encontrado. Posiciones recorridas: " + recorridas3);
    }
}