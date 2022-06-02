package com.etit.bl2.ui.books;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
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
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import com.etit.bl2.data.DatabaseHelper;
import com.etit.bl2.ui.MainActivity;
import com.etit.bl2.R;
import com.squareup.picasso.Picasso;

public class AddNewBook extends AppCompatActivity {

    private EditText title_input, author_input, image_uri_input, description_input;
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
        description_input = findViewById(R.id.etComment);
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
                byte[] cover_byte = drawable2Bytes(cover_output.getDrawable());
                DatabaseHelper myDB = new DatabaseHelper(AddNewBook.this);
                if(cover_byte.length != 0){
                    myDB.insertBook(title_input.getText().toString().trim(),
                            author_input.getText().toString().trim(),
                            image_uri_input.getText().toString().trim(),
                            cover_byte,
                            progress_input.getProgress(),
                            rating_input.getProgress(),
                            description_input.getText().toString().trim());
                    //myDB.customSelect("library_books", "rating", "> 2");
                    Intent intent = new Intent(AddNewBook.this, MainActivity.class);
                    startActivityForResult(intent, 1);

                    finish();
                }else{
                    Toast.makeText(view.getContext(), "Нет обложки", Toast.LENGTH_SHORT);
                }

            }
        });
    }


    public static byte[] drawable2Bytes(Drawable d) {
        Bitmap bitmap = drawable2Bitmap(d);
        return bitmap2Bytes(bitmap);
    }

    public static Bitmap drawable2Bitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    static void isEmpty(){

    }
}