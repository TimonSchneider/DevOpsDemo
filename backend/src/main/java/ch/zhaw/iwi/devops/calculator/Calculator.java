package ch.zhaw.iwi.devops.calculator;
import java.lang.Math;

public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public double divide(int a, int b) {
        if(b == 0){
            throw new IllegalArgumentException("Cannot divide by zero!");
        }
        return (double)a / b;
    }
    
    public double calculateCircle(int radius) {
        return 2 * radius * Math.PI;
    }
}
