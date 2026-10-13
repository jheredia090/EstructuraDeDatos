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