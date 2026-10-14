/*
Prompt inicial utilizado:
Actúa como un desarrollador Java experto en estructuras de datos. Implementa el Ejercicio 6 de la Guía 6: Sistema de atención de clientes de un banco utilizando una cola enlazada propia (FIFO).

Requisitos específicos:
1. Clase Cliente con atributos encapsulados: nombre (String), numeroTurno (int) y motivoConsulta (String), con constructor, getters y toString() formateado.
2. Implementar una Cola Enlazada con clase NodoCliente interna privada, manteniendo referencias a head (frente) y tail (final), junto a un contador tamano.
3. Métodos requeridos:
   - agregarCliente(Cliente cliente): inserta al final en O(1).
   - atenderProximo(): desencola y retorna al cliente del frente en O(1). Lanza NoSuchElementException si la fila está vacía.
   - consultarQuienSigue(): retorna el cliente del frente sin retirarlo en O(1). Lanza NoSuchElementException si está vacía.
   - estaVacia(): boolean que indica si no hay clientes en espera.
   - cantidadEnEspera(): int con la longitud actual de la fila.
   - imprimirFila(): recorre e imprime los clientes en orden de llegada.
4. Justificar en comentarios de cabecera por qué se utiliza FIFO y no LIFO en este dominio (equidad y prevención de inanición/starvation).
5. En el método main de la clase Ej6, simular un escenario completo: encolar clientes, consultar el próximo, atender secuencialmente, imprimir la fila en cada etapa y demostrar el manejo de excepciones cuando la fila queda vacía.

Ajustes realizados luego de la primera respuesta de OpenCode:
Se incorporó la validación explícita de clientes nulos en 'agregarCliente' para proteger la integridad de la estructura, y se detalló la justificación teórica sobre la imparcialidad del modelo FIFO frente a LIFO: en atención al cliente, un esquema LIFO provocaría que los clientes que llegaron primero nunca fueran atendidos mientras sigan llegando personas (inanición o starvation). La cola enlazada garantiza tanto justicia operativa como tiempo de respuesta O(1) con punteros head y tail.
*/

import java.util.NoSuchElementException;

/**
 * Ejercicio 6: Cola de atención de clientes de un banco mediante cola enlazada (FIFO).
 *
 * Justificación de la estructura:
 * Se utiliza una Cola (FIFO - First In, First Out) porque garantiza el principio
 * fundamental de atención al público: el primer cliente en llegar es el primero en ser atendido.
 * Si se utilizara una Pila (LIFO - Last In, First Out), cada nuevo cliente postergaría
 * indefinidamente a los clientes previos, generando inanición (starvation) y un trato injusto.
 */
public class Ej6 {

    /**
     * Representa un cliente que solicita atención en una sucursal bancaria.
     */
    public static class Cliente {
        private final String nombre;
        private final int numeroTurno;
        private final String motivoConsulta;

        public Cliente(String nombre, int numeroTurno, String motivoConsulta) {
            if (nombre == null || nombre.trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre del cliente no puede ser nulo ni vacío.");
            }
            if (numeroTurno <= 0) {
                throw new IllegalArgumentException("El número de turno debe ser positivo.");
            }
            if (motivoConsulta == null || motivoConsulta.trim().isEmpty()) {
                throw new IllegalArgumentException("El motivo de consulta no puede ser nulo ni vacío.");
            }
            this.nombre = nombre;
            this.numeroTurno = numeroTurno;
            this.motivoConsulta = motivoConsulta;
        }

        public String getNombre() {
            return nombre;
        }

        public int getNumeroTurno() {
            return numeroTurno;
        }

        public String getMotivoConsulta() {
            return motivoConsulta;
        }

        @Override
        public String toString() {
            return String.format("[Turno #%03d | Cliente: %s | Motivo: %s]",
                    numeroTurno, nombre, motivoConsulta);
        }
    }

    /**
     * Nodo interno de la cola enlazada que almacena la referencia al cliente
     * y el enlace al siguiente nodo de la fila.
     */
    private static class NodoCliente {
        Cliente cliente;
        NodoCliente siguiente;

        public NodoCliente(Cliente cliente) {
            this.cliente = cliente;
            this.siguiente = null;
        }
    }

    // head apunta al frente de la cola (próximo cliente a atender)
    private NodoCliente head;

    // tail apunta al final de la cola (último cliente en llegar)
    private NodoCliente tail;

    // Cantidad de clientes esperando ser atendidos
    private int tamano;

    /**
     * Constructor que inicializa una cola de atención vacía.
     */
    public Ej6() {
        this.head = null;
        this.tail = null;
        this.tamano = 0;
    }

