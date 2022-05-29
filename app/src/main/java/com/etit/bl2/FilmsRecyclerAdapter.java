package com.etit.bl2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FilmsRecyclerAdapter extends RecyclerView.Adapter<FilmsRecyclerAdapter.FilmsViewHolder> {
    private Context context;
    Activity activity;
    int position;
    private ArrayList film_id, film_title, film_author, film_image_uri, film_image, film_rating,
         film_description, film_watch_txt, film_watch_id;

    public FilmsRecyclerAdapter(Activity activity, Context context, ArrayList book_id,
                                ArrayList book_title, ArrayList book_author,
                                ArrayList book_image_uri, ArrayList book_image,
                                ArrayList book_rating, ArrayList book_description,
                                ArrayList film_watch_id, ArrayList film_watch_txt) {
        this.activity = activity;
        this.context = context;
        this.film_id = book_id;
        this.film_title = book_title;
        this.film_author = book_author;
        this.film_image = book_image;
        this.film_image_uri = book_image_uri;
        this.film_rating = book_rating;
        this.film_description = book_description;
        this.film_watch_id = film_watch_id;
        this.film_watch_txt = film_watch_txt;
    }


    public class FilmsViewHolder extends RecyclerView.ViewHolder{
        TextView film_title_txt, film_author_txt, film_watch_txt, film_rating_txt;
        ImageView book_cover;
        LinearLayout layout;
        public FilmsViewHolder(@NonNull View itemView) {
            super(itemView);
            film_title_txt = itemView.findViewById(R.id.tvFilmTitle);
            film_author_txt = itemView.findViewById(R.id.tvFilmAuthor);
            film_watch_txt = itemView.findViewById(R.id.tvWatch);
            film_rating_txt = itemView.findViewById(R.id.tvFilmRating);
            book_cover = itemView.findViewById(R.id.ivFilmCover);
            layout = itemView.findViewById(R.id.recycler_row_films);
        }
    }

    @NonNull
    @Override
    public FilmsRecyclerAdapter.FilmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_row_films, parent, false);
        return new FilmsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmsViewHolder holder, int position) {
        this.position = position;
        Integer rating_int = (Integer) film_rating.get(position);
        float rating_float = rating_int / 2f;
        holder.film_title_txt.setText(String.valueOf(film_title.get(position)));
        holder.film_author_txt.setText(String.valueOf(film_author.get(position)));
        holder.film_watch_txt.setText(String.valueOf(film_watch_txt.get(position)));
        holder.film_rating_txt.setText(String.valueOf(rating_float));
        holder.book_cover.setImageBitmap((Bitmap) film_image.get(position));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateFilm.class);
                intent.putExtra("id", String.valueOf(film_id.get(position)));
                intent.putExtra("title", String.valueOf(film_title.get(position)));
                intent.putExtra("author", String.valueOf(film_author.get(position)));
                intent.putExtra("image_uri", String.valueOf(film_image_uri.get(position)));
                intent.putExtra("watch_id", (Integer) film_watch_id.get(position));
//                intent.putExtra("watch_txt", String.valueOf(film_watch_txt.get(position)));
                intent.putExtra("rating", (Integer) film_rating.get(position));
                intent.putExtra("description",String.valueOf(film_description.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return film_id.size();
    }


}