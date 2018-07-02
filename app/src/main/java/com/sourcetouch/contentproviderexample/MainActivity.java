package com.sourcetouch.contentproviderexample;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.et_task)
    EditText etTask;

    @BindView(R.id.tv_add)
    TextView tvAdd;

    private TaskAdapter adapter;
    private List<Task> tasks = new ArrayList<>();
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tvAdd.setTag("Add");
        getAllRecords();

        recyclerView.addOnItemTouchListener(
                new RecycleViewItemClickListener(this, new RecycleViewItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        task = tasks.get(position);
                        etTask.setText(task.getTask());
                        tvAdd.setText("Update");
                        tvAdd.setTag("Update");
                    }
                }));
    }

    @OnClick(R.id.tv_add)
    public void onAddClick(View view) {
        if(tvAdd.getText().toString().equalsIgnoreCase("Update")) {
            String taskName = etTask.getText().toString();
            if (!TextUtils.isEmpty(taskName)) {
                task.setTask(taskName);
                updateRecord(task);
                etTask.setText("");
                tvAdd.setText("Add");
                tvAdd.setTag("Add");
                getAllRecords();
            } else {
                Toast.makeText(this, "Task name cannot be empty.", Toast.LENGTH_SHORT).show();
            }
        } else {
            String taskName = etTask.getText().toString();
            if (!TextUtils.isEmpty(taskName)) {
                addNewRecord(new Task(taskName, System.currentTimeMillis()));
                etTask.setText("");
                tvAdd.setText("Add");
                tvAdd.setTag("Add");
                getAllRecords();
            } else {
                Toast.makeText(this, "Task name cannot be empty.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Uri addNewRecord(Task task) {
        ContentValues values = new ContentValues();
        values.put(TodoContract.TodoEntry.COLUMN_TASK, task.getTask());
        values.put(TodoContract.TodoEntry.COLUMN_DATE, task.getDate());
        return getContentResolver().insert(TodoContract.TodoEntry.CONTENT_URI, values);
    }

    private int updateRecord(Task task) {
        ContentValues values = new ContentValues();
        values.put(TodoContract.TodoEntry.COLUMN_TASK, task.getTask());
        values.put(TodoContract.TodoEntry.COLUMN_DATE, task.getDate());
        return getContentResolver().update(TodoContract.TodoEntry.CONTENT_URI, values, TodoContract.TodoEntry._ID + "=?", new String[]{task.getId()});
    }

    private void getAllRecords() {
        tasks = null;
        tasks = new ArrayList<>();
        Cursor cursor = getContentResolver().query(TodoContract.TodoEntry.CONTENT_URI,null,null,null,null);
        while (cursor.moveToNext()) {
            Task task = new Task(cursor.getString(cursor.getColumnIndex(TodoContract.TodoEntry._ID)),
                    cursor.getString(cursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_TASK)),
                    cursor.getLong(cursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_DATE)));
            tasks.add(task);
        }

        adapter = new TaskAdapter(tasks);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
