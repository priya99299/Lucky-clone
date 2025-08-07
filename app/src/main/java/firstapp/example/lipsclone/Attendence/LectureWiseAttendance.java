package firstapp.example.lipsclone.Attendence;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.attendence.LectureAttendanceRequest;
import firstapp.example.lipsclone.api.Models.attendence.LectureAttendanceResponse;
import firstapp.example.lipsclone.api.Network.apiServices;
import firstapp.example.lipsclone.api.Network.apiclient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LectureWiseAttendance extends AppCompatActivity {

    private static final String TAG = "LectureAttendanceLogs";

    TextView tvFromDate, tvToDate;
    LinearLayout fromDateLayout, toDateLayout, tableContainer;
    Button btnGo;
    RecyclerView attendanceRecyclerView;

    private String s_id, sessionId, f_id, college, sem;
    private String fromDate = "", toDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_wise_attendence);

        initViews();
        setupToolbar();
        setupDatePickers();
        setupGoButton();
    }

    private void initViews() {
        tvFromDate = findViewById(R.id.tvFromDate);
        tvToDate = findViewById(R.id.tvToDate);
        fromDateLayout = findViewById(R.id.fromDateLayout);
        toDateLayout = findViewById(R.id.toDateLayout);
        btnGo = findViewById(R.id.btnGo);
        attendanceRecyclerView = findViewById(R.id.attendanceRecyclerView);
        tableContainer = findViewById(R.id.tableContainer);
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setupDatePickers() {
        fromDateLayout.setOnClickListener(v -> showDatePicker(true));
        toDateLayout.setOnClickListener(v -> showDatePicker(false));
    }

    private void showDatePicker(boolean isFrom) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            String formatted = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(calendar.getTime());
            if (isFrom) {
                fromDate = formatted;
                tvFromDate.setText(formatted);
            } else {
                toDate = formatted;
                tvToDate.setText(formatted);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void setupGoButton() {
        btnGo.setOnClickListener(v -> {
            if (!fromDate.isEmpty() && !toDate.isEmpty()) {
                tableContainer.setVisibility(View.VISIBLE);
                loadAttendanceData();
            }
        });
    }

    private void loadAttendanceData() {
        s_id = getIntent().getStringExtra("s_id");
        sessionId = getIntent().getStringExtra("session");
        f_id = getIntent().getStringExtra("f_id");
        college = getIntent().getStringExtra("college");
        sem = getIntent().getStringExtra("sem");

        // Step 2: If Intent fails, use SharedPreferences fallback
        if (sem == null || sem.isEmpty()) {
            SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            sem = prefs.getString("sem", "");
        }
        if (college == null) college = "gdcol1";

        LectureAttendanceRequest request = new LectureAttendanceRequest(
                "api", "student_attendence_bylecture", s_id, college, f_id, fromDate, toDate, sem
        );

        Log.d(TAG, "API Request:\n" +
                "s_id: " + s_id + "\n" +
                "f_id: " + f_id + "\n" +
                "college: " + college + "\n" +
                "semester: " + sem + "\n" +
                "from: " + fromDate + "\n" +
                "to: " + toDate);

        apiServices api = apiclient.getClient().create(apiServices.class);

        api.getLectureAttendance(request).enqueue(new Callback<LectureAttendanceResponse>() {
            @Override
            public void onResponse(Call<LectureAttendanceResponse> call, Response<LectureAttendanceResponse> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().error) {
                    Log.d(TAG, "API Response: " + response.body());

                    List<String[]> attendanceRows = new ArrayList<>();
                    for (LectureAttendanceResponse.AttendanceItem item : response.body().response) {
                        attendanceRows.add(new String[]{
                                item.subject,
                                item.total_lecture_held,
                                item.total_lecture_attnd
                        });
                    }

                    attendanceRecyclerView.setLayoutManager(new LinearLayoutManager(LectureWiseAttendance.this));
                    LectureAdapter adapter = new LectureAdapter(attendanceRows);
                    attendanceRecyclerView.setAdapter(adapter);
                } else {
                    Log.e(TAG, "Invalid API Response or Empty Data");
                }
            }

            @Override
            public void onFailure(Call<LectureAttendanceResponse> call, Throwable t) {
                Log.e(TAG, "API Failure: " + t.getMessage());
            }
        });
    }
}
