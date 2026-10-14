# Estructuras de Datos

Resolución de los trabajos prácticos de la materia **Estructuras de Datos**, implementados en **Java** y desarrollados con **OpenCode** como asistente.

La consigna de todos los prácticos es la misma: el código no se pide "a ciegas". Antes de generarlo hay que **analizar el problema y escribir un prompt claro** (estrategia, casos límite, complejidad esperada). La calidad del código depende de la calidad de ese razonamiento. Por eso **cada ejercicio conserva, como comentario dentro del propio archivo `.java`, el prompt inicial que se usó**.

## Estructura del repositorio

```
EstructuraDeDatos/
├── Guia1/   Análisis de algoritmos y complejidad (vectores y matrices)
├── Guia2/   Recursividad
├── Guia3/   Algoritmos de ordenamiento
├── Guia4/   Pilas y colas con arreglos
├── Guia5/   Listas enlazadas simples
└── Guia6/   Pilas y colas con listas enlazadas
```

Cada carpeta contiene el `.docx` con las consignas y 10 ejercicios en Java (60 archivos en total). En la Guía 1 los archivos se llaman `EjercicioN.java`. En las demás, `EjN.java`.

## Cómo está documentado cada ejercicio

Todos los archivos `.java` empiezan con un bloque de comentario que contiene el **prompt inicial** usado en OpenCode. Además, según la guía:

| Guía | Dónde queda registrado lo demás |
|------|----------------------------------|
| 1 | El prompt va al inicio del archivo. La justificación y el análisis de complejidad están en los comentarios del código (Javadoc). |
| 2, 3 y 4 | Al final del archivo hay un bloque **"Registro de modificaciones al prompt / ajustes post-implementación"**, que explica qué se cambió respecto del prompt inicial y por qué. |
| 5 y 6 | Dentro del comentario inicial, bajo **"Ajustes realizados luego de la primera respuesta de OpenCode"**. |

Todos los archivos incluyen un `main` con casos de prueba que se pueden ejecutar directamente.

## Contenido de las guías

### Guía 1: Análisis de algoritmos y complejidad

Cada ejercicio indica la complejidad temporal y espacial, y el mejor caso, peor caso y caso promedio cuando corresponde.

| Archivo | Problema | Qué implementa |
|---------|----------|----------------|
| `Ejercicio1` | Mínimo de un vector | `encontrarMinimo`, `encontrarPosicionMinimo`. Sin ordenar el vector: O(n) en tiempo y O(1) en espacio. |
| `Ejercicio2` | Búsqueda en vector desordenado | Búsqueda secuencial, con un `record ResultadoBusqueda` que informa si lo encontró, la posición y cuántas posiciones recorrió. |
| `Ejercicio3` | Búsqueda en vector ordenado | Búsqueda binaria iterativa (`low`, `high`, `mid`) que muestra paso a paso la evolución de las variables. |
| `Ejercicio4` | Elementos duplicados | Dos soluciones: ciclos anidados (`contieneDuplicadosCiclos`) y `HashSet` (`contieneDuplicadosHashSet`), con su comparación de complejidad. |
| `Ejercicio5` | Contar ocurrencias | `contarOcurrencias`: recorrido completo, con justificación de por qué no hay salida anticipada. |
| `Ejercicio6` | Comparar dos vectores | `sonIgualesPosicional` (corta en la primera diferencia) y `sonIgualesContenido` (mismos elementos y frecuencias, sin importar el orden). |
| `Ejercicio7` | Invertir un vector | `invertirConCopia` (arreglo auxiliar) e `invertirInPlace` (dos punteros, memoria O(1)). |
| `Ejercicio8` | Suma de una matriz | `sumarElementos`: recorrido por filas, soporta matrices irregulares y devuelve `record ResultadoMatriz(suma, operaciones)`. |
| `Ejercicio9` | Mayor elemento de una matriz | `encontrarMaximo`: valida los casos límite con `IllegalArgumentException`, tolera filas nulas y vacías. |
| `Ejercicio10` | Bubble Sort | `ordenar` trabaja sobre una copia, corta de forma anticipada y devuelve `record ResultadoBubbleSort` con el arreglo ordenado, comparaciones e intercambios. |

