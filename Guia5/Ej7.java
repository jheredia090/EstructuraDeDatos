/*
Prompt inicial utilizado:
Quiero implementar en Java el método 'void modificar(int posicion, int nuevoDato)' dentro de una lista enlazada simple de enteros (en el archivo Ej7.java).

El método debe cambiar el valor almacenado en un nodo situado en un índice específico (base 0).

El prompt debe explicar detalladamente la diferencia conceptual entre:
- Modificar el dato de un nodo (actual.dato = nuevoDato): altera únicamente la información encapsulada en la celda de memoria de ese nodo. La estructura física, las referencias 'siguiente', el tamaño 'size' y el orden de los demás nodos quedan intactos.
- Modificar la referencia al siguiente nodo (actual.siguiente = ...): es una operación estructural que cambia los enlaces de la lista (como en inserción o eliminación), con el riesgo de desconectar o romper la secuencia si se maneja incorrectamente.

Validaciones obligatorias:
- Debe validar la posición antes de iniciar el recorrido: si posicion < 0 o posicion >= size, debe lanzar IndexOutOfBoundsException.

Comportamiento esperado del método:
1. Validar el rango [0, size - 1].
2. Recorrer desde 'head' exactamente 'posicion' pasos con un puntero auxiliar (actual = actual.siguiente).
3. Reasignar actual.dato = nuevoDato.

Casos a probar en main:
- Cargar la lista: 10 -> 20 -> 30 -> 40 -> null (size = 4).
- Modificar la primera posición (posición 0: cambiar 10 por 99, quedando 99 -> 20 -> 30 -> 40).
- Modificar una posición intermedia (posición 2: cambiar 30 por 77, quedando 99 -> 20 -> 77 -> 40).
- Modificar la última posición (posición 3: cambiar 40 por 55, quedando 99 -> 20 -> 77 -> 55).
- Probar posiciones inválidas (-1 y 4) capturando IndexOutOfBoundsException.

Ajustes realizados luego de la primera respuesta de OpenCode:
Ninguno. La lógica de mutación de contenido directo sobre el atributo 'dato' sin tocar la referencia 'siguiente' ilustró claramente la diferencia entre actualización de valores y reestructuración de enlaces.
*/

/**
 * Ejercicio 7: Modificar un elemento de la lista.
 * Demuestra la diferencia entre modificar el dato de un nodo (mutación de contenido)
 * y modificar sus referencias (mutación estructural).
 */
public class Ej7 {

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

    public Ej7() {
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
     * Modifica el valor almacenado en un nodo de una posición determinada.
     * Complejidad: O(n) debido al recorrido secuencial previo.
     *
     * @param posicion Índice del nodo a modificar (0 <= posicion < size).
     * @param nuevoDato Nuevo valor entero a asignar.
     * @throws IndexOutOfBoundsException Si la posición no existe.
     */
    public void modificar(int posicion, int nuevoDato) {
        // Validación previa de límites
        if (posicion < 0 || posicion >= size) {
            throw new IndexOutOfBoundsException("Posición inválida: " + posicion + ". Rango válido: [0, " + (size - 1) + "].");
        }

        // Recorrido secuencial hasta el nodo ubicado en 'posicion'
        Nodo actual = head;
        for (int i = 0; i < posicion; i++) {
            actual = actual.siguiente;
        }

        // MUTACIÓN DE CONTENIDO (NO ESTRUCTURAL):
        // Solo cambiamos el valor del dato dentro del objeto existente.
        // Las referencias 'siguiente' permanecen idénticas, por lo que la lista no sufre
        // ningún riesgo de desconexión ni cambia de tamaño.
        actual.dato = nuevoDato;
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
        System.out.println("PRUEBA: EJERCICIO 7 - MODIFICAR ELEMENTO DE LA LISTA");
        System.out.println("========================================================================");

        Ej7 lista = new Ej7();
        lista.insertarAlFinal(10);
        lista.insertarAlFinal(20);
        lista.insertarAlFinal(30);
        lista.insertarAlFinal(40);

        System.out.print("Lista original: ");
        lista.imprimir();

        // 1. Modificar posición 0 (primer elemento)
        System.out.println("\n--- 1. modificar(0, 99) [Cambiar 10 por 99] ---");
        lista.modificar(0, 99);
        lista.imprimir();

        // 2. Modificar posición 2 (intermedio)
        System.out.println("\n--- 2. modificar(2, 77) [Cambiar 30 por 77] ---");
        lista.modificar(2, 77);
        lista.imprimir();

        // 3. Modificar posición 3 (último elemento)
        System.out.println("\n--- 3. modificar(3, 55) [Cambiar 40 por 55] ---");
        lista.modificar(3, 55);
        lista.imprimir();

        // 4. Intentos con posiciones fuera de rango
        System.out.println("\n--- 4. Validaciones de posición inválida ---");
        probarPosicionInvalida(lista, -1, 100);
        probarPosicionInvalida(lista, 4, 100);
        System.out.println("========================================================================");
    }

    private static void probarPosicionInvalida(Ej7 lista, int pos, int valor) {
        try {
            System.out.print("Intentando modificar(" + pos + ", " + valor + ")... ");
            lista.modificar(pos, valor);
            System.out.println("ERROR: Debería haber lanzado excepción.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Excepción capturada con éxito: " + e.getMessage());
        }
    }
}
