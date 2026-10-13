/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Quiero implementar en Java una clase genérica llamada Pila<T> (en el archivo Ej5.java) 
 * que modele una Pila sobre un arreglo de tamaño fijo capaz de almacenar cualquier tipo de 
 * datos (como Integer, String u objetos propios).
 * 
 * El programa debe justificar por qué los Genéricos en Java resuelven la limitación de una 
 * pila hecha solamente para 'int':
 * - Evitan la duplicación de código al permitir que una única estructura maneje cualquier tipo de objeto.
 * - Proveen seguridad de tipos en tiempo de compilación (Type Safety), impidiendo insertar 
 *   tipos incompatibles y eliminando la necesidad de castings manuales propensos a ClassCastException 
 *   en tiempo de ejecución.
 * 
 * La estructura interna debe gestionarse mediante un arreglo genérico 'T[] elementos' y un 
 * puntero 'int top' inicializado en -1:
 * - Explicar la técnica idiomática de instanciación en Java: debido al Type Erasure, no se puede 
 *   hacer new T[capacidad]; se debe instanciar (T[]) new Object[capacidad] anotando con 
 *   @SuppressWarnings("unchecked").
 * - push(T elemento): almacena el elemento en elementos[++top]. Lanza IllegalStateException si 
 *   isFull() == true.
 * - T pop(): retorna elementos[top], limpia la posición con elementos[top] = null para permitir 
 *   recolección de basura, y decrementa top--. Lanza IllegalStateException si isEmpty() == true.
 * - T peek(): retorna elementos[top] sin removerlo. Lanza IllegalStateException si está vacía.
 * - boolean isEmpty(): retorna top == -1.
 * - boolean isFull(): retorna top == capacidad - 1.
 * - int size(): retorna top + 1.
 * 
 * Quiero que el código tenga comentarios explicando:
 * - La instanciación del arreglo de Object con casteo seguro a T[].
 * - La anulación de referencias (elementos[top] = null) en pop() para prevenir fugas de memoria.
 * - Cómo el compilador garantiza Type Safety en los métodos push y pop.
 * 
 * La verificación del resultado debe probarse en el método main de Ej5 con diferentes tipos parametrizados:
 * - Caso 1: Pila<String> para almacenar textos ("Hola", "Estructura", "Datos").
 * - Caso 2: Pila<Integer> para almacenar números (100, 200, 300).
 * - Caso 3: Pila<Persona> para almacenar objetos de un record personalizado Persona(String nombre, int edad).
 * - Caso 4: Verificación de control de desbordamiento (isFull) y subdesbordamiento (isEmpty).
 * =========================================================================================
 */

/**
 * Ejercicio 5: Implementación de una Pila Genérica Pila<T> sobre arreglos.
 * Permite almacenar cualquier tipo de datos garantizando Type Safety en tiempo de compilación.
 */
public class Ej5 {

    /**
     * Clase Pila genérica parametrizada con el tipo T.
     *
     * @param <T> Tipo de los elementos que contendrá la pila.
     */
    public static class Pila<T> {

        // Arreglo genérico subyacente
        private final T[] elementos;

        // Puntero de cima (índice del último elemento ingresado)
        private int top;

        // Capacidad máxima fija
        private final int capacidad;

        /**
         * Constructor de la pila genérica.
         *
         * @param capacidad Tamaño máximo del arreglo.
         */
        @SuppressWarnings("unchecked")
        public Pila(int capacidad) {
            if (capacidad <= 0) {
                throw new IllegalArgumentException("La capacidad debe ser mayor a 0.");
            }
            this.capacidad = capacidad;

            // En Java no es legal instanciar directamente un arreglo de tipo genérico (new T[capacidad])
            // debido al "Type Erasure" (borrado de tipos en tiempo de compilación).
            // La solución idiomática es instanciar un arreglo de Object[] y castearlo a T[].
            this.elementos = (T[]) new Object[capacidad];
            this.top = -1; // -1 indica pila vacía
        }

        /**
         * Apila un elemento de tipo T en la cima.
         *
         * @param elemento Objeto a apilar.
         * @throws IllegalStateException Si la pila está llena (Stack Overflow).
         */
        public void push(T elemento) {
            if (isFull()) {
                throw new IllegalStateException("Desbordamiento (Stack Overflow): La pila está llena con " 
                        + capacidad + " elementos.");
            }
            elementos[++top] = elemento;
        }

        /**
         * Desapila y retorna el elemento de tipo T situado en la cima.
         *
         * @return El elemento desapilado.
         * @throws IllegalStateException Si la pila está vacía (Stack Underflow).
         */
        public T pop() {
            if (isEmpty()) {
                throw new IllegalStateException("Subdesbordamiento (Stack Underflow): La pila está vacía.");
            }
            T item = elementos[top];

            // Limpieza de referencia: evita que el arreglo retenga el objeto en el Heap
            // permitiendo que el Garbage Collector lo reclame si nadie más lo referencia
            elementos[top--] = null;

            return item;
        }

