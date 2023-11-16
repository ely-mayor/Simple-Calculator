package com.group5.midterm;

public class Calculation {
    private String expression;
    private String result;
    private int image;

    public Calculation(int image, String expression, String result) {
        this.image = image;
        this.expression = expression;
        this.result = result;
    }
    public String getExpression() {
        return expression;
    }

    public String getResult() {
        return result;
    }
    public void setImage(int image) {
        this.image = image;
    }
    public int getImage() {
        return image;
    }
}