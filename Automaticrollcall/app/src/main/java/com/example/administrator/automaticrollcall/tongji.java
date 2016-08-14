package com.example.administrator.automaticrollcall;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
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
import android.widget.Toast;

public class tongji extends AppCompatActivity {
    Button cx,first,second,three;
    EditText stu;
    Mydatbasehelper dbHelper;
    SmsManager smanger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tongji);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back);


        smanger = SmsManager.getDefault();
        cx = (Button) findViewById(R.id.cx);
        first = (Button) findViewById(R.id.first);
        second = (Button) findViewById(R.id.second);
        three  = (Button) findViewById(R.id.three);
        stu = (EditText) findViewById(R.id.stu);
        dbHelper = new Mydatbasehelper(this,"myStudy.db3",1);


        cx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor cursor = dbHelper.getReadableDatabase().rawQuery("select timess from tab where studentnum = ?"
                        , new String[]{stu.getText().toString()});
                if(cursor.getCount() == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(tongji.this)
                            .setTitle("缺勤次数")
                            .setIcon(R.drawable.no)
                            .setMessage("该学生缺勤次数为:全勤");
                    builder.setPositiveButton("sure", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();

                }else{
                    while(cursor.moveToNext()){
                        AlertDialog.Builder builder = new AlertDialog.Builder(tongji.this)
                                .setTitle("缺勤次数")
                                .setIcon(R.drawable.no)
                                .setMessage("该学生缺勤次数为:" + cursor.getString(cursor.getColumnIndex("timess")) + "次");
                        builder.setPositiveButton("sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                    }
                }
            }
        });

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dbHelper.getReadableDatabase().rawQuery("select studentnum from tab where timess = ?", new String[]{"1"});
                if(cursor.getCount() == 0){
                    Toast.makeText(tongji.this, "没有缺课一次的同学", Toast.LENGTH_LONG).show();
                }else{
                      while(cursor.moveToNext()){
                        System.out.println(cursor.getString(cursor.getColumnIndex("studentnum")));
                        Cursor cursor1 = dbHelper.getReadableDatabase().rawQuery("select * from study where studentnum = ?"
                                , new String[]{cursor.getString(cursor.getColumnIndex("studentnum"))});
                          while(cursor1.moveToNext()){
                              PendingIntent pi = PendingIntent.getActivity(tongji.this, 0, new Intent(), 0);
                              smanger.sendTextMessage(cursor1.getString(cursor1.getColumnIndex("telphone")),
                                      null, "这是你的第一次缺课，我希望也是你的最后一次", pi, null);
                          }

                      }
                }


            }
        });
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dbHelper.getReadableDatabase().rawQuery("select studentnum from tab where timess = '2'", null);
                if(cursor.getCount() == 0){
                    Toast.makeText(tongji.this, "没有缺课2次的同学", Toast.LENGTH_LONG).show();
                }else{
                    while(cursor.moveToNext()){
                        System.out.println(cursor.getString(cursor.getColumnIndex("studentnum")));
                        Cursor cursor1 = dbHelper.getReadableDatabase().rawQuery("select * from study where studentnum = ?"
                                ,new String[]{cursor.getString(cursor.getColumnIndex("studentnum"))});
                        while(cursor1.moveToNext()){
                            PendingIntent pi = PendingIntent.getActivity(tongji.this, 0, new Intent(), 0);
                            smanger.sendTextMessage(cursor1.getString(cursor1.getColumnIndex("telphone")),
                                    null, "你已经缺课两次了，在缺课一次你将被取消考试资格", pi, null);
                        }
                    }
                }
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dbHelper.getReadableDatabase().rawQuery("select studentnum from tab where timess >= '3'", null);
                if(cursor.getCount() == 0){
                    Toast.makeText(tongji.this, "没有缺课3次的同学", Toast.LENGTH_LONG).show();

                }else{
                    while(cursor.moveToNext()){
                        System.out.println(cursor.getString(cursor.getColumnIndex("studentnum")));
                        Cursor cursor1 = dbHelper.getReadableDatabase().rawQuery("select * from study where studentnum = ?"
                                ,new String[]{cursor.getString(cursor.getColumnIndex("studentnum"))});
                        while(cursor1.moveToNext()){
                            PendingIntent pi = PendingIntent.getActivity(tongji.this, 0, new Intent(), 0);
                            smanger.sendTextMessage(cursor1.getString(cursor1.getColumnIndex("telphone")),
                                    null, "你已经缺课3次了，请找老师说明情况，否则你将没有这门课的成绩", pi, null);
                        }
                    }
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
