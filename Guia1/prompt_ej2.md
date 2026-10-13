Ejercicio 2

Implementá en Java un algoritmo de búsqueda secuencial que dado un vector
de enteros desordenado y un elemento a buscar, recorra el vector desde la
primera posición hasta encontrar el elemento o determinar que no existe.

Restricciones:
- El vector es de enteros y NO está ordenado.
- Se debe usar búsqueda secuencial (recorrido linea de frente, posición por posición).
- No se permite reordenar el vector ni usar estructuras auxiliares (hash, árboles, etc.).

Requisitos del programa:
1. Mostrar por pantalla cuántas posiciones se recorrieron hasta encontrar
   el elemento o hasta determinar que no existe.
2. Informar si el elemento fue encontrado o no.
3. Incluir un análisis justificando por qué la búsqueda secuencial es el método
   adecuado para un vector desordenado (vs búsqueda binaria u otros).
4. Analizar la complejidad de los tres casos:
   - Mejor caso (elemento en la primera posición) → O(1)
   - Peor caso (elemento no existe o está en la última posición) → O(n)
   - Caso promedio (posición equiprobable) → O(n/2) ≈ O(n)

Estructura esperada:
- Método que reciba el vector y el elemento a buscar, devolviendo la cantidad
  de posiciones recorridas.
- Método main que pruebe con al menos 3 casos: encontrado al inicio, encontrado
  al final, y no encontrado.
- Justificación del método y análisis de complejidad en comentarios o aparte.
