package com.example.moneylist.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.moneylist.User;

import java.util.UUID;

public class UserCursorWrapper extends CursorWrapper {
    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    //用户
    public User getUser(){
        String useridString =getString(getColumnIndex(MoneyDbSchema.MonetTable.Cols.UserId));
        String username = getString (getColumnIndex(MoneyDbSchema.MonetTable.Cols.username));
        String password = getString(getColumnIndex(MoneyDbSchema.MonetTable.Cols.password));

        User user = new User(UUID.fromString(useridString));
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }
}
