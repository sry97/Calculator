package com.example.myfirstapp;

import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    double result = 0;
    String input = "";
    EditText editText;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//将资源菜单文件加载到主界面中
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//对菜单选项点击处理
        int id = item.getItemId();
        switch (id) {
            case R.id.help:
                Toast.makeText(this, "这是帮助", Toast.LENGTH_SHORT).show();
                break;
            case R.id.binary:
                Intent intent_binary = new Intent(MainActivity.this, Binary.class);
                startActivity(intent_binary);
                break;
            case R.id.unit:
                Intent intent_unit = new Intent(MainActivity.this, unit_conversion.class);
                startActivity(intent_unit);
                break;
            case R.id.owner:
                Toast.makeText(this, "Copyright© 2017 By 沈荣耀.All rights reserved.", Toast.LENGTH_LONG).show();
                break;
            case R.id.exit:
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    int[] num_id = new int[]{//定义一个数组存放部分不需特殊处理的Id
            R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7,
            R.id.btn_8, R.id.btn_9, R.id.btn_leftbracket, R.id.btn_rightbracket, R.id.btn_add,
            R.id.btn_sub, R.id.btn_x2, R.id.btn_multiply, R.id.btn_x3, R.id.btn_division,
            R.id.btn_sqrt, R.id.btn_point, R.id.btn_ln, R.id.btn_percent
    };
    Button[] btn_name = new Button[24];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < num_id.length; i++) {  //利用for循环获得数组 num_id 中的按钮并为其注册监听器
            btn_name[i] = (Button) findViewById(num_id[i]);
            btn_name[i].setOnClickListener(this);
        }
        editText = (EditText) findViewById(R.id.edittext);

        Button btn_equal = (Button) findViewById(R.id.btn_equal);//在点击等于号后进行最终的计算处理
        btn_equal.setOnClickListener(new View.OnClickListener() {//等于号的点击事件处理
            @Override
            public void onClick(View view) {
                result = deal(editText.getText().toString());
                String str1 = String.valueOf(result);
                editText.setText(str1);
            }
        });

        Button btn_clear = (Button) findViewById(R.id.btn_clear);//清空文本框
        btn_clear.setOnClickListener(new View.OnClickListener() {//清空按钮的点击处理
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });

        Button btn_sin = (Button) findViewById(R.id.btn_sin);//sin按钮的点击事件处理
        btn_sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input = editText.getText().toString();
                input = input + "s";
                editText.setText(input);
            }
        });
        Button btn_cos = (Button) findViewById(R.id.btn_cos);//cos按钮的点击事件处理
        btn_cos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input = editText.getText().toString();
                input = input + "c";
                editText.setText(input);
            }
        });
        Button btn_tan = (Button) findViewById(R.id.btn_tan);//tan按钮的点击事件处理
        btn_tan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input = editText.getText().toString();
                input = input + "t";
                editText.setText(input);
            }
        });
        Button btn_mi = (Button) findViewById(R.id.btn_mi);// x^y 按钮的点击事件处理
        btn_mi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input = editText.getText().toString();
                input = input + "^";
                editText.setText(input);
            }
        });
        Button btn_factorial = (Button) findViewById(R.id.btn_factorial);// n!按钮的点击事件处理
        btn_factorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input = editText.getText().toString();
                input = input + "!";
                editText.setText(input);
            }
        });
    }

    @Override
    public void onClick(View view) {//对大部分按钮进行点击处理，将按钮内text的内容放入input中
        String string = ((Button) view).getText().toString();
        input = editText.getText().toString();
        input = input + string;
        editText.setText(input);
    }

    public void operate(Stack<Double> number, Stack<Character> operator) {//进行+-x÷，y次幂 运算操作
        char oper = operator.pop();
        Double num_1 = number.pop();
        Double num_2 = number.pop();
        if (oper == '+') {
            number.push(num_2 + num_1);
        } else if (oper == '-') {
            number.push(num_2 - num_1);
        } else if (oper == 'x') {
            number.push(num_2 * num_1);
        } else if (oper == '÷') {
            number.push(num_2 / num_1);
        } else if (oper == '^') {
            double itemp = num_2;
            for (int i = 0; i < num_1 - 1; i++) {
                num_2 = num_2 * itemp;
            }
            number.push(num_2);
        }
    }

    public void operate_power(Stack<Double> number, Stack<Character> operator) {//进行平方，立方，开根，sin,cos,tan,㏑,n!,%运算操作
        Double num_temp = number.pop();
        char operator_temp = operator.pop();
        if (operator_temp == '²') {
            number.push(num_temp * num_temp);
        } else if (operator_temp == '³') {
            number.push(num_temp * num_temp * num_temp);
        } else if (operator_temp == '√') {
            number.push(Math.sqrt(num_temp));
        } else if (operator_temp == 's') {
            number.push(Math.sin(Math.PI * num_temp / 180.0));
        } else if (operator_temp == 'c') {
            number.push(Math.cos(Math.PI * num_temp / 180.0));
        } else if (operator_temp == 't') {
            number.push(Math.tan(Math.PI * num_temp / 180.0));
        } else if (operator_temp == '㏑') {
            number.push(Math.log(num_temp));
        } else if (operator_temp == '!') {
            double t = 1;
            for (int i = 1; i <= num_temp; i++) {
                t = t * i;
            }
            number.push(t);
        } else if (operator_temp == '%') {
            number.push(num_temp / 100.0);
        }
    }

    public String insert(String string) {//在运算符两边插入空格
        String result = "";
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '+' || string.charAt(i) == '-' || string.charAt(i) == 'x'
                    || string.charAt(i) == '÷' || string.charAt(i) == '(' || string.charAt(i) == ')'
                    || string.charAt(i) == '²' || string.charAt(i) == '³' || string.charAt(i) == '√'
                    || string.charAt(i) == 's' || string.charAt(i) == 'c' || string.charAt(i) == 't'
                    || string.charAt(i) == '㏑' || string.charAt(i) == '^' || string.charAt(i) == '!'
                    || string.charAt(i) == '%') {
                result += " " + string.charAt(i) + " ";
            } else {
                result += string.charAt(i);
            }
        }
        return result;
    }

    public double deal(String string) {//主要处理过程

        Stack<Double> number = new Stack<>();//定义一个存放数字的栈
        Stack<Character> operator = new Stack<>();//存放操作符

        String s = insert(string);
        String[] str = s.split(" ");
        try {
            for (String st : str) {
                if (st.length() == 0) {
                    continue;
                } else if (st.charAt(0) == '+' || st.charAt(0) == '-') {//若当前字符为  + ， -
                    while (!operator.isEmpty() && (operator.peek() == '+' || operator.peek() == '-' || operator.peek() == 'x'
                        || operator.peek() == '÷' || operator.peek() == '^')) {
                        operate(number, operator);
                    }
                    while (!operator.isEmpty() && (operator.peek() == '²' || operator.peek() == '³' || operator.peek() == '√'
                        || operator.peek() == 's' || operator.peek() == 'c' || operator.peek() == 't' || operator.peek() == '㏑'
                        || operator.peek() == '!' || operator.peek() == '%')) {
                        operate_power(number, operator);
                    }
                    operator.push(st.charAt(0));
                } else if (st.charAt(0) == 'x' || st.charAt(0) == '÷') {//若当前字符为  x , ÷
                    while (!operator.isEmpty() && (operator.peek() == 'x' || operator.peek() == '÷')) {
                        operate(number, operator);
                    }
                    while (!operator.isEmpty() && (operator.peek() == '²' || operator.peek() == '³' || operator.peek() == '√'
                        || operator.peek() == 's' || operator.peek() == 'c' || operator.peek() == 't' || operator.peek() == '%'
                        || operator.peek() == '㏑' || operator.peek() == '!' || operator.peek() == '^')) {
                        operate_power(number, operator);
                    }
                    operator.push(st.charAt(0));
                } else if (st.charAt(0) == '^') {
                    while (!operator.isEmpty() && (st.charAt(0) == '²' || st.charAt(0) == '³' || st.charAt(0) == '√'
                        || operator.peek() == '%' || operator.peek() == '!' || operator.peek() == '^')) {
                        operate_power(number, operator);
                    }
                    operator.push(st.charAt(0));
                } else if (st.charAt(0) == '²' || st.charAt(0) == '³' || st.charAt(0) == '√' || st.charAt(0) == 's'
                    || st.charAt(0) == 'c' || st.charAt(0) == 't' || st.charAt(0) == '%' || st.charAt(0) == '㏑'
                    || st.charAt(0) == '!') {//若当前字符为 平方，立方，开根，sin，cos,tan，%，㏑，n!
                    while (!operator.isEmpty() && (operator.peek() == '²' || operator.peek() == '³'
                        || operator.peek() == '√' || operator.peek() == '%' || operator.peek() == '!')) {
                        operate_power(number, operator);
                    }
                    while (!operator.isEmpty() && (operator.peek() == '^')) {
                        operate(number, operator);
                    }
                    operator.push(st.charAt(0));
                } else if (st.trim().charAt(0) == '(') {
                    operator.push('(');
                } else if (st.trim().charAt(0) == ')') {
                    while (operator.peek() != '(') {
                        if (operator.peek() == '+' || operator.peek() == '-' || operator.peek() == 'x'
                                || operator.peek() == '÷' || operator.peek() == '^') {
                            operate(number, operator);
                        } else if (operator.peek() == '√' || operator.peek() == '²' || operator.peek() == '³'
                                || operator.peek() == 's' || operator.peek() == 'c' || operator.peek() == 't'
                                || operator.peek() == '%' || operator.peek() == '!' || operator.peek() == '㏑') {
                        operate_power(number, operator);
                        }
                    }
                    operator.pop();
                } else {
                    number.push(Double.valueOf(st));
                }
            }
            while (!operator.isEmpty()) {
                if (operator.peek() == '+' || operator.peek() == '-' || operator.peek() == 'x'
                        || operator.peek() == '÷' || operator.peek() == '^') {
                    operate(number, operator);
                } else if (operator.peek() == '√' || operator.peek() == '²' || operator.peek() == '³'
                        || operator.peek() == 's' || operator.peek() == 'c' || operator.peek() == 't'
                        || operator.peek() == '㏑' || operator.peek() == '%' || operator.peek() == '!') {
                    operate_power(number, operator);
                }
            }
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "输入错误，请检查后重新输入！", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return number.pop();
    }
}