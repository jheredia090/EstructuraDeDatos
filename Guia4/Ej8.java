/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Quiero implementar en Java una clase llamada Ej8 que simule una cola de impresión de 
 * documentos utilizando la estructura de datos Cola (Queue) implementada sobre un arreglo de 
 * tamaño fijo bajo el principio FIFO (First In, First Out).
 * 
 * El programa debe justificar por qué una cola representa fielmente el orden de impresión de 
 * un sistema operativo:
 * - Los trabajos de impresión se atienden por orden de llegada (spooling); el primer documento 
 *   en enviarse a la cola debe ser el primero en imprimirse físicamente.
 * - Si se utilizara una pila (LIFO), los documentos enviados con anterioridad quedarían 
 *   postergados cada vez que ingresara un nuevo archivo, provocando retrasos injustos e inanición.
 * 
 * El modelo de documento debe definirse como un record inmutable:
 * public record Documento(String nombre, int paginas) {}
 * 
 * La estructura interna debe gestionarse mediante un arreglo 'Documento[] buffer' con una 
 * capacidad máxima, punteros 'front = 0', 'rear = -1' y contador 'size = 0':
 * - encolarDocumento(Documento doc): agrega un trabajo al final de la cola (rear++). Lanza 
 *   IllegalStateException si el buffer está lleno (Queue Overflow).
 * - imprimirSiguiente(): retira y simula la impresión del documento del frente (front++), 
 *   retornando el Documento impreso. Lanza IllegalStateException si no hay trabajos pendientes 
 *   (Queue Underflow).
 * - verProximoDocumento(): retorna el documento del frente sin removerlo. Lanza 
 *   IllegalStateException si no hay trabajos.
 * - hayTrabajosPendientes(): retorna true si size > 0.
 * - cantidadTrabajosPendientes(): retorna size.
 * 
 * Quiero que el código tenga comentarios explicando:
 * - La correspondencia entre el spooler de impresión del sistema operativo y la estructura FIFO.
 * - El movimiento de front (documentos impresos) y rear (documentos recibidos).
 * - La limpieza de referencias (buffer[front] = null) al imprimir para liberar memoria.
 * 
 * La verificación del resultado debe probarse en el método main con una simulación de impresión:
 * - Encolar tres documentos: "Tesis.pdf" (120 págs), "Contrato.docx" (5 págs) y "Factura.pdf" (1 pág).
 * - Consultar el próximo documento a imprimir.
 * - Imprimir dos documentos, verificando que se imprimen en estricto orden de llegada.
 * - Encolar un nuevo documento: "Resumen.txt" (2 págs).
 * - Imprimir todos los documentos restantes hasta vaciar el buffer.
 * - Intentar imprimir con el buffer vacío para verificar el manejo de la excepción.
 * =========================================================================================
 */

/**
 * Ejercicio 8: Sistema de cola de impresión (Print Spooler).
 * Modela la gestión de trabajos de impresión mediante una Cola FIFO sobre arreglo de tamaño fijo.
 */
public class Ej8 {

    /**
     * Modelo inmutable que representa un trabajo de impresión.
     *
     * @param nombre Nombre o ruta del archivo a imprimir.
     * @param paginas Cantidad de páginas del documento.
     */
    public record Documento(String nombre, int paginas) {
        @Override
        public String toString() {
            return String.format("\"%s\" (%d págs)", nombre, paginas);
        }
    }

    // Buffer de almacenamiento de documentos en espera
    private final Documento[] buffer;

    // Puntero 'front': apunta al documento que está en la cabecera para ser impreso
    private int front;

    // Puntero 'rear': apunta al último documento recibido por el spooler
    private int rear;

    // Cantidad actual de documentos en el buffer
    private int size;

    // Capacidad máxima de trabajos en cola
    private final int capacidad;

