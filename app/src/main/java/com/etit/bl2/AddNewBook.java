package com.etit.bl2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

import java.io.ByteArrayOutputStream;
import com.squareup.picasso.Picasso;

public class AddNewBook extends AppCompatActivity {

    private EditText title_input, author_input, image_uri_input;
    private TextView progress_txt;
    private Button add_button;
    private SeekBar progress_input;
    private RatingBar rating_input;
    private ImageView cover_output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_book);

        title_input = findViewById(R.id.etTitle);
        author_input = findViewById(R.id.etAuthor);
        image_uri_input = findViewById(R.id.etImageUri);
        progress_txt = findViewById(R.id.tvProgressIndication);
        add_button = findViewById(R.id.button);
        progress_input = findViewById(R.id.sbProgress);
        rating_input = findViewById(R.id.rbRating);
        cover_output = findViewById(R.id.ivCover);

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
                            //.error(R.mipmap.ic_launcher)
                            .into(cover_output);
                }else{
                    cover_output.setImageResource(R.drawable.ic_launcher_background);
                }
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[] cover_byte = imageViewToByteArray(cover_output);
                DatabaseHelper myDB = new DatabaseHelper(AddNewBook.this);
                myDB.insertBook(title_input.getText().toString().trim(),
                        author_input.getText().toString().trim(),
                        image_uri_input.getText().toString().trim(),
                        cover_byte,
                        progress_input.getProgress(),
                        rating_input.getProgress());
                //myDB.customSelect("library_books", "rating", "> 2");
                Activity activity = getParent();
                activity.recreate();
                finish();
            }
        });
    }

    static byte[] imageViewToByteArray(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}