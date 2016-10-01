package com.example.telusm.certamen_2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.telusm.certamen_2.connection.HttpServerConnection;
import com.example.telusm.certamen_2.objects.DataObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Lista_usuario extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuario);

        //Obteniendo la instancia del Intent
        Intent intent = getIntent();
        //Extrayendo el extra de tipo cadena
        final String dir = intent.getStringExtra(MainActivity.EXTRA_NOMBRE);
        System.out.println(dir);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute(){

            }

            @Override
            protected String doInBackground(Void... params) {
                return new HttpServerConnection().connectToServer(dir, 15000);
            }

            @Override
            protected void onPostExecute(String result) {
                if(result != null){
                    // specify an adapter (see also next example)
                    mAdapter = new UIAdapter(getLista(result));
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        };

        task.execute();
    }
    private List<DataObject> getLista(String result){
        List<DataObject> listaData = new ArrayList<DataObject>();
        try {
            JSONArray lista = new JSONArray(result);

            int size = lista.length();
            for(int i = 0; i < size; i++){
                DataObject data = new DataObject();
                JSONObject objeto = lista.getJSONObject(i);

                data.setNombre(objeto.getString("name"));
                data.setDescripcion(objeto.getString("description"));
                data.setUpdate(objeto.getString("updated_at"));

                listaData.add(data);
            }
            return listaData;
        } catch (JSONException e) {
            e.printStackTrace();
            return listaData;
        }
    }


}
