package com.group5.midterm;

import java.util.ArrayList;
import java.util.List;

public class CalculationHistorySingleton {
    private static CalculationHistorySingleton instance;
    private List<Calculation> calculationHistory;

    private CalculationHistorySingleton() {
        calculationHistory = new ArrayList<>();
    }

    public static CalculationHistorySingleton getInstance() {
        if (instance == null) {
            instance = new CalculationHistorySingleton();
        }
        return instance;
    }
    public List<Calculation> getCalculationHistory() {
        return calculationHistory;
    }

    public void addCalculation(Calculation calculation) {
        calculationHistory.add(calculation);
    }
}
