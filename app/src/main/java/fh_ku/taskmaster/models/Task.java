package fh_ku.taskmaster.models;

import android.content.Context;
import android.text.format.DateFormat;

import java.util.Date;

public class Task {

    protected int id;
    protected String name;
    protected int priority;
    protected Date dueDate;
    protected Date created;

    public Task() {
        this.id       = -1;
        this.name     = "";
        this.priority = 1;
        this.dueDate  = new Date();
        this.created  = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Task setName(String name) {
        this.name = name;
        return this;
    }

    public int getPriority() {
        return priority;
    }

    public Task setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Task setDueDate(Date dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public Date getCreated() {
        return created;
    }

    public Task setCreated(Date created) {
        this.created = created;
        return this;
    }

    public static String formatTime (Context context, Date date) {
        return DateFormat.getTimeFormat(context).format(date);
    }

    public static String formatDate (Context context, Date date) {
        return DateFormat.getDateFormat(context).format(date);
    }

    public static String formatDateTime (Context context, Date date) {
        return formatDate(context,date) + " " + formatTime(context,date);
    }

}
