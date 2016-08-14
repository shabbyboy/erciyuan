package com.example.administrator.automaticrollcall;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Numbermanger extends AppCompatActivity {
    EditText class_num,student_num,student_name,lanya,phone;
    Button add;
    Mydatbasehelper dbhelper;

    EditText xuehao,cnumber,snumber,sname,lany,phone1;
    Button del,check,change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbermanger);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back);

        class_num = (EditText) findViewById(R.id.class_num);
        student_num = (EditText) findViewById(R.id.student_num);
        student_name = (EditText) findViewById(R.id.student_name);
        lanya = (EditText) findViewById(R.id.lanya);
        phone = (EditText) findViewById(R.id.phone);
        add = (Button) findViewById(R.id.add);

        xuehao  = (EditText) findViewById(R.id.xuhao);
        cnumber = (EditText) findViewById(R.id.cnumber);
        snumber = (EditText) findViewById(R.id.snumber);
        sname = (EditText) findViewById(R.id.sname);
        lany = (EditText) findViewById(R.id.lany);
        check = (Button) findViewById(R.id.check);
        del = (Button) findViewById(R.id.del);
        phone1 = (EditText) findViewById(R.id.phone1);
        change = (Button) findViewById(R.id.change);


        dbhelper = new Mydatbasehelper(this,"myStudy.db3",1);

        //手动导入学生信息
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String classnum = class_num.getText().toString();
                String studentnum = student_num.getText().toString();
                String studentname = student_name.getText().toString();
                String ly = lanya.getText().toString();
                String phonenumber = phone.getText().toString();
                insertData(dbhelper.getReadableDatabase(),classnum,studentnum,studentname,ly,phonenumber);
                Toast.makeText(Numbermanger.this,"添加成功",Toast.LENGTH_LONG).show();

            }
        });
        //查看学生信息
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String xh = xuehao.getText().toString();
                Cursor cursor = dbhelper.getReadableDatabase().rawQuery("select classnum," +
                        "studentnum,studentname,lanya,telphone" +
                        " from study where studentnum = ?",new String[]{xh});
                while (cursor.moveToNext()){
                    cnumber.setText(cursor.getString(cursor.getColumnIndex("classnum")));
                    snumber.setText(cursor.getString(cursor.getColumnIndex("studentnum")));
                    sname.setText(cursor.getString(cursor.getColumnIndex("studentname")));
                    lany.setText(cursor.getString(cursor.getColumnIndex("lanya")));
                    phone1.setText(cursor.getString(cursor.getColumnIndex("telphone")));
                }
            }
        });

        //删除学生信息
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String xh = xuehao.getText().toString();
                dbhelper.getReadableDatabase().delete("study","studentnum = ?",new String[]{xh});
                Toast.makeText(Numbermanger.this,"删除成功",Toast.LENGTH_LONG).show();
            }
        });

        //修改学生信息
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String xh = xuehao.getText().toString();
                ContentValues values = new ContentValues();
                values.put("classnum",cnumber.getText().toString());
                values.put("studentnum",snumber.getText().toString());
                values.put("studentname",sname.getText().toString());
                values.put("lanya",lany.getText().toString());
                values.put("telphone",phone1.getText().toString());
                dbhelper.getReadableDatabase().update("study", values, "studentnum = ?", new String[]{xh});
                Toast.makeText(Numbermanger.this,"修改成功",Toast.LENGTH_LONG).show();

            }
        });
    }
    private void insertData(SQLiteDatabase db,String classnum,String studentnum,String studentname,String lanya,String phone){
        db.execSQL("insert into study values(null,?,?,?,?,?)",
                new String[]{classnum, studentnum, studentname, lanya,phone});
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dbhelper != null){
            dbhelper.close();
        }
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
