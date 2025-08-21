package firstapp.example.lipsclone.Msgfromclg;

import android.os.Bundle;
import android.util.Log;
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
import firstapp.example.lipsclone.api.Models.Messages.MsgToAllRequest;
import firstapp.example.lipsclone.api.Models.Messages.MsgToAllResponse;
import firstapp.example.lipsclone.api.Network.apiServices;
import firstapp.example.lipsclone.api.Network.apiclient;
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
                Log.w(TAG, "Message is empty, not sending.");
            }
        });

    }

    private void fetchMessages() {
        Messages request = new Messages(s_id, session, college, "", "4");
        Log.d(TAG, "Fetch Request Payload: " + new Gson().toJson(request));

        apiServices api = apiclient.getClient().create(apiServices.class);
        api.getStudentMessages(request).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    messageList = response.body().getResponse();
                    adapter = new DirectorMsgAdapter(messageList);
                    recyclerView.setAdapter(adapter);

                    Log.d(TAG, " Fetch Response: " + new Gson().toJson(response.body()));
                } else {
                    Log.e(TAG, " Fetch API Error: Code = " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Log.e(TAG, " Fetch API Failure: " + t.getMessage());
            }
        });
    }

    private void sendMessage(String remark) {
        // ✅ Correct Request Model
        MsgToAllRequest request = new MsgToAllRequest(
                "api",
                "msg_toall",
                s_id,
                session,
                college,
                remark
        );

        apiServices api = apiclient.getClient().create(apiServices.class);
        Call<MsgToAllResponse> call = api.sendMsgToAll(request);

        call.enqueue(new Callback<MsgToAllResponse>() {
            @Override
            public void onResponse(Call<MsgToAllResponse> call, Response<MsgToAllResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MsgToAllResponse res = response.body();

                    Log.d("Send API Response", new Gson().toJson(res));
//
//                    Toast.makeText(DirectorMsg.this,
//                            "Server: " + res.getMessage(),
//                            Toast.LENGTH_SHORT).show();

                    DirectorMessageItem item = new DirectorMessageItem();
                    item.setMsg(remark);
                    item.setCdate("Now");
                    item.setCtime("");

                    messageList.add(0, item);
                    adapter.notifyItemInserted(0);
                    recyclerView.scrollToPosition(0);

                    messageEditText.setText(""); // clear input
                } else {
                    Toast.makeText(DirectorMsg.this, "Failed: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MsgToAllResponse> call, Throwable t) {
                Toast.makeText(DirectorMsg.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




}
