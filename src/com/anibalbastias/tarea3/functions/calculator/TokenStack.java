package com.anibalbastias.tarea3.functions.calculator;

import java.util.ArrayList;

/**
 * <h1>TokenStack - Clase de pila de simbolos</h1>
 * <p>
 * Define el arreglo o pila de simbolos
 * </p>
 *
 * @author Anibal Bastias Soto
 * @version 1.0
 * @since 2019-07-13
 */

public class TokenStack {

    private ArrayList<Token> tokens;

    /**
     * Constructor
     */
    TokenStack() {
        tokens = new ArrayList<>();
    }

    /**
     * Metodo de acceso
     */
    public boolean isEmpty() {
        return tokens.size() == 0;
    }

    public Token top() {
        return tokens.get(tokens.size() - 1);
    }

    /**
     * Metodo mutador
     */
    public void push(Token t) {
        tokens.add(t);
    }

    public void pop() {
        tokens.remove(tokens.size() - 1);
    }
}