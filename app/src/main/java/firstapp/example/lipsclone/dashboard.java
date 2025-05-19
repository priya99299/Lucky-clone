package firstapp.example.lipsclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import firstapp.example.lipsclone.api.Models.AppConfigResponse;

public class dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageView ProfilePic;
        TextView name,Session;
        CardView profile,Stuattendence,notes,timetabl,address,documen,noticeboard;
        Button btn;

        name=findViewById(R.id.studentName);
        Session=findViewById(R.id.Section);
        ProfilePic=findViewById(R.id.studentImage);


        btn = findViewById(R.id.btm);
        profile = findViewById(R.id. stuProfile);
        Stuattendence =findViewById(R.id.attendence);


        String Studentdetails=getIntent().getStringExtra("name");
        String  class_name=getIntent().getStringExtra("class_name");
        //sset ttxton dasboard
         name.setText(Studentdetails);
         Session.setText(class_name);



        // Logout button click listener
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(logout);
            }
        });

        // Profile card click listener
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent students=new Intent(dashboard.this,Profile_module.class);
                startActivity(students);
            }
        });
//        attendence module
        Stuattendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent attedence= new Intent(getApplicationContext(),Attendence_module.class);
                startActivity(attedence);
            }
        });








    }

}
