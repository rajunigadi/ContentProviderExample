package com.sourcetouch.contentproviderexample;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rajashekhar Vanahalli on 27/06/18.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<Task> tasks;

    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_task_item, parent, false);
        return new TaskAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        holder.tvTask.setText(tasks.get(position).getTask());
    }

    @Override
    public int getItemCount() {
        return tasks != null && !tasks.isEmpty()?tasks.size():0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTask;

        public ViewHolder(View view) {
            super(view);
            tvTask = (TextView) view.findViewById(R.id.tv_task);
        }
    }
}
