/*
Prompt inicial utilizado:
Actúa como un desarrollador Java experto en estructuras de datos. Implementa el Ejercicio 7 de la Guía 6: Verificador de paréntesis y delimitadores balanceados utilizando una pila enlazada propia (LIFO).

Requisitos específicos:
1. Implementar una Pila enlazada interna para caracteres (char) con clase Nodo interna, garantizando push, pop, peek y estaVacia en tiempo O(1).
2. Desarrollar el método estático o de instancia boolean estaBalanceado(String expresion) que analice delimitadores: paréntesis '()', corchetes '[]' y llaves '{}'.
3. El algoritmo debe:
   - Apilar los símbolos de apertura.
   - Al hallar un cierre, verificar que la pila no esté vacía y que el elemento en el tope corresponda a su apertura equivalente; de lo contrario, retornar false.
   - Ignorar cualquier caracter que no sea delimitador (letras, números, operadores, espacios).
   - Al concluir el recorrido, verificar que la pila haya quedado completamente vacía.
4. Incluir un método auxiliar de diagnóstico que devuelva un String con el motivo exacto del desbalanceo (ej: "Error: cierre ']' sin apertura previa", "Error: se esperaba ')' pero se encontró ']'").
5. Justificar detalladamente en los comentarios de cabecera por qué la estructura LIFO es la idónea para este problema.
6. En el método main de la clase Ej7, incluir un conjunto exhaustivo de pruebas con casos válidos, casos inválidos por cierres huérfanos, casos inválidos por cruce de tipos y casos con aperturas incompletas.

Ajustes realizados luego de la primera respuesta de OpenCode:
Se integró el soporte para tres tipos de delimitadores concurrentes: paréntesis '()', corchetes '[]' y llaves '{}'. La pila LIFO enlazada resulta matemáticamente óptima porque cada apertura establece un ámbito anidado cuyo cierre inmediato debe coincidir con la cima de la pila. El diagnóstico adicional permite no solo saber si la expresión es válida sino identificar la posición y causa del error sintáctico.
*/

import java.util.NoSuchElementException;

/**
 * Ejercicio 7: Verificador de paréntesis y delimitadores balanceados mediante Pila Enlazada (LIFO).
 *
 * Justificación teórica:
 * El anidamiento sintáctico de paréntesis, corchetes y llaves sigue de forma natural
 * la disciplina LIFO (Last In, First Out). Cada símbolo de apertura introduce un nuevo nivel
 * de anidamiento que debe resolverse antes de poder cerrar los niveles previos. Por lo tanto,
 * el delimitador abierto más recientemente debe ser obligatoriamente el primero en cerrarse.
 */
public class Ej7 {

    /**
     * Nodo enlazado simple para almacenar caracteres individuales en la pila.
     */
    private static class NodoChar {
        char caracter;
        int posicion; // Índice en la cadena original para diagnóstico preciso
        NodoChar siguiente;

        public NodoChar(char caracter, int posicion) {
            this.caracter = caracter;
            this.posicion = posicion;
            this.siguiente = null;
        }
    }

    /**
     * Implementación enlazada propia de una Pila de caracteres con operaciones en O(1).
     */
    private static class PilaCaracteres {
        private NodoChar cima;
        private int tamano;

        public PilaCaracteres() {
            this.cima = null;
            this.tamano = 0;
        }

        public void push(char c, int pos) {
            NodoChar nuevo = new NodoChar(c, pos);
            nuevo.siguiente = cima;
            cima = nuevo;
            tamano++;
        }

        public NodoChar pop() {
            if (estaVacia()) {
                throw new NoSuchElementException("La pila de caracteres está vacía.");
            }
            NodoChar extraido = cima;
            cima = cima.siguiente;
            tamano--;
            return extraido;
        }

        public NodoChar peek() {
            if (estaVacia()) {
                throw new NoSuchElementException("La pila de caracteres está vacía.");
            }
            return cima;
        }

        public boolean estaVacia() {
            return cima == null;
        }

        public int getTamano() {
            return tamano;
        }
    }

    /**
     * Determina si un carácter es un símbolo de apertura.
     */
    private static boolean esApertura(char c) {
        return c == '(' || c == '[' || c == '{';
    }

    /**
     * Determina si un carácter es un símbolo de cierre.
     */
    private static boolean esCierre(char c) {
        return c == ')' || c == ']' || c == '}';
    }

