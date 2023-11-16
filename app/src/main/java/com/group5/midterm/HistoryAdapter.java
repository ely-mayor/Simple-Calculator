package com.group5.midterm;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<Calculation> calculationList;

    public HistoryAdapter(List<Calculation> calculationList) {
        this.calculationList = calculationList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate your item layout here
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Bind data to views in your item layout
        Calculation calculation = calculationList.get(position);
        Log.d("HistoryAdapter", "Binding data at position: " + position);
        holder.expressionTextView.setText(calculation.getExpression());
        holder.resultTextView.setText(calculation.getResult());
    }

    @Override
    public int getItemCount() {
        return calculationList.size();
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView expressionTextView;
        TextView resultTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            // Initialize your views here
            expressionTextView = itemView.findViewById(R.id.expressionTextView);
            resultTextView = itemView.findViewById(R.id.resultTextView);
        }
    }
}
