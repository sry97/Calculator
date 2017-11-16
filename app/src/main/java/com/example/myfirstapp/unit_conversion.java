package com.example.myfirstapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class unit_conversion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_conversion);

        final EditText unit_input1 = (EditText) findViewById(R.id.unit_input1);
        final EditText unit_input2 = (EditText) findViewById(R.id.unit_input2);
        final TextView unit_out1 = (TextView) findViewById(R.id.unit_out1);
        final TextView unit_out2 = (TextView) findViewById(R.id.unit_out2);
        Button btn_unit_trans = (Button) findViewById(R.id.btn_unit_tran);
        Button btn_unit_clear = (Button) findViewById(R.id.btn_unit_clear);

        btn_unit_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(unit_input1.getText())) {

                } else {
                    unit_out1.setText(String.valueOf(Integer.parseInt(unit_input1.getText().toString()) + 273.15));
                }

                if (TextUtils.isEmpty(unit_input2.getText())) {

                } else {
                    unit_out2.setText(String.valueOf(Integer.parseInt(unit_input2.getText().toString()) * Math.PI / 180));
                }
            }
        });
        btn_unit_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unit_input1.setText("");
                unit_input2.setText("");
                unit_out1.setText("");
                unit_out2.setText("");
            }
        });
    }
}
