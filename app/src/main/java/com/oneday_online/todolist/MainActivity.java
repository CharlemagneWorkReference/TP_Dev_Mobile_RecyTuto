package com.oneday_online.todolist;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<TodoItem> items;
    private RecyclerView recycler;
    private LinearLayoutManager manager;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(MainActivity.this,AddActivity.class));
            }
        });
        Log.i("INIT", "Fin initialisation composantes");

        // Test d'ajout d'un item
//        TodoItem item = new TodoItem(TodoItem.Tags.Important, "Réviser ses cours");
//        TodoDbHelper.addItem(item, getBaseContext());
//        item = new TodoItem(TodoItem.Tags.Normal, "Acheter du pain");
//        TodoDbHelper.addItem(item, getBaseContext());

        // On récupère les items
        items = TodoDbHelper.getItems(getBaseContext());
        Log.i("INIT", "Fin initialisation items");

        // On initialise le RecyclerView
        recycler = (RecyclerView) findViewById(R.id.recycler);
        manager = new LinearLayoutManager(this);
        recycler.setLayoutManager(manager);

        adapter = new RecyclerAdapter(items);
        recycler.setAdapter(adapter);

        setRecyclerViewItemTouchListener();
        Log.i("INIT", "Fin initialisation recycler");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_debug:
                startActivity(new Intent(MainActivity.this,AndroidDatabaseManager.class));
                return true;
            case R.id.action_clear:
                TodoDbHelper.emptyList(MainActivity.this);
                update();
                Toast.makeText(MainActivity.this, R.string.list_empty, Toast.LENGTH_LONG).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setRecyclerViewItemTouchListener() {
        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
                // Non géré dans cet exemple (ce sont les drags) -> on retourne false
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                TodoItem item = items.get(position);

                switch(swipeDir) {
                    case ItemTouchHelper.RIGHT:
                        item.setDone(true);
                        break;
                    case ItemTouchHelper.LEFT:
                        item.setDone(false);
                        break;
                }
                recycler.getAdapter().notifyItemChanged(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recycler);
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    /**
     * Met à jour la liste
     */
    private void update(){
        items.clear();
        items = TodoDbHelper.getItems(this);
        adapter = new RecyclerAdapter(items);
        recycler.setAdapter(adapter);
    }
}
