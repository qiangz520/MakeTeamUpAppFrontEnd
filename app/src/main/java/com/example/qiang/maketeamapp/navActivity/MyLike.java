package com.example.qiang.maketeamapp.navActivity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.qiang.maketeamapp.R;
import com.example.qiang.maketeamapp.Team_kind;

import java.util.ArrayList;
import java.util.List;

import classes.LikeKindAdapter;

public class MyLike extends AppCompatActivity {
    private List<Team_kind> likeList=new ArrayList<>();

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
        Team_kind studyL = new Team_kind("学习",R.drawable.ic_like_untouched);
        likeList.add(studyL);
        Team_kind competitionL = new Team_kind("竞赛",R.drawable.ic_like_untouched);
        likeList.add(competitionL);
        Team_kind sportsL = new Team_kind("运动",R.drawable.ic_like_untouched);
        likeList.add(sportsL);
        Team_kind funtimeL = new Team_kind("娱乐",R.drawable.ic_like_untouched);
        likeList.add(funtimeL);
        Team_kind outsidesL = new Team_kind("户外",R.drawable.ic_like_untouched);
        likeList.add(outsidesL);
        Team_kind groupbookL = new Team_kind("拼团",R.drawable.ic_like_untouched);
        likeList.add(groupbookL);
    }
}
