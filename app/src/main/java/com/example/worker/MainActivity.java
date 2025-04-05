package com.example.worker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword;
     Button btnLogin, btnToRegister;
     TextView tvForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btn_login);
        btnToRegister = findViewById(R.id.btn_to_register);
        tvForgotPassword = findViewById(R.id.tvforgot);

        // Xử lý sự kiện nhấn nút đăng nhập
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy email và mật khẩu nhập vào
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();

                // Kiểm tra nếu email và mật khẩu không rỗng
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {

                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish(); // Đóng màn hình đăng nhập khi đăng nhập thành công
                }
            }
        });

        // Xử lý sự kiện chuyển đến màn hình đăng ký
        btnToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện quên mật khẩu (tùy chỉnh chức năng quên mật khẩu)
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện thao tác quên mật khẩu, có thể mở màn hình đặt lại mật khẩu
                Toast.makeText(MainActivity.this, "Chức năng quên mật khẩu đang phát triển", Toast.LENGTH_SHORT).show();
            }
        });
    }
}