package com.c196.c196_termtracker.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.c196.c196_termtracker.Entity.Assessment;
import com.c196.c196_termtracker.R;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder>{

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentTextView;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentTextView = itemView.findViewById(R.id.assessmentTextView);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessment.get(position);
                    Intent intent = new Intent(context, AssessmentDetails.class);
                    intent.putExtra("assessmentID", current.getAssessmentID());
                    intent.putExtra("assessmentName", current.getAssessmentName());
                    intent.putExtra("typeID", current.getTypeID());
                    intent.putExtra("type", current.getType());
                    intent.putExtra("assessmentEnd", current.getEndDate());
                    intent.putExtra("courseItemID", current.getCourseItemID());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Assessment> mAssessment;
    private final Context context;
    private final LayoutInflater mInflater;

    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_item_view, parent, false);
        return new AssessmentAdapter.AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if (mAssessment != null) {
            Assessment current = mAssessment.get(position);
            String name = current.getAssessmentName() + " - " +
                    current.getType();
            holder.assessmentTextView.setText(name);
        }
    }

    public void setAssessment(List<Assessment> assessment) {
        mAssessment = assessment;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAssessment != null) {
            return mAssessment.size();
        } else {
            return 0;
        }
    }
}
