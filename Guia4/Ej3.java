/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Quiero implementar en Java una clase llamada Ej3 que verifique si los paréntesis de una 
 * expresión matemática están correctamente balanceados utilizando una Pila de caracteres 
 * implementada internamente sobre un arreglo de tamaño fijo.
 * 
 * El programa debe justificar por qué este problema se resuelve con una pila (LIFO):
 * - Cada vez que aparece un paréntesis de apertura '(', se apila (push), representando una 
 *   deuda de cierre pendiente.
 * - Cada vez que aparece un paréntesis de cierre ')', se desapila (pop) el paréntesis de 
 *   apertura más reciente para cancelar el par.
 * - Si aparece un ')' cuando la pila está vacía, la expresión es inmediatamente inválida 
 *   (hay un cierre sin apertura previa).
 * - Al finalizar el recorrido de la cadena, la expresión es válida solo si la pila quedó 
 *   completamente vacía; si quedaron elementos en la pila, significa que quedaron aperturas 
 *   sin su correspondiente cierre.
 * 
 * La estructura interna de la pila debe gestionarse mediante un arreglo 'char[] pila' con 
 * capacidad igual a la longitud de la expresión analizada y un puntero 'int top' inicializado en -1:
 * - push(char c): almacena el carácter en pila[++top].
 * - pop(): desapila y decrementa top--.
 * - isEmpty(): retorna true si top == -1.
 * 
 * La clase debe implementar el método estático:
 * public static boolean validarParentesis(String expresion)
 * 
 * Quiero que el código tenga comentarios explicando:
 * - Cuándo se realiza push ('(') y cuándo pop (')').
 * - La detección de fallo temprano cuando se encuentra ')' con pila vacía.
 * - La comprobación final de pila vacía (isEmpty()) al concluir el recorrido.
 * 
 * La verificación del resultado debe probarse en el método main con los casos del enunciado y adicionales:
 * - Caso 1 (válido del enunciado): "(5 + 3) * (2 + 1)" -> Válido.
 * - Caso 2 (inválido del enunciado - exceso de cierres): "(5 + 3)) * (2 + 1" -> Inválido.
 * - Caso 3 (inválido - apertura sin cierre): "((5 + 2) * 3" -> Inválido.
 * - Caso 4 (inválido - cierre al inicio): ")5 + 3(" -> Inválido.
 * - Caso 5 (sin paréntesis): "5 + 3 * 2" -> Válido.
 * =========================================================================================
 */

/**
 * Ejercicio 3: Validación de paréntesis balanceados.
 * Utiliza una estructura de Pila sobre un arreglo de tamaño fijo para verificar
 * la correcta apertura y clausura de paréntesis en expresiones matemáticas.
 */
public class Ej3 {

    /**
     * Pila interna de caracteres implementada con un arreglo de tamaño fijo.
     */
    private static class PilaCaracteres {
        private final char[] datos;
        private int top;

        public PilaCaracteres(int capacidad) {
            this.datos = new char[capacidad];
            this.top = -1; // -1 indica que la pila está vacía
        }

        public void push(char c) {
            datos[++top] = c;
        }

        public char pop() {
            return datos[top--];
        }

        public boolean isEmpty() {
            return top == -1;
        }
    }

