package com.example.qiang.maketeamapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import HttpTool.HttpUtil;
import bean.ResponseState;
import classes.Constant;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddTeam extends AppCompatActivity {
    private EditText title_et;
    private EditText description_et;

    private List<String> categoryList = new ArrayList<String>();
    private TextView category_tv;
    private Spinner categorySpinner;
    private ArrayAdapter<String> adapter1;

//    private DatePicker datePicker;
//    private TimePicker timePicker;

    private Button date_button;
    private Button time_button;
    private TextView date_text;
    private TextView time_text;
    private int year, monthOfYear, dayOfMonth, hourOfDay, minute;

    private EditText place_et;
    private EditText maxNumer_et;
    private EditText demand_et;

    private String issueResponseStr;
    Handler mHandler_issue = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Log.e("zengq", "" + msg.obj.toString());
            issueResponseStr = msg.obj.toString();
            Gson gson = new Gson();
            ResponseState issueState = gson.fromJson(issueResponseStr, ResponseState.class);
            Toast.makeText(AddTeam.this, issueState.getMsg(), Toast.LENGTH_SHORT).show();
            if (issueState.getCode().equals("200")) {
                Intent intent_issue_success = new Intent(AddTeam.this, MainActivity.class);
                startActivity(intent_issue_success);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //cate_Spinner下拉菜单

        categoryList.add("学习");
        categoryList.add("竞赛");
        categoryList.add("娱乐");
        categoryList.add("户外");
        categoryList.add("运动");
        categoryList.add("拼团");
        category_tv = (TextView) findViewById(R.id.team_category);
        categorySpinner = (Spinner) findViewById(R.id.spinner_category);
        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryList);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        categorySpinner.setAdapter(adapter1);
        //第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
        categorySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                /* 将所选mySpinner 的值带入myTextView 中*/
                //category_tv.setText("您选择的是："+ adapter1.getItem(arg2));
                /* 将mySpinner 显示*/
                arg0.setVisibility(View.VISIBLE);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                category_tv.setText(null);
                arg0.setVisibility(View.VISIBLE);
            }
        });
        /*下拉菜单弹出的内容选项触屏事件处理*/
        categorySpinner.setOnTouchListener(new Spinner.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                /**
                 *
                 */
                return false;
            }
        });
        /*下拉菜单弹出的内容选项焦点改变事件处理*/
        categorySpinner.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

            }
        });

        InitView();//find view,Init.
        date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 实例化一个DatePickerDialog的对象
                 * 第二个参数是一个DatePickerDialog.OnDateSetListener匿名内部类，当用户选择好日期点击done会调用里面的onDateSet方法
                 */
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTeam.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        date_text.setText(year + "-" + String.format("%02d", monthOfYear + 1) + "-" + String.format("%02d", dayOfMonth));
                    }
                }, year, monthOfYear, dayOfMonth);

                datePickerDialog.show();
            }
        });

        time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 实例化一个TimePickerDialog的对象
                 * 第二个参数是一个TimePickerDialog.OnTimeSetListener匿名内部类，当用户选择好时间后点击done会调用里面的onTimeset方法
                 */
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTeam.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time_text.setText(" " + String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
                    }
                }, hourOfDay, minute, true);

                timePickerDialog.show();
            }
        });

        FloatingActionButton fab_issue = (FloatingActionButton) findViewById(R.id.fab_issue); //发布FAB
        fab_issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new AlertDialog.Builder(AddTeam.this)
                        .setIcon(R.drawable.ic_smile)//这里是显示提示框的图片信息，我这里使用的默认androidApp的图标
                        .setTitle("确认发布")
                        .setMessage("您真的要发布吗？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Snackbar.make(view, "正在发布", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();


                                IssueTeamInfo();
//                                Intent intent_issue_finished=new Intent(AddTeam.this, MainActivity.class);
//                                startActivity(intent_issue_finished);
                            }
                        }).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void InitView() {

        title_et = (EditText) findViewById(R.id.team_title_text);
        description_et = (EditText) findViewById(R.id.team_description_text);

        date_button = (Button) findViewById(R.id.button1);
        time_button = (Button) findViewById(R.id.button2);
        date_text = (TextView) findViewById(R.id.date_text);
        time_text = (TextView) findViewById(R.id.time_text);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        place_et = (EditText) findViewById(R.id.place_text);
        maxNumer_et = (EditText) findViewById(R.id.team_number_text);
        demand_et = (EditText) findViewById(R.id.team_demand_text);
    }

    private void IssueTeamInfo() {
        SharedPreferences pref = getSharedPreferences("Token", MODE_PRIVATE);
        String token = pref.getString("Token", "");//获取token

        String title = title_et.getText().toString();
        String description = description_et.getText().toString();
        String category = categorySpinner.getSelectedItem().toString();
        String date = date_text.getText().toString();
        String time = time_text.getText().toString();
        String date_time = date + time; //活动时间

        Date cDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String currentTime = sdf.format(cDate);
        ;//生成当前时间
        Log.e("currentTime ", "" + currentTime);

        String place = place_et.getText().toString();
        String number = maxNumer_et.getText().toString();
        String demand = demand_et.getText().toString();
        if (title.equals("") || description.equals("") || category.equals("") || date.equals("") || time.equals("") || place.equals("") || number.equals("") || demand.equals("")) {
            Toast.makeText(AddTeam.this, "请完善组队信息！", Toast.LENGTH_SHORT).show();
        } else {
            String compeletedURL = Constant.URL_AddTeam;
            RequestBody requestBody = new FormBody.Builder()
                    .add("token", token)
                    .add("title", title)
                    .add("description", description)
                    .add("category", category)
                    .add("datetime", date_time)
                    .add("currentTime", currentTime)
                    .add("place", place)
                    .add("number", number)
                    .add("demand", demand)
                    .build();
            try {
                //try okhttp3
                HttpUtil.sendOkHttpRequestPost(compeletedURL, requestBody, new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //
                        Log.e("zengq:", "Failure");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
//                        返回发布结果
                        String responseData = response.body().string();
                        Log.e("onResponse: ", responseData);
                        Message message = new Message();
                        message.obj = responseData;
                        mHandler_issue.sendMessage(message);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
