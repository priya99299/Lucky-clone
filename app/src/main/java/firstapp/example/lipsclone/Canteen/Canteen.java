package firstapp.example.lipsclone.Canteen;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.canteen.CanteenItem;
import firstapp.example.lipsclone.api.Models.canteen.CanteenMenuRequest;
import firstapp.example.lipsclone.api.Models.canteen.CanteenResponse;
import firstapp.example.lipsclone.api.Network.apiServices;
import firstapp.example.lipsclone.api.Network.apiclient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Canteen extends AppCompatActivity {

    RecyclerView recyclerView;
    private static final String TAG = "CanteenAPI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_canteen);

        recyclerView = findViewById(R.id.recyclerViewCanteen);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadCanteenMenu();
    }

    private void loadCanteenMenu() {
        apiServices api = apiclient.getClient().create(apiServices.class);

        CanteenMenuRequest request = new CanteenMenuRequest("api", "canteen");

        api.getCanteenMenu(request).enqueue(new Callback<CanteenResponse>() {
            @Override
            public void onResponse(Call<CanteenResponse> call, Response<CanteenResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CanteenItem> menuList = response.body().getResponse();
                    recyclerView.setAdapter(new CanteenAdapter(Canteen.this, menuList));

                    // Log raw JSON
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    Log.d(TAG, "API Response: \n" + gson.toJson(response.body()));
                } else {
                    Log.e(TAG, "API Failed - Code: " + response.code());
                    try {
                        Log.e(TAG, "Error body: " + response.errorBody().string());
                    } catch (Exception ignored) {}
                    Toast.makeText(Canteen.this, "Failed to load menu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CanteenResponse> call, Throwable t) {
                Toast.makeText(Canteen.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "API Error", t);
            }
        });
    }

}
