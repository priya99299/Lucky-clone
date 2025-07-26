package firstapp.example.lipsclone.Attendence;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import firstapp.example.lipsclone.R;

public class LectureAdapter extends RecyclerView.Adapter<LectureAdapter.AttendanceViewHolder> {

    private final List<String[]> attendanceList;

    public LectureAdapter(List<String[]> data) {
        attendanceList = new ArrayList<>();
      
        attendanceList.addAll(data);
    }

    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attendancelecture_row, parent, false);
        return new AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewHolder holder, int position) {
        String[] row = attendanceList.get(position);

        holder.tvSubject.setText(row[0]);
        holder.tvTotal.setText(row[1]);
        holder.tvAttd.setText(row[2]);

        if (position == 0) {


            holder.tvSubject.setTextColor(Color.WHITE);
            holder.tvTotal.setTextColor(Color.WHITE);
            holder.tvAttd.setTextColor(Color.WHITE);


        } else {
            // Data styling
            holder.tvSubject.setTypeface(null, Typeface.NORMAL);
            holder.tvTotal.setTypeface(null, Typeface.NORMAL);
            holder.tvAttd.setTypeface(null, Typeface.NORMAL);

            holder.tvSubject.setTextColor(Color.BLACK);
            holder.tvTotal.setTextColor(Color.BLACK);
            holder.tvAttd.setTextColor(Color.BLACK);

            holder.itemView.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    static class AttendanceViewHolder extends RecyclerView.ViewHolder {
        TextView tvSubject, tvTotal, tvAttd;

        public AttendanceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvTotal = itemView.findViewById(R.id.tvTotalLecture);
            tvAttd = itemView.findViewById(R.id.tvClassAttd);
        }
    }
}
