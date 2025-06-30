package firstapp.example.lipsclone.Profile;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;

import firstapp.example.lipsclone.R;

public class Profile_module extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_module);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());


        // UI Components
        ImageView profile_photo = findViewById(R.id.profile_photo);
        TextView studentName = findViewById(R.id.student_name);
        TextView StudentadmissionNo = findViewById(R.id.admission_no);
        TextView Session = findViewById(R.id.class_name);
        TextView fathername = findViewById(R.id.father_name);
        TextView mothername = findViewById(R.id.mother_name);
        TextView contact = findViewById(R.id.contact);
        TextView address = findViewById(R.id.address);

        // Get Intent Data
        String Studentdetails = getIntent().getStringExtra("name");
        String admisionno = getIntent().getStringExtra("admno");
        String Section = getIntent().getStringExtra("class_name");
        String fname1 = getIntent().getStringExtra("fname");
        String Mothername = getIntent().getStringExtra("mname");
        String Mobile = getIntent().getStringExtra("mobile1");
        String addresss = getIntent().getStringExtra("address2");
        String ImageUrl = getIntent().getStringExtra("image_url");

        // Set Values to Views
        studentName.setText(Studentdetails);
        StudentadmissionNo.setText(admisionno);
        Session.setText(Section);
        fathername.setText(fname1);
        mothername.setText(Mothername);
        contact.setText(Mobile);
        address.setText(addresss);

        // Load Image
        if (ImageUrl != null && !ImageUrl.isEmpty()) {
            Glide.with(this).load(ImageUrl).into(profile_photo);
        } else {
            profile_photo.setImageResource(R.drawable.profile1);
        }
    }
}
