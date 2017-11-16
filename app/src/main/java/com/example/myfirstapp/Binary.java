package com.example.myfirstapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Binary extends AppCompatActivity {
    int binary_input;
    String binary_output = "";
    String binary_temp = "";
    String binary_tem = "";
    int binary_inputop = 0;
    int binary_outop = 0;
    EditText binary_output_text;
    EditText binary_input_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binary);

        binary_output_text = (EditText) findViewById(R.id.binary_out_text);
        binary_input_text = (EditText) findViewById(R.id.binary_input_text);

        binary_input_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    binary_temp = binary_input_text.getText().toString();
                    switch (binary_inputop) {
                        case 2:
                            binary_input = Integer.valueOf(binary_temp, 2);
                            break;
                        case 8:
                            binary_input = Integer.valueOf(binary_temp, 8);
                            break;
                        case 10:
                            binary_input = Integer.parseInt(binary_temp);
                            break;
                        case 16:
                            binary_input = Integer.valueOf(binary_temp, 16);
                            break;
                    }
                    switch (binary_outop) {
                        case 2:
                            binary_output = Integer.toBinaryString(binary_input);
                            break;
                        case 8:
                            binary_output = Integer.toOctalString(binary_input);
                            break;
                        case 10:
                            binary_output = String.valueOf(binary_input);
                            break;
                        case 16:
                            binary_output = Integer.toHexString(binary_input);
                            break;
                    }
                } catch (Exception e) {
                    binary_output = "0";
                }
                binary_output_text.setText(binary_output);
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        try {
            switch (view.getId()) {
                case R.id.binary_input_2:
                    if (checked) {
                        binary_inputop = 2;
                        binary_input_text.setText("");
                        binary_input = 0;
                    }
                    break;
                case R.id.binary_input_8:
                    if (checked) {
                        binary_inputop = 8;
                        binary_input_text.setText("");
                        binary_input = 0;
                    }
                    break;
                case R.id.binary_input_10:
                    if (checked) {
                        binary_inputop = 10;
                        binary_input_text.setText("");
                        binary_input = 0;
                    }
                    break;
                case R.id.binary_input_16:
                    if (checked) {
                        binary_inputop = 16;
                        binary_input_text.setText("");
                        binary_input = 0;
                    }
                    break;

            }
        } catch (Exception e) {

        }
    }
    public void onRadioClicked(View v) {
        boolean checked = ((RadioButton) v).isChecked();
        switch (v.getId()) {
            case R.id.binary_out_2:
                if (checked) {
                    binary_outop = 2;
                    binary_output = Integer.toBinaryString(binary_input);
                }
                break;
            case R.id.binary_out_8:
                if (checked) {
                    binary_outop = 8;
                    binary_output = Integer.toOctalString(binary_input);
                }
                break;
            case R.id.binary_out_10:
                if (checked) {
                    binary_outop = 10;
                    binary_output = String.valueOf(binary_input);
                }
                break;
            case R.id.binary_out_16:
                if (checked) {
                    binary_outop = 16;
                    binary_output = Integer.toHexString(binary_input);
                }
                break;
        }
        binary_output_text.setText(binary_output);
    }
}
