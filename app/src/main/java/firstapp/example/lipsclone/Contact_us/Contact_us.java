package firstapp.example.lipsclone.Contact_us;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.api.IMapController;
import org.osmdroid.views.overlay.Marker;

import firstapp.example.lipsclone.R;

public class Contact_us extends AppCompatActivity {

    private MapView osmMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration.getInstance().load(
                getApplicationContext(),
                getSharedPreferences("osm_prefs", MODE_PRIVATE)
        );
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact_us);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Setup OSMDroid MapView
        osmMap = findViewById(R.id.osm_map);
        osmMap.setTileSource(TileSourceFactory.MAPNIK);
        osmMap.setMultiTouchControls(true);

        IMapController mapController = osmMap.getController();
        mapController.setZoom(17.0);

        // Location: Lucky Institute of Professional Studies
        GeoPoint location = new GeoPoint(26.2849727, 72.9854775);
        mapController.setCenter(location);

        // Add marker
        Marker marker = new Marker(osmMap);
        marker.setPosition(location);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setTitle("Lucky Institute of Professional Studies");
        marker.setIcon(getResources().getDrawable(R.drawable.baseline_place_24));
        osmMap.getOverlays().add(marker);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (osmMap != null) osmMap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (osmMap != null) osmMap.onPause();
    }
}
