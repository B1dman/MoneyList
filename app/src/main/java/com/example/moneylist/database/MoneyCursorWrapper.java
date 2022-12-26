package com.example.moneylist.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.moneylist.Amoney;
import com.example.moneylist.User;

import java.util.Date;
import java.util.UUID;

public class MoneyCursorWrapper extends CursorWrapper {
    public MoneyCursorWrapper(Cursor cursor){
        super(cursor);
    }
    public Amoney getMoney(){
        String uuidString = getString(getColumnIndex(MoneyDbSchema.MonetTable.Cols.UUID));
        String title = getString(getColumnIndex(MoneyDbSchema.MonetTable.Cols.TITLE));
        String money = getString(getColumnIndex(MoneyDbSchema.MonetTable.Cols.Money));
        long date = getLong(getColumnIndex(MoneyDbSchema.MonetTable.Cols.Date));
        String method = getString(getColumnIndex(MoneyDbSchema.MonetTable.Cols.Method));
        String notes = getString(getColumnIndex(MoneyDbSchema.MonetTable.Cols.Notes));
        String contanter = getString(getColumnIndex(MoneyDbSchema.MonetTable.Cols.Contanter));

        Amoney amoney = new Amoney(UUID.fromString(uuidString));
        amoney.setDate(new Date(date));
        amoney.setTitle(title);
        amoney.setMoney(money);
        amoney.setMethod(method);
        amoney.setNotes(notes);
        amoney.setmContanter(contanter);
        return amoney;
    }

}
