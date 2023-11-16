package com.group5.midterm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.TextViewCompat;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView displayView;
    private final float MAX_TEXT_SIZE = 85f;
    private final float MINIMUM_TEXT_SIZE = 20f;
    List<Calculation> calculationHistory = new ArrayList<>();

    private Toast toast;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // Start SettingsActivity
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.action_about) {
            // Start AboutActivity
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        } else if (id == R.id.action_history) {
            // Start HistoryActivity
            startActivity(new Intent(this, HistoryActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Check if there's a selected calculation in HistoryActivity
        if (HistoryActivity.selectedCalculation != null) {
            // Update your displayView with the selected data
            displayView.setText(HistoryActivity.selectedCalculation.getExpression());

            // Reset the selected calculation to avoid updating displayView multiple times
            HistoryActivity.selectedCalculation = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve the saved dark mode preference
        SharedPreferences preferences = getSharedPreferences(SettingsActivity.PREF_NAME, MODE_PRIVATE);
        boolean darkModeEnabled = preferences.getBoolean(SettingsActivity.DARK_MODE_KEY, false);

        // Set the theme based on the saved preference
        if (darkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // Set the night mode
        UiModeManager uiModeManager = (UiModeManager) getSystemService(Context.UI_MODE_SERVICE);
        uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_YES);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the title for the action bar
        getSupportActionBar().setTitle("");

        displayView = (TextView) findViewById(R.id.input_tv);

        // Get all the buttons view
        final LinearLayout buttonlayout = findViewById(R.id.button_layout);

        for (int i = 0; i < buttonlayout.getChildCount(); i++) {
            View childView = buttonlayout.getChildAt(i);

            if (childView instanceof LinearLayout) {
                LinearLayout linearLayout = (LinearLayout) childView;

                for (int j = 0; j < linearLayout.getChildCount(); j++) {
                    View buttonView = linearLayout.getChildAt(j);

                    if (buttonView instanceof MaterialButton) {
                        MaterialButton materialButton = (MaterialButton) buttonView;

                        MaterialButton ce = findViewById(R.id.button_c);

                        materialButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String buttonText = materialButton.getText().toString();
                                System.out.println("button text: " + buttonText);

                                String expression = displayView.getText().toString();

                                if (buttonText.equals("=")) {
                                    if (expression.equals("0") && !expression.isEmpty()) {
                                        return;
                                    } else {
                                        String arithmetic = displayView.getText().toString();

                                        arithmetic = arithmetic.replace("x", "*");
                                        arithmetic = arithmetic.replace("%", "/100");
                                        arithmetic = arithmetic.replace("÷", "/");

                                        String finalResult = CalculatorHelper.evaluateExpression(arithmetic);

                                        if (!finalResult.equals("Err")) {
                                            // Create a Calculation object and add it to the history
                                            CalculationHistorySingleton historySingleton = CalculationHistorySingleton.getInstance();
                                            historySingleton.addCalculation(new Calculation(R.mipmap.ic_launcher,expression, finalResult));


                                            displayView.setText(finalResult);


                                            CalculatorHelper.adjustTextSize(displayView,MAX_TEXT_SIZE,MINIMUM_TEXT_SIZE, 2);
                                            ce.setText("AC");
                                        }
                                    }
                                } else if (buttonText.equals("CE")) {
                                    if (expression.equals("Err")) {
                                        displayView.setText("0");
                                        return;
                                    }
                                    if (expression.length() == 2 && expression.startsWith("-")) {
                                        displayView.setText("0");
                                        return; // Exit the method early, no need to continue processing
                                    }
                                    try {
                                        expression = expression.substring(0, expression.length() - 1);
                                        displayView.setText(expression);
                                        CalculatorHelper.adjustTextSize(displayView,MAX_TEXT_SIZE,MINIMUM_TEXT_SIZE, 2);
                                    } catch (NumberFormatException | ArithmeticException e) {
                                        // Handle the case where parsing fails
                                        e.printStackTrace();
                                        System.out.println(e.getMessage());
                                    }
                                    if (expression.isEmpty()) {
                                        displayView.setText("0");
                                    }
                                } else if (buttonText.equals("AC")) {
                                    TextViewCompat.setAutoSizeTextTypeWithDefaults(
                                            displayView,
                                            TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE
                                    );
                                    displayView.setTextSize(MAX_TEXT_SIZE);
                                    displayView.setText("0");
                                    ce.setText("CE");
                                    return;
                                } else if (buttonText.equals("+")) {
                                    if (expression.equals("0") && !expression.isEmpty()) {
                                        displayView.setText("0+");
                                        return;
                                    } else {
                                        expression += buttonText;
                                        displayView.setText(expression);
                                        CalculatorHelper.adjustTextSize(displayView,MAX_TEXT_SIZE,MINIMUM_TEXT_SIZE, 2);
                                        ce.setText("CE");
                                    }
                                } else if (buttonText.equals("-")) {
                                    if (expression.equals("0") && !expression.isEmpty()) {
                                        displayView.setText("0-");
                                        return;
                                    } else {
                                        expression += buttonText;
                                        displayView.setText(expression);
                                        CalculatorHelper.adjustTextSize(displayView,MAX_TEXT_SIZE,MINIMUM_TEXT_SIZE, 2);
                                        ce.setText("CE");
                                    }
                                }
                                else if (buttonText.equals("±")) {
                                    if (!expression.equals("0") && !expression.isEmpty()) {
                                        try {
                                            double num = Double.parseDouble(expression);
                                            num = -num;
                                            expression = CalculatorHelper.removeDecimalIfWholeNumber(num);
                                            displayView.setText(String.valueOf(expression));

                                        } catch (NumberFormatException e) {
                                            e.getMessage();
                                        }
                                    }
                                    if (expression.equals("0")) {
                                        return;
                                    }
                                }
                                else if (buttonText.equals("x")) {
                                    if (expression.equals("0") && !expression.isEmpty()) {
                                        displayView.setText("0x");
                                        return;
                                    } else {
                                        expression += buttonText;
                                        displayView.setText(expression);
                                        CalculatorHelper.adjustTextSize(displayView,MAX_TEXT_SIZE,MINIMUM_TEXT_SIZE, 2);
                                        ce.setText("CE");
                                    }
                                } else if (buttonText.equals("%")) {
                                    if (expression.equals("0") && !expression.isEmpty()) {
                                        displayView.setText("0%");
                                        return;
                                    } else {
                                        expression += buttonText;
                                        displayView.setText(expression);
                                        CalculatorHelper.adjustTextSize(displayView,MAX_TEXT_SIZE,MINIMUM_TEXT_SIZE, 2);
                                        ce.setText("CE");
                                    }
                                } else if (buttonText.equals("÷")) {
                                    if (expression.equals("0") && !expression.isEmpty()) {
                                        displayView.setText("0÷");
                                        return;
                                    } else {
                                        expression += buttonText;
                                        displayView.setText(expression);
                                        CalculatorHelper.adjustTextSize(displayView,MAX_TEXT_SIZE,MINIMUM_TEXT_SIZE, 2);
                                        ce.setText("CE");
                                    }
                                } else if (buttonText.equals(".")) {
                                    // Check if the expression is "0"
                                    if (expression.equals("0")) {
                                        expression += buttonText;
                                    } else {
                                        expression += buttonText;
                                    }

                                    // Update display and UI
                                    displayView.setText(expression);
                                    CalculatorHelper.adjustTextSize(displayView,MAX_TEXT_SIZE,MINIMUM_TEXT_SIZE, 2);
                                    ce.setText("CE");

                                } else if (expression.equals("0") || expression.equals("Err")) {
                                    displayView.setText(buttonText);
                                }
                                else {
                                    try {
                                        if (expression.length() > 14) {
                                            if (toast != null) {
                                                toast.cancel();
                                            }

                                            // Display a Toast message indicating that the limit is reached
                                            toast = Toast.makeText(getApplicationContext(), "Character limit reached!", Toast.LENGTH_SHORT);
                                            toast.show();
                                            return;
                                        }
                                        expression += buttonText;
                                        displayView.setText(expression);
                                        CalculatorHelper.adjustTextSize(displayView,MAX_TEXT_SIZE,MINIMUM_TEXT_SIZE, 2);

                                        // Display the formatted expression on the displayView
                                    } catch (NumberFormatException | ArithmeticException e) {
                                        // Handle the case where parsing fails
                                        e.printStackTrace();
                                        System.out.println(e.getMessage());

                                    }
                                }
                            }
                        });

                    }
                }
            }

        }
    }

}