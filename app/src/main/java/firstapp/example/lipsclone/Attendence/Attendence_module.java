package firstapp.example.lipsclone.Attendence;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.attendence.LiveAttendanceRequest;
import firstapp.example.lipsclone.api.Models.attendence.LiveAttendanceResponse;
import firstapp.example.lipsclone.api.Models.attendence.SaveAttendanceRequest;
import firstapp.example.lipsclone.api.Network.apiServices;
import firstapp.example.lipsclone.api.Network.apiclient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Attendence_module extends AppCompatActivity {

    private static final String TAG = "Attendance";
    private Button markAttendanceBtn;
    private String s_id, sessionId, f_id, college, a_id, sem;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_attendence_module);

        gson = new GsonBuilder().setPrettyPrinting().setLenient().create();

        // Status bar insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Save semester to SharedPreferences
        if (sem != null && !sem.isEmpty()) {
            getSharedPreferences("MyPrefs", MODE_PRIVATE)
                    .edit()
                    .putString("sem", sem)
                    .apply();
            Log.d(TAG, "Saved semester to SharedPreferences: " + sem);
        }


        // Toolbar back button
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Get intent extras
        s_id = getIntent().getStringExtra("s_id");
        sessionId = getIntent().getStringExtra("session");
        f_id = getIntent().getStringExtra("f_id");
        college = getIntent().getStringExtra("college");
        sem = getIntent().getStringExtra("sem");

        if (college == null) college = "gdcol1";

        Log.d(TAG, "Intent Extras --> s_id: " + s_id + ", sessionId: " + sessionId +
                ", f_id: " + f_id + ", college: " + college + ", semester: " + sem);

