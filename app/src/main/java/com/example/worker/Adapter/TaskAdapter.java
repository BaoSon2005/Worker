package com.example.worker.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.worker.EditTaskActivity;
import com.example.worker.Modal.Task;
import com.example.worker.R;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private Context context;
    private List<Task> taskList;
    private OnEditClickListener onEditClickListener;

    // Constructor
    public TaskAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    // Set listener for edit button click
    public void setOnEditClickListener(OnEditClickListener onEditClickListener) {
        this.onEditClickListener = onEditClickListener;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.title.setText(task.getTitle());
        holder.description.setText(task.getDescription());
        holder.time.setText(task.getTime());

        holder.btnEdit.setOnClickListener(v -> {
            if (onEditClickListener != null) {
                onEditClickListener.onEditClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    // ViewHolder class
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, time;
        Button btnEdit;

        public TaskViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txtTitle);
            description = itemView.findViewById(R.id.txtDesc);
            time = itemView.findViewById(R.id.txtTime);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }

    // Listener for edit button click
    public interface OnEditClickListener {
        void onEditClick(int position);
    }
}
