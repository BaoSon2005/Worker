package com.example.worker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditTaskActivity extends AppCompatActivity {

    private EditText edtTitle, edtDesc, edtTime;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        edtTitle = findViewById(R.id.edtTitle);
        edtDesc = findViewById(R.id.edtDesc);
        edtTime = findViewById(R.id.edtTime);
        btnSave = findViewById(R.id.btnUpdate );

        // Get the task data from the intent
        Intent intent = getIntent();
        int index = intent.getIntExtra("index", -1);
        String title = intent.getStringExtra("title");
        String desc = intent.getStringExtra("desc");
        String time = intent.getStringExtra("time");

        // Set the data in the EditText fields
        edtTitle.setText(title);
        edtDesc.setText(desc);
        edtTime.setText(time);

        btnSave.setOnClickListener(v -> {
            String updatedTitle = edtTitle.getText().toString();
            String updatedDesc = edtDesc.getText().toString();
            String updatedTime = edtTime.getText().toString();

            // Return updated data to HomeActivity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("index", index);
            resultIntent.putExtra("title", updatedTitle);
            resultIntent.putExtra("desc", updatedDesc);
            resultIntent.putExtra("time", updatedTime);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
