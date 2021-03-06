package com.example.mygallery.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mygallery.Adapters.GridAdapter;
import com.example.mygallery.ClickListeners.MyOnClickListener;
import com.example.mygallery.DataHandling.DataDownload;
import com.example.mygallery.DataHandling.DataModel;
import com.example.mygallery.R;

import java.util.ArrayList;

public class GridActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private GridAdapter gridAdapter;
    private ArrayList<DataModel> data;
    public static View.OnClickListener myOnClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        //get downloaded data
        data = DataDownload.getMyData();

        recyclerView = findViewById(R.id.grid_recycler_view);
        myOnClickListener = new MyOnClickListener(this, recyclerView);
        //ensure that each added item is of same size (true - size of each item is fixed it won;t be
        // checked each time after insertion)
        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this, calculateNoOfColumns(this));
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        gridAdapter = new GridAdapter(this, data);
        recyclerView.setAdapter(gridAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate the menu - adding items to actions bar
        getMenuInflater().inflate(R.menu.grid_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_grid_view:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_list_view:
                Intent listView = new Intent(this, ListActivity.class);
                startActivity(listView);
                return true;
            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        //return nr of columns (180 to make each cell to be square (180 = height od grid item)
        return (int) (dpWidth / 180);
    }

    @Override
    protected void onStart() {
        super.onStart();
        data = DataDownload.getMyData();
    }


}