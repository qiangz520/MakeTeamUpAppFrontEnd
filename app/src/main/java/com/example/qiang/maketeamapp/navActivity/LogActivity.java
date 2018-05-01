package com.example.qiang.maketeamapp.navActivity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.qiang.maketeamapp.R;
import com.example.qiang.maketeamapp.RegActivity;

import org.w3c.dom.Text;

public class LogActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar6);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        tv_reg=(TextView)findViewById(R.id.click_to_register);
        tv_reg.setOnClickListener(this);
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
    public void onClick(View v){
        switch (v.getId()){
            case R.id.click_to_register:
                Intent intent1=new Intent(LogActivity.this, RegActivity.class);
                startActivity(intent1);
                break;

        }
    }
}
