/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Quiero implementar en Java una clase llamada Ej7 que simule un sistema de turnos para 
 * atención al público en una oficina utilizando una Cola (Queue) de cadenas (nombres/turnos) 
 * implementada sobre un arreglo de tamaño fijo.
 * 
 * El programa debe justificar por qué este problema utiliza estrictamente el principio FIFO 
 * (First In, First Out) y no LIFO (Last In, First Out):
 * - En un sistema de atención, el orden de llegada determina el orden de servicio por equidad; 
 *   la primera persona que llega es la primera en ser atendida.
 * - Si se utilizara una pila (LIFO), la última persona en llegar sería atendida primero y las 
 *   personas que llegaron temprano sufrirían inanición (starvation), lo cual viola la lógica 
 *   de una sala de espera.
 * 
 * La estructura interna debe gestionarse mediante un arreglo 'String[] turnos' con una 
 * capacidad máxima, con punteros 'front = 0', 'rear = -1' y un contador 'size = 0':
 * - agregarPersona(String persona): encola a la persona al final de la fila (rear++). Si la 
 *   sala está llena (size == capacidad), lanza IllegalStateException (Queue Overflow).
 * - atenderSiguiente(): desencola a la persona que está primera en el frente (front++) y 
 *   retorna su nombre. Si la sala está vacía (size == 0), lanza IllegalStateException (Queue Underflow).
 * - consultarPrimero(): retorna la persona que está primera en la fila sin atenderla (front). 
 *   Lanza IllegalStateException si no hay nadie.
 * - hayPersonasEsperando(): retorna true si size > 0.
 * - cantidadEnEspera(): retorna size.
 * 
 * Quiero que el código tenga comentarios explicando:
 * - La justificación del principio FIFO frente a LIFO en la atención al público.
 * - El movimiento de front (atención) y rear (llegada de clientes).
 * - El manejo de casos límite: sala llena y mostrador sin clientes en espera.
 * - La anulación de referencias (turnos[front] = null) al atender para liberar memoria en el Heap.
 * 
 * La verificación del resultado debe probarse en el método main con una jornada de atención:
 * - Llegada sucesiva de clientes: "Turno 1: Carlos", "Turno 2: Ana", "Turno 3: Pedro".
 * - Consultar quién está primero en la fila.
 * - Atender a dos personas consecutivas, verificando que son atendidas en orden de llegada 
 *   (Carlos primero, luego Ana).
 * - Atender a la última persona (Pedro).
 * - Intentar atender cuando no queda nadie esperando para verificar la excepción de subdesbordamiento.
 * =========================================================================================
 */

/**
 * Ejercicio 7: Sistema de turnos para atención en una oficina.
 * Modela una fila de atención al cliente bajo el principio de equidad FIFO (First In, First Out).
 */
public class Ej7 {

    // Arreglo que contiene los turnos/nombres de las personas en espera
    private final String[] turnos;

    // Puntero 'front': apunta a la persona que está primera para ser llamada
    private int front;

    // Puntero 'rear': apunta a la última persona que llegó a la sala
    private int rear;

    // Cantidad actual de personas esperando en la fila
    private int size;

    // Capacidad máxima de asientos/turnos de la sala de espera
    private final int capacidad;

