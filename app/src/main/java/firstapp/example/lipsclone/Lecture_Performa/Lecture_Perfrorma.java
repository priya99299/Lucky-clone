package firstapp.example.lipsclone.Lecture_Performa;
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
import firstapp.example.lipsclone.api.Models.Lecture.LectureItem;
import firstapp.example.lipsclone.api.Models.Lecture.LectureRequest;
import firstapp.example.lipsclone.api.Models.Lecture.LectureResponse;
import firstapp.example.lipsclone.api.Network.apiServices;
import firstapp.example.lipsclone.api.Network.apiclient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Lecture_Perfrorma extends AppCompatActivity {

    RecyclerView recyclerView;
    LectureAdapter adapter;
    List<LectureItem> lectureList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_perfrorma);

        recyclerView = findViewById(R.id.recyclerView_lectures);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new LectureAdapter(this, lectureList);
        recyclerView.setAdapter(adapter);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        fetchLectures();
    }

    private void fetchLectures() {
        String s_id = getIntent().getStringExtra("s_id");
        String sessionId = getIntent().getStringExtra("session");
        String college = getIntent().getStringExtra("college");
        if (college == null) college = "gdcol1";

        Log.d("response", "Received s_id: " + s_id + ", sessionId: " + sessionId + ", college: " + college);
        LectureRequest request = new LectureRequest("5552", "18", sessionId, college);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Log.d("Request Payload of lecture", gson.toJson(request));

        apiServices api = apiclient.getClient().create(apiServices.class);
        Call<LectureResponse> call = api.getLectures(request);

        call.enqueue(new Callback<LectureResponse>() {
            @Override
            public void onResponse(Call<LectureResponse> call, Response<LectureResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LectureResponse res = response.body();
                    Log.d("Response of lectrue", gson.toJson(res));

                    if (res.success && !res.response.isEmpty()) {
                        lectureList.clear();
                        lectureList.addAll(res.response);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Log.e("❌ Error", "Unsuccessful response");
                }
            }

            @Override
            public void onFailure(Call<LectureResponse> call, Throwable t) {
                Log.e("🚫 API Failed", t.getMessage());
            }
        });
    }
}
