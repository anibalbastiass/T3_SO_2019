package com.anibalbastias.tarea3.utils;

import com.anibalbastias.tarea3.models.QuestionType;
import com.anibalbastias.tarea3.interfaces.FileCallback;

import java.io.*;

/**
 * <h1>FileUtils - Clase Util para metodos de manejo de archivos</h1>
 * <p>
 *      Clase donde contiene metodos de manejo de archivos y directorios
 * </p>
 *
 * @author  Anibal Bastias Soto
 * @version 1.0
 * @since   2019-07-13
 */

public class FileUtils {

    /**
     * Obtiene la ruta actual del proyecto de Java
     *
     * @return Ruta actual del proyecto
     */
    public static String getCurrentDirectory() {
        return System.getProperty("user.dir");
    }

    /**
     * Recorre recursivamente un directorio para realizar una accion mediante el callback
     * FileCallback en cada archivo encontrado
     *
     * @param path Ruta de directorio
     * @param callback Interfaz para hacer una accion en cada iteracion
     */
    public static void listFilesForFolder(String path, FileCallback callback) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    callback.onReadFile(file.getName());
                }
            }
        }
    }

    /**
     * Metodo que lee un archivo linea por linea y retorna una interfaz FileCallback para decidir
     * que accion hacer en cada iteracion
     *
     * @param questionType Tipo de pregunta mediante un ENUM: QUESTION_1, QUESTION_2
     * @param fileName Nombre del archivo seleccionado
     * @param callback Interfaz para retonar la implementacion en otra clase
     */
    public static void readFile(QuestionType questionType, String fileName, FileCallback callback) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();

            int count = 0;
            while (line != null) {
                callback.onReadLineFile(questionType, line, count);

                // read next line
                line = reader.readLine();
                count++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


