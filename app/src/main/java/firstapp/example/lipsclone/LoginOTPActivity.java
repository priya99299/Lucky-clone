package firstapp.example.lipsclone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import firstapp.example.lipsclone.api.Models.AppConfigRequest;
import firstapp.example.lipsclone.api.Models.AppConfigResponse;
import firstapp.example.lipsclone.api.Models.StudentVerifyRequest;
import firstapp.example.lipsclone.api.apiServices;
import firstapp.example.lipsclone.api.apiclient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginOTPActivity extends AppCompatActivity {

    EditText otpInput;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otpactivity);

        otpInput = findViewById(R.id.otpInput);
        submitButton = findViewById(R.id.submit_button);

        String mobile = getIntent().getStringExtra("mobile");
        String s_id = getIntent().getStringExtra("s_id");
        String session = getIntent().getStringExtra("session");
        String college = getIntent().getStringExtra("college");
        String f_id = getIntent().getStringExtra("f_id");

        submitButton.setOnClickListener(v -> {
            String enteredOtp = otpInput.getText().toString().trim();

            if (enteredOtp.isEmpty()) {
                Toast.makeText(LoginOTPActivity.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                return;
            }

            StudentVerifyRequest request = new StudentVerifyRequest(session, college, mobile, enteredOtp);
            apiServices api = apiclient.getClient().create(apiServices.class);

            api.verifyStudent(request).enqueue(new Callback<AppConfigResponse>() {
                @Override
                public void onResponse(Call<AppConfigResponse> call, Response<AppConfigResponse> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().success) {
                        Toast.makeText(LoginOTPActivity.this, "OTP Verified", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginOTPActivity.this, dashboard.class);
                        intent.putExtra("mobile", mobile);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginOTPActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AppConfigResponse> call, Throwable t) {
                    Toast.makeText(LoginOTPActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}

