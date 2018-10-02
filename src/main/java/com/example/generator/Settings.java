package com.example.generator;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Settings {


    public static Settings create(String path) throws IOException {
        String line;
        File xmlFile = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(xmlFile));
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line.trim());
        }
        String xmlFileAsString = sb.toString();
        XmlMapper objectMapper = new XmlMapper();
        return objectMapper.readValue(xmlFileAsString, Settings.class);
    }

    public int getPageWidth() {
        return this.page.width;
    }

    public int getPageHeight() {
        return this.page.height;
    }

    public int getColumnWidth(int index) {
        return this.columns.get(index).width;
    }

    public Page page;
    public List<Column> columns;

    public static class Page {
        private int width;

        private int height;

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

    }

    public static class Column {
        public String title;
        public int width;
    }


}