    /**
     * Determina si una expresión matemática tiene sus paréntesis correctamente balanceados.
     *
     * @param expresion Cadena de texto que contiene la fórmula a evaluar.
     * @return true si los paréntesis están balanceados; false en caso contrario.
     */
    public static boolean validarParentesis(String expresion) {
        if (expresion == null) {
            return false;
        }

        // El tamaño máximo que puede alcanzar la pila es la longitud total de la expresión
        int n = expresion.length();
        PilaCaracteres pila = new PilaCaracteres(n);

        // Recorrido secuencial de cada carácter de la expresión
        for (int i = 0; i < n; i++) {
            char c = expresion.charAt(i);

            // 1. Si encontramos un paréntesis de apertura, lo apilamos (push)
            // Representa que se inició un bloque que obligatoriamente deberá cerrarse después
            if (c == '(') {
                pila.push(c);
            }
            // 2. Si encontramos un paréntesis de cierre
            else if (c == ')') {
                // Caso límite 1: Aparece un cierre pero la pila está vacía.
                // Significa que hay un ')' huérfano sin su correspondiente '(' previo.
                if (pila.isEmpty()) {
                    return false; // Corte temprano: expresión inválida de inmediato
                }
                // Si había un '(', lo desapilamos para cancelar el par abierto
                pila.pop();
            }
            // Los demás caracteres (números, operadores, espacios) se ignoran en el balance
        }

        // Caso límite 2: Al terminar el recorrido de toda la expresión:
        // - Si la pila quedó vacía, cada '(' tuvo su ')'.
        // - Si la pila NO está vacía, quedaron '(' que nunca fueron cerrados.
        return pila.isEmpty();
    }

    /**
     * Prueba de ejecución con los casos del enunciado y casos límite complementarios.
     */
    public static void main(String[] args) {
        System.out.println("========================================================================");
        System.out.println("PRUEBA DE EJECUCIÓN: VALIDACIÓN DE PARÉNTESIS (CLASE Ej3)");
        System.out.println("========================================================================");

        // Caso 1: Caso válido provisto en el enunciado
        probarExpresion("(5 + 3) * (2 + 1)", true);

        // Caso 2: Caso inválido provisto en el enunciado (cierre de más y apertura sin cerrar)
        probarExpresion("(5 + 3)) * (2 + 1", false);

        // Caso 3: Apertura sin cierre
        probarExpresion("((5 + 2) * 3", false);

        // Caso 4: Paréntesis invertidos (cierre antes que apertura)
        probarExpresion(")5 + 3(", false);

        // Caso 5: Expresión sin paréntesis
        probarExpresion("5 + 3 * 2", true);

        // Caso 6: Paréntesis anidados múltiples válidos
        probarExpresion("(((10 + 2) - 4) * (3))", true);

        System.out.println("========================================================================");
        System.out.println("MECÁNICA EXPLICADA:");
        System.out.println("- Cada '(' se guarda en la pila (push).");
        System.out.println("- Cada ')' saca el '(' correspondiente (pop).");
        System.out.println("- Si se intenta hacer pop con la pila vacía -> Inválido (cierre sin apertura).");
        System.out.println("- Si al final la pila no está vacía -> Inválido (apertura sin cierre).");
        System.out.println("========================================================================");
    }

    private static void probarExpresion(String exp, boolean esperado) {
        boolean obtenido = validarParentesis(exp);
        boolean correcto = (obtenido == esperado);
        System.out.printf("Expresión: %-25s -> Obtenido: %-8s | Esperado: %-8s | Estado: %s%n",
                "\"" + exp + "\"",
                obtenido ? "VÁLIDO" : "INVÁLIDO",
                esperado ? "VÁLIDO" : "INVÁLIDO",
                correcto ? "CORRECTO" : "FALLÓ");
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación / Ajuste del prompt:
 *    Se dimensionó el arreglo de la pila interna con la longitud total de la cadena
 *    'expresion.length()'.
 * 
 * 2. Justificación técnica:
 *    En el peor escenario teórico, la cadena podría estar compuesta exclusivamente por
 *    paréntesis de apertura (ej. "(((((((("). Al asignar la longitud de la cadena como
 *    capacidad máxima del arreglo fijo de la pila, se garantiza matemáticamente que la pila
 *    nunca sufrirá un desbordamiento (Stack Overflow) en tiempo de ejecución, manteniendo
 *    complejidad espacial O(n) y tiempo lineal O(n) en una única pasada sobre la cadena.
 * =========================================================================================
 */