    /**
     * Encola un nuevo cliente al final de la fila en tiempo O(1).
     *
     * @param cliente Cliente a agregar.
     */
    public void agregarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("No se puede agregar un cliente nulo a la fila.");
        }
        NodoCliente nuevo = new NodoCliente(cliente);
        if (estaVacia()) {
            head = nuevo;
            tail = nuevo;
        } else {
            tail.siguiente = nuevo;
            tail = nuevo;
        }
        tamano++;
        System.out.println("-> Llegó a la fila: " + cliente);
    }

    /**
     * Desencola y retorna al cliente ubicado en el frente de la fila en tiempo O(1).
     *
     * @return El cliente atendido.
     * @throws NoSuchElementException si la fila está vacía.
     */
    public Cliente atenderProximo() {
        if (estaVacia()) {
            throw new NoSuchElementException("No hay clientes en espera para atender.");
        }
        Cliente atendido = head.cliente;
        head = head.siguiente;
        if (head == null) {
            tail = null; // Si la cola quedó vacía, tail también debe ser null
        }
        tamano--;
        System.out.println("<- Atendido en ventanilla: " + atendido);
        return atendido;
    }

    /**
     * Consulta el cliente al frente de la fila sin retirarlo en tiempo O(1).
     *
     * @return El próximo cliente a ser atendido.
     * @throws NoSuchElementException si la fila está vacía.
     */
    public Cliente consultarQuienSigue() {
        if (estaVacia()) {
            throw new NoSuchElementException("No hay clientes esperando en la fila.");
        }
        return head.cliente;
    }

    /**
     * Determina si la cola de atención no posee clientes en espera.
     *
     * @return true si la fila está vacía, false en caso contrario.
     */
    public boolean estaVacia() {
        return head == null;
    }

    /**
     * Retorna la cantidad de clientes que aguardan en la fila.
     *
     * @return Número de clientes en espera.
     */
    public int cantidadEnEspera() {
        return tamano;
    }

    /**
     * Muestra la lista de clientes en la fila en orden de atención (desde head hasta tail).
     */
    public void imprimirFila() {
        System.out.println("\n--- ESTADO DE LA FILA DE ATENCIÓN (Total en espera: " + tamano + ") ---");
        if (estaVacia()) {
            System.out.println("  (Fila vacía: sin clientes esperando)");
        } else {
            NodoCliente actual = head;
            int posicion = 1;
            while (actual != null) {
                System.out.println("  Posición " + posicion + ": " + actual.cliente);
                actual = actual.siguiente;
                posicion++;
            }
        }
        System.out.println("-----------------------------------------------------------------------\n");
    }

    /**
     * Demostración y validación del sistema de atención bancaria.
     */
    public static void main(String[] args) {
        System.out.println("====================================================================");
        System.out.println("   DEMOSTRACIÓN: SISTEMA DE ATENCIÓN BANCARIA CON COLA ENLAZADA     ");
        System.out.println("====================================================================\n");

        Ej6 colaBanco = new Ej6();

        // 1. Estado inicial
        System.out.println("1. Comprobando estado inicial de la fila:");
        System.out.println("¿Fila vacía? " + colaBanco.estaVacia());
        System.out.println("Clientes en espera: " + colaBanco.cantidadEnEspera());
        colaBanco.imprimirFila();

        // 2. Llegada de clientes
        System.out.println("2. Llegada sucesiva de clientes al banco (enqueue en O(1)):");
        colaBanco.agregarCliente(new Cliente("Lucía Gómez", 101, "Apertura de Caja de Ahorro"));
        colaBanco.agregarCliente(new Cliente("Martín Pérez", 102, "Consulta de Saldo y Movimientos"));
        colaBanco.agregarCliente(new Cliente("Sofía Rodríguez", 103, "Solicitud de Préstamo Hipotecario"));
        colaBanco.agregarCliente(new Cliente("Carlos Fernández", 104, "Reclamo por Tarjeta de Crédito"));

        colaBanco.imprimirFila();

        // 3. Consultar quién sigue sin atenderlo
        System.out.println("3. Consultando quién sigue sin retirarlo de la fila (peek/front):");
        System.out.println("Próximo cliente a atender: " + colaBanco.consultarQuienSigue());

        // 4. Atender a los dos primeros clientes
        System.out.println("\n4. Atención de los dos primeros clientes (dequeue en O(1) respetando FIFO):");
        colaBanco.atenderProximo();
        colaBanco.atenderProximo();

        colaBanco.imprimirFila();

        // 5. Llegada de un nuevo cliente tras las primeras atenciones
        System.out.println("5. Llega un nuevo cliente mientras otros siguen esperando:");
        colaBanco.agregarCliente(new Cliente("Elena Benítez", 105, "Depósito de Cheque"));

        colaBanco.imprimirFila();

        // 6. Atender a todos los restantes hasta vaciar la fila
        System.out.println("6. Atendiendo al resto de los clientes hasta vaciar la fila:");
        while (!colaBanco.estaVacia()) {
            colaBanco.atenderProximo();
        }

        colaBanco.imprimirFila();

        // 7. Prueba del caso límite: intentar atender cuando la fila está vacía
        System.out.println("7. Prueba de caso límite (atender en cola vacía):");
        try {
            colaBanco.atenderProximo();
        } catch (NoSuchElementException e) {
            System.out.println("Excepción capturada con éxito: " + e.getMessage());
        }

        try {
            colaBanco.consultarQuienSigue();
        } catch (NoSuchElementException e) {
            System.out.println("Excepción capturada con éxito al consultar: " + e.getMessage());
        }

        System.out.println("\n====================================================================");
        System.out.println("   FIN DE LA DEMOSTRACIÓN DEL EJERCICIO 6                           ");
        System.out.println("====================================================================");
    }
}
