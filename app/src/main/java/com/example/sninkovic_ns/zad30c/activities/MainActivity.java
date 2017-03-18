package com.example.sninkovic_ns.zad30c.activities;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
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
import com.example.sninkovic_ns.zad30c.preferences.Preferences;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SharedPreferences prefs;

    public static String ACTOR_KEY = "ACTOR_KEY";
    public static String NOTIF_TOAST = "notif_toast";
    public static String NOTIF_STATUS = "notif_statis";
    public static String NOTIF_CHE= "notif_che";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }

        prefs= PreferenceManager.getDefaultSharedPreferences(this);

        final ListView listView = (ListView) findViewById(R.id.glumci_list);

        try {
            List<Glumac> list = getDatabaseHelper().getmGlumacDao().queryForAll();

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
                                   getDatabaseHelper().getmGlumacDao().create(g);

                                   boolean toast=prefs.getBoolean(NOTIF_TOAST, false);
                                   boolean status=prefs.getBoolean(NOTIF_STATUS, false);

                                   if(toast){
                                       Toast.makeText(MainActivity.this, "Dodat novi glumac", Toast.LENGTH_SHORT).show();
                                   }

                                   if(status){
                                       showStatusMesage("Dodat novi glumac");
                                   }

                                        refresh();

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
                    break;

            case(R.id.priprema_preferences):
                Intent intent=new Intent(MainActivity.this, Preferences.class);
                startActivity(intent);
                    break;
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

                    List<Glumac> list = getDatabaseHelper().getmGlumacDao().queryForAll();

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

    private void showStatusMesage(String message){
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        mBuilder.setContentTitle("Pripremni test");
        mBuilder.setContentText(message);

        Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.ic_action_create);

        mBuilder.setLargeIcon(bmp);
        mNotificationManager.notify(1,mBuilder.build());
    }
}
