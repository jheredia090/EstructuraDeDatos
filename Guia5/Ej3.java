/*
Prompt inicial utilizado:
Quiero implementar en Java el método 'int obtener(int posicion)' dentro de una lista enlazada simple de enteros (en el archivo Ej3.java).

El método debe devolver el dato almacenado en una posición determinada (base 0).

El prompt debe explicar:
- Por qué, aunque se use una posición numérica, la lista enlazada no tiene acceso directo como un arreglo: en los arreglos la memoria es contigua y el acceso es O(1); en una lista enlazada no existe dirección base para calcular índices, por lo que se debe recorrer nodo por nodo desde 'head' haciendo saltos secuenciales (actual = actual.siguiente), requiriendo tiempo O(n).

Validaciones y casos límite obligatorios:
- Debe validar posiciones inválidas antes de recorrer: si posicion < 0 o posicion >= size, debe lanzar IndexOutOfBoundsException informando el índice solicitado y el tamaño actual.

Comportamiento esperado del método:
1. Validar que la posición esté dentro del rango válido [0, size - 1].
2. Iniciar un puntero 'Nodo actual = head'.
3. Iterar con un ciclo for desde 0 hasta posicion - 1 avanzando actual = actual.siguiente.
4. Retornar el dato de ese nodo (actual.dato).

Casos a probar en main:
- Cargar la lista con: 10 -> 20 -> 30 -> 40 -> null (size = 4).
- Obtener el primer elemento (posición 0) -> 10.
- Obtener un elemento del medio (posición 2) -> 30.
- Obtener el último elemento (posición 3) -> 40.
- Probar posiciones inválidas: posición negativa (-1) y posición fuera de rango (4 y 10), capturando IndexOutOfBoundsException.

Ajustes realizados luego de la primera respuesta de OpenCode:
Ninguno. El método cumplió estrictamente con la validación de rango previo O(1) apoyado en el atributo 'size' y el recorrido secuencial de saltos for hasta el índice solicitado.
*/

/**
 * Ejercicio 3: Obtener un elemento por posición.
 * Contrasta el acceso secuencial O(n) de las listas enlazadas frente al acceso indexado directo O(1) de los arreglos.
 */
public class Ej3 {

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

    public Ej3() {
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
     * Obtiene el dato almacenado en una posición determinada (índice base 0).
     * Complejidad: O(n) al requerir recorrido secuencial.
     *
     * @param posicion Índice del elemento a consultar (debe cumplir 0 <= posicion < size).
     * @return El valor entero almacenado en dicho nodo.
     * @throws IndexOutOfBoundsException Si la posición es menor a 0 o mayor/igual al tamaño.
     */
    public int obtener(int posicion) {
        // Validación previa en O(1) usando el atributo size
        if (posicion < 0 || posicion >= size) {
            throw new IndexOutOfBoundsException("Posición inválida: " + posicion + ". Rango válido: [0, " + (size - 1) + "].");
        }

        // En una lista enlazada no existe el acceso directo;
        // debemos iniciar en head y avanzar secuencialmente exactamente 'posicion' veces
        Nodo actual = head;
        for (int i = 0; i < posicion; i++) {
            actual = actual.siguiente;
        }

        // Llegamos al nodo en la posición deseada
        return actual.dato;
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
        System.out.println("PRUEBA: EJERCICIO 3 - OBTENER ELEMENTO POR POSICIÓN");
        System.out.println("========================================================================");

        Ej3 lista = new Ej3();
        lista.insertarAlFinal(10);
        lista.insertarAlFinal(20);
        lista.insertarAlFinal(30);
        lista.insertarAlFinal(40);

        System.out.print("Lista de prueba (size = " + lista.getSize() + "): ");
        lista.imprimir();

        // 1. Pruebas con posiciones válidas
        System.out.println("\n--- Consultas con posiciones válidas ---");
        System.out.println("obtener(0) -> " + lista.obtener(0) + " (esperado: 10 - primer elemento)");
        System.out.println("obtener(2) -> " + lista.obtener(2) + " (esperado: 30 - intermedio)");
        System.out.println("obtener(3) -> " + lista.obtener(3) + " (esperado: 40 - último elemento)");

        // 2. Pruebas con posiciones inválidas
        System.out.println("\n--- Consultas con posiciones inválidas ---");
        probarPosicionInvalida(lista, -1);
        probarPosicionInvalida(lista, 4);
        probarPosicionInvalida(lista, 10);
        System.out.println("========================================================================");
    }

    private static void probarPosicionInvalida(Ej3 lista, int pos) {
        try {
            System.out.print("Intentando obtener(" + pos + ")... ");
            lista.obtener(pos);
            System.out.println("ERROR: Debería haber lanzado excepción.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Excepción capturada: " + e.getMessage());
        }
    }
}
