package com.notebook.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class DatabaseOperation {
    private SQLiteDatabase db;
    private Context context;

    public DatabaseOperation(Context context, SQLiteDatabase db) {
        this.db = db;
        this.context = context;
    }

    // 数据库的打开或创建
    public void create_db() {
        // 创建或打开数据库mynotes.db3
        db = SQLiteDatabase.openOrCreateDatabase(context.getFilesDir()
                .toString() + "/mynotes.db3", null);
        if (db == null) {//判断数据库是否创建成功
            Toast.makeText(context, "数据库创建不成功", Toast.LENGTH_LONG).show();
        }
        // 创建表,名称为notes,主键为_id
        db.execSQL("create table if not exists notes(_id integer NOT NULL primary key autoincrement ,"
                + "title text,"//标题title
                + "context text,"//内容context
                + "time varchar(20),"//创建时间time
                + "datatype text,"//是否设置了提醒时间datatype
                + "datatime varchar(20),"//提醒时间datatype
                + "locktype text,"//是否添加了锁locktype
                + "lock text" + ")");//锁秘密lock
    }

    //插入日记信息到数据库
    public void insert_db(String title, String text, String time,
                          String datatype, String datatime, String locktype, String lock) {
        if (text.isEmpty()) {//判断是否有内容
            Toast.makeText(context, "内容不能为空", Toast.LENGTH_LONG).show();
        } else {
            db.execSQL("insert into notes(title,context,time,datatype,datatime,locktype,lock) values('"
                    + title//日记标题
                    + "','"
                    + text//日记内容
                    + "','"
                    + time//日记添加时间
                    + "','"
                    + datatype//日记是否设置了时间提醒
                    + "','"
                    + datatime//日记提醒时间
                    + "','"
                    + locktype//日记是否设置类日记锁
                    + "','"
                    + lock//日记锁密码
                    + "');");
        }
    }
    //根据id更新数据库内容信息
    public void update_db(String title, String text, String time,
                          String datatype, String datatime, String locktype, String lock,
                          int item_ID) {
        if (text.isEmpty()) {
            Toast.makeText(context, "各字段不能为空", Toast.LENGTH_LONG).show();
        } else {
            db.execSQL("update notes set context='" + text + "',title='"
                    + title + "',time='" + time + "',datatype='" + datatype
                    + "',datatime='" + datatime + "',locktype='" + locktype
                    + "',lock='" + lock + "'where _id='" + item_ID + "'");
            Toast.makeText(context, "修改成功", Toast.LENGTH_LONG).show();
        }
    }
    //查询所有日记内容
    public Cursor query_db() {
        Cursor cursor = db.rawQuery("select * from notes", null);
        return cursor;
    }
    //根据数据id查询数据内容
    public Cursor query_db(int item_ID) {
        Cursor cursor = db.rawQuery("select * from notes where _id='" + item_ID
                + "';", null);
        return cursor;

    }

    // select * from 表名 where name like '%abc%'//模糊查询
    public Cursor query_db(String keword) {
        Cursor cursor = db.rawQuery("select * from notes where context like '%"
                + keword + "%';", null);
        return cursor;
    }

    // select * from 表名 where 时间 between '开始时间' and '结束时间'//时间段查询
    public Cursor query_db(String starttime, String endtime) {
        Cursor cursor = db.rawQuery("select * from notes where time >='" + starttime + "'and time<='"
                + endtime + "';", null);
        return cursor;
    }

    // 删除某一条数据
    public void delete_db(int item_ID) {
        db.execSQL("delete from notes where _id='" + item_ID + "'");
    }

    // 关闭数据库
    public void close_db() {
        db.close();
    }
}
