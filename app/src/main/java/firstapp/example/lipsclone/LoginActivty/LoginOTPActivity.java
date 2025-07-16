package firstapp.example.lipsclone.LoginActivty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.AppConfigResponse;
import firstapp.example.lipsclone.api.Models.StudentVerifyRequest;
import firstapp.example.lipsclone.api.Network.apiServices;
import firstapp.example.lipsclone.api.Network.apiclient;
import firstapp.example.lipsclone.dashboard;
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
            // 🚨 Bypass verification for default OTP




                StudentVerifyRequest request = new StudentVerifyRequest(session, college, mobile, enteredOtp);
                apiServices api = apiclient.getClient().create(apiServices.class);

                api.verifyStudent(request).enqueue(new Callback<AppConfigResponse>() {
                    @Override
                    public void onResponse(Call<AppConfigResponse> call, Response<AppConfigResponse> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().success) {
                            Toast.makeText(LoginOTPActivity.this, "OTP Verified", Toast.LENGTH_SHORT).show();
                            // Extract name and class_name from response
                            String s_id = response.body().response.s_id;
                            String name = response.body().response.name;
                            String className = response.body().response.class_name;
                            String imageUrl = response.body().response.pic;
                            String StudentAdmissionno = response.body().response.admno;
                            String fname = response.body().response.fname;
                            String mname = response.body().response.mname;
                            String mobile1 = response.body().response.mobile1;
                            String address2 = response.body().response.address2;
                            String file = response.body().response.file;
                            String filename = response.body().response.docname;
                            String session = response.body().response.session;
                            String f_id = response.body().response.f_id;


                            SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putBoolean("isLoggedIn", true);
                            editor.putString("name", name);
                            editor.putString("class_name", className);
                            editor.putString("image_url", imageUrl);
                            editor.putString("admno", StudentAdmissionno);
                            editor.putString("fname", fname);
                            editor.putString("mname", mname);
                            editor.putString("mobile1", mobile1);
                            editor.putString("address2", address2);
                            editor.putString("college", college);
                            editor.putString("s_id", s_id);
                            editor.putString("session", session);
                            editor.putString("f_id", f_id);

//                        editor.apply();

                            editor.apply();

                            // Send to dashboard
                            Intent intent = new Intent(LoginOTPActivity.this, dashboard.class);
                            intent.putExtra("name", name);
                            intent.putExtra("class_name", className);
                            intent.putExtra("image_url", imageUrl);
                            intent.putExtra("admno", StudentAdmissionno);
                            intent.putExtra("fname", fname);
                            intent.putExtra("mname", mname);
                            intent.putExtra("mobile1", mobile1);
                            intent.putExtra("address2", address2);
                            intent.putExtra("file", file);
                            intent.putExtra("filename", filename);
                            intent.putExtra("s_id", s_id);        // <-- add this
                            intent.putExtra("session", session);  // <-- add this
                            intent.putExtra("college", college);
                            intent.putExtra("f_id", f_id);


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

