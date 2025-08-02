package firstapp.example.lipsclone.timeTable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import firstapp.example.lipsclone.R;

public class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_DAY = 0;
    private static final int VIEW_TYPE_SCHEDULE = 1;

    private List<Object> scheduleItems;

    public ScheduleAdapter(List<Object> scheduleItems) {
        this.scheduleItems = scheduleItems;
    }

    @Override
    public int getItemViewType(int position) {
        Object item = scheduleItems.get(position);
        if (item instanceof String) {
            return VIEW_TYPE_DAY;
        } else {
            return VIEW_TYPE_SCHEDULE;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_DAY) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_day_header, parent, false);
            return new DayViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_time_slot, parent, false);
            return new ScheduleViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object item = scheduleItems.get(position);
        if (holder instanceof DayViewHolder) {
            ((DayViewHolder) holder).tvDay.setText((String) item);
        } else if (holder instanceof ScheduleViewHolder) {
            ScheduleItem scheduleItem = (ScheduleItem) item;
            ScheduleViewHolder scheduleHolder = (ScheduleViewHolder) holder;

            scheduleHolder.tvSubject.setText(scheduleItem.getSubject());
            scheduleHolder.tvTeacher.setText(scheduleItem.getTeacher());
            scheduleHolder.tvRoom.setText(scheduleItem.getRoom());
            scheduleHolder.tvTime.setText(scheduleItem.getTime());
            scheduleHolder.cardView.setCardBackgroundColor(scheduleItem.getColor());


        }
    }

    @Override
    public int getItemCount() {
        return scheduleItems != null ? scheduleItems.size() : 0;
    }

    public static class DayViewHolder extends RecyclerView.ViewHolder {
        TextView tvDay;

        public DayViewHolder(View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
        }
    }

    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView tvSubject;
        TextView tvTeacher;
        TextView tvRoom;
        TextView tvTime;
        CardView cardView;

        public ScheduleViewHolder(View itemView) {
            super(itemView);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvTeacher = itemView.findViewById(R.id.tvTeacher);
            tvRoom = itemView.findViewById(R.id.tvLocation);
            tvTime = itemView.findViewById(R.id.tvTiming);
            cardView = itemView.findViewById(R.id.cardView);
        }

    }
}