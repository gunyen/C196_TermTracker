package com.c196.c196_termtracker.UI;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class GetDate extends AppCompatActivity {
    EditText editText;
    Context current;
    DatePickerDialog.OnDateSetListener date;
    final Calendar myCalendar = Calendar.getInstance();
    String myFormat;
    SimpleDateFormat sdf;

    public GetDate(EditText editText,Context current) {
        this.editText = editText;
        this.current = current;

    }

    public void selectDate() {
        myFormat="MM/dd/yy";
        sdf=new SimpleDateFormat(myFormat, Locale.US);
        editText.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String info=editText.getText().toString();
                if(info.equals(""))info="01/01/22";
                try {
                    myCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(current,date,myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        date=new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel();
            }
        };

    }
    private void updateLabel(){
        editText.setText(sdf.format(myCalendar.getTime()));
    }
}
