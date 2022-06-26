package com.iman.jsonxml;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {
    public static String convertInputStreamToString(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            while (bis.available() != 0) {
                sb.append((char) bis.read());
            }
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();


    }

}
