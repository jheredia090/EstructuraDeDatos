Ejercicio 4


Actúa como un desarrollador Java experto y proporciona la implementación de dos métodos estáticos para determinar si un arreglo contiene elementos duplicados:

1. Solución 1 (Ciclos anidados): Recorre el arreglo comparando pares de elementos mediante dos bucles anidados.
2. Solución 2 (HashSet): Recorre el arreglo una sola vez insertando elementos en un java.util.HashSet para identificar duplicados.

Especificaciones técnicas:
- Firma de ambos métodos: public static boolean contieneDuplicados(int[] arr)
- Retorno: true ante el primer duplicado encontrado (early exit), false si todos son únicos.
- Validación de casos límite: arreglo nulo o con longitud menor a 2.
- Código limpio, idiomático y sin dependencias externas.

Análisis comparativo:
- Incluye una tabla comparativa final con:
  * Complejidad temporal en notación Big-O (mejor caso, caso promedio y peor caso).
  * Complejidad espacial auxiliar en notación Big-O.
  * Justificación técnica del trade-off de memoria frente a tiempo de ejecución.