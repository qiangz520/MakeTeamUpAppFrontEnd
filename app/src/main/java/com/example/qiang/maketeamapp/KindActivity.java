package com.example.qiang.maketeamapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import HttpTool.HttpUtil;
import adapters.IssuedActivityAdapter;
import bean.IssuedActivityClass;
import bean.ResponseState;
import bean.TeamInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static classes.Constant.URL_AddTeam;

public class KindActivity extends AppCompatActivity {
    public static final String KIND_NAME="kind_name";
    public static final String KIND_IMAGE_ID="kind_image_id";

    private String kindName;
    private IssuedActivityAdapter activityAdapter;
    private List<IssuedActivityClass> issuedActivityList=new ArrayList<>();
    private GridLayoutManager layoutManager= new GridLayoutManager(this,1);
    private RecyclerView recyclerView_activity_this_kind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kind);
        Intent intent = getIntent();
        kindName=intent.getStringExtra(KIND_NAME);
        int kindImageId=intent.getIntExtra(KIND_IMAGE_ID,0);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        ImageView kindImageView=(ImageView)findViewById(R.id.kind_image_view);
        TextView kindContentText=(TextView)findViewById(R.id.kind_content_text);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(kindName);
        Glide.with(this).load(kindImageId).into(kindImageView);
        String kindContent=generateKindContent(kindName);

        kindContentText.setText(kindContent);

        GetAllActivityInfoInThisKind(kindName);//请求

//        Log.e("zenq,ListCount onCreate",""+issuedActivityList.size() );

    }

    private String generateKindContent(String kindName){
        StringBuilder kindContent = new StringBuilder();

        if(kindName.equals("学习"))  kindContent.append(getString(R.string.study_content));
        if(kindName.equals("运动"))  kindContent.append(getString(R.string.sports_content));
        if(kindName.equals("户外"))  kindContent.append(getString(R.string.outsides_content));
        if(kindName.equals("竞赛"))  kindContent.append(getString(R.string.competition_content));
        if(kindName.equals("娱乐"))  kindContent.append(getString(R.string.funtime_content));
        if(kindName.equals("拼团"))  kindContent.append(getString(R.string.groupbook_content));

        return  kindContent.toString();
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

//    根据当前活动的类别向服务器发送请求获取所有的该类别下的活动
    private void GetAllActivityInfoInThisKind(String kindName){
        String originAddress=URL_AddTeam;
        HashMap<String,String>params=new HashMap<>();
        params.put("kindName",kindName);
        try {
            String completedURL=HttpUtil.getURLWithParams(originAddress,params);
            Log.e("zengq",completedURL );
            HttpUtil.sendOkHttpRequestGet(completedURL, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseData = response.body().string();
                    Message message = new Message();
                    message.obj = responseData;
                    mHandler_GetActivityInfo.sendMessage(message);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Handler mHandler_GetActivityInfo = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String activityInfoResponseStr=msg.obj.toString();
            Log.e("zengq",activityInfoResponseStr );
            Gson gson = new Gson();
            List<TeamInfo> teamInfoList=gson.fromJson(activityInfoResponseStr,new TypeToken<List<TeamInfo>>(){}.getType());
            issuedActivityList.clear();
            for(TeamInfo teamInfo:teamInfoList){
                IssuedActivityClass activityClass=new IssuedActivityClass();
                activityClass.setWho_issue(teamInfo.getIssuerNickName()+" 在"+teamInfo.getIssueTime()+" 发布了 "+kindName+"类 活动");
                activityClass.setTitle("标题:"+teamInfo.getTitle());
                activityClass.setDescription("描述:"+teamInfo.getDescription());
                activityClass.setPlace("地点:"+teamInfo.getPlace());
                activityClass.setDemand("要求:"+teamInfo.getDemand());
                activityClass.setStartTime("时间:"+teamInfo.getStartTime());
                activityClass.setContactMethod("联系方式:"+teamInfo.getContactMethod());
                activityClass.setJoinMessage("共需人数:"+teamInfo.getMaxNumber()+"人\n已有人数:"+teamInfo.getJoinedNumber()+"人");
                issuedActivityList.add(activityClass);
            }
            recyclerView_activity_this_kind=(RecyclerView)findViewById(R.id.recyclerView_all_activity);
            recyclerView_activity_this_kind.setLayoutManager(layoutManager);
            activityAdapter=new IssuedActivityAdapter(issuedActivityList);
            recyclerView_activity_this_kind.setAdapter(activityAdapter);
            Log.e("zenq,ListCount",""+issuedActivityList.size() );
        }
    };
}
