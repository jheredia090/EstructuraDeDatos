/*
Prompt inicial utilizado:
Quiero implementar en Java el método 'void eliminarEnPosicion(int posicion)' dentro de una lista enlazada simple de enteros (en el archivo Ej6.java).

El método debe eliminar el nodo que se encuentra en un índice específico (base 0) y actualizar correctamente el atributo size.

El prompt debe explicar detalladamente:
- Cómo encontrar el nodo anterior: se utiliza un puntero auxiliar que parte de 'head' y avanza con un bucle exactamente 'posicion - 1' veces, quedando situado en el nodo previo al que se desea eliminar.
- Cómo reconectar los enlaces: se hace que 'anterior.siguiente' apunte directamente al nodo siguiente del eliminado (anterior.siguiente = anterior.siguiente.siguiente), saltando y desconectando el nodo objetivo para que el Garbage Collector lo elimine.
- Caso especial de la cabeza: si posicion == 0, no existe un nodo anterior; la eliminación se resuelve directamente haciendo head = head.siguiente.

Validaciones obligatorias:
- Si posicion < 0 o posicion >= size, debe lanzar IndexOutOfBoundsException antes de realizar modificaciones.
- Tras la eliminación exitosa, debe decrementar size en 1.

Casos a probar en main:
- Cargar la lista: 10 -> 20 -> 30 -> 40 -> 50 -> null (size = 5).
- Eliminar la posición 0 (primer elemento: 10, quedando 20 al inicio).
- Eliminar la posición 1 (elemento intermedio: 30, quedando 20 -> 40 -> 50).
- Eliminar la última posición (posicion == size - 1: elemento 50, quedando 20 -> 40).
- Probar posiciones inválidas (-1, 5 y 10) capturando IndexOutOfBoundsException.

Ajustes realizados luego de la primera respuesta de OpenCode:
Ninguno. La bifurcación para el caso 'posicion == 0' y la búsqueda del nodo 'posicion - 1' resolvieron limpiamente tanto las eliminaciones intermedias como las del último elemento sin errores de punteros nulos.
*/

/**
 * Ejercicio 6: Eliminar un nodo por posición.
 * Localiza el nodo anterior al índice solicitado y reconecta la cadena saltando el nodo a descartar.
 */
public class Ej6 {

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

    public Ej6() {
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
     * Elimina el nodo ubicado en una posición específica de la lista (índice base 0).
     *
     * @param posicion Índice del nodo a remover (0 <= posicion < size).
     * @throws IndexOutOfBoundsException Si la posición está fuera de rango o la lista está vacía.
     */
    public void eliminarEnPosicion(int posicion) {
        // Validación previa en O(1)
        if (posicion < 0 || posicion >= size) {
            throw new IndexOutOfBoundsException("Posición inválida: " + posicion + ". Rango válido: [0, " + (size - 1) + "].");
        }

        // Caso 1: Eliminar el primer nodo (índice 0)
        if (posicion == 0) {
            head = head.siguiente; // Avanza la cabeza al segundo nodo
        } else {
            // Caso 2: Eliminar un nodo intermedio o final.
            // Debemos posicionarnos en el nodo ANTERIOR al que queremos eliminar (posicion - 1).
            Nodo anterior = head;
            for (int i = 0; i < posicion - 1; i++) {
                anterior = anterior.siguiente;
            }

            // RECONEXIÓN:
            // 'anterior.siguiente' pasa a ser el nodo que estaba después del eliminado.
            // El nodo en la posición 'posicion' queda desconectado.
            anterior.siguiente = anterior.siguiente.siguiente;
        }

        size--;
    }

    public int getSize() {
        return size;
    }

    public void imprimir() {
        if (head == null) {
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

    public static void main(String[] args) {
        System.out.println("========================================================================");
        System.out.println("PRUEBA: EJERCICIO 6 - ELIMINAR NODO POR POSICIÓN");
        System.out.println("========================================================================");

        Ej6 lista = new Ej6();
        lista.insertarAlFinal(10);
        lista.insertarAlFinal(20);
        lista.insertarAlFinal(30);
        lista.insertarAlFinal(40);
        lista.insertarAlFinal(50);

        System.out.print("Lista inicial (size = " + lista.getSize() + "): ");
        lista.imprimir();

        // 1. Eliminar la posición 0 (primer elemento: 10)
        System.out.println("\n--- 1. eliminarEnPosicion(0) [Primer elemento] ---");
        lista.eliminarEnPosicion(0);
        lista.imprimir();
        System.out.println("Tamaño: " + lista.getSize());

        // 2. Eliminar la posición 1 (elemento intermedio: 30 en la lista actual 20 -> 30 -> 40 -> 50)
        System.out.println("\n--- 2. eliminarEnPosicion(1) [Elemento intermedio: 30] ---");
        lista.eliminarEnPosicion(1);
        lista.imprimir();
        System.out.println("Tamaño: " + lista.getSize());

        // 3. Eliminar la última posición (índice size - 1: elemento 50)
        System.out.println("\n--- 3. eliminarEnPosicion(" + (lista.getSize() - 1) + ") [Último elemento: 50] ---");
        lista.eliminarEnPosicion(lista.getSize() - 1);
        lista.imprimir();
        System.out.println("Tamaño: " + lista.getSize());

        // 4. Intentar posiciones inválidas
        System.out.println("\n--- 4. Intentos con posiciones inválidas ---");
        probarPosicionInvalida(lista, -1);
        probarPosicionInvalida(lista, 5);
        probarPosicionInvalida(lista, 10);
        System.out.println("========================================================================");
    }

    private static void probarPosicionInvalida(Ej6 lista, int pos) {
        try {
            System.out.print("Intentando eliminarEnPosicion(" + pos + ")... ");
            lista.eliminarEnPosicion(pos);
            System.out.println("ERROR: Debería haber lanzado excepción.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Excepción capturada con éxito: " + e.getMessage());
        }
    }
}
