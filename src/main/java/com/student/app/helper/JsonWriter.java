package com.student.app.helper;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonWriter {

    public static Runnable jsonFileWriter(String fileName, Object input){
        return () -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                String path = System.getProperty("user.dir") + "/json/";
                mapper.writeValue(new File(path + fileName + ".json"), input );
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
