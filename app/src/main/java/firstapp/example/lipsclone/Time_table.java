package firstapp.example.lipsclone;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import firstapp.example.lipsclone.api.Models.StudentTimeTableRequest;
import firstapp.example.lipsclone.api.Models.StudentTimeTableResponse;
import firstapp.example.lipsclone.api.apiServices;
import firstapp.example.lipsclone.api.apiclient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Time_table extends AppCompatActivity {

    private static final String TAG = "TimeTableActivity";
    private RecyclerView rvSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

//        rvSchedule = findViewById(R.id.recyclerView_lectures);
        rvSchedule.setLayoutManager(new LinearLayoutManager(this));

        String s_id = getIntent().getStringExtra("s_id");
        String sessionId = getIntent().getStringExtra("session");
        String college = getIntent().getStringExtra("college");
        if (college == null) college = "gdcol1";

        Log.d(TAG, "Received s_id: " + s_id + ", sessionId: " + sessionId + ", college: " + college);

        // Build request object and log it
        StudentTimeTableRequest request = new StudentTimeTableRequest(s_id, sessionId, college);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String requestJson = gson.toJson(request);
        Log.d(TAG, "📝 Request JSON:\n" + requestJson);

        apiServices api = apiclient.getClient().create(apiServices.class);
        Call<StudentTimeTableResponse> call = api.getStudentTimeTable(request);

        call.enqueue(new Callback<StudentTimeTableResponse>() {
            @Override
            public void onResponse(Call<StudentTimeTableResponse> call, Response<StudentTimeTableResponse> response) {
                try {
                    Log.d(TAG, "✅ Response Code: " + response.code());

                    if (response.isSuccessful() && response.body() != null) {
                        // Pretty print full JSON
                        String prettyResponse = gson.toJson(response.body());
                        Log.d(TAG, "📦 Time Table Response:\n" + prettyResponse);

                        // If needed, you can now pass this data to your RecyclerView Adapter here
                        // List<DaySchedule> scheduleList = response.body().getResponse().get(0).getTimeTableFinal();

                    } else {
                        Log.e(TAG, "❌ API response not successful. Code: " + response.code());
                        if (response.errorBody() != null) {
                            Log.e(TAG, "❌ Error Body: " + response.errorBody().string());
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "❗ Exception while processing response: " + e.getMessage(), e);
                }
            }

            @Override
            public void onFailure(Call<StudentTimeTableResponse> call, Throwable t) {
                Log.e(TAG, "❌ Network/API failure: " + t.getMessage(), t);
            }
        });
    }
}
