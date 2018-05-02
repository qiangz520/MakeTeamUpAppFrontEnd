package com.example.qiang.maketeamapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.qiang.maketeamapp.navActivity.HistoryJoin;
import com.example.qiang.maketeamapp.navActivity.LogActivity;
import com.example.qiang.maketeamapp.navActivity.MyLike;
import com.example.qiang.maketeamapp.navActivity.MyTeam;
import com.example.qiang.maketeamapp.navActivity.PersonInfo;
import com.example.qiang.maketeamapp.navActivity.SystemMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;  //滑动菜单

    private Team_kind[] kinds = {new Team_kind("学习", R.drawable.study),
            new Team_kind("竞赛", R.drawable.competition), new Team_kind("运动", R.drawable.sports),
            new Team_kind("户外", R.drawable.outdoors), new Team_kind("娱乐", R.drawable.funtime),
            new Team_kind("拼团", R.drawable.groupbook)};  //运动分类

    private List<Team_kind> kindList = new ArrayList<>();
    private KindAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//加载布局
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);//nav
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_house);
        }
        navView.setCheckedItem(R.id.personal_info);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
               switch(item.getItemId()){
                   case R.id.personal_info:
                       Intent intent1=new Intent(MainActivity.this, PersonInfo.class);
                       startActivity(intent1);
                       break;
                   case R.id.groups:
                       Intent intent2=new Intent(MainActivity.this, MyTeam.class);
                       startActivity(intent2);
                       break;
                   case R.id.finished:
                       Intent intent3=new Intent(MainActivity.this,HistoryJoin.class);
                       startActivity(intent3);
                       break;
                   case R.id.notifications:
                       Intent intent4=new Intent(MainActivity.this, SystemMessage.class);
                       startActivity(intent4);
                       break;
                   case R.id.my_like_:
                       Intent intent5=new Intent(MainActivity.this, MyLike.class);
                       startActivity(intent5);
                       break;
                   case R.id.user_login:
                       Intent intent6=new Intent(MainActivity.this,LogActivity.class);
                       startActivity(intent6);
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
                Toast.makeText(MainActivity.this, "添加组队活动", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,AddTeam.class);
                startActivity(intent);
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
                break;
            case R.id.search:
                Toast.makeText(this,"You clicked search",Toast.LENGTH_SHORT).show();
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
}
