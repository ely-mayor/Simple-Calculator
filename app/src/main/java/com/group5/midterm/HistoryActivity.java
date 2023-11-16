package com.group5.midterm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private List<Calculation> calculationHistory = null;

    private RecyclerView recyclerView;

    public static Calculation selectedCalculation;
    private RecyclerViewAdapter recyclerViewAdapter;

    private TextView displayView;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle the back button click
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        displayView = (TextView) findViewById(R.id.input_tv);

        CalculationHistorySingleton historySingleton = CalculationHistorySingleton.getInstance();
        List<Calculation> calculationHistory = historySingleton.getCalculationHistory();

        // Check if the calculation history is updated
        // Perform your calculation

        // Iterate through the calculation history
        for (Calculation calculation : calculationHistory) {
            String expression = calculation.getExpression();
            String result = calculation.getResult();

            // Print or display the calculation details
            System.out.println("Expression: " + expression + ", Result: " + result);
        }

        // Button to reset history
        MaterialButton resetButton = findViewById(R.id.resetButton);

        // TextView to show empty history message
        TextView emptyHistoryTextView = findViewById(R.id.emptyHistoryTextView);

        // Check if calculation history is initially empty
        if (calculationHistory.isEmpty()) {
            // Hide the button
            resetButton.setVisibility(View.GONE);
            // Show the empty history message
            emptyHistoryTextView.setVisibility(View.VISIBLE);
        } else {
            // Show the button
            resetButton.setVisibility(View.VISIBLE);
            // Hide the empty history message
            emptyHistoryTextView.setVisibility(View.GONE);
        }
        // Set a listener for the reset button
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear the calculation history
                calculationHistory.clear();

                // Notify the adapter that the data set has changed
                recyclerViewAdapter.notifyDataSetChanged();

                // Hide the button
                resetButton.setVisibility(View.GONE);

                // Show the empty history message
                emptyHistoryTextView.setVisibility(View.VISIBLE);
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(calculationHistory);
        recyclerView.setAdapter(recyclerViewAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewClickListener<Calculation>() {
            @Override
            public void onItemClick(Calculation data) {
                Toast.makeText(HistoryActivity.this, "Successfully revert", Toast.LENGTH_SHORT).show();

                // Update UI on the main thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        selectedCalculation = data;
                        finish();  // Close the HistoryActivity
                    }
                });

            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);

        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);

        // Set the title for this activity
        toolbarTitle.setText("History");

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
