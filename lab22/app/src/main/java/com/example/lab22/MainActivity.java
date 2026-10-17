package com.example.lab22;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
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

  List<Note> notes = new ArrayList<>();
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

    for (int i = 0; i < 27; i++) {
      loadData();
    }
    for (int i = 0; i < notes.size(); i++) {
      notes.get(i).setCreatedAt(Instant.now());
    }
    NoteAdapter adapter = new NoteAdapter(notes);
    RecyclerView recyclerView = findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(adapter);
  }


  private void loadData(){
    notes.add(new Note("Почистить зубы", 1, "Утро"));
    notes.add(new Note("Сделать уроки", 2, "Обед"));
    notes.add(new Note("Постирать вещи", 3, "Вечер"));
  }
}