package com.example.lab22;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NoteActivity extends AppCompatActivity {

    EditText editTitle;
    EditText editContent;
    Button buttonSave;
    Button buttonDelete;
    Button buttonCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_note);
        Intent intent = getIntent();
        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContent);
        buttonSave = findViewById(R.id.save);
        buttonDelete = findViewById(R.id.delete);
        buttonCancel = findViewById(R.id.cancel);
        String title = intent.getStringExtra("NOTE_TITLE");
        String content = intent.getStringExtra("NOTE_CONTENT");
        editTitle.setText(title);
        editContent.setText(content);
        buttonSave.setOnClickListener(v -> {
            saveAndReturn();
        });
        buttonDelete.setOnClickListener(v -> showDeleteDialog());
        buttonCancel.setOnClickListener(v ->{
            cancelAndReturn();
        });
    }

    private void cancelAndReturn() {
        setResult(RESULT_CANCELED);
        finish();
    }
    private void deleteAndReturn() {
        setResult(100);
        finish();
    }

    private void saveAndReturn() {
        String title = editTitle.getText().toString().trim();
        String content = editContent.getText().toString().trim();
        if (!title.isEmpty()){
            Intent resultIntent = new Intent();
            resultIntent.putExtra("UPDATED_TITLE", title);
            resultIntent.putExtra("UPDATED_CONTENT", content);

            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }

    private void showDeleteDialog(){
        new AlertDialog.Builder(this)
                .setTitle("Удаление")
                .setMessage("Удалить эту заметку?")
                .setPositiveButton("Удалить",
                        (dialogInterface, i) -> deleteAndReturn())
                .setNegativeButton("Отмена", null)
                .show();
    }
}