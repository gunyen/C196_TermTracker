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
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAssessmentDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAssessmentDetails extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentAssessmentDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAssessmentDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAssessmentDetails newInstance(String param1, String param2) {
        FragmentAssessmentDetails fragment = new FragmentAssessmentDetails();
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

    String sAName;
    String sAType;
    String sAEnd;
    String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_assessment_details, container, false);
        TextView aName = view.findViewById(R.id.eAssessmentName);
        TextView aType = view.findViewById(R.id.eAssessmentType);
        TextView aEnd = view.findViewById(R.id.eAssessmentEnd);
        aName.setText(this.getArguments().getString("aName"));
        aType.setText(this.getArguments().getString("aType"));
        aEnd.setText(this.getArguments().getString("aEnd"));
        sAName = aName.getText().toString();
        sAType = aType.getText().toString();
        sAEnd = aEnd.getText().toString();
        SwitchCompat endSwitch = view.findViewById(R.id.alertAssessmentEnd);
        SharedPreferences assessmentEndPreferences = getActivity().getSharedPreferences(sAName, MODE_PRIVATE);
        endSwitch.setChecked(assessmentEndPreferences.getBoolean("value", false));
        endSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (endSwitch.isChecked()) {
                    // When switch is checked
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(sAName, MODE_PRIVATE).edit();
                    editor.putBoolean("value", true);
                    editor.apply();
                    endSwitch.setChecked(true);
                    setAlert(true);
                    Toast.makeText(getActivity(), "Alert Scheduled", Toast.LENGTH_LONG).show();
                } else {
                    // When switch is unchecked
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(sAName, MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);
                    editor.apply();
                    endSwitch.setChecked(false);
                    setAlert(false);
                    Toast.makeText(getActivity(), "Alert Canceled", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    public void setAlert(Boolean toggle) {
        Date myDate = null;
        try {
            myDate = sdf.parse(sAEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long trigger = myDate.getTime();
        Intent intent = new Intent(getActivity(), MyReceiver.class);
        intent.putExtra("key", sAName + " ends today");
        PendingIntent sender = PendingIntent.getBroadcast(getActivity(), MainActivity.numAlert++, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        if (toggle) {
            alarmManager.set(AlarmManager.RTC_WAKEUP,trigger,sender);
        } else {
            alarmManager.cancel(sender);
        }
    }
}