    /**
     * Constructor del sistema de turnos.
     *
     * @param capacidad Cantidad máxima de personas que pueden esperar simultáneamente.
     */
    public Ej7(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad de la sala debe ser mayor a 0.");
        }
        this.capacidad = capacidad;
        this.turnos = new String[capacidad];
        this.front = 0;  // La primera persona en ser llamada estará en el índice 0
        this.rear = -1;  // -1 indica que nadie ha llegado aún
        this.size = 0;   // Sala vacía inicialmente
    }

    /**
     * Registra la llegada de una persona y la agrega al final de la fila (operación enqueue).
     *
     * @param persona Nombre o código de turno de la persona.
     * @throws IllegalStateException Si la sala de espera está llena (Queue Overflow).
     */
    public void agregarPersona(String persona) {
        if (rear == capacidad - 1) {
            throw new IllegalStateException("Sala llena (Queue Overflow): No se pueden emitir más turnos hoy.");
        }
        // Se avanza el puntero rear y se posiciona a la persona al final de la fila
        turnos[++rear] = persona;
        size++;
        System.out.println("-> Llegó: \"" + persona + "\" (Personas en espera: " + size + ")");
    }

    /**
     * Llama y atiende a la persona que está primera en el frente de la fila (operación dequeue).
     *
     * @return El nombre de la persona atendida.
     * @throws IllegalStateException Si no hay nadie esperando en la sala (Queue Underflow).
     */
    public String atenderSiguiente() {
        if (!hayPersonasEsperando()) {
            throw new IllegalStateException("Mostrador libre (Queue Underflow): No hay personas esperando en la sala.");
        }

        // Se toma la persona del frente
        String personaAtendida = turnos[front];

        // Limpieza de referencia: se pone en null para liberar la referencia en memoria
        turnos[front] = null;

        // El frente avanza al siguiente en la fila
        front++;
        size--;

        return personaAtendida;
    }

    /**
     * Consulta quién es la siguiente persona en ser llamada sin retirarla de la fila (operación front/peek).
     *
     * @return El nombre de la persona en el frente.
     * @throws IllegalStateException Si no hay nadie en la sala.
     */
    public String consultarPrimero() {
        if (!hayPersonasEsperando()) {
            throw new IllegalStateException("La sala está vacía; no hay nadie en el frente.");
        }
        return turnos[front];
    }

    /**
     * Indica si hay al menos una persona esperando ser atendida.
     */
    public boolean hayPersonasEsperando() {
        return size > 0;
    }

    /**
     * Retorna la cantidad de personas esperando en la sala.
     */
    public int cantidadEnEspera() {
        return size;
    }

    /**
     * Simulación de la jornada de atención en la oficina.
     */
    public static void main(String[] args) {
        System.out.println("========================================================================");
        System.out.println("SIMULACIÓN DE SISTEMA DE TURNOS (CLASE Ej7)");
        System.out.println("========================================================================");

        int capacidadSala = 3;
        Ej7 oficina = new Ej7(capacidadSala);

        System.out.println("Oficina abierta. Capacidad de la sala: " + capacidadSala);
        System.out.println("¿Hay personas esperando al inicio? " + oficina.hayPersonasEsperando());

        // 1. Llegada de clientes en orden cronológico
        System.out.println("\n--- 1. Llegada de clientes a la sala ---");
        oficina.agregarPersona("Turno 1: Carlos");
        oficina.agregarPersona("Turno 2: Ana");
        oficina.agregarPersona("Turno 3: Pedro");

        System.out.println("\nTotal de personas en espera: " + oficina.cantidadEnEspera());
        System.out.println("Próximo cliente a ser llamado (frente): \"" + oficina.consultarPrimero() + "\"");

        // 2. Intentar agregar a alguien cuando la sala está llena (Caso límite: Overflow)
        System.out.println("\n--- 2. Caso límite: Intento de ingreso con sala llena ---");
        try {
            System.out.print("Intentando ingresar 'Turno 4: Sofia'... ");
            oficina.agregarPersona("Turno 4: Sofia");
        } catch (IllegalStateException e) {
            System.out.println("Excepción capturada: " + e.getMessage());
        }

        // 3. Atención de clientes en estricto orden de llegada (FIFO)
        System.out.println("\n--- 3. Atención por ventanilla (FIFO: Primero en llegar, primero en ser atendido) ---");
        System.out.println("<- Atendiendo a: \"" + oficina.atenderSiguiente() + "\" | Restantes: " + oficina.cantidadEnEspera());
        System.out.println("<- Atendiendo a: \"" + oficina.atenderSiguiente() + "\" | Restantes: " + oficina.cantidadEnEspera());

        System.out.println("\nCliente restante en el frente: \"" + oficina.consultarPrimero() + "\"");

        System.out.println("<- Atendiendo a: \"" + oficina.atenderSiguiente() + "\" | Restantes: " + oficina.cantidadEnEspera());
        System.out.println("¿Quedan personas en la sala? " + oficina.hayPersonasEsperando());

        // 4. Intentar atender cuando no queda nadie (Caso límite: Underflow)
        System.out.println("\n--- 4. Caso límite: Intento de llamar a cliente con sala vacía ---");
        try {
            System.out.print("Ventanilla llama a siguiente cliente... ");
            oficina.atenderSiguiente();
        } catch (IllegalStateException e) {
            System.out.println("Excepción capturada: " + e.getMessage());
        }

        System.out.println("\n========================================================================");
        System.out.println("JUSTIFICACIÓN FIFO vs LIFO EN ATENCIÓN:");
        System.out.println("- Carlos llegó primero y fue atendido primero.");
        System.out.println("- Pedro llegó último y fue atendido al final.");
        System.out.println("- Si se hubiera usado una Pila (LIFO), Pedro habría sido atendido antes");
        System.out.println("  que Carlos, violando la equidad y provocando que los primeros en llegar");
        System.out.println("  esperen indefinidamente (inanición / starvation).");
        System.out.println("========================================================================");
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación / Ajuste del prompt:
 *    Se incluyó la instrucción 'turnos[front] = null' al desencolar en atenderSiguiente().
 * 
 * 2. Justificación técnica:
 *    Al tratarse de un arreglo de objetos String, si solo incrementamos el índice 'front'
 *    sin limpiar la celda previa, la referencia a la persona atendida permanece en el arreglo
 *    impidiendo que el Garbage Collector libere esa memoria hasta que todo el arreglo sea
 *    descartado. Anular la celda garantiza que el objeto pueda ser reciclado de inmediato.
 * =========================================================================================
 */