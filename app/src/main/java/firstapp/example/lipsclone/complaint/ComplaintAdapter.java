package firstapp.example.lipsclone.complaint;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import firstapp.example.lipsclone.R;

public class ComplaintAdapter extends ArrayAdapter<String> {

    public ComplaintAdapter(Context context, List<String> complaints) {
        super(context, 0, complaints);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String complaint = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_complaint_row, parent, false);
        }

        // Split the complaint string to extract details
        // Format: "Category: x\nSubject: y\nDescription: z"
        String[] lines = complaint.split("\n");
        String category = "";
        String subject = "";
        String description = "";

        if (lines.length >= 1) category = lines[0].replace("Category: ", "");
        if (lines.length >= 2) subject = lines[1].replace("Subject: ", "");
        if (lines.length >= 3) description = lines[2].replace("Description: ", "");

        TextView categoryText = convertView.findViewById(R.id.categoryText);
        TextView subjectText = convertView.findViewById(R.id.subjectText);
        TextView statusText = convertView.findViewById(R.id.statusText);
        TextView dateText = convertView.findViewById(R.id.dateText);

        categoryText.setText(category);
        subjectText.setText(subject);
        statusText.setText("Pending"); // Set static or from data
        dateText.setText("Today");     // Set current date or from data

        return convertView;
    }
}
