package com.example.qiang.maketeamapp.navActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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

import com.example.qiang.maketeamapp.LogsuccessActivity;
import com.example.qiang.maketeamapp.MainActivity;
import com.example.qiang.maketeamapp.R;
import com.example.qiang.maketeamapp.RegActivity;

import org.w3c.dom.Text;

import classes.Constant;

import static android.text.TextUtils.isEmpty;

public class LogActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_reg;
    private EditText edt_log_account;
    private EditText edt_log_pwd;
    private TextView tv_log_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar6);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        tv_reg=(TextView)findViewById(R.id.click_to_register);
        tv_reg.setOnClickListener(this);

        Button buttonLog=(Button)findViewById(R.id.log_button);

        edt_log_account=(EditText)findViewById(R.id.edt_login_account);
        edt_log_pwd=(EditText)findViewById(R.id.edt_login_pwd);
        tv_log_result=(TextView)findViewById(R.id.log_tv_result);
        buttonLog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!isEmpty(edt_log_account.getText().toString())&&!isEmpty(edt_log_pwd.getText().toString())){
                    Log.e("zengq","账号密码都不空");
                    login(edt_log_account.getText().toString(), edt_log_pwd.getText().toString());

                    Toast.makeText(LogActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent_log_success =new Intent(LogActivity.this, LogsuccessActivity.class);
                    startActivity(intent_log_success);
                }
                else{
                    Toast.makeText(LogActivity.this, "账号、密码都不能为空！", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.click_to_register:
                Intent intent1=new Intent(LogActivity.this, RegActivity.class);
                startActivity(intent1);
                break;

        }
    }

    private void login(String account,String password){
        String registerUrlStr = Constant.URL_Login + "?account=" + account + "&password=" + password;
        new RegActivity.MyAsyncTask(tv_log_result).execute(registerUrlStr);
    }
}
