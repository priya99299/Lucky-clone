package firstapp.example.lipsclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import firstapp.example.lipsclone.api.Models.AppConfigRequest;
import firstapp.example.lipsclone.api.Models.AppConfigResponse;
import firstapp.example.lipsclone.api.apiServices;
import firstapp.example.lipsclone.api.apiclient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginOTPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_otpactivity);

        Button submit = findViewById(R.id.submit_button);
        EditText otpInput = findViewById(R.id.otpInput);

        String mobile = getIntent().getStringExtra("mobile");
        String s_id = getIntent().getStringExtra("s_id");
        String session = getIntent().getStringExtra("session");
        String college = getIntent().getStringExtra("college");
        String f_id = getIntent().getStringExtra("f_id");

        apiServices api = apiclient.getClient().create(apiServices.class);
        AppConfigRequest request = new AppConfigRequest(s_id, session, college, f_id);

        api.getAppConfig(request).enqueue(new Callback<AppConfigResponse>() {
            @Override
            public void onResponse(Call<AppConfigResponse> call, Response<AppConfigResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().success) {
                    String serverOtp = response.body().response.otp;

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String enteredOtp = otpInput.getText().toString();
                            if (enteredOtp.equals(serverOtp)) {
                                Intent dashboardIntent = new Intent(LoginOTPActivity.this, dashboard.class);
                                startActivity(dashboardIntent);
                            } else {
                                Toast.makeText(LoginOTPActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(LoginOTPActivity.this, "Failed to fetch OTP", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AppConfigResponse> call, Throwable t) {
                Toast.makeText(LoginOTPActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
