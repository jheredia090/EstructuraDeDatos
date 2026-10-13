/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Quiero implementar en Java una clase llamada Ej10 que modele una Cola Circular 
 * (Circular Queue) de enteros sobre un arreglo de tamaño fijo, reutilizando los espacios 
 * liberados al inicio mediante aritmética modular.
 * 
 * El programa debe justificar cómo la cola circular resuelve el problema del falso desbordamiento 
 * (Ejercicio 9):
 * - Los punteros 'front' y 'rear' avanzan utilizando el operador módulo (% capacidad), 
 *   conectando lógicamente el final del arreglo con el inicio:
 *   rear = (rear + 1) % capacidad
 *   front = (front + 1) % capacidad
 * - Cuando un puntero supera el último índice, vuelve automáticamente al índice 0 
 *   (efecto wrap-around), permitiendo reutilizar los casilleros desocupados a la izquierda.
 * - Para evitar la ambigüedad donde front == rear puede representar tanto cola vacía como cola llena, 
 *   se utiliza un contador explícito 'size':
 *   isEmpty() se cumple cuando size == 0
 *   isFull() se cumple cuando size == capacidad
 * 
 * La estructura interna debe gestionarse mediante un arreglo 'Integer[] elementos' de tamaño 
 * fijo, punteros 'front = 0', 'rear = -1' y contador 'size = 0':
 * - enqueue(int valor): avanza rear con (rear + 1) % capacidad, almacena el valor y hace size++. 
 *   Si isFull(), lanza IllegalStateException (Overflow genuino).
 * - dequeue(): extrae el elemento en front, limpia la celda con null, avanza front con 
 *   (front + 1) % capacidad y decrementa size--. Si isEmpty(), lanza IllegalStateException (Underflow).
 * - front(): retorna elementos[front]. Si está vacía, lanza IllegalStateException.
 * - mostrarEstadoMemoria(): imprime el arreglo físico completo con sus índices, la posición de 
 *   front y rear, y la cantidad de elementos activos.
 * 
 * Quiero que el código tenga comentarios explicando:
 * - La fórmula del operador módulo para dar la vuelta al inicio del arreglo.
 * - La eliminación de ambigüedad de punteros mediante la variable size.
 * - Cómo las operaciones conservan complejidad O(1) pura sin requerir desplazamientos de elementos.
 * 
 * La verificación del resultado debe probarse en el método main con una prueba que demuestre la 
 * reutilización de memoria:
 * 1. Crear una cola de capacidad 5 y llenarla completamente con 10, 20, 30, 40, 50.
 * 2. Desencolar 3 elementos (salen 10, 20, 30), liberando los índices 0, 1 y 2.
 * 3. Encolar 2 nuevos elementos (60 y 70): demostrar que se insertan exitosamente en los 
 *    índices 0 y 1 dando la vuelta circular.
 * 4. Desencolar todos los elementos restantes verificando que salen en estricto orden FIFO (40, 50, 60, 70).
 * 5. Probar los casos límite de overflow genuino y underflow.
 * =========================================================================================
 */

/**
 * Ejercicio 10: Implementación de una Cola Circular (Circular Queue) sobre arreglo de tamaño fijo.
 * Resuelve definitivamente el desperdicio de espacio mediante aritmética modular (operador %).
 */
public class Ej10 {

    // Arreglo físico continuo donde se almacenan los elementos
    private final Integer[] elementos;

    // Puntero de frente: apunta al elemento más antiguo listo para ser extraído
    private int front;

    // Puntero de final: apunta a la posición del último elemento insertado
    private int rear;

    // Contador de elementos lógicos activos (elimina la ambigüedad entre vacía y llena)
    private int size;

    // Capacidad fija del arreglo
    private final int capacidad;

