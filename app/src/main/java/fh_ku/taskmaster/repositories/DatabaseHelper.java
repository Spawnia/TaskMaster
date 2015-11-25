package fh_ku.taskmaster.repositories;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static int DB_VERSION = 1;
    public static String DB_NAME = "task_masta";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTasksTable = "CREATE TABLE " + TaskRepository.TABLE_TASKS + " (" +
                TaskRepository.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskRepository.COLUMN_NAME + " TEXT, " +
                TaskRepository.COLUMN_PRIORITY + " INTEGER, " +
                TaskRepository.COLUMN_DUE_DATE + " INTEGER, " +
                TaskRepository.COLUMN_CREATED + " INTEGER," +
                TaskRepository.COLUMN_TAG + " TEXT," +
                TaskRepository.COLUMN_CLOSED + " INTEGER" +
                ")";
        db.execSQL(createTasksTable);

        String createTagsTable = "CREATE TABLE " + TagRepository.TABLE_TAGS + "(" +
                TagRepository.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TagRepository.COLUMN_TAG + " TEXT" +
                ")";
        db.execSQL(createTagsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}