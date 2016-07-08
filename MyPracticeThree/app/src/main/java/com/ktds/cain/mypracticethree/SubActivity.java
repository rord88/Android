package com.ktds.cain.mypracticethree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SubActivity extends AppCompatActivity {

    private Button btnClose;
    private EditText etResult;
    private String operator;
    private int one;
    private int two;
    private int result;
    private String sentences;

    private int calculate(int one, int two, String operator) {
        if(operator.equals("+")) {
            return one + two;
        } else if (operator.equals("-")) {
            return one - two;
        } else if (operator.equals("*")) {
            return one * two;
        } else if (operator.equals("/")) {
            return one / two;
        } else {
            return 0;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        etResult = (EditText) findViewById(R.id.etResult);
        btnClose = (Button) findViewById(R.id.btnClose);

        Intent intent = getIntent();
        operator = intent.getStringExtra("operator");
        one = Integer.parseInt(intent.getStringExtra("firstNumber"));
        two = Integer.parseInt(intent.getStringExtra("secondNumber"));

        result = calculate(one, two, operator);

        sentences = one + " " + operator + " " + two + " = " + result ;

        etResult.setText(sentences);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calculated = new Intent();
                calculated.putExtra("sentences", sentences);
                setResult(RESULT_OK, calculated);
                finish();
            }
        });
    }
}