### Guía 2: Recursividad

Cada ejercicio explicita el caso base, el caso recursivo y cómo se reduce el problema.

| Archivo | Problema | Método principal |
|---------|----------|------------------|
| `Ej1` | Factorial | `factorial(int)`. Valida `n > 20` para evitar desbordamiento de `long`. |
| `Ej2` | Suma de los primeros N números | `sumarHastaUno(int)` y `formulaGauss(int)` como contraste. |
| `Ej3` | Multiplicación mediante sumas | `multiplicar(int, int)` sin usar el operador `*`. |
| `Ej4` | Potencia | `potencia(double, int)` sin usar `Math.pow`. |
| `Ej5` | Conteo regresivo | `cuentaRegresiva(int)` (método `void` recursivo). |
| `Ej6` | Contar dígitos | `contarDigitos(long)` |
| `Ej7` | Sumar dígitos | `sumarDigitos(long)` |
| `Ej8` | Invertir una palabra | `invertirCadena(String)` |
| `Ej9` | Palíndromo | `esPalindromo(String)` y `esPalindromoNormalizado(String)` |
| `Ej10` | Buscar un elemento en un arreglo | `buscar(int[], int)`: búsqueda lineal recursiva, sin ciclos. |

### Guía 3: Algoritmos de ordenamiento

| Archivo | Problema | Qué implementa |
|---------|----------|----------------|
| `Ej1` | Burbuja paso a paso | `ordenarBurbujaConPasadas`: muestra el arreglo después de cada pasada. |
| `Ej2` | Burbuja vs. Selección | Ambos algoritmos sobre el mismo arreglo, con `record MetricasOrdenamiento` (comparaciones e intercambios). |
| `Ej3` | Inserción con arreglo casi ordenado | `insertionSortDetallado`: mide los desplazamientos realizados. |
| `Ej4` | Nombres con Selection Sort | `ordenarNombres(String[])`: orden alfabético. |
| `Ej5` | ShellSort | `ordenarShellSort`: muestra la evolución de los gaps. |
| `Ej6` | Quicksort (pivote: primer elemento) | `quicksort`: muestra el proceso de partición. |
| `Ej7` | Peor caso de Quicksort | `ordenarYContarLlamadas`: cuenta las llamadas recursivas sobre un arreglo ya ordenado. |
| `Ej8` | MergeSort paso a paso | `mergeSort`: muestra la división recursiva y la fusión. |
| `Ej9` | Comparación de algoritmos simples | Burbuja, Selección e Inserción sobre los mismos datos, con `record ResultadoComparacion`. |
| `Ej10` | Ranking de puntajes | `record Jugador` y `ordenarRankingDescendente` (Insertion Sort estable, in-place). |

### Guía 4: Pilas y colas con arreglos

Todas las estructuras usan un arreglo de tamaño fijo.

| Archivo | Problema | Qué implementa |
|---------|----------|----------------|
| `Ej1` | Pila de enteros | `push`, `pop`, `peek`, `isEmpty`, `isFull`, `size` (LIFO). |
| `Ej2` | Torre de platos | Pila de `String`: `agregarPlato`, `retirarPlato`, `verPlatoSuperior`. |
| `Ej3` | Paréntesis balanceados | `validarParentesis(String)` con una pila de caracteres interna. |
| `Ej4` | Historial de navegación | Pila de URLs: `visitarPagina`, `volverAtras`, `obtenerPaginaActual`. |
| `Ej5` | Pila genérica | `Pila<T>` probada con distintos tipos de datos (incluido un `record Persona`). |
| `Ej6` | Cola simple de enteros | `enqueue`, `dequeue`, `front`, `isEmpty`, `isFull`, `size` (FIFO). |
| `Ej7` | Sistema de turnos | Cola de `String`: `agregarPersona`, `atenderSiguiente`, `consultarPrimero`. |
| `Ej8` | Cola de impresión | Cola de `record Documento`: `encolarDocumento`, `imprimirSiguiente`. |
| `Ej9` | Desperdicio de espacio | Cola simple que muestra el falso desbordamiento (*faux overflow*) con `mostrarEstadoMemoria`. |
| `Ej10` | Cola circular | Reutiliza los espacios liberados con aritmética modular. |

