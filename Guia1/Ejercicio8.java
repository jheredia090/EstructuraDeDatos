/*
Prompt inicial utilizado (prompt_ej8.md):
Actúa como un desarrollador Java experto y proporciona la implementación de un algoritmo para calcular la suma de todos los elementos de una matriz de enteros, instrumentando la medición de operaciones:

Especificaciones técnicas:
- Encapsulación de salida:
  * Define un record inmutable para entregar el resultado:
    public record ResultadoMatriz(long suma, long operaciones) {}
- Patrón de recorrido:
  * Itera estrictamente por filas (row-major order) mediante ciclos anidados.
  * Justifica técnicamente por qué este patrón optimiza la tasa de aciertos de caché (cache hits) en la JVM frente a un recorrido por columnas.
- Contabilización de operaciones:
  * Incrementa el contador por cada lectura/acceso a celda y por cada suma aritmética ejecutada.
- Casos límite y robustez:
  * Si la matriz es nula o tiene longitud 0, retorna ResultadoMatriz(0, 0).
  * Soporta matrices irregulares (jagged arrays) y valida filas individuales que puedan ser nulas.

Análisis asintótico y validación:
- Especifica la complejidad temporal en notación Big-O en términos de N (filas) y M (columnas promedio).
- Especifica la complejidad espacial auxiliar en notación Big-O.
- Demuestra matemáticamente cómo la cantidad final de operaciones contabilizadas se correlaciona de forma exacta con la función de complejidad temporal O(N * M).
*/

import java.util.Arrays;

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

    public static void main(String[] args) {
        System.out.println("====================================================================");
        System.out.println("   DEMOSTRACIÓN: SUMA DE MATRIZ Y CONTEO DE OPERACIONES (EJ 8)      ");
        System.out.println("====================================================================\n");

        // 1. Matriz rectangular normal
        int[][] matriz = {
            {1, 2, 3},
            {4, 5, 6}
        };
        System.out.println("1. Matriz rectangular 2x3: " + Arrays.deepToString(matriz));
        ResultadoMatriz res1 = sumarElementos(matriz);
        System.out.println("   Suma acumulada      : " + res1.suma());
        System.out.println("   Operaciones medidas : " + res1.operaciones() + " (6 lecturas + 6 adiciones)\n");

        // 2. Matriz irregular (jagged array con fila nula)
        int[][] irregular = {
            {10, 20},
            null,
            {30, 40, 50}
        };
        System.out.println("2. Matriz irregular con fila nula: " + Arrays.deepToString(irregular));
        ResultadoMatriz res2 = sumarElementos(irregular);
        System.out.println("   Suma acumulada      : " + res2.suma());
        System.out.println("   Operaciones medidas : " + res2.operaciones() + "\n");

        // 3. Casos límite
        System.out.println("3. Casos límite:");
        ResultadoMatriz resVacia = sumarElementos(new int[0][0]);
        System.out.println("   Matriz vacía [0][0] -> Suma: " + resVacia.suma() + ", Operaciones: " + resVacia.operaciones());

        ResultadoMatriz resNula = sumarElementos(null);
        System.out.println("   Matriz nula         -> Suma: " + resNula.suma() + ", Operaciones: " + resNula.operaciones());

        System.out.println("\n====================================================================");
    }
}