package com.example.administrator.automaticrollcall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private ImageButton import_student;
    private ImageButton history;
    private ImageButton check_student;
    private ImageButton statistics;
    private ImageButton tongji;
    private ImageButton tongzhi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        import_student = (ImageButton) findViewById(R.id.import_student);
        history = (ImageButton) findViewById(R.id.history);
        check_student = (ImageButton) findViewById(R.id.check_student);
        statistics = (ImageButton) findViewById(R.id.statistics);
        tongji = (ImageButton) findViewById(R.id.tongji);
        tongzhi = (ImageButton) findViewById(R.id.tongzhi);
        //导入班级信息模块
        import_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Classmanger.class);
                startActivity(intent);
                //finish();

            }
        });
        //名单管理模块
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Numbermanger.class);
                startActivity(intent);
               // finish();
            }
        });
        //点名模块
        check_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Checkstudent.class);
                startActivity(intent);
                //finish();
            }
        });
        //出勤记录模块
        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Attendancerecord.class);
                startActivity(intent);
                //finish();
            }
        });
        //缺勤统计模块
        tongji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,tongji.class);
                startActivity(intent);
            }
        });
        //相关通知模块
        tongzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,tongzhi.class);
                startActivity(intent);
            }
        });



    }
}
