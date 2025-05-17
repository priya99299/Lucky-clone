package firstapp.example.lipsclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
//        IEtem
        CardView profile,Stuattendence,notes,timetabl,address,documen,noticeboard;
        Button btn;
//        notes=findViewById(R.id.Notes);
        btn = findViewById(R.id.btm);
        profile = findViewById(R.id. stuProfile);
        Stuattendence =findViewById(R.id.attendence);
//        address=findViewById(R.id.contact);
//        documen=findViewById(R.id.document);
//        noticeboard=findViewById(R.id.Notice);
//        timetabl=findViewById(R.id.timeTable);


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
                Intent profileIntent = new Intent(getApplicationContext(),Profile_module.class);
                startActivity(profileIntent);
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
//        Notes module
//        notes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent notes=new Intent(getApplicationContext(),Notes.class);
//                startActivity(notes);
//
//            }
//        });
//        timeaTable
//        timetabl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent timetable= new Intent(getApplicationContext(),tim)
//            }
//        });

//        Contact-us
//        address.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent contact=new Intent(getApplicationContext(),Contact_us.class);
//            }
//        });
////        Document module
//        documen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent contact=new Intent(getApplicationContext(),Contact_us.class);
//            }
//        });
////        Notice-Board
//        noticeboard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent contact=new Intent(getApplicationContext(),Contact_us.class);
//            }
//        });
////        Time-Table
//        timetabl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent timTable=new Intent(getApplicationContext(),TimaTable.class);
//            }
//        });




    }

}