// ✅ Now save semester to SharedPreferences
        if (sem != null && !sem.isEmpty()) {
            getSharedPreferences("MyPrefs", MODE_PRIVATE)
                    .edit()
                    .putString("sem", sem)
                    .apply();
            Log.d(TAG, "Saved semester to SharedPreferences: " + sem);
        }


        // Monthly Attendance click
        findViewById(R.id.Monthly).setOnClickListener(v -> {
            Intent intent = new Intent(Attendence_module.this, Monthly_Attendence.class);
            intent.putExtra("s_id", s_id);
            intent.putExtra("session", sessionId);
            intent.putExtra("f_id", f_id);
            intent.putExtra("college", college);
            Log.d("Onclick","On click sems" +sem);
            intent.putExtra("sem", sem);
            startActivity(intent);

        });
        CardView Lecture;
        Lecture=findViewById(R.id.Lecture);

        // Lecture-wise Attendance click
        Lecture.setOnClickListener(v -> {
            Intent lectureIntent = new Intent(this, LectureWiseAttendance.class);
            lectureIntent.putExtra("s_id", s_id);
            lectureIntent.putExtra("session", sessionId);
            lectureIntent.putExtra("f_id", f_id);
            lectureIntent.putExtra("sem", sem);
            startActivity(lectureIntent);

        });

        // Mark Attendance button setup
        markAttendanceBtn = findViewById(R.id.markAttendanceBtn);
        markAttendanceBtn.setOnClickListener(v -> saveAttendance());

        // Refresh button click
        findViewById(R.id.refreshFab).setOnClickListener(v -> fetchLiveAttendance());

        // Fetch attendance status initially
        fetchLiveAttendance();
    }

    /**
     * Calls the live attendance API to enable/disable button
     */
    private void fetchLiveAttendance() {
        Log.d(TAG, "Starting fetchLiveAttendance...");

        apiServices api = apiclient.getClient().create(apiServices.class);

        LiveAttendanceRequest request = new LiveAttendanceRequest(
                "api",
                "student_attendence_live",
                college,
                sessionId,
                s_id,
                f_id,
                sem
        );

        Log.d(TAG, "LiveAttendance API Request Payload:\n" + gson.toJson(request));

        Call<LiveAttendanceResponse> call = api.getLiveAttendance(request);

        call.enqueue(new Callback<LiveAttendanceResponse>() {
            @Override
            public void onResponse(Call<LiveAttendanceResponse> call, Response<LiveAttendanceResponse> response) {
                Log.d(TAG, "Response received - Code: " + response.code());

                if (response.isSuccessful()) {
                    LiveAttendanceResponse res = response.body();

                    if (res == null) {
                        Log.e(TAG, "Response body is NULL");
                        logRawResponse(response);
                        disableButton();
                        Toast.makeText(Attendence_module.this, "Empty response from server", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Log.d(TAG, "Parsed Response:\n" + gson.toJson(res));

                    LiveAttendanceResponse.ResponseData data = res.getResponse();
                    if (data != null) {
                        String status = data.getStatus();
                        String markStatus = data.getMark_status();
                        String msg = data.getMsg();
                        a_id = data.getA_id();

                        if ("1".equals(status) && "1".equals(markStatus)) {
                            enableButton();
                            Toast.makeText(Attendence_module.this, msg != null ? msg : "Attendance available", Toast.LENGTH_SHORT).show();
                        } else {
                            disableButton();
                            Toast.makeText(Attendence_module.this, msg != null ? msg : "Attendance not available", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        disableButton();
                        Toast.makeText(Attendence_module.this, "No attendance data available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "API call unsuccessful. Response code: " + response.code());
                    logRawResponse(response);
                    disableButton();
                    Toast.makeText(Attendence_module.this, "Failed to fetch attendance status", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LiveAttendanceResponse> call, Throwable t) {
                Log.d(TAG, "Attendence api not called/ not lived");
                t.printStackTrace();
                disableButton();
                Toast.makeText(Attendence_module.this, "Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void logRawResponse(Response<?> response) {
        try {
            ResponseBody errorBody = response.errorBody();
            if (errorBody != null) {
                Log.e(TAG, "Raw ErrorBody:\n" + errorBody.string());
            } else if (response.body() != null) {
                Log.d(TAG, "Raw Body:\n" + gson.toJson(response.body()));
            } else {
                Log.e(TAG, "Both response body and error body are null");
            }
        } catch (IOException e) {
            Log.e(TAG, "Error reading raw response: " + e.getMessage());
        }
    }


    private void saveAttendance() {
        if (a_id == null || a_id.isEmpty()) {
            Toast.makeText(this, "Attendance not available to mark.", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d(TAG, "Starting saveAttendance with a_id: " + a_id);

        apiServices api = apiclient.getClient().create(apiServices.class);

        SaveAttendanceRequest request = new SaveAttendanceRequest(
                "api",
                "student_attendence_live_save",
                college,
                sessionId,
                s_id,
                a_id
        );

        Log.d(TAG, "SaveAttendance API Request Payload:\n" + gson.toJson(request));

        Call<LiveAttendanceResponse> call = api.saveAttendance(request);

        call.enqueue(new Callback<LiveAttendanceResponse>() {
            @Override
            public void onResponse(Call<LiveAttendanceResponse> call, Response<LiveAttendanceResponse> response) {
                Log.d(TAG, "SaveAttendance Response - Code: " + response.code());

                if (response.isSuccessful()) {
                    LiveAttendanceResponse res = response.body();
                    if (res != null) {
                        String msg = res.getResponse() != null ? res.getResponse().getMsg() : "Attendance marked successfully";
                        Toast.makeText(Attendence_module.this, msg, Toast.LENGTH_SHORT).show();
                        disableButton();
                    } else {
                        Toast.makeText(Attendence_module.this, "Invalid response from server", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    logRawResponse(response);
                    Toast.makeText(Attendence_module.this, "Failed to mark attendance", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LiveAttendanceResponse> call, Throwable t) {
                Log.e(TAG, "SaveAttendance API call failed: " + t.getMessage());
                t.printStackTrace();
                Toast.makeText(Attendence_module.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enableButton() {
        markAttendanceBtn.setEnabled(true);
        markAttendanceBtn.setAlpha(1f);
        markAttendanceBtn.setText("Mark Attendance");
    }

    private void disableButton() {
        markAttendanceBtn.setEnabled(false);
        markAttendanceBtn.setAlpha(0.5f);
        markAttendanceBtn.setText("Not Available");
    }
}
