package com.taraan.dum.common;

import org.apache.tika.Tika;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class FileTypesUtils {
    private FileTypesUtils() {
    }

    private static Tika tika = new Tika();
    private static Map<String, String> maps = new HashMap();

    static {
        maps.put("image/jpeg", "jpg");
        maps.put("image/png", "png");
        maps.put("application/pdf", "pdf");
        maps.put("text/plain", "txt");
        maps.put("audio/mpeg", "mp3");
        maps.put("video/mp4", "mp4");
        maps.put("video/x-sgi-movie", "movie");
        maps.put("video/x-msvideo", "avi");
        maps.put("video/avi", "avi");
        maps.put("video/msvideo", "avi");
        maps.put("video/x-matroska", "mkv");
        maps.put("application/vnd.ms-powerpoint", "ppt");
        maps.put("application/vnd.openxmlformats-officedocument.presentationml.presentation", "pptx");
        maps.put("application/msword", "doc");
        maps.put("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx");
    }

    public static String getTypeByMimeType(String key) {
        String extension = maps.get(key);
        if (extension == null)
            throw new InvalidFileTypeException();
        return extension;
    }

    public static String checkType(byte[] file) {
        String mimeType = tika.detect(file);
        return getTypeByMimeType(mimeType);
    }

    public static String checkType(File file) {

        String mimeType = null;
        try {
            mimeType = tika.detect(file);
        } catch (IOException e) {
            throw new InvalidFileTypeException(e);
        }
        return getTypeByMimeType(mimeType);
    }

    public static String checkType(InputStream file) {
        String mimeType = null;
        try {
            mimeType = tika.detect(file);
        } catch (IOException e) {
            throw new InvalidFileTypeException(e);
        }
        return getTypeByMimeType(mimeType);
    }


}
