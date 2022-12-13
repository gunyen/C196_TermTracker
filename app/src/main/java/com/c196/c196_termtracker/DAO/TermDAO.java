package com.c196.c196_termtracker.DAO;

import androidx.room.*;
import com.c196.c196_termtracker.Entity.Term;

import java.util.List;

@Dao
public interface TermDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("SELECT * FROM term ORDER BY termID ASC")
    List<Term> getAllTerms();
}
