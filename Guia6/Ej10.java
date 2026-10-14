/*
Prompt inicial utilizado:
Actúa como un desarrollador Java experto en estructuras de datos. Implementa el Ejercicio 10 de la Guía 6: Lista Doblemente Enlazada Genérica propia (Ej10<T> / ListaDoble<T>).

Requisitos específicos:
1. Clase genérica Ej10<T> con clase interna estática privada NodoDoble<T> (con atributos: T dato, NodoDoble<T> anterior, NodoDoble<T> siguiente).
2. Mantener referencias a head (primer nodo), tail (último nodo) y tamano (int).
3. Operaciones a implementar:
   - void insertarAlInicio(T dato): O(1).
   - void insertarAlFinal(T dato): O(1).
   - boolean eliminar(T dato): localiza y elimina la primera coincidencia reconectando punteros 'anterior' y 'siguiente'. Debe cubrir exhaustivamente todos los casos borde: único nodo, eliminación en head, eliminación en tail, nodo intermedio y elemento no encontrado.
   - T eliminarAlInicio(): O(1), retorna el dato eliminado. Lanza NoSuchElementException si está vacía.
   - T eliminarAlFinal(): O(1), retorna el dato eliminado. Lanza NoSuchElementException si está vacía.
   - void imprimirHaciaAdelante(): recorre desde head hacia tail mostrando la lista.
   - void imprimirHaciaAtras(): recorre desde tail hacia head demostrando la bidireccionalidad real de los punteros 'anterior'.
   - boolean estaVacia(), int getTamano(), boolean contiene(T dato).
4. Justificar en la cabecera las ventajas y desventajas de la lista doblemente enlazada frente a la simplemente enlazada (bidireccionalidad y facilidad de borrado vs mayor sobrecarga de memoria por nodo).
5. En el método main de la clase Ej10, probar exhaustivamente todos los métodos con tipos String e Integer: inserciones combinadas, recorridos en ambos sentidos, eliminaciones en todas las posiciones críticas y casos límite.

Ajustes realizados luego de la primera respuesta de OpenCode:
Se aseguraron todas las reconexiones de punteros para prevenir fugas de memoria o estados inconsistentes: al eliminar un nodo intermedio se limpian sus referencias 'anterior' y 'siguiente' a null, y se implementaron tanto 'eliminar' por valor como 'eliminarAlInicio' y 'eliminarAlFinal' con retorno del elemento en O(1).
*/

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Ejercicio 10: Lista Doblemente Enlazada Genérica (Ej10<T>).
 *
 * Justificación teórica y balance de ventajas / desventajas:
 * 1. Ventajas:
 *    - Navegación bidireccional completa: Permite iterar hacia adelante (head -> tail)
 *      y hacia atrás (tail -> head) de forma directa en O(N) sin estructuras auxiliares.
 *    - Inserción y extracción en ambos extremos en tiempo O(1) manteniendo referencias a head y tail.
 *    - Facilidad de eliminación: Conocer el puntero a un nodo permite desconectarlo en O(1)
 *      sin tener que recorrer la lista desde el inicio buscando su predecesor.
 * 2. Desventajas:
 *    - Mayor costo de memoria espacial: Cada nodo almacena una referencia adicional ('anterior').
 *    - Mayor complejidad en las operaciones de inserción y borrado: Cada modificación exige
 *      actualizar de forma coordinada hasta cuatro referencias de punteros.
 */
public class Ej10<T> {

    /**
     * Nodo genérico doblemente enlazado.
     */
    private static class NodoDoble<T> {
        T dato;
        NodoDoble<T> anterior;
        NodoDoble<T> siguiente;

        public NodoDoble(T dato) {
            this.dato = dato;
            this.anterior = null;
            this.siguiente = null;
        }
    }

    // Puntero al primer nodo de la lista
    private NodoDoble<T> head;

    // Puntero al último nodo de la lista
    private NodoDoble<T> tail;

    // Cantidad actual de elementos almacenados
    private int tamano;

    /**
     * Constructor que inicializa la lista doblemente enlazada vacía.
     */
    public Ej10() {
        this.head = null;
        this.tail = null;
        this.tamano = 0;
    }

    /**
     * Inserta un nuevo elemento al comienzo de la lista en tiempo O(1).
     *
     * @param dato Elemento a insertar.
     */
    public void insertarAlInicio(T dato) {
        NodoDoble<T> nuevo = new NodoDoble<>(dato);
        if (estaVacia()) {
            head = nuevo;
            tail = nuevo;
        } else {
            nuevo.siguiente = head;
            head.anterior = nuevo;
            head = nuevo;
        }
        tamano++;
    }

