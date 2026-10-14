/*
Prompt inicial utilizado (prompt_ej6.md):
Ejercicio 6


Actúa como un desarrollador Java experto y proporciona la implementación de dos métodos estáticos para verificar la igualdad entre dos arreglos, contemplando dos semánticas distintas:

1. Método 1 (Igualdad posicional): Determina si ambos arreglos contienen exactamente los mismos elementos en los mismos índices.
   - Firma: public static boolean sonIgualesPosicional(int[] a, int[] b)
   - Salida temprana (early exit):
     * Retorna true en O(1) si a == b (misma referencia).
     * Retorna false en O(1) si hay asimetría de nulos o si a.length != b.length.
     * Interrumpe el ciclo y retorna false en el instante exacto en que a[i] != b[i].

2. Método 2 (Igualdad de contenido / sin importar el orden): Determina si ambos arreglos contienen exactamente los mismos elementos con las mismas frecuencias de aparición, sin importar su disposición.
   - Firma: public static boolean sonIgualesContenido(int[] a, int[] b)
   - Salida temprana (early exit):
     * Mismas validaciones O(1) iniciales (referencia, nulos y longitud).
     * Implementa una estrategia eficiente (ej. ordenamiento previo o tabla de frecuencias) que interrumpa la ejecución apenas se detecte un elemento faltante o con frecuencia inconsistente.

Análisis comparativo de complejidad:
- Para cada método, detalla en una tabla la complejidad temporal (Big-O) justificando:
  * Mejor caso (escenarios donde resuelve en O(1)).
  * Caso promedio.
  * Peor caso (arreglos idénticos o discrepancia al final del análisis).
- Detalla la complejidad espacial auxiliar (Big-O) de cada alternativa, contrastando memoria vs. tiempo de ejecución.
*/

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Proveedor de algoritmos para determinar la igualdad entre arreglos bajo dos semánticas:
 * igualdad posicional estricta e igualdad de multiconjunto (independiente del orden).
 */
public class Ejercicio6 {

    /**
     * Determina si dos arreglos contienen exactamente los mismos elementos en las mismas posiciones.
     * Interrumpe la ejecución ante la primera discrepancia detectada (early exit).
     *
     * @param a Primer arreglo a comparar.
     * @param b Segundo arreglo a comparar.
     * @return {@code true} si ambos arreglos son idénticos posición a posición; {@code false} en caso contrario.
     */
    public static boolean sonIgualesPosicional(int[] a, int[] b) {
        // Optimización O(1): coincidencia inmediata si ambas referencias apuntan a la misma instancia en memoria
        if (a == b) {
            return true;
        }
        // Descarte O(1) si alguno es nulo o si las longitudes no coinciden
        if (a == null || b == null || a.length != b.length) {
            return false;
        }

        // Inspección lineal: retorna false en el instante en que un par de elementos difiere
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determina si dos arreglos contienen exactamente los mismos valores con idéntica frecuencia,
     * sin importar el orden de su disposición (equivalencia de multiconjunto).
     *
     * @param a Primer arreglo a evaluar.
     * @param b Segundo arreglo a evaluar.
     * @return {@code true} si ambos arreglos contienen exactamente los mismos elementos y frecuencias;
     *         {@code false} en caso contrario.
     */
    public static boolean sonIgualesContenido(int[] a, int[] b) {
        // Verificaciones preliminares en O(1) idénticas al caso posicional
        if (a == b) {
            return true;
        }
        if (a == null || b == null || a.length != b.length) {
            return false;
        }

        // Conteo de frecuencias de los elementos del primer arreglo
        Map<Integer, Integer> frecuencias = new HashMap<>();
        for (int num : a) {
            frecuencias.put(num, frecuencias.getOrDefault(num, 0) + 1);
        }

        // Consumo de frecuencias contra el segundo arreglo.
        // Dado que a.length == b.length, consumir todas las cuentas sin interrupción
        // asegura coincidencia total sin necesidad de una verificación posterior del mapa.
        for (int num : b) {
            Integer cuenta = frecuencias.get(num);
            if (cuenta == null || cuenta == 0) {
                return false;
            }
            frecuencias.put(num, cuenta - 1);
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("====================================================================");
        System.out.println("   DEMOSTRACIÓN: COMPARACIÓN DE VECTORES (POSICIONAL vs CONTENIDO)  ");
        System.out.println("====================================================================\n");

        int[] v1 = {1, 2, 3, 4};
        int[] v2 = {1, 2, 3, 4};
        int[] v3 = {4, 3, 2, 1};
        int[] v4 = {1, 2, 3};
        int[] v5 = {1, 2, 2, 4};

        System.out.println("v1: " + Arrays.toString(v1));
        System.out.println("v2 (idéntico a v1): " + Arrays.toString(v2));
        System.out.println("v3 (mismos datos, orden inverso): " + Arrays.toString(v3));
        System.out.println("v4 (menor longitud): " + Arrays.toString(v4));
        System.out.println("v5 (distintas frecuencias): " + Arrays.toString(v5));

        System.out.println("\n--- Pruebas de Comparación ---");
        System.out.printf("v1 vs v2 -> Posicional: %-5s | Por Contenido: %-5s\n",
                sonIgualesPosicional(v1, v2) ? "SÍ" : "NO", sonIgualesContenido(v1, v2) ? "SÍ" : "NO");

        System.out.printf("v1 vs v3 -> Posicional: %-5s | Por Contenido: %-5s\n",
                sonIgualesPosicional(v1, v3) ? "SÍ" : "NO", sonIgualesContenido(v1, v3) ? "SÍ" : "NO");

        System.out.printf("v1 vs v4 -> Posicional: %-5s | Por Contenido: %-5s\n",
                sonIgualesPosicional(v1, v4) ? "SÍ" : "NO", sonIgualesContenido(v1, v4) ? "SÍ" : "NO");

        System.out.printf("v1 vs v5 -> Posicional: %-5s | Por Contenido: %-5s\n",
                sonIgualesPosicional(v1, v5) ? "SÍ" : "NO", sonIgualesContenido(v1, v5) ? "SÍ" : "NO");

        System.out.printf("v1 vs null -> Posicional: %-5s | Por Contenido: %-5s\n",
                sonIgualesPosicional(v1, null) ? "SÍ" : "NO", sonIgualesContenido(v1, null) ? "SÍ" : "NO");

        System.out.printf("null vs null -> Posicional: %-5s | Por Contenido: %-5s\n",
                sonIgualesPosicional(null, null) ? "SÍ" : "NO", sonIgualesContenido(null, null) ? "SÍ" : "NO");

        System.out.println("\n====================================================================");
    }
}