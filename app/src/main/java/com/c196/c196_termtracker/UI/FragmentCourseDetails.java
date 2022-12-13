package com.c196.c196_termtracker.UI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.c196.c196_termtracker.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCourseDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCourseDetails extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentCourseDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentCourseDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCourseDetails newInstance(String param1, String param2) {
        FragmentCourseDetails fragment = new FragmentCourseDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    String courseName;
    String courseStart;
    String courseEnd;
    String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_details, container, false);
        TextView cName = view.findViewById(R.id.eCourseName);
        TextView cStart = view.findViewById(R.id.eCourseStart);
        TextView cEnd = view.findViewById(R.id.eCourseEnd);
        TextView status = view.findViewById(R.id.eStatus);
        TextView cIName = view.findViewById(R.id.eInstructorName);
        TextView cIPhone = view.findViewById(R.id.eInstructorPhone);
        TextView cIEmail = view.findViewById(R.id.eInstructorEmail);
        TextView cCNotes = view.findViewById(R.id.eCourseNotes);
        cName.setText(this.getArguments().getString("cName"));
        cStart.setText(this.getArguments().getString("cStart"));
        cEnd.setText(this.getArguments().getString("cEnd"));
        status.setText(this.getArguments().getString("status"));
        cIName.setText(this.getArguments().getString("cIName"));
        cIPhone.setText(this.getArguments().getString("cIPhone"));
        cIEmail.setText(this.getArguments().getString("cIEmail"));
        cCNotes.setText(this.getArguments().getString("cCNotes"));
        courseName = cName.getText().toString();
        courseStart = cStart.getText().toString();
        courseEnd = cEnd.getText().toString();

        SwitchCompat startSwitch = view.findViewById(R.id.alertCourseStart);
        SharedPreferences courseStartPreferences = this.getActivity().getSharedPreferences(courseName, MODE_PRIVATE);
        startSwitch.setChecked(courseStartPreferences.getBoolean("value", false));
        startSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (startSwitch.isChecked()) {
                    // When switch is checked
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(courseName, MODE_PRIVATE).edit();
                    editor.putBoolean("value", true);
                    editor.apply();
                    startSwitch.setChecked(true);
                    setAlertStart(true);
                    Toast.makeText(getActivity(), "Alert Scheduled", Toast.LENGTH_SHORT).show();
                } else {
                    // When switch is unchecked
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(courseName, MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);
                    editor.apply();
                    startSwitch.setChecked(false);
                    setAlertStart(false);
                    Toast.makeText(getActivity(), "Alert Canceled", Toast.LENGTH_SHORT).show();

                }
            }
        });
        SwitchCompat endSwitch = view.findViewById(R.id.alertCourseEnd);
        SharedPreferences courseEndPreferences = getActivity().getSharedPreferences(courseName+1, MODE_PRIVATE);
        endSwitch.setChecked(courseEndPreferences.getBoolean("value", false));
        endSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (endSwitch.isChecked()) {
                    // When switch is checked
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(courseName+1, MODE_PRIVATE).edit();
                    editor.putBoolean("value", true);
                    editor.apply();
                    endSwitch.setChecked(true);
                    setAlertEnd(true);
                    Toast.makeText(getActivity(), "Alert Scheduled", Toast.LENGTH_LONG).show();
                } else {
                    // When switch is unchecked
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(courseName+1, MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);
                    editor.apply();
                    setAlertEnd(false);
                    Toast.makeText(getActivity(), "Alert Canceled", Toast.LENGTH_LONG).show();

                }
            }
        });
        return view;
    }
    public void setAlertStart(Boolean toggle) {
        Date myDate = null;
        try {
            myDate = sdf.parse(courseStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long trigger = myDate.getTime();
        Intent intent = new Intent(getActivity(), MyReceiver.class);
        intent.putExtra("key", courseName + " starts today");
        PendingIntent sender = PendingIntent.getBroadcast(getActivity(), MainActivity.numAlert++, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        if (toggle) {
            alarmManager.set(AlarmManager.RTC_WAKEUP,trigger,sender);
        } else {
            alarmManager.cancel(sender);
        }
    }

    public void setAlertEnd(Boolean toggle) {
        Date myDate = null;
        try {
            myDate = sdf.parse(courseEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long trigger = myDate.getTime();
        Intent intent = new Intent(getActivity(), MyReceiver.class);
        intent.putExtra("key", courseName + " ends today");
        PendingIntent sender = PendingIntent.getBroadcast(getActivity(), MainActivity.numAlert++, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        if (toggle) {
            alarmManager.set(AlarmManager.RTC_WAKEUP,trigger,sender);
        } else {
            alarmManager.cancel(sender);
        }
    }
}