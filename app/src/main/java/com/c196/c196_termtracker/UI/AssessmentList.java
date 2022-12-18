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
import com.c196.c196_termtracker.Entity.Assessment;
import com.c196.c196_termtracker.Entity.Course;
import com.c196.c196_termtracker.Entity.Term;
import com.c196.c196_termtracker.R;

import java.util.ArrayList;
import java.util.List;

public class AssessmentList extends AppCompatActivity {
    int transferCourse;
    TextView textCourseName;
    String courseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        transferCourse = getIntent().getIntExtra("courseItemID", transferCourse);
        courseName = getIntent().getStringExtra("courseName");
        // Display Database Info
        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        Repository repo = new Repository(getApplication());
        List<Assessment> assessments = new ArrayList<>();
        for (Assessment assessment : repo.getAllAssessments()) {
            if (assessment.getCourseItemID() == transferCourse) assessments.add(assessment);
        }
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAssessment(assessments);
        //Display name of course the assessments are under
        textCourseName = findViewById(R.id.courseItemName);
        textCourseName.setText(courseName);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_assessment_list, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.refreshAssessmentList:
                finish();
                startActivity(getIntent());
                return true;
            case R.id.addNewAssessment:
                transferCourse = getIntent().getIntExtra("courseItemID", transferCourse);
                Intent addAssessmentListIntent = new Intent(AssessmentList.this, AssessmentDetails.class);
                addAssessmentListIntent.putExtra("courseItemID", transferCourse);
                startActivity(addAssessmentListIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}