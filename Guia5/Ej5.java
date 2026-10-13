/*
Prompt inicial utilizado:
Quiero implementar en Java el método 'boolean eliminar(int dato)' dentro de una lista enlazada simple de enteros (en el archivo Ej5.java).

El método debe eliminar la primera aparición del valor especificado y retornar true si fue eliminado, o false si el dato no existía en la lista.

El prompt debe explicar:
- Cómo funciona la eliminación y el Garbage Collector en Java: en Java no existe una instrucción explícita para destruir un objeto en memoria (como free() en C). La eliminación consiste en actualizar las referencias para que el nodo quede "puenteado" y aislado de la lista (anterior.siguiente = actual.siguiente). Al no existir ninguna referencia alcanzable que apunte a él, el Garbage Collector de la JVM lo detecta y libera su memoria automáticamente.

Casos obligatorios a contemplar:
1. Lista vacía: retorna false inmediatamente.
2. Eliminar el primer nodo (head.dato == dato): se actualiza head = head.siguiente y se decrementa size.
3. Eliminar un nodo del medio: se localiza con dos punteros (anterior y actual) y se reasigna anterior.siguiente = actual.siguiente.
4. Eliminar el último nodo: el nodo anterior pasa a apuntar a null.
5. Dato inexistente: se recorre toda la lista sin encontrarlo y retorna false sin modificar la estructura.

Casos a probar en main:
- Cargar la lista con: 10 -> 20 -> 30 -> 40 -> 50 -> null.
- Eliminar el primer nodo (10) -> true (verificar que el nuevo inicio es 20).
- Eliminar un nodo del medio (30) -> true (verificar que 20 salta directo a 40).
- Eliminar el último nodo (50) -> true (verificar que 40 apunta a null).
- Intentar eliminar un valor inexistente (99) -> false.
- Intentar eliminar en una lista que ha quedado vacía -> false.

Ajustes realizados luego de la primera respuesta de OpenCode:
Ninguno. El manejo de los dos punteros 'anterior' y 'actual' permitió resolver limpiamente los casos intermedios y terminales, diferenciando de forma inequívoca el caso especial de eliminación en head.
*/

/**
 * Ejercicio 5: Eliminar un nodo por valor.
 * Demuestra la desconexión de referencias para que el recolector de basura (Garbage Collector)
 * de Java libere la memoria del nodo que ha quedado inalcanzable.
 */
public class Ej5 {

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

    public Ej5() {
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
     * Elimina la primera aparición de un dato en la lista enlazada.
     *
     * @param dato Valor a buscar y eliminar.
     * @return true si el nodo fue encontrado y eliminado; false si no existía o la lista estaba vacía.
     */
    public boolean eliminar(int dato) {
        // Caso 1: Lista vacía
        if (head == null) {
            return false;
        }

        // Caso 2: El elemento a eliminar está en el primer nodo (cabeza)
        if (head.dato == dato) {
            // Avanzamos head al siguiente eslabón; el nodo anterior queda aislado sin referencias
            head = head.siguiente;
            size--;
            return true;
        }

        // Caso 3 y 4: El elemento está en el medio o al final de la lista.
        // Mantenemos dos punteros: 'anterior' (nodo previo) y 'actual' (nodo a evaluar).
        Nodo anterior = head;
        Nodo actual = head.siguiente;

        while (actual != null) {
            if (actual.dato == dato) {
                // DESCONEXIÓN / PUENTEADO:
                // 'anterior' saltea a 'actual' y apunta directamente a 'actual.siguiente'.
                // 'actual' queda desconectado de la lista y el Garbage Collector lo recolectará.
                anterior.siguiente = actual.siguiente;
                size--;
                return true;
            }
            // Avanzan ambos punteros en tándem
            anterior = actual;
            actual = actual.siguiente;
        }

        // Caso 5: Se recorrió toda la lista y el dato no existía
        return false;
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
        System.out.println("PRUEBA: EJERCICIO 5 - ELIMINAR NODO POR VALOR");
        System.out.println("========================================================================");

        Ej5 lista = new Ej5();
        lista.insertarAlFinal(10);
        lista.insertarAlFinal(20);
        lista.insertarAlFinal(30);
        lista.insertarAlFinal(40);
        lista.insertarAlFinal(50);

        System.out.print("Lista inicial (size = " + lista.getSize() + "): ");
        lista.imprimir();

        // 1. Eliminar el primer nodo (10)
        System.out.println("\n--- 1. Eliminar primer nodo (10) ---");
        System.out.println("Resultado: " + lista.eliminar(10));
        lista.imprimir();
        System.out.println("Tamaño: " + lista.getSize());

        // 2. Eliminar un nodo del medio (30)
        System.out.println("\n--- 2. Eliminar nodo del medio (30) ---");
        System.out.println("Resultado: " + lista.eliminar(30));
        lista.imprimir();
        System.out.println("Tamaño: " + lista.getSize());

        // 3. Eliminar el último nodo (50)
        System.out.println("\n--- 3. Eliminar último nodo (50) ---");
        System.out.println("Resultado: " + lista.eliminar(50));
        lista.imprimir();
        System.out.println("Tamaño: " + lista.getSize());

        // 4. Intentar eliminar un valor inexistente (99)
        System.out.println("\n--- 4. Intentar eliminar valor inexistente (99) ---");
        System.out.println("Resultado: " + lista.eliminar(99));
        lista.imprimir();

        // 5. Vaciar completamente la lista y probar sobre lista vacía
        System.out.println("\n--- 5. Vaciar lista y probar eliminación sobre lista vacía ---");
        lista.eliminar(20);
        lista.eliminar(40);
        lista.imprimir();
        System.out.println("Intentando eliminar en lista vacía: " + lista.eliminar(100));
        System.out.println("========================================================================");
    }
}
