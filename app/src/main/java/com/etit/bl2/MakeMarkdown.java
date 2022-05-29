package com.etit.bl2;


import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.github.javafaker.Faker;

import net.steppschuh.markdowngenerator.table.Table;
import net.steppschuh.markdowngenerator.text.heading.Heading;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class MakeMarkdown {
    ArrayList<String> film_title, film_author, film_description, film_watch_txt;
    ArrayList<Integer> film_rating;

    ArrayList<String> book_title, book_author,  book_description;
    ArrayList<Integer> book_rating, book_progress;

    DatabaseHelper db;

    public void make(Context context) throws IOException {

        db = new DatabaseHelper(context);
        film_title = new ArrayList<>();
        film_author = new ArrayList<>();
        film_rating = new ArrayList<>();
        film_description = new ArrayList<>();
        film_watch_txt = new ArrayList<>();


        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_rating = new ArrayList<>();
        book_progress = new ArrayList<>();
        book_description = new ArrayList<>();

        dataToArraysFilms();
        dataToArraysBooks();



        Table.Builder tableFilms = new Table.Builder()
                .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_LEFT)
                .addRow("Title", "Director", "Watch", "Rating", "Description");

        Table.Builder tableBooks = new Table.Builder()
                .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_LEFT)
                .addRow("Title", "Author", "Progress", "Rating", "Description");


        for (int i = 0; i < film_title.size() ; i++) {
           tableFilms.addRow(film_title.get(i),film_author.get(i), film_watch_txt.get(i), film_rating.get(i), film_description.get(i));
         }

        for (int i = 0; i < book_title.size() ; i++) {
            tableBooks.addRow(book_title.get(i),book_author.get(i), book_progress.get(i), book_rating.get(i), book_description.get(i));
        }

        StringBuilder sb = new StringBuilder()
                .append(new Heading("My books", 1))
                .append("\n")
                .append(tableBooks.build().toString())
                .append("\n")
                .append(new Heading("My films", 1))
                .append("\n")
                .append(tableFilms.build().toString())
                .append("\n");

        File path = new File("/storage/emulated/0/Download");
        String table_films = tableFilms.build().toString();
        //Log.d("dsa",table_txt);
        try {
            FileWriter writer = new FileWriter(new File(path, "markdown.md"));
            writer.append(sb.toString());

            writer.flush();
            writer.close();
        }catch (Exception e){

        }




    }

    private void dataToArraysFilms() {
        Faker faker = new Faker();
        //Cursor cursor = db.customSelect("library_books", "rating", "> 2");
        Cursor cursor = db.readAllFilms();
        if (cursor.getCount() == 0) {
            Log.d("DataToArrays", "NO DATA");
//            String name;
//            String book;
//            String comment;
//            String uri = "https://avatars.githubusercontent.com/u/71878526?s=400&u=f8ef188976fdbc7d0bc8e072732a1669648e11b5&v=4";
//            byte[] image = {1, 2, 3};
//            int progress = 45;
//            int rating = 8;
//            for (int i=0;i < 10; i++){
//                name = faker.book().author();
//                book = faker.book().title();
//                comment = faker.dune().saying();
//                db.insertBook(name, book, uri, image, progress, rating, comment);
//            }
        } else {
            byte[] imgByte;
            while(cursor.moveToNext()) {
                film_title.add(cursor.getString(1));
                film_author.add(cursor.getString(2));
                //Добавить метод на извлечение из Byte в Bitmap
//                imgByte = cursor.getBlob(4);
//                film_watch_id.add(cursor.getInt(5));
                film_watch_txt.add(cursor.getString(6));
                film_rating.add(cursor.getInt(7));
                film_description.add(cursor.getString(8));
            }
        }
    }

    private void dataToArraysBooks() {
        Faker faker = new Faker();
        //Cursor cursor = db.customSelect("library_books", "rating", "> 2");
        Cursor cursor = db.readAllBooks();
        if (cursor.getCount() == 0) {
            Log.d("DataToArrays", "NO DATA");
//            String name;
//            String book;
//            String comment;
//            String uri = "https://avatars.githubusercontent.com/u/71878526?s=400&u=f8ef188976fdbc7d0bc8e072732a1669648e11b5&v=4";
//            byte[] image = {1, 2, 3};
//            int progress = 45;
//            int rating = 8;
//            for (int i=0;i < 10; i++){
//                name = faker.book().author();
//                book = faker.book().title();
//                comment = faker.dune().saying();
//                db.insertBook(name, book, uri, image, progress, rating, comment);
//            }
        } else {
            while(cursor.moveToNext()) {
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                //Добавить метод на извлечение из Byte в Bitmap
                book_progress.add(cursor.getInt(5));
                book_rating.add(cursor.getInt(6));
                book_description.add(cursor.getString(7));
            }
        }
    }
}

