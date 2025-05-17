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
import firstapp.example.lipsclone.api.apiServices;
import firstapp.example.lipsclone.api.apiclient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginOTPActivity extends AppCompatActivity {

    EditText otpInput;
    Button submitButton;
    String serverOtp = null;

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

        // Step 1: Fetch OTP from API
        AppConfigRequest request = new AppConfigRequest(s_id, session, college, f_id);
        apiServices api = apiclient.getClient().create(apiServices.class);

        api.getAppConfig(request).enqueue(new Callback<AppConfigResponse>() {
            @Override

            public void onResponse(Call<AppConfigResponse> call, Response<AppConfigResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AppConfigResponse body = response.body();
                    Log.e("OTP_RESPONSE", "Success: " + body.success + ", OTP: " + body.response.otp);

                    if (body.success && body.response != null) {
                        serverOtp = body.response.otp;

                        Toast.makeText(LoginOTPActivity.this, "OTP Loaded: " + serverOtp, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginOTPActivity.this, "Server responded but no OTP", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("OTP_RESPONSE", "Not successful: " + response.code());
                    Toast.makeText(LoginOTPActivity.this, "Failed to fetch OTP", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<AppConfigResponse> call, Throwable t) {
                Toast.makeText(LoginOTPActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        submitButton.setOnClickListener(v -> {
            String enteredOtp = otpInput.getText().toString().trim();

            if (enteredOtp.isEmpty()) {
                Toast.makeText(LoginOTPActivity.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                return;
            }


            Toast.makeText(LoginOTPActivity.this, "OTP Verified", Toast.LENGTH_SHORT).show();

            // Next screen par jao
            Intent intent = new Intent(LoginOTPActivity.this, dashboard.class);
            intent.putExtra("mobile", getIntent().getStringExtra("mobile"));
            startActivity(intent);
            finish();
        });

    }
}
