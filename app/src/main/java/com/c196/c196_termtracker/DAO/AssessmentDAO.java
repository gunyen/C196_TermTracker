package com.c196.c196_termtracker.DAO;

import androidx.room.*;
import com.c196.c196_termtracker.Entity.Assessment;

import java.util.List;

@Dao
public interface AssessmentDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("SELECT * FROM assessment ORDER BY assessmentID ASC")
    List<Assessment> getAllAssessments();
}