    /**
     * Inserta un nuevo elemento al final de la lista en tiempo O(1).
     *
     * @param dato Elemento a insertar.
     */
    public void insertarAlFinal(T dato) {
        NodoDoble<T> nuevo = new NodoDoble<>(dato);
        if (estaVacia()) {
            head = nuevo;
            tail = nuevo;
        } else {
            tail.siguiente = nuevo;
            nuevo.anterior = tail;
            tail = nuevo;
        }
        tamano++;
    }

    /**
     * Elimina la primera ocurrencia del elemento especificado en la lista.
     * Gestiona exhaustivamente todos los casos borde posibles.
     *
     * @param dato Elemento a eliminar.
     * @return true si el elemento fue encontrado y eliminado; false si no existía.
     */
    public boolean eliminar(T dato) {
        if (estaVacia()) {
            return false;
        }

        NodoDoble<T> actual = head;
        while (actual != null && !Objects.equals(actual.dato, dato)) {
            actual = actual.siguiente;
        }

        if (actual == null) {
            return false; // Elemento no encontrado
        }

        // Caso 1: La lista tiene un único nodo
        if (head == tail) {
            head = null;
            tail = null;
        }
        // Caso 2: El nodo a eliminar es la cabeza (head)
        else if (actual == head) {
            head = head.siguiente;
            head.anterior = null;
        }
        // Caso 3: El nodo a eliminar es la cola (tail)
        else if (actual == tail) {
            tail = tail.anterior;
            tail.siguiente = null;
        }
        // Caso 4: El nodo a eliminar es un elemento intermedio
        else {
            actual.anterior.siguiente = actual.siguiente;
            actual.siguiente.anterior = actual.anterior;
        }

        // Limpiar referencias del nodo desconectado
        actual.siguiente = null;
        actual.anterior = null;
        tamano--;
        return true;
    }

