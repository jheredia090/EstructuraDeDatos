/*
Prompt inicial utilizado:
Quiero implementar en Java una clase genérica llamada Ej4 que modele una Cola (Queue) utilizando una lista enlazada simple dinámica capaz de almacenar cualquier tipo de dato bajo el principio FIFO (First In, First Out).

El prompt debe explicar detalladamente:
- Por qué corresponde usar una cola y cómo se mantiene el comportamiento FIFO independientemente del tipo de dato: la política FIFO está determinada por la topología de la estructura (las inserciones ocurren exclusivamente por 'tail' y las extracciones exclusivamente por 'head'); la naturaleza o tipo del dato genérico <T> almacenado dentro del nodo no altera en absoluto cómo se encadenan o desencadenan los punteros en el Heap.
- Manejo de dos referencias ('head' y 'tail') para garantizar que enqueue y dequeue operen en tiempo constante O(1).
- Uso de Objects.equals() para comparar elementos en el método buscar().

Estructura de clases requerida:
1. Clase Nodo<T>: con 'T dato' y 'Nodo<T> siguiente'.
2. Clase Ej4<T>: con 'head', 'tail' y 'size'.

Operaciones a implementar:
- void enqueue(T dato): encola al final mediante tail en O(1).
- T dequeue(): desencola del frente mediante head en O(1). Lanza IllegalStateException si está vacía.
- T front(): retorna head.dato sin removerlo en O(1). Lanza IllegalStateException si está vacía.
- boolean estaVacia(): retorna true si head == null en O(1).
- boolean buscar(T dato): búsqueda secuencial en O(n) usando Objects.equals().
- void imprimir(): muestra los elementos desde el frente hacia el final.
- int getSize(): retorna la cantidad de elementos.

Casos a probar en main:
- Caso 1: Cola de nombres de personas (Ej4<String>) con "Ana", "Carlos", "Beatriz", verificando que son desencolados en estricto orden de llegada.
- Caso 2: Cola de objetos propios Cliente (record Cliente(int id, String nombre, String categoria)), verificando encolado, consulta del frente, búsqueda y atención completa.
- Caso 3: Validar excepción al intentar desencolar en una cola vacía.

Ajustes realizados luego de la primera respuesta de OpenCode:
Ninguno. La separación entre el tipo parametrizado <T> y la lógica de avance por 'head' y 'tail' aseguró que la semántica FIFO funcione de manera exactamente idéntica tanto para cadenas de texto como para registros estructurados de Cliente.
*/

import java.util.Objects;

/**
 * Ejercicio 4: Cola enlazada genérica Cola<T>.
 * Demuestra el principio FIFO (First In, First Out) aplicado a tipos de datos arbitrarios.
 *
 * @param <T> Tipo de los elementos encolados.
 */
public class Ej4<T> {

    /**
     * Nodo genérico que contiene un elemento de tipo T y un enlace al siguiente.
     */
    public static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;

        public Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    // Puntero de extracción (frente de la cola)
    private Nodo<T> head;

    // Puntero de inserción (final de la cola)
    private Nodo<T> tail;

    // Contador de elementos presentes en la cola
    private int size;

    /**
     * Constructor que inicializa una cola genérica vacía.
     */
    public Ej4() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Encola un nuevo elemento de tipo T al final de la cola.
     * Complejidad: O(1).
     *
     * @param dato Elemento a encolar.
     */
    public void enqueue(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);

