package com.example.generator;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    private static OutputStreamWriter charOutput;


    public static void main(String[] args) throws IOException {
        Settings tableSettings = Settings.create(args[0]);
        int pageWidth = tableSettings.getPageWidth();

        //Read TSV and add settings list
        List<Entry> entries = Entry.readTsv(args[1]);

        File file = new File(args[2]);
        //Create file and stream writer in the source folder
        charOutput = new OutputStreamWriter(new FileOutputStream(args[2]), Charset.forName("UTF-16"));

        int currentHeight = 3;
        printHead(tableSettings, pageWidth);

        for (Entry entry : entries) {
            //Get file entry and correcting strings into that odd format
            List<String> indexes = correctSplit(tableSettings.getColumnWidth(0), entry.getNumber());
            List<String> dates = correctSplit(tableSettings.getColumnWidth(1), entry.getDate());
            List<String> thatGuyNames = correctSplit(tableSettings.getColumnWidth(2), entry.getName());

            if (currentHeight + Math.max(dates.size(), thatGuyNames.size()) > tableSettings.getPageHeight()) {
                printNewPage(tableSettings, pageWidth);
                currentHeight = 3;
            }
            for (int i = 0; i < Math.max(dates.size(), thatGuyNames.size()); i++) {
                fillEnoughSpace(i, indexes, dates, thatGuyNames);

                charOutput.write(writeEntry(tableSettings, indexes.get(i), dates.get(i), thatGuyNames.get(i)));

            }
            currentHeight += Math.max(dates.size(), thatGuyNames.size());
            charOutput.write(StringUtils.repeat("-", pageWidth) + System.lineSeparator());
            currentHeight++;
        }


        charOutput.close();
    }


    private static void printHead(Settings tableSettings, int pageWidth) throws IOException {
        charOutput.write(writeHead(tableSettings));
        charOutput.write(StringUtils.repeat("-", pageWidth) + System.lineSeparator());
    }

    private static void printNewPage(Settings tableSettings, int pageWidth) throws IOException {
        charOutput.write("~\n");
        charOutput.write(writeHead(tableSettings));
        charOutput.write(StringUtils.repeat("-", pageWidth) + System.lineSeparator());
    }

    private static void fillEnoughSpace(int index, List<String> indexes, List<String> dates, List<String> names) {
        if (index >= indexes.size()) {
            indexes.add("");
        }
        if (index >= dates.size()) {
            dates.add("");
        }
        if (index >= names.size()) {
            names.add("");
        }
    }


    private static String writeHead(Settings settings) {
        return writeHead(settings.getColumnWidth(0), settings.getColumnWidth(1), settings.getColumnWidth(2));
    }

    private static String writeHead(int numberWidth, int dateWidth, int nameWidth) {
        return ("| " + String.format("%-" + numberWidth + "s", "Номер") + " | " + String.format("%-" + dateWidth + "s", "Дата") + " | " + String.format("%-" + nameWidth + "s", "Фио") + " |\n");
    }


    private static String writeEntry(Settings settings, String number, String date, String name) {
        return writeEntry(settings.getColumnWidth(0), settings.getColumnWidth(1), settings.getColumnWidth(2), number, date, name);
    }


    private static String writeEntry(int numberWidth, int dateWidth, int nameWidth, String number, String date, String name) {
        return ("| " + String.format("%-" + numberWidth + "s", number) + " | " + String.format("%-" + dateWidth + "s", date) + " | " + String.format("%-" + nameWidth + "s", name) + " |\n");
    }


    private static ArrayList<String> correctSplit(int width, String s) {
        if (s.length() <= width) {
            return new ArrayList(Collections.singleton(s));
        } //return string that not requires splitting

        String[] sArray = s.split(" ");
        ArrayList<String> finalList = new ArrayList();


        for (String aSArray : sArray) {
            if (aSArray.length() <= width) {
                finalList.add(aSArray);
            } else {
                String[] newArray = aSArray.split("(?=[^A-Za-zа-яА-Я0-9'])");
                for (int j = 0; j < newArray.length; j++) {
                    if (newArray[j].length() <= width) {
                        finalList.add(newArray[j]);
                    } else {
                        while (newArray[j].length() > width) {
                            finalList.add(newArray[j].substring(0, width));
                            newArray[j] = newArray[j].substring(width, newArray[j].length());
                        }
                        finalList.add(newArray[j]);
                    }
                }
            }
        }

        for (int i = 0; i < finalList.size() - 1; i++) {
            if (finalList.get(i).length() + finalList.get(i + 1).length() <= width) {
                finalList.set(i, finalList.get(i) + finalList.get(i + 1));
                finalList.remove(i + 1);
            }
        }

        return finalList;
    }
}



