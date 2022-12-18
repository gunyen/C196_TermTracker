package com.c196.c196_termtracker.Database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.c196.c196_termtracker.DAO.AssessmentDAO;
import com.c196.c196_termtracker.DAO.CourseDAO;
import com.c196.c196_termtracker.DAO.TermDAO;
import com.c196.c196_termtracker.Entity.Assessment;
import com.c196.c196_termtracker.Entity.Course;
import com.c196.c196_termtracker.Entity.Term;

@Database(entities = {Term.class, Course.class, Assessment.class}, version = 7, exportSchema = false)
public abstract class TrackerDatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static volatile TrackerDatabaseBuilder INSTANCE;

    static TrackerDatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TrackerDatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TrackerDatabaseBuilder.class, "myTrackerDatabase")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
