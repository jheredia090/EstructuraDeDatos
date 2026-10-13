/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Quiero implementar en Java una clase llamada PilaEnteros que modele la estructura de datos 
 * Pila (Stack) de números enteros utilizando un arreglo de tamaño fijo bajo el principio 
 * LIFO (Last In, First Out).
 * 
 * La estructura debe gestionarse mediante un arreglo interno 'int[] elementos' y un puntero 
 * de control 'int top':
 * - El índice 'top' representa la posición del elemento situado en la cima de la pila.
 * - Su valor inicial debe ser -1 para indicar explícitamente que la pila se encuentra vacía 
 *   (sin elementos cargados).
 * - En cada operación push, 'top' se incrementa en 1 antes de almacenar el valor en elementos[top].
 * - En cada operación pop, se recupera el elemento en elementos[top] y 'top' se decrementa en 1.
 * 
 * La clase debe implementar los siguientes métodos:
 * 1. PilaEnteros(int capacidad): Constructor que inicializa el arreglo con la capacidad fija y fija top = -1.
 * 2. void push(int valor): Apila un número. Si la pila está llena (isFull() == true), debe lanzar 
 *    IllegalStateException evitando el desbordamiento (Stack Overflow).
 * 3. int pop(): Desapila y retorna el elemento del tope. Si la pila está vacía (isEmpty() == true), 
 *    debe lanzar IllegalStateException evitando el subdesbordamiento (Stack Underflow).
 * 4. int peek(): Retorna el elemento del tope sin modificar 'top'. Si está vacía, lanza IllegalStateException.
 * 5. boolean isEmpty(): Retorna true si top == -1, false en caso contrario.
 * 6. boolean isFull(): Retorna true si top == capacidad - 1, false en caso contrario.
 * 7. int size(): Retorna la cantidad actual de elementos apilados (top + 1).
 * 
 * Quiero que el código tenga comentarios explicando:
 * - El rol y valor inicial del puntero top (-1).
 * - El control de desbordamiento (overflow) en push y subdesbordamiento (underflow) en pop/peek.
 * - Cómo las operaciones push, pop y peek se resuelven en tiempo constante O(1).
 * 
 * La verificación del resultado debe incluir un método main con pruebas completas:
 * - Apilar hasta llenar la pila y verificar isFull().
 * - Intentar apilar en una pila llena para verificar la excepción de desbordamiento.
 * - Consultar el tope con peek().
 * - Desapilar completamente hasta vaciarla y verificar isEmpty().
 * - Intentar desapilar en una pila vacía para verificar la excepción de subdesbordamiento.
 * =========================================================================================
 */

/**
 * Ejercicio 1: Implementación de una Pila de Enteros basada en un arreglo de tamaño fijo.
 * Sigue el principio LIFO (Last In, First Out) con operaciones en tiempo constante O(1).
 */
public class Ej1 {

    // Arreglo subyacente de almacenamiento contiguo
    private final int[] elementos;

    // Puntero de cima: indica el índice del último elemento insertado
    // Inicia en -1 para representar que la pila está vacía (ninguna posición ocupada)
    private int top;

    // Capacidad máxima fija de la estructura
    private final int capacidad;

