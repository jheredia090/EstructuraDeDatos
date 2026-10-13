Ejercicio 7

Actúa como un desarrollador Java experto y proporciona la implementación de dos métodos estáticos para invertir un arreglo de enteros, contrastando un enfoque inmutable con uno destructivo in-place:

1. Método 1 (Arreglo auxiliar / No destructivo):
   - Invierte el contenido instanciando un nuevo arreglo de igual longitud, preservando intacto el arreglo de entrada.
   - Firma requerida: public static int[] invertirConCopia(int[] arr)
   - Validación de casos límite: si el arreglo es nulo, retorna null; si tiene 0 o 1 elemento, retorna un clon o arreglo equivalente.

2. Método 2 (In-place / Destructivo con dos punteros):
   - Invierte los elementos sobre el mismo arreglo mediante intercambio simétrico (swap) de elementos opuestos convergiendo al centro, con memoria auxiliar O(1).
   - Firma requerida: public static void invertirInPlace(int[] arr)
   - Validación de casos límite: si el arreglo es nulo o de longitud menor a 2, no realiza operaciones.

Análisis comparativo:
- Incluye una tabla comparativa final con:
  * Complejidad temporal en notación Big-O (mejor, peor y promedio).
  * Complejidad espacial auxiliar en notación Big-O.
  * Ventajas de cada solución (inmutabilidad y seguridad de concurrencia vs. ahorro de memoria y localidad espacial en caché).
  * Desventajas de cada solución (costo de instanciación y Garbage Collection vs. efectos colaterales por mutabilidad del estado original).