package com.prm391.techstore.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public class StorageUtils {
    public static <T> T GetFromStorage(String filename, T result, Type typeToken, Context context) {
        String contents = null;
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(filename);
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();
                }
            } catch (IOException e) {
                // Error occurred when opening raw file for reading.
            } finally {
                contents = stringBuilder.toString();
            }

            Gson gson = new Gson();
            if (!contents.isEmpty()) {
                result = gson.fromJson(contents, typeToken);
            }
        } catch (FileNotFoundException e) {

        }

        return result;
    }

    public static <T> void SaveToStorage(String filename, Context context, T content) {
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
            Gson gson = new Gson();
            String json = gson.toJson(content);
            fos.write(json.getBytes());
        } catch (Exception ignored) {

        }
    }
}
