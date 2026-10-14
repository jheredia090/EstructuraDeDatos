/*
Prompt inicial utilizado:
Actúa como un desarrollador Java experto en estructuras de datos. Implementa el Ejercicio 8 de la Guía 6: Inversión de palabras y cadenas de texto utilizando una pila enlazada propia (LIFO).

Requisitos específicos:
1. Implementar una Pila enlazada interna de caracteres con clase NodoChar interna, con puntero head a la cima y operaciones push, pop y estaVacia en tiempo O(1).
2. Implementar el método String invertirPalabra(String texto) que:
   - Recorra la cadena de entrada e inserte cada carácter en la pila en O(N).
   - Desapile todos los caracteres reconstruyendo la cadena invertida con StringBuilder en O(N).
   - Gestione adecuadamente cadenas vacías y caracteres únicos.
3. Implementar un método complementario boolean esPalindromo(String texto) que utilice la lógica de la pila para comprobar si una palabra o frase se lee igual en ambos sentidos (ignorando espacios y diferencias entre mayúsculas y minúsculas).
4. Explicar en comentarios de cabecera por qué la propiedad LIFO invierte de forma natural cualquier secuencia temporal o posicional.
5. En el método main de la clase Ej8, probar una batería completa de casos: "algoritmo", "estructura", palabras de 1 letra, cadena vacía, frases con espacios y verificación de palíndromos ("reconocer", "radar", "anita lava la tina").

Ajustes realizados luego de la primera respuesta de OpenCode:
Se utilizó un StringBuilder para acumular los caracteres desapilados y evitar la creación innecesaria de objetos String inmutables durante la concatenación, optimizando el tiempo total a O(N). Se incorporó además la verificación de palíndromos normalizando previamente los caracteres alfanuméricos en minúsculas.
*/

import java.util.NoSuchElementException;

/**
 * Ejercicio 8: Inversión de palabras y verificación de palíndromos mediante Pila Enlazada (LIFO).
 *
 * Justificación teórica:
 * La estructura Pila (LIFO - Last In, First Out) produce una inversión intrínseca del orden
 * de cualquier secuencia de datos. Dado que el último elemento apilado es el primero en ser
 * retirado, si se ingresan los caracteres de izquierda a derecha (posiciones 0 a N-1), la
 * operación pop() los devolverá necesariamente en sentido contrario (de N-1 a 0).
 */
public class Ej8 {

    /**
     * Nodo enlazado simple para almacenar un carácter en la pila.
     */
    private static class NodoChar {
        char caracter;
        NodoChar siguiente;

        public NodoChar(char caracter) {
            this.caracter = caracter;
            this.siguiente = null;
        }
    }

    /**
     * Pila enlazada dinámica para caracteres con operaciones en O(1).
     */
    public static class PilaCaracteres {
        private NodoChar cima;
        private int tamano;

        public PilaCaracteres() {
            this.cima = null;
            this.tamano = 0;
        }

        /**
         * Inserta un carácter en la cima de la pila en tiempo O(1).
         */
        public void push(char c) {
            NodoChar nuevo = new NodoChar(c);
            nuevo.siguiente = cima;
            cima = nuevo;
            tamano++;
        }

        /**
         * Retira y devuelve el carácter en la cima de la pila en tiempo O(1).
         *
         * @throws NoSuchElementException si la pila está vacía.
         */
        public char pop() {
            if (estaVacia()) {
                throw new NoSuchElementException("La pila de caracteres está vacía.");
            }
            char c = cima.caracter;
            cima = cima.siguiente;
            tamano--;
            return c;
        }

        /**
         * Retorna true si la pila no tiene elementos.
         */
        public boolean estaVacia() {
            return cima == null;
        }

        /**
         * Retorna la cantidad de caracteres en la pila.
         */
        public int getTamano() {
            return tamano;
        }
    }

