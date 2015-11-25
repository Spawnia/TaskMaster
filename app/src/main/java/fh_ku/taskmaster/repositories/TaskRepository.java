package fh_ku.taskmaster.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.Calendar;

import fh_ku.taskmaster.models.Task;

public class TaskRepository {

    public static final String TABLE_TASKS = "tasks";

    public static final String COLUMN_ID = "TaskID";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_PRIORITY = "Priority";
    public static final String COLUMN_DUE_DATE = "DueDate";
    public static final String COLUMN_CREATED = "Created";
    public static final String COLUMN_TAG = "Tag";
    public static final String COLUMN_CLOSED = "Closed";

    private DatabaseHelper dbHelper;

    public TaskRepository(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void close() {
        this.dbHelper.close();
    }

    public Task taskAtCursorPosition(Cursor cursor, int position) {
        if (cursor != null && cursor.moveToPosition(position)) {
            Log.i("TASK REPOSITORY", "Task found at position: " + position);
            return new Task()
                    .setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)))
                    .setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)))
                    .setPriority(cursor.getInt(cursor.getColumnIndex(COLUMN_PRIORITY)))
                    .setDueDate(Task.TimestampToDate(cursor.getLong(cursor.getColumnIndex(COLUMN_DUE_DATE))))
                    .setCreated(Task.TimestampToDate(cursor.getLong(cursor.getColumnIndex(COLUMN_CREATED))))
                    .setTag(cursor.getString(cursor.getColumnIndex(COLUMN_TAG)))
                    .setClosed(cursor.getInt(cursor.getColumnIndex(COLUMN_CLOSED)));
        }

        Log.i("TASK REPOSITORY", "No Task at position " + position + " found.");
        return null;
    }

    public Cursor queryAllTasks() {
        return this.dbHelper.getReadableDatabase().query(TABLE_TASKS,null,null,null,null,null,null);
    }

    public Cursor queryTasksByTag(String tag) {
        return this.dbHelper.getReadableDatabase().query(TABLE_TASKS,null,COLUMN_TAG+"=?",new String[]{tag},null,null,null);
    }

    public Task getTask(int id) {
        Cursor cursor = dbHelper.getReadableDatabase().query(TABLE_TASKS,null,COLUMN_ID+"=?",new String[]{String.valueOf(id)},null,null,null);
        return this.taskAtCursorPosition(cursor, 0);
    }

    public ContentValues taskToContentValues(Task task) {
        ContentValues cvs = new ContentValues();
        cvs.put(COLUMN_NAME, task.getName());
        cvs.put(COLUMN_PRIORITY,task.getPriority());
        cvs.put(COLUMN_DUE_DATE,task.getDueDate().getTime());
        cvs.put(COLUMN_CREATED,task.getCreated() == null ? Calendar.getInstance().getTimeInMillis() : task.getCreated().getTime());
        cvs.put(COLUMN_TAG,task.getTag());
        cvs.put(COLUMN_CLOSED,task.isClosed() ? 1 : 0);
        return cvs;
    }

    public Task addTask(Task task) {
        task.setId((int) dbHelper.getWritableDatabase().insert(TABLE_TASKS,null,taskToContentValues(task)));
        return task;
    }

    public Task updateTask(Task task) {
        dbHelper.getWritableDatabase().update(TABLE_TASKS,taskToContentValues(task),COLUMN_ID+"=?",new String[]{String.valueOf(task.getId())});
        return task;
    }

}
