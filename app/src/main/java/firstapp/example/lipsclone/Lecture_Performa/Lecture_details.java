package firstapp.example.lipsclone.Lecture_Performa;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.Lecture.Lecturedetails.LectureDetailItem;
import firstapp.example.lipsclone.api.Models.Lecture.Lecturedetails.LectureDetailResponse;
import firstapp.example.lipsclone.api.Models.Lecture.Lecturedetails.StudentLectureDetailsRequest;
import firstapp.example.lipsclone.api.Models.Lecture.Lecturedetails.LectureInfo;
import firstapp.example.lipsclone.api.Network.apiServices;
import firstapp.example.lipsclone.api.Network.apiclient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class Lecture_details extends AppCompatActivity {

    private static final String TAG = "LectureDetails";
    private TextView subject, faculty, totalLecture;
    private RecyclerView recyclerView;
    private LecturedetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lecture_details);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom

            );
            return insets;

        });
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        subject = findViewById(R.id.subjectValue);
        faculty = findViewById(R.id.facultyValue);
        totalLecture = findViewById(R.id.totalLectureValue);
        recyclerView = findViewById(R.id.recyclerView_lectures);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String p_id = getIntent().getStringExtra("p_id");
        if (p_id == null || p_id.isEmpty()) {
            Log.e(TAG, "p_id is missing!");
            return;
        }

        fetchLectureDetails(p_id);
    }

    private void fetchLectureDetails(String pId) {
        StudentLectureDetailsRequest request = new StudentLectureDetailsRequest(pId, "2024_25", "gdcol1");

        apiServices api = apiclient.getClient().create(apiServices.class);
        Call<LectureDetailResponse> call = api.getStudentLectureDetails(request);

        call.enqueue(new Callback<LectureDetailResponse>() {
            @Override
            public void onResponse(Call<LectureDetailResponse> call, Response<LectureDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().success) {
                    LectureInfo info = response.body().response.lectureInfo;
                    List<LectureDetailItem> details = response.body().response.lectureDetails;
                    if (info != null) showLectureInfo(info);
                    if (details != null && !details.isEmpty()) showLectureDetails(details);
                    else Log.e(TAG, "lectureDetails empty!");
                } else {
                    Log.e(TAG, "Response not successful!");
                }
            }

            @Override
            public void onFailure(Call<LectureDetailResponse> call, Throwable t) {
                Log.e(TAG, "API Call Failed", t);
            }
        });
    }

    private void showLectureInfo(LectureInfo info) {
        subject.setText(info.subject != null ? info.subject : "--");
        faculty.setText(info.facultyName != null ? info.facultyName : "--");
        totalLecture.setText(info.totalLecture != null ? info.totalLecture : "--");
    }

    private void showLectureDetails(List<LectureDetailItem> details) {
        adapter = new LecturedetailsAdapter(this, details);
        recyclerView.setAdapter(adapter);
    }
}
