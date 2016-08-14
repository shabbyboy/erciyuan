package com.example.administrator.automaticrollcall;

import android.app.AlertDialog;
import android.app.ExpandableListActivity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Attendancerecord extends AppCompatActivity {
    Mydatbasehelper dbhelper;
    LinearLayout mainlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_attendancerecord);
        mainlist = (LinearLayout) findViewById(R.id.mainlist);
        dbhelper = new Mydatbasehelper(this, "myStudy.db3", 1);

        List<String> list = new ArrayList<String>();
        Cursor cursor = dbhelper.getReadableDatabase().rawQuery("select recordid from attendance", null);
        if(cursor.getCount() == 0){
            Toast.makeText(this,"无记录",Toast.LENGTH_LONG).show();
        }else{
            while(cursor.moveToNext()){
                list.add(cursor.getString(cursor.getColumnIndex("recordid")));
            }
        }
        for(String record : list){
            View view = getview(dbhelper.getReadableDatabase(),record,mainlist);
            mainlist.addView(view);
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

    private View getview(SQLiteDatabase db,String string, final LinearLayout lyout){
        final SQLiteDatabase db1 = db;
        final String string1 = string;
        final LinearLayout line = new LinearLayout(Attendancerecord.this);
        line.setOrientation(LinearLayout.HORIZONTAL);
        TextView text = new TextView(Attendancerecord.this);
        text.setText(string);
        text.setTextSize(40);
        Button show = new Button(Attendancerecord.this);
        show.setText("查看");
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Cursor cursor = db1.rawQuery("select studentname,ischecked from record where recordid = ?", new String[]{string1});
                BaseAdapter adapter = new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return cursor.getCount();
                    }

                    @Override
                    public Object getItem(int position) {
                        return position;
                    }

                    @Override
                    public long getItemId(int position) {
                        return position;
                    }

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        cursor.moveToPosition(position);
                        CheckBox rb = new CheckBox(Attendancerecord.this);
                        String first = cursor.getString(cursor.getColumnIndex("studentname"));
                        String second = cursor.getString(cursor.getColumnIndex("ischecked"));

                        rb.setText(first);
                        if (second.equals("yes")) {
                            rb.setChecked(true);
                        }
                        return rb;
                    }
                };
                View selectview = getLayoutInflater().inflate(R.layout.listone, null);
                final ListView listView = (ListView) selectview.findViewById(R.id.listone);
                listView.setAdapter(adapter);
                new AlertDialog.Builder(Attendancerecord.this)
                        .setView(selectview)
                        .setPositiveButton("sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
            }
        });
        Button del = new Button(Attendancerecord.this);
        del.setText("删除");
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db1.delete("attendance","recordid = ?",new String[]{string1});
                db1.delete("record", "recordid = ?", new String[]{string1});
                lyout.removeView(line);
            }
        });
        Button pull = new Button(Attendancerecord.this);
        pull.setText("导出");
        pull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path0 = null;
                final Cursor cursor = db1.rawQuery("select studentname,ischecked from record where recordid = ?", new String[]{string1});
                try {
                    path0 = Environment.getExternalStorageDirectory().getCanonicalPath();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                File file = new File(path0+File.separator+string1+".xls");
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    WritableWorkbook wwb = Workbook.createWorkbook(file);
                    WritableSheet ws = wwb.createSheet("chuqing", 0);
                    Label label = new Label(0, 0, "姓名");
                    ws.addCell(label);
                    Label label1 = new Label(1, 0, "是否出勤");
                    ws.addCell(label1);
                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToPosition(i);
                        Label label2 = new Label(0, i+1, cursor.getString(cursor.getColumnIndex("studentname")));
                        ws.addCell(label2);
                        if (cursor.getString(cursor.getColumnIndex("ischecked")) == "yes") {
                            Label label3 = new Label(1, i+1, "是");
                            ws.addCell(label3);
                        } else {
                            Label label4 = new Label(1,i+1,"否");
                            ws.addCell(label4);
                        }
                    }
                    wwb.write();
                    wwb.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                Uri uri = Uri.parse("mailto:92570301@qq.com");
                Intent intent = new Intent(android.content.Intent.ACTION_SENDTO,uri);
                //intent.setType("application/octet-stream");
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "出勤记录");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, "这是今天的学生出勤记录表格");
                intent.putExtra(android.content.Intent.EXTRA_STREAM, Uri.fromFile(file));
                startActivity(intent);

            }
        });
        line.addView(text);
        line.addView(show);
        line.addView(del);
        line.addView(pull);
        return line;
    }


}


