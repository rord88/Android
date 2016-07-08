package com.ktds.cain.mypracticethree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText firstNumber;
    private EditText secondNumber;
    private EditText etResult;
    private boolean isValidCheck;
    private Button plus;
    private Button minus;
    private Button multi;
    private Button div;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstNumber = (EditText) findViewById(R.id.etNumberOne);
        secondNumber = (EditText) findViewById(R.id.etNumberTwo);
        etResult = (EditText) findViewById(R.id.etResult);
        plus = (Button) findViewById(R.id.btnPlus);
        minus = (Button) findViewById(R.id.btnMinus);
        multi = (Button) findViewById(R.id.btnMulti);
        div = (Button) findViewById(R.id.btnDiv);


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnIntent(v);
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnIntent(v);
            }
        });
        multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnIntent(v);
            }
        });
        div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnIntent(v);
            }
        });

        firstNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    Pattern p = Pattern.compile("^[a-zA-Z0-9]");
                    Matcher m = p.matcher(firstNumber.getText().toString());
                    isValidCheck = !m.matches();
                    if(isValidCheck) {
                        Toast.makeText(MainActivity.this, "숫자만 입력하세요.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if(isValidCheck) {
                        firstNumber.requestFocus();
                    }
                }
            }
        });

        secondNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    Pattern p = Pattern.compile("^[a-zA-Z0-9]");
                    Matcher m = p.matcher(secondNumber.getText().toString());
                    isValidCheck = !m.matches();
                    if(isValidCheck) {
                        Toast.makeText(MainActivity.this, "숫자만 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if(isValidCheck) {
                        secondNumber.requestFocus();
                    }
                }

            }
        });
    }
    private void returnIntent(View v) {

        String operator = (String) ((Button) v).getText();

        if(firstNumber.getText().toString().length() == 0) {
            Toast.makeText(MainActivity.this, "첫번째 숫자를 작성하세요.", Toast.LENGTH_SHORT).show();
            firstNumber.requestFocus();
            return;
        }
        if(secondNumber.getText().toString().length() == 0) {
            Toast.makeText(MainActivity.this, "두번째 숫자를 작성하세요.", Toast.LENGTH_SHORT).show();
            secondNumber.requestFocus();
            return;
        }

        if(operator.equals("/") && secondNumber.getText().toString().equals("0")) {
            Toast.makeText(MainActivity.this, "0 나눌 수 없습니다.", Toast.LENGTH_SHORT).show();
            secondNumber.requestFocus();
        }

        Intent intent = new Intent(v.getContext(), SubActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("firstNumber", firstNumber.getText().toString());
        intent.putExtra("secondNumber", secondNumber.getText().toString());
        intent.putExtra("operator", operator);

        startActivityForResult(intent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000 && resultCode == RESULT_OK) {
            etResult.setText(data.getStringExtra("sentences"));
        }
    }
}