    /**
     * Elimina y retorna el elemento ubicado al inicio de la lista en tiempo O(1).
     *
     * @return El dato que estaba en el primer nodo.
     * @throws NoSuchElementException si la lista está vacía.
     */
    public T eliminarAlInicio() {
        if (estaVacia()) {
            throw new NoSuchElementException("La lista doble está vacía.");
        }
        T dato = head.dato;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = head.siguiente;
            head.anterior = null;
        }
        tamano--;
        return dato;
    }

    /**
     * Elimina y retorna el elemento ubicado al final de la lista en tiempo O(1).
     *
     * @return El dato que estaba en el último nodo.
     * @throws NoSuchElementException si la lista está vacía.
     */
    public T eliminarAlFinal() {
        if (estaVacia()) {
            throw new NoSuchElementException("La lista doble está vacía.");
        }
        T dato = tail.dato;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            tail = tail.anterior;
            tail.siguiente = null;
        }
        tamano--;
        return dato;
    }

    /**
     * Determina si la lista contiene al menos una ocurrencia del dato dado.
     *
     * @param dato Elemento a buscar.
     * @return true si está presente, false en caso contrario.
     */
    public boolean contiene(T dato) {
        NodoDoble<T> actual = head;
        while (actual != null) {
            if (Objects.equals(actual.dato, dato)) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    /**
     * Comprueba si la lista no contiene nodos.
     *
     * @return true si está vacía, false si tiene elementos.
     */
    public boolean estaVacia() {
        return head == null;
    }

    /**
     * Retorna la cantidad de elementos en la lista.
     *
     * @return Entero con el tamaño actual.
     */
    public int getTamano() {
        return tamano;
    }

    /**
     * Imprime la lista desde head hacia tail utilizando las referencias 'siguiente'.
     */
    public void imprimirHaciaAdelante() {
        System.out.print("Hacia adelante (head -> tail): [ ");
        NodoDoble<T> actual = head;
        while (actual != null) {
            System.out.print(actual.dato);
            if (actual.siguiente != null) {
                System.out.print(" <-> ");
            }
            actual = actual.siguiente;
        }
        System.out.println(" ] (Total: " + tamano + ")");
    }

    /**
     * Imprime la lista desde tail hacia head utilizando las referencias 'anterior'.
     * Demuestra la navegación bidireccional genuina de la estructura.
     */
    public void imprimirHaciaAtras() {
        System.out.print("Hacia atrás     (tail -> head): [ ");
        NodoDoble<T> actual = tail;
        while (actual != null) {
            System.out.print(actual.dato);
            if (actual.anterior != null) {
                System.out.print(" <-> ");
            }
            actual = actual.anterior;
        }
        System.out.println(" ] (Total: " + tamano + ")");
    }

    /**
     * Demostración exhaustiva de las operaciones de la lista doblemente enlazada genérica.
     */
    public static void main(String[] args) {
        System.out.println("====================================================================");
        System.out.println("   DEMOSTRACIÓN: LISTA DOBLEMENTE ENLAZADA GENÉRICA (Ej10<T>)       ");
        System.out.println("====================================================================\n");

        // --- PRUEBA 1: Lista doble de Cadenas (String) ---
        System.out.println("--- 1. Pruebas con tipo String ---");
        Ej10<String> listaNombres = new Ej10<>();

        System.out.println("Estado inicial:");
        System.out.println("¿Vacía? " + listaNombres.estaVacia() + " | Tamaño: " + listaNombres.getTamano());

        // Inserciones combinadas
        System.out.println("\nInsertando elementos:");
        listaNombres.insertarAlFinal("Beta");
        listaNombres.insertarAlFinal("Gamma");
        listaNombres.insertarAlInicio("Alpha");
        listaNombres.insertarAlFinal("Delta");
        listaNombres.insertarAlFinal("Epsilon");

        listaNombres.imprimirHaciaAdelante();
        listaNombres.imprimirHaciaAtras();

        // Búsqueda
        System.out.println("\nBúsquedas:");
        System.out.println("¿Contiene 'Gamma'? " + listaNombres.contiene("Gamma"));
        System.out.println("¿Contiene 'Omega'? " + listaNombres.contiene("Omega"));

        // Eliminación de un nodo intermedio ("Gamma")
        System.out.println("\nEliminando nodo intermedio ('Gamma'):");
        boolean borradoMedio = listaNombres.eliminar("Gamma");
        System.out.println("Resultado de borrado: " + borradoMedio);
        listaNombres.imprimirHaciaAdelante();
        listaNombres.imprimirHaciaAtras();

        // Eliminación de la cabeza (head) mediante eliminar("Alpha")
        System.out.println("\nEliminando nodo inicial ('Alpha') mediante eliminar():");
        listaNombres.eliminar("Alpha");
        listaNombres.imprimirHaciaAdelante();
        listaNombres.imprimirHaciaAtras();

        // Eliminación de la cola (tail) mediante eliminar("Epsilon")
        System.out.println("\nEliminando nodo final ('Epsilon') mediante eliminar():");
        listaNombres.eliminar("Epsilon");
        listaNombres.imprimirHaciaAdelante();
        listaNombres.imprimirHaciaAtras();

        // Eliminación de elemento inexistente
        System.out.println("\nIntentando eliminar elemento no existente ('Zeta'):");
        System.out.println("Resultado: " + listaNombres.eliminar("Zeta"));

        // Extracciones directas con eliminarAlInicio y eliminarAlFinal
        System.out.println("\nExtracción en extremos:");
        System.out.println("Eliminado al inicio: " + listaNombres.eliminarAlInicio());
        listaNombres.imprimirHaciaAdelante();

        System.out.println("Eliminado al final: " + listaNombres.eliminarAlFinal());
        listaNombres.imprimirHaciaAdelante();
        System.out.println("¿Quedó vacía la lista? " + listaNombres.estaVacia());

        // --- PRUEBA 2: Lista doble de Enteros (Integer) ---
        System.out.println("\n--- 2. Pruebas con tipo Integer ---");
        Ej10<Integer> listaNumeros = new Ej10<>();

        listaNumeros.insertarAlFinal(10);
        listaNumeros.insertarAlFinal(20);
        listaNumeros.insertarAlFinal(30);
        listaNumeros.insertarAlInicio(5);
        listaNumeros.insertarAlInicio(1);

        System.out.println("Lista de números construida:");
        listaNumeros.imprimirHaciaAdelante();
        listaNumeros.imprimirHaciaAtras();

        // Eliminar único elemento restante hasta vaciar
        System.out.println("\nEliminaciones sucesivas en lista de números:");
        while (!listaNumeros.estaVacia()) {
            Integer valor = listaNumeros.eliminarAlInicio();
            System.out.println("Extraído elemento: " + valor + " | Elementos restantes: " + listaNumeros.getTamano());
        }

        // Caso límite: excepción en lista vacía
        System.out.println("\nCaso límite: Intento de eliminar en lista vacía:");
        try {
            listaNumeros.eliminarAlInicio();
        } catch (NoSuchElementException e) {
            System.out.println("Excepción capturada con éxito: " + e.getMessage());
        }

        try {
            listaNumeros.eliminarAlFinal();
        } catch (NoSuchElementException e) {
            System.out.println("Excepción capturada con éxito: " + e.getMessage());
        }

        System.out.println("\n====================================================================");
        System.out.println("   FIN DE LA DEMOSTRACIÓN DEL EJERCICIO 10                          ");
        System.out.println("====================================================================");
    }
}
