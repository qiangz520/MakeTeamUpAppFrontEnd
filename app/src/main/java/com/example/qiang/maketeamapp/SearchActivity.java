package com.example.qiang.maketeamapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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

import static classes.Constant.URL_Search;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView recyclerView_search;
    private IssuedActivityAdapter activityAdapter;
    private List<IssuedActivityClass> searchResultList =new ArrayList<>();
    private GridLayoutManager layoutManager= new GridLayoutManager(this,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent= getIntent();
        String text=intent.getStringExtra("content");
        String originAddress=URL_Search;
        HashMap<String,String>params= new HashMap<>();
        params.put("text",text);
        try {
            String completedURL = HttpUtil.getURLWithParams(originAddress,params);
            HttpUtil.sendOkHttpRequestGet(completedURL, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseData = response.body().string();
                    Message message = new Message();
                    message.obj = responseData;
                    mHandler_searchInfo.sendMessage(message);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    Handler mHandler_searchInfo=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String searchInfoResponseStr=msg.obj.toString();
            if(searchInfoResponseStr.equals("]"))
                Toast.makeText(SearchActivity.this,"暂无相关组队活动！",Toast.LENGTH_SHORT).show();
            else {
                Gson gson = new Gson();
                List<TeamInfo> teamInfoList = gson.fromJson(searchInfoResponseStr, new TypeToken<List<TeamInfo>>() {
                }.getType());
                searchResultList.clear();
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
                    searchResultList.add(activityClass);
                }
                recyclerView_search = (RecyclerView) findViewById(R.id.search_recyclerView);
                recyclerView_search.setLayoutManager(layoutManager);
                activityAdapter = new IssuedActivityAdapter(searchResultList);
                recyclerView_search.setAdapter(activityAdapter);
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
