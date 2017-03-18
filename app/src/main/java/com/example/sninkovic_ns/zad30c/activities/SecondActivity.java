package com.example.sninkovic_ns.zad30c.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.sninkovic_ns.zad30c.db.DatabaseHelper;
import com.example.sninkovic_ns.zad30c.db.model.Filmovi;
import com.example.sninkovic_ns.zad30c.db.model.Glumac;
import com.example.sninkovic_ns.zad30c.R;

import com.j256.ormlite.android.apptools.OpenHelperManager;



import java.sql.SQLException;
import java.util.List;

public class SecondActivity extends AppCompatActivity {


    private DatabaseHelper databaseHelper;
    private Glumac g;

    private EditText name;
    private EditText bio;
    private EditText birth;
    private RatingBar rating;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }


       // Toast.makeText(SecondActivity.this, "PRE GLUMCA", Toast.LENGTH_SHORT).show();



        int key = getIntent().getExtras().getInt(MainActivity.ACTOR_KEY);

        try {
            g = getDatabaseHelper().getmGlumacDao().queryForId(key);

            name=(EditText) findViewById(R.id.actor_name);
            bio=(EditText) findViewById(R.id.actor_biography);
            birth=(EditText) findViewById(R.id.actor_birth);
           //rating=(RatingBar) findViewById(R.id.actor_rating);

            name.setText(g.getmName());
            bio.setText(g.getmBiography());
            birth.setText(g.getmBirth());
           //rating.setRating(g.getmScore());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        final ListView listView= (ListView) findViewById(R.id.glumac_movies);

               try {

           List<Filmovi> list = getDatabaseHelper().getmFilmoviDao().queryBuilder()
                    .where()
                    .eq(Filmovi.FIELD_MOVIE_NAME, g.getmId())
                    .query();

                   ListAdapter adapter= new ArrayAdapter<>(this, R.layout.list_item, list);
                   listView.setAdapter(adapter);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void refresh(){
        ListView listview=(ListView) findViewById(R.id.glumac_movies);

        if (listview != null) {
            ArrayAdapter<Filmovi> adapter = (ArrayAdapter<Filmovi>) listview.getAdapter();

            if(adapter!= null){
                adapter.clear();
                List<Filmovi> list = null;
                try {
                    list = getDatabaseHelper().getmFilmoviDao().queryBuilder()
                            .where()
                            .eq(Filmovi.FIELD_NAME_GLUMAC, g.getmId())
                            .query();
                    adapter.addAll(list);

                    adapter.notifyDataSetChanged();
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            }

        }
    }



    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }
}
