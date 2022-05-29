package com.etit.bl2;


import android.os.Environment;
import android.util.Log;

import net.steppschuh.markdowngenerator.table.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;


public class MakeMarkdown {
    public static void make(){
        Table.Builder tableBuilder = new Table.Builder()
                .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_LEFT)
                .withRowLimit(7)
                .addRow("Index", "Boolean");
        for (int i = 1; i <= 20; i++) {
           tableBuilder.addRow(i, Math.random() > 0.5);
         }

        String table_txt = tableBuilder.build().toString();
        //Log.d("dsa",table_txt);
        String filepath ="/mnt/sdcard/test.txt";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filepath);
            byte[] buffer = "This will be writtent in test.txt".getBytes();
            fos.write(buffer, 0, buffer.length);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

