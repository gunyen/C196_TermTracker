package com.c196.c196_termtracker.Database;

import android.app.Application;
import com.c196.c196_termtracker.DAO.AssessmentDAO;
import com.c196.c196_termtracker.DAO.CourseDAO;
import com.c196.c196_termtracker.DAO.TermDAO;
import com.c196.c196_termtracker.Entity.Assessment;
import com.c196.c196_termtracker.Entity.Course;
import com.c196.c196_termtracker.Entity.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private TermDAO mTermDAO;
    private CourseDAO mCourseDAO;
    private AssessmentDAO mAssessmentDAO;
    private List<Term> mAllTerms;
    private List<Course> mAllCourses;
    private List<Assessment> mAllAssessments;
    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        TrackerDatabaseBuilder db=TrackerDatabaseBuilder.getDatabase(application);
        mTermDAO=db.termDAO();
        mCourseDAO=db.courseDAO();
        mAssessmentDAO=db.assessmentDAO();
    }

    // Methods for Term
    public List<Term> getAllTerms(){
        databaseExecutor.execute(()->{
            mAllTerms=mTermDAO.getAllTerms();
        });

        try {
            Thread.sleep(1000);
        }catch(InterruptedException e){
            // Find out what the error is
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public void insert(Term term) {
        databaseExecutor.execute(()->{
            mTermDAO.insert(term);
        });

        try {
            Thread.sleep(1000);
        }catch(InterruptedException e){
            // Find out what the error is
            e.printStackTrace();
        }
    }

    public void update(Term term) {
        databaseExecutor.execute(()->{
            mTermDAO.update(term);
        });

        try {
            Thread.sleep(1000);
        }catch(InterruptedException e){
            // Find out what the error is
            e.printStackTrace();
        }
    }

    public void delete(Term term) {
        databaseExecutor.execute(()->{
            mTermDAO.delete(term);
        });

        try {
            Thread.sleep(1000);
        }catch(InterruptedException e){
            // Find out what the error is
            e.printStackTrace();
        }
    }

    // Methods for Course
    public List<Course> getAllCourses(){
        databaseExecutor.execute(()->{
            mAllCourses=mCourseDAO.getAllCourses();
        });

        try {
            Thread.sleep(1000);
        }catch(InterruptedException e){
            // Find out what the error is
            e.printStackTrace();
        }
        return mAllCourses;
    }

    public void insert(Course course) {
        databaseExecutor.execute(()->{
            mCourseDAO.insert(course);
        });

        try {
            Thread.sleep(1000);
        }catch(InterruptedException e){
            // Find out what the error is
            e.printStackTrace();
        }
    }

    public void update(Course course) {
        databaseExecutor.execute(()->{
            mCourseDAO.update(course);
        });

        try {
            Thread.sleep(1000);
        }catch(InterruptedException e){
            // Find out what the error is
            e.printStackTrace();
        }
    }

    public void delete(Course course) {
        databaseExecutor.execute(()->{
            mCourseDAO.delete(course);
        });

        try {
            Thread.sleep(1000);
        }catch(InterruptedException e){
            // Find out what the error is
            e.printStackTrace();
        }
    }

    // Methods for Assessments
    public List<Assessment> getAllAssessments(){
        databaseExecutor.execute(()->{
            mAllAssessments=mAssessmentDAO.getAllAssessments();
        });

        try {
            Thread.sleep(1000);
        }catch(InterruptedException e){
            // Find out what the error is
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    public void insert(Assessment assessment) {
        databaseExecutor.execute(()->{
            mAssessmentDAO.insert(assessment);
        });

        try {
            Thread.sleep(1000);
        }catch(InterruptedException e){
            // Find out what the error is
            e.printStackTrace();
        }
    }

    public void update(Assessment assessment) {
        databaseExecutor.execute(()->{
            mAssessmentDAO.update(assessment);
        });

        try {
            Thread.sleep(1000);
        }catch(InterruptedException e){
            // Find out what the error is
            e.printStackTrace();
        }
    }

    public void delete(Assessment assessment) {
        databaseExecutor.execute(()->{
            mAssessmentDAO.delete(assessment);
        });

        try {
            Thread.sleep(1000);
        }catch(InterruptedException e){
            // Find out what the error is
            e.printStackTrace();
        }
    }
}
