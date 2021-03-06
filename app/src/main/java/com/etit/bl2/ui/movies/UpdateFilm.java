package com.etit.bl2.ui.movies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.etit.bl2.data.DatabaseHelper;
import com.etit.bl2.R;
import com.etit.bl2.ui.MainActivity;
import com.etit.bl2.ui.books.AddNewBook;
import com.squareup.picasso.Picasso;

public class UpdateFilm extends AppCompatActivity {
    private String id, title, author, image_uri, description;
    private int watch_id, rating;

    private EditText title_input, author_input, image_uri_input, description_input;
    private ImageView cover_output;
    private Spinner watch_input;
    private Button update_button;
    private RatingBar rating_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_film);

        title_input = findViewById(R.id.etTitleFilmUpdate);
        author_input = findViewById(R.id.etDirectorUpdate);
        image_uri_input = findViewById(R.id.etImageUriFilmUpdate);
        watch_input = findViewById(R.id.spWatchUpdate);
        description_input = findViewById(R.id.etCommentFilmUpdate);
        update_button = findViewById(R.id.buttonFilmUpdate);
        rating_input = findViewById(R.id.rbRatingFilmUpdate);
        cover_output = findViewById(R.id.ivCoverFilmUpdate);

        image_uri_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String image_path = image_uri_input.getText().toString().trim();
                if(!image_path.isEmpty()){
                    Picasso.get()
                            .load(image_path)
                            .placeholder(R.drawable.ic_image_search)
                            .error(R.drawable.ic_no_image)
                            .into(cover_output);
                }else{
                    cover_output.setImageResource(R.drawable.ic_no_image);
                }
            }
        });

        getAndSetExtra();

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[] cover_byte = AddNewBook.drawable2Bytes(cover_output.getDrawable());
                DatabaseHelper db = new DatabaseHelper(UpdateFilm.this);
                db.updateFilm(id, title_input.getText().toString().trim(),
                        author_input.getText().toString().trim(),
                        image_uri_input.getText().toString().trim(),
                        cover_byte,
                        (int) watch_input.getSelectedItemId(),
                        watch_input.getSelectedItem().toString(),
                        rating_input.getProgress(),
                        description_input.getText().toString().trim());
                Intent intent = new Intent(UpdateFilm.this, MainActivity.class);
                startActivityForResult(intent, 1);
                finish();
            }
        });
    }

    private void getAndSetExtra(){
        if(getIntent().hasExtra("id")){
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            image_uri = getIntent().getStringExtra("image_uri");
            watch_id = getIntent().getIntExtra("watch_id", 0);
            rating = getIntent().getIntExtra("rating", -1);
            description = getIntent().getStringExtra("description");

            title_input.setText(title);
            author_input.setText(author);
            description_input.setText(description);
            image_uri_input.setText(image_uri);
            watch_input.setSelection(watch_id);
            rating_input.setProgress(rating);
        }else{

        }
    }
}