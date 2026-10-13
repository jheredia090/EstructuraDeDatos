Ejercicio 3


Implementá en Java un algoritmo de búsqueda binaria que dado un vector de
enteros ORDENADO de forma ascendente y un elemento a buscar, encuentre el
elemento dividiendo repetidamente el intervalo de búsqueda a la mitad.

Restricciones:
- El vector está ORDENADO de forma ascendente (requisito indispensable).
- Se debe usar búsqueda binaria iterativa con las variables low, high y mid.
- No se permite usar Arrays.binarySearch() ni estructuras auxiliares.

Requisitos del programa:
1. Mostrar paso a paso cómo evolucionan las variables de búsqueda:
   - En cada iteración imprimir: low, high, mid, el valor en la posición mid
     y si ese valor es menor, mayor o igual al elemento buscado.
2. Informar si el elemento fue encontrado y en qué posición, o si no existe.
3. Incluir un análisis justificando por qué la búsqueda binaria es más
   eficiente que la lineal cuando los datos están ordenados.
4. Analizar la complejidad de los tres casos:
   - Mejor caso (elemento en el medio en la primera iteración) → O(1)
   - Peor caso (elemento no existe o está en un extremo) → O(log n)
   - Caso promedio → O(log n)

Estructura esperada:
- Método que reciba el vector ordenado y el elemento, devuelva la posición
  encontrada o -1 si no existe, e imprima el paso a paso de las variables.
- Método main que pruebe con al menos 3 casos: encontrado a la izquierda,
  encontrado a la derecha, y no encontrado.
- Justificación del método y análisis de complejidad en comentarios o aparte.
