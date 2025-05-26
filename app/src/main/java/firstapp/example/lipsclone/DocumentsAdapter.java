package firstapp.example.lipsclone;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import android.widget.ArrayAdapter;


import firstapp.example.lipsclone.api.Models.DocumentModel;

public class DocumentsAdapter extends ArrayAdapter<DocumentModel> {

    private Context context;
    private List<DocumentModel> documents;

    public DocumentsAdapter(Context context, List<DocumentModel> documents) {
        super(context, 0, documents);
        this.context = context;
        this.documents = documents;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.item_document, parent, false);

        TextView title = convertView.findViewById(R.id.documentTitle);
        ImageButton openButton = convertView.findViewById(R.id.showDocumentButton);

        DocumentModel doc = documents.get(position);

        title.setText(doc.getDocname());

        openButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(doc.getDocurl()), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            context.startActivity(intent);
        });

        return convertView;
    }
}

