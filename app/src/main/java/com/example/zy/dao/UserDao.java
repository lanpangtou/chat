package com.example.zy.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.zy.Constants;
import com.example.zy.DatabaseHelper;
import com.example.zy.pojo.User;


public class UserDao {
    private final DatabaseHelper databaseHelper;

    public UserDao(Context context) {

        databaseHelper = new DatabaseHelper(context);
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    public long insert(User user){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",user.getName());
        values.put("password",user.getPassword());
        values.put("sex",user.getSex());
        values.put("address",user.getAddress());
        values.put("headImg",user.getHeadImg());
        long count = db.insert(Constants.TABLE_NAME, null, values);
        db.close();
        return count;
    }

    /**
     * 根据id删除用户
     * @param id
     */
    public void delete(Integer id){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL("delete from "+Constants.TABLE_NAME + " where id = " + id);
        db.close();
    }

    /**
     * 更新用户
     * @param user
     */
    public void update(User user){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL("update "+Constants.TABLE_NAME + " set name =" +user.getName() +",password = "+user.getPassword()+" ,sex = "+user.getSex() +" ,address = "+user.getAddress()+" ,headImg = "+user.getHeadImg()+" where id = " + user.getId());
        db.close();
    }

    /**
     * 根据用户名和密码查找用户，登录验证
     * @param name 用户名
     * @param pass  密码
     * @return
     */
    public User findByUser(String name,String pass){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + Constants.TABLE_NAME + " where name = '" +name+"' and password = "+pass, null);
        User user = new User();
        while (cursor.moveToNext()){
            //根据索引获取字段名称
            int id = cursor.getInt(0);
            String username = cursor.getString(1);
            String password = cursor.getString(2);
            user.setId(id);
            user.setName(username);
            user.setPassword(password);
        }
        db.close();
        return user;
    }
}
