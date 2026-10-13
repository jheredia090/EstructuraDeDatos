/**
 * Utilidad para el cómputo acumulativo de elementos en matrices bidimensionales
 * e instrumentación de operaciones elementales ejecutadas.
 */
public class Ejercicio8 {

    /**
     * Representación inmutable del resultado del procesamiento de la matriz.
     *
     * @param suma Valor acumulado total de los elementos de la matriz.
     * @param operaciones Cantidad total de accesos y adiciones aritméticas ejecutadas.
     */
    public record ResultadoMatriz(long suma, long operaciones) {}

    /**
     * Calcula la suma de todos los elementos presentes en una matriz bidimensional
     * mediante un recorrido por filas (row-major order) y contabiliza el costo operativo.
     *
     * @param matriz Matriz de enteros a procesar. Puede ser nula, vacía o irregular (jagged array).
     * @return Instancia de {@link ResultadoMatriz} con la suma total y el conteo de operaciones.
     */
    public static ResultadoMatriz sumarElementos(int[][] matriz) {
        // Manejo de caso borde: matriz nula o sin filas definidas
        if (matriz == null || matriz.length == 0) {
            return new ResultadoMatriz(0, 0);
        }

        long suma = 0;
        long operaciones = 0;

        // Recorrido externo por filas (row-major) para optimizar la localidad espacial en caché
        for (int i = 0; i < matriz.length; i++) {
            int[] fila = matriz[i];
            // Protección contra referencias nulas en matrices irregulares (jagged arrays)
            if (fila == null) {
                continue;
            }

            // Recorrido secuencial contiguo sobre el arreglo de la fila actual
            for (int j = 0; j < fila.length; j++) {
                int valor = fila[j]; // 1 operación: acceso/lectura
                suma += valor;       // 1 operación: suma aritmética
                operaciones += 2;
            }
        }

        return new ResultadoMatriz(suma, operaciones);
    }
}