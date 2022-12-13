package com.c196.c196_termtracker.UI;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.c196.c196_termtracker.R;

public class MainActivity extends AppCompatActivity {

    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void viewTerms(View view) {
        Intent addTermIntent = new Intent(MainActivity.this, TermList.class);
        startActivity(addTermIntent);
    }
}