    /**
     * Verifica si una apertura y un cierre son complementarios y del mismo tipo.
     */
    private static boolean sonPareja(char apertura, char cierre) {
        return (apertura == '(' && cierre == ')') ||
               (apertura == '[' && cierre == ']') ||
               (apertura == '{' && cierre == '}');
    }

    /**
     * Evalúa si una expresión matemática o textual posee todos sus delimitadores balanceados.
     * Complejidad temporal: O(N) donde N es la longitud de la cadena.
     * Complejidad espacial: O(N) en el peor caso (todos caracteres de apertura).
     *
     * @param expresion Cadena de caracteres a verificar.
     * @return true si los delimitadores están correctamente balanceados y anidados; false en caso contrario.
     */
    public static boolean estaBalanceado(String expresion) {
        if (expresion == null) {
            return false;
        }

        PilaCaracteres pila = new PilaCaracteres();

        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);

            if (esApertura(c)) {
                pila.push(c, i);
            } else if (esCierre(c)) {
                if (pila.estaVacia()) {
                    return false; // Cierre huérfano
                }
                NodoChar tope = pila.pop();
                if (!sonPareja(tope.caracter, c)) {
                    return false; // Cruce de tipos no concordante
                }
            }
        }

        return pila.estaVacia(); // Válido solo si no quedaron aperturas pendientes
    }

    /**
     * Realiza el análisis sintáctico de la expresión y devuelve un informe detallado
     * indicando si está balanceada o el motivo y posición exacta del error.
     *
     * @param expresion Cadena a analizar.
     * @return Diagnóstico descriptivo del balanceo.
     */
    public static String analizarBalanceo(String expresion) {
        if (expresion == null) {
            return "ERROR: La expresión proporcionada es nula.";
        }

        PilaCaracteres pila = new PilaCaracteres();

        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);

            if (esApertura(c)) {
                pila.push(c, i);
            } else if (esCierre(c)) {
                if (pila.estaVacia()) {
                    return String.format("DESBALANCEADA -> Error en índice %d: Cierre '%c' sin apertura previa.", i, c);
                }
                NodoChar tope = pila.pop();
                if (!sonPareja(tope.caracter, c)) {
                    return String.format("DESBALANCEADA -> Error en índice %d: Se cerró con '%c' pero en el tope estaba '%c' (abierto en índice %d).",
                            i, c, tope.caracter, tope.posicion);
                }
            }
        }

        if (!pila.estaVacia()) {
            NodoChar restante = pila.peek();
            return String.format("DESBALANCEADA -> Error: Quedaron %d apertura(s) sin cerrar. La más reciente es '%c' en índice %d.",
                    pila.getTamano(), restante.caracter, restante.posicion);
        }

        return "CORRECTA -> Todos los delimitadores están correctamente balanceados y anidados.";
    }

    /**
     * Ejecución de pruebas y validaciones de los diferentes casos de uso.
     */
    public static void main(String[] args) {
        System.out.println("====================================================================");
        System.out.println("   DEMOSTRACIÓN: VERIFICADOR DE DELIMITADORES BALANCEADOS (PILA)    ");
        System.out.println("====================================================================\n");

        String[] casosDePrueba = {
            // Casos válidos
            "(a + b) * (c - d)",
            "{ [ 2 * (a + b) ] / 5 }",
            "((()))",
            "[ { ( ) } ]",
            "sin delimitadores en el texto 123",
            "",

            // Casos inválidos: cierres huérfanos
            "())",
            "]",
            "a + b) * (c + d)",

            // Casos inválidos: tipos no concordantes (cruzados)
            "( [ ) ]",
            "{ [ ( a + b ) ] } )",
            "{ ( ] ) }",

            // Casos inválidos: aperturas sin cerrar
            "((a + b)",
            "{ [ (",
            "function() { if (x > 0) { return true;"
        };

        for (int i = 0; i < casosDePrueba.length; i++) {
            String expr = casosDePrueba[i];
            boolean balanceado = estaBalanceado(expr);
            String diagnostico = analizarBalanceo(expr);

            System.out.printf("Caso %2d: \"%s\"\n", (i + 1), expr);
            System.out.printf("  ¿Balanceado? : %s\n", balanceado ? "SÍ" : "NO");
            System.out.printf("  Diagnóstico  : %s\n\n", diagnostico);
        }

        System.out.println("====================================================================");
        System.out.println("   FIN DE LA DEMOSTRACIÓN DEL EJERCICIO 7                           ");
        System.out.println("====================================================================");
    }
}
