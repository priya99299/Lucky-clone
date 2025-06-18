    package firstapp.example.lipsclone;

    import android.content.ActivityNotFoundException;
    import android.content.Context;
    import android.content.Intent;
    import android.net.Uri;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;


    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import java.util.List;

    import firstapp.example.lipsclone.api.Models.Notice_Reponse;

    public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {
        private List<Notice_Reponse.Notice> noticeList;
        private Context context;

        public NoticeAdapter(Context context, List<Notice_Reponse.Notice> noticeList) {
            this.context = context;
            this.noticeList = noticeList;
        }

        @NonNull
        @Override
        public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_notice_card, parent, false);
            return new NoticeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {
            Notice_Reponse.Notice notice = noticeList.get(position);
            holder.title.setText( notice.title);

            holder.icon.setOnClickListener(v -> {
                String url = notice.description; // Your document URL
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "No app found to open this link.", Toast.LENGTH_SHORT).show();
                }
            });



        }

        @Override
        public int getItemCount() {
            return noticeList.size();
        }

        public static class NoticeViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            ImageView icon;

            public NoticeViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.noticeTitle);
                icon = itemView.findViewById(R.id.iconImage);
            }
        }
    }
