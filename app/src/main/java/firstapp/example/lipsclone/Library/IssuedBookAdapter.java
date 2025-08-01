package firstapp.example.lipsclone.Library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import firstapp.example.lipsclone.R;
import firstapp.example.lipsclone.api.Models.Library.IssuedBook;

public class IssuedBookAdapter extends RecyclerView.Adapter<IssuedBookAdapter.ViewHolder> {

    private final Context context;
    private final List<IssuedBook> bookList;

    public IssuedBookAdapter(Context context, List<IssuedBook> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, author, issueDate;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.bookTitle);
            author = view.findViewById(R.id.author);
            issueDate = view.findViewById(R.id.issueDates);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_issued_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        IssuedBook book = bookList.get(position);
        holder.title.setText(book.bookname);
        holder.author.setText(book.author);
        holder.issueDate.setText("Issued: " + book.issuefrom + " to " + book.issueupto);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}
