package com.etit.bl2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class UpdateBook extends AppCompatActivity {
    private String id, title, author, image_uri;
    private int progress, rating;

    private EditText title_input, author_input, image_uri_input;
    private ImageView cover_output;
    private TextView progress_txt;
    private Button update_button;
    private SeekBar progress_input;
    private RatingBar rating_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        title_input = findViewById(R.id.etTitleUpdate);
        author_input = findViewById(R.id.etAuthorUpdate);
        image_uri_input = findViewById(R.id.etImageUriUpdate);
        progress_txt = findViewById(R.id.tvProgressIndicationUpdate);
        update_button = findViewById(R.id.buttonUpdate);
        progress_input = findViewById(R.id.sbProgressUpdate);
        rating_input = findViewById(R.id.rbRatingUpdate);
        cover_output = findViewById(R.id.ivCoverUpdate);

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
                if(!image_path.isEmpty()) Picasso.get().load(image_path).into(cover_output);
            }
        });

        progress_input.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String percent = String.valueOf(seekBar.getProgress()) + '%';
                progress_txt.setText(percent);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        getAndSetExtra();

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[] cover_byte = AddNewBook.imageViewToByteArray(cover_output);
                DatabaseHelper db = new DatabaseHelper(UpdateBook.this);
                db.updateBooks(id, title_input.getText().toString().trim(),
                        author_input.getText().toString().trim(),
                        image_uri_input.getText().toString().trim(),
                        cover_byte,
                        progress_input.getProgress(),
                        rating_input.getProgress());
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
            progress = getIntent().getIntExtra("progress", -1);
            rating = getIntent().getIntExtra("rating", -1);

            title_input.setText(title);
            author_input.setText(author);
            image_uri_input.setText(image_uri);
            progress_input.setProgress(progress);
            rating_input.setProgress(rating);
        }else{

        }
    }
}