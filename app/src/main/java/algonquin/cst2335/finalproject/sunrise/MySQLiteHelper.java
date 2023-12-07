package algonquin.cst2335.finalproject.sunrise;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySQLiteHelper extends SQLiteOpenHelper {
   static final String DATABASE_NAME="app.db";

    static final String TABLE_NAME_SUNINFO="suninfo";
    static final String TABLE_NAME_SEARCH="search";
    static final  int VERSION_CODE=1;
    public MySQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_CODE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table "+TABLE_NAME_SEARCH+"(lat,lng)";
        db.execSQL(sql);
        sql = "create table "+TABLE_NAME_SUNINFO+"(date,sunrise,sunset,first_flight,last_flight,dawn,dusk,solar_moon,golden_moon,day_length,timezone,utc_offset)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}