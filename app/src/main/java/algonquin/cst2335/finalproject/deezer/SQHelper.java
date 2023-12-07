package algonquin.cst2335.finalproject.deezer;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * The {@code SQHelper} class represents a SQLite database helper for managing song-related data.
 */
public class SQHelper extends SQLiteOpenHelper {
    /**
     * The name of the database.
     */
    static final String DATABASE_NAME = "app.db";

    /**
     * The name of the table storing song data.
     */
    static final String TABLE_NAME_SONG = "song";

    /**
     * The version code of the database.
     */
    static final int VERSION_CODE = 1;

    /**
     * Constructs a new instance of {@code SQHelper}.
     *
     * @param context The context in which the database helper is created.
     */
    public SQHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION_CODE);
    }

    /**
     * Called when the database is created for the first time.
     *
     * @param db The database being created.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_NAME_SONG + "(id,title,duration,album_title,cover,collection)";
        db.execSQL(sql);
    }

    /**
     * Called when the database needs to be upgraded.
     *
     * @param db         The database being upgraded.
     * @param oldVersion The old version of the database.
     * @param newVersion The new version of the database.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Perform upgrade operations if needed
    }
}

