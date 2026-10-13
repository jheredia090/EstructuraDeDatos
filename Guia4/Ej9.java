/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Quiero implementar en Java una clase llamada Ej9 que modele una Cola Simple de enteros 
 * sobre un arreglo de tamaño fijo con el propósito específico de evidenciar y demostrar el 
 * problema del desperdicio de espacio (falso desbordamiento o faux overflow).
 * 
 * El programa debe justificar por qué ocurre esta ineficiencia estructural en una cola lineal:
 * - El puntero 'rear' avanza hacia la derecha con cada enqueue y el puntero 'front' avanza 
 *   hacia la derecha con cada dequeue.
 * - A medida que se desencolan elementos, se van liberando casilleros al inicio del arreglo 
 *   (a la izquierda de front).
 * - Sin embargo, una vez que 'rear' alcanza el último índice físico (rear == capacidad - 1), 
 *   la cola simple considera que está llena y rechaza nuevas inserciones, a pesar de que el 
 *   arreglo tiene posiciones libres desaprovechadas al inicio.
 * 
 * La estructura interna debe gestionarse mediante un arreglo 'Integer[] elementos' de tamaño 
 * fijo (ej. capacidad 5), punteros 'front = 0', 'rear = -1' y contador 'size = 0':
 * - enqueue(int valor): incrementa rear++ y almacena el dato. Lanza IllegalStateException si 
 *   rear == capacidad - 1, evidenciando el desbordamiento lineal.
 * - dequeue(): lee elementos[front], asigna elementos[front] = null (para visualizar la celda 
 *   liberada), avanza front++ y decrementa size.
 * - mostrarEstadoMemoria(): método de inspección que imprime el contenido físico de cada celda 
 *   del arreglo, la ubicación actual de los punteros 'front' y 'rear', y la cantidad de casilleros 
 *   libres desperdiciados.
 * 
 * Quiero que el código tenga comentarios explicando:
 * - Por qué las celdas liberadas a la izquierda de front quedan inaccesibles en una cola simple.
 * - La diferencia entre "capacidad física alcanzada al final" y "capacidad lógica disponible".
 * - Por qué este problema motiva la necesidad de implementar una Cola Circular (Ejercicio 10).
 * 
 * La verificación del resultado debe probarse en el método main con la secuencia exacta de demostración:
 * 1. Crear una cola de capacidad 5 y llenarla completamente con los valores: 10, 20, 30, 40, 50.
 * 2. Mostrar el estado de memoria (0 espacios libres).
 * 3. Desencolar 3 elementos (salen 10, 20, 30).
 * 4. Mostrar el estado de memoria: se evidencian 3 espacios vacíos a la izquierda (índices 0, 1, 2) 
 *    y solo 2 elementos activos (40, 50).
 * 5. Intentar hacer enqueue(60): capturar la excepción y evidenciar que la cola rechaza el elemento 
 *    a pesar de tener 3 casilleros vacíos.
 * =========================================================================================
 */

import java.util.Arrays;

/**
 * Ejercicio 9: Demostración del problema de desperdicio de espacio en una Cola Simple lineal.
 * Muestra visualmente cómo el avance unidireccional de los punteros genera un falso desbordamiento
 * (faux overflow) impidiendo reutilizar la memoria liberada al inicio del arreglo.
 */
public class Ej9 {

    // Arreglo de Integer (permite asignar null en celdas vacías para visualización limpia)
    private final Integer[] elementos;

    // Puntero que apunta al primer elemento activo listo para salir
    private int front;

    // Puntero que apunta al último elemento insertado
    private int rear;

    // Cantidad actual de elementos lógicos activos en la cola
    private int size;

    // Capacidad fija total del arreglo
    private final int capacidad;

    /**
     * Constructor de la cola lineal de demostración.
     *
     * @param capacidad Tamaño físico del arreglo.
     */
    public Ej9(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0.");
        }
        this.capacidad = capacidad;
        this.elementos = new Integer[capacidad];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    /**
     * Encola un valor al final del arreglo.
     *
     * @param valor Entero a encolar.
     * @throws IllegalStateException Si el puntero rear llegó al último casillero físico.
     */
    public void enqueue(int valor) {
        // En una cola simple, el desbordamiento se evalúa solo mirando el extremo derecho:
        if (rear == capacidad - 1) {
            throw new IllegalStateException("Desbordamiento Lineal (Faux Overflow): "
                    + "El puntero 'rear' alcanzó el límite físico del arreglo (índice " + rear + "). "
                    + "No se pueden insertar más elementos, aunque existan " + front + " casilleros libres al inicio.");
        }
        elementos[++rear] = valor;
        size++;
        System.out.println("-> enqueue(" + valor + ")");
    }

