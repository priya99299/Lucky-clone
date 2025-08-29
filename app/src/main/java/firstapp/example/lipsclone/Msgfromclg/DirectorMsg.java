package firstapp.example.lipsclone.Msgfromclg;

import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.Messages.DirectorMessageItem;
import firstapp.example.lipsclone.api.Models.Messages.MessageResponse;
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
    TextInputLayout messageInputLayout;
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

        // ✅ Initialize adapter immediately to avoid crash
        adapter = new DirectorMsgAdapter(messageList);
        recyclerView.setAdapter(adapter);

        messageEditText = findViewById(R.id.messageEditText);
        messageInputLayout = findViewById(R.id.messageInputLayout);
        sendButton = findViewById(R.id.sendButton);

        s_id = getIntent().getStringExtra("s_id");
        session = getIntent().getStringExtra("session");

        fetchMessages();

        sendButton.setOnClickListener(v -> {
            String remark = messageEditText.getText().toString().trim();
            if (!remark.isEmpty()) {
                messageEditText.setText("");
                messageInputLayout.setError(null);
                messageInputLayout.setHelperText(null);

                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(messageEditText.getWindowToken(), 0);

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
                    messageList.clear();
                    messageList.addAll(response.body().getResponse());
                    adapter.notifyDataSetChanged();
                    Log.d(TAG, "Fetch Response: " + new Gson().toJson(response.body()));
                } else {
                    Log.e(TAG, "Fetch API Error: Code = " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Log.e(TAG, "Fetch API Failure: " + t.getMessage());
            }
        });
    }

    private void sendMessage(String remark) {
        MsgToAllRequest request = new MsgToAllRequest(s_id, session, college, remark);

        apiServices api = apiclient.getClient().create(apiServices.class);
        Call<MsgToAllResponse> call = api.sendMsgToAll(request);

        call.enqueue(new Callback<MsgToAllResponse>() {
            @Override
            public void onResponse(Call<MsgToAllResponse> call, Response<MsgToAllResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    DirectorMessageItem item = new DirectorMessageItem();
                    item.setMsg(remark);
                    item.setCdate("Now");
                    item.setCtime("");

                    messageList.add(0, item);
                    adapter.notifyItemInserted(0);
                    recyclerView.scrollToPosition(0);

                    messageEditText.post(() -> {
                        messageEditText.setText("");
                        messageInputLayout.setError(null);
                        messageInputLayout.setHelperText(null);
                    });

                } else {
                    Toast.makeText(DirectorMsg.this, "Failed: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MsgToAllResponse> call, Throwable t) {
                Log.e(TAG, "Send API Failure: " + t.getMessage());
            }
        });
    }
}
