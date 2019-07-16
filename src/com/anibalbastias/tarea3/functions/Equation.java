package com.anibalbastias.tarea3.functions;


import com.anibalbastias.tarea3.models.EquationModel;
import com.anibalbastias.tarea3.utils.Utils;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h1>Equation - Clase de Ecuacion para diferenciar la funcion, polinomio y valor asignado</h1>
 * <p>
 *      Clase que ejecuta el Runnable que resuelve el sistema de ecucaciones
 * </p>
 *
 * @author Anibal Bastias Soto
 * @version 1.0
 * @since 2019-07-13
 */

public class Equation {

    /**
     * Contador de funciones
     */
    public static int countFunctions = 0;

    /**
     * Arreglo de funciones
     */
    public static ArrayList<String> equations = new ArrayList<>();
    private static ArrayList<EquationModel> equationList = new ArrayList<>();

    public static void assignValueFunction(int valueFunction) {

        // Limpia la lista en caso de volver a consultar esta funcion
        equationList.clear();

        for (int i = equations.size() - 1; i >= 0; i--) {
            String equation = equations.get(i);

            // Separa la funcion del polinomio por el simbolo '='
            String key = equation.split("=")[0];
            String value = equation.split("=")[1];

            // Los separa y agrega al modelo Equation como key, value (Similar a un HashMap)
            // No se utilizo HashMap porque se necesita saber el orden
            equationList.add(new EquationModel(key, value));

            //  Imprime las funciones agregadas
            Utils.print(new EquationModel(key, value).toString());
        }

        Utils.print("");

        // Ejecuta el EquationRunnable para recorrer las funciones
        final AtomicInteger count = new AtomicInteger(countFunctions);
        EquationRunnable.pool.execute(new EquationRunnable(count, valueFunction, equationList));

        try {
            synchronized (count) {
                count.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
