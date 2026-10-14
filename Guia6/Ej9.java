/*
Prompt inicial utilizado:
Actúa como un desarrollador Java experto en estructuras de datos y sistemas operativos. Implementa el Ejercicio 9 de la Guía 6: Sistema de cola de impresión (Print Spooler) utilizando una cola enlazada propia (FIFO).

Requisitos específicos:
1. Clase TrabajoImpresion con atributos: idTrabajo (int), nombreArchivo (String), paginas (int), usuario (String), constructor validado, getters y toString() detallado.
2. Clase Ej9 que gestione la cola de impresión mediante nodos enlazados propios (NodoTrabajo), manteniendo referencias head, tail, cantidadTrabajos y totalPaginasPendientes.
3. Operaciones requeridas:
   - void agregarTrabajo(TrabajoImpresion trabajo): encola al final en O(1) y actualiza el contador de páginas.
   - TrabajoImpresion imprimirProximo(): desencola y retorna el trabajo del frente en O(1), actualizando métricas. Lanza NoSuchElementException si no hay trabajos pendientes.
   - TrabajoImpresion consultarProximo(): consulta el frente en O(1) sin retirar el trabajo. Lanza NoSuchElementException si está vacía.
   - void mostrarPendientes(): recorre e imprime todos los trabajos en cola y el resumen de páginas pendientes.
   - boolean estaVacia(), int getCantidadTrabajos(), int getTotalPaginasPendientes().
4. Justificar en la cabecera por qué el patrón FIFO es el estándar de la industria en sistemas spooler para prevenir la inanición de trabajos de impresión.
5. En el main, simular un flujo realista de oficina: envío simultáneo de varios archivos por distintos usuarios, consulta del próximo documento, impresión secuencial, envío de nuevos archivos en caliente, vaciado y prueba de cola vacía con captura de excepciones.

Ajustes realizados luego de la primera respuesta de OpenCode:
Se agregó un acumulador de total de páginas pendientes ('totalPaginasPendientes') mantenido en O(1) tanto en inserciones como en extracciones, permitiendo monitorear la carga de trabajo de la impresora sin necesidad de recorrer la lista. Se validaron exhaustivamente los parámetros de entrada y se demostró la robustez frente a intentos de impresión con la cola vacía.
*/

import java.util.NoSuchElementException;

/**
 * Ejercicio 9: Sistema de cola de impresión (Print Spooler) mediante Cola Enlazada (FIFO).
 *
 * Justificación teórica:
 * El modelo FIFO (First In, First Out) es el estándar indispensable en subsistemas de impresión
 * compartidos (*spoolers*). Una impresora es un periférico de salida físico y secuencial de uso
 * concurrente; si se procesaran las solicitudes bajo una política LIFO o aleatoria, los usuarios
 * que enviaron trabajos voluminosos o tempranos sufrirían inanición (*starvation*) indefinida
 * ante la llegada constante de documentos nuevos.
 */
public class Ej9 {

    /**
     * Representa un trabajo de impresión individual encolado por un usuario.
     */
    public static class TrabajoImpresion {
        private final int idTrabajo;
        private final String nombreArchivo;
        private final int paginas;
        private final String usuario;

        public TrabajoImpresion(int idTrabajo, String nombreArchivo, int paginas, String usuario) {
            if (idTrabajo <= 0) {
                throw new IllegalArgumentException("El ID del trabajo debe ser mayor a cero.");
            }
            if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre del archivo no puede ser nulo ni vacío.");
            }
            if (paginas <= 0) {
                throw new IllegalArgumentException("La cantidad de páginas debe ser mayor a cero.");
            }
            if (usuario == null || usuario.trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre de usuario no puede ser nulo ni vacío.");
            }
            this.idTrabajo = idTrabajo;
            this.nombreArchivo = nombreArchivo;
            this.paginas = paginas;
            this.usuario = usuario;
        }

        public int getIdTrabajo() {
            return idTrabajo;
        }

