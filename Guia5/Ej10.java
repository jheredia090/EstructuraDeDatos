/*
Prompt inicial utilizado:
Quiero implementar en Java una Lista Enlazada Simple Genérica llamada ListaEnlazada<T> (en el archivo Ej10.java) que permita almacenar distintos tipos de objetos como Integer, String o una clase propia como Alumno.

Estructura de clases requerida:
1. Clase Nodo<T>: con un atributo 'T dato' y 'Nodo<T> siguiente'.
2. Clase Ej10<T> (ListaEnlazada<T>): con 'Nodo<T> head' y 'int size'.

El prompt debe explicar detalladamente:
- Qué partes del código cambian:
  * Las clases se parametrizan con <T>.
  * El atributo 'int dato' se convierte en 'T dato'.
  * Las firmas reciben y retornan el tipo 'T'.
  * Las comparaciones de datos para buscar o eliminar reemplazan '==' por 'Objects.equals(actual.dato, dato)' para soportar igualdad semántica entre objetos.
- Cuáles se mantienen iguales:
  * Toda la lógica de punteros (head, siguiente, enlaces, recorrido con while y reconexión de nodos).
  * La variable 'size' y el manejo de excepciones de índice.
- Por qué los algoritmos de listas enlazadas son independientes del tipo:
  Porque la lista es una estructura de almacenamiento topológico. Su lógica consiste únicamente en enlazar referencias en el Heap; la naturaleza del dato que viaja dentro del nodo no altera en nada cómo se conectan o desconectan las referencias entre sí.

Métodos genéricos a implementar:
- void insertarAlFinal(T dato)
- void insertarAlInicio(T dato)
- boolean buscar(T dato)
- boolean eliminar(T dato)
- T obtener(int posicion)
- void imprimir()
- int getSize()

Casos a probar en main:
- Caso 1: ListaEnlazada<Integer> con números (10, 20, 30).
- Caso 2: ListaEnlazada<String> con textos ("Java", "Estructura", "Datos").
- Caso 3: ListaEnlazada<Alumno> utilizando un record Alumno(String nombre, int legajo), probando inserción, búsqueda y eliminación de objetos de dominio.

Ajustes realizados luego de la primera respuesta de OpenCode:
Se utilizó 'java.util.Objects.equals()' para comparar datos en buscar() y eliminar(), lo que previene 'NullPointerException' y asegura que dos objetos con el mismo contenido (como dos Alumnos con igual nombre y legajo) se reconozcan como equivalentes según su método equals().
*/

import java.util.Objects;

/**
 * Ejercicio 10: Convertir la lista de enteros en una lista genérica parametrizada <T>.
 * Demuestra la separación entre la estructura topológica de punteros y el tipo de dato contenido.
 *
 * @param <T> Tipo de los elementos que contendrá la lista.
 */
public class Ej10<T> {

    /**
     * Nodo genérico que almacena un dato de tipo T y una referencia a otro Nodo<T>.
     */
    public static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;

        public Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    private Nodo<T> head;
    private int size;

    public Ej10() {
        this.head = null;
        this.size = 0;
    }

    public void insertarAlInicio(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.siguiente = head;
        head = nuevo;
        size++;
    }

    public void insertarAlFinal(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (head == null) {
            head = nuevo;
        } else {
            Nodo<T> actual = head;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
        size++;
    }

    /**
     * Busca si un dato está en la lista usando Objects.equals() para comparar objetos.
     */
    public boolean buscar(T dato) {
        Nodo<T> actual = head;
        while (actual != null) {
            // En genéricos no se usa '=='; se usa Objects.equals() para comparar contenido
            if (Objects.equals(actual.dato, dato)) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    /**
     * Elimina la primera aparición de un dato.
     */
    public boolean eliminar(T dato) {
        if (head == null) {
            return false;
        }

        if (Objects.equals(head.dato, dato)) {
            head = head.siguiente;
            size--;
            return true;
        }

        Nodo<T> anterior = head;
        Nodo<T> actual = head.siguiente;

        while (actual != null) {
            if (Objects.equals(actual.dato, dato)) {
                anterior.siguiente = actual.siguiente;
                size--;
                return true;
            }
            anterior = actual;
            actual = actual.siguiente;
        }
        return false;
    }

    /**
     * Obtiene el dato en una posición determinada.
     */
    public T obtener(int posicion) {
        if (posicion < 0 || posicion >= size) {
            throw new IndexOutOfBoundsException("Posición inválida: " + posicion + ". Rango: [0, " + (size - 1) + "].");
        }
        Nodo<T> actual = head;
        for (int i = 0; i < posicion; i++) {
            actual = actual.siguiente;
        }
        return actual.dato;
    }

    public int getSize() {
        return size;
    }

    public void imprimir() {
        if (head == null) {
            System.out.println("Lista vacía: null");
            return;
        }
        Nodo<T> actual = head;
        while (actual != null) {
            System.out.print(actual.dato + " -> ");
            actual = actual.siguiente;
        }
        System.out.println("null");
    }

    /**
     * Objeto de dominio para probar la lista genérica con clases de negocio.
     */
    public record Alumno(String nombre, int legajo) {
        @Override
        public String toString() {
            return nombre + " (Leg: " + legajo + ")";
        }
    }

    public static void main(String[] args) {
        System.out.println("========================================================================");
        System.out.println("PRUEBA: EJERCICIO 10 - LISTA ENLAZADA SIMPLE GENÉRICA <T>");
        System.out.println("========================================================================");

        // 1. Prueba con Integer
        System.out.println("--- 1. ListaEnlazada<Integer> ---");
        Ej10<Integer> listaEnteros = new Ej10<>();
        listaEnteros.insertarAlFinal(10);
        listaEnteros.insertarAlFinal(20);
        listaEnteros.insertarAlFinal(30);
        listaEnteros.imprimir();
        System.out.println("buscar(20): " + listaEnteros.buscar(20));
        System.out.println("obtener(1): " + listaEnteros.obtener(1));

        // 2. Prueba con String
        System.out.println("\n--- 2. ListaEnlazada<String> ---");
        Ej10<String> listaTextos = new Ej10<>();
        listaTextos.insertarAlFinal("Java");
        listaTextos.insertarAlFinal("Estructura");
        listaTextos.insertarAlFinal("Datos");
        listaTextos.imprimir();
        System.out.println("buscar(\"Java\"): " + listaTextos.buscar("Java"));
        System.out.println("eliminar(\"Estructura\"): " + listaTextos.eliminar("Estructura"));
        listaTextos.imprimir();

        // 3. Prueba con objetos propios (Alumno)
        System.out.println("\n--- 3. ListaEnlazada<Alumno> ---");
        Ej10<Alumno> listaAlumnos = new Ej10<>();
        Alumno a1 = new Alumno("Lucia Gomez", 101);
        Alumno a2 = new Alumno("Pedro Perez", 102);
        Alumno a3 = new Alumno("Ana Rodriguez", 103);

        listaAlumnos.insertarAlFinal(a1);
        listaAlumnos.insertarAlFinal(a2);
        listaAlumnos.insertarAlFinal(a3);
        listaAlumnos.imprimir();

        System.out.println("buscar(Pedro Perez 102): " + listaAlumnos.buscar(new Alumno("Pedro Perez", 102)));
        System.out.println("eliminar(Pedro Perez 102): " + listaAlumnos.eliminar(new Alumno("Pedro Perez", 102)));
        listaAlumnos.imprimir();
        System.out.println("Tamaño final: " + listaAlumnos.getSize());
        System.out.println("========================================================================");
    }
}
