package org.lbarbaris.fileutils;

import java.io.*;
import java.util.ArrayList;

public class FileManager {
    ArrayList<String> inputPaths;
    String outputPath, line;
    boolean appendData;
    BufferedReader bufferedReader;
    BufferedWriter floatWriter, longWriter, stringWriter;
    File outputFile;
    float floatNumber;
    long longNumber;
    short typeOfStatistics;
    long[] counters;
    long[] fullStatisticsLong;
    float[] fullStatisticsFloat;
    int[] fullStatisticsString;


    public FileManager(ArrayList<String> inputPaths, String floatOutputPath, boolean appendData, short typeOfStatistics) {
        this.typeOfStatistics = typeOfStatistics;
        this.inputPaths = inputPaths;
        this.outputPath = floatOutputPath;
        this.appendData = appendData;

        if (typeOfStatistics != 0) counters = new long[3];


        if (typeOfStatistics == 2) {
            fullStatisticsLong = new long[3];
            fullStatisticsLong[0] = -9223372036854775808L;
            fullStatisticsLong[1] = 9223372036854775807L;
            fullStatisticsLong[2] = 0;

            fullStatisticsFloat = new float[3];
            fullStatisticsFloat[0] = -3.4028235E38F;
            fullStatisticsFloat[1] = 3.4028235E38F;
            fullStatisticsFloat[2] = 0;

            fullStatisticsString = new int[2];
            fullStatisticsString[0] = "".length();
            fullStatisticsString[1] = 2147483647;


        }

    }

    public void read() throws IOException {
        for (String inputPath: inputPaths){
            try {
                bufferedReader = new BufferedReader(new FileReader(inputPath));
            }
            catch (Exception e){
                System.out.println("Файл с данными не найден. Попробуйте снова");
                System.exit(0);
            }

            floatWriter = null;
            longWriter = null;
            stringWriter = null;

            while ((line = bufferedReader.readLine()) != null){

                try {
                    longNumber = Long.parseLong(line);

                    if (typeOfStatistics != 0){
                        counters[0]++;
                    }

                    if (typeOfStatistics == 2) {
                        if (longNumber > fullStatisticsLong[0]){
                            fullStatisticsLong[0] = longNumber;
                        }
                        if (longNumber < fullStatisticsLong[1]){
                            fullStatisticsLong[1] = longNumber;
                        }
                        fullStatisticsLong[2] += longNumber;
                    }

                    if (longWriter == null) longWriter = createWriter("integers.txt");
                    longWriter.write(String.valueOf(longNumber));
                    longWriter.newLine();

                } catch (Exception e) {
                    try {
                        floatNumber = Float.parseFloat(line);

                        if (typeOfStatistics != 0){
                            counters[1]++;
                        }

                        if (typeOfStatistics == 2) {
                            if (floatNumber > fullStatisticsFloat[0]){
                                fullStatisticsFloat[0] = floatNumber;
                            }
                            if (floatNumber < fullStatisticsFloat[1]){
                                fullStatisticsFloat[1] = floatNumber;
                            }
                            fullStatisticsFloat[2] += floatNumber;
                        }

                        if (floatWriter == null) floatWriter = createWriter("floats.txt");
                        floatWriter.write(String.valueOf(floatNumber));
                        floatWriter.newLine();
                    }

                    catch (Exception ignored){
                        if (typeOfStatistics != 0){
                            counters[2]++;
                        }
                        if (typeOfStatistics == 2){
                            if (line.length() > fullStatisticsString[0]){
                                fullStatisticsString[0] = line.length();
                            }
                            if (line.length() < fullStatisticsString[1]){
                                fullStatisticsString[1] = line.length();
                            }
                        }
                        if (stringWriter == null) stringWriter = createWriter("strings.txt");
                        stringWriter.write(line);
                        stringWriter.newLine();
                    }
                }
            }
            close(stringWriter);
            close(longWriter);
            close(floatWriter);
        }

        if (typeOfStatistics == 1){
            if (counters[0] != 0) System.out.println("Записано целых чисел:" + counters[0]);
            if (counters[1] != 0) System.out.println("Записано вещественных чисел:" + counters[1]);
            if (counters[2] != 0) System.out.println("Записано строк:" + counters[2]);

        }
        else if (typeOfStatistics == 2){
            if (counters[0] != 0){
                System.out.println("Статистика по целым числам");
                System.out.println("Записано чисел:" + counters[0]);
                System.out.println("Самое большое:" + fullStatisticsLong[0]);
                System.out.println("Самое маленькое:" + fullStatisticsLong[1]);
                System.out.println("Сумма:" + fullStatisticsLong[2]);
                System.out.println("Среднее:" + fullStatisticsLong[2] / counters[0] + '\n');
            }
            if (counters[1] != 0){
                System.out.println("Статистика по вещественным числам");
                System.out.println("Записано чисел:" + counters[1]);
                System.out.println("Самое большое:" + fullStatisticsFloat[0]);
                System.out.println("Самое маленькое:" + fullStatisticsFloat[1]);
                System.out.println("Сумма:" + fullStatisticsFloat[2]);
                System.out.println("Среднее:" + fullStatisticsFloat[2] / counters[1]  + '\n');
            }
            if (counters[2] != 0){
                System.out.println("Статистика по строкам");
                System.out.println("Записано cтрок:" + counters[2]);
                System.out.println("Размер самой большой:" + fullStatisticsString[0]);
                System.out.println("Размер самой маленькой:" + fullStatisticsString[1]);
            }




        }


    }

    private BufferedWriter createWriter(String fileType) throws IOException {
            outputFile = new File(outputPath);
            File outputFile2 = new File(outputPath + fileType);

            if (outputFile.getParentFile() != null && !outputFile.getParentFile().exists()){
                outputFile.getParentFile().mkdirs();
            }
            return new BufferedWriter(new FileWriter(outputFile2, appendData));
    }

    private void close(BufferedWriter bw) throws IOException {
        if (bw != null) bw.close();
    }
}