    /**
     * Constructor que inicializa la pila con una capacidad definida.
     *
     * @param capacidad Tamaño máximo del arreglo. Debe ser mayor a 0.
     * @throws IllegalArgumentException Si la capacidad es menor o igual a cero.
     */
    public Ej1(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad de la pila debe ser mayor a 0. Recibida: " + capacidad);
        }
        this.capacidad = capacidad;
        this.elementos = new int[capacidad];
        this.top = -1; // -1 indica estado vacío
    }

    /**
     * Inserta un nuevo elemento en la cima de la pila.
     * Complejidad temporal: O(1).
     *
     * @param valor Número entero a apilar.
     * @throws IllegalStateException Si la pila ha alcanzado su capacidad máxima (Overflow).
     */
    public void push(int valor) {
        // Control de desbordamiento (Stack Overflow): no se puede insertar si top alcanzó el último índice
        if (isFull()) {
            throw new IllegalStateException("Desbordamiento de Pila (Stack Overflow): la pila está llena con " + capacidad + " elementos.");
        }
        // Incrementa top para apuntar a la nueva posición disponible y almacena el valor
        elementos[++top] = valor;
    }

    /**
     * Retira y devuelve el elemento situado en la cima de la pila.
     * Complejidad temporal: O(1).
     *
     * @return El entero que estaba en la cima.
     * @throws IllegalStateException Si la pila no contiene elementos (Underflow).
     */
    public int pop() {
        // Control de subdesbordamiento (Stack Underflow): no se puede extraer si top es -1
        if (isEmpty()) {
            throw new IllegalStateException("Subdesbordamiento de Pila (Stack Underflow): la pila está vacía.");
        }
        // Recupera el elemento de la cima actual y decrementa el puntero top
        return elementos[top--];
    }

    /**
     * Consulta el elemento situado en la cima de la pila sin removerlo.
     * Complejidad temporal: O(1).
     *
     * @return El entero en la cima.
     * @throws IllegalStateException Si la pila está vacía.
     */
    public int peek() {
        if (isEmpty()) {
            throw new IllegalStateException("La pila está vacía; no existe elemento en la cima.");
        }
        return elementos[top];
    }

    /**
     * Evalúa si la pila carece de elementos.
     *
     * @return true si top == -1; false en caso contrario.
     */
    public boolean isEmpty() {
        return top == -1;
    }

    /**
     * Evalúa si la pila alcanzó su capacidad máxima.
     *
     * @return true si top == capacidad - 1; false en caso contrario.
     */
    public boolean isFull() {
        return top == capacidad - 1;
    }

    /**
     * Retorna la cantidad actual de elementos presentes en la pila.
     *
     * @return Número de elementos almacenados (entre 0 y capacidad).
     */
    public int size() {
        return top + 1;
    }

    /**
     * Prueba de ejecución con datos de ejemplo que valida exhaustivamente todos los casos límite.
     */
    public static void main(String[] args) {
        System.out.println("========================================================================");
        System.out.println("PRUEBA DE EJECUCIÓN: PILA DE ENTEROS CON ARREGLO FIJO");
        System.out.println("========================================================================");

        int capacidad = 3;
        Ej1 pila = new Ej1(capacidad);

        System.out.println("Pila creada con capacidad: " + capacidad);
        System.out.println("¿Está vacía inicialmente? " + pila.isEmpty() + " (size = " + pila.size() + ")");

        // 1. Apilar elementos hasta llenar la pila
        System.out.println("\n--- Apilando elementos ---");
        pila.push(10);
        System.out.println("push(10) -> size: " + pila.size() + ", tope (peek): " + pila.peek());
        pila.push(20);
        System.out.println("push(20) -> size: " + pila.size() + ", tope (peek): " + pila.peek());
        pila.push(30);
        System.out.println("push(30) -> size: " + pila.size() + ", tope (peek): " + pila.peek());
        System.out.println("¿Está llena la pila? " + pila.isFull());

        // 2. Caso límite: Intentar apilar en pila llena (Stack Overflow)
        System.out.println("\n--- Caso límite: Intento de Overflow ---");
        try {
            System.out.print("Intentando push(40)... ");
            pila.push(40);
        } catch (IllegalStateException e) {
            System.out.println("Excepción capturada con éxito: " + e.getMessage());
        }

        // 3. Desapilar elementos hasta vaciar la pila
        System.out.println("\n--- Desapilando elementos (LIFO) ---");
        while (!pila.isEmpty()) {
            System.out.println("pop() extrajo: " + pila.pop() + " | Elementos restantes: " + pila.size());
        }
        System.out.println("¿Está vacía la pila tras desapilar todo? " + pila.isEmpty());

        // 4. Caso límite: Intentar desapilar en pila vacía (Stack Underflow)
        System.out.println("\n--- Caso límite: Intento de Underflow ---");
        try {
            System.out.print("Intentando pop() en pila vacía... ");
            pila.pop();
        } catch (IllegalStateException e) {
            System.out.println("Excepción capturada con éxito: " + e.getMessage());
        }

        try {
            System.out.print("Intentando peek() en pila vacía... ");
            pila.peek();
        } catch (IllegalStateException e) {
            System.out.println("Excepción capturada con éxito: " + e.getMessage());
        }

        System.out.println("\n========================================================================");
        System.out.println("VERIFICACIÓN DE PRUEBAS COMPLETADA EXITOSAMENTE.");
        System.out.println("========================================================================");
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación / Ajuste del prompt:
 *    El prompt inicial fue sumamente preciso y no requirió cambios conceptuales durante el
 *    desarrollo. Se contemplaron explícitamente los cinco requisitos clave: estructura (arreglo
 *    fijo), operaciones (push, pop, peek, isEmpty, isFull, size), casos límite (overflow/underflow),
 *    manejo del arreglo interno (puntero top inicializado en -1) y comportamiento LIFO.
 * 
 * 2. Justificación técnica:
 *    Inicializar 'top' en -1 es el estándar canónico en ciencias de la computación para arreglos
 *    de base 0, ya que desacopla la posición física del elemento (que va de 0 a capacidad - 1)
 *    de la cantidad de elementos (size = top + 1). Al usar pre-incremento en push (++top) y
 *    post-decremento en pop (top--), todas las operaciones se resuelven en exactamente una
 *    instrucción de indexación directa sin ciclos, garantizando complejidad temporal O(1) pura.
 * =========================================================================================
 */