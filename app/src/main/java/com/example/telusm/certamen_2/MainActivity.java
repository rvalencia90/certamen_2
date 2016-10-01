package com.example.telusm.certamen_2;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.telusm.certamen_2.connection.HttpServerConnection;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Button buscar;
    private EditText user;
    private EditText estado;
    public final static String EXTRA_NOMBRE = "Direccion";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (EditText) findViewById(R.id.editTextUser);
        buscar = (Button) findViewById(R.id.button);
        estado = (EditText) findViewById(R.id.editText2);
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                String usuario = user.getText().toString();
                final String dir;
                //dir = "https://api.github.com/users/"+usuario+"/repos";
                //dir = "http://www.mocky.io/v2/57eee5352600000d25111203"; //dir mala
                dir = "http://www.mocky.io/v2/57eee3822600009324111202";

                AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

                    @Override
                    protected void onPreExecute() {

                    }

                    @Override
                    protected String doInBackground(Void... params) {
                        return new HttpServerConnection().connectToServer(dir, 15000);
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        if (result != null) {
                            //estado.setText(result);
                            try {
                                JSONObject obj = new JSONObject(result);
                                if (obj.getString("message").equals("User is not found")) {
                                    estado.setText("usuario no esta");
                                    Intent intent = new Intent(v.getContext(), Error.class);
                                    startActivityForResult(intent, 0);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                try {
                                    JSONArray arr = new JSONArray(result);
                                    estado.setText("usuario si esta =)");
                                    Intent intent = new Intent(v.getContext(), Lista_usuario.class);
                                    intent.putExtra(EXTRA_NOMBRE,dir);
                                    startActivityForResult(intent, 0);
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }

                            }
                        }


                    }
                };
                task.execute();
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
