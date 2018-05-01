package com.example.qiang.maketeamapp;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class KindActivity extends AppCompatActivity {
    public static final String KIND_NAME="kind_name";
    public static final String KIND_IMAGE_ID="kind_image_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kind);
        Intent intent = getIntent();
        String kindName=intent.getStringExtra(KIND_NAME);
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
        /*   StringBuilder kindContent=new StringBuilder();
        for(int i=0;i<50;i++){
            kindContent.append(kindName);
        }
        return kindContent.toString();*/
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
