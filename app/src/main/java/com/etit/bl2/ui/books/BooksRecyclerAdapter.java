package com.etit.bl2.ui.books;

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

import com.etit.bl2.R;

import java.util.ArrayList;

public class BooksRecyclerAdapter extends RecyclerView.Adapter<BooksRecyclerAdapter.BooksViewHolder> {
    private Context context;
    Activity activity;
    int position;
    private ArrayList book_id, book_title, book_author, book_image_uri, book_image, book_rating,
            book_progress, book_description;

    public BooksRecyclerAdapter(Activity activity, Context context, ArrayList book_id,
                                ArrayList book_title, ArrayList book_author,
                                ArrayList book_image_uri, ArrayList book_image,
                                ArrayList book_progress, ArrayList book_rating, ArrayList book_description) {
        this.activity = activity;
        this.context = context;
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_author = book_author;
        this.book_image = book_image;
        this.book_image_uri = book_image_uri;
        this.book_progress = book_progress;
        this.book_rating = book_rating;
        this.book_description = book_description;
    }


    public class BooksViewHolder extends RecyclerView.ViewHolder{
        TextView book_title_txt, book_author_txt, book_progress_txt, book_rating_txt;
        ImageView book_cover;
        LinearLayout layout;
        public BooksViewHolder(@NonNull View itemView) {
            super(itemView);
            book_title_txt = itemView.findViewById(R.id.tvBookTitle);
            book_author_txt = itemView.findViewById(R.id.tvBookAuthor);
            book_progress_txt = itemView.findViewById(R.id.tvProgressIndication);
            book_rating_txt = itemView.findViewById(R.id.tvBookRating);
            book_cover = itemView.findViewById(R.id.ivBookCover);
            layout = itemView.findViewById(R.id.recycler_row);
        }
    }

    @NonNull
    @Override
    public BooksRecyclerAdapter.BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_row, parent, false);
        return new BooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksViewHolder holder, int position) {
        this.position = position;
        Integer rating_int = (Integer) book_rating.get(position);
        float rating_float = rating_int / 2f;
        String progress = String.valueOf(book_progress.get(position)) + '%';
        holder.book_title_txt.setText(String.valueOf(book_title.get(position)));
        holder.book_author_txt.setText(String.valueOf(book_author.get(position)));
        holder.book_progress_txt.setText(progress);
        holder.book_rating_txt.setText(String.valueOf(rating_float));
        holder.book_cover.setImageBitmap((Bitmap) book_image.get(position));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateBook.class);
                intent.putExtra("id", String.valueOf(book_id.get(position)));
                intent.putExtra("title", String.valueOf(book_title.get(position)));
                intent.putExtra("author", String.valueOf(book_author.get(position)));
                intent.putExtra("image_uri", String.valueOf(book_image_uri.get(position)));
                intent.putExtra("progress", (Integer) book_progress.get(position));
                intent.putExtra("rating", (Integer) book_rating.get(position));
                intent.putExtra("description",String.valueOf(book_description.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return book_id.size();
    }


}
