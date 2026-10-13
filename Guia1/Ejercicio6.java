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
}