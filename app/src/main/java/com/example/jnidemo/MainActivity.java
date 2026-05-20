package com.example.jnidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public native String helloFromJNI();
    public native int factorial(int n);
    public native String reverseString(String s);
    public native int sumArray(int[] values);

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvHello = findViewById(R.id.tvHello);
        TextView tvFact = findViewById(R.id.tvFact);
        TextView tvReverse = findViewById(R.id.tvReverse);
        TextView tvArray = findViewById(R.id.tvArray);
        TextView tvTests = findViewById(R.id.tvTests);

        tvHello.setText(helloFromJNI());

        int fact10 = factorial(10);
        tvFact.setText("Factoriel de 10 = " + fact10);

        String reversed = reverseString("JNI is powerful!");
        tvReverse.setText("Texte inverse : " + reversed);

        int[] numbers = {10, 20, 30, 40, 50};
        int sum = sumArray(numbers);
        tvArray.setText("Somme du tableau = " + sum);

        String tests =
                "Tests supplémentaires :\n\n"
                        + "factorial(-5) = " + factorial(-5) + "\n"
                        + "factorial(20) = " + factorial(20) + "\n"
                        + "reverseString(\"\") = " + reverseString("") + "\n"
                        + "sumArray(new int[]{}) = " + sumArray(new int[]{});

        tvTests.setText(tests);
    }
}