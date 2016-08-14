package com.example.administrator.automaticrollcall;

import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Checkstudent extends AppCompatActivity {
    Button dianming,shoudong,send;
    Mydatbasehelper dbHelper;
    EditText record,class_num,student_num,student_name,ischeck,daoyuanhaoma;
    SmsManager smanger;
    BluetoothAdapter mybluetoothAdapter;
    ArrayList<String> list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkstudent);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back);

        mybluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        smanger = SmsManager.getDefault();
        daoyuanhaoma = (EditText) findViewById(R.id.daoyuanhaoma);
        send = (Button) findViewById(R.id.send);
        student_num = (EditText) findViewById(R.id.student_num);
        student_name = (EditText) findViewById(R.id.student_name);
        ischeck = (EditText) findViewById(R.id.ischeck);
        class_num = (EditText) findViewById(R.id.class_num);
        shoudong  = (Button) findViewById(R.id.shoudong);
        dianming = (Button) findViewById(R.id.dianming);
        record = (EditText) findViewById(R.id.record);
        //ceshi = (EditText) findViewById(R.id.ceshi);
        dbHelper = new Mydatbasehelper(this,"myStudy.db3",1);
        list1 = new ArrayList<String>();

        if (mybluetoothAdapter == null) {
            Toast.makeText(Checkstudent.this, "此设备不支持蓝牙", Toast.LENGTH_LONG).show();
        }
        //请求打开蓝牙
        if (!mybluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 0);
        }

        Cursor cursor2 = dbHelper.getReadableDatabase().rawQuery("select * from tab where studentnum = ?"
                ,new String[]{"2012040101325"});


        dianming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mybluetoothAdapter.startDiscovery();
                final BroadcastReceiver mbroadcastReceiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String action = intent.getAction();
                        if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)){
                            Toast.makeText(Checkstudent.this,"正在扫描",Toast.LENGTH_SHORT).show();
                        }else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                            String devicename = device.getName();
                            //System.out.println(device.getName());
                            Cursor cursor = dbHelper.getReadableDatabase().rawQuery("select classnum,studentnum,studentname," +
                                    "telphone from study where lanya = ?", new String[]{devicename});
                            if (cursor.getCount() != 0) {
                                while (cursor.moveToNext()) {
                                    insertData(dbHelper.getReadableDatabase(), cursor.getString(cursor.getColumnIndex("classnum"))
                                            , cursor.getString(cursor.getColumnIndex("studentnum"))
                                            , cursor.getString(cursor.getColumnIndex("studentname"))
                                            , "yes"
                                            , record.getText().toString()
                                            , cursor.getString(cursor.getColumnIndex("telphone")));
                                    //ceshi.setText(cursor.getString(cursor.getColumnIndex("telphone")));

                                }
                            }
                        }else if(action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)){
                            String record_id = record.getText().toString();
                            //sendlist.add(daoyuanhaoma.getText().toString());

                            Cursor cursor1 = dbHelper.getReadableDatabase().rawQuery(
                                    "select classnum,studentnum,studentname,telphone from study where studentnum NOT IN (" +
                                            "select studentnum from record where recordid = ?)", new String[]{record_id});
                            while (cursor1.moveToNext()) {
                                insertData(dbHelper.getReadableDatabase()
                                        , cursor1.getString(cursor1.getColumnIndex("classnum"))
                                        , cursor1.getString(cursor1.getColumnIndex("studentnum"))
                                        ,cursor1.getString(cursor1.getColumnIndex("studentname"))
                                        , "no"
                                        , record.getText().toString()
                                        ,cursor1.getString(cursor1.getColumnIndex("telphone")));
                                Cursor cursor2 = dbHelper.getReadableDatabase().rawQuery("select * from tab where studentnum = ?"
                                        ,new String[]{cursor1.getString(cursor1.getColumnIndex("studentnum"))});
                                if(cursor2.getCount() == 0){
                                    dbHelper.getReadableDatabase().execSQL("insert into tab values(null,?,?,?)"
                                            ,new String[]{cursor1.getString(cursor1.getColumnIndex("studentname"))
                                            ,cursor1.getString(cursor1.getColumnIndex("studentnum")),"1"});

                                }else{
                                    while(cursor2.moveToNext()){
                                        int times = Integer.parseInt(cursor2.getString(cursor2.getColumnIndex("timess")));
                                        ContentValues values = new ContentValues();
                                        values.put("timess",Integer.toString(times+1));
                                        int result = dbHelper.getReadableDatabase().update("tab",values,"studentnum = ?"
                                                ,new String[]{cursor1.getString(cursor1.getColumnIndex("studentnum"))});
                                    }
                                }


                            }
                            Toast.makeText(Checkstudent.this,"点名结束",Toast.LENGTH_LONG).show();
                        }
                        //context.unregisterReceiver(this);
                    }
                };
                IntentFilter filter = new IntentFilter();
                filter.addAction(BluetoothDevice.ACTION_FOUND);
                filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
                filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
                registerReceiver(mbroadcastReceiver, filter);
            }
        });

