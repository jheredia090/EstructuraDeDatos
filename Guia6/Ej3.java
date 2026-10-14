/*
Prompt inicial utilizado:
Quiero implementar en Java una clase genérica llamada Ej3 (o clase interna Pila<T>) que modele una Pila utilizando una lista enlazada simple dinámica capaz de almacenar cualquier tipo de dato (como Integer, String u objetos propios).

El prompt debe explicar detalladamente:
- Qué significa usar genéricos (<T>) en Java: parametrizar la estructura con un tipo de dato abstracto que garantiza seguridad de tipos en tiempo de compilación (Type Safety), evitando duplicación de clases (no hace falta crear PilaEnteros, PilaStrings, etc.) y eliminando castings manuales en tiempo de ejecución.
- Por qué la lógica de la pila no depende del tipo de dato: la estructura se encarga únicamente de manipular las referencias del nodo ('head' y 'siguiente') bajo la política LIFO; el contenido almacenado es ortogonal a cómo se conectan y desconectan los nodos en el Heap.
- Comparación de objetos: para el método buscar(), se debe utilizar Objects.equals(actual.dato, dato) en lugar de '==' para respetar la igualdad semántica de los objetos genéricos.

Estructura de clases requerida:
1. Clase Nodo<T>: con 'T dato' y 'Nodo<T> siguiente'.
2. Clase Ej3<T>: con 'Nodo<T> head' y 'int size'.

Operaciones a implementar:
- void push(T dato): apila en la cima en O(1).
- T pop(): desapila y retorna el dato en O(1). Lanza IllegalStateException si está vacía.
- T peek(): retorna el dato del tope sin removerlo en O(1). Lanza IllegalStateException si está vacía.
- boolean estaVacia(): retorna true si head == null en O(1).
- boolean buscar(T dato): búsqueda secuencial en O(n) usando Objects.equals().
- void imprimir(): muestra los elementos desde el tope hacia la base.
- int getSize(): retorna la cantidad de elementos.

Casos a probar en main:
- Pila<Integer>: apilar 10, 20, 30; verificar peek() y desapilar.
- Pila<String>: apilar "Primero", "Segundo", "Tercero"; verificar LIFO y buscar("Segundo").
- Pila<Libro>: usar un record Libro(String titulo, String autor) para demostrar el funcionamiento con objetos propios del dominio.
- Validar excepción al intentar pop() en una pila vacía.

Ajustes realizados luego de la primera respuesta de OpenCode:
Ninguno. La parametrización con <T> y el uso de Objects.equals() para comparar datos permitieron que la misma estructura de punteros opere con tipos primitivos envueltos (Integer), cadenas (String) y objetos propios (records), garantizando Type Safety estricto.
*/

import java.util.Objects;

/**
 * Ejercicio 3: Pila enlazada genérica Pila<T>.
 * Demuestra la independencia entre la estructura topológica de punteros LIFO
 * y el tipo de dato que viaja dentro de cada nodo.
 *
 * @param <T> Tipo de los elementos que almacenará la pila.
 */
public class Ej3<T> {

    /**
     * Nodo genérico que contiene un dato de tipo T y una referencia al siguiente nodo.
     */
    public static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;

        public Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    // Referencia al tope de la pila (coincide con head)
    private Nodo<T> head;

    // Contador de elementos presentes
    private int size;

    /**
     * Constructor que inicia una pila genérica vacía.
     */
    public Ej3() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Apila un nuevo elemento en la cima.
     * Complejidad: O(1).
     *
     * @param dato Elemento de tipo T a apilar.
     */
    public void push(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.siguiente = head;
        head = nuevo;
        size++;
    }

    /**
     * Desapila y retorna el elemento en la cima.
     * Complejidad: O(1).
     *
     * @return El elemento de tipo T que estaba en la cima.
     * @throws IllegalStateException Si la pila está vacía.
     */
    public T pop() {
        if (estaVacia()) {
            throw new IllegalStateException("Subdesbordamiento (Stack Underflow): La pila está vacía.");
        }
        T valor = head.dato;
        head = head.siguiente;
        size--;
        return valor;
    }

    /**
     * Consulta el elemento en la cima sin retirarlo.
     * Complejidad: O(1).
     *
     * @return El elemento en la cima.
     * @throws IllegalStateException Si la pila está vacía.
     */
    public T peek() {
        if (estaVacia()) {
            throw new IllegalStateException("La pila está vacía; no hay elemento en el tope.");
        }
        return head.dato;
    }

