package com.etit.bl2.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import  android.content.Context;

public class DatabaseHelper extends SQLiteOpenHelper{
    private Context context;

    private static final String DB_NAME = "library";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME_BOOK = "library_books";
    private static final String TABLE_NAME_GAME = "library_games";
    private static final String TABLE_NAME_FILM = "library_films";
    private static final String TABLE_NAME_SERIES = "library_series";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_PROGRESS = "progress";
    private static final String COLUMN_IMAGE_URI = "image_uri";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_RELEASE = "release_date";
    private static final String COLUMN_DESCRIPTION = "description";

    private static final String BOOK_AUTHOR = "author";
    private static final String GAME_PLATFORM = "platform";
    private static final String GAME_HOURS = "hours";
    private static final String GAME_DEV = "developer";
    private static final String FILM_DIRECTOR = "director";
    private static final String FILM_WATCH = "watch";
    private static final String FILM_WATCH_ID = "watch_id";
    private static final String SERIES_STUDIO = "studio";
    private static final String SERIES_FINAL = "final_date";
    private static final String SERIES_SEASONS = "seasons";
    private static final String SERIES_EPISODES = "episodes";


    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME_BOOK
                + " (" +  COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + BOOK_AUTHOR + " TEXT, "
                + COLUMN_IMAGE_URI + " TEXT, "
                + COLUMN_IMAGE + " BLOB, "
                + COLUMN_PROGRESS + " INTEGER, "
                + COLUMN_RATING + " INTEGER, "
                + COLUMN_DESCRIPTION + " TEXT);";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_NAME_GAME
                + " (" +  COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + GAME_DEV + " TEXT, "
                + COLUMN_IMAGE_URI + " TEXT, "
                + COLUMN_IMAGE + " BLOB, "
                + GAME_PLATFORM + " TEXT, "
                + GAME_HOURS + " INTEGER, "
                + COLUMN_RELEASE + " INTEGER, "
                + COLUMN_PROGRESS + " INTEGER, "
                + COLUMN_DESCRIPTION + " TEXT);";
        db.execSQL(query);
        query = "CREATE TABLE " + TABLE_NAME_FILM
                + " (" +  COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + FILM_DIRECTOR + " TEXT, "
                + COLUMN_IMAGE_URI + " TEXT, "
                + COLUMN_IMAGE + " BLOB, "
                + FILM_WATCH_ID + " INTEGER, "
                + FILM_WATCH + " TEXT, "
                + COLUMN_RATING + " INTEGER, "
                + COLUMN_DESCRIPTION + " TEXT);";
        db.execSQL(query);
        query = "CREATE TABLE " + TABLE_NAME_SERIES
                + " (" +  COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + SERIES_STUDIO + " TEXT, "
                + COLUMN_IMAGE_URI + " TEXT, "
                + COLUMN_IMAGE + " BLOB, "
                + SERIES_SEASONS + " INTEGER, "
                + SERIES_EPISODES + " INTEGER, "
                + COLUMN_RELEASE + " INTEGER, "
                + SERIES_FINAL + " INTEGER, "
                + COLUMN_PROGRESS + " INTEGER, "
                + COLUMN_RATING + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_BOOK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_GAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_FILM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SERIES);
        onCreate(db);
    }

    public void insertBook(String title, String author, String image_uri, byte [] image, int progress, int rating, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(BOOK_AUTHOR, author);
        cv.put(COLUMN_IMAGE, image);
        cv.put(COLUMN_IMAGE_URI, image_uri);
        cv.put(COLUMN_PROGRESS, progress);
        cv.put(COLUMN_RATING, rating);
        cv.put(COLUMN_DESCRIPTION, description);

        db.insert(TABLE_NAME_BOOK, null, cv);
    }

    public Cursor customSelect(String table, String column, String rowFilter){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + table + " WHERE " + column + ' ' + rowFilter;
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor readAllBooks(){
        String query = "SELECT * FROM " + TABLE_NAME_BOOK;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateBooks(String row_id, String title, String author, String image_uri, byte[] image, int progress, int rating, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(BOOK_AUTHOR, author);
        cv.put(COLUMN_IMAGE_URI, image_uri);
        cv.put(COLUMN_IMAGE, image);
        cv.put(COLUMN_PROGRESS, progress);
        cv.put(COLUMN_RATING, rating);
        cv.put(COLUMN_DESCRIPTION, description);

        db.update(TABLE_NAME_BOOK, cv, "_id=?", new String[]{row_id});
    }

    public void insertFilm(String title, String director, String image_uri, byte [] image,int watch_id, String watch, int rating, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(FILM_DIRECTOR, director);
        cv.put(COLUMN_IMAGE, image);
        cv.put(COLUMN_IMAGE_URI, image_uri);
        cv.put(FILM_WATCH, watch);
        cv.put(FILM_WATCH_ID, watch_id);
        cv.put(COLUMN_RATING, rating);
        cv.put(COLUMN_DESCRIPTION, description);

        db.insert(TABLE_NAME_FILM, null, cv);
    }

    public void updateFilm(String row_id, String title, String director, String image_uri, byte [] image,int watch_id, String watch, int rating, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(FILM_DIRECTOR, director);
        cv.put(COLUMN_IMAGE, image);
        cv.put(COLUMN_IMAGE_URI, image_uri);
        cv.put(FILM_WATCH, watch);
        cv.put(FILM_WATCH_ID, watch_id);
        cv.put(COLUMN_RATING, rating);
        cv.put(COLUMN_DESCRIPTION, description);

        db.update(TABLE_NAME_FILM, cv, "_id=?", new String[]{row_id});
    }

    public Cursor readAllFilms(){
        String query = "SELECT * FROM " + TABLE_NAME_FILM;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}
