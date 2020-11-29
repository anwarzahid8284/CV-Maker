package com.example.cvmaker.DBClient;

import android.content.Context;

import androidx.room.Room;

import com.example.cvmaker.AppDataBase.CVDataBase;

public class DatabaseClient {
    private Context mCtx;
    private static DatabaseClient mInstance;

    //our app database object
    private CVDataBase appDatabase;

    private DatabaseClient(Context mCtx) {
        this.mCtx = mCtx;

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        appDatabase = Room.databaseBuilder(mCtx, CVDataBase.class, "MyToDos").build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public CVDataBase getAppDatabase() {
        return appDatabase;
    }
}
