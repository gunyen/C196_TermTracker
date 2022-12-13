package com.c196.c196_termtracker.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.c196.c196_termtracker.Entity.Course;
import com.c196.c196_termtracker.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder>{

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;

        private CourseViewHolder(View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.courseItemView);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course current = mCourse.get(position);
                    Intent intent = new Intent(context, CourseDetails.class);
                    intent.putExtra("courseID", current.getCourseID());
                    intent.putExtra("termItemID", current.getTermItemID());
                    intent.putExtra("courseName", current.getCourseName());
                    intent.putExtra("courseStart", current.getStartDate());
                    intent.putExtra("courseEnd", current.getEndDate());
                    intent.putExtra("statusItem", current.getStatusItem());
                    intent.putExtra("status", current.getStatus());
                    intent.putExtra("instructorName", current.getInstructorName());
                    intent.putExtra("instructorPhone", current.getInstructorPhone());
                    intent.putExtra("instructorEmail", current.getInstructorEmail());
                    intent.putExtra("courseNotes", current.getCourseNotes());

                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Course> mCourse;
    private final Context context;
    private final LayoutInflater mInflater;

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_item_view, parent, false);
        return new CourseAdapter.CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if (mCourse != null) {
            Course current = mCourse.get(position);
            String name = current.getCourseName();
            holder.courseItemView.setText(name);
        }
    }

    public void setCourse(List<Course> course) {
        mCourse = course;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCourse != null) {
            return mCourse.size();
        } else {
            return 0;
        }
    }
}
