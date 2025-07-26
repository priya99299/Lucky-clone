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
import firstapp.example.lipsclone.api.Models.attendence.AttendanceData;

public class TableStyleLectureAdapter extends RecyclerView.Adapter<TableStyleLectureAdapter.AttendanceViewHolder> {

    private final List<AttendanceData> attendanceListWithHeader;

    public TableStyleLectureAdapter(List<AttendanceData> data) {
        attendanceListWithHeader = new ArrayList<>();
        attendanceListWithHeader.add(new AttendanceData("Subject", "0", "0"));

        attendanceListWithHeader.addAll(data); // Real data
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
        AttendanceData data = attendanceListWithHeader.get(position);

        holder.tvSubject.setText(data.getMonth());
        holder.tvTotalLecture.setText(position == 0 ? "Total" : String.valueOf(data.getTotalClass()));
        holder.tvClassAttd.setText(position == 0 ? "Attended" : String.valueOf(data.getPresent()));

        if (position == 0) {
            // Style header
            holder.tvSubject.setTypeface(null, Typeface.BOLD);
            holder.tvTotalLecture.setTypeface(null, Typeface.BOLD);
            holder.tvClassAttd.setTypeface(null, Typeface.BOLD);

            holder.tvSubject.setTextColor(Color.WHITE);
            holder.tvTotalLecture.setTextColor(Color.WHITE);
            holder.tvClassAttd.setTextColor(Color.WHITE);

            holder.itemView.setBackgroundColor(Color.parseColor("#4B3F9C"));
        } else {
            // Style data
            holder.tvSubject.setTypeface(null, Typeface.NORMAL);
            holder.tvTotalLecture.setTypeface(null, Typeface.NORMAL);
            holder.tvClassAttd.setTypeface(null, Typeface.NORMAL);

            holder.tvSubject.setTextColor(Color.BLACK);
            holder.tvTotalLecture.setTextColor(Color.BLACK);
            holder.tvClassAttd.setTextColor(Color.BLACK);

            holder.itemView.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return attendanceListWithHeader.size();
    }

    static class AttendanceViewHolder extends RecyclerView.ViewHolder {
        TextView tvSubject, tvTotalLecture, tvClassAttd;

        public AttendanceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvTotalLecture = itemView.findViewById(R.id.tvTotalLecture);
            tvClassAttd = itemView.findViewById(R.id.tvClassAttd);
        }
    }
}
