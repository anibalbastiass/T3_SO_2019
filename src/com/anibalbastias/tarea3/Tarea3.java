package com.anibalbastias.tarea3;

import com.anibalbastias.tarea3.models.QuestionType;
import com.anibalbastias.tarea3.functions.Equation;
import com.anibalbastias.tarea3.interfaces.FileCallback;
import com.anibalbastias.tarea3.sort.Sorter;
import com.anibalbastias.tarea3.utils.FileUtils;
import com.anibalbastias.tarea3.utils.Utils;

import java.util.Arrays;
import java.util.Scanner;

import static com.anibalbastias.tarea3.utils.FileUtils.getCurrentDirectory;

/**
 * <h1>Tarea3 - Clase Main para Ejecuction</h1>
 * <p>
 *      Clase donde muestra el menu para seleccionar el analisis de las 2 preguntas solicitadas
 *      en esta tarea: Sistema de Ecuaciones lineales, Ordenamiento; ejecutando algoritmos
 *      con uso de multi hebras. Estas funciones utilizan archivos de entrada (recorren recursivamente)
 *      la carpeta mencionada y procede a la lectura de los archivos, cada linea leida en una hebra
 *      segun sea el caso
 * </p>
 *
 * @author  Anibal Bastias Soto
 * @version 1.0
 * @since   2019-07-13
 */

public class Tarea3 {

    private static int valueFunction;

    /**
     * Clase Main para ejecucion de la clase
     *
     * @param args Argumentos de entrada
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int option;

        // Menu de bienvenida
        while (true) {
            Utils.print("\n====================================================\n");
            Utils.print("Tarea 3 - Sistemas Operativos\n\n");
            Utils.print("Selecciona la opcion que deseas evaluar:\n");
            Utils.print("(1): Opcion 1 - Funciones\n(2): Opcion 2 - Ordenamiento\n(3): Salir");
            Utils.print("\n====================================================\n");

            try {
                option = Integer.parseInt(scanner.nextLine());

                if (option == 3) {
                    System.exit(0);
                }

                if (option >= 1 && option <= 2) {
                    // Lee el directorio in/question1 o in/question2 para funciones u ordenamiento
                    String questionPath = getCurrentDirectory() + "/in/question" + option;

                    switch (option) {
                        case 1: {
                            // Limpia la lista analizada para nuevos casos
                            if (Equation.equations != null) Equation.equations.clear();

                            // Pide un valor de x para analisis de funciones
                            Utils.print("Funciones ingresadas!\n");
                            Utils.print("Ingrese valor para x:\n");

                            valueFunction = Integer.parseInt(scanner.nextLine());
                            parseFiles(questionPath, QuestionType.QUESTION_1);
                        }
                        break;
                        case 2:
                            parseFiles(questionPath, QuestionType.QUESTION_2);
                            break;
                    }
                }
            } catch (NumberFormatException nfe) {
                // Continua en el menu
            }
        }
    }

    /**
     * Funcion que recorre archivos en un directorio mediante la implementacion de la interfaz
     * que decide que hacer con cada archivo recorrido
     *
     * @param questionPath Ruta del directorio in/question{x} x=1,2
     * @param chosenQuestion Enum de la pregunta seleccionada: QUESTION_1, QUESTION_2
     */
    private static void parseFiles(String questionPath, QuestionType chosenQuestion) {
        FileUtils.listFilesForFolder(questionPath, new FileCallback() {

            @Override
            public void onReadFile(String fileName) {
                FileUtils.readFile(chosenQuestion, questionPath + "/" + fileName, new FileCallback() {
                    @Override
                    public void onReadLineFile(QuestionType questionType, String line, int count) {
                        onReadLineFileMain(chosenQuestion, line, count);
                    }

                    @Override
                    public void onReadFile(String fileName) { /* nothing to do */ }
                });
            }

            @Override
            public void onReadLineFile(QuestionType questionType, String line, int count) { /* nothing to do */ }
        });
    }

    /**
     * Funcion que lee cada linea del archivo obtenido, mediante la interfaz decide que accion hacer en cada
     * linea recorrida
     *
     * @param questionType Enum de la pregunta seleccionada: QUESTION_1, QUESTION_2
     * @param line String obtenido de la linea de archivo
     * @param count Contador de la linea recorrida del archivo
     */
    private static void onReadLineFileMain(QuestionType questionType, String line, int count) {
        switch (questionType) {

            // Pregunta de Sistema de funciones
            case QUESTION_1: {

                // Guarda la primera linea donde indica cuantas funciones contiene el archivo
                if (count == 0) {
                    Equation.countFunctions = Integer.parseInt(line);
                } else {
                    // Guarda las funciones de cada linea en un arreglo
                    Equation.equations.add(line);

                    // Si llega la ultima linea, procede a recorrer inversamente las funciones y calcula
                    if (count == Equation.countFunctions) {
                        Equation.assignValueFunction(valueFunction);
                    }
                }
            }
            break;

            // Pregunta de Ordenamiento
            case QUESTION_2: {

                // Define algunos valores para ordenar
                Integer[] originalValues = Utils.convertLineToIntArray(line);
                Integer[] values = Utils.convertLineToIntArray(line);

                // Ejecuta el Quick Sort para multi hebras
                Sorter.quickSort(values);

                // Imprime los valores originales y ordenados
                Utils.print("Original: " + Arrays.toString(originalValues) + "\nOrdenado: " + Arrays.toString(values) + "\n");
            }
            break;
        }
    }
}
