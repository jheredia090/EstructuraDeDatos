/*
Prompt inicial utilizado:
Quiero implementar en Java una clase llamada Ej2 (en el paquete/carpeta Guia6) que modele una Cola (Queue) de enteros utilizando una lista enlazada simple dinámica bajo el principio FIFO (First In, First Out).

El prompt debe explicar detalladamente:
- Por qué corresponde usar una cola: se requiere una política FIFO donde el primer elemento ingresado sea el primero en ser atendido/retirado.
- Por qué la cola necesita dos referencias, 'head' y 'tail':
  * 'head' apunta al frente de la cola, permitiendo desencolar (dequeue) en O(1) con head = head.siguiente.
  * 'tail' apunta al final de la cola, permitiendo encolar (enqueue) en O(1) con tail.siguiente = nuevo; tail = nuevo;.
- Qué problema aparecería si solamente se usara 'head':
  Si solo existiera head, para encolar un nuevo elemento al final de la lista sería obligatorio recorrerla secuencialmente nodo por nodo desde el inicio, degradando la complejidad de enqueue a O(n). El puntero 'tail' evita este recorrido y garantiza O(1) en ambas operaciones clave.

Estructura de clases requerida:
1. Clase Nodo: con 'int dato' y 'Nodo siguiente'.
2. Clase Ej2: con 'head', 'tail' y 'size'.

Operaciones a implementar:
1. void enqueue(int dato): agrega al final. Si la cola está vacía, head y tail apuntan al nuevo nodo. Si no, tail.siguiente = nuevo y tail = nuevo. Complejidad: O(1).
2. int dequeue(): extrae del frente (head = head.siguiente). Si la cola queda vacía, tail también debe actualizarse a null. Lanza IllegalStateException si está vacía (Queue Underflow). Complejidad: O(1).
3. int front(): retorna head.dato sin removerlo. Lanza IllegalStateException si está vacía. Complejidad: O(1).
4. boolean estaVacia(): retorna true si head == null (o size == 0). Complejidad: O(1).
5. boolean buscar(int dato): recorre secuencialmente desde head hasta null verificando si el valor existe. Complejidad: O(n).
6. void imprimir(): muestra los elementos desde el frente hacia el final (ej. "Frente -> 10 -> 20 -> 30 -> Final").
7. int getSize(): retorna la cantidad de elementos.

Casos a probar en main:
- Verificar estaVacia() en cola recién creada.
- Encolar 10, 20 y 30; verificar que front() devuelve 10 (el primero que entró).
- Buscar elementos existentes (20) e inexistentes (50).
- Desencolar elementos verificando que salen en orden FIFO (10, luego 20, luego 30).
- Desencolar hasta vaciar y verificar que tail quede en null y estaVacia() sea true.
- Intentar desencolar en cola vacía para verificar la captura de IllegalStateException.

Ajustes realizados luego de la primera respuesta de OpenCode:
Ninguno. El uso conjunto de head y tail resolvió la asimetría natural de las listas enlazadas simples, permitiendo que tanto la extracción en el frente como la inserción en el extremo final operen en tiempo constante O(1).
*/

/**
 * Ejercicio 2: Cola enlazada de enteros.
 * Modela la estructura FIFO mediante dos referencias: head (frente) y tail (final),
 * garantizando que enqueue() y dequeue() operen en tiempo constante O(1).
 */
public class Ej2 {

    /**
     * Nodo interno que almacena el entero y la referencia al siguiente nodo.
     */
    public static class Nodo {
        int dato;
        Nodo siguiente;

