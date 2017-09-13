package com.example.myfirstapp;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
            case R.id.exit:
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    int[] num_id = new int[]{//定义一个数组存放Id
            R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7,
            R.id.btn_8, R.id.btn_9, R.id.btn_sin, R.id.btn_leftbracket, R.id.btn_rightbracket, R.id.btn_add,
            R.id.btn_cos, R.id.btn_sub, R.id.btn_x2, R.id.btn_multiply, R.id.btn_x3, R.id.btn_division,
            R.id.btn_sqrt, R.id.btn_point
    };
    Button[] btn_name = new Button[24];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < num_id.length; i++) {  //利用for循环实例化每个按钮并为其注册监听器
            btn_name[i] = (Button) findViewById(num_id[i]);
            btn_name[i].setOnClickListener(this);
        }
        editText = (EditText) findViewById(R.id.edittext);
        Button btn_equal = (Button) findViewById(R.id.btn_equal);
        btn_equal.setOnClickListener(new View.OnClickListener() {//等于号的点击事件处理
            @Override
            public void onClick(View view) {
                result = deal(editText.getText().toString());
                String str1 = String.valueOf(result);
                editText.setText(str1);
            }
        });
        Button btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(new View.OnClickListener() {//清空按钮的点击处理
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });
    }

    @Override
    public void onClick(View view) {
        String string = ((Button) view).getText().toString();
        input = editText.getText().toString();
        input = input + string;
        editText.setText(input);

    }

    public void operate(Stack<Double> number, Stack<Character> operator) {//进行运算操作
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
        }
    }

    public String insert(String string) {//在运算符两边插入空格
        String result = "";
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '+' || string.charAt(i) == '-' || string.charAt(i) == 'x' || string.charAt(i) == '÷'
                    || string.charAt(i) == '(' || string.charAt(i) == ')') {
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

        for (String st : str) {
            if(st.length()==0){
                continue;
            }
            else if (st.charAt(0)=='+'||st.charAt(0)=='-') {
                while(!operator.isEmpty()&&(operator.peek()=='+'||operator.peek()=='-'||operator.peek()=='x'||operator.peek()=='÷')){
                    operate(number,operator);
                }
                operator.push(st.charAt(0));
            }
            else if(st.charAt(0)=='x'||st.charAt(0)=='÷'){
                while (!operator.isEmpty()&&(operator.peek()=='x'||operator.peek()=='÷')){
                    operate(number,operator);
                }
                operator.push(st.charAt(0));
            }
            else if(st.trim().charAt(0)=='('){
                operator.push('(');
            }
            else if(st.trim().charAt(0)==')'){
                while (operator.peek()!='('){
                    operate(number,operator);
                }
                operator.pop();
            }
            else {
                number.push(Double.valueOf(st));
            }
        }
        while (!operator.isEmpty()){
            operate(number,operator);
        }
        return number.pop();
    }
}