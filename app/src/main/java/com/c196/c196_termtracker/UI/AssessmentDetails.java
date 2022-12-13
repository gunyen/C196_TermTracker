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
import com.c196.c196_termtracker.Entity.Assessment;
import com.c196.c196_termtracker.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AssessmentDetails extends AppCompatActivity {
    EditText editAssessmentName;
    EditText editAssessmentEnd;
    int assessmentID;
    int courseItemID;
    String assessmentName;
    String assessmentEnd;
    SwitchCompat endSwitch;
    Repository repositoryAssessment;
    Assessment currentAssessment;
    String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        editAssessmentName = findViewById(R.id.editAssessmentName);
        editAssessmentEnd = findViewById(R.id.editAssessmentEnd);
        GetDate getEndDate = new GetDate(editAssessmentEnd, AssessmentDetails.this);
        getEndDate.selectDate();
        assessmentID = getIntent().getIntExtra("assessmentID", -1);
        assessmentName = getIntent().getStringExtra("assessmentName");
        assessmentEnd = getIntent().getStringExtra("assessmentEnd");
        courseItemID = getIntent().getIntExtra("courseItemID", -1);
        editAssessmentName.setText(assessmentName);
        editAssessmentEnd.setText(assessmentEnd);
        repositoryAssessment = new Repository(getApplication());


        endSwitch = findViewById(R.id.alertAssessmentEnd);
        endSwitch.setVisibility(View.INVISIBLE);


        if (assessmentID > -1) {

            Button saveAssessment = findViewById(R.id.saveAssessment);
            editAssessmentName.setEnabled(false);
            editAssessmentEnd.setEnabled(false);
            endSwitch.setVisibility(View.VISIBLE);
            saveAssessment.setVisibility(View.INVISIBLE);


        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("aName", assessmentName);
        bundle.putString("aEnd", assessmentEnd);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.assessmentFrag, fragment);
        fragmentTransaction.commit();
    }



    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem dropCourse = menu.findItem(R.id.dropAssessment);
        MenuItem editAssessmentDetails = menu.findItem(R.id.editAssessmentDetails);
        if (assessmentID == -1) {
            dropCourse.setVisible(false);
            editAssessmentDetails.setVisible(false);
        }
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_assessment_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.dropAssessment:
                for (Assessment assessment : repositoryAssessment.getAllAssessments()) {
                    if (assessment.getAssessmentID() == assessmentID) currentAssessment = assessment;
                }
                repositoryAssessment.delete(currentAssessment);
                Toast.makeText(AssessmentDetails.this, "Course has been dropped", Toast.LENGTH_LONG).show();
                finish();
                return true;
            case R.id.editAssessmentDetails:
                editAssessmentName = findViewById(R.id.editAssessmentName);
                editAssessmentEnd = findViewById(R.id.editAssessmentEnd);
                Button saveAssessment = findViewById(R.id.saveAssessment);
                editAssessmentName.setEnabled(true);
                editAssessmentEnd.setEnabled(true);
                saveAssessment.setVisibility(View.VISIBLE);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveAssessment(View view) {
        Assessment assessment;
        if (assessmentID == -1) {
            int newID;
            if ((repositoryAssessment.getAllAssessments().size() - 1) < 0) {
                newID = 1;
            } else {
                newID = repositoryAssessment.getAllAssessments().get(repositoryAssessment.getAllAssessments().size() - 1).getAssessmentID() + 1;
            }
            assessment = new Assessment(newID, editAssessmentName.getText().toString(), editAssessmentEnd.getText().toString(), courseItemID);
            repositoryAssessment.insert(assessment);
            Toast.makeText(AssessmentDetails.this, "Course has been added", Toast.LENGTH_LONG).show();
        } else {
            assessment = new Assessment(assessmentID, editAssessmentName.getText().toString(), editAssessmentEnd.getText().toString(), courseItemID);
            repositoryAssessment.update(assessment);
            Toast.makeText(AssessmentDetails.this, "Course has been updated", Toast.LENGTH_LONG).show();
        }
        SharedPreferences assessmentEndPreferences = getSharedPreferences("save1", MODE_PRIVATE);
        endSwitch.setChecked(assessmentEndPreferences.getBoolean("value", false));
        finish();
    }
}