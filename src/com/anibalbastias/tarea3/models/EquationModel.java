package com.anibalbastias.tarea3.models;

/**
 * <h1>EquationModel - Clase modelo de la ecuacion</h1>
 * <p>
 *      Clase modelo de la ecuacion que contiene la funcion, polinomio y valor del polinomio
 *      con valor asignado a x.
 * </p>
 *
 * @author  Anibal Bastias Soto
 * @version 1.0
 * @since   2019-07-13
 */

public class EquationModel {

    private String function;
    private String polynomial;
    private double value;

    public EquationModel(String function, String polynomial) {
        this.function = function;
        this.polynomial = polynomial;
    }

    public String getFunction() {
        return function;
    }

    public String getPolynomial() {
        return polynomial;
    }

    public double getValue() {
        return value;
    }

    public void setPolynomial(String polynomial) {
        this.polynomial = polynomial;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Equation: " + function + " = " + polynomial;
    }
}
