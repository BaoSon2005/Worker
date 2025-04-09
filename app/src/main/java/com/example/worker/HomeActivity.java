package com.example.worker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
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

        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(this, taskList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        addDummyData();

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AddTaskActivity.class);
            startActivityForResult(intent, 1);
        });

        taskAdapter.setOnEditClickListener(position -> {
            Task task = taskList.get(position);

            Intent intent = new Intent(HomeActivity.this, EditTaskActivity.class);
            intent.putExtra("index", position);
            intent.putExtra("title", task.getTitle());
            intent.putExtra("desc", task.getDescription());
            intent.putExtra("time", task.getTime());
            intent.putExtra("imageUri", task.getImageUri());
            startActivityForResult(intent, 2);
        });

        taskAdapter.setOnDeleteClickListener(position -> {
            taskList.remove(position);
            taskAdapter.notifyItemRemoved(position);
            Toast.makeText(HomeActivity.this, "Đã xóa công việc!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) return;

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String title = data.getStringExtra("title");
            String desc = data.getStringExtra("desc");
            String time = data.getStringExtra("time");
            String imageUri = data.getStringExtra("imageUri");

            taskList.add(new Task(title, desc, time, imageUri));
            taskAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Công việc đã được thêm!", Toast.LENGTH_SHORT).show();

        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            int index = data.getIntExtra("index", -1);
            String title = data.getStringExtra("title");
            String desc = data.getStringExtra("desc");
            String time = data.getStringExtra("time");
            String imageUri = data.getStringExtra("imageUri");

            if (index != -1) {
                Task updatedTask = taskList.get(index);
                updatedTask.setTitle(title);
                updatedTask.setDescription(desc);
                updatedTask.setTime(time);
                updatedTask.setImageUri(imageUri);
                taskAdapter.notifyItemChanged(index);
                Toast.makeText(this, "Công việc đã được cập nhật!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addDummyData() {
        taskList.add(new Task("Học Java", "Ôn lại cú pháp cơ bản", "10:00 AM", "https://picsum.photos/id/1011/500/300"));
        taskList.add(new Task("Đi siêu thị", "Mua đồ ăn cho cả tuần", "5:00 PM", "https://picsum.photos/id/1012/500/300"));
        taskList.add(new Task("Tập gym", "Tập cardio 30 phút", "7:00 AM", "https://picsum.photos/id/1015/500/300"));
        taskList.add(new Task("Đọc sách", "Đọc 50 trang sách lập trình", "8:00 PM", "https://picsum.photos/id/1025/500/300"));
        taskList.add(new Task("Viết code", "Làm project Android", "2:00 PM", "https://picsum.photos/id/1035/500/300"));
        taskList.add(new Task("Đi bộ", "Đi dạo công viên", "6:00 AM", "https://picsum.photos/id/1045/500/300"));
        taskList.add(new Task("Nấu ăn", "Nấu bữa tối", "7:30 PM", "https://picsum.photos/id/1060/500/300"));

        taskAdapter.notifyDataSetChanged();
    }



}