//手动点名
        shoudong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = null;
                String studentnumber = student_name.getText().toString();
                Cursor  cursor = dbHelper.getReadableDatabase().rawQuery("select telphone from study where studentnum = ?", new String[]{studentnumber});
                while(cursor.moveToNext()){
                       phone = cursor.getString(cursor.getColumnIndex("telphone"));}
                ContentValues values = new ContentValues();
                values.put("classnum",class_num.getText().toString());
                values.put("studentnum",student_num.getText().toString());
                values.put("studentname",student_name.getText().toString());
                values.put("ischecked",ischeck.getText().toString());
                values.put("recordid",record.getText().toString());
                values.put("telphone", phone);
                int result = dbHelper.getReadableDatabase().update("record",values,
                        "studentnum = ? AND recordid = ?",new String[]{student_num.getText().toString(),record.getText().toString()});

            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = new ArrayList<String>();
                String geidaoyuandexiaoxi =null;
                String record_id = record.getText().toString();

                Cursor cursor = dbHelper.getReadableDatabase().rawQuery("select classnum,studentnum,studentname,telphone " +
                        "from record where ischecked = ? AND recordid = ?",new String[]{"no",record_id});
                while (cursor.moveToNext()){
                    PendingIntent pi = PendingIntent.getActivity(Checkstudent.this,0,new Intent(),0);
                    smanger.sendTextMessage(cursor.getString(cursor.getColumnIndex("telphone")),
                            null, "你缺席了今天的课程", pi, null);
//                    System.out.println(cursor.getString(cursor.getColumnIndex("studentname"))+"niquexile jintian de cheng");

                    String string = cursor.getString(cursor.getColumnIndex("classnum"))+"班的"+
                            cursor.getString(cursor.getColumnIndex("studentnum"))+
                            cursor.getString(cursor.getColumnIndex("studentname"));
                    geidaoyuandexiaoxi += string;

                }
                PendingIntent pi = PendingIntent.getActivity(Checkstudent.this,0,new Intent(),0);
                smanger.sendTextMessage(daoyuanhaoma.getText().toString(),
                        null,geidaoyuandexiaoxi+"这些同学缺席了这次的课程",pi,null);
//                System.out.println(geidaoyuandexiaoxi+"这些同学缺席了这次的课程");

                dbHelper.getReadableDatabase().execSQL("insert into attendance values(null,?)"
                        ,new String[]{record.getText().toString()});

            }
        });

    }


    private void insertData(SQLiteDatabase db,String classnum,String studentnum,String studentname,String ischecked,String recordid,String phone){
        db.execSQL("insert into record values(null,?,?,?,?,?,?)"
        ,new String[]{classnum,studentnum,studentname,ischecked,recordid,phone});
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dbHelper != null){
            dbHelper.close();
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
