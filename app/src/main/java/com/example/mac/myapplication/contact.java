package com.example.mac.myapplication;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class contact extends AppCompatActivity {


    private static final int DIALOG_ALERT =10 ;
    private String TAG = contact.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;
    private String num,mail;

    private static String url = "http://shemax.esy.es/Progmobile/contact.json";
    ArrayList<HashMap<String, String>> contactList;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        contactList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.listView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Log.i("Test clic",contactList.get(i).values().toString());
               // Log.i("Test clic",contactList.get(i).get("mobile").toString());
                num=contactList.get(i).get("mobile").toString();
                mail=contactList.get(i).get("email").toString();
                showDialog(DIALOG_ALERT);



            }
        });
        new GetContacts().execute();

    }


    //Notif dans la barre de notification pour retour à l'appli

    public void notif(){

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle("ProgMobile")
                        .setContentText("retourner sur l'application");

        Intent resultIntent = new Intent(this, MainActivity.class);


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(contact.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int mId=1;
        mNotificationManager.notify(mId, mBuilder.build());
    }



//Fin notification barre de notif



    //actionBar //////

    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_contact à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_contact, menu);

        return true;
    }

    private void retour(){

       System.exit(0);
    }

    private void navigateur(){
        Intent intent= new Intent(contact.this,GoogleSearch.class);
        startActivity(intent);
     }



    //gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_retour:
               retour();
                return true;
            case R.id.action_navigateur:
                navigateur();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    //fin actionBar//


    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(contact.this);
            pDialog.setMessage("telechargement");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        //traite le fichier JSOn
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();


            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("contacts");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("id");
                        String name = c.getString("name");
                        String email = c.getString("email");
                        String address = c.getString("address");
                        String gender = c.getString("gender");

                        // Phone node is JSON Object
                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("name", name);
                        contact.put("email", email);
                        contact.put("mobile", mobile);

                        // adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Couldn't get json from server. Check LogCat for possible errors!",Toast.LENGTH_LONG).show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


            if (pDialog.isShowing()) pDialog.dismiss();

            ListAdapter adapter = new SimpleAdapter(contact.this, contactList, R.layout.list_item, new String[]{"name", "email","mobile"}, new int[]{R.id.name, R.id.email, R.id.mobile});
            lv.setAdapter(adapter);
            Toast sucess =Toast.makeText(contact.this,"téléchargement terminé",Toast.LENGTH_SHORT);
            sucess.show();
        }
    }

    //Dialogue boite
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_ALERT:
                // Create out AlterDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Que voulez vous faire ?");
                builder.setCancelable(true);
                builder.setPositiveButton("sms", new smsOnClickListener());
                builder.setNeutralButton("mail",new mailOnClickListener());
                builder.setNegativeButton("appel", new appelOnClickListener());
                AlertDialog dialog = builder.create();
                dialog.show();
        }
        return super.onCreateDialog(id);
    }


    private final class mailOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {

            String emailUrl = "mailto:"+mail+"?subject=Prise de contact&body=Bonjour,";
            Intent request = new Intent(Intent.ACTION_VIEW);
            request.setData(Uri.parse(emailUrl));
            notif();
            startActivity(request);
        }
    }


    private final class appelOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            Intent appel = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+num));
            notif();
            startActivity(appel);
        }
    }

    private final class smsOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            Intent sms = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+num));
            sms.putExtra("sms_body", "Bonjour, je vous contact grâce à LA liste");
            notif();
            startActivity(sms);

        }
    }
// fin dialogue boite



}
