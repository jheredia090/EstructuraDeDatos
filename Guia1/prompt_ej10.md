Actúa como un desarrollador Java experto y proporciona la implementación del algoritmo Bubble Sort (ordenamiento por burbuja) en Java, trabajando sobre una copia y midiendo su costo computacional:

Especificaciones técnicas:
- Enfoque no destructivo: no modifica el arreglo original; clona o instancia una copia del arreglo recibido para realizar el ordenamiento sobre ella.
- Encapsulación de salida: define un record inmutable para entregar el arreglo ordenado junto con sus métricas:
  public record ResultadoBubbleSort(int[] arregloOrdenado, long comparaciones, long intercambios) {}
- Optimización algorítmica:
  * Reduce el límite superior del ciclo interno en cada iteración (n - 1 - i).
  * Incluye una bandera de corte temprano (early exit) que termine el proceso si una pasada completa transcurre sin intercambios.
- Manejo de casos límite: si el arreglo es nulo, retorna null en el record; si tiene longitud 0 o 1, retorna una copia equivalente con 0 comparaciones y 0 intercambios.

Justificación teórica obligatoria:
- Explica de forma concisa por qué Bubble Sort es estrictamente didáctico y desaconsejado en sistemas reales frente a conjuntos medianos o grandes:
  * Su complejidad temporal O(N^2) frente al estándar O(N log N) de algoritmos modernos (Dual-Pivot Quicksort, TimSort).
  * La sobrecarga excesiva de escrituras en memoria (swap overhead), donde incluso dentro de los algoritmos O(N^2), Insertion Sort y Selection Sort realizan significativamente menos asignaciones.

Análisis asintótico de operaciones:
- Presenta una tabla con la complejidad temporal en notación Big-O detallando el conteo de comparaciones e intercambios para:
  * Mejor caso (arreglo ya ordenado).
  * Peor caso (arreglo invertido).
  * Caso promedio.
- Especifica la complejidad espacial auxiliar en notación Big-O (contrastando la memoria del algoritmo vs. la memoria de la copia retornada).
