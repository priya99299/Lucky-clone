package firstapp.example.lipsclone.Msgfromclg;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.Messages.DirectorMessageItem;
import firstapp.example.lipsclone.api.Models.Messages.MessageResponse;
import firstapp.example.lipsclone.api.Models.Messages.MessageToDirectorRequest;
import firstapp.example.lipsclone.api.Models.Messages.Messages;
import firstapp.example.lipsclone.api.Network.apiServices;
import firstapp.example.lipsclone.api.Network.apiclient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DirectorMsg extends AppCompatActivity {

    private static final String TAG = "DirectorMsg";

    RecyclerView recyclerView;
    DirectorMsgAdapter adapter;
    TextInputEditText messageEditText;
    Button sendButton;

    List<DirectorMessageItem> messageList = new ArrayList<>();
    String s_id, session, college = "gdcol1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_director_msg);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.directorMsgRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);

        s_id = getIntent().getStringExtra("s_id");
        session = getIntent().getStringExtra("session");

        fetchMessages();

        sendButton.setOnClickListener(v -> {
            String remark = messageEditText.getText().toString().trim();
            if (!remark.isEmpty()) {
                sendMessage(remark);
            } else {
                Log.w(TAG, "✏️ Message is empty, not sending.");
            }
        });
    }

    private void fetchMessages() {
        Messages request = new Messages(s_id, session, college, "", "4");
        Log.d(TAG, "📤 Fetch Request Payload: " + new Gson().toJson(request));

        apiServices api = apiclient.getClient().create(apiServices.class);
        api.getStudentMessages(request).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    messageList = response.body().getResponse();
                    adapter = new DirectorMsgAdapter(messageList);
                    recyclerView.setAdapter(adapter);

                    Log.d(TAG, "✅ Fetch Response: " + new Gson().toJson(response.body()));
                } else {
                    Log.e(TAG, "❌ Fetch API Error: Code = " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Log.e(TAG, "❌ Fetch API Failure: " + t.getMessage());
            }
        });
    }

    private void sendMessage(String remark) {
        MessageToDirectorRequest request = new MessageToDirectorRequest(s_id, session, college, remark);
        Log.d(TAG, "📤 Send Request Payload: " + new Gson().toJson(request));

        apiServices api = apiclient.getClient().create(apiServices.class);
        api.sendMessageToDirector(request).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "📥 Send Response: " + new Gson().toJson(response.body()));

                    Toast.makeText(DirectorMsg.this, "✅ Message sent successfully!", Toast.LENGTH_SHORT).show();
                    messageEditText.setText("");

                    // Delay before re-fetching messages
                    new android.os.Handler().postDelayed(() -> fetchMessages(), 1000);
                } else {
                    Log.e(TAG, "❌ Send API Error: Code = " + response.code());
                    Toast.makeText(DirectorMsg.this, "❌ Error sending message", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Log.e(TAG, "❌ Send API Failure: " + t.getMessage());
                Toast.makeText(DirectorMsg.this, "❌ Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
