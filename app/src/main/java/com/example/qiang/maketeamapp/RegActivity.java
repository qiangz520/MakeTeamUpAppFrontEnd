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

import static android.text.TextUtils.isEmpty;

public class RegActivity extends AppCompatActivity {

    private EditText edtAccount;
    private EditText edtPassword;
    private EditText edtRepeatPassword;
    private Button buttonReg;
    private TextView tvResult;

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

                        Intent intent_reg_success=new Intent(RegActivity.this, LogActivity.class);
                        startActivity(intent_reg_success);
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

    private void register(String account, String password) {
        String registerUrlStr = Constant.URL_Register + "?account=" + account + "&password=" + password;
        new MyAsyncTask(tvResult).execute(registerUrlStr);
    }

    public static class MyAsyncTask extends AsyncTask<String, Integer, String> {

        private TextView tv; // 举例一个UI元素，后边会用到

        public MyAsyncTask(TextView v) {
            tv = v;
        }

        @Override
        protected void onPreExecute() {
            Log.w("Zengq", "task onPreExecute()");
        }

        /**
         * @param params 这里的params是一个数组，即AsyncTask在激活运行是调用execute()方法传入的参数
         */
        @Override
        protected String doInBackground(String... params) {
            Log.w("Zengq", "task doInBackground()");
            HttpURLConnection connection = null;
            StringBuilder response = new StringBuilder();
            try {
                URL url = new URL(params[0]); // 声明一个URL,注意如果用百度首页实验，请使用https开头，否则获取不到返回报文
                connection = (HttpURLConnection) url.openConnection(); // 打开该URL连接
                connection.setRequestMethod("GET"); // 设置请求方法，“POST或GET”，我们这里用GET，在说到POST的时候再用POST
                connection.setConnectTimeout(80000); // 设置连接建立的超时时间
                connection.setReadTimeout(80000); // 设置网络报文收发超时时间
                InputStream in = connection.getInputStream();  // 通过连接的输入流获取下发报文，然后就是Java的流处理
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response.toString(); // 这里返回的结果就作为onPostExecute方法的入参
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // 如果在doInBackground方法，那么就会立刻执行本方法
            // 本方法在UI线程中执行，可以更新UI元素，典型的就是更新进度条进度，一般是在下载时候使用
        }

        /**
         * 运行在UI线程中，所以可以直接操作UI元素
         * @param s
         */
        @Override
        protected void onPostExecute(String s) {
            Log.w("Zengq", "task onPostExecute()");
            tv.setText(s);
        }

    }
}
