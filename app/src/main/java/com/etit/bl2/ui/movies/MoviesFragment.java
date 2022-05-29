package com.etit.bl2.ui.movies;

import androidx.lifecycle.ViewModelProvider;

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

import com.etit.bl2.BooksRecyclerAdapter;
import com.etit.bl2.DatabaseHelper;
import com.etit.bl2.FilmsRecyclerAdapter;
import com.etit.bl2.R;
import com.github.javafaker.Faker;

import java.util.ArrayList;

public class MoviesFragment extends Fragment {

    private MoviesViewModel mViewModel;

    RecyclerView recyclerView;
    DatabaseHelper db;
    ArrayList<String> film_id, film_title, film_author, film_image_uri, film_description, film_watch_txt;
    ArrayList<Integer> film_rating, film_watch_id;
    ArrayList<Bitmap> film_image;
    FilmsRecyclerAdapter adapter;

    public static MoviesFragment newInstance() {
        return new MoviesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewFilms);
        db = new DatabaseHelper(view.getContext());
        film_id = new ArrayList<>();
        film_title = new ArrayList<>();
        film_author = new ArrayList<>();
        film_image_uri = new ArrayList<>();
        film_image = new ArrayList<>();
        film_rating = new ArrayList<>();
        film_description = new ArrayList<>();
        film_watch_txt = new ArrayList<>();
        film_watch_id = new ArrayList<>();

        dataToArrays();
        adapter = new FilmsRecyclerAdapter(getActivity(), view.getContext(), film_id, film_title,
                film_author, film_image_uri, film_image, film_rating, film_description, film_watch_id, film_watch_txt);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MoviesViewModel.class);
        // TODO: Use the ViewModel
    }

    private void dataToArrays() {
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
                film_id.add(cursor.getString(0));
                film_title.add(cursor.getString(1));
                film_author.add(cursor.getString(2));
                film_image_uri.add(cursor.getString(3));
                //Добавить метод на извлечение из Byte в Bitmap
                imgByte = cursor.getBlob(4);
                film_image.add(BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length));
                film_watch_id.add(cursor.getInt(5));
                film_watch_txt.add(cursor.getString(6));
                film_rating.add(cursor.getInt(7));
                film_description.add(cursor.getString(8));
            }
        }
    }

}