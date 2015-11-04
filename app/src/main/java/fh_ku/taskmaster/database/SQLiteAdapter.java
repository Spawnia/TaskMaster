package fh_ku.taskmaster.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fh_ku.taskmaster.models.Task;

/**
 * Created by Benedikt on 03.11.2015.
 */
public class SQLiteAdapter {
    SQLiteHelper helper;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public SQLiteAdapter(Context context) {
        helper = new SQLiteHelper(context);
    }

    private static final String TABLE_TASKS = "tasks";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PRIORITY = "priority";
    private static final String KEY_DUEDATE = "duedate";
    private static final String KEY_CREATED = "created";

    private static final String[] COLUMNS = {KEY_ID, KEY_NAME, KEY_PRIORITY, KEY_DUEDATE, KEY_CREATED};

    public void addTask(Task task) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        //values.put(KEY_ID, task.getId());
        values.put(KEY_NAME, task.getName());
        values.put(KEY_PRIORITY, task.getPriority());
        values.put(KEY_DUEDATE, sdf.format(task.getDueDate()));
        values.put(KEY_CREATED, sdf.format(task.getCreated()));

        db.insert(TABLE_TASKS, null, values);
        //db.execSQL("INSERT INTO " + TABLE_TASKS + " VALUES (" + task.getId() + ",'" + task.getName() + "'," + task.getPriority() + "," + task.getDueDate() + "," + task.getCreated() + ")");
        db.close();
    }


    public Task getTask(int id) {
        Task task = new Task();

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TASKS, COLUMNS, " id = ?", null, null, null, null);

        if (cursor.moveToFirst()) {

            Date cursorDueDate = null;
            Date cursorCreate = null;
            try {
                cursorDueDate = sdf.parse(cursor.getString(3));
                cursorCreate = sdf.parse(cursor.getString(4));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            /*
            try {
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(cursor.getBlob(3)));
                cursorDueDate = (Date) ois.readObject();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(cursor.getBlob(4)));
                cursorCreate = (Date) ois.readObject();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            */

            task.setId(cursor.getInt(0));
            task.setName(cursor.getString(1));
            task.setPriority(cursor.getInt(2));
            task.setDueDate(cursorDueDate);
            task.setCreated(cursorCreate);
        }
        return task;
    }

    public List<Task> getAllData() {
        List<Task> list = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TASKS, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Date cursorDueDate = null;
                Date cursorCreate = null;
                try {
                    cursorDueDate = sdf.parse(cursor.getString(3));
                    cursorCreate = sdf.parse(cursor.getString(4));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                /*
                try {
                    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(cursor.getBlob(3)));
                    cursorDueDate = (Date) ois.readObject();
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(cursor.getBlob(4)));
                    cursorCreate = (Date) ois.readObject();
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                */

                Task task = new Task();
                task.setId(cursor.getInt(0));
                task.setName(cursor.getString(1));
                task.setPriority(cursor.getInt(2));
                task.setDueDate(cursorDueDate);
                task.setCreated(cursorCreate);
                list.add(task);
            }
        }
        return list;
    }


    public int updateTask(Task task) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, task.getId());
        values.put(KEY_NAME, task.getName());
        values.put(KEY_PRIORITY, task.getPriority());

        values.put(KEY_DUEDATE, sdf.format(task.getDueDate()));
        values.put(KEY_CREATED, sdf.format(task.getCreated()));

        int i = db.update(TABLE_TASKS, values, KEY_ID + " = ?", new String[]{String.valueOf(task.getId())});

        db.close();

        return i;

    }

    public void deleteTask(Task task){
        SQLiteDatabase db = helper.getWritableDatabase();

        db.delete(TABLE_TASKS, KEY_ID+ " = ?", new String[] {String.valueOf(task.getId())});

        db.close();

    }
}
