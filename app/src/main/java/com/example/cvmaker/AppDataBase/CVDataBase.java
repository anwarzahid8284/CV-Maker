package com.example.cvmaker.AppDataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.cvmaker.DataAccesObject.CVDao;
import com.example.cvmaker.Model.CVModel;

@Database(entities = {CVModel.class},version = 1,exportSchema = false)
public abstract class CVDataBase extends RoomDatabase {
    public abstract CVDao cvDao();
}