    /**
     * Indica si la pila no tiene elementos.
     */
    public boolean estaVacia() {
        return head == null;
    }

    /**
     * Busca secuencialmente si un elemento está en la pila usando Objects.equals().
     * Complejidad: O(n).
     *
     * @param dato Elemento a buscar.
     * @return true si se encuentra un nodo equivalente; false en caso contrario.
     */
    public boolean buscar(T dato) {
        Nodo<T> actual = head;
        while (actual != null) {
            // En genéricos se usa Objects.equals para comparar por contenido y prevenir NullPointerException
            if (Objects.equals(actual.dato, dato)) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    /**
     * Imprime el contenido de la pila desde el tope hacia la base.
     */
    public void imprimir() {
        if (estaVacia()) {
            System.out.println("Pila vacía: [Tope -> null]");
            return;
        }
        System.out.print("Tope -> ");
        Nodo<T> actual = head;
        while (actual != null) {
            System.out.print(actual.dato);
            if (actual.siguiente != null) {
                System.out.print(" -> ");
            }
            actual = actual.siguiente;
        }
        System.out.println(" -> Base");
    }

    /**
     * Retorna la cantidad de elementos en la pila.
     */
    public int getSize() {
        return size;
    }

    /**
     * Objeto de dominio propio para probar la pila con clases de usuario.
     */
    public record Libro(String titulo, String autor) {
        @Override
        public String toString() {
            return String.format("\"%s\" (%s)", titulo, autor);
        }
    }

    /**
     * Prueba de ejecución con múltiples tipos parametrizados.
     */
    public static void main(String[] args) {
        System.out.println("========================================================================");
        System.out.println("PRUEBA: EJERCICIO 3 - PILA ENLAZADA GENÉRICA <T> (CLASE Ej3)");
        System.out.println("========================================================================");

        // 1. Pila de enteros (Integer)
        System.out.println("--- 1. Pila con tipo Integer (Ej3<Integer>) ---");
        Ej3<Integer> pilaEnteros = new Ej3<>();
        pilaEnteros.push(10);
        pilaEnteros.push(20);
        pilaEnteros.push(30);
        pilaEnteros.imprimir();
        System.out.println("Tope actual: " + pilaEnteros.peek());
        System.out.println("pop() extrajo: " + pilaEnteros.pop());
        pilaEnteros.imprimir();

        // 2. Pila de cadenas (String)
        System.out.println("\n--- 2. Pila con tipo String (Ej3<String>) ---");
        Ej3<String> pilaTextos = new Ej3<>();
        pilaTextos.push("Primero");
        pilaTextos.push("Segundo");
        pilaTextos.push("Tercero");
        pilaTextos.imprimir();
        System.out.println("buscar(\"Segundo\"): " + pilaTextos.buscar("Segundo") + " (esperado: true)");
        System.out.println("buscar(\"Inexistente\"): " + pilaTextos.buscar("Inexistente") + " (esperado: false)");
        System.out.println("Desapilando en orden LIFO:");
        while (!pilaTextos.estaVacia()) {
            System.out.println("pop(): " + pilaTextos.pop());
        }

        // 3. Pila de objetos propios (Libro)
        System.out.println("\n--- 3. Pila con objetos propios de dominio (Ej3<Libro>) ---");
        Ej3<Libro> pilaLibros = new Ej3<>();
        pilaLibros.push(new Libro("El Quijote", "Cervantes"));
        pilaLibros.push(new Libro("Cien Años de Soledad", "García Márquez"));
        pilaLibros.push(new Libro("Ficciones", "Borges"));
        pilaLibros.imprimir();

        System.out.println("Libro en la cima: " + pilaLibros.peek());
        System.out.println("buscar(\"Ficciones\", \"Borges\"): " 
                + pilaLibros.buscar(new Libro("Ficciones", "Borges")) + " (esperado: true)");

        // 4. Validación de caso límite de pila vacía
        System.out.println("\n--- 4. Caso límite: Intento de pop en pila vacía ---");
        Ej3<Double> pilaVacia = new Ej3<>();
        try {
            pilaVacia.pop();
        } catch (IllegalStateException e) {
            System.out.println("Excepción capturada con éxito: " + e.getMessage());
        }

        System.out.println("========================================================================");
    }
}
