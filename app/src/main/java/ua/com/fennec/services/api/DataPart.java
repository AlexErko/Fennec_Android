package ua.com.fennec.services.api;

import android.app.DatePickerDialog;

public class DataPart {
    private String fileName;
    private byte[] content;
    private String type;

    public DataPart(String name, byte[] data) {
        fileName = name;
        content = data;
    }

    String getFileName() {
        return fileName;
    }

    byte[] getContent() {
        return content;
    }

    String getType() {
        return "image/jpeg";
    }

}