/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Quiero implementar en Java un programa que simule una torre de platos utilizando la 
 * estructura de datos Pila (Stack) implementada sobre un arreglo de tamaño fijo.
 * 
 * El programa debe justificar por qué este problema se resuelve con una pila (LIFO) y no con 
 * una cola (FIFO): en una torre de platos real, la restricción física exige que el último plato 
 * colocado en la parte superior sea el primero en ser retirado para evitar la caída de la torre; 
 * una cola retiraría el plato del fondo, violando el comportamiento natural del problema.
 * 
 * La estructura interna debe gestionarse mediante un arreglo 'String[] platos' con una 
 * capacidad máxima y un índice de control 'int top':
 * - 'top' inicia en -1 indicando que la torre está vacía.
 * - Al agregar un plato, 'top' se incrementa en 1 y se coloca en platos[top].
 * - Al retirar un plato, se devuelve platos[top] y 'top' se decrementa en 1.
 * 
 * La clase debe implementar los siguientes métodos:
 * 1. TorreDePlatos(int capacidadMaxima): Constructor que define la altura máxima de la torre.
 * 2. void agregarPlato(String plato): Apila un plato en la cima. Si la torre está llena, 
 *    lanza IllegalStateException informando que la torre no puede recibir más platos por 
 *    riesgo de caída (Stack Overflow).
 * 3. String retirarPlato(): Retira y retorna el plato superior. Si la torre está vacía, 
 *    lanza IllegalStateException (Stack Underflow).
 * 4. String verPlatoSuperior(): Consulta el plato de arriba sin retirarlo. Si está vacía, 
 *    lanza IllegalStateException.
 * 5. boolean estaVacia(): Retorna true si top == -1.
 * 6. boolean estaLlena(): Retorna true si top == capacidadMaxima - 1.
 * 7. int cantidadPlatos(): Retorna la cantidad actual de platos apilados (top + 1).
 * 
 * Quiero que el código tenga comentarios explicando:
 * - La correspondencia directa entre la cima física de la torre y el índice top del arreglo.
 * - La justificación del principio LIFO frente a FIFO.
 * - La protección de desbordamiento de la torre (altura máxima alcanzada) y subdesbordamiento 
 *   (sin platos que retirar).
 * 
 * La verificación del resultado debe incluir un método main con una simulación completa:
 * - Agregar platos sucesivos (ej. "Plato Rojo", "Plato Azul", "Plato Blanco").
 * - Consultar el plato superior con verPlatoSuperior().
 * - Intentar superar la capacidad máxima para verificar la excepción de desbordamiento.
 * - Retirar platos uno por uno comprobando que salen en orden inverso al que entraron (LIFO).
 * - Intentar retirar de una torre vacía para verificar la excepción de subdesbordamiento.
 * =========================================================================================
 */

/**
 * Ejercicio 2: Simulador de torre de platos.
 * Modela una pila física de platos donde el acceso está restringido exclusivamente
 * a la parte superior (tope) bajo la disciplina LIFO (Last In, First Out).
 */
public class Ej2 {

    // Arreglo que almacena los nombres o descripciones de los platos apilados
    private final String[] platos;

    // Índice que apunta a la cima de la torre de platos
    // top = -1 indica que la torre está vacía (no hay platos sobre la mesa)
    private int top;

    // Altura máxima segura permitida para la torre
    private final int capacidadMaxima;

    /**
     * Constructor que inicializa la torre con una capacidad máxima fija.
     *
     * @param capacidadMaxima Cantidad máxima de platos que soporta la torre.
     */
    public Ej2(int capacidadMaxima) {
        if (capacidadMaxima <= 0) {
            throw new IllegalArgumentException("La altura máxima debe ser mayor a cero. Valor: " + capacidadMaxima);
        }
        this.capacidadMaxima = capacidadMaxima;
        this.platos = new String[capacidadMaxima];
        this.top = -1; // Comienza vacía
    }

    /**
     * Coloca un nuevo plato en la parte superior de la torre (operación push).
     *
     * @param plato Nombre o identificador del plato a apilar.
     * @throws IllegalStateException Si la torre alcanzó su capacidad máxima (Stack Overflow).
     */
    public void agregarPlato(String plato) {
        // Validación de caso límite: Desbordamiento de la torre
        if (estaLlena()) {
            throw new IllegalStateException("Desbordamiento (Stack Overflow): La torre de platos alcanzó su límite (" 
                    + capacidadMaxima + "). No se pueden agregar más platos.");
        }
        // Se incrementa top para posicionar el nuevo plato en la cima física
        platos[++top] = plato;
        System.out.println("-> Se agregó: \"" + plato + "\" (Platos en la torre: " + cantidadPlatos() + ")");
    }

