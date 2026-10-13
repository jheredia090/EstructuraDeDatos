Implementá en Java un algoritmo que encuentre el valor mínimo de un vector
de números enteros.

Antes de escribir el código, explicá la estrategia que considerás más
adecuada para resolver este problema y justificá por qué esa estrategia
es la correcta. Debés explicar que, para encontrar el mínimo, no hace falta
ordenar el vector ni recorrerlo en un orden específico; basta con comparar
cada elemento con el menor encontrado hasta el momento.

La estrategia recomendada es la siguiente:
- asumir que el primer elemento del vector es el menor actual;
- recorrer el vector desde la segunda posición;
- si un elemento es menor que el mínimo actual, actualizar el valor del mínimo;
- al finalizar el recorrido, devolver el valor mínimo.

Justificá por qué esta estrategia es eficiente para este problema:
- porque cada elemento se recorre una sola vez;
- porque no es necesario ordenar el arreglo;
- porque el valor mínimo se determina por comparación directa y no por
  reorganización de datos.

Indicá la complejidad esperada:
- complejidad temporal: O(n)
- complejidad espacial: O(1)

Requisitos del programa:
1. El código debe estar escrito en Java.
2. Debe recibir un vector de enteros.
3. Debe devolver o mostrar el valor mínimo del vector.
4. Debe estar correctamente comentado.
5. Debe incluir una explicación clara de por qué no es necesario ordenar
   el vector para encontrar el mínimo.
6. El programa debe mostrar el resultado en consola y también puede
   incluir una prueba con un vector de ejemplo.
7. Si se decide hacer una versión con un método separado, debe quedar
   bien estructurada y legible.

Ejemplo de prueba:
int[] vector = {12, 4, 7, 1, 9, 15, 3};

Resultado esperado:
1