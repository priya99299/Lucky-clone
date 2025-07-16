package firstapp.example.lipsclone.Attendence;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.attendence.AttendanceData;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder> {

    private final List<AttendanceData> attendanceList;

    public AttendanceAdapter(List<AttendanceData> attendanceList) {
        this.attendanceList = attendanceList;
    }

    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_attendance_row, parent, false);
        return new AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewHolder holder, int position) {
        AttendanceData data = attendanceList.get(position);

        if (data != null) {
            holder.tvMonth.setText(data.month);
            holder.tvPresent.setText(data.present);
            holder.tvTotalClass.setText(data.totalClass);
        }
    }

    @Override
    public int getItemCount() {
        return attendanceList != null ? attendanceList.size() : 0;
    }

    static class AttendanceViewHolder extends RecyclerView.ViewHolder {
        final TextView tvMonth;
        final TextView tvPresent;
        final TextView tvTotalClass;

        public AttendanceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMonth = itemView.findViewById(R.id.tvMonth);
            tvPresent = itemView.findViewById(R.id.tvPresent);
            tvTotalClass = itemView.findViewById(R.id.tvTotalClass);
        }
    }
}
