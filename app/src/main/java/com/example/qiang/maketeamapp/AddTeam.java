package com.example.qiang.maketeamapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.bean.DateType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.lang.Thread.sleep;

public class AddTeam extends AppCompatActivity{
    private List<String> categoryList = new ArrayList<String>();
    private TextView myTextView;
    private Spinner categorySpinner;
    private ArrayAdapter<String> adapter1;

    private DatePicker datePicker;
    private TimePicker timePicker;

    private Button date_button;
    private Button time_button;
    private TextView date_text;
    private TextView time_text;
    private int year, monthOfYear, dayOfMonth, hourOfDay, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //cate_Spinner下拉菜单

        categoryList.add("学习");
        categoryList.add("竞赛");
        categoryList.add("娱乐");
        categoryList.add("户外");
        categoryList.add("运动");
        categoryList.add("拼团");
        myTextView = (TextView)findViewById(R.id.team_category);
        categorySpinner = (Spinner)findViewById(R.id.spinner_category);
        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, categoryList);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        categorySpinner.setAdapter(adapter1);
        //第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
        categorySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                /* 将所选mySpinner 的值带入myTextView 中*/
                //myTextView.setText("您选择的是："+ adapter1.getItem(arg2));
                /* 将mySpinner 显示*/
                arg0.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                myTextView.setText(null);
                arg0.setVisibility(View.VISIBLE);
            }
        });
        /*下拉菜单弹出的内容选项触屏事件处理*/
       categorySpinner.setOnTouchListener(new Spinner.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                /**
                 *
                 */
                return false;
            }
        });
        /*下拉菜单弹出的内容选项焦点改变事件处理*/
       categorySpinner.setOnFocusChangeListener(new Spinner.OnFocusChangeListener(){
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

            }
        });


        date_button = (Button)findViewById(R.id.button1);
        time_button= (Button)findViewById(R.id.button2);
        date_text = (TextView)findViewById(R.id.date_text);
        time_text = (TextView)findViewById(R.id.time_text);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        date_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                /**
                 * 实例化一个DatePickerDialog的对象
                 * 第二个参数是一个DatePickerDialog.OnDateSetListener匿名内部类，当用户选择好日期点击done会调用里面的onDateSet方法
                 */
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTeam.this, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth)
                    {
                        date_text.setText(" " + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, year, monthOfYear, dayOfMonth);

                datePickerDialog.show();
            }
        });

        time_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /**
                 * 实例化一个TimePickerDialog的对象
                 * 第二个参数是一个TimePickerDialog.OnTimeSetListener匿名内部类，当用户选择好时间后点击done会调用里面的onTimeset方法
                 */
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTeam.this, new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                    {
                        time_text.setText(" " + hourOfDay + "点" + minute+"分");
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
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Snackbar.make(view, "正在发布", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show(); //暂未显示

                                Intent intent_issue_finished=new Intent(AddTeam.this, MainActivity.class);
                                startActivity(intent_issue_finished);
                            }
                        }).show();
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
}
