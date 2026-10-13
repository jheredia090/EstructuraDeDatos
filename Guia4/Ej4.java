/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Quiero implementar en Java una clase llamada Ej4 que simule un historial de navegación web 
 * utilizando una Pila (Stack) de cadenas (URLs) sobre un arreglo de tamaño fijo, modelando el 
 * comportamiento del botón "Atrás".
 * 
 * El programa debe justificar por qué el historial de navegación opera estrictamente bajo el 
 * principio LIFO (Last In, First Out):
 * - Cada vez que el usuario navega a un nuevo enlace, la URL se apila (push) sobre la cima del historial.
 * - Cuando el usuario presiona "Atrás", la página web actual (la última visitada) se desapila (pop) 
 *   y la página inmediatamente anterior pasa a ser la nueva cima visible.
 * - El orden de retorno es exactamente el inverso al orden cronológico de visita.
 * 
 * La estructura interna debe gestionarse mediante un arreglo 'String[] historial' con una 
 * capacidad máxima y un índice 'int top' inicializado en -1:
 * - visitarPagina(String url): apila la URL incrementando top. Si el arreglo está lleno, lanza 
 *   IllegalStateException (Stack Overflow).
 * - volverAtras(): desapila la página actual para regresar a la anterior. Si no hay páginas previas 
 *   (top <= 0), lanza IllegalStateException indicando que no se puede retroceder más.
 * - obtenerPaginaActual(): retorna la URL en historial[top] sin modificar el puntero (peek).
 * - puedeVolverAtras(): retorna true si top > 0.
 * - cantidadPaginas(): retorna top + 1.
 * 
 * Quiero que el código tenga comentarios explicando:
 * - La correspondencia entre la página actualmente visible y el índice top.
 * - La distinción entre un historial vacío (top == -1) y un historial con una sola página inicial 
 *   donde ya no se puede retroceder (top == 0).
 * - La limpieza de referencias al desapilar (historial[top] = null) para evitar retención innecesaria 
 *   de memoria.
 * 
 * La verificación del resultado debe probarse en el método main con una sesión de navegación simulada:
 * - Navegar sucesivamente por: "https://google.com", "https://wikipedia.org", "https://github.com", 
 *   "https://stackoverflow.com".
 * - Consultar la página actual tras cada navegación.
 * - Retroceder dos veces con volverAtras(), verificando que regresa en orden inverso (primero a 
 *   wikipedia, luego a google).
 * - Intentar retroceder cuando ya se está en la página inicial ("https://google.com") para comprobar 
 *   el control del caso límite.
 * =========================================================================================
 */

/**
 * Ejercicio 4: Simulador de historial de navegación web.
 * Modela el botón "Atrás" de un navegador web mediante el principio LIFO (Last In, First Out).
 */
public class Ej4 {

    // Arreglo que almacena las direcciones URL visitadas en orden cronológico
    private final String[] historial;

    // Índice del tope: representa la página web que el usuario está viendo actualmente
    // top = -1 indica navegador recién abierto sin ninguna página cargada
    private int top;

    // Capacidad máxima de páginas registradas en memoria
    private final int capacidadMaxima;

    /**
     * Constructor del simulador de historial.
     *
     * @param capacidadMaxima Cantidad máxima de URLs que puede almacenar la pila de navegación.
     */
    public Ej4(int capacidadMaxima) {
        if (capacidadMaxima <= 0) {
            throw new IllegalArgumentException("La capacidad del historial debe ser mayor a 0.");
        }
        this.capacidadMaxima = capacidadMaxima;
        this.historial = new String[capacidadMaxima];
        this.top = -1; // Comienza sin páginas cargadas
    }

    /**
     * Navega a una nueva página web y la agrega al historial (operación push).
     *
     * @param url Dirección web visitada.
     * @throws IllegalStateException Si el historial alcanzó su capacidad máxima.
     */
    public void visitarPagina(String url) {
        if (estaLleno()) {
            throw new IllegalStateException("Desbordamiento (Stack Overflow): El historial de navegación está lleno.");
        }
        // Se incrementa top y se guarda la nueva página en la cima del historial
        historial[++top] = url;
        System.out.println("-> Navegando a: " + url + " | [Página visible actual]");
    }

    /**
     * Simula el botón "Atrás": desapila la página actual y regresa a la página anterior (operación pop).
     *
     * @return La URL a la que se regresa luego de retroceder.
     * @throws IllegalStateException Si no existe una página previa en el historial.
     */
    public String volverAtras() {
        // Caso límite: Si top == -1 (vacío) o top == 0 (solo queda la página inicial),
        // no hay a dónde retroceder; el botón "Atrás" está deshabilitado en el navegador.
        if (!puedeVolverAtras()) {
            throw new IllegalStateException("No hay páginas previas a las que volver en el historial.");
        }

        String paginaAbandonada = historial[top];
        historial[top--] = null; // Limpieza de referencia para evitar retención de memoria

        System.out.println("<- [Botón Atrás] Saliendo de: " + paginaAbandonada 
                + " | Regresando a: " + obtenerPaginaActual());
        return obtenerPaginaActual();
    }

