package com.example.worker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.*;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.worker.Modal.Task;

import java.util.ArrayList;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    private EditText edtTitle, edtDesc, edtTime;
    private Button btnSelectTime, btnAdd, btnSelectImage;
    private ImageView imgPreview;
    private Uri selectedImageUri; // store selected image URI
    private int hour, minute;
    private static ArrayList<Task> taskList = new ArrayList<>();

    ActivityResultLauncher<String> imagePickerLauncher; // for selecting image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        edtTitle = findViewById(R.id.edtTitle);
        edtDesc = findViewById(R.id.edtDesc);
        edtTime = findViewById(R.id.edtTime);
        btnSelectTime = findViewById(R.id.btnSelectTime);
        btnAdd = findViewById(R.id.btnAdd);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        imgPreview = findViewById(R.id.imgPreview);

        // Set up image picker
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        selectedImageUri = uri;
                        imgPreview.setImageURI(uri);
                    }
                }
        );

        btnSelectImage.setOnClickListener(v -> {
            imagePickerLauncher.launch("image/*");
        });

        btnSelectTime.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (view, hourOfDay, minuteOfHour) -> edtTime.setText(String.format("%02d:%02d", hourOfDay, minuteOfHour)), hour, minute, true);
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

            Task newTask = new Task(title, desc, time); // Update Task class if needed
            taskList.add(newTask);

            showNotification(title, time);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("title", title);
            resultIntent.putExtra("desc", desc);
            resultIntent.putExtra("time", time);
            if (selectedImageUri != null) {
                resultIntent.putExtra("imageUri", selectedImageUri.toString());
            }
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    private void showNotification(String title, String time) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("task_channel", "Task Notifications",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "task_channel")
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle("Đã thêm công việc mới!")
                .setContentText("Công việc: " + title + " lúc " + time)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        notificationManager.notify(1, builder.build());
    }
}
