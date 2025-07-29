package firstapp.example.lipsclone.fees;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.Fees.FeeData;
import firstapp.example.lipsclone.api.Models.Fees.FeeRequest;
import firstapp.example.lipsclone.api.Models.Fees.FeeResponse;
import firstapp.example.lipsclone.api.Models.Fees.FeeTransactionItem;
import firstapp.example.lipsclone.api.Models.Fees.FeeTransactionRequest;
import firstapp.example.lipsclone.api.Models.Fees.FeeTransactionResponse;
import firstapp.example.lipsclone.api.Network.apiServices;
import firstapp.example.lipsclone.api.Network.apiclient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fees_Details extends AppCompatActivity {

    private TextView totalAmountTextView, dueDateTextView;
    private RecyclerView feeContainer;

    private static final String TAG = "f";
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees_details);


        Log.d(TAG, "fees_Details Activity started");

        // Toolbar setup
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // UI init
        totalAmountTextView = findViewById(R.id.total_amount);
        dueDateTextView = findViewById(R.id.due_date);
        feeContainer = findViewById(R.id.fee_container);

        gson = new Gson();

        try {
            // Get data from intent
            String s_id = getIntent().getStringExtra("s_id");
            String sessionId = getIntent().getStringExtra("session");
            String college = getIntent().getStringExtra("college");
            String F_id = getIntent().getStringExtra("f_id");

            if (college == null) college = "gdcol1";

//            // Validate intent values
//            if (s_id == null || sessionId == null || F_id == null) {
//                Log.e(TAG, "Intent data missing. s_id: " + s_id + ", session: " + sessionId + ", f_id: " + F_id);
//                return;
//            }

            Log.d(TAG, "Received s_id: " + s_id + ", sessionId: " + sessionId + ", college: " + college + ", f_id: " + F_id);

            // Create request
            FeeRequest request = new FeeRequest("api","student_fee",s_id, F_id, sessionId, college);
            Log.d(TAG, "Request Payload: " + gson.toJson(request));

            // API call
            apiServices api = apiclient.getClient().create(apiServices.class);
            Call<FeeResponse> call = api.getFeeDetails(request);

            call.enqueue(new Callback<FeeResponse>() {
                @Override
                public void onResponse(Call<FeeResponse> call, Response<FeeResponse> response) {
                    Log.d(TAG, "API onResponse called");
                    if (response.isSuccessful() && response.body() != null) {
                        String responseJson = gson.toJson(response.body());
                        Log.d(TAG, "Response Body: " + responseJson);

                        FeeData data = response.body().getResponse();

                        if (data != null) {
                            totalAmountTextView.setText("₹" + data.getTotalDeposite());
                            dueDateTextView.setText("Due Date: " + data.getNextDueDate());
                            Log.d(TAG, "Data shown on UI");
                        } else {
                            Log.e(TAG, "FeeData in response is null");
                        }
                    } else {
                        try {
                            String errorBody = response.errorBody() != null ? response.errorBody().string() : "null";
                            Log.e(TAG, "Response Error: " + errorBody);
                        } catch (Exception e) {
                            Log.e(TAG, "Error reading errorBody", e);
                        }
                    }
                }

                @Override
                public void onFailure(Call<FeeResponse> call, Throwable t) {
                    Log.e(TAG, "API Failure: " + t.getMessage(), t);
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "Exception in onCreate", e);
        }

        // Now these variables are in scope
        String s_id = getIntent().getStringExtra("s_id");
        String sessionId = getIntent().getStringExtra("session");
        String college = getIntent().getStringExtra("college");
        String F_id = getIntent().getStringExtra("f_id");
        FeeTransactionRequest transactionRequest = new FeeTransactionRequest("api", "student_fee_transaction", s_id, F_id, sessionId, college);
        apiServices api = apiclient.getClient().create(apiServices.class);
        Call<FeeTransactionResponse> call2 = api.getFeeTransaction(transactionRequest);

        call2.enqueue(new Callback<FeeTransactionResponse>() {
            @Override
            public void onResponse(Call<FeeTransactionResponse> call, Response<FeeTransactionResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<FeeTransactionItem> items = response.body().getResponse();

                    List<FeeTransactionItem> filtered = new ArrayList<>();
                    for (FeeTransactionItem item : items) {
                        if (item.getTransactionId() != null) {
                            filtered.add(item);
                        }
                    }

                    FeeTransactionAdapter adapter = new FeeTransactionAdapter(filtered);
                    feeContainer.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<FeeTransactionResponse> call, Throwable t) {
                Log.e(TAG, "FeeTransaction API Failure: " + t.getMessage());
            }
        });

    }
}
