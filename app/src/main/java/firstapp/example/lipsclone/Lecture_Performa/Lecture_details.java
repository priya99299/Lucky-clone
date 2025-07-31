package firstapp.example.lipsclone.Lecture_Performa;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.Lecture.Lecturedetails.LectureDetailItem;
import firstapp.example.lipsclone.api.Models.Lecture.Lecturedetails.LectureDetailResponse;
import firstapp.example.lipsclone.api.Models.Lecture.Lecturedetails.LectureInfo;
import firstapp.example.lipsclone.api.Models.Lecture.Lecturedetails.StudentLectureDetailsRequest;
import firstapp.example.lipsclone.api.Network.apiServices;
import firstapp.example.lipsclone.api.Network.apiclient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Lecture_details extends AppCompatActivity {
    private static final String TAG = "LectureDetails";

    private TextView subject, faculty, totalLecture;
    private RecyclerView recyclerView;
    private LecturedetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_details);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        subject = findViewById(R.id.subjectValue);
        faculty = findViewById(R.id.facultyValue);
        totalLecture = findViewById(R.id.totalLectureValue);
        recyclerView = findViewById(R.id.recyclerView_lectures);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String p_id = getIntent().getStringExtra("p_id");
        String sessionId = getIntent().getStringExtra("session");

        if (p_id == null || p_id.isEmpty()) {
            Log.e(TAG, "❌ p_id is missing!");
            return;
        }

        Log.d(TAG, "➡ p_id: " + p_id + ", session: " + sessionId);
        fetchLectureDetails(p_id, sessionId);
    }

    private void fetchLectureDetails(String pId, String sessionId) {
        StudentLectureDetailsRequest request = new StudentLectureDetailsRequest(pId, sessionId, "gdcol1");
        Log.d(TAG, "Request Payload => " + new Gson().toJson(request));

        apiServices api = apiclient.getClient().create(apiServices.class);
        Call<LectureDetailResponse> call = api.getStudentLectureDetails(request);

        call.enqueue(new Callback<LectureDetailResponse>() {
            @Override
            public void onResponse(Call<LectureDetailResponse> call, Response<LectureDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LectureDetailResponse res = response.body();
                    Log.d(TAG, "✅ Response Received: " + new Gson().toJson(res));

                    if (res.success && res.response != null) {
                        showLectureInfo(res.response.lectureInfo);
                        showLectureDetails(res.response.lectureDetails);
                    } else {
                        Log.w(TAG, "⚠️ Empty or failed API response");
                    }
                } else {
                    Log.e(TAG, "❌ API Error Code: " + response.code());
                    try {
                        if (response.errorBody() != null)
                            Log.e(TAG, "🔍 Error Body: " + response.errorBody().string());
                    } catch (IOException e) {
                        Log.e(TAG, "❌ Failed to read error body", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<LectureDetailResponse> call, Throwable t) {
                Log.e(TAG, "❌ Network/API Failure", t);
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
