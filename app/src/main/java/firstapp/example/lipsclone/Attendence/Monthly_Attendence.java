package firstapp.example.lipsclone.Attendence;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import firstapp.example.lipsclone.R;

import firstapp.example.lipsclone.api.Models.Downloads.StudentDownloadResponse;
import firstapp.example.lipsclone.api.Models.attendence.AttendanceData;
import firstapp.example.lipsclone.api.Models.attendence.AttendanceRequest;
import firstapp.example.lipsclone.api.Models.attendence.AttendanceResponse;
import firstapp.example.lipsclone.api.Network.apiServices;
import firstapp.example.lipsclone.api.Network.apiclient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Monthly_Attendence extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AttendanceAdapter adapter;
    private String s_id, sessionId, f_id, college, a_id, sem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_monthly_attendence);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.attendanceRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initially empty
        adapter = new AttendanceAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        fetchAttendanceData();
    }
    private void fetchAttendanceData() {
        final String TAG = "AttendanceAPI";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();


        // Get intent extras
        s_id = getIntent().getStringExtra("s_id");
        sessionId = getIntent().getStringExtra("session");
        f_id = getIntent().getStringExtra("f_id");
        college = getIntent().getStringExtra("college");
        sem = getIntent().getStringExtra("semester");

        if (college == null) college = "gdcol1";

        Log.d(TAG, "Intent Extras --> s_id: " + s_id + ", sessionId: " + sessionId +
                ", f_id: " + f_id + ", college: " + college + ", semester: " + sem);

        String fId = getIntent().getStringExtra("f_id");

        Log.d(TAG, "Intent Extras Check --> f_id"+fId);
        AttendanceRequest request = new AttendanceRequest(s_id, sessionId, college, fId);


//        request.setSem("");

        String requestJson = gson.toJson(request);
        Log.d(TAG, "--> REQUEST PAYLOAD:\n" + requestJson);

        apiServices api = apiclient.getClient().create(apiServices.class);
        Call<AttendanceResponse> call = api.getMonthlyAttendance(request);
        call.enqueue(new Callback<AttendanceResponse>() {
            @Override
            public void onResponse(Call<AttendanceResponse> call, Response<AttendanceResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseJson = gson.toJson(response.body());
                    Log.d(TAG, "--> RESPONSE PAYLOAD:\n" + responseJson);

                    if (response.body().isSuccess() && response.body().getResponse() != null && !response.body().getResponse().isEmpty()) {
                        Log.d(TAG, "Loaded attendance list with " + response.body().getResponse().size() + " items.");
                        adapter = new AttendanceAdapter(response.body().getResponse());
                        recyclerView.setAdapter(adapter);
                    } else {
                        Log.d(TAG, "Server returned an empty attendance list.");
                        Toast.makeText(Monthly_Attendence.this, "No attendance data found.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "RESPONSE ERROR CODE: " + response.code());
                    Toast.makeText(Monthly_Attendence.this, "Server Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AttendanceResponse> call, Throwable t) {
                Log.e(TAG, "FAILURE: ", t);
                Toast.makeText(Monthly_Attendence.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }





}
