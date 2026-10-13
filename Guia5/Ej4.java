/*
Prompt inicial utilizado:
Quiero implementar en Java el método 'void insertarEnPosicion(int dato, int posicion)' dentro de una lista enlazada simple de enteros (en el archivo Ej4.java).

El método debe permitir insertar un elemento en cualquier punto de la lista:
- Al inicio (posicion == 0).
- En el medio (0 < posicion < size).
- Al final (posicion == size).

El prompt debe explicar detalladamente el orden estricto de actualización de referencias y el riesgo de invertirlo:
- Orden correcto:
  1. nuevo.siguiente = actual.siguiente;  (el nuevo nodo se enlaza con el resto de la lista).
  2. actual.siguiente = nuevo;           (el nodo anterior enlaza al nuevo nodo).
- Qué pasaría si se invierte el orden:
  Si se ejecuta primero 'actual.siguiente = nuevo', se destruye la única referencia que apuntaba al nodo siguiente original. Al perderse ese puntero, todos los nodos restantes de la lista quedan desconectados e irrecuperables en memoria, provocando la pérdida total de los datos posteriores.

Validaciones y casos límite obligatorios:
- Si posicion < 0 o posicion > size, debe lanzar IndexOutOfBoundsException antes de realizar cualquier cambio.
- Si la inserción es exitosa, se debe incrementar 'size++'.

Casos a probar en main:
- Iniciar con lista vacía e insertar en posición 0 (primer elemento: 10).
- Insertar al final con posicion == size (elemento 30 en posición 1).
- Insertar en el medio (elemento 20 en posición 1, quedando: 10 -> 20 -> 30).
- Insertar al inicio de nuevo (elemento 5 en posición 0, quedando: 5 -> 10 -> 20 -> 30).
- Casos de error: posición negativa (-1) y posición fuera de rango (ej. 10 con size 4), capturando IndexOutOfBoundsException.

Ajustes realizados luego de la primera respuesta de OpenCode:
Ninguno. El método manejó correctamente tanto el caso borde de inserción en head (posicion == 0) como la iteración hasta el nodo 'posicion - 1' para empalmar las referencias intermedias o finales sin pérdida de memoria.
*/

/**
 * Ejercicio 4: Insertar un nodo en una posición específica.
 * Demuestra el orden crítico de reconexión de punteros para no romper la cadena de referencias.
 */
public class Ej4 {

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

    public Ej4() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Inserta un nuevo nodo en una posición determinada de la lista.
     *
     * @param dato Valor entero a insertar.
     * @param posicion Índice destino (0 <= posicion <= size).
     * @throws IndexOutOfBoundsException Si la posición está fuera de rango.
     */
    public void insertarEnPosicion(int dato, int posicion) {
        // Validación estricta del rango válido: desde 0 hasta size (inclusive para inserción al final)
        if (posicion < 0 || posicion > size) {
            throw new IndexOutOfBoundsException("Posición inválida: " + posicion + ". Rango válido: [0, " + size + "].");
        }

        Nodo nuevo = new Nodo(dato);

        // Caso 1: Inserción al inicio (reemplazo de cabeza)
        if (posicion == 0) {
            nuevo.siguiente = head;
            head = nuevo;
        } else {
            // Caso 2 y 3: Inserción en el medio o al final.
            // Necesitamos posicionarnos en el nodo ANTERIOR al índice buscado (posicion - 1).
            Nodo actual = head;
            for (int i = 0; i < posicion - 1; i++) {
                actual = actual.siguiente;
            }

            // =========================================================================
            // ORDEN CRÍTICO DE ACTUALIZACIÓN DE REFERENCIAS:
            // =========================================================================
            // PASO 1: El nuevo nodo debe apuntar primero al que era el sucesor de 'actual'.
            //         nuevo.siguiente = actual.siguiente;
            // PASO 2: Ahora sí, 'actual' puede apuntar al nuevo nodo sin perder el resto.
            //         actual.siguiente = nuevo;
            //
            // ¡PELIGRO SI SE INVIERTE!:
            // Si hiciéramos primero: actual.siguiente = nuevo;
            // perderíamos para siempre la referencia a actual.siguiente original, desconectando
            // y perdiendo en memoria todo el resto de la lista.
            // =========================================================================
            nuevo.siguiente = actual.siguiente; // Paso 1
            actual.siguiente = nuevo;           // Paso 2
        }

        size++;
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
        System.out.println("PRUEBA: EJERCICIO 4 - INSERTAR EN POSICIÓN ESPECÍFICA");
        System.out.println("========================================================================");

        Ej4 lista = new Ej4();

        // 1. Inserción en posición 0 sobre lista vacía
        System.out.println("--- 1. Insertar 10 en posición 0 (lista vacía) ---");
        lista.insertarEnPosicion(10, 0);
        lista.imprimir();

        // 2. Inserción al final usando posicion == size
        System.out.println("\n--- 2. Insertar 30 en posición 1 (al final, posicion == size) ---");
        lista.insertarEnPosicion(30, 1);
        lista.imprimir();

        // 3. Inserción en el medio
        System.out.println("\n--- 3. Insertar 20 en posición 1 (en el medio de 10 y 30) ---");
        lista.insertarEnPosicion(20, 1);
        lista.imprimir();

        // 4. Inserción al inicio con elementos existentes
        System.out.println("\n--- 4. Insertar 5 en posición 0 (nuevo inicio) ---");
        lista.insertarEnPosicion(5, 0);
        lista.imprimir();
        System.out.println("Tamaño final: " + lista.getSize());

        // 5. Casos de error con posiciones inválidas
        System.out.println("\n--- 5. Validaciones de posición inválida ---");
        probarPosicionInvalida(lista, 99, -1);
        probarPosicionInvalida(lista, 99, 10);
        System.out.println("========================================================================");
    }

    private static void probarPosicionInvalida(Ej4 lista, int dato, int pos) {
        try {
            System.out.print("Intentando insertarEnPosicion(" + dato + ", " + pos + ")... ");
            lista.insertarEnPosicion(dato, pos);
            System.out.println("ERROR: Debería haber lanzado excepción.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Excepción capturada con éxito: " + e.getMessage());
        }
    }
}