        if (estaVacia()) {
            head = nuevo;
            tail = nuevo;
        } else {
            tail.siguiente = nuevo;
            tail = nuevo;
        }
        size++;
    }

    /**
     * Desencola y retorna el elemento situado al frente de la cola.
     * Complejidad: O(1).
     *
     * @return El elemento del frente.
     * @throws IllegalStateException Si la cola está vacía.
     */
    public T dequeue() {
        if (estaVacia()) {
            throw new IllegalStateException("Subdesbordamiento (Queue Underflow): La cola está vacía.");
        }

        T valor = head.dato;
        head = head.siguiente;
        size--;

        // Si la cola se vació por completo, se actualiza tail a null
        if (head == null) {
            tail = null;
        }

        return valor;
    }

    /**
     * Consulta el elemento al frente de la cola sin removerlo.
     * Complejidad: O(1).
     *
     * @return El elemento en el frente.
     * @throws IllegalStateException Si la cola está vacía.
     */
    public T front() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola está vacía; no hay elemento en el frente.");
        }
        return head.dato;
    }

    /**
     * Verifica si la cola carece de elementos.
     */
    public boolean estaVacia() {
        return head == null;
    }

    /**
     * Busca secuencialmente si un elemento existe en la cola usando Objects.equals().
     * Complejidad: O(n).
     *
     * @param dato Elemento a buscar.
     * @return true si se encuentra un elemento coincidente; false en caso contrario.
     */
    public boolean buscar(T dato) {
        Nodo<T> actual = head;
        while (actual != null) {
            if (Objects.equals(actual.dato, dato)) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    /**
     * Imprime los elementos de la cola desde el frente hasta el final.
     */
    public void imprimir() {
        if (estaVacia()) {
            System.out.println("Cola vacía: [Frente -> null <- Final]");
            return;
        }
        System.out.print("Frente -> ");
        Nodo<T> actual = head;
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
     * Retorna la cantidad de elementos encolados.
     */
    public int getSize() {
        return size;
    }

    /**
     * Objeto de dominio solicitado por la consigna para probar la cola genérica.
     */
    public record Cliente(int id, String nombre, String categoria) {
        @Override
        public String toString() {
            return String.format("Cliente #%d: %s (%s)", id, nombre, categoria);
        }
    }

    /**
     * Pruebas de ejecución con cola de Strings y cola de objetos Cliente.
     */
    public static void main(String[] args) {
        System.out.println("========================================================================");
        System.out.println("PRUEBA: EJERCICIO 4 - COLA ENLAZADA GENÉRICA <T> (CLASE Ej4)");
        System.out.println("========================================================================");

        // 1. Caso 1: Cola de nombres de personas (Ej4<String>)
        System.out.println("--- 1. Cola de nombres de personas (Ej4<String>) ---");
        Ej4<String> colaNombres = new Ej4<>();
        colaNombres.enqueue("Ana");
        colaNombres.enqueue("Carlos");
        colaNombres.enqueue("Beatriz");
        colaNombres.imprimir();

        System.out.println("Primero en ser atendido (front): " + colaNombres.front());
        System.out.println("buscar(\"Carlos\"): " + colaNombres.buscar("Carlos"));
        System.out.println("buscar(\"Julian\"): " + colaNombres.buscar("Julian"));

        System.out.println("Desencolando en orden FIFO:");
        while (!colaNombres.estaVacia()) {
            System.out.println("dequeue(): " + colaNombres.dequeue());
        }

        // 2. Caso 2: Cola de objetos propios Cliente (Ej4<Cliente>)
        System.out.println("\n--- 2. Cola de objetos Cliente (Ej4<Cliente>) ---");
        Ej4<Cliente> colaClientes = new Ej4<>();
        Cliente c1 = new Cliente(101, "Lucia Perez", "Premium");
        Cliente c2 = new Cliente(102, "Martin Gomez", "Regular");
        Cliente c3 = new Cliente(103, "Valeria Diaz", "Corporativo");

        colaClientes.enqueue(c1);
        colaClientes.enqueue(c2);
        colaClientes.enqueue(c3);
        colaClientes.imprimir();

        System.out.println("\nPróximo cliente a atender: " + colaClientes.front());
        System.out.println("¿Está registrado Martin Gomez? " 
                + colaClientes.buscar(new Cliente(102, "Martin Gomez", "Regular")));

        System.out.println("\nAtendiendo fila de clientes:");
        while (!colaClientes.estaVacia()) {
            System.out.println("Atendido -> " + colaClientes.dequeue());
        }

        // 3. Caso límite de Underflow
        System.out.println("\n--- 3. Caso límite: Intento de dequeue en cola vacía ---");
        try {
            colaClientes.dequeue();
        } catch (IllegalStateException e) {
            System.out.println("Excepción capturada con éxito: " + e.getMessage());
        }

        System.out.println("========================================================================");
    }
}
