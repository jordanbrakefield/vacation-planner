package com.jordanbrakefield.vacationplanner.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jordanbrakefield.vacationplanner.R;
import com.jordanbrakefield.vacationplanner.entities.Vacation;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {
    private List<Vacation> mVacations;
    private final LayoutInflater mInflater;
    private final Context context;

    public ReportAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }
    public class ReportViewHolder extends RecyclerView.ViewHolder {
        private final TextView ReportItemView1;
        private final TextView ReportItemView2;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            ReportItemView1 = itemView.findViewById(R.id.textView4);
            ReportItemView2 = itemView.findViewById(R.id.textView5);

        }
    }
    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.report_list_item, parent, false);
        return new ReportViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        if(mVacations != null){
            Vacation current = mVacations.get(position);
            String title = current.getVacationTitle();
            String hotel = current.getHotelName();
            holder.ReportItemView1.setText(title);
            holder.ReportItemView2.setText((hotel));
        }
        else{
            holder.ReportItemView1.setText("No vacation title");
            holder.ReportItemView2.setText("No hotel info");
        }
    }

    @Override
    public int getItemCount() {
        if(mVacations != null){
            return mVacations.size();
        }
        else return 0;

    }

    public void setVacations(List<Vacation> vacations){
        mVacations = vacations;
        notifyDataSetChanged();
    }


}