        public Nodo(int dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    // 'head' apunta al frente de la cola (por donde se desencola)
    private Nodo head;

    // 'tail' apunta al final de la cola (por donde se encola)
    private Nodo tail;

    // Contador de elementos presentes en la cola
    private int size;

    /**
     * Constructor que inicializa una cola enlazada vacía.
     */
    public Ej2() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Inserta un nuevo elemento al final de la cola (enqueue).
     * Complejidad: O(1) gracias al puntero 'tail'.
     *
     * @param dato Entero a encolar.
     */
    public void enqueue(int dato) {
        Nodo nuevo = new Nodo(dato);

        // Si la cola está vacía, el nuevo nodo es tanto el frente (head) como el final (tail)
        if (estaVacia()) {
            head = nuevo;
            tail = nuevo;
        } else {
            // El nodo que estaba al final apunta al nuevo nodo
            tail.siguiente = nuevo;
            // 'tail' avanza para ser el nuevo último nodo
            tail = nuevo;
        }
        size++;
    }

    /**
     * Extrae y retorna el elemento situado al frente de la cola (dequeue).
     * Complejidad: O(1).
     *
     * @return El entero que estaba al frente.
     * @throws IllegalStateException Si la cola está vacía (Queue Underflow).
     */
    public int dequeue() {
        if (estaVacia()) {
            throw new IllegalStateException("Subdesbordamiento (Queue Underflow): La cola está vacía.");
        }

        int valor = head.dato;
        // Avanzamos 'head' al siguiente nodo
        head = head.siguiente;
        size--;

        // Caso especial: si al desencolar la lista quedó vacía,
        // 'tail' también debe actualizarse a null para no apuntar a un nodo descartado
        if (head == null) {
            tail = null;
        }

        return valor;
    }

    /**
     * Consulta el elemento que está al frente de la cola sin removerlo.
     * Complejidad: O(1).
     *
     * @return El entero en el frente.
     * @throws IllegalStateException Si la cola está vacía.
     */
    public int front() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola está vacía; no hay elemento en el frente.");
        }
        return head.dato;
    }

    /**
     * Evalúa si la cola carece de elementos.
     * Complejidad: O(1).
     *
     * @return true si head == null; false en caso contrario.
     */
    public boolean estaVacia() {
        return head == null;
    }

    /**
     * Busca si un valor está presente en la cola mediante recorrido secuencial.
     * Complejidad: O(n).
     *
     * @param dato Entero a buscar.
     * @return true si el dato existe; false si se llega a null sin encontrarlo.
     */
    public boolean buscar(int dato) {
        Nodo actual = head;
        while (actual != null) {
            if (actual.dato == dato) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    /**
     * Imprime el contenido de la cola desde el frente hasta el final.
     */
    public void imprimir() {
        if (estaVacia()) {
            System.out.println("Cola vacía: [Frente -> null <- Final]");
            return;
        }
        System.out.print("Frente -> ");
        Nodo actual = head;
        while (actual != null) {
            System.out.print(actual.dato);
            if (actual.siguiente != null) {
                System.out.print(" -> ");
            }
            actual = actual.siguiente;
        }
        System.out.println(" <- Final");
    }

    /**
     * Retorna la cantidad de elementos en la cola.
     */
    public int getSize() {
        return size;
    }

    /**
     * Prueba de ejecución completa con los casos del prompt.
     */
    public static void main(String[] args) {
        System.out.println("========================================================================");
        System.out.println("PRUEBA: EJERCICIO 2 - COLA ENLAZADA DE ENTEROS (CLASE Ej2)");
        System.out.println("========================================================================");

        Ej2 cola = new Ej2();

        // 1. Estado inicial
        System.out.println("¿Está vacía al inicio? " + cola.estaVacia() + " | Tamaño: " + cola.getSize());
        cola.imprimir();

        // 2. Encolando elementos (enqueue)
        System.out.println("\n--- Encolando elementos: 10, 20, 30 ---");
        cola.enqueue(10);
        cola.imprimir();
        cola.enqueue(20);
        cola.imprimir();
        cola.enqueue(30);
        cola.imprimir();

        System.out.println("Elemento al frente (front): " + cola.front() + " (esperado: 10 - primero que entró)");
        System.out.println("Tamaño de la cola: " + cola.getSize());

        // 3. Búsqueda de elementos
        System.out.println("\n--- Búsqueda de elementos ---");
        System.out.println("buscar(20): " + cola.buscar(20) + " (esperado: true)");
        System.out.println("buscar(50): " + cola.buscar(50) + " (esperado: false)");

        // 4. Desencolando elementos (FIFO: First In, First Out)
        System.out.println("\n--- Desencolando elementos (orden FIFO) ---");
        while (!cola.estaVacia()) {
            System.out.println("dequeue() extrajo: " + cola.dequeue() + " | Elementos restantes: " + cola.getSize());
            cola.imprimir();
        }

        System.out.println("¿Está vacía la cola al finalizar? " + cola.estaVacia());

        // 5. Casos límite de Underflow
        System.out.println("\n--- Casos límite en cola vacía ---");
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

        System.out.println("========================================================================");
    }
}
