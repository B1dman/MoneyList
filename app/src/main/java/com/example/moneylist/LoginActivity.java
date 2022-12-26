package com.example.moneylist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_login);
        Button loginButton =findViewById(R.id.login);
        Button get = findViewById(R.id.get);
        get.setOnClickListener(this::onClick3);
        loginButton.setOnClickListener(this);
        Button registerButton = findViewById(R.id.register);
        registerButton.setOnClickListener(this::onClick2);
    }
    //获取账号密码
    private void onClick3(View view) {
        SharedPreferences test = getSharedPreferences("Test",MODE_PRIVATE);
        String username = test.getString("username","");
        String password = test.getString("password","");
        EditText username1 = findViewById(R.id.username);
        //用户密码
        EditText password1 = findViewById(R.id.password);
        username1.setText(username);
        password1.setText(password);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, MoneyListActivity.class);
        //用户名
        EditText username = findViewById(R.id.username);
        //用户密码
        EditText password = findViewById(R.id.password);
        String  usernameString  =  username.getText().toString();
        String passwordString =  password.getText().toString();
        //登录时候先保存
        SharedPreferences test = getSharedPreferences("Test",MODE_PRIVATE);
        SharedPreferences.Editor editor = test.edit();
        editor.putString("username",usernameString);
        editor.putString("password",passwordString);
        editor.commit();
        boolean flag = UserLab.get(this).isMatch(usernameString,passwordString);
        System.out.println(flag);
        if(flag ==true){
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else{
            return;
        }
    }
    public void onClick2(View view){
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        String  usernameString  =  username.getText().toString();
        String passwordString =  password.getText().toString();
        UserLab userLab = UserLab.get(this);
        User user = new User();
        user.setUsername(usernameString);
        user.setPassword(passwordString);
        userLab.addUser(user);
    }



}