    /**
     * Retira el plato que se encuentra en la parte superior de la torre (operación pop).
     *
     * @return El nombre del plato retirado.
     * @throws IllegalStateException Si la torre no tiene ningún plato (Stack Underflow).
     */
    public String retirarPlato() {
        // Validación de caso límite: Subdesbordamiento
        if (estaVacia()) {
            throw new IllegalStateException("Subdesbordamiento (Stack Underflow): No hay platos en la torre para retirar.");
        }
        // Se toma el plato del tope y se decrementa el índice, anulando la referencia en memoria
        String platoRetirado = platos[top];
        platos[top--] = null; // Buena práctica en Java para liberar referencias en el Heap
        return platoRetirado;
    }

    /**
     * Consulta el plato que está en la cima sin retirarlo (operación peek).
     *
     * @return El nombre del plato superior.
     * @throws IllegalStateException Si la torre está vacía.
     */
    public String verPlatoSuperior() {
        if (estaVacia()) {
            throw new IllegalStateException("La torre está vacía; no hay ningún plato arriba.");
        }
        return platos[top];
    }

    /**
     * Indica si la torre no contiene platos.
     *
     * @return true si top == -1; false en caso contrario.
     */
    public boolean estaVacia() {
        return top == -1;
    }

    /**
     * Indica si la torre llegó a su límite de capacidad.
     *
     * @return true si top == capacidadMaxima - 1.
     */
    public boolean estaLlena() {
        return top == capacidadMaxima - 1;
    }

    /**
     * Devuelve la cantidad de platos apilados actualmente.
     *
     * @return Cantidad de platos (top + 1).
     */
    public int cantidadPlatos() {
        return top + 1;
    }

    /**
     * Simulación y verificación del comportamiento LIFO de la torre de platos.
     */
    public static void main(String[] args) {
        System.out.println("========================================================================");
        System.out.println("SIMULACIÓN DE TORRE DE PLATOS (CLASE Ej2)");
        System.out.println("========================================================================");

        int capacidad = 3;
        Ej2 torre = new Ej2(capacidad);

        System.out.println("Torre creada con capacidad máxima de " + capacidad + " platos.");
        System.out.println("¿La torre está vacía? " + torre.estaVacia());

        // 1. Agregar platos a la torre
        System.out.println("\n--- Apilando platos en la torre ---");
        torre.agregarPlato("Plato Rojo");
        torre.agregarPlato("Plato Azul");
        torre.agregarPlato("Plato Blanco");

        System.out.println("¿La torre está llena? " + torre.estaLlena());
        System.out.println("Plato que está actualmente arriba de todo: \"" + torre.verPlatoSuperior() + "\"");

        // 2. Intentar apilar en una torre llena (Caso límite: Overflow)
        System.out.println("\n--- Caso límite: Intento de agregar plato a torre llena ---");
        try {
            System.out.print("Intentando agregar 'Plato Dorado'... ");
            torre.agregarPlato("Plato Dorado");
        } catch (IllegalStateException e) {
            System.out.println("Excepción capturada: " + e.getMessage());
        }

        // 3. Retirar platos comprobando el orden LIFO
        System.out.println("\n--- Retirando platos (comprobación LIFO) ---");
        System.out.println("Nota: Los platos deben salir en orden inverso al que entraron.");
        while (!torre.estaVacia()) {
            System.out.println("<- Plato retirado: \"" + torre.retirarPlato() 
                    + "\" | Platos restantes: " + torre.cantidadPlatos());
        }

        System.out.println("¿La torre quedó vacía? " + torre.estaVacia());

        // 4. Intentar retirar de una torre vacía (Caso límite: Underflow)
        System.out.println("\n--- Caso límite: Intento de retirar de torre vacía ---");
        try {
            System.out.print("Intentando retirar un plato de la torre vacía... ");
            torre.retirarPlato();
        } catch (IllegalStateException e) {
            System.out.println("Excepción capturada: " + e.getMessage());
        }

        System.out.println("\n========================================================================");
        System.out.println("JUSTIFICACIÓN LIFO vs FIFO:");
        System.out.println("Una torre de platos física solo permite manipular el plato superior.");
        System.out.println("Si se usara una Cola (FIFO), se retiraría el plato del fondo, lo que");
        System.out.println("provocaría la caída de la torre. Por eso la Pila (LIFO) es la estructura");
        System.out.println("correcta para modelar este problema.");
        System.out.println("========================================================================");
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación / Ajuste del prompt:
 *    Se adoptó el nombre de clase 'Ej2' según la pauta de nomenclatura y se incorporó en el
 *    código la limpieza de la referencia 'platos[top] = null' al desapilar.
 * 
 * 2. Justificación técnica:
 *    En arreglos de objetos (como String[] o clases de dominio), decrementar el puntero 'top'
 *    sin limpiar la celda previa deja una referencia "fantasma" que el Garbage Collector de Java
 *    no puede reclamar inmediatamente (lo que en Java se conoce como 'memory leak' o 'loitering').
 *    Poner en null la posición desapilada garantiza una gestión de memoria limpia sin alterar
 *    la complejidad O(1) de la operación pop().
 * =========================================================================================
 */