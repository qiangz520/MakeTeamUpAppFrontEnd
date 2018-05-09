package com.example.qiang.maketeamapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.qiang.maketeamapp.navActivity.LogActivity;

public class LogsuccessActivity extends AppCompatActivity {
    private Button logoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logsuccess);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_log_success);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        logoutButton=(Button)findViewById(R.id.log_out_button);
        logoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent_log_out=new Intent(LogsuccessActivity.this, LogActivity.class);
                startActivity(intent_log_out);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId()){
            case android.R.id.home:
                Intent intent_to_main=new Intent(LogsuccessActivity.this,MainActivity.class);
                startActivity(intent_to_main);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
