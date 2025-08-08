package firstapp.example.lipsclone.calender;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.acadmic.AcademicEvent;

public class AcademicCalendarAdapter extends RecyclerView.Adapter<AcademicCalendarAdapter.ViewHolder> {

    private List<AcademicEvent> eventList;

    public void setEventList(List<firstapp.example.lipsclone.api.Models.acadmic.AcademicEvent> eventList) {
        this.eventList = eventList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_academic_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AcademicEvent event = eventList.get(position);
        holder.eventName.setText(event.getEventname());
        holder.dateRange.setText(event.getDatefrom() + " to " + event.getDateto());
    }

    @Override
    public int getItemCount() {
        return eventList != null ? eventList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventName, dateRange;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.tvEventName);
            dateRange = itemView.findViewById(R.id.tvDateRange);
        }
    }
}
