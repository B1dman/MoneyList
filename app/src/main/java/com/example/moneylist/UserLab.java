package com.example.moneylist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.moneylist.database.MoneyBaseHelper;
import com.example.moneylist.database.MoneyCursorWrapper;
import com.example.moneylist.database.MoneyDbSchema;
import com.example.moneylist.database.UserCursorWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserLab {
    private static UserLab userLab;
    private Context mContext;
    private SQLiteDatabase mDataBase;
    private UserLab(Context context){
        mContext = context.getApplicationContext();
        mDataBase = new MoneyBaseHelper(mContext).getWritableDatabase();
    }

    public static UserLab get(Context context){
        if(userLab == null){
            userLab = new UserLab(context);
        }
        return userLab;
    }
    public List<User> getUsers(){
//        return mMoneys;
        //数据库方法
        List<User> users = new ArrayList<>();

        UserCursorWrapper cursor = query(null,null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                users.add(cursor.getUser());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return users;
    }

    public User getUser(String username){
        UserCursorWrapper cursor = query(MoneyDbSchema.MonetTable.Cols.username+ "=?",new String[]{username});
        try{
            if(cursor.getCount()==0){
                return null;
            }
            cursor.moveToNext();
            return cursor.getUser();
        }finally {
            cursor.close();
        }
    }

    //判断登录成功
    public boolean isMatch(String username,String password){
        boolean flag = false;
        for(int i=0;i<getUsers().size();i++ ){
            User user = getUsers().get(i);
            if(getUser(username).getUsername().equals(user.getUsername())&&getUser(username).getPassword().equals(password)){
                flag = true;
            }
        }
        return flag;
    }

    //添加
    public void addUser(User user){
//        mMoneys.add(amoney);
        ContentValues values = getContentValues(user);
        System.out.println("添加成功");
        mDataBase.insert(MoneyDbSchema.MonetTable.NAME2,null,values);
    }

    //指针初始化
    private UserCursorWrapper query(String whereCalues, String[] whereArgs){
        //指针初始化
        Cursor cursor = mDataBase.query(
                MoneyDbSchema.MonetTable.NAME2,
                null,
                whereCalues,
                whereArgs,
                null,
                null,
                null
        );
        return new UserCursorWrapper(cursor);
    }
    private static ContentValues getContentValues(User user){
        ContentValues values = new ContentValues();
        values.put(MoneyDbSchema.MonetTable.Cols.UserId,user.getMid().toString());
        values.put(MoneyDbSchema.MonetTable.Cols.username,user.getUsername());
        values.put(MoneyDbSchema.MonetTable.Cols.password,user.getPassword());

        return values;
    }


}
