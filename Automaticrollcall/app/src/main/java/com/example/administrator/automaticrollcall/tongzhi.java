package com.example.administrator.automaticrollcall;

import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class tongzhi extends AppCompatActivity {
    EditText editText;
    Button send;
    Mydatbasehelper dbhelper;
    SmsManager smsmanger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tongzhi);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back);


        editText = (EditText) findViewById(R.id.editText);
        send = (Button) findViewById(R.id.send);
        dbhelper = new Mydatbasehelper(this,"myStudy.db3",1);
        smsmanger = SmsManager.getDefault();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dbhelper.getReadableDatabase().rawQuery("select telphone from study",null);
                while (cursor.moveToNext()){
                    PendingIntent pi = PendingIntent.getActivity(tongzhi.this, 0, new Intent(), 0);
                    smsmanger.sendTextMessage(cursor.getString(cursor.getColumnIndex("telphone")),
                            null, "这是你的第一次缺课，我希望也是你的最后一次", pi, null);
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
