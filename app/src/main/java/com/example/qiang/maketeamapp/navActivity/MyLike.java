package com.example.qiang.maketeamapp.navActivity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.qiang.maketeamapp.R;
import bean.TeamKind;

import java.util.ArrayList;
import java.util.List;

import adapters.LikeKindAdapter;

public class MyLike extends AppCompatActivity {
    private List<TeamKind> likeList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_like);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initLikes();
        RecyclerView recyclerView =(RecyclerView)findViewById(R.id.my_like_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        LikeKindAdapter lAdapter = new LikeKindAdapter(likeList);
        recyclerView.setAdapter(lAdapter);
//        System.out.println("zengq:"+lAdapter.getItemCount());
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
    private void initLikes(){
        TeamKind studyL = new TeamKind("学习",R.drawable.ic_like_untouched);
        likeList.add(studyL);
        TeamKind competitionL = new TeamKind("竞赛",R.drawable.ic_like_untouched);
        likeList.add(competitionL);
        TeamKind sportsL = new TeamKind("运动",R.drawable.ic_like_untouched);
        likeList.add(sportsL);
        TeamKind funtimeL = new TeamKind("娱乐",R.drawable.ic_like_untouched);
        likeList.add(funtimeL);
        TeamKind outsidesL = new TeamKind("户外",R.drawable.ic_like_untouched);
        likeList.add(outsidesL);
        TeamKind groupbookL = new TeamKind("拼团",R.drawable.ic_like_untouched);
        likeList.add(groupbookL);
    }
}
