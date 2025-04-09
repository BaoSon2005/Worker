package com.example.worker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.worker.Modal.Task;
import com.example.worker.R;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private Context context;
    private ArrayList<Task> taskList;
    private OnItemClickListener editClickListener;
    private OnItemClickListener deleteClickListener;

    public TaskAdapter(Context context, ArrayList<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnEditClickListener(OnItemClickListener listener) {
        this.editClickListener = listener;
    }

    public void setOnDeleteClickListener(OnItemClickListener listener) {
        this.deleteClickListener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        holder.txtTitle.setText(task.getTitle());
        holder.txtDesc.setText(task.getDescription());
        holder.txtTime.setText(task.getTime());

        // Load ảnh online bằng Glide
        if (task.getImageUri() != null && !task.getImageUri().isEmpty()) {
            Glide.with(context)
                    .load(task.getImageUri())
                    .placeholder(R.drawable.ic_launcher_background) // ảnh loading tạm thời
                    .error(R.drawable.ic_launcher_background)      // ảnh nếu load lỗi
                    .into(holder.imgTask);
        } else {
            holder.imgTask.setImageResource(R.drawable.ic_launcher_background);
        }

        holder.btnEdit.setOnClickListener(v -> {
            if (editClickListener != null) {
                editClickListener.onItemClick(position);
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (deleteClickListener != null) {
                deleteClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtDesc, txtTime;
        ImageView imgTask;
        ImageButton btnEdit, btnDelete;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDesc = itemView.findViewById(R.id.txtDesc);
            txtTime = itemView.findViewById(R.id.txtTime);
            imgTask = itemView.findViewById(R.id.imgTask);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
