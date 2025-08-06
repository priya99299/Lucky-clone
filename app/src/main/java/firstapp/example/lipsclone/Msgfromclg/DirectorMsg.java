package firstapp.example.lipsclone.Msgfromclg;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;

import java.util.List;

import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.Messages.DirectorMessageItem;
import firstapp.example.lipsclone.api.Models.Messages.MessageResponse;
import firstapp.example.lipsclone.api.Models.Messages.Messages;
import firstapp.example.lipsclone.api.Network.apiServices;
import firstapp.example.lipsclone.api.Network.apiclient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DirectorMsg extends AppCompatActivity {

    private static final String TAG = "DirectorMSg";

    RecyclerView recyclerView;
    DirectorMsgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_director_msg);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.directorMsgRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String s_id = getIntent().getStringExtra("s_id");
        String session = getIntent().getStringExtra("session");
        String college = "gdcol1";

        fetchMessages(s_id, session, college);
    }

    private void fetchMessages(String s_id, String session, String college) {
        apiServices api = apiclient.getClient().create(apiServices.class);

        Messages request = new Messages(s_id, session, college, "", "4");

        // ✅ Log the request payload
        String requestJson = new Gson().toJson(request);
        Log.d(TAG, "Request Payload: " + requestJson);

        Call<MessageResponse> call = api.getStudentMessages(request);

        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {

                    List<DirectorMessageItem> list = response.body().getResponse();
                    adapter = new DirectorMsgAdapter(list);
                    recyclerView.setAdapter(adapter);

                    // ✅ Log the response body
                    String responseJson = new Gson().toJson(response.body());
                    Log.d(TAG, "Response JSON: " + responseJson);

                } else {
                    Log.e(TAG, "API Failed - HTTP Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Log.e(TAG, "API Call Failed: " + t.getMessage());
            }
        });
    }

}
