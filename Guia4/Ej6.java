/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Quiero implementar en Java una clase llamada Ej6 que modele una estructura de datos Cola Simple 
 * (Queue) de números enteros utilizando un arreglo de tamaño fijo bajo el principio FIFO 
 * (First In, First Out).
 * 
 * La estructura debe gestionarse mediante un arreglo 'int[] elementos', un tamaño fijo 'capacidad', 
 * y dos índices de control 'front' y 'rear', junto a un contador 'size':
 * - 'front': índice del elemento situado al frente de la cola (el próximo a ser atendido/desencolado). 
 *   Inicia en 0.
 * - 'rear': índice del último elemento insertado al final de la cola. Inicia en -1.
 * - 'size': cantidad de elementos activos en la cola. Inicia en 0.
 * - En cada operación enqueue(x): se incrementa rear en 1 (rear++), se asigna elementos[rear] = x 
 *   y se incrementa size.
 * - En cada operación dequeue(): se recupera el valor en elementos[front], se avanza el índice 
 *   front en 1 (front++) y se decrementa size.
 * 
 * La clase debe implementar los siguientes métodos:
 * 1. Ej6(int capacidad): Constructor que inicializa el arreglo con la capacidad fija, con 
 *    front = 0, rear = -1 y size = 0.
 * 2. void enqueue(int valor): Agrega un elemento al final de la cola. Si la cola está llena 
 *    (isFull()), lanza IllegalStateException evitando el desbordamiento (Queue Overflow).
 * 3. int dequeue(): Remueve y retorna el elemento del frente. Si la cola está vacía (isEmpty()), 
 *    lanza IllegalStateException evitando el subdesbordamiento (Queue Underflow).
 * 4. int front(): Consulta el elemento del frente sin removerlo. Si está vacía, lanza IllegalStateException.
 * 5. boolean isEmpty(): Retorna true si size == 0.
 * 6. boolean isFull(): Retorna true si rear == capacidad - 1.
 * 7. int size(): Retorna la cantidad actual de elementos en la cola.
 * 
 * Quiero que el código tenga comentarios explicando:
 * - El significado y desplazamiento de los punteros front y rear durante enqueue y dequeue.
 * - El principio FIFO frente a LIFO.
 * - La condición de desbordamiento lineal (rear == capacidad - 1) y subdesbordamiento (size == 0).
 * 
 * La verificación del resultado debe probarse en el método main con un flujo de prueba completo:
 * - Encolar elementos hasta llenar la cola (ej. 10, 20, 30).
 * - Verificar el estado de isFull() y el valor del frente (front()).
 * - Intentar encolar en cola llena para verificar la excepción de desbordamiento.
 * - Desencolar elementos comprobando que salen en estricto orden de llegada (FIFO).
 * - Intentar desencolar en cola vacía para verificar la excepción de subdesbordamiento.
 * =========================================================================================
 */

/**
 * Ejercicio 6: Implementación de una Cola Simple de Enteros sobre arreglo de tamaño fijo.
 * Sigue el principio FIFO (First In, First Out) mediante dos punteros: front (frente) y rear (final).
 */
public class Ej6 {

    // Arreglo contiguo de almacenamiento de datos
    private final int[] elementos;

    // Puntero de frente: indica el índice del primer elemento listo para salir
    private int front;

    // Puntero de final: indica el índice del último elemento ingresado a la cola
    private int rear;

    // Cantidad actual de elementos contenidos en la cola
    private int size;

    // Capacidad máxima asignada al arreglo
    private final int capacidad;

