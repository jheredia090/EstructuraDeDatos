Actúa como un desarrollador Java experto y proporciona la implementación de un método estático para encontrar el valor máximo dentro de una matriz de enteros:

Especificaciones técnicas:
- Firma requerida: public static int encontrarMaximo(int[][] matriz)
- Manejo estricto de casos límite: lanza IllegalArgumentException si la matriz es nula, está vacía (0 filas) o si ninguna de sus filas contiene elementos válidos.
- Inicialización rigurosa: inicializa el valor máximo candidato con el primer elemento real disponible de la estructura (no utilizar 0 ni Integer.MIN_VALUE para no corromper la lógica ante matrices con enteros negativos).
- Tolerancia a matrices irregulares (jagged arrays) y omisión segura de filas nulas o de longitud 0.

Justificación teórica del recorrido exhaustivo:
- Explica de forma concisa por qué en una estructura bidimensional no ordenada es teóricamente imposible aplicar una salida anticipada (early exit) y por qué cualquier elemento no inspeccionado podría ser estrictamente mayor al máximo provisional.

Análisis asintótico:
- Especifica la complejidad temporal en notación Big-O para:
  * Mejor caso
  * Caso promedio
  * Peor caso
  (Explica por qué los tres escenarios resultan idénticos).
- Especifica la complejidad espacial auxiliar en notación Big-O.
