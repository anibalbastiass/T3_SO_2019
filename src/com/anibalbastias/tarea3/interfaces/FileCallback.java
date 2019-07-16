package com.anibalbastias.tarea3.interfaces;


import com.anibalbastias.tarea3.models.QuestionType;

/**
 * <h1>FileCallback - Interfaz de Archivos</h1>
 * <p>
 *      Interfaz que contiene las funciones para leer archivo por cada iteracion
 *      y leer linea por archivo.
 * </p>
 *
 * @author  Anibal Bastias Soto
 * @version 1.0
 * @since   2019-07-13
 */

public interface FileCallback {
    void onReadLineFile(QuestionType questionType, String line, int count);
    void onReadFile(String fileName);
}
