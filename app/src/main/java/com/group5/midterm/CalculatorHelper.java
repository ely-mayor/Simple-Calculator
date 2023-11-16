package com.group5.midterm;

import android.util.TypedValue;
import android.widget.TextView;
import androidx.core.widget.TextViewCompat;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import java.text.DecimalFormat;
public class CalculatorHelper {
        public static void adjustTextSize(TextView displayView, float maxSize, float minimumSize, int stepGranularity) {
            if (displayView.getText().length() > 6) {
                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
                        displayView,
                        Math.round(minimumSize), // minSize
                        Math.round(maxSize), // maxSize
                        stepGranularity,  // stepGranularity
                        TypedValue.COMPLEX_UNIT_SP // unit
                );
            } else if (displayView.getText().length() <= 7) {
                TextViewCompat.setAutoSizeTextTypeWithDefaults(
                        displayView,
                        TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE
                );
                displayView.setTextSize(maxSize);
            }
        }
        public static String removeDecimalIfWholeNumber(double number) {
            if (number % 1 == 0) {
                DecimalFormat decimalFormat = new DecimalFormat("0");
                return decimalFormat.format(number);
            } else {
                return String.valueOf(number);
            }
        }
        public static String evaluateExpression(String data) {
            try {
                Context context = Context.enter();
                Scriptable scriptable = context.initStandardObjects();
                context.setOptimizationLevel(-1);
                String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
                if (finalResult.endsWith(".0")) {
                    finalResult = finalResult.replace(".0", "");
                }
                return finalResult;
            } catch (Exception e) {
                return "Err";
            }
        }
    }


