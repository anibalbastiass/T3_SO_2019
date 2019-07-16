package com.anibalbastias.tarea3.sort;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h1>QuickSortRunnable - Clase donde se ejecuta cada hebra para realizar el ordenamiento</h1>
 * <p>
 *      Ordena una seccion de un arreglo usando Quick Sort. El metodo usado no es tecnicamente recursivo ya que este crea
 *      un nueva hebra y maneja a traves de un ThreadPoolExecutor
 * </p>
 *
 * @param <T> El tipo de objetos siendo ordenados, debe extender de Comparable
 *
 * @author  Anibal Bastias Soto
 * @version 1.0
 * @since   2019-07-13
 */

class QuickSortRunnable<T extends Comparable<T>> implements Runnable {

    /**
     * Numer de hebras a usar para el ordenamiento, segun la cantidad de Cores del procesador
     */
    private static final int N_THREADS = Runtime.getRuntime().availableProcessors();

    /**
     * Mutiplo a usar cuando deermina el retroceso de iteracion
     */
    private static final int FALLBACK = 2;

    /**
     * Grupo de hilos usados para la ejecucion de ordenamiento mediante Runnables
     */
    public static Executor pool = Executors.newFixedThreadPool(N_THREADS);

    /**
     * El arreglo que esta siendo ordenado
     */
    private final T[] values;

    /**
     *
     * El indice inicial de la seccion del arreglo que se ordena
     */
    private final int left;

    /**
     * El indice final de la seccion del arreglo que se ordena
     */
    private final int right;

    /**
     * El numero de hebras que actualmente ejecutando. Notifica mediante notify cuando cambia de valor,
     * razon que es un AtomicInteger (Similar a un ObservableInt)
     */
    private final AtomicInteger count;

    /**
     * Constructor por defecto. Establece el objeto Runnable para la ejecucion
     *
     * @param values Arreglo a ordenar
     * @param left   Indice de inicio de la seccin del arreglo a ordenar
     * @param right  Indice de termino de la seccion  del arreglo a ordenar
     * @param count  El numbero de hebras actualmente en ejecucion
     */
    QuickSortRunnable(T[] values, int left, int right, AtomicInteger count) {
        this.values = values;
        this.left = left;
        this.right = right;
        this.count = count;
    }

    /**
     * La logica de ejecucion de la hebra. Cuando esta hebra termina de hacer sus cosas, comprueba si todos las demas
     * hebras tambien lo estan. Si es así, notificamos el objeto de recuento para que Sorter.quickSort deje de bloquear.
     */
    @Override
    public void run() {
        quickSort(left, right);
        synchronized (count) {
            // AtomicInteger.getAndDecrement() retorna el valor antiguo.
            // Si el valor antiguo es 1, podemos saber que el actual valor es 0
            if (count.getAndDecrement() == 1)
                count.notify();
        }
    }

    /**
     * Método que realmente hace la clasificación. Se recurre a la recursividad si hay un cierto número de tareas
     * en ejecución en cola.
     *
     * @param pLeft  El índice izquierdo del subarreglo para ordenar.
     * @param pRight El índice correcto del suebarreglo para ordenar.
     */
    private void quickSort(int pLeft, int pRight) {
        if (pLeft < pRight) {
            int storeIndex = partition(pLeft, pRight);

            // Verifica si el calculo se puede hacer paralelizado, sino supera la cantidad de cores de procesador
            // del computador, los encola
            if (count.get() >= FALLBACK * N_THREADS) {
                quickSort(pLeft, storeIndex - 1);
                quickSort(storeIndex + 1, pRight);
            } else {
                count.getAndAdd(2);
                pool.execute(new QuickSortRunnable<>(values, pLeft, storeIndex - 1, count));
                pool.execute(new QuickSortRunnable<>(values, storeIndex + 1, pRight, count));
            }
        }
    }

    /**
     * Particiona la parte del arreglo entre los índices a la izquierda y a la derecha, inclusive,
     * moviendo todos los elementos menos que los valores [pivotIndex] antes del pivote,
     * y los elementos iguales o mayores después de este.
     *
     * @param pLeft  El índice izquierdo del subarreglo para ordenar.
     * @param pRight El índice correcto del suebarreglo para ordenar.
     * @return El indice final del valor del pivote
     */
    private int partition(int pLeft, int pRight) {
        T pivotValue = values[pRight];
        int storeIndex = pLeft;
        for (int i = pLeft; i < pRight; i++) {
            if (values[i].compareTo(pivotValue) < 0) {
                swap(i, storeIndex);
                storeIndex++;
            }
        }
        swap(storeIndex, pRight);
        return storeIndex;
    }

    /**
     * Methodo de un intercambio simple
     *
     * @param left  El índice del primer valor para intercambiar con el segundo valor.
     * @param right El índice del segundo valor a intercambiar con el primer valor.
     */
    private void swap(int left, int right) {
        T temp = values[left];
        values[left] = values[right];
        values[right] = temp;
    }
}