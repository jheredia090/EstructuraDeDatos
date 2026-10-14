/*
Prompt inicial utilizado:
Quiero implementar en Java una clase llamada Ej5 que simule un sistema de historial de navegación web utilizando una Pila (Stack) enlazada dinámica de cadenas (URLs).

El prompt debe explicar detalladamente:
- Por qué una pila representa correctamente el comportamiento de "volver atrás": el historial opera bajo el principio LIFO; la última página web visitada se sitúa en la cima de la pila (página activa visible). Cuando el usuario presiona "volver atrás", esa página más reciente se desapila (pop), quedando como página activa la que fue visitada inmediatamente antes.
- Ventaja de la lista enlazada frente al arreglo: la pila enlazada no tiene una cota estática predefinida, permitiendo que el historial crezca dinámicamente con tantas páginas como permita la memoria del sistema.

Estructura de clases requerida:
1. Clase Nodo: con 'String url' y 'Nodo siguiente'.
2. Clase Ej5: con la referencia 'head' (página actual) y 'size'.

Operaciones a implementar:
- void visitarPagina(String url): apila una nueva dirección en O(1).
- String volverAtras(): desapila la página actual y retorna la URL a la que se regresa. Si el historial tiene 1 o menos páginas (no hay a dónde volver), lanza IllegalStateException. Complejidad: O(1).
- String paginaActual(): consulta la URL en el tope en O(1). Lanza IllegalStateException si el historial está vacío.
- void imprimirHistorial(): muestra la traza completa de páginas desde la actual hasta la inicial.
- boolean puedeVolverAtras(): retorna true si size > 1.
- int getCantidadPaginas(): retorna size.

Casos a probar en main:
- Visitar sucesivamente: "google.com", "wikipedia.org", "github.com", "stackoverflow.com".
- Consultar la página actual tras cada visita.
- Imprimir el historial completo.
- Volver atrás dos veces consecutivas, verificando que regresa en orden inverso (a github.com, luego a wikipedia.org).
- Volver atrás hasta llegar a google.com.
- Intentar volver atrás cuando ya se está en google.com para comprobar el caso límite (no hay páginas previas).

Ajustes realizados luego de la primera respuesta de OpenCode:
Ninguno. La estructura LIFO enlazada permitió gestionar el historial con tamaño dinámico y tiempo O(1) en cada navegación, controlando con precisión la imposibilidad de retroceder cuando solo queda la página inicial (size <= 1).
*/

/**
 * Ejercicio 5: Historial de navegación con pila enlazada dinámica.
 * Modela el retroceso de páginas web mediante la política LIFO (Last In, First Out).
 */
public class Ej5 {

    /**
     * Nodo que representa una entrada individual en el historial de navegación.
     */
    public static class Nodo {
        String url;
        Nodo siguiente;

        public Nodo(String url) {
            this.url = url;
            this.siguiente = null;
        }
    }

    // 'head' apunta a la página web que está abierta actualmente en el navegador
    private Nodo head;

    // Contador de páginas visitadas acumuladas en la sesión
    private int size;

    /**
     * Constructor del historial de navegación.
     */
    public Ej5() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Navega a una nueva página web y la coloca en la cima del historial (operación push).
     * Complejidad: O(1).
     *
     * @param url Dirección del sitio web visitado.
     */
    public void visitarPagina(String url) {
        Nodo nuevo = new Nodo(url);
        nuevo.siguiente = head;
        head = nuevo;
        size++;
        System.out.println("-> [Navegando a]: " + url);
    }

    /**
     * Simula presionar el botón "Atrás" del navegador: desapila la página actual
     * y regresa a la página inmediatamente anterior (operación pop).
     * Complejidad: O(1).
     *
     * @return La URL a la que se regresa luego de retroceder.
     * @throws IllegalStateException Si no hay páginas previas en el historial.
     */
    public String volverAtras() {
        // Caso límite: si size <= 1, no hay páginas anteriores a las que retroceder
        if (!puedeVolverAtras()) {
            throw new IllegalStateException("No hay páginas anteriores en el historial para volver atrás.");
        }

        String paginaCerrada = head.url;
        // Desapilamos la página actual
        head = head.siguiente;
        size--;

        System.out.println("<- [Botón Atrás] Saliendo de: " + paginaCerrada + " | Regresando a: " + head.url);
        return head.url;
    }