        public String getNombreArchivo() {
            return nombreArchivo;
        }

        public int getPaginas() {
            return paginas;
        }

        public String getUsuario() {
            return usuario;
        }

        @Override
        public String toString() {
            return String.format("[Trabajo #%03d | Archivo: '%s' | Páginas: %2d | Usuario: %s]",
                    idTrabajo, nombreArchivo, paginas, usuario);
        }
    }

    /**
     * Nodo enlazado simple para almacenar un trabajo de impresión en la cola.
     */
    private static class NodoTrabajo {
        TrabajoImpresion trabajo;
        NodoTrabajo siguiente;

        public NodoTrabajo(TrabajoImpresion trabajo) {
            this.trabajo = trabajo;
            this.siguiente = null;
        }
    }

    // Puntero al primer trabajo en espera de impresión (frente de la cola)
    private NodoTrabajo head;

    // Puntero al último trabajo recibido (final de la cola)
    private NodoTrabajo tail;

    // Métricas en O(1)
    private int cantidadTrabajos;
    private int totalPaginasPendientes;

    /**
     * Constructor que inicializa el spooler de impresión vacío.
     */
    public Ej9() {
        this.head = null;
        this.tail = null;
        this.cantidadTrabajos = 0;
        this.totalPaginasPendientes = 0;
    }

    /**
     * Agrega un nuevo trabajo de impresión al final de la cola en tiempo O(1).
     *
     * @param trabajo Documento a encolar.
     */
    public void agregarTrabajo(TrabajoImpresion trabajo) {
        if (trabajo == null) {
            throw new IllegalArgumentException("No se puede encolar un trabajo de impresión nulo.");
        }
        NodoTrabajo nuevo = new NodoTrabajo(trabajo);
        if (estaVacia()) {
            head = nuevo;
            tail = nuevo;
        } else {
            tail.siguiente = nuevo;
            tail = nuevo;
        }
        cantidadTrabajos++;
        totalPaginasPendientes += trabajo.getPaginas();
        System.out.println("-> [Spooler] Encolado con éxito: " + trabajo);
    }

    /**
     * Desencola y simula la impresión del trabajo ubicado al frente en tiempo O(1).
     *
     * @return El trabajo de impresión completado.
     * @throws NoSuchElementException si la cola de impresión no tiene trabajos pendientes.
     */
    public TrabajoImpresion imprimirProximo() {
        if (estaVacia()) {
            throw new NoSuchElementException("La cola de impresión está vacía. No hay trabajos para imprimir.");
        }
        TrabajoImpresion impreso = head.trabajo;
        head = head.siguiente;
        if (head == null) {
            tail = null;
        }
        cantidadTrabajos--;
        totalPaginasPendientes -= impreso.getPaginas();
        System.out.println("<- [Impresora] Imprimiendo documento: " + impreso);
        return impreso;
    }

    /**
     * Consulta el próximo trabajo que será impreso sin retirarlo de la cola en tiempo O(1).
     *
     * @return El trabajo de impresión en cabecera.
     * @throws NoSuchElementException si la cola está vacía.
     */
    public TrabajoImpresion consultarProximo() {
        if (estaVacia()) {
            throw new NoSuchElementException("La cola de impresión está vacía.");
        }
        return head.trabajo;
    }

    /**
     * Comprueba si el spooler no tiene trabajos pendientes.
     *
     * @return true si la cola está vacía, false en caso contrario.
     */
    public boolean estaVacia() {
        return head == null;
    }

    /**
     * Retorna la cantidad de documentos que esperan en la cola.
     */
    public int getCantidadTrabajos() {
        return cantidadTrabajos;
    }

    /**
     * Retorna la suma total de páginas de todos los trabajos en espera.
     */
    public int getTotalPaginasPendientes() {
        return totalPaginasPendientes;
    }

    /**
     * Muestra la lista de trabajos en espera y el balance total de páginas.
     */
    public void mostrarPendientes() {
        System.out.println("\n--- COLA DE IMPRESIÓN (Trabajos: " + cantidadTrabajos +
                " | Total Páginas: " + totalPaginasPendientes + ") ---");
        if (estaVacia()) {
            System.out.println("  (Sin trabajos pendientes en el spooler)");
        } else {
            NodoTrabajo actual = head;
            int posicion = 1;
            while (actual != null) {
                System.out.printf("  Orden %d: %s\n", posicion, actual.trabajo);
                actual = actual.siguiente;
                posicion++;
            }
        }
        System.out.println("----------------------------------------------------------------------\n");
    }

    /**
     * Demostración completa del flujo de trabajo de la cola de impresión.
     */
    public static void main(String[] args) {
        System.out.println("====================================================================");
        System.out.println("   DEMOSTRACIÓN: COLA DE IMPRESIÓN (PRINT SPOOLER FIFO)             ");
        System.out.println("====================================================================\n");

        Ej9 spooler = new Ej9();

        // 1. Estado inicial
        System.out.println("1. Verificando estado inicial del spooler:");
        System.out.println("¿Spooler libre? " + spooler.estaVacia());
        System.out.println("Trabajos en cola: " + spooler.getCantidadTrabajos());
        System.out.println("Páginas pendientes: " + spooler.getTotalPaginasPendientes());
        spooler.mostrarPendientes();

        // 2. Llegada de varios trabajos de distintos usuarios
        System.out.println("2. Envío de solicitudes de impresión desde diferentes terminales (enqueue en O(1)):");
        spooler.agregarTrabajo(new TrabajoImpresion(1, "Balance_Financiero_2025.pdf", 45, "julian.c"));
        spooler.agregarTrabajo(new TrabajoImpresion(2, "Tesis_Final_Version_3.docx", 120, "maria.r"));
        spooler.agregarTrabajo(new TrabajoImpresion(3, "Contrato_Alquiler_Firmado.pdf", 6, "abogacia.dto"));
        spooler.agregarTrabajo(new TrabajoImpresion(4, "Plano_Planta_Baja.dwg", 1, "arquitectura.lab"));

        spooler.mostrarPendientes();

        // 3. Consultar próximo trabajo sin imprimirlo
        System.out.println("3. Consultando el próximo archivo en turno (peek/front en O(1)):");
        System.out.println("Siguiente a imprimir: " + spooler.consultarProximo());

        // 4. Imprimir los dos primeros trabajos
        System.out.println("\n4. Procesando la impresión de los primeros 2 documentos (dequeue en O(1) respetando FIFO):");
        spooler.imprimirProximo();
        spooler.imprimirProximo();

        spooler.mostrarPendientes();

        // 5. Envío de un nuevo archivo en caliente
        System.out.println("5. Llega un nuevo trabajo urgente de gerencia mientras la cola opera:");
        spooler.agregarTrabajo(new TrabajoImpresion(5, "Informe_Urgente_Directorio.pdf", 12, "gerencia.gral"));

        spooler.mostrarPendientes();

        // 6. Imprimir todos los documentos restantes
        System.out.println("6. Procesando todos los trabajos restantes hasta completar el spooler:");
        while (!spooler.estaVacia()) {
            spooler.imprimirProximo();
        }

        spooler.mostrarPendientes();

        // 7. Prueba de caso límite en cola vacía
        System.out.println("7. Prueba de caso límite (intentar imprimir con spooler vacío):");
        try {
            spooler.imprimirProximo();
        } catch (NoSuchElementException e) {
            System.out.println("Excepción capturada con éxito al imprimir: " + e.getMessage());
        }

        try {
            spooler.consultarProximo();
        } catch (NoSuchElementException e) {
            System.out.println("Excepción capturada con éxito al consultar: " + e.getMessage());
        }

        System.out.println("\n====================================================================");
        System.out.println("   FIN DE LA DEMOSTRACIÓN DEL EJERCICIO 9                           ");
        System.out.println("====================================================================");
    }
}
