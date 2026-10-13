/*
Prompt inicial utilizado:
A partir de la implementación de una lista enlazada simple de enteros, quiero implementar en Java el método de búsqueda (en el archivo Ej2.java):
boolean buscar(int dato)

El método debe recorrer la lista desde 'head' hasta encontrar el dato o llegar a null.

El prompt debe explicar:
- Por qué no se puede acceder directamente por índice: en un arreglo la memoria es contigua y se accede en O(1) calculando la dirección; en una lista enlazada los nodos están dispersos en el Heap y no hay acceso directo, siendo indispensable seguir los punteros 'siguiente'.
- Por qué la búsqueda requiere recorrido secuencial: se debe avanzar nodo por nodo desde 'head' mediante un puntero auxiliar (actual = actual.siguiente), resultando en complejidad temporal O(n).

Comportamiento esperado del método:
1. Iniciar un puntero auxiliar 'Nodo actual = head'.
2. Mientras 'actual != null', comparar si actual.dato == dato. Si coincide, retornar true inmediatamente.
3. Si no coincide, avanzar actual = actual.siguiente.
4. Si el ciclo termina (actual llegó a null), retornar false.

Casos a probar en main:
- Buscar en lista vacía -> false.
- Cargar la lista con: 10 -> 25 -> 40 -> 99 -> null.
- Buscar el primer elemento (10) -> true (mejor caso O(1)).
- Buscar un elemento intermedio (40) -> true.
- Buscar el último elemento (99) -> true.
- Buscar un elemento inexistente (50) -> false (peor caso O(n)).

Ajustes realizados luego de la primera respuesta de OpenCode:
Ninguno. La lógica de recorrido secuencial con salida temprana al encontrar la primera coincidencia cubrió adecuadamente tanto los casos de éxito en O(1) y O(n) como los casos de fallo (lista vacía o elemento ausente).
*/

/**
 * Ejercicio 2: Buscar elementos en una lista enlazada simple.
 * Demuestra la necesidad del recorrido secuencial paso a paso debido a la falta de contigüidad física.
 */
public class Ej2 {

    /**
     * Representación de un nodo individual.
     */
    public static class Nodo {
        int dato;
        Nodo siguiente;

        public Nodo(int dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    private Nodo head;
    private int size;

    public Ej2() {
        this.head = null;
        this.size = 0;
    }

    public void insertarAlFinal(int dato) {
        Nodo nuevo = new Nodo(dato);
        if (head == null) {
            head = nuevo;
        } else {
            Nodo actual = head;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
        size++;
    }

    /**
     * Busca si un elemento existe en la lista mediante recorrido secuencial.
     * Complejidad temporal: O(n) en el peor caso, O(1) en el mejor caso.
     *
     * @param dato Entero a buscar.
     * @return true si el dato está en la lista; false si se llega a null sin encontrarlo.
     */
    public boolean buscar(int dato) {
        // En una lista enlazada no podemos hacer 'elementos[i]';
        // debemos iniciar obligatoriamente en head y saltar de nodo en nodo
        Nodo actual = head;

        // Recorrido secuencial hasta el final de la cadena
        while (actual != null) {
            // Comparación en el nodo vigente
            if (actual.dato == dato) {
                return true; // Salida temprana apenas se encuentra el dato
            }
            // Avance al próximo eslabón
            actual = actual.siguiente;
        }

        // Se llegó al final (null) sin encontrar coincidencias
        return false;
    }

    public int getSize() {
        return size;
    }

    public void imprimir() {
        Nodo actual = head;
        while (actual != null) {
            System.out.print(actual.dato + " -> ");
            actual = actual.siguiente;
        }
        System.out.println("null");
    }

    public static void main(String[] args) {
        System.out.println("========================================================================");
        System.out.println("PRUEBA: EJERCICIO 2 - BÚSQUEDA EN LISTA ENLAZADA SIMPLE");
        System.out.println("========================================================================");

        Ej2 lista = new Ej2();

        // 1. Búsqueda en lista vacía
        System.out.println("Búsqueda en lista vacía: buscar(5) -> " + lista.buscar(5));

        // 2. Carga de datos
        lista.insertarAlFinal(10);
        lista.insertarAlFinal(25);
        lista.insertarAlFinal(40);
        lista.insertarAlFinal(99);

        System.out.print("\nLista cargada: ");
        lista.imprimir();

        // 3. Pruebas de búsqueda
        System.out.println("\n--- Pruebas de búsqueda ---");
        System.out.println("Buscar primer elemento (10):     " + lista.buscar(10) + " (esperado: true - mejor caso O(1))");
        System.out.println("Buscar elemento intermedio (40): " + lista.buscar(40) + " (esperado: true)");
        System.out.println("Buscar último elemento (99):     " + lista.buscar(99) + " (esperado: true - peor caso O(n))");
        System.out.println("Buscar elemento ausente (50):    " + lista.buscar(50) + " (esperado: false)");
        System.out.println("========================================================================");
    }
}
