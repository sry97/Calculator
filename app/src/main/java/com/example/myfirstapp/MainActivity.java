package com.example.myfirstapp;

import android.app.Application;
import android.provider.Settings;
import android.support.annotation.IntegerRes;
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

import com.squareup.leakcanary.LeakCanary;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //声明控件
    double result = 0;
    String input=null;
    EditText editText ;

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

    int[] num_id = new int[]{//定义一个数组存放所有按钮的Id
            R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7,
            R.id.btn_8, R.id.btn_9, R.id.btn_sin, R.id.btn_leftbracket, R.id.btn_rightbracket, R.id.btn_add,
            R.id.btn_cos, R.id.btn_sub, R.id.btn_x2, R.id.btn_multiply, R.id.btn_x3, R.id.btn_division,
            R.id.btn_sqrt, R.id.btn_point, R.id.btn_clear
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
        editText= (EditText) findViewById(R.id.edittext);
        Button btn_equal = (Button) findViewById(R.id.btn_equal);
        btn_equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result=deal(editText.getText().toString());
                 String str1=String.valueOf(result);
                 editText.setText(str1);
                //Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onClick(View view) {
        String string = ((Button) view).getText().toString();
        input=editText.getText().toString();
        input=input+string;
        editText.setText(input);
    }

    public char Precede(String s1,String s2){//判断两个运算符之间的优先级
        int i=0;
        int j=0;
        char[] array=new char[]{//定义运算符优先级的矩阵
            '>','>','<','<','<','>','>',
            '>','>','<','<','<','>','>',
            '>','>','>','>','<','>','>',
            '>','>','>','>','<','>','>',
            '<','<','<','<','<','=','!',
            '>','>','>','>','!','>','>',
            '<','<','<','<','<','!','='
        };
        switch (s1)    //i为数组array 行标
        {
            case "+" : i=0;break;
            case "-" : i=1;break;
            case "X" : i=2;break;
            case "÷" : i=3;break;
            case "(" : i=4;break;
            case ")" : i=5;break;
            case "#" : i=6;break;
        }
        switch (s2)    //j为数组array 列标
        {
            case "+" : j=0;break;
            case "-" : j=1;break;
            case "X" : j=2;break;
            case "÷" : j=3;break;
            case "(" : j=4;break;
            case ")" : j=5;break;
            case "#" : j=6;break;
        }
        return (array[7*i+j]);  //返回对应优先级关系
    }

    public boolean isoperator(char ch){//判断是否是运算符

        if(ch=='+'||ch=='-'||ch=='X'||ch=='÷'||ch=='('||ch==')'||ch=='#'){
            return true;
        }
        else{
            return false;
        }
    }

    public double operator(double a,String theta,double b){//进行运算操作
        double t=0;
        switch(theta)
        {
            case "+" : t=a+b;break;
            case "-" : t=a-b;break;
            case "X" : t=a*b;break;
            case "÷" : t=a/b;break;
        }
        return t;
    }

    public String insert(String string){//在运算符两边插入空格
        String result=null;
        for(int i=0;i<string.length();i++){
            if(string.charAt(i)=='+'||string.charAt(i)=='-'||string.charAt(i)=='X'||string.charAt(i)=='÷'
                    ||string.charAt(i)=='('||string.charAt(i)==')'||string.charAt(i)=='#'){
                result +=' '+string.charAt(i)+' ';
            }
            else{
                result+=string.charAt(i);
            }
        }
        return result;
    }

    public double deal(String string){//主要处理过程
        Stack<Double> number=new Stack<Double>();
        Stack<String> operator=new Stack<String>();

        operator.push("#");
        String s=insert(editText.getText().toString()+"#");
        String[] str=s.split(" ");
        String tp;
        Double a;
        Double b;

        for(String st:str){
            if(!isoperator(st.charAt(0))){
                Double t= Double.valueOf(st);
                number.push(t);
            }
            else{
                switch (Precede(operator.peek(),st)){
                    case '<':
                        operator.push(st);break;
                    case '=':
                        operator.pop();break;
                    case '>':
                        tp=operator.pop();
                        b=number.pop();
                        a=number.pop();
                        number.push(operator(a,tp,b));break;
                }
            }
        }
        return number.pop();
    }
}