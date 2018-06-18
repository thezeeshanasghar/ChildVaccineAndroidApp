package com.example.basit009.vaccs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.basit009.vaccs.ClientsJson.AdminUser;
import com.example.basit009.vaccs.ClientsJson.NetworkUtils;
import com.example.basit009.vaccs.ClientsJson.ServiceGenerator;
import com.example.basit009.vaccs.ClientsJson.VaccsClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText editUserName;
    private EditText editUserPassword;
    private Button buttonLogin;
    private VaccsClient vaccsClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        vaccsClient = ServiceGenerator.createService(VaccsClient.class);
        initGui();
        initListeners();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getIntent().getBooleanExtra("Exit", true)) {
                Toast.makeText(this, "inside keyback", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            }

        }

        return super.onKeyDown(keyCode, event);
    }


    private void initGui() {
        editUserName = findViewById(R.id.login_user_name_id);
        editUserPassword = findViewById(R.id.login_user_password_id);
        buttonLogin = findViewById(R.id.login_user_button_id);
    }

    private void initListeners() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();


            }
        });
    }

    private void validateFields() {
        if (editUserName.getText().toString().equals("")) {
            editUserName.requestFocus();
            editUserName.setError("Field Required");
            return;
        }
        if (editUserPassword.getText().toString().equals("")) {
            editUserPassword.requestFocus();
            editUserPassword.setError("Field Required");
            return;
        }

        if (!NetworkUtils.isNetworkAvailable(this)) {
            Toast.makeText(this, "NO internet Available", Toast.LENGTH_LONG).show();
            return;
        }
        String userName = editUserName.getText().toString().trim();
        String password = editUserPassword.getText().toString().trim();

        final Call<AdminUser> call = vaccsClient.user(userName, password, "SUPERADMIN");
        call.enqueue(new Callback<AdminUser>() {
            @Override
            public void onResponse(Call<AdminUser> call, Response<AdminUser> response) {

                AdminUser loginModal = response.body();
                if (loginModal != null) {

                    Toast.makeText(LoginActivity.this, loginModal.Message, Toast.LENGTH_LONG).show();
                    if (loginModal.IsSuccess.equals("true")) {

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("Exit", true);
                        startActivity(intent);

                    }

                } else {
                    Toast.makeText(LoginActivity.this, "No response available", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AdminUser> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Call Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

}