package com.example.moneylist.database;

import android.content.Context;

public class MoneyDbSchema {
    public static final class MonetTable{
        //表名
        public static final String NAME = "moneys";
        //用户名的表
        public static final String NAME2 = "users";
        //属性名
        public static final class Cols{
            public static final String UUID ="uuid";
            public static final String TITLE = "title";
            public static final String Money= "money";
            public static final String Date = "date";
            public static final String Method = "method";
            public static final String Notes = "notes";
            public static final String Contanter = "contanter";

            //用户id
            public static final String UserId = "userid";
            //用户名
            public static final String username = "username";
            //密码
            public static final String password = "password";
        }

    }
}
