package firstapp.example.lipsclone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

public class fees_Details extends AppCompatActivity {

    String[][] feeData = {
            {"Tuition Fee", "1000.00", "500.00"},
            {"Library Fee", "50.00", "25.00"},
            {"Lab Fee", "75.00", "37.50"},
            {"Activity Fee", "25.00", "12.50"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees_details);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        RecyclerView container = findViewById(R.id.fee_container);
        LayoutInflater inflater = LayoutInflater.from(this);

        for (String[] fee : feeData) {
            View cardView = inflater.inflate(R.layout.item_fee, container, false);

            TextView name = cardView.findViewById(R.id.fee_name);
            TextView amount = cardView.findViewById(R.id.fee_amount);
            TextView paid = cardView.findViewById(R.id.pay_in);

            name.setText(fee[0]);
            amount.setText("$" + fee[1]);
            paid.setText("$" + fee[2]);

            container.addView(cardView);
        }
    }
}
