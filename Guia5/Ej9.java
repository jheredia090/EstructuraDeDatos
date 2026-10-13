/*
Prompt inicial utilizado:
Quiero implementar en Java el método 'void invertir()' dentro de una lista enlazada simple de enteros (en el archivo Ej9.java), invirtiendo el orden de los enlaces in-place sin instanciar nuevos nodos.

Ejemplo esperado:
- Entrada: 10 -> 20 -> 30 -> 40 -> null
- Salida:  40 -> 30 -> 20 -> 10 -> null

El prompt debe explicar detalladamente:
- Qué referencias auxiliares hacen falta: se requieren tres punteros:
  1. 'anterior' (inicia en null, representará el extremo final invertido).
  2. 'actual' (inicia en head, nodo a procesar).
  3. 'siguiente' (puntero temporal para no perder la cadena restante).
- Por qué el orden de actualización es fundamental:
  Antes de reorientar la flecha de 'actual' hacia 'anterior', se debe resguardar obligatoriamente 'siguiente = actual.siguiente'. Si se alterara este orden, al sobreescribir 'actual.siguiente' se destruiría la única vía de acceso al resto de la lista, perdiendo los nodos subsiguientes de forma irrecuperable en memoria.
- Actualización final de head: al finalizar el bucle (cuando actual llega a null), la nueva cabeza de la lista debe ser 'head = anterior'.

Casos a contemplar:
- Lista vacía (head == null) o con 1 solo nodo: la operación no tiene efecto (la lista ya está invertida).
- Lista con múltiples nodos (par e impar).

Casos a probar en main:
- Caso del enunciado: 10 -> 20 -> 30 -> 40 -> null. Invertir y verificar: 40 -> 30 -> 20 -> 10 -> null.
- Caso de 1 solo elemento: 99 -> null. Invertir y verificar que permanece idéntico.
- Caso de lista vacía: invertir sin provocar excepciones.
- Doble inversión: invertir la lista dos veces consecutivas y comprobar que recupera su orden original.

Ajustes realizados luego de la primera respuesta de OpenCode:
Ninguno. El algoritmo de los tres punteros operó en tiempo lineal O(n) y espacio auxiliar constante O(1), resolviendo limpiamente la reorientación de punteros in-place sin duplicar estructuras en memoria.
*/

/**
 * Ejercicio 9: Invertir una lista enlazada simple.
 * Reorienta el sentido de los enlaces mediante tres punteros auxiliares (anterior, actual y siguiente).
 */
public class Ej9 {

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

    public Ej9() {
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
     * Invierte el orden de la lista enlazada in-place (tiempo O(n), espacio auxiliar O(1)).
     */
    public void invertir() {
        // Si la lista está vacía o tiene un solo elemento, ya se encuentra invertida
        if (head == null || head.siguiente == null) {
            return;
        }

        // Puntero 'anterior': inicia en null porque la cabeza original pasará a ser la nueva cola
        Nodo anterior = null;

        // Puntero 'actual': inicia en head y recorre cada nodo reorientando su enlace
        Nodo actual = head;

        // Puntero 'siguiente': variable temporal que guardará el resto de la lista
        Nodo siguiente = null;

        while (actual != null) {
            // =========================================================================
            // ORDEN FUNDAMENTAL DE ACTUALIZACIÓN:
            // =========================================================================
            // 1. Guardar la referencia al siguiente nodo ANTES de romper el enlace
            siguiente = actual.siguiente;

            // 2. Invertir el puntero: el nodo actual ahora apunta hacia atrás (al nodo anterior)
            actual.siguiente = anterior;

            // 3. Avanzar 'anterior' al nodo que acabamos de invertir
            anterior = actual;

            // 4. Avanzar 'actual' al siguiente nodo pendiente en la secuencia original
            actual = siguiente;
        }

        // Al finalizar el bucle, 'actual' es null y 'anterior' quedó posicionado
        // en el último nodo de la lista original, el cual pasa a ser la nueva cabeza
        head = anterior;
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
        System.out.println("PRUEBA: EJERCICIO 9 - INVERTIR LISTA ENLAZADA SIMPLE");
        System.out.println("========================================================================");

        // 1. Caso del enunciado: 10 -> 20 -> 30 -> 40 -> null
        Ej9 lista = new Ej9();
        lista.insertarAlFinal(10);
        lista.insertarAlFinal(20);
        lista.insertarAlFinal(30);
        lista.insertarAlFinal(40);

        System.out.print("Lista original:   ");
        lista.imprimir();

        lista.invertir();
        System.out.print("Lista invertida:  ");
        lista.imprimir();

        // 2. Doble inversión (debe volver al estado original)
        System.out.println("\n--- Probando doble inversión ---");
        lista.invertir();
        System.out.print("Vuelta a invertir: ");
        lista.imprimir();

        // 3. Caso con 1 solo nodo
        System.out.println("\n--- Caso con un solo elemento ---");
        Ej9 listaUnitaria = new Ej9();
        listaUnitaria.insertarAlFinal(99);
        System.out.print("Antes: ");
        listaUnitaria.imprimir();
        listaUnitaria.invertir();
        System.out.print("Después: ");
        listaUnitaria.imprimir();

        // 4. Caso con lista vacía
        System.out.println("\n--- Caso con lista vacía ---");
        Ej9 listaVacia = new Ej9();
        System.out.print("Antes: ");
        listaVacia.imprimir();
        listaVacia.invertir();
        System.out.print("Después: ");
        listaVacia.imprimir();
        System.out.println("========================================================================");
    }
}