    /**
     * Constructor del spooler de impresión.
     *
     * @param capacidad Cantidad máxima de documentos que caben en el buffer.
     */
    public Ej8(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad del buffer debe ser mayor a 0.");
        }
        this.capacidad = capacidad;
        this.buffer = new Documento[capacidad];
        this.front = 0;  // Primer documento a imprimir en el índice 0
        this.rear = -1;  // Sin documentos al inicio
        this.size = 0;   // Buffer inicialmente vacío
    }

    /**
     * Recibe un documento y lo agrega al final del buffer de impresión (enqueue).
     *
     * @param doc Objeto Documento a imprimir.
     * @throws IllegalStateException Si el buffer está lleno (Queue Overflow).
     */
    public void encolarDocumento(Documento doc) {
        if (rear == capacidad - 1) {
            throw new IllegalStateException("Buffer lleno (Queue Overflow): No se pueden recibir más trabajos de impresión.");
        }
        buffer[++rear] = doc;
        size++;
        System.out.println("-> [Spooler] Trabajo recibido: " + doc + " | (En cola: " + size + ")");
    }

    /**
     * Simula la impresión del documento que está al frente y lo retira de la cola (dequeue).
     *
     * @return El Documento impreso.
     * @throws IllegalStateException Si no hay documentos en el buffer (Queue Underflow).
     */
    public Documento imprimirSiguiente() {
        if (!hayTrabajosPendientes()) {
            throw new IllegalStateException("Buffer vacío (Queue Underflow): No hay trabajos de impresión pendientes.");
        }

        Documento impreso = buffer[front];

        // Limpieza de referencia para que el recolector de basura libere el objeto impreso
        buffer[front] = null;

        front++;
        size--;

        System.out.println("<- [Impresora] Imprimiendo: " + impreso + " | (Restantes: " + size + ")");
        return impreso;
    }

    /**
     * Consulta el próximo documento en la fila sin imprimirlo (front/peek).
     *
     * @return El Documento al frente de la cola.
     */
    public Documento verProximoDocumento() {
        if (!hayTrabajosPendientes()) {
            throw new IllegalStateException("No hay documentos en espera en el buffer.");
        }
        return buffer[front];
    }

    /**
     * Indica si existen trabajos pendientes por imprimir.
     */
    public boolean hayTrabajosPendientes() {
        return size > 0;
    }

    /**
     * Retorna la cantidad de documentos esperando en la cola.
     */
    public int cantidadTrabajosPendientes() {
        return size;
    }

    /**
     * Simulación completa de la cola de impresión.
     */
    public static void main(String[] args) {
        System.out.println("========================================================================");
        System.out.println("SIMULACIÓN DE COLA DE IMPRESIÓN (CLASE Ej8)");
        System.out.println("========================================================================");

        int capacidadBuffer = 4;
        Ej8 spooler = new Ej8(capacidadBuffer);

        System.out.println("Impresora lista. Capacidad máxima del buffer: " + capacidadBuffer + " trabajos.");

        // 1. Encolar los primeros tres documentos
        System.out.println("\n--- 1. Encolando documentos iniciales ---");
        spooler.encolarDocumento(new Documento("Tesis.pdf", 120));
        spooler.encolarDocumento(new Documento("Contrato.docx", 5));
        spooler.encolarDocumento(new Documento("Factura.pdf", 1));

        System.out.println("\nPróximo trabajo a imprimir: " + spooler.verProximoDocumento());
        System.out.println("Total en cola: " + spooler.cantidadTrabajosPendientes());

        // 2. Imprimir los dos primeros documentos (FIFO)
        System.out.println("\n--- 2. Imprimiendo los dos primeros trabajos (FIFO) ---");
        spooler.imprimirSiguiente(); // Tesis.pdf (120 págs)
        spooler.imprimirSiguiente(); // Contrato.docx (5 págs)

        // 3. Encolar un nuevo documento mientras la cola está en uso
        System.out.println("\n--- 3. Llega un nuevo documento al buffer ---");
        spooler.encolarDocumento(new Documento("Resumen.txt", 2));

        System.out.println("Próximo trabajo a imprimir: " + spooler.verProximoDocumento());

        // 4. Imprimir todos los documentos restantes
        System.out.println("\n--- 4. Vaciando el buffer de impresión ---");
        while (spooler.hayTrabajosPendientes()) {
            spooler.imprimirSiguiente();
        }

        System.out.println("¿Hay trabajos pendientes? " + spooler.hayTrabajosPendientes());

        // 5. Caso límite: Intentar imprimir con el buffer vacío (Underflow)
        System.out.println("\n--- 5. Caso límite: Intento de imprimir con buffer vacío ---");
        try {
            System.out.print("Impresora solicita siguiente trabajo... ");
            spooler.imprimirSiguiente();
        } catch (IllegalStateException e) {
            System.out.println("Excepción capturada con éxito: " + e.getMessage());
        }

        System.out.println("\n========================================================================");
        System.out.println("FUNDAMENTACIÓN FIFO EN IMPRESIÓN:");
        System.out.println("- 'Tesis.pdf' se envió primero y se imprimió primero.");
        System.out.println("- 'Resumen.txt' se envió último y se imprimió al final.");
        System.out.println("- El modelo FIFO garantiza que ningún usuario sea postergado");
        System.out.println("  indefinidamente por documentos que lleguen más tarde.");
        System.out.println("========================================================================");
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación / Ajuste del prompt:
 *    Se utilizó un 'record Documento(String nombre, int paginas)' inmutable y se mostró en
 *    la prueba el comportamiento de agregar un nuevo documento luego de haber procesado
 *    los dos primeros.
 * 
 * 2. Justificación técnica:
 *    En Java moderno (desde Java 16), los records proporcionan inmutabilidad garantizada,
 *    constructores canónicos y métodos equals/hashCode/toString automáticos, siendo ideales
 *    para transportar datos de trabajos de impresión. Además, intercalar llegadas y salidas
 *    demuestra cómo los punteros 'front' y 'rear' avanzan hacia la derecha, dejando en
 *    evidencia la limitación física de una cola lineal (desperdicio de espacio a la izquierda)
 *    que se analizará formalmente en el Ejercicio 9.
 * =========================================================================================
 */