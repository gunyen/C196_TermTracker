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
import com.c196.c196_termtracker.Entity.Term;
import com.c196.c196_termtracker.R;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder>{
    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;
        private TermViewHolder(View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.termTextView);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Term current = mTerm.get(position);
                    Intent intent = new Intent(context, TermDetails.class);
                    intent.putExtra("termID", current.getTermID());
                    intent.putExtra("termName", current.getTermName());
                    intent.putExtra("termStart", current.getTermStart());
                    intent.putExtra("termEnd", current.getTermEnd());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Term> mTerm;
    private final Context context;
    private final LayoutInflater mInflater;

    public TermAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_item_view, parent, false);
        return new TermViewHolder(itemView);
    }

    int termItem;

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if (mTerm != null) {
            Term current = mTerm.get(position);
            String termHolder = current.getTermName() + "\nStart Date: " +
                    current.getTermStart() + " - End Date: " +
                    current.getTermEnd() + "\n";
            holder.termItemView.setText(termHolder);
            termItem = current.getTermID();
        }
    }

    public void setTerm(List<Term> terms) {
        mTerm = terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTerm != null) {
            return mTerm.size();
        } else {
            return 0;
        }
    }

}
