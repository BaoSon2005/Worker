package com.example.worker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worker.Adapter.TaskAdapter;
import com.example.worker.Modal.Task;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnAdd;
    private ArrayList<Task> taskList;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerTasks);
        btnAdd = findViewById(R.id.btnAdd);

        // Initialize task list and adapter
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(this, taskList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        // Add dummy tasks
        addDummyData();

        // Open AddTaskActivity when Add button is clicked
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AddTaskActivity.class);
            startActivityForResult(intent, 1);
        });

        // Set up edit listener for RecyclerView
        taskAdapter.setOnEditClickListener(position -> {
            Task task = taskList.get(position);

            Intent intent = new Intent(HomeActivity.this, EditTaskActivity.class);
            intent.putExtra("index", position);
            intent.putExtra("title", task.getTitle());
            intent.putExtra("desc", task.getDescription());
            intent.putExtra("time", task.getTime());
            startActivityForResult(intent, 2);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Add new task to the list
            String title = data.getStringExtra("title");
            String desc = data.getStringExtra("desc");
            String time = data.getStringExtra("time");

            taskList.add(new Task(title, desc, time));
            taskAdapter.notifyDataSetChanged(); // Notify adapter to refresh the list
            Toast.makeText(this, "Công việc đã được thêm!", Toast.LENGTH_SHORT).show();
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            // Get updated task information
            int index = data.getIntExtra("index", -1);
            String title = data.getStringExtra("title");
            String desc = data.getStringExtra("desc");
            String time = data.getStringExtra("time");

            if (index != -1) {
                Task updatedTask = taskList.get(index);
                updatedTask.setTitle(title);
                updatedTask.setDescription(desc);
                updatedTask.setTime(time);
                taskAdapter.notifyItemChanged(index); // Update the specific item
                Toast.makeText(this, "Công việc đã được cập nhật!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to add dummy data
    private void addDummyData() {
        taskList.add(new Task("Công việc 1", "Hoàn thành tài liệu hướng dẫn", "09:00 Sáng"));
        taskList.add(new Task("Công việc 2", "Tham dự cuộc họp", "11:00 Sáng"));
        taskList.add(new Task("Công việc 3", "Nộp báo cáo", "02:00 Chiều"));
        taskList.add(new Task("Công việc 4", "Kiểm tra email và trả lời", "04:00 Chiều"));

        taskAdapter.notifyDataSetChanged(); // Refresh the RecyclerView with the dummy tasks
    }
}
