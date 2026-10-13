Ejercicio 6


Actúa como un desarrollador Java experto y proporciona la implementación de dos métodos estáticos para verificar la igualdad entre dos arreglos, contemplando dos semánticas distintas:

1. Método 1 (Igualdad posicional): Determina si ambos arreglos contienen exactamente los mismos elementos en los mismos índices.
   - Firma: public static boolean sonIgualesPosicional(int[] a, int[] b)
   - Salida temprana (early exit):
     * Retorna true en O(1) si a == b (misma referencia).
     * Retorna false en O(1) si hay asimetría de nulos o si a.length != b.length.
     * Interrumpe el ciclo y retorna false en el instante exacto en que a[i] != b[i].

2. Método 2 (Igualdad de contenido / sin importar el orden): Determina si ambos arreglos contienen exactamente los mismos elementos con las mismas frecuencias de aparición, sin importar su disposición.
   - Firma: public static boolean sonIgualesContenido(int[] a, int[] b)
   - Salida temprana (early exit):
     * Mismas validaciones O(1) iniciales (referencia, nulos y longitud).
     * Implementa una estrategia eficiente (ej. ordenamiento previo o tabla de frecuencias) que interrumpa la ejecución apenas se detecte un elemento faltante o con frecuencia inconsistente.

Análisis comparativo de complejidad:
- Para cada método, detalla en una tabla la complejidad temporal (Big-O) justificando:
  * Mejor caso (escenarios donde resuelve en O(1)).
  * Caso promedio.
  * Peor caso (arreglos idénticos o discrepancia al final del análisis).
- Detalla la complejidad espacial auxiliar (Big-O) de cada alternativa, contrastando memoria vs. tiempo de ejecución.