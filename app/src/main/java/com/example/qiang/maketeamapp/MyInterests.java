package com.example.qiang.maketeamapp;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import HttpTool.HttpUtil;
import adapters.IssuedActivityAdapter;
import bean.IssuedActivityClass;
import bean.TeamInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static classes.Constant.URL_Recommend;

public class MyInterests extends AppCompatActivity {

    private RecyclerView recyclerView_recommend;
    private IssuedActivityAdapter activityAdapter;
    private List<IssuedActivityClass> recommendList =new ArrayList<>();
    private GridLayoutManager layoutManager= new GridLayoutManager(this,1);
    private TextView tv_recommend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_interests);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_my_interest);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        tv_recommend=(TextView)findViewById(R.id.recommend_tv);

        SharedPreferences pref  = getSharedPreferences("Token",MODE_PRIVATE);
        final String token=pref.getString("Token","");//获取token
        HashMap<String,String>params=new HashMap<>();
        params.put("token",token);
        try {
            String completedURL= HttpUtil.getURLWithParams(URL_Recommend,params);
            HttpUtil.sendOkHttpRequestGet(completedURL, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseData = response.body().string();
                    Message message = new Message();
                    message.obj = responseData;
                    mHandler_recommend.sendMessage(message);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    Handler mHandler_recommend=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String recommendResponseStr=msg.obj.toString();
            if(recommendResponseStr.equals("]")) {
                tv_recommend.setText("你尚未发布过活动，无法推荐类似活动!");
                Toast.makeText(MyInterests.this, "暂无相关推荐！", Toast.LENGTH_SHORT).show();
            } else {
                Gson gson = new Gson();
                List<TeamInfo> teamInfoList = gson.fromJson(recommendResponseStr, new TypeToken<List<TeamInfo>>() {
                }.getType());
                recommendList.clear();
                for (TeamInfo teamInfo : teamInfoList) {
                    IssuedActivityClass activityClass = new IssuedActivityClass();
                    activityClass.setWho_issue(teamInfo.getIssuerNickName() + " 在" + teamInfo.getIssueTime() + " 发布了活动");
                    activityClass.setActivityID(String.valueOf(teamInfo.getActivityID()));
                    activityClass.setTitle("标题:" + teamInfo.getTitle());
                    activityClass.setDescription("描述:" + teamInfo.getDescription());
                    activityClass.setPlace("地点:" + teamInfo.getPlace());
                    activityClass.setDemand("要求:" + teamInfo.getDemand());
                    activityClass.setStartTime("时间:" + teamInfo.getStartTime());
                    activityClass.setContactMethod("联系方式:" + teamInfo.getContactMethod());
                    activityClass.setJoinMessage("共需人数:" + teamInfo.getMaxNumber() + "人\n已有人数:" + teamInfo.getJoinedNumber() + "人");
                    recommendList.add(activityClass);
                }
                tv_recommend.setText("根据你近期发布的活动，猜你可能会喜欢:");
                recyclerView_recommend = (RecyclerView) findViewById(R.id.interest_recyclerView);
                recyclerView_recommend.setLayoutManager(layoutManager);
                activityAdapter = new IssuedActivityAdapter(recommendList);
                recyclerView_recommend.setAdapter(activityAdapter);
            }
        }
    };

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
