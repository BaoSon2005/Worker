package com.example.worker;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Calendar;

public class EditTaskActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText edtTitle, edtDesc;
    private TextView txtTime;
    private ImageView imgTask;
    private Button btnSave;
    private Uri imageUri; // Lưu đường dẫn ảnh đã chọn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        edtTitle = findViewById(R.id.edtTitle);
        edtDesc = findViewById(R.id.edtDesc);
        txtTime = findViewById(R.id.txtTime);
        imgTask = findViewById(R.id.imgTask);
        btnSave = findViewById(R.id.btnUpdate);

        // Get the task data from the intent
        Intent intent = getIntent();
        int index = intent.getIntExtra("index", -1);
        String title = intent.getStringExtra("title");
        String desc = intent.getStringExtra("desc");
        String time = intent.getStringExtra("time");
        String imageUriString = intent.getStringExtra("imageUri");

        // Set the data in the EditText, TextView, and ImageView
        edtTitle.setText(title);
        edtDesc.setText(desc);
        txtTime.setText(time);

        if (imageUriString != null) {
            imageUri = Uri.parse(imageUriString);
            imgTask.setImageURI(imageUri);
        }

        // Click để chọn ảnh mới
        imgTask.setOnClickListener(v -> openImagePicker());

        // Click để chọn thời gian
        txtTime.setOnClickListener(v -> showTimePickerDialog());

        // Click để lưu cập nhật
        btnSave.setOnClickListener(v -> {
            String updatedTitle = edtTitle.getText().toString();
            String updatedDesc = edtDesc.getText().toString();
            String updatedTime = txtTime.getText().toString();
            String updatedImageUri = imageUri != null ? imageUri.toString() : null;

            // Return updated data to HomeActivity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("index", index);
            resultIntent.putExtra("title", updatedTitle);
            resultIntent.putExtra("desc", updatedDesc);
            resultIntent.putExtra("time", updatedTime);
            resultIntent.putExtra("imageUri", updatedImageUri);

            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (TimePicker view, int hourOfDay, int minute1) -> {
                    String selectedTime = String.format("%02d:%02d", hourOfDay, minute1);
                    txtTime.setText(selectedTime);
                }, hour, minute, true);

        timePickerDialog.show();
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imgTask.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
