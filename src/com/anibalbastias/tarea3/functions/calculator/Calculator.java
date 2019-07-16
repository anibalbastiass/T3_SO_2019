package com.anibalbastias.tarea3.functions.calculator;

/**
 * <h1>Calculator - Clase analizadora de expresiones</h1>
 * <p>
 *      Esta clase analiza el polinomio si cumple la sintaxis y si contiene los simbolos
 *      (constantes y operadores) y opera en estos.
 * </p>
 *
 * @author  Anibal Bastias Soto
 * @version 1.0
 * @since   2019-07-13
 */

public class Calculator {

    private TokenStack operatorStack;
    private TokenStack valueStack;
    private boolean error;

    /**
     * Constructor por defecto
     */
    public Calculator() {
        operatorStack = new TokenStack();
        valueStack = new TokenStack();
        error = false;
    }

    /**
     * Procesa operacion
     * @param t simbolo a operar
     */
    private void processOperator(Token t) {
        Token A, B;

        if (valueStack.isEmpty()) {
            System.out.println("Expression error.");
            error = true;
            return;
        } else {
            B = valueStack.top();
            valueStack.pop();
        }

        if (valueStack.isEmpty()) {
            System.out.println("Expression error.");
            error = true;
            return;
        } else {
            A = valueStack.top();
            valueStack.pop();
        }

        Token R = t.operate(A.getValue(), B.getValue());
        valueStack.push(R);
    }

    /**
     * Procesa polinomio con valores
     * @param input Polinomio de entrada
     * @return valor calculado de polinomio
     */
    public double processInput(String input) {
        double resultValue = 0;

        // Los simboles que estaran en entrada
        String[] parts = input.split(" ");
        Token[] tokens = new Token[parts.length];

        for (int n = 0; n < parts.length; n++) {
            tokens[n] = new Token(parts[n]);
        }

        // Bucle principal - processa todos los simbolos de entrada
        for (Token nextToken : tokens) {
            if (nextToken.getType() == Token.NUMBER) {
                valueStack.push(nextToken);
            } else if (nextToken.getType() == Token.OPERATOR) {
                if (operatorStack.isEmpty() || nextToken.getPrecedence() > operatorStack.top().getPrecedence()) {
                    operatorStack.push(nextToken);
                } else {
                    while (!operatorStack.isEmpty() && nextToken.getPrecedence() <= operatorStack.top().getPrecedence()) {
                        Token toProcess = operatorStack.top();
                        operatorStack.pop();
                        processOperator(toProcess);
                    }
                    operatorStack.push(nextToken);
                }
            } else if (nextToken.getType() == Token.LEFT_PARENTHESIS) {
                operatorStack.push(nextToken);
            } else if (nextToken.getType() == Token.RIGHT_PARENTHESIS) {
                while (!operatorStack.isEmpty() && operatorStack.top().getType() == Token.OPERATOR) {
                    Token toProcess = operatorStack.top();
                    operatorStack.pop();
                    processOperator(toProcess);
                }
                if (!operatorStack.isEmpty() && operatorStack.top().getType() == Token.LEFT_PARENTHESIS) {
                    operatorStack.pop();
                } else {
                    System.out.println("Error: unbalanced parenthesis.");
                    error = true;
                }
            }
        }

        // Vacia el la pila de operador al final de cada entrada
        while (!operatorStack.isEmpty() && operatorStack.top().getType() == Token.OPERATOR) {
            Token toProcess = operatorStack.top();
            operatorStack.pop();
            processOperator(toProcess);
        }

        // Retunra el resultado
        if (!error) {
            Token result = valueStack.top();
            valueStack.pop();
            if (!operatorStack.isEmpty() || !valueStack.isEmpty()) {
                //System.out.println("Expression error.");
            } else {
                resultValue = result.getValue();
                //System.out.println("The result is " + result.getValue());
            }
        }
        return resultValue;
    }
}