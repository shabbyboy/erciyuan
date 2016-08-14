package com.example.administrator.automaticrollcall;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import jxl.Sheet;
import jxl.Workbook;


public class Classmanger extends AppCompatActivity {
     Button browse;
     Button import_excel,delete_class,update_class;
     EditText filename,prename,befname,delclass;
     Mydatbasehelper dbHelper;
    // SQLiteDatabase db;

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            Uri uri = data.getData();
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            String img_path = actualimagecursor.getString(actual_image_column_index);
            File file = new File(img_path);
          Toast.makeText(this, file.toString(), Toast.LENGTH_SHORT).show();

            filename.setText(file.toString());
       }
    }
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classmanger);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back);


        browse = (Button) findViewById(R.id.browse);
        import_excel = (Button) findViewById(R.id.import_excel);
        filename = (EditText) findViewById(R.id.filename);
        delete_class = (Button) findViewById(R.id.delete_class);
        update_class = (Button) findViewById(R.id.update_class);
        befname = (EditText) findViewById(R.id.befname);
        prename = (EditText) findViewById(R.id.prename);
        delclass = (EditText) findViewById(R.id.delclass);
        dbHelper = new Mydatbasehelper(this,"myStudy.db3",1);
       // db = dbHelper.getReadableDatabase();
//        browse.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("**/*//*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                startActivityForResult(intent,1);
//            }
//        });


        //查找要导入的excel文件
       browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path0 = null;
                try {
                    path0 = Environment.getExternalStorageDirectory().getCanonicalPath();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                final File[] files = new File(path0).listFiles();

                BaseAdapter adapter = new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return files.length;
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
                        CheckBox rb = new CheckBox(Classmanger.this);
                        String path = files[position].getPath();
                        rb.setText(path);
                        return rb;
                    }
                };
                View selectview = getLayoutInflater().inflate(R.layout.list,null);
                final ListView listView = (ListView) selectview.findViewById(R.id.list);
                listView.setAdapter(adapter);
                new AlertDialog.Builder(Classmanger.this)
                        .setView(selectview)
                        .setPositiveButton("sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               for(int i = 0; i<listView.getCount(); i++){
                                   CheckBox checkBox = (CheckBox) listView.getChildAt(i);
                                   if(checkBox.isChecked()){
                                       filename.setText(checkBox.getText());
                                       break;
                                   }
                               }
                            }
                        }).show();
            }
        });

        //导入名单
        import_excel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Log.i("filename",filename.getText().toString());
                    FileInputStream is = new FileInputStream(filename.getText().toString());
                    //Log.i("filename",filename.getText().toString());
                    Workbook wb = Workbook.getWorkbook(is);
                    Sheet sheet = wb.getSheet(0);
                    for(int i = 1; i< sheet.getRows(); ++i){
                        String classnum = sheet.getCell(0,i).getContents();
                        String studentnum = sheet.getCell(1,i).getContents();
                        String studentname = sheet.getCell(2,i).getContents();
                        String lanya = sheet.getCell(3,i).getContents();
                        String phone = sheet.getCell(4,i).getContents();
                        //System.out.println(classnum+studentname+studentnum+lanya);
                        insertData(dbHelper.getReadableDatabase(), classnum, studentnum, studentname, lanya, phone);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(Classmanger.this, "导入名单成功", Toast.LENGTH_LONG).show();

            }
        });
        //修改班级信息
        update_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("classnum", befname.getText().toString());
                int result = dbHelper.getReadableDatabase().update("study", values, "classnum = ?"
                        , new String[]{prename.getText().toString()});
                Toast.makeText(Classmanger.this, "修改信息成功", Toast.LENGTH_LONG).show();

            }
        });


        //删除班级信息
        delete_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getReadableDatabase().delete("study", "classnum = ?"
                        , new String[]{delclass.getText().toString()});
                Toast.makeText(Classmanger.this, "删除信息成功", Toast.LENGTH_LONG).show();

            }
        });

    }
    //更新数据库班级信息

    //插入数据
    private void insertData(SQLiteDatabase db,String classnum,String studentnum,String studentname,String lanya, String phone) {
        db.execSQL("insert into study values(null,?,?,?,?,?)",
                new String[]{classnum,studentnum,studentname,lanya,phone});
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