    /**
     * Constructor de la cola circular.
     *
     * @param capacidad Tamaño máximo del arreglo.
     */
    public Ej10(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0.");
        }
        this.capacidad = capacidad;
        this.elementos = new Integer[capacidad];
        this.front = 0;  // El frente inicia en el índice 0
        this.rear = -1;  // -1 para que la primera inserción lo traslade a 0: (-1 + 1) % cap = 0
        this.size = 0;   // Cola inicialmente vacía
    }

    /**
     * Encola un elemento al final de la cola circular.
     * Si 'rear' llega al final del arreglo, el operador módulo lo redirige al índice 0 (wrap-around).
     * Complejidad: O(1).
     *
     * @param valor Número entero a encolar.
     * @throws IllegalStateException Si la cola alcanzó su capacidad total (Overflow genuino).
     */
    public void enqueue(int valor) {
        // En una cola circular el overflow solo ocurre cuando verdaderamente no quedan casilleros libres
        if (isFull()) {
            throw new IllegalStateException("Desbordamiento Genuino (Queue Overflow): "
                    + "La cola está 100% llena (" + size + "/" + capacidad + " elementos).");
        }

        // Aritmética modular: avanza en círculo conectando el índice final con el índice 0
        rear = (rear + 1) % capacidad;
        elementos[rear] = valor;
        size++;
        System.out.println("-> enqueue(" + valor + ") en índice [" + rear + "] (size = " + size + ")");
    }

    /**
     * Desencola el elemento situado en el frente y avanza 'front' circularmente.
     * Complejidad: O(1).
     *
     * @return El entero que estaba en el frente.
     * @throws IllegalStateException Si la cola está vacía (Underflow).
     */
    public int dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Subdesbordamiento (Queue Underflow): La cola está vacía.");
        }

        int valor = elementos[front];

        // Anulamos la celda para visualizar limpiamente que quedó disponible para reutilizarse
        elementos[front] = null;

        // Aritmética modular: avanza el frente en forma circular
        front = (front + 1) % capacidad;
        size--;

        System.out.println("<- dequeue() extrajo: " + valor + " | Nuevo frente en índice [" + front + "]");
        return valor;
    }

    /**
     * Consulta el elemento que está al frente de la cola sin removerlo.
     */
    public int front() {
        if (isEmpty()) {
            throw new IllegalStateException("La cola está vacía; no hay elemento en el frente.");
        }
        return elementos[front];
    }

    /**
     * Indica si la cola no contiene elementos.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Indica si la cola alcanzó su límite total de capacidad.
     */
    public boolean isFull() {
        return size == capacidad;
    }

    /**
     * Retorna la cantidad de elementos activos en la cola.
     */
    public int size() {
        return size;
    }

    /**
     * Muestra una radiografía visual de cómo residen los elementos en el arreglo físico.
     */
    public void mostrarEstadoMemoria() {
        System.out.println("------------------------------------------------------------------------");
        System.out.println("RADIOGRAFÍA DEL ARREGLO FÍSICO:");
        System.out.print("Celdas:   [ ");
        for (int i = 0; i < capacidad; i++) {
            if (elementos[i] == null) {
                System.out.print("(libre) ");
            } else {
                System.out.printf("%5d   ", elementos[i]);
            }
        }
        System.out.println("]");

        System.out.print("Índices:  [ ");
        for (int i = 0; i < capacidad; i++) {
            System.out.printf("   %d    ", i);
        }
        System.out.println("]");

        System.out.printf("Punteros:  front = %d | rear = %d | elementos activos = %d / %d%n",
                front, rear, size, capacidad);
        System.out.println("------------------------------------------------------------------------");
    }

    /**
     * Secuencia de prueba que demuestra la reutilización circular de memoria y orden FIFO.
     */
    public static void main(String[] args) {
        System.out.println("========================================================================");
        System.out.println("DEMOSTRACIÓN: COLA CIRCULAR CON ARITMÉTICA MODULAR (CLASE Ej10)");
        System.out.println("========================================================================");

        int capacidad = 5;
        Ej10 cola = new Ej10(capacidad);

        // PASO 1: Llenar la cola con 5 elementos
        System.out.println("\n--- PASO 1: Llenando la cola circular con 5 elementos ---");
        cola.enqueue(10);
        cola.enqueue(20);
        cola.enqueue(30);
        cola.enqueue(40);
        cola.enqueue(50);

        cola.mostrarEstadoMemoria();
        System.out.println("¿Está llena la cola? " + cola.isFull());

        // PASO 2: Desencolar 3 elementos (libera índices 0, 1 y 2)
        System.out.println("\n--- PASO 2: Desencolando 3 elementos (salen 10, 20, 30) ---");
        cola.dequeue();
        cola.dequeue();
        cola.dequeue();

        cola.mostrarEstadoMemoria();

        // PASO 3: Encolar 2 nuevos elementos (Reutilización con efecto wrap-around)
        System.out.println("\n--- PASO 3: Encolando 60 y 70 (deben reusar los índices 0 y 1) ---");
        System.out.println("En el Ejercicio 9 esto fallaba por falso overflow.");
        System.out.println("En la Cola Circular, el operador módulo da la vuelta:");
        cola.enqueue(60); // Ocupa el índice 0: (4 + 1) % 5 = 0
        cola.enqueue(70); // Ocupa el índice 1: (0 + 1) % 5 = 1

        cola.mostrarEstadoMemoria();
        System.out.println("Elemento al frente de la cola (front): " + cola.front() + " (en índice " + cola.front + ")");

        // PASO 4: Encolar un elemento más para llenar completamente la cola circular
        System.out.println("\n--- PASO 4: Encolando 80 para alcanzar el 100% de capacidad ---");
        cola.enqueue(80); // Ocupa el índice 2: (1 + 1) % 5 = 2
        cola.mostrarEstadoMemoria();
        System.out.println("¿Está llena la cola ahora? " + cola.isFull());

        // PASO 5: Intentar encolar en cola circular 100% llena (Overflow genuino)
        System.out.println("\n--- PASO 5: Caso límite: Intento de Overflow con cola 100% llena ---");
        try {
            System.out.print("Intentando enqueue(90)... ");
            cola.enqueue(90);
        } catch (IllegalStateException e) {
            System.out.println("\nExcepción capturada con éxito: " + e.getMessage());
        }

        // PASO 6: Desencolar todos los elementos verificando el orden FIFO estricto
        System.out.println("\n--- PASO 6: Desencolando todos los elementos (verificación FIFO) ---");
        System.out.println("Orden esperado de salida: 40, 50, 60, 70, 80.");
        while (!cola.isEmpty()) {
            cola.dequeue();
        }

        cola.mostrarEstadoMemoria();
        System.out.println("¿La cola quedó vacía? " + cola.isEmpty());

        // PASO 7: Intentar desencolar en cola vacía (Underflow)
        System.out.println("\n--- PASO 7: Caso límite: Intento de Underflow ---");
        try {
            System.out.print("Intentando dequeue() en cola vacía... ");
            cola.dequeue();
        } catch (IllegalStateException e) {
            System.out.println("Excepción capturada con éxito: " + e.getMessage());
        }

        System.out.println("\n========================================================================");
        System.out.println("CONCLUSIÓN TÉCNICA DE LA COLA CIRCULAR:");
        System.out.println("1. Reutilización completa: los índices 0, 1 y 2 fueron reocupados por 60, 70 y 80.");
        System.out.println("2. Operaciones O(1): no requiere desplazar elementos con ciclos.");
        System.out.println("3. Sin ambigüedad: la variable 'size' distingue inequívocamente si está vacía o llena.");
        System.out.println("========================================================================");
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación / Ajuste del prompt:
 *    Se inicializó el puntero 'rear' en -1 y se aplicó la fórmula '(rear + 1) % capacidad'
 *    en cada enqueue, manteniendo el puntero 'front' en 0 al inicio.
 * 
 * 2. Justificación técnica:
 *    Al combinar la aritmética modular con el contador explícito 'size', la estructura es
 *    completamente inmune a los problemas de solapamiento de punteros. Cuando front == rear,
 *    en lugar de requerir algoritmos complejos con celdas de desperdicio (como dejar un casillero
 *    vacío de seguridad), el estado se resuelve en O(1) con 'size == 0' (vacía) o 'size == capacidad'
 *    (llena), aprovechando el 100% del espacio del arreglo y resolviendo definitivamente la
 *    falla de la cola simple.
 * =========================================================================================
 */
