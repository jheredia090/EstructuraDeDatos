/*
Prompt inicial utilizado:
Quiero implementar en Java una clase llamada Ej1 (en el paquete/carpeta Guia6) que modele una Pila (Stack) de enteros utilizando una lista enlazada simple dinámica bajo el principio LIFO (Last In, First Out).

El prompt debe explicar:
- Por qué corresponde usar una pila: se requiere una política LIFO donde el último elemento apilado sea el primero en desapilarse.
- Cómo funciona internamente y por qué 'head' representa el tope de la pila: en una lista enlazada simple, insertar y eliminar al principio (en la cabeza) es una operación inmediata de reconexión de punteros que no exige recorrer la lista. Por ende, hacer coincidir la cima (tope) con 'head' garantiza que tanto push como pop operen en tiempo constante O(1). Además, al ser dinámica, la pila no tiene límite fijo de capacidad (no sufre de desbordamiento por tamaño estático).

Estructura de clases requerida:
1. Clase Nodo: con 'int dato' y 'Nodo siguiente'.
2. Clase Ej1: con la referencia 'head' (tope de la pila) y el contador 'size'.

Operaciones a implementar:
1. void push(int dato): inserta un nuevo nodo al inicio (nuevo.siguiente = head; head = nuevo; size++). Complejidad: O(1).
2. int pop(): extrae y retorna el elemento de la cabeza (head = head.siguiente; size--). Si está vacía, lanza IllegalStateException (Stack Underflow). Complejidad: O(1).
3. int peek(): retorna head.dato sin removerlo. Lanza IllegalStateException si está vacía. Complejidad: O(1).
4. boolean estaVacia(): retorna true si head == null. Complejidad: O(1).
5. boolean buscar(int dato): recorre secuencialmente desde head hasta null verificando si el valor existe. Complejidad: O(n).
6. void imprimir(): muestra los elementos desde el tope hacia la base (ej. "Tope -> 30 -> 20 -> 10 -> Base").
7. int getSize(): retorna la cantidad de elementos apilados.

Casos a probar en main:
- Verificar estaVacia() en pila recién creada.
- Apilar los elementos 10, 20 y 30; verificar que 30 queda en el tope (peek).
- Buscar elementos existentes (20) e inexistentes (99).
- Desapilar completamente comprobando el orden LIFO (salen 30, 20, 10).
- Intentar desapilar y consultar el tope en pila vacía capturando IllegalStateException.

Ajustes realizados luego de la primera respuesta de OpenCode:
Ninguno. Hacer coincidir el tope de la pila con la cabeza de la lista enlazada permitió resolver push, pop y peek en O(1) puro sin necesidad de memoria contigua ni capacidades estáticas fijas.
*/

/**
 * Ejercicio 1: Pila enlazada de enteros.
 * Implementa la estructura de datos Pila sobre una lista enlazada simple dinámica.
 * Al hacer coincidir el tope con 'head', las operaciones LIFO operan en O(1).
 */
public class Ej1 {

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

    // 'head' actúa directamente como la cima o tope de la pila (LIFO)
    private Nodo head;

    // Contador de elementos presentes en la pila
    private int size;

    /**
     * Constructor que inicializa la pila enlazada vacía.
     */
    public Ej1() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Apila un nuevo elemento en la cima de la pila.
     * Complejidad: O(1).
     *
     * @param dato Entero a apilar.
     */
    public void push(int dato) {
        Nodo nuevo = new Nodo(dato);
        // El nuevo nodo apunta a la cabeza actual
        nuevo.siguiente = head;
        // La cabeza ahora es el nuevo nodo (nuevo tope de la pila)
        head = nuevo;
        size++;
    }

    /**
     * Desapila y retorna el elemento situado en el tope.
     * Complejidad: O(1).
     *
     * @return El entero desapilado.
     * @throws IllegalStateException Si la pila está vacía (Stack Underflow).
     */
    public int pop() {
        if (estaVacia()) {
            throw new IllegalStateException("Subdesbordamiento (Stack Underflow): La pila está vacía.");
        }
        int valor = head.dato;
        // Avanzamos head al siguiente nodo; el nodo anterior queda libre para el Garbage Collector
        head = head.siguiente;
        size--;
        return valor;
    }

    /**
     * Consulta el valor en el tope de la pila sin desapilarlo.
     * Complejidad: O(1).
     *
     * @return El entero en la cima.
     * @throws IllegalStateException Si la pila está vacía.
     */
    public int peek() {
        if (estaVacia()) {
            throw new IllegalStateException("La pila está vacía; no existe elemento en el tope.");
        }
        return head.dato;
    }

    /**
     * Verifica si la pila carece de elementos.
     * Complejidad: O(1).
     *
     * @return true si head == null; false en caso contrario.
     */
    public boolean estaVacia() {
        return head == null;
    }

    /**
     * Busca secuencialmente si un valor se encuentra dentro de la pila.
     * Complejidad: O(n).
     *
     * @param dato Entero a buscar.
     * @return true si el valor está presente; false en caso contrario.
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
     * Imprime los elementos de la pila desde el tope hasta la base.
     */
    public void imprimir() {
        if (estaVacia()) {
            System.out.println("Pila vacía: [Tope -> null]");
            return;
        }
        System.out.print("Tope -> ");
        Nodo actual = head;
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
     * Retorna la cantidad de elementos apilados.
     */
    public int getSize() {
        return size;
    }

    /**
     * Prueba de ejecución con los casos definidos en el prompt.
     */
    public static void main(String[] args) {
        System.out.println("========================================================================");
        System.out.println("PRUEBA: EJERCICIO 1 - PILA ENLAZADA DE ENTEROS (CLASE Ej1)");
        System.out.println("========================================================================");

        Ej1 pila = new Ej1();

        // 1. Estado inicial
        System.out.println("¿Está vacía al inicio? " + pila.estaVacia() + " | Tamaño: " + pila.getSize());
        pila.imprimir();

        // 2. Apilando elementos (push)
        System.out.println("\n--- Apilando elementos: 10, 20, 30 ---");
        pila.push(10);
        pila.imprimir();
        pila.push(20);
        pila.imprimir();
        pila.push(30);
        pila.imprimir();

        System.out.println("Elemento en el tope (peek): " + pila.peek());
        System.out.println("Tamaño de la pila: " + pila.getSize());

        // 3. Búsqueda de elementos
        System.out.println("\n--- Búsqueda de elementos ---");
        System.out.println("buscar(20): " + pila.buscar(20) + " (esperado: true)");
        System.out.println("buscar(99): " + pila.buscar(99) + " (esperado: false)");

        // 4. Desapilando elementos (LIFO)
        System.out.println("\n--- Desapilando elementos (orden LIFO) ---");
        while (!pila.estaVacia()) {
            System.out.println("pop() extrajo: " + pila.pop() + " | Elementos restantes: " + pila.getSize());
            pila.imprimir();
        }

        // 5. Casos límite de Underflow
        System.out.println("\n--- Casos límite en pila vacía ---");
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

        System.out.println("========================================================================");
    }
}
