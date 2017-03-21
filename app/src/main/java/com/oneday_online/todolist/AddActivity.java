package com.oneday_online.todolist;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText taskname;
    private Button add;
    private Spinner imp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        taskname = (EditText)findViewById(R.id.taskname);
        imp = (Spinner)findViewById(R.id.imp);
        add = (Button)findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (taskname.getText().toString().equals("")){
                    AlertDialog alertDialog = new AlertDialog.Builder(AddActivity.this).create();
                    alertDialog.setTitle("Attention");
                    alertDialog.setMessage("Veuillez insérer un nom de tâche");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }else{

                switch (imp.getSelectedItemPosition()){
                    case 0:
                        TodoItem i = new TodoItem(TodoItem.Tags.Faible,taskname.getText().toString());
                        TodoDbHelper.addItem(i,AddActivity.this);
                        break;
                    case 1:
                        TodoItem i2 = new TodoItem(TodoItem.Tags.Normal,taskname.getText().toString());
                        TodoDbHelper.addItem(i2,AddActivity.this);
                        break;
                    case 2:
                        TodoItem i3 = new TodoItem(TodoItem.Tags.Important,taskname.getText().toString());
                        TodoDbHelper.addItem(i3,AddActivity.this);
                        break;
                }
                    Context context = getApplicationContext();
                    CharSequence text = "Ajout réussi !";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                finish();

            }}
        });
    }
}
