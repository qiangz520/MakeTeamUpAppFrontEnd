package com.example.qiang.maketeamapp.navActivity;

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

import com.example.qiang.maketeamapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import HttpTool.HttpUtil;
import adapters.MyTeamAdapter;
import bean.IssuedActivityClass;
import bean.TeamInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static classes.Constant.URL_MyTeam;

public class MyTeam extends AppCompatActivity {

    private RecyclerView recyclerView_my_team;
    private MyTeamAdapter activityAdapter;
    private List<IssuedActivityClass> my_teamList =new ArrayList<>();
    private GridLayoutManager layoutManager= new GridLayoutManager(this,1);
    private TextView tv_my_team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        tv_my_team =(TextView)findViewById(R.id.my_team_tv);

        SharedPreferences pref  = getSharedPreferences("Token",MODE_PRIVATE);
        final String token=pref.getString("Token","");//获取token
        HashMap<String,String> params=new HashMap<>();
        params.put("token",token);
        try {
            String completedURL= HttpUtil.getURLWithParams(URL_MyTeam,params);
            HttpUtil.sendOkHttpRequestGet(completedURL, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseData = response.body().string();
                    Message message = new Message();
                    message.obj = responseData;
                    mHandler_my_team.sendMessage(message);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Handler mHandler_my_team =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String recommendResponseStr=msg.obj.toString();
            if(recommendResponseStr.equals("]")) {
                tv_my_team.setText("你尚未发布过活动!");
            } else {
                Gson gson = new Gson();
                List<TeamInfo> teamInfoList = gson.fromJson(recommendResponseStr, new TypeToken<List<TeamInfo>>() {
                }.getType());
                my_teamList.clear();
                for (TeamInfo teamInfo : teamInfoList) {
                    IssuedActivityClass activityClass = new IssuedActivityClass();
                    activityClass.setWho_issue("你在" + teamInfo.getIssueTime() + " 发布了活动");
                    activityClass.setActivityID(String.valueOf(teamInfo.getActivityID()));
                    activityClass.setTitle("标题:" + teamInfo.getTitle());
                    activityClass.setDescription("描述:" + teamInfo.getDescription());
                    activityClass.setPlace("地点:" + teamInfo.getPlace());
                    activityClass.setDemand("要求:" + teamInfo.getDemand());
                    activityClass.setStartTime("时间:" + teamInfo.getStartTime());
//                    activityClass.setContactMethod("联系方式:" + teamInfo.getContactMethod());
                    activityClass.setJoinMessage("共需人数:" + teamInfo.getMaxNumber() + "人\n已有人数:" + teamInfo.getJoinedNumber() + "人");
                    my_teamList.add(activityClass);
                }
                tv_my_team.setText("你发布过以下活动:");
                recyclerView_my_team = (RecyclerView) findViewById(R.id.my_team_recyclerView);
                recyclerView_my_team.setLayoutManager(layoutManager);
                activityAdapter = new MyTeamAdapter(my_teamList);
                recyclerView_my_team.setAdapter(activityAdapter);
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
