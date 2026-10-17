package com.example.lab22;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.activity.SystemBarStyle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class NoteActivity extends AppCompatActivity {

    EditText editTitle;
    EditText editContent;
    Button buttonSave;
    Button buttonDelete;
    Button buttonCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this,
                SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
                SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT));
        setContentView(R.layout.activity_note);
        // Отступы под статус-бар, навигацию и клавиатуру (чтобы она не закрывала кнопки)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.ime());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
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
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.delete_dialog_title)
                .setMessage(R.string.delete_dialog_message)
                .setPositiveButton(R.string.delete,
                        (dialogInterface, i) -> deleteAndReturn())
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}