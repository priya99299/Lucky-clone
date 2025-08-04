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
import firstapp.example.lipsclone.api.Models.complaint.Complaint;

public class ComplaintAdapter extends ArrayAdapter<Complaint> {

    public ComplaintAdapter(Context context, List<Complaint> complaints) {
        super(context, 0, complaints);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Complaint complaint = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_complaint_row, parent, false);
        }

        TextView categoryText = convertView.findViewById(R.id.categoryText);
        TextView subjectText = convertView.findViewById(R.id.subjectText);
        TextView statusText = convertView.findViewById(R.id.statusText);
        TextView dateText = convertView.findViewById(R.id.dateText);

        if (complaint != null) {
            categoryText.setText(complaint.getTitle()); // Title of complaint
            subjectText.setText(complaint.getDescription()); // Description of complaint
            statusText.setText(complaint.getStatus()); // Status like Pending
            dateText.setText(complaint.getDate()); // Complaint date
        }

        return convertView;
    }
}
