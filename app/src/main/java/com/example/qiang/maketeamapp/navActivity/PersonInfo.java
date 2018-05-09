package com.example.qiang.maketeamapp.navActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qiang.maketeamapp.MainActivity;
import com.example.qiang.maketeamapp.R;
import com.example.qiang.maketeamapp.SearchActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonInfo extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        CircleImageView headerCIW=(CircleImageView)findViewById(R.id.header_image_CIW);
        RelativeLayout nickNameLayout=(RelativeLayout)findViewById(R.id.nickName_layout);
        RelativeLayout sexLayout=(RelativeLayout)findViewById(R.id.sex_layout);
        RelativeLayout schoolLayout=(RelativeLayout)findViewById(R.id.school_layout);
        RelativeLayout contactMethod=(RelativeLayout)findViewById(R.id.contactMethod_layout);

        headerCIW.setOnClickListener(this);
        nickNameLayout.setOnClickListener(this);
        sexLayout.setOnClickListener(this);
        schoolLayout.setOnClickListener(this);
        contactMethod.setOnClickListener(this);


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
    public void onClick(View v){   //点击个人信息子布局可进行编辑
        switch (v.getId()){
            case R.id.header_image_CIW:
                dialogList();
                break;
            case R.id.nickName_layout:
                final EditText et_nickname = new EditText(this);
                final TextView tv_nickName=(TextView)findViewById(R.id.nickName_tv);
                new AlertDialog.Builder(this).setTitle("编辑昵称")
                        .setIcon(R.drawable.ic_smile)
                        .setView(et_nickname)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String input = et_nickname.getText().toString();
                                if (input.equals("")) {
                                    Toast.makeText(getApplicationContext(), "内容不能为空！" + input, Toast.LENGTH_LONG).show();
                                }
                                else{
                                    tv_nickName.setText(input);
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;

            case R.id.sex_layout:
                dialogChoice();
                break;
            case R.id.school_layout:
                final EditText et_school = new EditText(this);
                final TextView tv_school= (TextView)findViewById(R.id.school_tv);
                new AlertDialog.Builder(this).setTitle("填写学校")
                        .setIcon(R.drawable.ic_smile)
                        .setView(et_school)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String input = et_school.getText().toString();
                                if (input.equals("")) {
                                    Toast.makeText(getApplicationContext(), "学校不能为空！" + input, Toast.LENGTH_LONG).show();
                                }
                                else {
                                    tv_school.setText(input);
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.contactMethod_layout:
                final EditText et_contactMethod = new EditText(this);
                final TextView tv_contactMethod = (TextView) findViewById(R.id.contactMethod_tv);
                new AlertDialog.Builder(this).setTitle("填写联系方式")
                        .setIcon(R.drawable.ic_smile)
                        .setView(et_contactMethod)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String input = et_contactMethod.getText().toString();
                                if (input.equals("")) {
                                    Toast.makeText(getApplicationContext(), "联系方式不能为空！" + input, Toast.LENGTH_LONG).show();
                                }
                                else {
                                    tv_contactMethod.setText(input);
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;

        }

    }

    private void dialogList() {
        final String items[] = {"Take a photo", "Select from album"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择图片");
        // builder.setMessage("是否确认退出?"); //设置内容
        builder.setIcon(R.drawable.ic_smile);
        // 设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }
        });
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void dialogChoice() {
        final String items[] = {"男", "女"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择性别");
        builder.setIcon(R.drawable.ic_smile);
        builder.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    final TextView tv_sex=(TextView) findViewById(R.id.sex_tv);
                    @Override
                   public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case 0:
                                tv_sex.setText(items[0]);
                                break;
                            case 1:
                                tv_sex.setText(items[1]);
                                break;
                        }
                    }
                });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
