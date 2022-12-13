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
import com.c196.c196_termtracker.Entity.Term;
import com.c196.c196_termtracker.R;

import java.util.List;

public class TermList extends AppCompatActivity {

    TextView editTermsCount;
    String termsCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Display Database Info
        RecyclerView recyclerView = findViewById(R.id.termRecyclerView);
        Repository repo = new Repository(getApplication());
        List<Term> terms = repo.getAllTerms();
        final TermAdapter adapter = new TermAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTerm(terms);
        // Terms Counter
        editTermsCount=findViewById(R.id.termsCount);
        termsCount="Terms: " + terms.size();
        editTermsCount.setText(termsCount);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_term_list, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.refreshTermList:
                finish();
                startActivity(getIntent());
                return true;
            case R.id.addNewTerm:
                Intent addTermListIntent = new Intent(TermList.this, TermDetails.class);
                startActivity(addTermListIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}