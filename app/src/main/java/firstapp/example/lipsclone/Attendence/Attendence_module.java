package firstapp.example.lipsclone.Attendence;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    private String s_id, sessionId, f_id, college, a_id;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_attendence_module);

        gson = new GsonBuilder().setPrettyPrinting().create();

        // Handle status bar insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Toolbar back button
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Get intent extras
        s_id = getIntent().getStringExtra("s_id");
        sessionId = getIntent().getStringExtra("session");
        f_id = getIntent().getStringExtra("f_id");
        college = getIntent().getStringExtra("college");
        if (college == null) college = "gdcol1";

        Log.d(TAG, "Intent Extras --> s_id: " + s_id + ", sessionId: " + sessionId + ", f_id: " + f_id + ", college: " + college);

        // Monthly Attendance CardView click
        findViewById(R.id.Monthly).setOnClickListener(v -> {
            Intent monthly = new Intent(this, Monthly_Attendence.class);
            monthly.putExtra("s_id", s_id);
            monthly.putExtra("session", sessionId);
            monthly.putExtra("f_id", f_id);
            startActivity(monthly);
        });

        // Lecture-wise Attendance CardView click
        findViewById(R.id.Lecture).setOnClickListener(v -> {
            Intent lectureIntent = new Intent(this, Lecture_wise_Attendence.class);
            lectureIntent.putExtra("s_id", s_id);
            lectureIntent.putExtra("session", sessionId);
            lectureIntent.putExtra("f_id", f_id);
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
        apiServices api = apiclient.getClient().create(apiServices.class);

        LiveAttendanceRequest request = new LiveAttendanceRequest(
                "api",
                "student_attendence_live",
                college,
                sessionId,
                s_id,
                f_id
        );

        // Log request payload
        Log.d(TAG, "LiveAttendance API Request Payload:\n" + gson.toJson(request));

        Call<LiveAttendanceResponse> call = api.getLiveAttendance(request);

        call.enqueue(new Callback<LiveAttendanceResponse>() {
            @Override
            public void onResponse(Call<LiveAttendanceResponse> call, Response<LiveAttendanceResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "LiveAttendance API Response:\n" + gson.toJson(response.body()));
                    LiveAttendanceResponse res = response.body();

                    if (res.isSuccess() && !res.isError() && res.getResponse() != null) {
                        String status = res.getResponse().getStatus();
                        String markStatus = res.getResponse().getMark_status();
                        a_id = res.getResponse().getA_id();

                        Log.d(TAG, "status: " + status + ", mark_status: " + markStatus + ", a_id: " + a_id);

                        if ("1".equals(status) && "1".equals(markStatus)) {
                            enableButton();
                        } else {
                            disableButton();
                        }
                    } else {
                        Log.e(TAG, "API returned error or null response data");
                        disableButton();
                    }
                } else {
                    // 🔴 Log raw error body for debugging
                    ResponseBody errorBody = response.errorBody();
                    if (errorBody != null) {
                        try {
                            Log.e(TAG, "ErrorBody:\n" + errorBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.e(TAG, "API call failed. Response code: " + response.code());
                    disableButton();
                }
            }

            @Override
            public void onFailure(Call<LiveAttendanceResponse> call, Throwable t) {
                Log.e(TAG, "API call failed: " + t.getMessage());
                disableButton();
            }
        });
    }

    /**
     * Calls save attendance API on button click
     */
    private void saveAttendance() {
        if (a_id == null || a_id.isEmpty()) {
            Toast.makeText(this, "Attendance not available to mark.", Toast.LENGTH_SHORT).show();
            return;
        }

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
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "SaveAttendance API Response:\n" + gson.toJson(response.body()));
                    LiveAttendanceResponse res = response.body();
                    String msg = res.getResponse() != null ? res.getResponse().getMsg() : "Attendance marked.";

                    Toast.makeText(Attendence_module.this, msg, Toast.LENGTH_SHORT).show();
                    disableButton(); // disable after marking
                } else {
                    Log.e(TAG, "SaveAttendance API call failed. Response code: " + response.code());
                    Toast.makeText(Attendence_module.this, "Failed to mark attendance.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LiveAttendanceResponse> call, Throwable t) {
                Log.e(TAG, "SaveAttendance API call failed: " + t.getMessage());
                Toast.makeText(Attendence_module.this, "API error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enableButton() {
        markAttendanceBtn.setEnabled(true);
        markAttendanceBtn.setAlpha(1f);
    }

    private void disableButton() {
        markAttendanceBtn.setEnabled(false);
        markAttendanceBtn.setAlpha(0.5f);
    }
}
