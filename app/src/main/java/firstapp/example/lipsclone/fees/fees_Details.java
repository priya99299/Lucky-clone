package firstapp.example.lipsclone.fees;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;

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
    private static final String TAG = "fes";
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees_details);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        Log.d(TAG, "fees_Details Activity started");

        // Toolbar setup
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // UI init
        totalAmountTextView = findViewById(R.id.total_amount);
        dueDateTextView = findViewById(R.id.due_date);
        feeContainer = findViewById(R.id.fee_container);
        feeContainer.setLayoutManager(new LinearLayoutManager(this));
        gson = new Gson();

        try {
            // Get Intent Data
            String s_id = getIntent().getStringExtra("s_id");
            String sessionId = getIntent().getStringExtra("session");
            String college = getIntent().getStringExtra("college");
            String F_id = getIntent().getStringExtra("f_id");
            if (college == null) college = "gdcol1";

            Log.d(TAG, "Received s_id: " + s_id + ", sessionId: " + sessionId + ", college: " + college + ", f_id: " + F_id);

            // ----------- First API: student_fee -----------
            FeeRequest feeRequest = new FeeRequest("api", "student_fee", s_id, F_id, sessionId, college);
            Log.d(TAG, "Request Payload (student_fee): " + gson.toJson(feeRequest));

            apiServices api = apiclient.getClient().create(apiServices.class);
            Call<FeeResponse> call1 = api.getFeeDetails(feeRequest);

            call1.enqueue(new Callback<FeeResponse>() {
                @Override
                public void onResponse(Call<FeeResponse> call, Response<FeeResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        FeeData data = response.body().getResponse();
                        Log.d(TAG, "FeeData Response: " + gson.toJson(data));

                        if (data != null) {
                            String amount = data.getTotalDeposite();
                            String dueDate = data.getNextDueDate();

                            totalAmountTextView.setText("₹" + (amount != null ? amount : "0"));
                            dueDateTextView.setText("Due Date: " + (dueDate != null ? dueDate : "N/A"));
                        } else {
                            Log.e(TAG, "FeeData is null");
                            totalAmountTextView.setText("₹0");
                            dueDateTextView.setText("Due Date: N/A");
                        }
                    } else {
                        Log.e(TAG, "FeeDetails API Error: " + response.errorBody());
                        totalAmountTextView.setText("₹0");
                        dueDateTextView.setText("Due Date: N/A");
                    }
                }

                @Override
                public void onFailure(Call<FeeResponse> call, Throwable t) {
                    Log.e(TAG, "FeeDetails API Failure: " + t.getMessage(), t);
                    totalAmountTextView.setText("₹0");
                    dueDateTextView.setText("Due Date: N/A");
                }
            });

            // ----------- Second API: student_fee_transaction -----------
            FeeTransactionRequest transactionRequest = new FeeTransactionRequest("api", "student_fee_transaction", s_id, F_id, sessionId, college);
            Log.d(TAG, "Request Payload (student_fee_transaction): " + gson.toJson(transactionRequest));

            Call<FeeTransactionResponse> call2 = api.getFeeTransaction(transactionRequest);
            call2.enqueue(new Callback<FeeTransactionResponse>() {
                @Override
                public void onResponse(Call<FeeTransactionResponse> call, Response<FeeTransactionResponse> response) {
                    if (response.isSuccessful() && response.body() != null && !response.body().isError()) {
                        List<FeeTransactionItem> transactionList = response.body().getResponse();
                        Log.d("fes3", "Transaction count: " + transactionList.size());

                        FeeTransactionAdapter adapter = new FeeTransactionAdapter(transactionList);
                        feeContainer.setLayoutManager(new LinearLayoutManager(fees_Details.this));
                        feeContainer.setAdapter(adapter);
                    } else {
                        Log.e("fes", "API Error or Empty Response");
                    }
                }

                @Override
                public void onFailure(Call<FeeTransactionResponse> call, Throwable t) {
                    Log.e("fes", "API Failure: " + t.getMessage());
                }
            });


        } catch (Exception e) {
            Log.e(TAG, "Exception in onCreate", e);
        }
    }
}