    /**
     * Desencola el elemento del frente y marca la celda como null.
     *
     * @return El valor desencolado.
     */
    public int dequeue() {
        if (size == 0) {
            throw new IllegalStateException("Subdesbordamiento: La cola está vacía.");
        }
        int valor = elementos[front];

        // Anulamos la celda para visualizar explícitamente en el arreglo que la posición quedó libre
        elementos[front] = null;

        front++;
        size--;
        System.out.println("<- dequeue() extrajo: " + valor);
        return valor;
    }

    /**
     * Imprime una radiografía del estado interno de la memoria física del arreglo.
     */
    public void mostrarEstadoMemoria() {
        System.out.println("------------------------------------------------------------------------");
        System.out.println("ESTADO INTERNO DEL ARREGLO EN MEMORIA:");
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

        int casillerosDesperdiciados = front; // Todo lo que quedó a la izquierda de front
        System.out.println("Casilleros libres desperdiciados al inicio: " + casillerosDesperdiciados);
        System.out.println("------------------------------------------------------------------------");
    }

    /**
     * Secuencia de prueba que demuestra el falso desbordamiento paso a paso.
     */
    public static void main(String[] args) {
        System.out.println("========================================================================");
        System.out.println("DEMOSTRACIÓN: PROBLEMA DE DESPERDICIO DE ESPACIO EN COLA SIMPLE (CLASE Ej9)");
        System.out.println("========================================================================");

        int capacidad = 5;
        Ej9 cola = new Ej9(capacidad);

        // PASO 1: Llenar la cola con 5 elementos
        System.out.println("\n--- PASO 1: Llenando la cola con 5 elementos (10, 20, 30, 40, 50) ---");
        cola.enqueue(10);
        cola.enqueue(20);
        cola.enqueue(30);
        cola.enqueue(40);
        cola.enqueue(50);

        cola.mostrarEstadoMemoria();

        // PASO 2: Desencolar 3 elementos
        System.out.println("\n--- PASO 2: Desencolando 3 elementos (salen 10, 20 y 30) ---");
        cola.dequeue();
        cola.dequeue();
        cola.dequeue();

        cola.mostrarEstadoMemoria();

        // PASO 3: Intentar encolar un nuevo elemento (Demostración de Faux Overflow)
        System.out.println("\n--- PASO 3: Intento de encolar un nuevo valor (60) ---");
        System.out.println("Estado lógico actual: La cola solo tiene " + cola.size + " elementos ocupados.");
        System.out.println("Espacio físico disponible: Hay " + (capacidad - cola.size) + " casilleros completamente vacíos.");
        System.out.print("Ejecutando cola.enqueue(60)... ");

        try {
            cola.enqueue(60);
        } catch (IllegalStateException e) {
            System.out.println("\n¡ERROR CAPTURADO!");
            System.out.println(e.getMessage());
        }

        System.out.println("\n========================================================================");
        System.out.println("CONCLUSIÓN TÉCNICA:");
        System.out.println("En una cola simple lineal, a medida que los elementos se retiran,");
        System.out.println("el frente avanza hacia la derecha. Los casilleros 0, 1 y 2 quedaron");
        System.out.println("vacíos pero el puntero 'rear' ya no puede avanzar más allá del índice 4.");
        System.out.println("La cola queda inutilizada a pesar de tener el 60% de su espacio libre.");
        System.out.println("Solución necesaria: Implementar una COLA CIRCULAR (Ejercicio 10).");
        System.out.println("========================================================================");
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación / Ajuste del prompt:
 *    Se implementó el arreglo interno como 'Integer[]' en lugar de 'int[]' primitivo y se
 *    diseñó el método 'mostrarEstadoMemoria()' con formateo visual por columnas alineadas.
 * 
 * 2. Justificación técnica:
 *    En un arreglo primitivo 'int[]', las celdas no ocupadas contienen por defecto el valor 0,
 *    lo que genera ambigüedad entre un valor real insertado (el número 0) y una posición libre.
 *    Al usar 'Integer[]', las celdas liberadas se establecen explícitamente en 'null', lo que
 *    permite al método de diagnóstico imprimir visualmente la palabra '(libre)' en los casilleros
 *    anteriores a 'front', evidenciando de manera contundente el desperdicio de memoria física.
 * =========================================================================================
 */