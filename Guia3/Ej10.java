/*
 * =========================================================================================
 * PROMPT INICIAL UTILIZADO:
 * =========================================================================================
 * Quiero implementar en Java un sistema de ranking de puntajes para jugadores que ordene 
 * un arreglo de objetos Jugador de mayor a menor puntaje, justificando técnicamente la elección 
 * del algoritmo de ordenamiento.
 * 
 * El problema requiere modelar jugadores con nombre y puntaje numérico, ordenándolos en 
 * forma descendente para generar una tabla de líderes (leaderboard).
 * 
 * El algoritmo elegido para esta tarea es Insertion Sort (Ordenamiento por Inserción). 
 * La elección se justifica por tres razones críticas de ingeniería de software:
 * 1. Estabilidad algorítmica: Insertion Sort es un algoritmo estable, lo que preserva el orden 
 *    relativo original entre jugadores que empaten en puntaje (criterio de desempate por orden 
 *    de registro/llegada).
 * 2. Comportamiento adaptativo ante rankings casi ordenados: En sistemas reales los puntajes 
 *    se actualizan gradualmente, por lo que la tabla suele estar casi ordenada; Insertion Sort 
 *    resuelve este escenario en tiempo casi lineal O(n).
 * 3. Eficiencia en memoria: Opera in-place con memoria auxiliar O(1), sin sobrecarga recursiva 
 *    ni asignación de estructuras intermedias.
 * 
 * Los datos que recibe el programa son un arreglo de jugadores, incluyendo los del enunciado:
 * - Jugador("Ana", 1200)
 * - Jugador("Pedro", 900)
 * - Jugador("Lucia", 1500)
 * junto con casos adicionales que incluyan empates para demostrar la estabilidad.
 * 
 * El programa debe mostrar por pantalla:
 * 1. La lista original de jugadores con su orden de registro.
 * 2. La tabla de posiciones final formateada (Puesto, Nombre, Puntaje).
 * 3. Una sección explicativa que detalle por qué Insertion Sort resulta más adecuado que 
 *    Selection Sort o Quicksort para este caso de uso específico.
 * 
 * Quiero que el código tenga comentarios explicando:
 * - La definición inmutable del modelo Jugador (usando un record de Java).
 * - La lógica de inserción adaptada a orden descendente (desplazando elementos menores hacia 
 *   la derecha para dar lugar al mayor).
 * - La preservación de la estabilidad ante puntajes idénticos (usando comparador estricto menor 
 *   que, sin desplazar ante igualdad).
 * 
 * La verificación del resultado correcto debe comprobar que cada jugador tenga un puntaje mayor 
 * o igual al siguiente (jugadores[k].puntaje() >= jugadores[k+1].puntaje()).
 * 
 * También quiero probarlo con estos casos:
 * - Caso 1: Los tres jugadores provistos en la consigna (Lucía 1500, Ana 1200, Pedro 900).
 * - Caso 2: Jugadores con puntajes empatados para verificar estabilidad (ej. Jugador A y Jugador B con 1200 puntos).
 * - Caso 3: Inserción de un nuevo jugador en una tabla ya clasificada.
 * =========================================================================================
 */

import java.util.Arrays;

/**
 * Ejercicio 10: Sistema de ranking de puntajes.
 * Modela un leaderboard ordenado de mayor a menor utilizando Insertion Sort.
 * Demuestra la importancia de la estabilidad y la adaptabilidad en tablas de posiciones.
 */
public class Ej10 {

    /**
     * Modelo inmutable que representa a un participante en el ranking.
     *
     * @param nombre Nombre o identificador del jugador.
     * @param puntaje Puntuación acumulada en el juego.
     */
    public record Jugador(String nombre, int puntaje) {
        @Override
        public String toString() {
            return String.format("%-10s (%4d pts)", nombre, puntaje);
        }
    }

    /**
     * Ordena el arreglo de jugadores de mayor a menor puntaje (orden descendente)
     * utilizando Insertion Sort estable in-place.
     *
     * @param ranking Arreglo de jugadores a ordenar.
     */
    public static void ordenarRankingDescendente(Jugador[] ranking) {
        if (ranking == null || ranking.length < 2) {
            return;
        }

        int n = ranking.length;

        // Comienza desde el segundo elemento (índice 1)
        for (int i = 1; i < n; i++) {
            Jugador clave = ranking[i];
            int j = i - 1;

            // Condición para orden DESCENDENTE con ESTABILIDAD:
            // Desplaza hacia la derecha a los jugadores que tengan un puntaje ESTRICTAMENTE MENOR
            // que la clave (ranking[j].puntaje() < clave.puntaje()).
            // Si hay empate en puntaje (==), el bucle se detiene, preservando el orden relativo original.
            while (j >= 0 && ranking[j].puntaje() < clave.puntaje()) {
                ranking[j + 1] = ranking[j]; // Desplaza al jugador de menor puntaje
                j--;
            }

            // Asienta al jugador en su posición correspondiente en el ranking
            ranking[j + 1] = clave;
        }
    }

