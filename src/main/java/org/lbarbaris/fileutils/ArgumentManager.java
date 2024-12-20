package org.lbarbaris.fileutils;

import java.io.IOException;
import java.util.ArrayList;

public class ArgumentManager {
    boolean appendData;
    String outputPath;
    ArrayList<String> inputPaths;
    byte typeOfStatistics;
    public ArgumentManager(String [] args) throws Exception {
        typeOfStatistics = 0;
        inputPaths = new ArrayList<>();
        outputPath = "";
        appendData = false;

        if (args.length == 0){
            throw new Exception("При запуске программы не найден источник данных и иные аргументы. Пожалуйста, попробуйте снова");
        }
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            switch (arg) {
                case "-a" -> appendData = true;
                case "-p" -> {
                    if (i + 1 < args.length) {
                        outputPath += args[i + 1];
                        i++;
                    } else {
                        System.out.println("Параметр после -p не указан. Аргумент -p проигнорирован");
                    }
                }
                case "-o" -> {
                    if (i + 1 < args.length) {
                        outputPath = args[i + 1] + outputPath;
                        i++;
                    } else {
                        System.out.println("Параметр после -o не указан. Аргумент -o проигнорирован");
                    }
                }
                case "-s" -> {
                    if (typeOfStatistics == 2) {
                    System.out.println("До этого вы уже ввели сбор полной статистики. Тип статистики изменён на краткую");
                    }
                    typeOfStatistics = 1;
                }
                case "-f" -> {
                    if (typeOfStatistics == 1) {
                        System.out.println("До этого вы уже ввели сбор краткой статистики. Тип статистики изменён на полную");
                    }
                    typeOfStatistics = 2;
                }
                default -> inputPaths.add(arg);
            }
        }
        if (inputPaths.isEmpty()){
            throw new Exception("При запуске программы не найден источник данных и иные аргументы. Пожалуйста, попробуйте снова");
        }
    }
    public void run() throws IOException {
        new FileManager(inputPaths, outputPath, appendData, typeOfStatistics).read();
    }
}
