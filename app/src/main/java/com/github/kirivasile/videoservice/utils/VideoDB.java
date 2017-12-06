package com.github.kirivasile.videoservice.utils;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.kirivasile.videoservice.model.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoDB extends SQLiteOpenHelper{
    private static final String TABLE_NAME = "video_table";
    private static VideoDB sInstance;

    private VideoDB(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    public static VideoDB getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new VideoDB(context);
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + "id integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + "name text,"
                + "videoUrl text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }

    public void saveVideo(Video video) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", video.getId());
        cv.put("name", video.getName());
        cv.put("videoUrl", video.getVideoUrl());
        db.insertOrThrow(TABLE_NAME, null, cv);
    }

    public void deleteVideo(int videoId) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = "id=?";
        String[] selectionArgs = {Integer.toString(videoId)};
        db.delete(TABLE_NAME, selection, selectionArgs);
    }

    public List<Integer> getSavedVideos() {
        List<Integer> ids = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int idColIndex = cursor.getColumnIndex("id");

            do {
                ids.add(cursor.getInt(idColIndex));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return ids;
    }
}
