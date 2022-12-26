package com.example.moneylist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.moneylist.database.MoneyBaseHelper;
import com.example.moneylist.database.MoneyCursorWrapper;
import com.example.moneylist.database.MoneyDbSchema;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MoneyLab {
    private static MoneyLab sMoneyLab;
    private Context mContext;
    private SQLiteDatabase mDataBase;
    private List<Amoney> mMoneys;

    private MoneyLab(Context context){

        mContext = context.getApplicationContext();
        mDataBase = new MoneyBaseHelper(mContext).getWritableDatabase();

//        mMoneys = new ArrayList<>();
//        for(int i=0;i<100;i++){
//            Amoney amoney=new Amoney();
//            amoney.setTitle("支出");
//            amoney.setMethod("吃饭");
//            amoney.setMoney("1");
//            amoney.setNotes("111");
//            mMoneys.add(amoney);
//        }
    }
    public static MoneyLab get(Context context){
        if(sMoneyLab == null){
            sMoneyLab = new MoneyLab(context);
        }
        return sMoneyLab;
    }



    public List<Amoney> getMoneys(){
//        return mMoneys;
        //数据库方法
        List<Amoney> moneys = new ArrayList<>();

        MoneyCursorWrapper cursor = query(null,null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                moneys.add(cursor.getMoney());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return moneys;
    }

    public Amoney getAmoney(UUID id){
//        Amoney money = new Amoney();
//        for(Amoney amoney : mMoneys){
//            if(amoney.getId().equals(id)){
//                money = amoney;
//            }
//        }
//        return money;
        MoneyCursorWrapper cursor = query(MoneyDbSchema.MonetTable.Cols.UUID+ "=?",new String[]{id.toString()});
        try{
            if(cursor.getCount()==0){
                return null;
            }
            cursor.moveToNext();
            return cursor.getMoney();
        }finally {
            cursor.close();
        }
    }




    //添加
    public void addAmoney(Amoney amoney){
//        mMoneys.add(amoney);
        ContentValues values = getContentValues(amoney);
        mDataBase.insert(MoneyDbSchema.MonetTable.NAME,null,values);
    }
    //删除
    public void removeAmoney(Amoney amoney){
//        mMoneys.remove(amoney);
        String id = amoney.getId().toString();
        mDataBase.delete(MoneyDbSchema.MonetTable.NAME, MoneyDbSchema.MonetTable.Cols.UUID +"=?",new String[]{id});

    }

    //更新
    public void updateMoney(Amoney amoney){
        String uuidString = amoney.getId().toString();
        ContentValues values = getContentValues(amoney);

        mDataBase.update(MoneyDbSchema.MonetTable.NAME,values,
                MoneyDbSchema.MonetTable.Cols.UUID+"=?",new String[]{uuidString});
    }


    //指针初始化
    private MoneyCursorWrapper query(String whereCalues,String[] whereArgs){
        //指针初始化
        Cursor cursor = mDataBase.query(
                MoneyDbSchema.MonetTable.NAME,
                null,
                whereCalues,
                whereArgs,
                null,
                null,
                null
        );
                return new MoneyCursorWrapper(cursor);
    }
    private static ContentValues getContentValues(Amoney money){
        ContentValues values = new ContentValues();
        values.put(MoneyDbSchema.MonetTable.Cols.UUID,money.getId().toString());
        values.put(MoneyDbSchema.MonetTable.Cols.TITLE,money.getTitle());
        values.put(MoneyDbSchema.MonetTable.Cols.Money,money.getMoney());
        values.put(MoneyDbSchema.MonetTable.Cols.Date,money.getDate().getTime());
        values.put(MoneyDbSchema.MonetTable.Cols.Method,money.getMethod());
        values.put(MoneyDbSchema.MonetTable.Cols.Notes,money.getNotes());
        values.put(MoneyDbSchema.MonetTable.Cols.Contanter,money.getmContanter());
        return values;
    }
    public File getPhotoFile(Amoney amoney){
        File fileDir = mContext.getFilesDir();
        return new File(fileDir,amoney.getPhotoFilename());
    }

}
