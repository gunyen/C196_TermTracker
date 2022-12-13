package com.c196.c196_termtracker.UI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.c196.c196_termtracker.Database.Repository;
import com.c196.c196_termtracker.Entity.Course;
import com.c196.c196_termtracker.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CourseDetails extends AppCompatActivity {

    EditText editCourseName;
    EditText editCourseStart;
    EditText editCourseEnd;
    Spinner editStatus;
    EditText editInstructorName;
    EditText editInstructorPhone;
    EditText editInstructorEmail;
    EditText editCourseNotes;
    int courseID;
    int termItemID;
    String courseName;
    String courseStart;
    String courseEnd;
    int statusItem;
    String status;
    String instructorName;
    String instructorPhone;
    String instructorEmail;
    String courseNotes;
    Course currentCourse;
    Repository repositoryCourse;
    String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        editCourseName = findViewById(R.id.editCourseName);
        editCourseStart = findViewById(R.id.editCourseStart);
        GetDate getStartDate = new GetDate(editCourseStart, CourseDetails.this);
        getStartDate.selectDate();
        editCourseEnd = findViewById(R.id.editCourseEnd);
        GetDate getEndDate = new GetDate(editCourseEnd, CourseDetails.this);
        getEndDate.selectDate();
        editStatus = findViewById(R.id.editStatus);
        editInstructorName = findViewById(R.id.editInstructorName);
        editInstructorPhone = findViewById(R.id.editInstructorPhone);
        editInstructorEmail = findViewById(R.id.editInstructorEmail);
        editCourseNotes = findViewById(R.id.editCourseNotes);
        courseID = getIntent().getIntExtra("courseID", -1);
        termItemID = getIntent().getIntExtra("termItemID", -1);
        courseName = getIntent().getStringExtra("courseName");
        courseStart = getIntent().getStringExtra("courseStart");
        courseEnd = getIntent().getStringExtra("courseEnd");
        statusItem = getIntent().getIntExtra("statusItem", -1);
        instructorName = getIntent().getStringExtra("instructorName");
        instructorPhone = getIntent().getStringExtra("instructorPhone");
        instructorEmail = getIntent().getStringExtra("instructorEmail");
        courseNotes = getIntent().getStringExtra("courseNotes");
        editCourseName.setText(courseName);
        editCourseStart.setText(courseStart);
        editCourseEnd.setText(courseEnd);
        editStatus.setSelection(statusSpinner(editStatus));
        status = getIntent().getStringExtra("status");
        editInstructorName.setText(instructorName);
        editInstructorPhone.setText(instructorPhone);
        editInstructorEmail.setText(instructorEmail);
        editCourseNotes.setText(courseNotes);
        repositoryCourse = new Repository(getApplication());

        if (courseID > -1) {
            editStatus.setSelection(statusItem);
            replaceFragment(new FragmentCourseDetails());
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("cID", this.courseID);
        bundle.putString("cName", this.courseName);
        bundle.putString("cStart", this.courseStart);
        bundle.putString("cEnd", this.courseEnd);
        bundle.putString("status",this.status);
        bundle.putString("cIName", this.instructorName);
        bundle.putString("cIPhone", this.instructorPhone);
        bundle.putString("cIEmail", this.instructorEmail);
        bundle.putString("cCNotes", this.courseNotes);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.courseFrag, fragment);
        fragmentTransaction.commit();
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem dropCourse = menu.findItem(R.id.dropCourse);
        MenuItem editCourseDetails = menu.findItem(R.id.editCourseDetails);
        MenuItem shareCourseNotes = menu.findItem(R.id.shareCourseNotes);
        MenuItem viewAssessmentList = menu.findItem(R.id.viewAssessmentList);
        if (courseID == -1) {
            dropCourse.setVisible(false);
            editCourseDetails.setVisible(false);
            shareCourseNotes.setVisible(false);
            viewAssessmentList.setVisible(false);
        }
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.dropCourse:
                for (Course courses : repositoryCourse.getAllCourses()) {
                    if (courses.getCourseID() == courseID) currentCourse = courses;
                }
                repositoryCourse.delete(currentCourse);
                Toast.makeText(CourseDetails.this, "Course has been dropped", Toast.LENGTH_LONG).show();
                finish();
                return true;
            case R.id.editCourseDetails:
                getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.courseFrag)).commit() ;
                return true;
            case R.id.shareCourseNotes:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, courseNotes);
                sendIntent.putExtra(Intent.EXTRA_TITLE, courseName + " notes");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
            case R.id.viewAssessmentList:
                Intent addCourseDetailsIntent = new Intent(CourseDetails.this, AssessmentList.class);
                addCourseDetailsIntent.putExtra("courseItemID", courseID);
                addCourseDetailsIntent.putExtra("courseName", courseName);
                startActivity(addCourseDetailsIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public int statusSpinner(Spinner spinner) {
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this, R.array.statusArray, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(statusAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                statusItem = editStatus.getSelectedItemPosition();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return spinner.getSelectedItemPosition();
    }

    public void saveCourse(View view) {
        Course course;
        if (courseID == -1) {
            int newID;
            if ((repositoryCourse.getAllCourses().size() - 1) < 0) {
                newID = 1;
            } else {
                newID = repositoryCourse.getAllCourses().get(repositoryCourse.getAllCourses().size() - 1).getCourseID() + 1;
            }
            course = new Course(newID, termItemID, editCourseName.getText().toString(), editCourseStart.getText().toString(), editCourseEnd.getText().toString(), statusItem, editStatus.getSelectedItem().toString(), editInstructorName.getText().toString(), editInstructorPhone.getText().toString(), editInstructorEmail.getText().toString(), editCourseNotes.getText().toString());
            repositoryCourse.insert(course);
            Toast.makeText(CourseDetails.this, "Course has been added", Toast.LENGTH_LONG).show();
        } else {
            course = new Course(courseID, termItemID, editCourseName.getText().toString(), editCourseStart.getText().toString(), editCourseEnd.getText().toString(), statusItem, editStatus.getSelectedItem().toString(), editInstructorName.getText().toString(), editInstructorPhone.getText().toString(), editInstructorEmail.getText().toString(), editCourseNotes.getText().toString());
            repositoryCourse.update(course);
            Toast.makeText(CourseDetails.this, "Course has been updated", Toast.LENGTH_LONG).show();
        }
        finish();
    }
}