package com.example.sninkovic_ns.zad30c.activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.sninkovic_ns.zad30c.R;
import com.example.sninkovic_ns.zad30c.db.DatabaseHelper;
import com.example.sninkovic_ns.zad30c.db.model.Glumac;
import com.example.sninkovic_ns.zad30c.dialogs.AboutDialog;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    public static String ACTOR_KEY = "ACTOR_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }


        final ListView listView = (ListView) findViewById(R.id.glumci_list);

        try {
            List<Glumac> list = getDatabaseHelper().getmGlumacDaoDao().queryForAll();

            ListAdapter adapter = new ArrayAdapter<>(MainActivity.this, R.layout.list_item, list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Glumac p = (Glumac) listView.getItemAtPosition(position);

                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra(ACTOR_KEY, p.getmId());
                    startActivity(intent);
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){

            case R.id.priprema_add_new_actor:
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.priprema_add_new_actor);

                Button add = (Button) dialog.findViewById(R.id.add_actor);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                               EditText name=(EditText) dialog.findViewById(R.id.actor_name);
                               EditText bio= (EditText) dialog.findViewById(R.id.actor_biography);
                               RatingBar rating= (RatingBar) dialog.findViewById(R.id.actor_rating);
                               EditText birth= (EditText)dialog.findViewById(R.id.actor_birth);


                               Glumac g=new Glumac();
                               g.setmName(name.getText().toString());
                               g.setmBiography(bio.getText().toString());
                               g.setmBirth(birth.getText().toString());
                               g.setmScore(rating.getRating());


                               try {

                                        Toast.makeText(getBaseContext(), "Pre helpera", Toast.LENGTH_SHORT).show();

                                   getDatabaseHelper().getmGlumacDaoDao().create(g);

                                        Toast.makeText(getBaseContext(), "Posle helpera", Toast.LENGTH_SHORT).show();
                                   refresh();

                                        Toast.makeText(getBaseContext(), "Posle refresha", Toast.LENGTH_SHORT).show();



                               } catch (SQLException e) {
                                   e.printStackTrace();
                               }

                           }


                });

                Button cancel = (Button) dialog.findViewById(R.id.add_actor_cancel);
                cancel.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.show();
                break;

            case(R.id.priprema_about):

                AlertDialog alertDialog = new AboutDialog(this).prepareDialog();
                            alertDialog.show();




        }

        refresh();


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        refresh();
    }

    private void refresh() {
        ListView listview = (ListView) findViewById(R.id.glumci_list);

        if (listview != null){
            ArrayAdapter<Glumac> adapter = (ArrayAdapter<Glumac>) listview.getAdapter();

            if(adapter!= null)
            {
                try {
                    adapter.clear();

                    List<Glumac> list = getDatabaseHelper().getmGlumacDaoDao().queryForAll();

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
