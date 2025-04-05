package com.example.worker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.worker.Modal.Task;

import java.util.ArrayList;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    private EditText edtTitle, edtDesc, edtTime;
    private Button btnSelectTime, btnAdd;
    private int hour, minute;
    private static ArrayList<Task> taskList = new ArrayList<>(); // ArrayList lưu các công việc

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        edtTitle = findViewById(R.id.edtTitle);
        edtDesc = findViewById(R.id.edtDesc);
        edtTime = findViewById(R.id.edtTime);
        btnSelectTime = findViewById(R.id.btnSelectTime);
        btnAdd = findViewById(R.id.btnAdd);

        btnSelectTime.setOnClickListener(v -> {
            // Lấy thời gian hiện tại
            Calendar calendar = Calendar.getInstance();
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);

            // Mở TimePickerDialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (view, hourOfDay, minuteOfHour) -> {
                        // Hiển thị giờ và phút trong edtTime
                        edtTime.setText(String.format("%02d:%02d", hourOfDay, minuteOfHour));
                    }, hour, minute, true); // true cho 24-hour format
            timePickerDialog.show();
        });

        btnAdd.setOnClickListener(v -> {
            String title = edtTitle.getText().toString().trim();
            String desc = edtDesc.getText().toString().trim();
            String time = edtTime.getText().toString().trim();

            if (title.isEmpty() || desc.isEmpty() || time.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lưu task mới vào ArrayList
            Task newTask = new Task(title, desc, time);
            taskList.add(newTask);

            // Tiến hành gửi notification khi thêm công việc mới
            showNotification(title, time);

            // Gửi kết quả về MainActivity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("title", title);
            resultIntent.putExtra("desc", desc);
            resultIntent.putExtra("time", time);
            setResult(RESULT_OK, resultIntent);
            finish(); // Đóng AddTaskActivity
        });
    }

    private void showNotification(String title, String time) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Android 8.0 trở lên cần NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("task_channel", "Task Notifications",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "task_channel")
                .setSmallIcon(R.drawable.ic_notifications) // icon bạn cần thêm vào drawable
                .setContentTitle("Đã thêm công việc mới!")
                .setContentText("Công việc: " + title + " lúc " + time)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        notificationManager.notify(1, builder.build()); // ID của notification
    }
}
