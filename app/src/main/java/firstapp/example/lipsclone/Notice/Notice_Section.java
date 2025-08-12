package firstapp.example.lipsclone.Notice;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.Notice.NoticeRequest;
import firstapp.example.lipsclone.api.Models.Notice.Notice_Reponse;
import firstapp.example.lipsclone.api.Network.apiServices;
import firstapp.example.lipsclone.api.Network.apiclient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notice_Section extends AppCompatActivity {

    private static final String TAG = "NoticeSection";
    private RecyclerView recyclerView;
//    private TextView emptyView;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notice_section);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        emptyView = findViewById(R.id.emptyView);

        gson = new GsonBuilder().setPrettyPrinting().create();

        // Intent data
        String s_id = getIntent().getStringExtra("s_id");
        String sessionId = getIntent().getStringExtra("session");
        String college = getIntent().getStringExtra("college");
        if (college == null) college = "gdcol1";

        Log.d(TAG, "Received s_id: " + s_id + ", sessionId: " + sessionId + ", college: " + college);

        NoticeRequest request = new NoticeRequest("api", "student_notice", s_id, sessionId, college);
        Log.d(TAG, "Request JSON:\n" + gson.toJson(request));

        // API Call
        apiServices api = apiclient.getClient().create(apiServices.class);
        Call<Notice_Reponse> call = api.getNotices(request);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        call.enqueue(new Callback<Notice_Reponse>() {
            @Override
            public void onResponse(Call<Notice_Reponse> call, Response<Notice_Reponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().response != null) {
                    List<Notice_Reponse.Notice> notices = response.body().response;

                    if (notices.isEmpty()) {
                        recyclerView.setVisibility(View.GONE);
//                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
//                        emptyView.setVisibility(View.GONE);
                        recyclerView.setAdapter(new NoticeAdapter(Notice_Section.this, notices));
                    }

                    Log.d(TAG, "Response JSON:\n" + gson.toJson(response.body()));
                } else {
                    recyclerView.setVisibility(View.GONE);
//                    emptyView.setVisibility(View.VISIBLE);
                    Log.e(TAG, "API error - Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Notice_Reponse> call, Throwable t) {
                recyclerView.setVisibility(View.GONE);
//                emptyView.setVisibility(View.VISIBLE);
                Log.e(TAG, "API call failure: " + t.getMessage(), t);
                Toast.makeText(Notice_Section.this, "No updates", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