    /**
     * Consulta la página que el usuario está viendo actualmente (operación peek).
     * Complejidad: O(1).
     *
     * @return La URL en la cima del historial.
     */
    public String paginaActual() {
        if (head == null) {
            throw new IllegalStateException("El navegador no tiene ninguna página abierta.");
        }
        return head.url;
    }

    /**
     * Evalúa si es posible retroceder en el historial.
     *
     * @return true si hay al menos dos páginas registradas (size > 1).
     */
    public boolean puedeVolverAtras() {
        return size > 1;
    }

    /**
     * Imprime el historial completo desde la página actual hasta la primera visitada.
     */
    public void imprimirHistorial() {
        if (head == null) {
            System.out.println("Historial vacío.");
            return;
        }
        System.out.println("--- Historial de navegación (de más reciente a más antigua) ---");
        Nodo actual = head;
        int orden = 1;
        while (actual != null) {
            String etiqueta = (actual == head) ? " [ACTUAL]" : "";
            System.out.printf("  %d. %s%s%n", orden++, actual.url, etiqueta);
            actual = actual.siguiente;
        }
        System.out.println("----------------------------------------------------------------");
    }

    /**
     * Retorna la cantidad de páginas acumuladas en el historial.
     */
    public int getCantidadPaginas() {
        return size;
    }

    /**
     * Prueba de ejecución completa con una sesión de navegación web simulada.
     */
    public static void main(String[] args) {
        System.out.println("========================================================================");
        System.out.println("PRUEBA: EJERCICIO 5 - HISTORIAL DE NAVEGACIÓN CON PILA (CLASE Ej5)");
        System.out.println("========================================================================");

        Ej5 navegador = new Ej5();

        // 1. Navegación secuencial hacia adelante
        System.out.println("\n--- 1. Navegación hacia adelante ---");
        navegador.visitarPagina("google.com");
        navegador.visitarPagina("wikipedia.org");
        navegador.visitarPagina("github.com");
        navegador.visitarPagina("stackoverflow.com");

        System.out.println("\nPágina visible actual: " + navegador.paginaActual());
        System.out.println("Páginas en el historial: " + navegador.getCantidadPaginas());
        navegador.imprimirHistorial();

        // 2. Retroceso secuencial con el botón "Atrás" (LIFO)
        System.out.println("\n--- 2. Presionando 'Atrás' dos veces ---");
        navegador.volverAtras(); // Vuelve a github.com
        navegador.volverAtras(); // Vuelve a wikipedia.org

        System.out.println("\nPágina visible tras retroceder: " + navegador.paginaActual());
        navegador.imprimirHistorial();

        // 3. Retroceder hasta la página inicial
        System.out.println("\n--- 3. Retrocediendo hasta la página de origen ---");
        navegador.volverAtras(); // Vuelve a google.com
        System.out.println("Página visible: " + navegador.paginaActual());
        System.out.println("¿Puede volver atrás ahora?: " + navegador.puedeVolverAtras());

        // 4. Caso límite: Intento de volver atrás sin páginas previas
        System.out.println("\n--- 4. Caso límite: Intento de volver atrás desde la página inicial ---");
        try {
            System.out.print("Presionando botón 'Atrás'... ");
            navegador.volverAtras();
        } catch (IllegalStateException e) {
            System.out.println("Excepción capturada con éxito: " + e.getMessage());
        }

        System.out.println("\n========================================================================");
        System.out.println("FUNDAMENTACIÓN LIFO EN HISTORIAL DE NAVEGACIÓN:");
        System.out.println("El historial web es intrínsecamente LIFO:");
        System.out.println("- La última página visitada (stackoverflow.com) fue la primera en abandonarse.");
        System.out.println("- La primera página visitada (google.com) quedó en la base del historial.");
        System.out.println("========================================================================");
    }
}
