package com.sourcetouch.contentproviderexample;

/**
 * Created by Rajashekhar Vanahalli on 29/06/18.
 */
public class Task {
    private String id;
    private String task;
    private long date;

    public Task(String task, long date) {
        this.task = task;
        this.date = date;
    }

    public Task(String id, String task, long date) {
        this.id = id;
        this.task = task;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
