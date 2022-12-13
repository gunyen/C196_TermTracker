package com.c196.c196_termtracker.UI;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.c196.c196_termtracker.Database.Repository;
import com.c196.c196_termtracker.Entity.Course;
import com.c196.c196_termtracker.Entity.Term;
import com.c196.c196_termtracker.R;

public class TermDetails extends AppCompatActivity {
    EditText editTermName;
    EditText editTermStart;
    EditText editTermEnd;
    Button saveTermButton;
    int termID;
    String termName;
    String termStart;
    String termEnd;
    Repository repository;
    Term currentTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        editTermName = findViewById(R.id.editTermName);
        editTermStart = findViewById(R.id.editTermStart);
        GetDate getStartDate = new GetDate(editTermStart, TermDetails.this);
        getStartDate.selectDate();
        termStart = getIntent().getStringExtra("termStart");
        editTermStart.setText(termStart);
        editTermEnd = findViewById(R.id.editTermEnd);
        GetDate getEndDate = new GetDate(editTermEnd, TermDetails.this);
        getEndDate.selectDate();
        termEnd = getIntent().getStringExtra("termEnd");
        editTermEnd.setText(termEnd);
        termID = getIntent().getIntExtra("termID", -1);
        termName = getIntent().getStringExtra("termName");
        editTermName.setText(termName);
        repository = new Repository(getApplication());
        if (termID > -1) {
            replaceFragment(new FragmentTermDetails());
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("tID", this.termID);
        bundle.putString("tName", this.termName);
        bundle.putString("tStart", this.termStart);
        bundle.putString("tEnd", this.termEnd);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem dropTerm = menu.findItem(R.id.dropTerm);
        MenuItem viewCourseList = menu.findItem(R.id.viewCourseList);
        if (termID == -1) {
            dropTerm.setVisible(false);
            viewCourseList.setVisible(false);
        }
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_term_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.dropTerm:
                for (Term term : repository.getAllTerms()) {
                    if (term.getTermID() == termID) currentTerm = term;
                }
                int numCourses = 0;
                for (Course course : repository.getAllCourses()) {
                    if (course.getCourseID() == termID) ++numCourses;
                }
                if (numCourses == 0) {
                    repository.delete(currentTerm);
                    Toast.makeText(TermDetails.this, currentTerm.getTermName() + " was deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(TermDetails.this, currentTerm.getTermName() + " cannot be deleted due to registered courses", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.viewCourseList:
                Intent sendTermID = new Intent(this, CourseList.class);
                sendTermID.putExtra("courseTermID",termID);
                sendTermID.putExtra("courseTermName", termName);
                this.startActivity(sendTermID);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveTerm(View view) {
        Term term;
        if (termID == -1) {
            int newID;
            if ((repository.getAllTerms().size() - 1) < 0) {
                newID = 1;
            } else {
                newID = repository.getAllTerms().get(repository.getAllTerms().size() - 1).getTermID() + 1;
            }
            term = new Term(newID, editTermName.getText().toString(), editTermStart.getText().toString(), editTermEnd.getText().toString());
            repository.insert(term);
            saveTermButton = findViewById(R.id.saveTermButton);
            editTermName.setEnabled(false);
            editTermStart.setEnabled(false);
            editTermEnd.setEnabled(false);
            saveTermButton.setVisibility(View.INVISIBLE);
            Toast.makeText(TermDetails.this, "Term has been Added", Toast.LENGTH_LONG).show();
        } else {
            term = new Term(termID, editTermName.getText().toString(), editTermStart.getText().toString(), editTermEnd.getText().toString());
            repository.update(term);
            Toast.makeText(TermDetails.this, "Term has been updated", Toast.LENGTH_LONG).show();
        }
        finish();
    }
}