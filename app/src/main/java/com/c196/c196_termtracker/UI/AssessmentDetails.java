package com.c196.c196_termtracker.UI;

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

public class AssessmentDetails extends AppCompatActivity {
    EditText editAssessmentName;
    EditText editAssessmentEnd;
    Spinner editType;
    int assessmentID;
    int courseItemID;
    int typeID;
    String assessmentName;
    String type;
    String assessmentEnd;
    Repository repositoryAssessment;
    Assessment currentAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        editAssessmentName = findViewById(R.id.editAssessmentName);
        editType = findViewById(R.id.editAssessmentType);
        editAssessmentEnd = findViewById(R.id.editAssessmentEnd);
        GetDate getEndDate = new GetDate(editAssessmentEnd, AssessmentDetails.this);
        getEndDate.selectDate();
        assessmentID = getIntent().getIntExtra("assessmentID", -1);
        assessmentName = getIntent().getStringExtra("assessmentName");
        assessmentEnd = getIntent().getStringExtra("assessmentEnd");
        typeID = getIntent().getIntExtra("typeID", -1);
        type = getIntent().getStringExtra("type");
        courseItemID = getIntent().getIntExtra("courseItemID", -1);
        editType.setSelection(typeSpinner(editType));
        editAssessmentName.setText(assessmentName);
        editAssessmentEnd.setText(assessmentEnd);
        repositoryAssessment = new Repository(getApplication());

        if (assessmentID > -1) {
            editType.setSelection(typeID);
            replaceFragment(new FragmentAssessmentDetails());
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("aName", this.assessmentName);
        bundle.putString("aType", this.type);
        bundle.putString("aEnd", this.assessmentEnd);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.assessmentFrag, fragment);
        fragmentTransaction.commit();
    }

    public int typeSpinner(Spinner spinner) {
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.typeArray, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(typeAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeID = editType.getSelectedItemPosition();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return spinner.getSelectedItemPosition();
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
                getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.assessmentFrag)).commit() ;
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
            assessment = new Assessment(newID, editAssessmentName.getText().toString(), typeID, editType.getSelectedItem().toString(),editAssessmentEnd.getText().toString(), courseItemID);
            repositoryAssessment.insert(assessment);
            Toast.makeText(AssessmentDetails.this, "Assessment has been added", Toast.LENGTH_LONG).show();
        } else {
            assessment = new Assessment(assessmentID, editAssessmentName.getText().toString(), typeID, editType.getSelectedItem().toString(),editAssessmentEnd.getText().toString(), courseItemID);
            repositoryAssessment.update(assessment);
            Toast.makeText(AssessmentDetails.this, "Assessment has been updated", Toast.LENGTH_LONG).show();
        }
//        SharedPreferences assessmentEndPreferences = getSharedPreferences("save1", MODE_PRIVATE);
//        endSwitch.setChecked(assessmentEndPreferences.getBoolean("value", false));
        finish();
    }
}