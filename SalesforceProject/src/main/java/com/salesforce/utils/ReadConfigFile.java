package com.salesforce.utils;

import java.io.*;
import java.util.*;

public class ReadConfigFile {

    static Properties props = new Properties();
    String strFileName;
    String strValue;

    public ReadConfigFile(String strFileName) {

        this.strFileName = strFileName;
    }

    public String getProperty(String strKey) {
        try {
            File f = new File(strFileName);
            if (f.exists()) {
                FileInputStream in = new FileInputStream(f);
                props.load(in);
                strValue = props.getProperty(strKey);
                in.close();
            } else
                System.out.println("File not Found");
        } catch (Exception e) {
            System.out.println(e);
        }
        return strValue;


    }
}
