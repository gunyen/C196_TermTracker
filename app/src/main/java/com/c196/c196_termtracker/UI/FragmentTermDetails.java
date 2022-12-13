package com.c196.c196_termtracker.UI;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.c196.c196_termtracker.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTermDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTermDetails extends Fragment {
    TextView EtName;
    TextView EtStart;
    TextView EtEnd;
    int tID;
    String tName;
    String tStart;
    String tEnd;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentTermDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTermDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTermDetails newInstance(String param1, String param2) {
        FragmentTermDetails fragment = new FragmentTermDetails();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_term_details, container, false);
        TextView tName = view.findViewById(R.id.tName);
        TextView tStart = view.findViewById(R.id.tStart);
        TextView tEnd = view.findViewById(R.id.tEnd);
        tName.setText(this.getArguments().getString("tName"));
        tStart.setText(this.getArguments().getString("tStart"));
        tEnd.setText(this.getArguments().getString("tEnd"));
        return view;
    }
}