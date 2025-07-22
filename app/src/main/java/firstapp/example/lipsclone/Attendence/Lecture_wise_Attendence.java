package firstapp.example.lipsclone.Attendence;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import  android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import firstapp.example.lipsclone.R;

public class Lecture_wise_Attendence extends AppCompatActivity {

    TextView tvFromDate, tvToDate;
    LinearLayout fromDateLayout, toDateLayout;
    Button btnGo;
    RecyclerView attendanceRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_wise_attendence);

        tvFromDate = findViewById(R.id.tvFromDate);
        tvToDate = findViewById(R.id.tvToDate);
        fromDateLayout = findViewById(R.id.fromDateLayout);
        toDateLayout = findViewById(R.id.toDateLayout);
        btnGo = findViewById(R.id.btnGo);
        attendanceRecyclerView = findViewById(R.id.attendanceRecyclerView);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Show date picker dialogs
        fromDateLayout.setOnClickListener(v -> showDatePicker(tvFromDate));
        toDateLayout.setOnClickListener(v -> showDatePicker(tvToDate));
        btnGo.setOnClickListener(v -> {
            Toast.makeText(this, "GO clicked", Toast.LENGTH_SHORT).show(); // ✅ Debug line

            attendanceRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            List<String[]> dynamicData = new ArrayList<>();
            dynamicData.add(new String[]{"MATH", "20", "18"});
            dynamicData.add(new String[]{"SCIENCE", "22", "20"});
            dynamicData.add(new String[]{"HISTORY", "25", "22"});

            attendanceRecyclerView.setAdapter(new LectureAdapter(dynamicData));
        });

    }    // Load data on GO click


    private void showDatePicker(TextView targetView) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                    targetView.setText(date);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }
}
