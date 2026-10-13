/*
Prompt inicial utilizado:
Quiero implementar en Java el método 'int contarOcurrencias(int dato)' dentro de una lista enlazada simple de enteros (en el archivo Ej8.java).

El método debe recorrer toda la lista y devolver la cantidad de veces que aparece un determinado valor.

El prompt debe explicar:
- Por qué no se debe cortar el recorrido al encontrar la primera coincidencia: a diferencia de una búsqueda simple de pertenencia booleana, el cálculo de frecuencia exige conocer el total exacto. Como una lista enlazada no garantiza elementos únicos, pueden existir múltiples nodos con el mismo dato a lo largo de toda la cadena; cortar la ejecución prematuramente provocaría un recuento incompleto (subestimación del total). El recorrido debe continuar obligatoriamente hasta llegar a null.

Comportamiento esperado del método:
1. Inicializar 'int contador = 0' y 'Nodo actual = head'.
2. Iterar mientras actual != null.
3. Si actual.dato coincide con el valor buscado, incrementar contador en 1.
4. Avanzar siempre al siguiente nodo (actual = actual.siguiente).
5. Retornar el contador al finalizar el recorrido.

Casos a probar en main:
- Caso del enunciado: 10 -> 20 -> 10 -> 30 -> 10 -> null.
  * contarOcurrencias(10) -> esperado: 3.
  * contarOcurrencias(20) -> esperado: 1 (elemento único).
  * contarOcurrencias(99) -> esperado: 0 (elemento ausente).
- Caso con lista vacía: contarOcurrencias(10) -> esperado: 0.
- Caso con todos los elementos idénticos: 7 -> 7 -> 7 -> 7 -> null para el valor 7 -> esperado: 4.

Ajustes realizados luego de la primera respuesta de OpenCode:
Ninguno. El bucle exhaustivo 'while (actual != null)' garantizó la evaluación del 100% de los nodos en tiempo lineal O(n), operando de forma limpia tanto en listas vacías como en colecciones con múltiples repeticiones consecutivas o alternadas.
*/

/**
 * Ejercicio 8: Contar ocurrencias de un valor.
 * Demuestra la necesidad de un recorrido exhaustivo e incondicional hasta null
 * para calcular frecuencias exactas en estructuras con elementos duplicados.
 */
public class Ej8 {

    public static class Nodo {
        int dato;
        Nodo siguiente;

        public Nodo(int dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    private Nodo head;
    private int size;

    public Ej8() {
        this.head = null;
        this.size = 0;
    }

    public void insertarAlFinal(int dato) {
        Nodo nuevo = new Nodo(dato);
        if (head == null) {
            head = nuevo;
        } else {
            Nodo actual = head;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
        size++;
    }

    /**
     * Cuenta cuántas veces aparece un determinado valor en la lista.
     * Complejidad: O(n) estricto (visita todos los nodos).
     *
     * @param dato Valor cuya frecuencia se desea contabilizar.
     * @return Cantidad total de apariciones (>= 0).
     */
    public int contarOcurrencias(int dato) {
        int contador = 0;
        Nodo actual = head;

        // Recorrido exhaustivo: NO se aplica early exit (corte anticipado)
        // porque cualquier nodo restante podría contener otra coincidencia del dato.
        while (actual != null) {
            if (actual.dato == dato) {
                contador++;
            }
            actual = actual.siguiente;
        }

        return contador;
    }

    public int getSize() {
        return size;
    }

    public void imprimir() {
        if (head == null) {
            System.out.println("Lista vacía: null");
            return;
        }
        Nodo actual = head;
        while (actual != null) {
            System.out.print(actual.dato + " -> ");
            actual = actual.siguiente;
        }
        System.out.println("null");
    }

    public static void main(String[] args) {
        System.out.println("========================================================================");
        System.out.println("PRUEBA: EJERCICIO 8 - CONTAR OCURRENCIAS DE UN VALOR");
        System.out.println("========================================================================");

        // Caso 1: Ejemplo específico del enunciado
        Ej8 lista1 = new Ej8();
        lista1.insertarAlFinal(10);
        lista1.insertarAlFinal(20);
        lista1.insertarAlFinal(10);
        lista1.insertarAlFinal(30);
        lista1.insertarAlFinal(10);

        System.out.print("Lista del enunciado: ");
        lista1.imprimir();

        System.out.println("\n--- Consultas de frecuencia ---");
        System.out.println("Ocurrencias de 10: " + lista1.contarOcurrencias(10) + " (esperado: 3)");
        System.out.println("Ocurrencias de 20: " + lista1.contarOcurrencias(20) + " (esperado: 1)");
        System.out.println("Ocurrencias de 30: " + lista1.contarOcurrencias(30) + " (esperado: 1)");
        System.out.println("Ocurrencias de 99: " + lista1.contarOcurrencias(99) + " (esperado: 0 - elemento ausente)");

        // Caso 2: Lista vacía
        System.out.println("\n--- Caso con lista vacía ---");
        Ej8 listaVacia = new Ej8();
        System.out.print("Lista vacía: ");
        listaVacia.imprimir();
        System.out.println("Ocurrencias de 10: " + listaVacia.contarOcurrencias(10) + " (esperado: 0)");

        // Caso 3: Todos los elementos repetidos
        System.out.println("\n--- Caso con todos los elementos idénticos ---");
        Ej8 listaRepetida = new Ej8();
        listaRepetida.insertarAlFinal(7);
        listaRepetida.insertarAlFinal(7);
        listaRepetida.insertarAlFinal(7);
        listaRepetida.insertarAlFinal(7);
        System.out.print("Lista: ");
        listaRepetida.imprimir();
        System.out.println("Ocurrencias de 7: " + listaRepetida.contarOcurrencias(7) + " (esperado: 4)");
        System.out.println("========================================================================");
    }
}
