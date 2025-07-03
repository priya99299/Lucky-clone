package firstapp.example.lipsclone.Lecture_Performa;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

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

public class Lecture_details extends AppCompatActivity {

    private static final String TAG = "LectureDetails";

    private TextView subject, faculty, totalLecture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lecture_details);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        subject = findViewById(R.id.subjectValue);
        faculty = findViewById(R.id.facultyValue);
//        totalLecture = findViewById(R.id.totalLectureValue);

        String p_id = getIntent().getStringExtra("p_id");
        Log.d(TAG, "✅ Received p_id from Intent: " + p_id);

        if (p_id == null || p_id.isEmpty()) {
            Log.e(TAG, " p_id is missing in Intent extras!");
            return;
        }

        fetchLectureDetails(p_id);
    }

    private void fetchLectureDetails(String pId) {
        Log.d(TAG, "Calling API with p_id: " + pId);

        StudentLectureDetailsRequest request = new StudentLectureDetailsRequest(pId, "2024_25", "gdcol1");


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Log.d(TAG, "Request Payload:\n" + gson.toJson(request));

        apiServices api = apiclient.getClient().create(apiServices.class);

        Call<LectureDetailResponse> call = api.getStudentLectureDetails(request);
        call.enqueue(new Callback<LectureDetailResponse>() {
            @Override
            public void onResponse(Call<LectureDetailResponse> call, Response<LectureDetailResponse> response) {
                Log.d(TAG, "🌐 HTTP Status Code: " + response.code());
                Log.d(TAG, "🌐 Raw Response: " + response.raw());

                try {
                    if (response.isSuccessful() && response.body() != null) {
                        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
                        Log.d(TAG, "✅ Parsed Success Body:\n" + prettyGson.toJson(response.body()));

                        if (response.body().success) {
                            LectureInfo info = response.body().response.lectureInfo;
                            List<LectureDetailItem> details = response.body().response.lectureDetails;

                            if (info != null) {
                                showLectureInfo(info);
                            } else {
                                Log.e(TAG, " lectureInfo is null!");
                            }

                            if (details != null && !details.isEmpty()) {
                                showLectureDetails(details);
                            } else {
                                Log.e(TAG, " lectureDetails is empty or null!");
                            }

                        } else {
                            Log.e(TAG, " Server responded with success=false!");
                        }

                    } else {
                        Log.e(TAG, "Unsuccessful response!");
                        if (response.errorBody() != null) {
                            String errorRaw = response.errorBody().string();
                            Log.e(TAG, "ErrorBody:\n" + errorRaw);
                        } else {
                            Log.e(TAG, " ErrorBody is null");
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, " Exception parsing response", e);
                    try {
                        if (response.errorBody() != null) {
                            Log.e(TAG, "ErrorBody:\n" + response.errorBody().string());
                        }
                    } catch (Exception ex) {
                        Log.e(TAG, " Exception reading errorBody", ex);
                    }
                }
            }


            @Override
            public void onFailure(Call<LectureDetailResponse> call, Throwable t) {
                Log.e(TAG, "🚫 API Call Failed: " + t.getMessage(), t);
            }
        });

    }
    private void showLectureDetails(List<LectureDetailItem> details) {
        if (details == null || details.isEmpty()) {
            Log.e(TAG, " lectureDetails list is empty or null!");
            return;
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Log.d(TAG, "Showing Lecture Details:\n" + gson.toJson(details));
    }


    private void showLectureInfo(LectureInfo info) {
        Log.d(TAG, " Showing Lecture Info: " + new Gson().toJson(info));

        subject.setText(info.subject != null ? info.subject : "--");
        faculty.setText(info.facultyName != null ? info.facultyName : "--");
        totalLecture.setText(info.totalLecture != null ? info.totalLecture : "--");
    }
}
