package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.recycler_view_test;


public class TableItems {
    public static final String NAME = TableItems.class.getSimpleName().toLowerCase();
    public static final String _ID = "_id";
    public static final String TEXT = "text";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + NAME +
                    " ( " +
                    _ID + " integer primary key autoincrement, " +
                    TEXT + " text " +
                    " ); ";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + NAME;
    public static String[] Columns = new String[]{_ID, TEXT};
}