package com.anibalbastias.tarea3.functions.calculator;

/**
 * <h1>Token - Clase Modelo de simbolo</h1>
 * <p>
 *      El simbolo puede ser un numero, operador o parentesis y verfica precendencia
 * </p>
 *
 * @author  Anibal Bastias Soto
 * @version 1.0
 * @since   2019-07-13
 */

public class Token {

    private static final int UNKNOWN = -1;
    public static final int NUMBER = 0;
    public static final int OPERATOR = 1;
    public static final int LEFT_PARENTHESIS = 2;
    public static final int RIGHT_PARENTHESIS = 3;

    private int type;
    private double value;
    private char operator;
    private int precedence;

    /**
     * Constructor que recibe el contenido
     * @param contents Contenido de simbolo
     */
    Token(String contents) {
        switch (contents) {
            case "+":
                type = OPERATOR;
                operator = contents.charAt(0);
                precedence = 1;
                break;
            case "-":
                type = OPERATOR;
                operator = contents.charAt(0);
                precedence = 1;
                break;
            case "*":
                type = OPERATOR;
                operator = contents.charAt(0);
                precedence = 2;
                break;
            case "/":
                type = OPERATOR;
                operator = contents.charAt(0);
                precedence = 2;
                break;
            case "(":
                type = LEFT_PARENTHESIS;
                break;
            case ")":
                type = RIGHT_PARENTHESIS;
                break;
            default:
                type = NUMBER;
                try {
                    value = Double.parseDouble(contents);
                } catch (Exception ex) {
                    type = UNKNOWN;
                }
        }
    }

    /**
     * Constructor que recibe un numero
     * @param x numero de expresion
     */
    private Token(double x) {
        type = NUMBER;
        value = x;
    }

    /**
     * Obtiene tipo de simbolo
     * @return tipo de simbolo
     */
    int getType() {
        return type;
    }

    /**
     * Obtiene valor de simbolo
     * @return Valor de simbolo
     */
    double getValue() {
        return value;
    }

    /**
     * Obtiene Valor de precendencia
     * @return valor de precendencia
     */
    int getPrecedence() {
        return precedence;
    }

    /**
     * Operacion aritmetica de simbolos
     * @param a operando 1
     * @param b operando 2
     * @return resultado aritmetico de operandos
     */
    Token operate(double a, double b) {
        double result = 0;
        switch (operator) {
            case '+':
                result = a + b;
                break;
            case '-':
                result = a - b;
                break;
            case '*':
                result = a * b;
                break;
            case '/':
                result = a / b;
                break;
        }
        return new Token(result);
    }
}