        /**
         * Consulta el elemento en la cima sin retirarlo.
         *
         * @return El elemento en la cima.
         * @throws IllegalStateException Si la pila está vacía.
         */
        public T peek() {
            if (isEmpty()) {
                throw new IllegalStateException("La pila está vacía; no hay elemento en la cima.");
            }
            return elementos[top];
        }

        /**
         * Determina si la pila está vacía.
         */
        public boolean isEmpty() {
            return top == -1;
        }

        /**
         * Determina si la pila alcanzó su capacidad máxima.
         */
        public boolean isFull() {
            return top == capacidad - 1;
        }

        /**
         * Retorna la cantidad de elementos almacenados.
         */
        public int size() {
            return top + 1;
        }
    }

    /**
     * Registro simple para demostrar la pila con objetos de dominio propios.
     */
    public record Persona(String nombre, int edad) {
        @Override
        public String toString() {
            return nombre + " (" + edad + " años)";
        }
    }

    /**
     * Batería de pruebas que verifica la pila con múltiples tipos de datos.
     */
    public static void main(String[] args) {
        System.out.println("========================================================================");
        System.out.println("PRUEBA DE EJECUCIÓN: PILA GENÉRICA Pila<T> (CLASE Ej5)");
        System.out.println("========================================================================");

        // 1. Pila de Strings
        System.out.println("--- 1. Pila parametrizada con String (Pila<String>) ---");
        Pila<String> pilaTextos = new Pila<>(3);
        pilaTextos.push("Hola");
        pilaTextos.push("Estructura");
        pilaTextos.push("Datos");
        System.out.println("Cima actual: " + pilaTextos.peek());
        while (!pilaTextos.isEmpty()) {
            System.out.println("pop(): " + pilaTextos.pop());
        }

        // 2. Pila de Integers
        System.out.println("\n--- 2. Pila parametrizada con Integer (Pila<Integer>) ---");
        Pila<Integer> pilaNumeros = new Pila<>(3);
        pilaNumeros.push(100);
        pilaNumeros.push(200);
        pilaNumeros.push(300);
        System.out.println("Cima actual: " + pilaNumeros.peek());
        while (!pilaNumeros.isEmpty()) {
            System.out.println("pop(): " + pilaNumeros.pop());
        }

        // 3. Pila de objetos propios (Persona)
        System.out.println("\n--- 3. Pila parametrizada con objetos Persona (Pila<Persona>) ---");
        Pila<Persona> pilaPersonas = new Pila<>(2);
        pilaPersonas.push(new Persona("Lucia", 21));
        pilaPersonas.push(new Persona("Carlos", 25));
        System.out.println("Cima actual: " + pilaPersonas.peek());
        while (!pilaPersonas.isEmpty()) {
            System.out.println("pop(): " + pilaPersonas.pop());
        }

        // 4. Verificación de casos límite (Overflow / Underflow)
        System.out.println("\n--- 4. Validación de Casos Límite ---");
        Pila<Double> pilaLimite = new Pila<>(1);
        pilaLimite.push(3.1415);
        System.out.println("¿Está llena la pila? " + pilaLimite.isFull());

        try {
            System.out.print("Intentando push en pila llena... ");
            pilaLimite.push(2.7182);
        } catch (IllegalStateException e) {
            System.out.println("Excepción capturada: " + e.getMessage());
        }

        pilaLimite.pop(); // Queda vacía
        try {
            System.out.print("Intentando pop en pila vacía... ");
            pilaLimite.pop();
        } catch (IllegalStateException e) {
            System.out.println("Excepción capturada: " + e.getMessage());
        }

        System.out.println("\n========================================================================");
        System.out.println("VENTAJAS DE LOS GENÉRICOS DEMOSTRADAS:");
        System.out.println("- No tuvimos que programar PilaString, PilaInteger ni PilaPersona por separado.");
        System.out.println("- Type Safety: El compilador impide insertar un número en Pila<String>.");
        System.out.println("- Sin casting manual: pop() devuelve directamente el tipo exacto.");
        System.out.println("========================================================================");
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación / Ajuste del prompt:
 *    Se implementó la clase genérica 'Pila<T>' como una clase estática interna dentro de 'Ej5'
 *    para respetar la regla de nomenclatura de archivo 'Ej5.java' sin violar las normas de
 *    visibilidad de tipos en Java.
 * 
 * 2. Justificación técnica:
 *    En Java, un archivo solo puede contener una clase pública de nivel superior con el mismo
 *    nombre del archivo. Anidar 'Pila<T>' estáticamente dentro de 'Ej5' permite mantener el
 *    nombre de clase requerido 'Ej5' como punto de entrada ejecutable, ofreciendo al mismo
 *    tiempo la clase genérica limpia y testeable con 'Pila<String>', 'Pila<Integer>' y 'Pila<Persona>'.
 * =========================================================================================
 */