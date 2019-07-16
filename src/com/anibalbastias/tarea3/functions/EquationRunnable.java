package com.anibalbastias.tarea3.functions;

import com.anibalbastias.tarea3.functions.calculator.Calculator;
import com.anibalbastias.tarea3.models.EquationModel;
import com.anibalbastias.tarea3.utils.Utils;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h1>EquationRunnable - Clase donde se ejecuta cada hebra para realizar el calculo de valores</h1>
 * <p>
 *      Transforma el polinomio en expresion y asigna valor en cad ahebra.
 *      El metodo usado no es tecnicamente recursivo ya que este crea un nueva hebra
 *      y maneja a traves de un ThreadPoolExecutor
 * </p>
 *
 * @author Anibal Bastias Soto
 * @version 1.0
 * @since 2019-07-13
 */

public class EquationRunnable implements Runnable {

    /**
     * Numero de hebras para cada funcion
     */
    private static final int N_THREADS = Runtime.getRuntime().availableProcessors();

    /**
     * Mutiplo a usar cuando deermina el retroceso de iteracion
     */
    private static final int FALLBACK = 2;

    /**
     * Grupo de hilos usados para la ejecucion de calculo de funciones mediante Runnables
     */
    public static Executor pool = Executors.newFixedThreadPool(N_THREADS);

    /**
     * El numero de hebras que actualmente ejecutando. Notifica mediante notify cuando cambia de valor,
     * razon que es un AtomicInteger (Similar a un ObservableInt)
     */
    private final AtomicInteger count;

    /**
     * Arreglo de funciones para analizar
     */
    private final ArrayList<EquationModel> equations;

    /**
     * Valor inicial pasado por Scanner del usuario
     */
    private static int initialValue;

    /**
     * Constructor que recibe la hebra, valor inicial y el arreglo de funciones
     *
     * @param count Hebra contador de la funcion
     * @param valueFunction Valor inicial pasado por el usuario, valor de x. Ej: x=2
     * @param equations lista de ecuaciones
     */
    EquationRunnable(AtomicInteger count, int valueFunction, ArrayList<EquationModel> equations) {
        initialValue = valueFunction;
        this.count = count;
        this.equations = equations;
    }

    /**
     * Constructor para cada hebra iterada, recibe el contador hebra y el arreglo de funciones
     * @param count Hebra contador de la funcion
     * @param equations lista de ecuaciones
     */
    EquationRunnable(AtomicInteger count, ArrayList<EquationModel> equations) {
        this.count = count;
        this.equations = equations;
    }

    /**
     * La logica de ejecucion de la hebra. Cuando esta hebra termina de hacer sus cosas, comprueba si todos las demas
     * hebras tambien lo estan. Si es así, notificamos el objeto de recuento para que solveEquation deje de bloquear.
     */
    @Override
    public void run() {
        if (count.get() == 0)
            solveEquation(equations.get(equations.size() - 1));
        else
            solveEquation(equations.get(equations.size() - (count.get())));

        synchronized (count) {
            // AtomicInteger.getAndDecrement() retorna el valor antiguo.
            // Si el valor antiguo es 1, podemos saber que el actual valor es 0
            if (count.getAndDecrement() == 1)
                count.notify();
        }
    }

    /**
     * Resuelve la ecuacion segun la posicion del contador y hebra
     * @param selectedEquation Ecuacion seleccionada en la hebra
     */
    private void solveEquation(EquationModel selectedEquation) {
        if (count.get() > 0) {
            int valueFunction = assignValue(selectedEquation);

            // Verifica si el calculo se puede hacer paralelizado, sino supera la cantidad de cores de procesador
            // del computador, los encola
            if (count.get() >= FALLBACK * N_THREADS) {
                solveEquation(equations.get(equations.size() - (count.getAndAdd(1))));
            } else {
                Utils.print(equations.get(equations.size() - (count.get())).getFunction().replace("x", "" + initialValue) + " = " + valueFunction);
                pool.execute(new EquationRunnable(count, equations));
            }
        }
    }

    /**
     * Asigna el valor al polinomio, segun la funcion o el x si es la primera funcion
     * @param selectedEquation ecuacion seleccionada
     * @return Valor del polinomio con valores asignados. Ej: f(2)=2, g(2)=3, x=2
     */
    private int assignValue(EquationModel selectedEquation) {

        // Recorre la lista de ecuaciones
        for (EquationModel equation : equations) {

            // Verifica si el polinomio seleccionado contiene la funcion iterada
            if (selectedEquation.getPolynomial().contains(equation.getFunction())) {

                // Reemplaza la funcion con valor asignado al polinomio que la contiene en otra funcion con ese valor
                selectedEquation.setPolynomial(selectedEquation.getPolynomial().replace(equation.getFunction(), "" + equation.getValue()));
            } else {

                // Si el polinomio no contiene funciones en su expresion, reemplaza el valor de x por el valor inicial
                selectedEquation.setPolynomial(selectedEquation.getPolynomial().replace("x", "" + initialValue));
                break;
            }
        }

        // Clase calculadora hace la operacion aritmetica con los valores reemplazados
        // Ej: 2 * 2 +1 = 5
        Calculator calc = new Calculator();
        selectedEquation.setValue(calc.processInput(selectedEquation.getPolynomial()));

        return (int) selectedEquation.getValue();
    }
}
