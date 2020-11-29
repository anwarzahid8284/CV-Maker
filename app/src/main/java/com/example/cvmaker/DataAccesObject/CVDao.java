package com.example.cvmaker.DataAccesObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cvmaker.Model.CVModel;

import java.util.List;

@Dao
public interface CVDao {
    @Insert
    void insert(CVModel cvModel);

    @Query("SELECT * FROM CVMODEL_TB ORDER BY pid DESC LIMIT 1")
    List<CVModel> getLastRow();

    @Delete
    void delete(CVModel cvModel);

    @Update
    void update(CVModel cvModel);
}
