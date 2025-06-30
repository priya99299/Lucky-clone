package firstapp.example.lipsclone.timeTable;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.Time_table.PeriodByDay;
import firstapp.example.lipsclone.api.Models.Time_table.StudentTimeTableRequest;
import firstapp.example.lipsclone.api.Models.Time_table.StudentTimeTableResponse;
import firstapp.example.lipsclone.api.Models.Time_table.TimeTableData;
import firstapp.example.lipsclone.api.Models.Time_table.TimeTableDay;
import firstapp.example.lipsclone.api.Network.apiServices;
import firstapp.example.lipsclone.api.Network.apiclient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Time_table extends AppCompatActivity {

    private static final String TAG = "TimeTableActivity";
    private RecyclerView rvSchedule;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        // Setup toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Setup RecyclerView
        rvSchedule = findViewById(R.id.recyclerView_lectures);
        rvSchedule.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Gson
        gson = new GsonBuilder().setPrettyPrinting().create();

        // Get Intent extras safely
        String s_id = getIntent().getStringExtra("s_id");
        String sessionId = getIntent().getStringExtra("session");
        String college = getIntent().getStringExtra("college");
        if (college == null || college.trim().isEmpty()) college = "gdcol1";

        if (s_id == null || sessionId == null) {
            Log.e(TAG, "❌ Missing required parameters! s_id or sessionId is null.");
            finish();
            return;
        }

        // Build correct request
        StudentTimeTableRequest request = new StudentTimeTableRequest(
                "api",
                "student_time_table",
                s_id,
                sessionId,
                college
        );

        Log.d(TAG, "📝 Request JSON:\n" + gson.toJson(request));
        fetchStudentTimeTable(request);
    }

    private void fetchStudentTimeTable(StudentTimeTableRequest request) {
        apiServices api = apiclient.getClient().create(apiServices.class);
        Call<StudentTimeTableResponse> call = api.getStudentTimeTable(request);
        call.enqueue(new Callback<StudentTimeTableResponse>() {
            @Override
            public void onResponse(Call<StudentTimeTableResponse> call, Response<StudentTimeTableResponse> response) {
                Log.d(TAG, "✅ HTTP Code: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    StudentTimeTableResponse body = response.body();
                    Log.d(TAG, "✅ PARSED JSON:\n" + gson.toJson(body));

                    if (body.success && body.response != null && !body.response.isEmpty()) {
                        List<Object> items = parseTimeTableItems(body.response);

                        Log.d(TAG, "✅ Parsed items count: " + items.size());

                        ScheduleAdapter adapter = new ScheduleAdapter(items);
                        rvSchedule.setAdapter(adapter);
                    } else {
                        Log.e(TAG, "❌ API returned no timetable data.");
                    }
                } else {
                    Log.e(TAG, "❌ Server error");
                }
            }

            @Override
            public void onFailure(Call<StudentTimeTableResponse> call, Throwable t) {
                Log.e(TAG, "❌ Network failure: " + t.getMessage(), t);
            }
        });
    }

    private List<Object> parseTimeTableItems(List<TimeTableData> timeTableDataList) {
        List<Object> items = new ArrayList<>();
        if (timeTableDataList == null) return items;

        for (TimeTableData data : timeTableDataList) {
            if (data.time_table_final != null) {
                for (TimeTableDay day : data.time_table_final) {
                    items.add(day.Day); // always add day header

                    if (day.period_by_days != null) {
                        for (PeriodByDay period : day.period_by_days) {
                            String subject = safe(period.getSubject());
                            String teacher = safe(period.getFacultyName());
                            String room = safe(period.getLocation());
                            String time = safe(period.getTime());

                            // Only add if there's real content in subject / teacher / room
                            if (!subject.isEmpty() || !teacher.isEmpty() || !room.isEmpty()) {
                                int color = Color.parseColor("#FFFFFF");
                                ScheduleItem item = new ScheduleItem(subject, teacher, room, time, color);
                                items.add(item);
                            }
                        }
                    }
                }
            }
        }
        return items;
    }


    private String safe(String str) {
        return (str != null) ? str.trim() : "";
    }
}
