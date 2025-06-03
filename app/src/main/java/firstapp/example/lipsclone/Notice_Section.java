package firstapp.example.lipsclone;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import firstapp.example.lipsclone.api.Models.Notice;
import firstapp.example.lipsclone.api.Models.NoticeRequest;
import firstapp.example.lipsclone.api.Models.Notice_Reponse;
import firstapp.example.lipsclone.api.Models.StudentDocumentResponse;
import firstapp.example.lipsclone.api.apiServices;
import firstapp.example.lipsclone.api.apiclient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notice_Section extends AppCompatActivity {
    private RecyclerView recyclerView;
//    private NoticeAdapter adapter;
private static final String TAG = "NoticeSecFtion";
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

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columns
//        recyclerView.setAdapter(new notice_section(item_notice_card));
        String s_id = getIntent().getStringExtra("s_id");
        String sessionId = getIntent().getStringExtra("session");
        String college = "gdcol1";

        NoticeRequest request = new NoticeRequest("api", "student_notice", s_id, sessionId, "gdcol1");




        // Log payload JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Log.d(TAG, "Request JSON:\n" + gson.toJson(request));

        // API call
        apiServices api = apiclient.getClient().create(apiServices.class);
        Call<Notice_Reponse> call = api.getNotices(request);

        call.enqueue(new Callback<Notice_Reponse>() {
            @Override
            public void onResponse(Call<Notice_Reponse> call, Response<Notice_Reponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonResponse = gson.toJson(response.body());
                    Log.d(TAG, "Response JSON:\n" + jsonResponse);

                    if (response.body().response != null) {
                        for (Notice_Reponse.Notice notice : response.body().response) {
                            Log.d(TAG, "Title: " + notice.title
                                    + " | Date: " + notice.noticeDate
                                    + " | Description: " + notice.description);
                        }
                    }
                } else {
                    Log.e(TAG, "API call failed with code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Notice_Reponse> call, Throwable t) {
                Log.e(TAG, "API call error: " + t.getMessage());
            }
        });
   }
}