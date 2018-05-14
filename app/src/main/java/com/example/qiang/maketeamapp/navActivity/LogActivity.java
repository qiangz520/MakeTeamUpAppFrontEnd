package com.example.qiang.maketeamapp.navActivity;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qiang.maketeamapp.LogsuccessActivity;
import com.example.qiang.maketeamapp.MainActivity;
import com.example.qiang.maketeamapp.R;
import com.example.qiang.maketeamapp.RegActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import HttpTool.HttpUtil;
import classes.Constant;
import bean.ResponseState;
import okhttp3.Call;
import okhttp3.Response;

import static android.text.TextUtils.isEmpty;

public class LogActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_reg;
    private EditText edt_log_account;
    private EditText edt_log_pwd;
    private String loginResponseStr;

    private CheckBox rememberPwd;
    private SharedPreferences pref_remember;
    private SharedPreferences.Editor editor;//保存Token 以及实现记住密码功能
    private String account,password;
    private boolean isRemember;

    Handler mHandler_log = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            loginResponseStr=msg.obj.toString();
            Gson gson = new Gson();
            ResponseState loginState=gson.fromJson(loginResponseStr, ResponseState.class);
            Toast.makeText(LogActivity.this, loginState.getMsg(), Toast.LENGTH_SHORT).show();
            if(loginState.getCode().equals("200")) {
                //登录成功，用SharedPreferesbao保存Token值维持登录状态。
                editor=getSharedPreferences("Token",MODE_PRIVATE).edit();
                editor.putString("Token",loginState.getToken());
                editor.apply();
//                System.out.println("Token:"+loginState.getToken() );
                SharedPreferences pref  = getSharedPreferences("Token",MODE_PRIVATE);
                String token=pref.getString("Token","");
                Log.e("LogActivity:", "Token is: "+token);

                //登录成功则记住密码
                editor=pref_remember.edit();
                account=edt_log_account.getText().toString();
                password=edt_log_pwd.getText().toString();
                if(rememberPwd.isChecked()){
                    editor.putBoolean("remember_password",true);
                    editor.putString("account",account);
                    editor.putString("password",password);
//                    Log.e("Remember", "account:"+account);
//                    Log.e("Remember","password:"+password);
                }else{
                    editor.clear();
                }
                editor.apply();

                Intent intent_log_success =new Intent(LogActivity.this, LogsuccessActivity.class);
                startActivity(intent_log_success);
            }
        }
    };
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

        rememberPwd=(CheckBox)findViewById(R.id.remember_pwd);

        pref_remember= PreferenceManager.getDefaultSharedPreferences(this);
        isRemember = pref_remember.getBoolean("remember_password",false);
        if(isRemember){
            account= pref_remember.getString("account","");
            password= pref_remember.getString("password","");
            edt_log_account.setText(account);
            edt_log_pwd.setText(password);
            rememberPwd.setChecked(true);

        }


        buttonLog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                login();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(LogActivity.this, MainActivity.class);
                startActivity(intent);
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
    public void login() {
        //取得用户输入的账号和密码
        if (!isInputValid()){
            Toast.makeText(LogActivity.this, "账号、密码都不能为空！", Toast.LENGTH_SHORT).show();
        }
        else {
            String originAddress = Constant.URL_Login;
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("account", edt_log_account.getText().toString());
            params.put("password", edt_log_pwd.getText().toString());
            try {
                String compeletedURL = HttpUtil.getURLWithParams(originAddress, params);
                //try okhttp3
                HttpUtil.sendOkHttpRequestGet(compeletedURL, new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        Message message = new Message();
                        message.obj = responseData;
                        mHandler_log.sendMessage(message);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isInputValid() {
        //检查用户输入的合法性
        if(!isEmpty(edt_log_account.getText().toString())&&!isEmpty(edt_log_pwd.getText().toString())) {
            Log.e("zengq","账号密码都不空");
            return true;
        }
        else{
            return false;
        }
    }
}