    /**
     * Constructor que inicializa la cola simple.
     *
     * @param capacidad Tamaño máximo del arreglo.
     */
    public Ej6(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0.");
        }
        this.capacidad = capacidad;
        this.elementos = new int[capacidad];
        this.front = 0;  // El frente inicia en la primera posición del arreglo
        this.rear = -1;  // El final inicia en -1 (ningún elemento encolado aún)
        this.size = 0;   // Cola inicialmente vacía
    }

    /**
     * Inserta un nuevo elemento al final de la cola (operación enqueue).
     * Complejidad: O(1).
     *
     * @param valor Número entero a encolar.
     * @throws IllegalStateException Si la cola alcanzó el extremo físico del arreglo (Queue Overflow).
     */
    public void enqueue(int valor) {
        // En una cola simple lineal, el desbordamiento ocurre cuando el puntero rear alcanza el último índice
        if (isFull()) {
            throw new IllegalStateException("Desbordamiento (Queue Overflow): El arreglo está lleno en el extremo rear (" 
                    + rear + ").");
        }
        // Se avanza el puntero rear hacia la derecha y se almacena el elemento
        elementos[++rear] = valor;
        size++;
    }

    /**
     * Remueve y retorna el elemento situado al frente de la cola (operación dequeue).
     * Complejidad: O(1).
     *
     * @return El número entero más antiguo que estaba al frente.
     * @throws IllegalStateException Si la cola no tiene elementos (Queue Underflow).
     */
    public int dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Subdesbordamiento (Queue Underflow): La cola está vacía.");
        }
        // Se toma el elemento en 'front' y se avanza el puntero hacia la derecha (front++)
        int valorDesencolado = elementos[front++];
        size--;
        return valorDesencolado;
    }

    /**
     * Consulta el elemento que está al frente de la cola sin removerlo (operación front / peek).
     *
     * @return El entero en la posición 'front'.
     * @throws IllegalStateException Si la cola está vacía.
     */
    public int front() {
        if (isEmpty()) {
            throw new IllegalStateException("La cola está vacía; no hay elemento en el frente.");
        }
        return elementos[front];
    }

    /**
     * Evalúa si la cola carece de elementos.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Evalúa si la cola alcanzó el final físico del arreglo.
     */
    public boolean isFull() {
        return rear == capacidad - 1;
    }

    /**
     * Retorna la cantidad de elementos activos en la cola.
     */
    public int size() {
        return size;
    }

    /**
     * Prueba de ejecución que verifica el ciclo de vida completo de la cola simple.
     */
    public static void main(String[] args) {
        System.out.println("========================================================================");
        System.out.println("PRUEBA DE EJECUCIÓN: COLA SIMPLE DE ENTEROS (CLASE Ej6)");
        System.out.println("========================================================================");

        int capacidad = 3;
        Ej6 cola = new Ej6(capacidad);

        System.out.println("Cola creada con capacidad: " + capacidad);
        System.out.println("¿Está vacía al inicio? " + cola.isEmpty() + " (size = " + cola.size() + ")");

        // 1. Encolar elementos hasta completar el arreglo
        System.out.println("\n--- 1. Encolando elementos (enqueue) ---");
        cola.enqueue(10);
        System.out.println("enqueue(10) -> size: " + cola.size() + ", front: " + cola.front());
        cola.enqueue(20);
        System.out.println("enqueue(20) -> size: " + cola.size() + ", front: " + cola.front());
        cola.enqueue(30);
        System.out.println("enqueue(30) -> size: " + cola.size() + ", front: " + cola.front());
        System.out.println("¿La cola está llena? " + cola.isFull());

        // 2. Intentar encolar en cola llena (Overflow)
        System.out.println("\n--- 2. Caso límite: Intento de Overflow ---");
        try {
            System.out.print("Intentando enqueue(40)... ");
            cola.enqueue(40);
        } catch (IllegalStateException e) {
            System.out.println("Excepción capturada con éxito: " + e.getMessage());
        }

        // 3. Desencolar comprobando el orden FIFO (First In, First Out)
        System.out.println("\n--- 3. Desencolando elementos (orden FIFO) ---");
        System.out.println("Nota: Los elementos deben salir en el mismo orden que entraron: 10, 20, 30.");
        while (!cola.isEmpty()) {
            System.out.println("dequeue() extrajo: " + cola.dequeue() + " | Elementos restantes: " + cola.size());
        }

        System.out.println("¿Está vacía la cola al finalizar? " + cola.isEmpty());

        // 4. Intentar desencolar en cola vacía (Underflow)
        System.out.println("\n--- 4. Caso límite: Intento de Underflow ---");
        try {
            System.out.print("Intentando dequeue() en cola vacía... ");
            cola.dequeue();
        } catch (IllegalStateException e) {
            System.out.println("Excepción capturada con éxito: " + e.getMessage());
        }

        try {
            System.out.print("Intentando front() en cola vacía... ");
            cola.front();
        } catch (IllegalStateException e) {
            System.out.println("Excepción capturada con éxito: " + e.getMessage());
        }

        System.out.println("\n========================================================================");
        System.out.println("MECÁNICA FIFO RESUMIDA:");
        System.out.println("- Elementos insertados: 10, 20, 30.");
        System.out.println("- Elementos retirados:  10, 20, 30 (exactamente en el mismo orden).");
        System.out.println("========================================================================");
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación / Ajuste del prompt:
 *    Se incorporó un contador explícito 'size' junto a los punteros 'front' y 'rear'.
 * 
 * 2. Justificación técnica:
 *    En una cola simple lineal, después de encolar y desencolar varios elementos, el puntero
 *    'front' queda adelantado respecto a la posición 0. Tener una variable 'size' hace que
 *    la verificación de vacío ('isEmpty() -> size == 0') sea inmediata e inequívoca,
 *    desacoplando el conteo lógico de la posición física de los punteros 'front' y 'rear'.
 * =========================================================================================
 */