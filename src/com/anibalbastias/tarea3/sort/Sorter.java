package com.anibalbastias.tarea3.sort;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * <h1>Sorter - Clase donde se llama el Sorter que extiende de un Runnable</h1>
 * <p>
 *      Esta clase define un metodo QuickSort que ordena un arreglo de objetos comparables usando
 *      mutiples hebras para paralelizar el ordenamiento
 * </p>
 *
 * @author  Anibal Bastias Soto
 * @version 1.0
 * @since   2019-07-13
 */

public class Sorter {

    /**
     * Metodo usado para el ordenamiento. La entrada es ordenada usando multiples hebras
     *
     * @param input Arreglo a ordenar
     * @param <T>   El tipo de objeto siendo ordenado, debe extender de Comparable
     */
    public static <T extends Comparable<T>> void quickSort(T[] input) {
        final AtomicInteger count = new AtomicInteger(1);
        QuickSortRunnable.pool.execute(new QuickSortRunnable<>(input, 0, input.length - 1, count));

        try {
            synchronized (count) {
                count.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}