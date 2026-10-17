package com.example.lab22.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab22.R;
import com.example.lab22.model.Note;

public class NoteViewHolder extends RecyclerView.ViewHolder {

  public TextView title;
  public TextView content;
  public TextView createAt;
  public NoteViewHolder(@NonNull View itemView) {
    super(itemView);
    title = itemView.findViewById(R.id.titleNote);
    content = itemView.findViewById(R.id.contentNote);
    createAt = itemView.findViewById(R.id.createAtNote);
  }
}
