package com.etit.bl2.ui.books;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etit.bl2.data.DatabaseHelper;
import com.etit.bl2.R;
import com.github.javafaker.Faker;

import java.util.ArrayList;

public class BooksFragment extends Fragment {

    private BooksViewModel mViewModel;

    RecyclerView recyclerView;
    DatabaseHelper db;
    ArrayList<String> book_id, book_title, book_author, book_image_uri, book_description;
    ArrayList<Integer> book_rating, book_progress;
    ArrayList<Bitmap> book_image;
    BooksRecyclerAdapter adapter;

    public static BooksFragment newInstance() {
        return new BooksFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewBooks);
        db = new DatabaseHelper(view.getContext());
        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_image_uri = new ArrayList<>();
        book_image = new ArrayList<>();
        book_rating = new ArrayList<>();
        book_progress = new ArrayList<>();
        book_description = new ArrayList<>();

        dataToArrays();
        adapter = new BooksRecyclerAdapter(getActivity(), view.getContext(), book_id, book_title,
                book_author, book_image_uri, book_image, book_progress, book_rating, book_description);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

    private Activity getAct() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    private void dataToArrays() {
        Faker faker = new Faker();
        //Cursor cursor = db.customSelect("library_books", "rating", "> 2");
        Cursor cursor = db.readAllBooks();
        if (cursor.getCount() == 0) {
            Log.d("DataToArrays", "NO DATA");
            String name;
            String book;
            String comment;
            String uri = "https://avatars.githubusercontent.com/u/71878526?s=400&u=f8ef188976fdbc7d0bc8e072732a1669648e11b5&v=4";
            byte[] image = {1, 2, 3};
            int progress = 45;
            int rating = 8;
//            for (int i=0;i < 10; i++){
//                name = faker.book().author();
//                book = faker.book().title();
//                comment = faker.dune().saying();
//                db.insertBook(name, book, uri, image, progress, rating, comment);
//            }
        } else {
            byte[] imgByte;
            while(cursor.moveToNext()) {
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_image_uri.add(cursor.getString(3));
                //Добавить метод на извлечение из Byte в Bitmap
                imgByte = cursor.getBlob(4);
                book_image.add(BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length));
                book_progress.add(cursor.getInt(5));
                book_rating.add(cursor.getInt(6));
                book_description.add(cursor.getString(7));
            }
        }
    }

}