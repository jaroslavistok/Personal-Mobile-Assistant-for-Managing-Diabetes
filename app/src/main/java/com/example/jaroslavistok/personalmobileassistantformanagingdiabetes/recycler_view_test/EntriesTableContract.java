package com.example.jaroslavistok.personalmobileassistantformanagingdiabetes.recycler_view_test;


public class EntriesTableContract {
    private EntriesTableContract(){}
    public static final String TABLE_NAME = "entries";

    public static final String _ID = "_id";
    public static final String DATE = "date";
    public static final String TIME = "time";
    public static final String GLUCOSE_VALUE = "glucose_value";
    public static final String CATEGORY = "category";
    public static final String FAST_INSULIN = "fast_insulin";
    public static final String SLOW_INSULIN = "slow_insulin";
    public static final String NOTE = "note";
    public static final String SYNCHORNIZED = "synchronized";

    private static final String CREATE_STATEMENT =
            "CREATE TABLE %s ("+
                    "%s integer primary key autoincrement, " +
                    "%s text, " +
                    "%s text, " +
                    "%s text, " +
                    "%s text, " +
                    "%s text, " +
                    "%s text, " +
                    "%s text, " +
                    "%s integer " +
                    " ); ";

    public static final String CREATE_TABLE = String.format(CREATE_STATEMENT, TABLE_NAME, _ID, DATE, TIME, GLUCOSE_VALUE, CATEGORY, FAST_INSULIN, SLOW_INSULIN, NOTE, SYNCHORNIZED);
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static String[] Columns = new String[]{_ID, DATE, TIME, GLUCOSE_VALUE, CATEGORY, FAST_INSULIN, SLOW_INSULIN, NOTE, SYNCHORNIZED};
}