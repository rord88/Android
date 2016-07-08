package com.ktds.cain.intenttest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private EditText etPasswordConfirm;
    private Button btnConfirm;
    private Button btnCancel;
    private boolean isValidEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPasswordConfirm = (EditText) findViewById(R.id.etPasswordConfirm);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    Pattern p = Pattern.compile("^[a-zA-Z0-9]@[a-zA-Z0-9].[a-zA-Z0-9]$");
                    Matcher m = p.matcher(etEmail.getText().toString());
                    isValidEmail = !m.matches();
                    if(isValidEmail){
                        Toast.makeText(RegistActivity.this, "Email fomat wirte", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    if(isValidEmail) {
                        etEmail.requestFocus();
                    }
                }
            }
        });

        etPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = etPassword.getText().toString();
                String confirm = etPasswordConfirm.getText().toString();
                if ( password.equals(confirm)) {
                    etPassword.setBackgroundColor(Color.GREEN);
                    etPasswordConfirm.setBackgroundColor(Color.GREEN);
                } else {
                    etPassword.setBackgroundColor(Color.RED);
                    etPasswordConfirm.setBackgroundColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etEmail.getText().toString().length() == 0) {
                    Toast.makeText(RegistActivity.this, "Email write", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                }
                if (etPassword.getText().toString().length() == 0) {
                    Toast.makeText(RegistActivity.this, "password write", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }

                if (etPasswordConfirm.getText().toString().length() == 0) {
                    Toast.makeText(RegistActivity.this, "passwordConfirm write", Toast.LENGTH_SHORT).show();
                    etPasswordConfirm.requestFocus();
                    return;
                }

                if(!etPassword.getText().toString().equals(etPasswordConfirm.getText().toString())) {
                    Toast.makeText(RegistActivity.this, "Password is not equals", Toast.LENGTH_SHORT).show();
                    etPassword.setText("");
                    etPasswordConfirm.setText("");

                    etPassword.requestFocus();
                    return;
                }
                //Answer
                Intent result = new Intent();
                result.putExtra("email", etEmail.getText().toString());
                setResult(RESULT_OK, result);
                finish();
            }
        });

    }
}
