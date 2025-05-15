package firstapp.example.lipsclone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import firstapp.example.lipsclone.api.LoginReponse;
import firstapp.example.lipsclone.api.LoginRequest;
import firstapp.example.lipsclone.api.apiServices;
import firstapp.example.lipsclone.api.apiclient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button btn;
    EditText phone_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        phone_no = findViewById(R.id.phone_input);
        btn = findViewById(R.id.submit_button);
        // Assuming you have already initialized the fields
        phone_no = findViewById(R.id.phone_input);
        btn = findViewById(R.id.submit_button);

        btn.setOnClickListener(v -> {
            String mobile = phone_no.getText().toString().trim();

            if (isValidMobile(mobile)) {
                // Send the mobile number to the server for verification
                sendPhoneForVerification(mobile);
            } else {
                Toast.makeText(MainActivity.this, "Please enter a valid 10-digit number.", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private boolean isValidMobile(String phone) {
        return phone.length() == 10 && phone.matches("[0-9]+");
    }

    private void sendPhoneForVerification(String mobile) {
        // Assuming LoginRequest takes mobile number and session ID
        LoginRequest loginRequest = new LoginRequest(mobile, "gdcol1");

        apiServices api = apiclient.getClient().create(apiServices.class);
        api.loginStudent(loginRequest).enqueue(new Callback<LoginReponse>() {
            @Override
            public void onResponse(Call<LoginReponse> call, Response<LoginReponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().response != null) {
                    Log.d("LOGIN_RESPONSE", new Gson().toJson(response.body()));

                    if (response.body().success) {
                        Intent intent = new Intent(MainActivity.this, LoginOTPActivity.class);
                        intent.putExtra("mobile", mobile);
                        intent.putExtra("s_id", response.body().response.s_id);
                        intent.putExtra("session", response.body().response.session);
                        intent.putExtra("f_id", response.body().response.f_id);
                        intent.putExtra("college", response.body().response.college);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid user or phone number not registered", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("LOGIN_ERROR", "Response body or response data is null");
                    Toast.makeText(MainActivity.this, "Server error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginReponse> call, Throwable t) {
                Log.e("LOGIN_ERROR", "Network failure: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
