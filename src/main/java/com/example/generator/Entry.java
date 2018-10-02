package com.example.generator;

import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Entry {
    private String number;
    private String date;
    private String name;

    private Entry(String number, String date, String name) {
        this.number = number;
        this.date = date;
        this.name = name;
    }

    public static List<Entry> readTsv(String filename) {
        ArrayList<Entry> data = new ArrayList();
        //Some arraylist for class fields mapping
        TsvParserSettings tsvSettings = new TsvParserSettings();
        tsvSettings.getFormat().setLineSeparator("\n");
        //create tsv parser
        TsvParser parser = new TsvParser(tsvSettings);
        List<String[]> allRows = parser.parseAll(new File(filename));

        for (String[] allRow : allRows) {
            data.add(new Entry(allRow[0], allRow[1], allRow[2]));
        }
        return data;
    }


    public String getNumber() {
        return number;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

}