### Guía 5: Listas enlazadas simples

Cada archivo tiene su propia clase `Nodo` y la referencia `head`, sin usar `ArrayList` ni `LinkedList`.

| Archivo | Problema | Método principal |
|---------|----------|------------------|
| `Ej1` | Lista desde cero | `insertarAlInicio`, `insertarAlFinal`, `imprimir`, `estaVacia`, `getSize` |
| `Ej2` | Buscar elementos | `buscar(int)` |
| `Ej3` | Obtener por posición | `obtener(int)` |
| `Ej4` | Insertar en una posición | `insertarEnPosicion(int, int)` |
| `Ej5` | Eliminar por valor | `eliminar(int)` |
| `Ej6` | Eliminar por posición | `eliminarEnPosicion(int)` |
| `Ej7` | Modificar un elemento | `modificar(int, int)` |
| `Ej8` | Contar ocurrencias | `contarOcurrencias(int)` |
| `Ej9` | Invertir la lista | `invertir()` |
| `Ej10` | Lista genérica | `Ej10<T>` con `insertar`, `buscar`, `eliminar` y `obtener`, probada con un `record Alumno`. |

### Guía 6: Pilas y colas con listas enlazadas

| Archivo | Problema | Qué implementa |
|---------|----------|----------------|
| `Ej1` | Pila enlazada de enteros | `push`, `pop`, `peek`, `buscar`, `imprimir`. `head` es el tope, y las operaciones son O(1). |
| `Ej2` | Cola enlazada de enteros | `enqueue`, `dequeue`, `front`, con referencias `head` y `tail`. |
| `Ej3` | Pila genérica | `Ej3<T>` probada con un `record Libro`. |
| `Ej4` | Cola genérica | `Ej4<T>` probada con un `record Cliente`. |
| `Ej5` | Historial de navegación con pila | `visitarPagina`, `volverAtras`, `paginaActual`, `imprimirHistorial`. |
| `Ej6` | Cola de atención de clientes | Clase `Cliente` y cola enlazada: `agregarCliente`, `atenderProximo`, `consultarQuienSigue`. |
| `Ej7` | Verificador de paréntesis | Pila enlazada de caracteres: `estaBalanceado` y `analizarBalanceo` (corchetes, llaves y paréntesis). |
| `Ej8` | Invertir una palabra con pila | `invertirPalabra(String)` y `esPalindromo(String)`. |
| `Ej9` | Cola de impresión | `TrabajoImpresion` y cola enlazada: `agregarTrabajo`, `imprimirProximo`, `getTotalPaginasPendientes`. |
| `Ej10` | Lista doblemente enlazada | `Ej10<T>` con `head` y `tail`: inserción y eliminación en ambos extremos, `eliminar`, `contiene`, e impresión hacia adelante y hacia atrás. |

## Requisitos

- **JDK 16 o superior**, porque varios ejercicios usan `record`. Probado con JDK 21.
- Solo se usa la biblioteca estándar de Java, sin dependencias externas.
- Ningún ejercicio usa `package` ni pide datos por teclado.

## Cómo compilar y ejecutar

Para compilar y ejecutar un ejercicio:

```bash
cd Guia2
javac Ej1.java
java Ej1
```

Para la Guía 1 es igual, con otro nombre de archivo:

```bash
cd Guia1
javac Ejercicio1.java
java Ejercicio1
```

También se puede compilar una guía completa de una vez, porque no hay conflictos de nombres dentro de cada carpeta:

```bash
cd Guia5
javac *.java
java Ej3
```

## Notas

- Los archivos compilados (`*.class`, `*.jar`, `bin/`, `out/`, `target/`) y la carpeta `.vscode/` están excluidos con `.gitignore`.
- Las consignas originales de cada práctico están en el `.docx` de su carpeta.
