package firstapp.example.lipsclone.Msgfromclg;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.Messages.DirectorMessageItem;

public class DirectorMsgAdapter extends RecyclerView.Adapter<DirectorMsgAdapter.ViewHolder> {

    private final List<DirectorMessageItem> messageList;

    public DirectorMsgAdapter(List<DirectorMessageItem> messageList) {
        this.messageList = messageList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView messageText, dateTime;

        public ViewHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
            dateTime = itemView.findViewById(R.id.dateTime);
        }

        public void bind(DirectorMessageItem item) {
            messageText.setText(item.getMsg());

            String formattedDateTime = item.getCdate() + " " + item.getCtime();
            dateTime.setText(formattedDateTime);
        }
    }

    @NonNull
    @Override
    public DirectorMsgAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DirectorMsgAdapter.ViewHolder holder, int position) {
        DirectorMessageItem item = messageList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
