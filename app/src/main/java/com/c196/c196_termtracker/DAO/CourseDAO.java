package com.c196.c196_termtracker.DAO;

import androidx.room.*;
import com.c196.c196_termtracker.Entity.Course;

import java.util.List;

@Dao
public interface CourseDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM course ORDER BY courseID ASC")
    List<Course> getAllCourses();
}
