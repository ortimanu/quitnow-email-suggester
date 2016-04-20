package com.fewlaps.quitnowemailsuggester.repository;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Repository {

    private final String SUFFIX_FILE_LOCATION = "/list/public_suffix_list.dat";
    private final String DISPOSABLES_FILE_LOCATION = "/disposables/index.json";

    public List<String> getTlds() {
        try {
            return getFileLines(SUFFIX_FILE_LOCATION);
        } catch (IOException e) {
            return null;
        }
    }

    public String[] getDisposables() {
        try {
            StringBuilder sb = new StringBuilder();
            List<String> lines = getFileLines(DISPOSABLES_FILE_LOCATION);
            for (String line : lines) {
                sb.append(line);
            }

            Gson gson = new Gson();
            return gson.fromJson(sb.toString(), String[].class);
        } catch (IOException e) {
            return null;
        }
    }

    private List<String> getFileLines(String fileLocation) throws IOException {
        List<String> lines = new ArrayList();

        InputStream inputStream = null;
        Scanner scanner = null;
        try {
            inputStream = getClass().getResourceAsStream(fileLocation);
            scanner = new Scanner(inputStream, "UTF-8");
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (scanner != null) {
                scanner.close();
            }
        }
        return lines;
    }
}