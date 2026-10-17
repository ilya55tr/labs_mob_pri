package com.example.lab22.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab22.R;
import com.example.lab22.holder.NoteViewHolder;
import com.example.lab22.model.Note;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

  private final List<Note> notes;
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

  public NoteAdapter(List<Note> notes) {
    this.notes = notes;
  }

  @NonNull
  @Override
  public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_note, parent, false);
    return new NoteViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
    Note note = notes.get(position);
    String dateStr = note.getCreatedAt().atZone(ZoneId.systemDefault()).format(formatter);
    holder.title.setText(note.getTitle());
    holder.content.setText(note.getContent());
    holder.createAt.setText(dateStr);
  }

  @Override
  public int getItemCount() {
    return notes.size();
  }
}