    /**
     * Consulta la página web actualmente abierta en el navegador (operación peek).
     *
     * @return La URL activa en la cima del historial.
     */
    public String obtenerPaginaActual() {
        if (estaVacio()) {
            throw new IllegalStateException("El navegador no tiene ninguna página abierta.");
        }
        return historial[top];
    }

    /**
     * Evalúa si es posible presionar el botón "Atrás".
     *
     * @return true si hay al menos dos páginas en el historial (top > 0).
     */
    public boolean puedeVolverAtras() {
        return top > 0;
    }

    /**
     * Indica si el historial no contiene ninguna URL.
     */
    public boolean estaVacio() {
        return top == -1;
    }

    /**
     * Indica si el historial alcanzó el tope del arreglo.
     */
    public boolean estaLleno() {
        return top == capacidadMaxima - 1;
    }

    /**
     * Retorna la cantidad de URLs almacenadas en la sesión.
     */
    public int cantidadPaginas() {
        return top + 1;
    }

    /**
     * Simulación de una sesión completa de navegación en consola.
     */
    public static void main(String[] args) {
        System.out.println("========================================================================");
        System.out.println("SIMULADOR DE HISTORIAL DE NAVEGACIÓN WEB (CLASE Ej4)");
        System.out.println("========================================================================");

        Ej4 navegador = new Ej4(10);

        // 1. Navegación secuencial hacia adelante
        System.out.println("--- 1. Navegación secuencial hacia adelante ---");
        navegador.visitarPagina("https://google.com");
        navegador.visitarPagina("https://wikipedia.org");
        navegador.visitarPagina("https://github.com");
        navegador.visitarPagina("https://stackoverflow.com");

        System.out.println("\nTotal de páginas en el historial: " + navegador.cantidadPaginas());
        System.out.println("Página activa en pantalla: " + navegador.obtenerPaginaActual());
        System.out.println("¿Se puede presionar 'Atrás'?: " + navegador.puedeVolverAtras());

        // 2. Retroceso secuencial usando el botón "Atrás" (LIFO)
        System.out.println("\n--- 2. Retroceso secuencial (Botón 'Atrás' - LIFO) ---");
        navegador.volverAtras(); // Vuelve a github.com
        navegador.volverAtras(); // Vuelve a wikipedia.org

        System.out.println("\nPágina activa tras dos retrocesos: " + navegador.obtenerPaginaActual());

        // 3. Retroceder hasta la página inicial
        System.out.println("\n--- 3. Retrocediendo hasta la página inicial ---");
        navegador.volverAtras(); // Vuelve a google.com
        System.out.println("Página activa: " + navegador.obtenerPaginaActual());
        System.out.println("¿Se puede volver atrás ahora?: " + navegador.puedeVolverAtras());

        // 4. Caso límite: Intentar retroceder más allá de la página inicial
        System.out.println("\n--- 4. Caso límite: Intento de retroceder sin páginas previas ---");
        try {
            System.out.print("Intentando volverAtras() en la página de inicio... ");
            navegador.volverAtras();
        } catch (IllegalStateException e) {
            System.out.println("Excepción capturada con éxito: " + e.getMessage());
        }

        System.out.println("\n========================================================================");
        System.out.println("FUNDAMENTACIÓN LIFO:");
        System.out.println("El historial de un navegador web es un ejemplo puro de LIFO:");
        System.out.println("La última página visitada (Stack Overflow) fue la primera en abandonarse.");
        System.out.println("La primera página visitada (Google) es la última que queda en la base.");
        System.out.println("========================================================================");
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación / Ajuste del prompt:
 *    Se definió la condición de parada 'puedeVolverAtras()' como 'top > 0' en lugar de 'top >= 0'.
 * 
 * 2. Justificación técnica:
 *    En una pila genérica tradicional, la condición de subdesbordamiento ocurre cuando la pila
 *    está completamente vacía (top == -1). Sin embargo, en el modelo de dominio de un navegador
 *    web, cuando queda exactamente una página cargada (top == 0), esa es la página activa que
 *    el usuario está viendo. No tiene sentido "desapilarla" dejándolo sin página web; lo correcto
 *    es bloquear el retroceso (botón Atrás inhabilitado) cuando top == 0, garantizando que el
 *    usuario siempre tenga una página activa en pantalla.
 * =========================================================================================
 */