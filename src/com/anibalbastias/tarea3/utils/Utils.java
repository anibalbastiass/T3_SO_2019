package com.anibalbastias.tarea3.utils;

/**
 * <h1>Utils - Clase Util para metodos comunes</h1>
 * <p>
 *      Clase donde contiene metodos utiles de toda la aplicacion
 * </p>
 *
 * @author  Anibal Bastias Soto
 * @version 1.0
 * @since   2019-07-13
 */

public class Utils {

    /**
     * Clase que convierte la linea String en un arreglo de enteros.
     * Este caso se utiliza para el ordenamiento
     *
     * @param line
     * @return
     */
    public static Integer[] convertLineToIntArray(String line) {

        // Arreglo diferencia si hay espacios
        String[] integerStrings = line.split(" ");

        // Realiza un split por cada espacio
        Integer[] integers = new Integer[integerStrings.length];

        for (int i = 0; i < integers.length; i++){
            integers[i] = Integer.parseInt(integerStrings[i]);
        }
        return integers;
    }

    /**
     * Imprime por pantalla textos
     * @param text Texto a mostrar en pantalla
     */
    public static void print(String text) {
        System.out.println(text);
    }
}
