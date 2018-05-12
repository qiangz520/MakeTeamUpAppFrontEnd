package com.example.qiang.maketeamapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import HttpTool.HttpUtil;
import classes.Constant;
import classes.LogRegState;
import okhttp3.Call;
import okhttp3.Response;

import static android.text.TextUtils.isEmpty;

public class RegActivity extends AppCompatActivity {

    private EditText edtAccount;
    private EditText edtPassword;
    private EditText edtRepeatPassword;
    private Button buttonReg;
    private String regResponseStr;
    final private int IS_VOID=3;//输入不全
    final private int NOT_SAME=2;//两次不一致
    final private int VALID_INPUT=1;//有效输入
    Handler mHandler_log = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

//            System.out.println("zengq"+msg.obj.toString());
            regResponseStr=msg.obj.toString();
            Gson gson = new Gson();
            LogRegState regState=gson.fromJson(regResponseStr, LogRegState.class);
            Toast.makeText(RegActivity.this, regState.getMsg(), Toast.LENGTH_SHORT).show();
            if(regState.getCode().equals("200")) {
                Intent intent_reg_success =new Intent(RegActivity.this, LogActivity.class);
                startActivity(intent_reg_success);
            }
        }
    };

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

        buttonReg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                   register();
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

    public void register() {
        //取得用户输入的账号和密码
        if (InputValidValue()==IS_VOID){
            Toast.makeText(RegActivity.this, "账号、密码都不能为空！", Toast.LENGTH_SHORT).show();
        }else if(InputValidValue()==NOT_SAME ){
            Toast.makeText(RegActivity.this,"密码两次输入不一致！",Toast.LENGTH_SHORT).show();
        }else if(InputValidValue()==VALID_INPUT){
            String originAddress = Constant.URL_Register;
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("account", edtAccount.getText().toString());
            params.put("password", edtPassword.getText().toString());
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
                        mHandler_log.sendMessage(message);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int InputValidValue() {
        //检查用户输入的合法性，这里暂且默认用户输入合法
        if(!isEmpty(edtAccount.getText().toString())&&!isEmpty(edtPassword.getText().toString())&&!isEmpty(edtRepeatPassword.getText().toString())){
            Log.e("zengq","账号密码都不空");
            if(!edtPassword.getText().toString().equals(edtRepeatPassword.getText().toString())){
                Log.e("zengq","两次密码输入不一致");
                return NOT_SAME;
            }
        }
        else {
            return IS_VOID;
        }
        return VALID_INPUT;
    }
}