    /**
     * Invierte una cadena de texto utilizando una pila enlazada (LIFO).
     *
     * Complejidad temporal: O(N) donde N es la longitud del texto.
     * Complejidad espacial: O(N) para alojar los nodos enlazados en memoria dinámica.
     *
     * @param texto Cadena original a invertir.
     * @return Nueva cadena con los caracteres en orden inverso.
     */
    public static String invertirPalabra(String texto) {
        if (texto == null) {
            throw new IllegalArgumentException("El texto no puede ser nulo.");
        }
        if (texto.isEmpty() || texto.length() == 1) {
            return texto;
        }

        PilaCaracteres pila = new PilaCaracteres();

        // Paso 1: Apilar cada carácter del texto de izquierda a derecha (O(N))
        for (int i = 0; i < texto.length(); i++) {
            pila.push(texto.charAt(i));
        }

        // Paso 2: Desapilar para reconstruir la cadena invertida (O(N))
        StringBuilder invertida = new StringBuilder(texto.length());
        while (!pila.estaVacia()) {
            invertida.append(pila.pop());
        }

        return invertida.toString();
    }

    /**
     * Verifica si una palabra o frase es palíndroma utilizando la técnica de inversión con pila.
     * Se normaliza la cadena omitiendo espacios, signos y distinguiendo mayúsculas de minúsculas.
     *
     * @param texto Cadena a evaluar.
     * @return true si el texto normalizado se lee igual al derecho y al revés; false en caso contrario.
     */
    public static boolean esPalindromo(String texto) {
        if (texto == null) {
            return false;
        }

        // Normalizar: conservar solo letras y dígitos en minúsculas
        StringBuilder normalizado = new StringBuilder();
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                normalizado.append(Character.toLowerCase(c));
            }
        }

        String textoLimpio = normalizado.toString();
        if (textoLimpio.isEmpty()) {
            return true;
        }

        String textoInvertido = invertirPalabra(textoLimpio);
        return textoLimpio.equals(textoInvertido);
    }

    /**
     * Demostración y validación de las operaciones de inversión y detección de palíndromos.
     */
    public static void main(String[] args) {
        System.out.println("====================================================================");
        System.out.println("   DEMOSTRACIÓN: INVERSIÓN DE PALABRAS CON PILA ENLAZADA (LIFO)    ");
        System.out.println("====================================================================\n");

        // 1. Inversión de palabras y cadenas
        System.out.println("--- 1. Pruebas de Inversión de Texto ---");
        String[] pruebasInversion = {
            "algoritmo",
            "estructura",
            "Java",
            "OpenCode",
            "A",
            "",
            "Estructura de Datos 2026",
            "123456789"
        };

        for (String palabra : pruebasInversion) {
            String invertida = invertirPalabra(palabra);
            System.out.printf("Original : \"%s\"\n", palabra);
            System.out.printf("Invertida: \"%s\"\n\n", invertida);
        }

        // 2. Verificación de Palíndromos
        System.out.println("--- 2. Pruebas de Detección de Palíndromos ---");
        String[] pruebasPalindromo = {
            "reconocer",
            "radar",
            "neuquen",
            "oso",
            "somos",
            "Anita lava la tina",
            "A ti no, bonita",
            "Dabale arroz a la zorra el abad",
            "algoritmo",
            "computadora"
        };

        for (String frase : pruebasPalindromo) {
            boolean resultado = esPalindromo(frase);
            System.out.printf("Texto : \"%-35s\" -> ¿Es palíndromo? %s\n",
                    frase, resultado ? "SÍ (Palíndromo)" : "NO");
        }

        // 3. Prueba de caso límite con nulo
        System.out.println("\n--- 3. Prueba de Caso Límite (Entrada nula) ---");
        try {
            invertirPalabra(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Excepción capturada correctamente al pasar texto nulo: " + e.getMessage());
        }

        System.out.println("\n====================================================================");
        System.out.println("   FIN DE LA DEMOSTRACIÓN DEL EJERCICIO 8                           ");
        System.out.println("====================================================================");
    }
}
