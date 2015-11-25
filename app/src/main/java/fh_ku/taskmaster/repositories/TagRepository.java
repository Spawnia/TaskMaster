package fh_ku.taskmaster.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import fh_ku.taskmaster.models.Tag;
import fh_ku.taskmaster.models.Task;

/**
 * Created by Benedikt on 25.11.2015.
 */
public class TagRepository {

    public static final String TABLE_TAGS = "tags";

    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_TAG = "Tag";

    private DatabaseHelper dbHelper;

    public TagRepository(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void close() {
        this.dbHelper.close();
    }

    public Cursor queryAllTags(){
        return this.dbHelper.getReadableDatabase().query(TABLE_TAGS,null,null,null,null,null,null);
    }

    public Tag tagAtCursorPosition(Cursor cursor, int position) {
        if(cursor != null && cursor.moveToPosition(position)){
            Log.i("TAG REPOSITORY", "Tag found at position: " + position);
            return new Tag()
                    .setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)))
                    .setTag(cursor.getString(cursor.getColumnIndex(COLUMN_TAG)));
        }
        Log.i("TAG REPOSITORY", "No Tag at position " + position + " found.");
        return null;
    }

    public Tag getTag(int id){
        Cursor cursor = dbHelper.getReadableDatabase().query(TABLE_TAGS,null,COLUMN_ID+"=?",new String[]{String.valueOf(id)},null,null,null);
        return this.tagAtCursorPosition(cursor, 0);
    }

    public ContentValues tagToContentValues(String tag){
        ContentValues cvs = new ContentValues();
        cvs.put(COLUMN_TAG, tag);
        return cvs;
    }
    //könnte zwei parameter entgegennehmen und alten tag evtl. löschen, wenn keine tasks mehr über sind
    public void addTag(String tag){
        Cursor cursor = dbHelper.getReadableDatabase().query(TABLE_TAGS,null,COLUMN_TAG+"=?",new String[]{tag},null,null,null);
        if(!cursor.moveToFirst()){
            cursor.close();
            dbHelper.getWritableDatabase().insert(TABLE_TAGS,null,tagToContentValues(tag));
        }
    }
}