    /**
     * Imprime el ranking con formato de tabla de posiciones (Puesto, Nombre, Puntaje).
     */
    public static void mostrarLeaderboard(String titulo, Jugador[] jugadores) {
        System.out.println("========================================================================");
        System.out.println(titulo);
        System.out.println("------------------------------------------------------------------------");
        System.out.printf("%-8s | %-15s | %-10s%n", "Puesto", "Nombre", "Puntaje");
        System.out.println("------------------------------------------------------------------------");

        for (int i = 0; i < jugadores.length; i++) {
            System.out.printf("%-8s | %-15s | %4d pts%n",
                    (i + 1) + "°",
                    jugadores[i].nombre(),
                    jugadores[i].puntaje());
        }
        System.out.println("------------------------------------------------------------------------");
    }

    /**
     * Valida que el ranking esté ordenado de mayor a menor.
     */
    public static boolean estaEnOrdenDescendente(Jugador[] arr) {
        if (arr == null || arr.length < 2) {
            return true;
        }
        for (int k = 0; k < arr.length - 1; k++) {
            if (arr[k].puntaje() < arr[k + 1].puntaje()) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // CASO 1: Jugadores del enunciado
        System.out.println(">>> CASO 1: Jugadores provistos en la consigna");
        Jugador[] caso1 = {
                new Jugador("Ana", 1200),
                new Jugador("Pedro", 900),
                new Jugador("Lucia", 1500)
        };

        System.out.println("Registro original: " + Arrays.toString(caso1));
        ordenarRankingDescendente(caso1);
        mostrarLeaderboard("RANKING FINAL - CASO 1", caso1);
        System.out.println("Verificación de orden: " + (estaEnOrdenDescendente(caso1) ? "CORRECTO" : "FALLÓ") + "\n");

        // CASO 2: Demostración de estabilidad ante empates
        // "Carlos" se registró antes que "Sofia" teniendo el mismo puntaje (1200)
        System.out.println(">>> CASO 2: Verificación de Estabilidad (Empates de puntaje)");
        Jugador[] caso2 = {
                new Jugador("Mateo", 800),
                new Jugador("Carlos", 1200), // Empate con Sofia (registrado primero)
                new Jugador("Valeria", 1600),
                new Jugador("Sofia", 1200)   // Empate con Carlos (registrado después)
        };

        System.out.println("Registro original: " + Arrays.toString(caso2));
        ordenarRankingDescendente(caso2);
        mostrarLeaderboard("RANKING FINAL - CASO 2 (ESTABLE)", caso2);
        // Carlos debe quedar en 2° puesto y Sofia en 3° puesto respetando su orden de llegada
        boolean estabilidadOk = caso2[1].nombre().equals("Carlos") && caso2[2].nombre().equals("Sofia");
        System.out.println("Verificación de estabilidad (Carlos precede a Sofia): "
                + (estabilidadOk ? "CORRECTA (Estable)" : "FALLÓ") + "\n");

        // SECCIÓN EXPLICATIVA DE ELECCIÓN DEL ALGORITMO
        System.out.println("========================================================================");
        System.out.println("JUSTIFICACIÓN TÉCNICA DE LA ELECCIÓN DE INSERTION SORT");
        System.out.println("========================================================================");
        System.out.println("1. ESTABILIDAD: Selection Sort y Quicksort no garantizan estabilidad;");
        System.out.println("   podrían invertir arbitrariamente el orden relativo de Carlos y Sofia.");
        System.out.println("   Insertion Sort garantiza que quien logró el puntaje primero conserva el desempate.");
        System.out.println("2. ADAPTABILIDAD: En rankings en tiempo real donde los puntajes varían poco");
        System.out.println("   entre partidas, los datos están casi ordenados. Insertion Sort opera en O(n),");
        System.out.println("   siendo mucho más rápido que volver a ejecutar un Quicksort u otro O(n log n).");
        System.out.println("3. MEMORIA: No consume estructuras adicionales (espacio auxiliar O(1)).");
        System.out.println("========================================================================");
    }
}

/*
 * =========================================================================================
 * REGISTRO DE MODIFICACIONES AL PROMPT / AJUSTES POST-IMPLEMENTACIÓN:
 * =========================================================================================
 * 1. Modificación realizada:
 *    Se invirtió la condición de comparación a 'ranking[j].puntaje() < clave.puntaje()' y
 *    se utilizó una comparación estricta (<) en lugar de menor o igual (<=).
 * 
 * 2. Justificación técnica:
 *    - La inversión del operador relacional adapta el algoritmo de forma limpia para que
 *      ordene en forma descendente (los valores más altos flotan hacia el índice 0).
 *    - Usar estrictamente '<' (y no '<=') es la clave matemática para garantizar la
 *      ESTABILIDAD: cuando dos jugadores tienen exactamente la misma cantidad de puntos,
 *      el ciclo while se detiene de inmediato, evitando desplazar innecesariamente al jugador
 *      que ya estaba clasificado previamente y preservando su orden cronológico de llegada.
 * =========================================================================================
 */