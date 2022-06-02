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
import com.etit.bl2.ui.MainActivity;
import com.etit.bl2.R;
import com.etit.bl2.ui.books.AddNewBook;
import com.squareup.picasso.Picasso;

public class AddNewFilm extends AppCompatActivity {

    private EditText title_input, author_input, image_uri_input, description_input;
    private Button add_button;
    private Spinner watch_input;
    private RatingBar rating_input;
    private ImageView cover_output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_film);


        title_input = findViewById(R.id.etTitleFilm);
        author_input = findViewById(R.id.etDirector);
        image_uri_input = findViewById(R.id.etImageUriFilm);
        description_input = findViewById(R.id.etCommentFilm);
        watch_input = findViewById(R.id.spWatch);
        add_button = findViewById(R.id.buttonFilm);
        rating_input = findViewById(R.id.rbRatingFilm);
        cover_output = findViewById(R.id.ivCoverFilm);

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

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[] cover_byte = AddNewBook.drawable2Bytes(cover_output.getDrawable());
                DatabaseHelper myDB = new DatabaseHelper(AddNewFilm.this);
                myDB.insertFilm(title_input.getText().toString().trim(),
                        author_input.getText().toString().trim(),
                        image_uri_input.getText().toString().trim(),
                        cover_byte,
                        (int) watch_input.getSelectedItemId(),
                        watch_input.getSelectedItem().toString(),
                        rating_input.getProgress(),
                        description_input.getText().toString().trim());
                //myDB.customSelect("library_books", "rating", "> 2");
                Intent intent = new Intent(AddNewFilm.this, MainActivity.class);
                startActivityForResult(intent, 1);
                finish();
            }
        });
    }
}