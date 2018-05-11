package com.example.qiang.maketeamapp.navActivity;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
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
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.HashMap;

import HttpTool.HttpCallbackListener;
import HttpTool.HttpUtil;
import classes.Constant;
import classes.LoginState;
import classes.MyAsyncTask;
import okhttp3.Call;
import okhttp3.Response;

import static android.text.TextUtils.isEmpty;

public class LogActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_reg;
    private EditText edt_log_account;
    private EditText edt_log_pwd;
    private TextView tv_log_result;
    private String loginResponseStr;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = "";
//            System.out.println("zengq"+msg.obj.toString());
            loginResponseStr=msg.obj.toString();
            Gson gson = new Gson();
            LoginState loginState=gson.fromJson(loginResponseStr,LoginState.class);
            Toast.makeText(LogActivity.this, loginState.getMsg(), Toast.LENGTH_SHORT).show();
//            Log.d("zengq", "onClick: "+loginState.getCode());
            if(loginState.getCode().equals("200")) {
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
        tv_log_result=(TextView)findViewById(R.id.log_tv_result);



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
                HttpUtil.sendOkHttpRequest(compeletedURL, new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        Message message = new Message();
                        message.obj = responseData;
                        mHandler.sendMessage(message);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isInputValid() {
        //检查用户输入的合法性，这里暂且默认用户输入合法
        if(!isEmpty(edt_log_account.getText().toString())&&!isEmpty(edt_log_pwd.getText().toString())) {
            Log.e("zengq","账号密码都不空");
            return true;
        }
        else{
            return false;
        }
    }
}
