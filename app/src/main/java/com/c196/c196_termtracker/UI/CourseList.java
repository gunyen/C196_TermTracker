package com.c196.c196_termtracker.UI;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.c196.c196_termtracker.Database.Repository;
import com.c196.c196_termtracker.Entity.Course;
import com.c196.c196_termtracker.R;

import java.util.ArrayList;
import java.util.List;

public class CourseList extends AppCompatActivity {
    int transfer;
    TextView textTermName;
    String termName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        transfer = getIntent().getIntExtra("courseTermID", transfer);
        termName = getIntent().getStringExtra("courseTermName");
        // Display Database Info
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        Repository repo = new Repository(getApplication());
        List<Course> courses = new ArrayList<>();
            for (Course course : repo.getAllCourses()) {
                if (course.getTermItemID() == transfer) courses.add(course);
            }
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCourse(courses);
        // Term Name
        textTermName = findViewById(R.id.courseCount);
        textTermName.setText(termName);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_list, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.refreshCourseList:
                finish();
                startActivity(getIntent());
                return true;
            case R.id.addNewCourse:
                transfer = getIntent().getIntExtra("courseTermID", transfer);
                Intent addCourseListIntent = new Intent(this, CourseDetails.class);
                addCourseListIntent.putExtra("termItemID", transfer);
                this.startActivity(addCourseListIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}