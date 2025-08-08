package firstapp.example.lipsclone.Lecture_Performa;

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
import firstapp.example.lipsclone.api.Models.Lecture.LectureItem;
import firstapp.example.lipsclone.api.Models.Lecture.LectureRequest;
import firstapp.example.lipsclone.api.Models.Lecture.LectureResponse;
import firstapp.example.lipsclone.api.Network.apiServices;
import firstapp.example.lipsclone.api.Network.apiclient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Lecture_Perfrorma extends AppCompatActivity {
    private static final String TAG = "LecturePerforma";

    RecyclerView recyclerView;
    LectureAdapter adapter;
    List<LectureItem> lectureList = new ArrayList<>();
    String sessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_perfrorma);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        recyclerView = findViewById(R.id.recyclerView_lectures);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sessionId = getIntent().getStringExtra("session");
        String s_id = getIntent().getStringExtra("s_id");
        String f_id = getIntent().getStringExtra("f_id");
        String college = getIntent().getStringExtra("college");
        String semester = getIntent().getStringExtra("sem");

        if (college == null) college = "gdcol1";

        Log.d(TAG, "Intent Extras --> s_id: " + s_id + ", session: " + sessionId +
                ", f_id: " + f_id + ", college: " + college + ", semester: " + semester);

        adapter = new LectureAdapter(this, lectureList, sessionId);
        recyclerView.setAdapter(adapter);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        LectureRequest request = new LectureRequest(s_id, f_id,  semester, sessionId,college);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Log.d(TAG, "Request Payload: " + gson.toJson(request));

        apiServices api = apiclient.getClient().create(apiServices.class);
        Call<LectureResponse> call = api.getLectures(request);

        call.enqueue(new Callback<LectureResponse>() {
            @Override
            public void onResponse(Call<LectureResponse> call, Response<LectureResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LectureResponse res = response.body();
                    Log.d(TAG, "API Response: " + gson.toJson(res));

                    if (res.success && !res.response.isEmpty()) {
                        lectureList.clear();
                        lectureList.addAll(res.response);
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.w(TAG, "Empty or failed response from API.");
                    }
                } else {
                    Log.e(TAG, "Unsuccessful API response");
                }
            }

            @Override
            public void onFailure(Call<LectureResponse> call, Throwable t) {
                Log.e(TAG, "API Failure: " + t.getMessage(), t);
            }
        });
    }
}
