package com.group5.midterm;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{
    private List<Calculation> calculationList;

    private  RecyclerViewClickListener recyclerViewClickListener;

    RecyclerViewAdapter(List<Calculation> calculationList) {
        this.calculationList = calculationList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Calculation expression = calculationList.get(position);
        holder.expression.setText(expression.getExpression() + " = " + expression.getResult());
        holder.image.setBackgroundResource(expression.getImage());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               recyclerViewClickListener.onItemClick(expression);
            }
        });
    }

    @Override
    public int getItemCount() {
        return calculationList.size();
    }

    public void setOnItemClickListener(RecyclerViewClickListener<Calculation> movieClickListener) {
        this.recyclerViewClickListener = movieClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView expression;
        private ImageView image;
        private CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            expression = itemView.findViewById(R.id.expression);
            image = itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.carView);
        }
    }
}