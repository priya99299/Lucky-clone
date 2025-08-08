package firstapp.example.lipsclone.calender;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;

import java.util.List;

import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.acadmic.AcademicCalendarRequest;
import firstapp.example.lipsclone.api.Models.acadmic.AcademicCalendarResponse;
import firstapp.example.lipsclone.api.Models.acadmic.AcademicEvent;
import firstapp.example.lipsclone.api.Network.apiServices;
import firstapp.example.lipsclone.api.Network.apiclient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class acadmicCalender extends AppCompatActivity {

    private CardView infoCard;
    private RecyclerView recyclerView;
    private TextView emptyMessage;
    private AcademicCalendarAdapter adapter;
    String s_id, sessionId, f_id, college;
    private static final String TAG = "AcadamicAPI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_acadmic_calender);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        infoCard = findViewById(R.id.infoCard);
        recyclerView = findViewById(R.id.recyclerView);
        emptyMessage = findViewById(R.id.emptyMessage);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AcademicCalendarAdapter();  // Make sure this uses the correct model import
        recyclerView.setAdapter(adapter);

        fetchAcademicCalendar();
    }

    private void fetchAcademicCalendar() {
        s_id = getIntent().getStringExtra("s_id");
        sessionId = getIntent().getStringExtra("session");
        f_id = getIntent().getStringExtra("f_id");
        college = getIntent().getStringExtra("college");

        if (college == null) college = "gdcol1";

        Log.d(TAG, "Intent Extras --> s_id: " + s_id + ", sessionId: " + sessionId +
                ", f_id: " + f_id + ", college: " + college);

        AcademicCalendarRequest request = new AcademicCalendarRequest(
                s_id, sessionId, college, f_id
        );

        Log.d(TAG, "Request: " + new Gson().toJson(request));

        apiServices api = apiclient.getClient().create(apiServices.class);
        Call<AcademicCalendarResponse> call = api.getAcademicCalendar(request);

        call.enqueue(new Callback<AcademicCalendarResponse>() {
            @Override
            public void onResponse(Call<AcademicCalendarResponse> call, Response<AcademicCalendarResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AcademicCalendarResponse res = response.body();
                    Log.d(TAG, "Response: " + new Gson().toJson(res));

                    List<AcademicEvent> events = res.getResponse();

                    if (events != null && !events.isEmpty()) {
                        if (events.get(0).getMsg() != null) {
                            showEmpty(events.get(0).getMsg());
                        } else {
                            emptyMessage.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            adapter.setEventList(events);
                        }
                    } else {
                        showEmpty("No data available.");
                    }
                } else {
                    showEmpty("Invalid response from server.");
                }
            }

            @Override
            public void onFailure(Call<AcademicCalendarResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
                showEmpty("API Failed: " + t.getMessage());
            }

            private void showEmpty(String message) {
                recyclerView.setVisibility(View.GONE);
                emptyMessage.setVisibility(View.VISIBLE);
                emptyMessage.setText(message);
            }
        });
    }
}
