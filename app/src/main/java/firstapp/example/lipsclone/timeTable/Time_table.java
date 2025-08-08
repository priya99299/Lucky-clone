package firstapp.example.lipsclone.timeTable;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
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
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        rvSchedule = findViewById(R.id.recyclerView_lectures);
        rvSchedule.setLayoutManager(new LinearLayoutManager(this));

        gson = new GsonBuilder().setPrettyPrinting().create();

        String s_id = getIntent().getStringExtra("s_id");
        String sessionId = getIntent().getStringExtra("session");
        String college = getIntent().getStringExtra("college");
        if (college == null || college.trim().isEmpty()) college = "gdcol1";

        Log.d(TAG, "Intent Extras --> s_id: " + s_id + ", sessionId: " + sessionId + ", college: " + college);

        if (s_id == null || sessionId == null) {
            Log.e(TAG, "Missing s_id or sessionId. Exiting activity.");
            finish();
            return;
        }

        StudentTimeTableRequest request = new StudentTimeTableRequest(
                "api", "student_time_table", s_id, sessionId, college
        );

        Log.d(TAG, "Request Payload:\n" + gson.toJson(request));
        fetchStudentTimeTable(request);
    }

    private void fetchStudentTimeTable(StudentTimeTableRequest request) {
        apiServices api = apiclient.getClient().create(apiServices.class);
        Call<StudentTimeTableResponse> call = api.getStudentTimeTable(request);

        call.enqueue(new Callback<StudentTimeTableResponse>() {
            @Override
            public void onResponse(Call<StudentTimeTableResponse> call, Response<StudentTimeTableResponse> response) {
                Log.d(TAG, "HTTP Code: " + response.code());

                if (response.isSuccessful() && response.body() != null) {
                    StudentTimeTableResponse body = response.body();
                    Log.d(TAG, "Full Response:\n" + gson.toJson(body));

                    if (body.success && body.response != null && !body.response.isEmpty()) {
                        extractAndStoreSemesterInfo(body.response);

                        List<Object> items = parseTimeTableItems(body.response);
                        Log.d(TAG, "Parsed items count: " + items.size());

                        ScheduleAdapter adapter = new ScheduleAdapter(items);
                        rvSchedule.setAdapter(adapter);
                    } else {
                        Log.e(TAG, "No timetable data or success=false");
                    }
                } else {
                    Log.e(TAG, "API Error - Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<StudentTimeTableResponse> call, Throwable t) {
                Log.e(TAG, "Network failure: " + t.getMessage(), t);
            }
        });
    }

    /**
     * Extract semester information and store it in SharedPreferences
     */
    private void extractAndStoreSemesterInfo(List<TimeTableData> dataList) {
        Log.d(TAG, "Extracting semester info...");

        for (TimeTableData data : dataList) {
            if (data != null && data.course_details != null) {
                String semester = safe(data.course_details.semester);
                String courseName = safe(data.course_details.courseName);
                String createdBy = safe(data.course_details.createdBy);
                String totalPeriods = safe(data.course_details.totalPeriods);

                Log.d(TAG, "Extracted --> Semester: " + semester + ", Course: " + courseName);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (!semester.isEmpty()) {
                    editor.putString("semester", semester);
                    Log.d(TAG, " Stored semester: " + semester);
                }
                if (!courseName.isEmpty()) {
                    editor.putString("course_name", courseName);
                }
                if (!createdBy.isEmpty()) {
                    editor.putString("created_by", createdBy);
                }
                if (!totalPeriods.isEmpty()) {
                    editor.putString("total_periods", totalPeriods);
                }

                editor.apply();
                verifyStoredData();
                break; // store only first valid entry
            }
        }
    }

    private void verifyStoredData() {
        Log.d(TAG, "Verifying SharedPreferences data:");
        Log.d(TAG, " - semester: " + sharedPreferences.getString("semester", "NOT_FOUND"));
        Log.d(TAG, " - course_name: " + sharedPreferences.getString("course_name", "NOT_FOUND"));
        Log.d(TAG, " - created_by: " + sharedPreferences.getString("created_by", "NOT_FOUND"));
        Log.d(TAG, " - total_periods: " + sharedPreferences.getString("total_periods", "NOT_FOUND"));
    }

    private List<Object> parseTimeTableItems(List<TimeTableData> dataList) {
        List<Object> items = new ArrayList<>();
        if (dataList == null) return items;

        for (TimeTableData data : dataList) {
            if (data.time_table_final != null) {
                for (TimeTableDay day : data.time_table_final) {
                    items.add(day.Day);

                    if (day.period_by_days != null) {
                        for (PeriodByDay period : day.period_by_days) {
                            String subject = safe(period.getSubject());
                            String teacher = safe(period.getFacultyName());
                            String room = safe(period.getLocation());
                            String time = safe(period.getTime());

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
        return str != null ? str.trim() : "";
    }
}
