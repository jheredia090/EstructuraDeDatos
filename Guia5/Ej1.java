/*
Prompt inicial utilizado:
Quiero implementar en Java una lista enlazada simple de números enteros desde cero (en el archivo Ej1.java), sin utilizar estructuras predefinidas de Java como ArrayList o LinkedList.

Estructura de clases requerida:
1. Clase Nodo: con un atributo entero 'dato' y una referencia 'siguiente' al próximo nodo.
2. Clase Ej1 (ListaEnlazada): con la referencia 'head' y el atributo 'size'.

El prompt debe explicar:
- Cómo se representa un nodo: una clase que combina un valor de información con un puntero o referencia hacia el siguiente nodo en el Heap.
- Qué función cumple 'head': es la única referencia que el programa guarda de toda la lista; a través de head se accede a todos los demás elementos. Si head es null, la lista está vacía.
- Cómo se actualiza el tamaño (size): se incrementa en 1 en cada inserción para poder saber cuántos elementos hay sin tener que recorrer toda la lista.

Métodos a implementar:
- void insertarAlInicio(int dato): crea el nodo y lo enlaza como nuevo head en O(1).
- void insertarAlFinal(int dato): si la lista está vacía asigna head; si no, recorre hasta el último nodo y lo enlaza al final en O(n).
- void imprimir(): muestra los elementos en formato visual "10 -> 20 -> null".
- boolean estaVacia(): retorna true si head == null.
- int getSize(): retorna la cantidad de elementos.

Casos a probar en main:
- Lista vacía inicial (verificar estaVacia() y getSize() == 0).
- Inserciones al inicio (ej. 20, luego 10) y verificar que 10 queda primero.
- Inserciones al final (ej. 30, luego 40).
- Impresión completa de la lista y verificación del tamaño final.

Ajustes realizados luego de la primera respuesta de OpenCode:
Ninguno. El diseño inicial funcionó correctamente; la clase Nodo se definió como clase interna estática dentro de Ej1 para mantener todo el código en un único archivo ejecutable.
*/

/**
 * Ejercicio 1: Crear una lista enlazada simple desde cero.
 * Modela la estructura básica mediante nodos y referencias unidireccionales.
 */
public class Ej1 {

    /**
     * Representación de un nodo individual de la lista enlazada.
     */
    public static class Nodo {
        int dato;
        Nodo siguiente;

        public Nodo(int dato) {
            this.dato = dato;
            this.siguiente = null; // Al crearse, todavía no apunta a ningún sucesor
        }
    }

    // Referencia al primer nodo de la lista (cabeza)
    private Nodo head;

    // Contador de elementos de la lista
    private int size;

    /**
     * Constructor que inicia la lista enlazada vacía.
     */
    public Ej1() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Inserta un nuevo elemento al comienzo de la lista.
     * Complejidad: O(1).
     *
     * @param dato Valor entero a insertar.
     */
    public void insertarAlInicio(int dato) {
        Nodo nuevo = new Nodo(dato);
        // El nuevo nodo apunta al nodo que antes era el primero
        nuevo.siguiente = head;
        // Ahora la cabeza de la lista pasa a ser el nuevo nodo
        head = nuevo;
        size++;
    }

    /**
     * Inserta un nuevo elemento al final de la lista.
     * Complejidad: O(n).
     *
     * @param dato Valor entero a insertar.
     */
    public void insertarAlFinal(int dato) {
        Nodo nuevo = new Nodo(dato);

        // Si la lista está vacía, el nuevo nodo se convierte en la cabeza
        if (estaVacia()) {
            head = nuevo;
        } else {
            // Recorremos desde head hasta encontrar el último nodo (aquel cuyo siguiente es null)
            Nodo actual = head;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            // Enlazamos el último nodo con el nuevo nodo
            actual.siguiente = nuevo;
        }
        size++;
    }

    /**
     * Imprime el contenido de la lista en consola con formato visual de flechas.
     */
    public void imprimir() {
        if (estaVacia()) {
            System.out.println("Lista vacía: null");
            return;
        }

        Nodo actual = head;
        while (actual != null) {
            System.out.print(actual.dato + " -> ");
            actual = actual.siguiente;
        }
        System.out.println("null");
    }

    /**
     * Determina si la lista carece de elementos.
     *
     * @return true si head es null (o size es 0); false en caso contrario.
     */
    public boolean estaVacia() {
        return head == null;
    }

    /**
     * Retorna la cantidad de elementos en la lista.
     *
     * @return El valor actual de size en tiempo constante O(1).
     */
    public int getSize() {
        return size;
    }

    /**
     * Prueba de ejecución con los casos planteados en el prompt.
     */
    public static void main(String[] args) {
        System.out.println("========================================================================");
        System.out.println("PRUEBA: EJERCICIO 1 - LISTA ENLAZADA SIMPLE DESDE CERO");
        System.out.println("========================================================================");

        Ej1 lista = new Ej1();

        // 1. Estado inicial
        System.out.println("¿Está vacía al inicio? " + lista.estaVacia() + " | Tamaño: " + lista.getSize());
        lista.imprimir();

        // 2. Inserciones al inicio
        System.out.println("\n--- Inserciones al inicio (20, luego 10) ---");
        lista.insertarAlInicio(20);
        lista.insertarAlInicio(10);
        lista.imprimir();
        System.out.println("Tamaño: " + lista.getSize());

        // 3. Inserciones al final
        System.out.println("\n--- Inserciones al final (30, luego 40) ---");
        lista.insertarAlFinal(30);
        lista.insertarAlFinal(40);
        lista.imprimir();
        System.out.println("Tamaño: " + lista.getSize());
        System.out.println("¿Está vacía ahora? " + lista.estaVacia());
        System.out.println("========================================================================");
    }
}
