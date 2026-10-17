package com.example.lab22;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab22.adapter.NoteAdapter;
import com.example.lab22.model.Note;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Note> notes = new ArrayList<>();
    private int editedPosition = -1;
    private NoteAdapter adapter;
    private Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        for (int i = 0; i < 2; i++) {
            loadData();
        }
        for (int i = 0; i < notes.size(); i++) {
            notes.get(i).setCreatedAt(Instant.now());
        }
        adapter = new NoteAdapter(notes, (this::openNoteActivity));
        buttonAdd = findViewById(R.id.addNote);
        buttonAdd.setOnClickListener(v -> {
            openNoteActivity(new Note(0, "", ""), -1);
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void openNoteActivity(Note note, int position) {
        editedPosition = position;
        Intent intent = new Intent(this, NoteActivity.class);

        intent.putExtra("NOTE_ID", note.getId());
        intent.putExtra("NOTE_TITLE", note.getTitle());
        intent.putExtra("NOTE_CONTENT", note.getContent());
        startActivityForResult(intent, 101);
    }


    private void loadData() {
        notes.add(new Note(1, "Почистить зубы", "Утро"));
        notes.add(new Note(2, "Сделать уроки", "Обед"));
        notes.add(new Note(3, "Постирать вещи", "Вечер"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            String title = data.getStringExtra("UPDATED_TITLE");
            String content = data.getStringExtra("UPDATED_CONTENT");
            if (editedPosition == -1) {
                int newId = notes.size() + 1;
                Note newNote = new Note(newId, title, content, Instant.now());
                notes.add(newNote);
                adapter.notifyItemInserted(notes.size() - 1);
            } else {
                Note note = notes.get(editedPosition);
                note.setTitle(title);
                note.setContent(content);
                adapter.notifyItemChanged(editedPosition);
            }
        } else if (requestCode == 101 && resultCode == 100 && data == null) {
            if (editedPosition > -1) {
                notes.remove(editedPosition);
                adapter.notifyItemRemoved(editedPosition);
            }
        }
    }
}