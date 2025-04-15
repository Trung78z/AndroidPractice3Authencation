package com.hcmus.authencation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.hcmus.authencation.activity.HomeActivity;
import com.hcmus.authencation.activity.RegisterActivity;
import com.hcmus.authencation.domains.users.User;
import com.hcmus.authencation.domains.users.UserDAO;

public class MainActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button loginButton;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        userDAO = UserDAO.getInstance();


        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        TextView signupText = findViewById(R.id.signingText);
        Log.d("LOGIN_DEBUG", "list user" + userDAO.getAllUsers().toString());
        signupText.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                Log.d("LOGIN_DEBUG", "Username: " + user + " | Password: " + pass);
                User foundUser = userDAO.findUserByUsername(user);
                if (foundUser != null) {
                    Log.d("LOGIN_DEBUG", "Found user with pass: " + foundUser.getPassword());
                } else {
                    Log.d("LOGIN_DEBUG", "User not found in DAO");
                }

                if (userDAO.checkLogin(user, pass)) {
                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
