package com.example.qiang.maketeamapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qiang.maketeamapp.navActivity.HistoryJoin;
import com.example.qiang.maketeamapp.navActivity.LogActivity;
import com.example.qiang.maketeamapp.navActivity.MyLike;
import com.example.qiang.maketeamapp.navActivity.MyTeam;
import com.example.qiang.maketeamapp.navActivity.PersonInfo;
import com.example.qiang.maketeamapp.navActivity.SystemMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import HttpTool.HttpUtil;
import adapters.KindAdapter;
import bean.TeamKind;
import classes.Constant;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;  //滑动菜单

    private TeamKind[] kinds = {new TeamKind("学习", R.drawable.study),
            new TeamKind("竞赛", R.drawable.competition), new TeamKind("运动", R.drawable.sports),
            new TeamKind("户外", R.drawable.outdoors), new TeamKind("娱乐", R.drawable.funtime),
            new TeamKind("拼团", R.drawable.groupbook)};  //运动分类

    private List<TeamKind> kindList = new ArrayList<>();
    private KindAdapter adapter;

    private TextView tv_NM_header;//头部昵称
    private TextView tv_CM_header;//头部联系方式
    private CircleImageView CIW_header;

    Handler mHandler_GetHeaderInfo = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String getHeaderResponseStr = msg.obj.toString();
            try {
                JSONObject object=new JSONObject(getHeaderResponseStr);
                String headerImageStr=object.getString("headImage");
                String nickNameStr=object.getString("nickName");
                String contactMethodStr=object.getString("contactMethod");
                if(headerImageStr.equals("")){
                    CIW_header.setImageResource(R.drawable.ic_header);
                }
                else{
                    byte[] bytes = Base64.decode(headerImageStr, Base64.DEFAULT);
                    CIW_header.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                }
                if(!nickNameStr.equals("未填写"))tv_NM_header.setText(nickNameStr);
                if(!contactMethodStr.equals("未填写")) tv_CM_header.setText(contactMethodStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//加载布局
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);//nav

        SharedPreferences pref  = getSharedPreferences("Token",MODE_PRIVATE);
        final String token=pref.getString("Token","");//获取token

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_house);
        }
        navView.setCheckedItem(R.id.personal_info);


        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch(item.getItemId()){

                   case R.id.personal_info:

                       if(token.equals("")){
                           Toast.makeText(MainActivity.this,"请先登录！",Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(MainActivity.this, LogActivity.class);
                           startActivity(intent);
                       }
                       else {
                           Intent intent1=new Intent(MainActivity.this, PersonInfo.class);
                           startActivity(intent1);
                       }
                       break;
                   case R.id.groups:
                       if(token.equals("")){
                           Toast.makeText(MainActivity.this,"请先登录！",Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(MainActivity.this, LogActivity.class);
                           startActivity(intent);
                       }
                       else {
                           Intent intent2 = new Intent(MainActivity.this, MyTeam.class);
                           startActivity(intent2);
                       }
                       break;
                   case R.id.finished:
                       if(token.equals("")){
                           Toast.makeText(MainActivity.this,"请先登录！",Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(MainActivity.this, LogActivity.class);
                           startActivity(intent);
                       }
                       else {
                           Intent intent3 = new Intent(MainActivity.this, HistoryJoin.class);
                           startActivity(intent3);
                       }
                       break;
                   case R.id.notifications:
                       if(token.equals("")){
                           Toast.makeText(MainActivity.this,"请先登录！",Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(MainActivity.this, LogActivity.class);
                           startActivity(intent);
                       }
                       else {
                           Intent intent4 = new Intent(MainActivity.this, SystemMessage.class);
                           startActivity(intent4);
                       }
                       break;
                   case R.id.my_like_:
                       if(token.equals("")){
                           Toast.makeText(MainActivity.this,"请先登录！",Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(MainActivity.this, LogActivity.class);
                           startActivity(intent);
                       }
                       else {
                           Intent intent5=new Intent(MainActivity.this, MyLike.class);
                           startActivity(intent5);
                       }
                       break;
                   case R.id.user_login:
                       Intent intent6;
//                       SharedPreferences pref  = getSharedPreferences("Token",MODE_PRIVATE);
//                       String token=pref.getString("Token","");
                       if(token.equals("")) {
                           intent6 = new Intent(MainActivity.this, LogActivity.class);
                           startActivity(intent6);
                       }
                       else{
                           intent6=new Intent(MainActivity.this,LogsuccessActivity.class);
                           startActivity(intent6);
                       }
                       break;
                   default:
               }
                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);//悬浮按钮
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref  = getSharedPreferences("Token",MODE_PRIVATE);
                String token=pref.getString("Token","");
                if(token.equals("")){
                    Toast.makeText(MainActivity.this,"请先登录！",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, LogActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this, "添加组队活动", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, AddTeam.class);
                    startActivity(intent);
                }
            }
        });
        //
        initKinds();
        RecyclerView recyclerview = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerview.setLayoutManager(layoutManager);
        adapter = new KindAdapter(kindList);
        recyclerview.setAdapter(adapter);
    }

    private void initKinds(){
        kindList.clear();
        boolean [] hashTable={false,false,false,false,false,false,false,false};
        int cnt=0;
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(kinds.length);
            if(!hashTable[index]) {
                kindList.add(kinds[index]);
                hashTable[index] = true;
                cnt++;
            }
            if(cnt==7) break;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){           //标题栏
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                SharedPreferences pref  = getSharedPreferences("Token",MODE_PRIVATE);
                final String token=pref.getString("Token","");//获取token
                tv_NM_header=(TextView)findViewById(R.id.header_tv_NM);
                tv_CM_header=(TextView)findViewById(R.id.header_tv_CM);
                CIW_header=(CircleImageView)findViewById(R.id.header_CIW);
                if(!token.equals(""))GetHeaderInfo();///获取该用户的头部信息
                break;
            case R.id.search:
               // Toast.makeText(this,"You clicked search",Toast.LENGTH_SHORT).show();
                final EditText et = new EditText(this);
                new AlertDialog.Builder(this).setTitle("搜索")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String input = et.getText().toString();
                                if (input.equals("")) {
                                    Toast.makeText(getApplicationContext(), "搜索内容不能为空！" + input, Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Intent intent_to_search = new Intent();
                                    intent_to_search.putExtra("content", input);
                                    intent_to_search.setClass(MainActivity.this, SearchActivity.class);
                                    startActivity(intent_to_search);
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.interest:
                Intent intent_interest=new Intent(MainActivity.this,MyInterests.class);
                startActivity(intent_interest);
                break;
            case R.id.settings:
                Toast.makeText(this,"You clicked settings",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

    void GetHeaderInfo(){
        SharedPreferences pref  = getSharedPreferences("Token",MODE_PRIVATE);
        final String token=pref.getString("Token","");//获取token
        if(!token.equals("")) {
            String originAddress = Constant.URL_GetHeaderInfo;
            HashMap<String, String> params = new HashMap<>();

            params.put("token", token);
            try {
                String completedURL = HttpUtil.getURLWithParams(originAddress, params);
                //try okhttp3
                HttpUtil.sendOkHttpRequestGet(completedURL, new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
//                        Log.e("ResponseData: ",responseData);
                        Message message = new Message();
                        message.obj = responseData;
                        mHandler_GetHeaderInfo.sendMessage(message);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
