package com.example.qiang.maketeamapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qiang.maketeamapp.navActivity.LogActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import classes.Constant;
import classes.MyAsyncTask;

import static android.text.TextUtils.isEmpty;

public class RegActivity extends AppCompatActivity {

    private EditText edtAccount;
    private EditText edtPassword;
    private EditText edtRepeatPassword;
    private Button buttonReg;
    private TextView tvResult;
    private String regResponseStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_reg);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        edtAccount=(EditText)findViewById(R.id.edt_reg_account);
        edtPassword=(EditText)findViewById(R.id.edt_reg_pwd);
        edtRepeatPassword=(EditText)findViewById(R.id.edt_reg_repeat_pwd);
        buttonReg=(Button)findViewById(R.id.reg_button);

        tvResult=(TextView)findViewById(R.id.reg_result_tv);
        buttonReg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (!isEmpty(edtAccount.getText().toString())
                        && !isEmpty(edtPassword.getText().toString())&&!isEmpty(edtRepeatPassword.getText().toString())) {
                    Log.e("Zengq", "都不空");

                    //System.out.println("zengq1:"+edtPassword.getText().toString());
                    //System.out.println("zengq2:"+edtRepeatPassword.getText().toString());
                    if(!(edtRepeatPassword.getText().toString().equals(edtPassword.getText().toString()))){
                        Toast.makeText(RegActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        register(edtAccount.getText().toString(), edtPassword.getText().toString());
                        Toast.makeText(RegActivity.this, "注册成功", Toast.LENGTH_LONG).show();

                       // Intent intent_reg_success=new Intent(RegActivity.this, LogActivity.class);
                        //startActivity(intent_reg_success);
                    }
                } else {
                    Toast.makeText(RegActivity.this, "账号、密码都不能为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private String register(String account, String password) {
        String registerUrlStr = Constant.URL_Register + "?account=" + account + "&password=" + password;
        new MyAsyncTask(tvResult,regResponseStr).execute(registerUrlStr);
        return regResponseStr;
    }


}
