package com.fewlaps.quitnowemailsuggester.repository;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Repository {

    private static final String SUFFIX_FILE_LOCATION = "list/public_suffix_list.dat";
    private static final String DISPOSABLES_FILE_LOCATION = "disposables/index.json";

    private static List<String> cachedTlds;
    private static String[] cachedDisposables;

    public List<String> getTlds() {
        if (cachedTlds == null) {
            try {
                List<String> lines = new ArrayList();
                FileInputStream inputStream = null;
                ClassLoader classLoader = getClass().getClassLoader();
                URL url = classLoader.getResource(SUFFIX_FILE_LOCATION);
                File file = new File(url.getFile());
                Scanner sc = null;
                try {
                    inputStream = new FileInputStream(file);
                    sc = new Scanner(inputStream, "UTF-8");
                    while (sc.hasNextLine()) {
                        lines.add(sc.nextLine());
                    }
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (sc != null) {
                        sc.close();
                    }
                }

                cachedTlds = lines;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return cachedTlds;
    }

    public String[] getDisposables() {
        if (cachedDisposables == null) {
            try {
                StringBuilder sb = new StringBuilder();
                FileInputStream inputStream = null;
                ClassLoader classLoader = getClass().getClassLoader();
                URL url = classLoader.getResource(DISPOSABLES_FILE_LOCATION);
                File file = new File(url.getFile());
                Scanner sc = null;
                try {
                    inputStream = new FileInputStream(file);
                    sc = new Scanner(inputStream, "UTF-8");
                    while (sc.hasNextLine()) {
                        sb.append(sc.nextLine());
                    }
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (sc != null) {
                        sc.close();
                    }
                }

                Gson gson = new Gson();
                cachedDisposables = gson.fromJson(sb.toString(), String[].class);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return cachedDisposables;
    }
}
