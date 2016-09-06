package br.com.projeto.gerenciadorfluxo;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.com.projeto.gerenciadorfluxo.UI.CustomAdapter;
import br.com.projeto.gerenciadorfluxo.firebase.EntradasAdapter;
import br.com.projeto.gerenciadorfluxo.model.RegistroEntrada;

/**
 * Created by Jonathan on 06/09/2016.
 */
public class ActHistorico extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    static final String TAG = "Fragment Histórico";
    private ListView lstResultadoPesquisa;
    private CustomAdapter adapter;
    private DatabaseReference mDatabase;
    private static ActHistorico actHistorico;
    private EntradasAdapter entradasAdapter;
    private Firebase firebase;
    private ProgressBar progressBar;
    private static ArrayList<RegistroEntrada> arrayRegistroEntrada = new ArrayList<>();
    //    private PersonDetailsAdapter personDetailsAdapter;
    private ArrayList<String> keysArray;

    public ActHistorico(){
        //
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_historico);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addChildEventListener(childEventListener);
        actHistorico = this;
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        lstResultadoPesquisa = (ListView) findViewById(R.id.lstResultadoPesquisa);
        keysArray = new ArrayList<>();


        lstResultadoPesquisa.setOnItemClickListener(this);


        //Adapter
        entradasAdapter = new EntradasAdapter(ActHistorico.this, arrayRegistroEntrada);
        lstResultadoPesquisa.setAdapter(entradasAdapter);

        new Wait().execute();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(ActHistorico.this, ActEntradaDetalhes.class);
        intent.putExtra("Position", position);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        arrayRegistroEntrada.clear();
        mDatabase.removeEventListener(childEventListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateView() {
        entradasAdapter.notifyDataSetChanged();
        lstResultadoPesquisa.invalidate();
        progressBar.setVisibility(View.GONE);
        lstResultadoPesquisa.setVisibility(View.VISIBLE);
    }

    public static ActHistorico getInstance() {
        return actHistorico;
    }

    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            lstResultadoPesquisa.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            Log.d(TAG, dataSnapshot.getKey() + ":" + dataSnapshot.getValue().toString());
            RegistroEntrada re = dataSnapshot.getValue(RegistroEntrada.class);

            long ra = dataSnapshot.child("ra").getValue(Long.class);
            String nome_aluno =  dataSnapshot.child("nome_aluno").getValue(String.class);
            String data_entrada = dataSnapshot.child("data_entrada").getValue(String.class);
            String hora_entrada = dataSnapshot.child("hora_entrada").getValue(String.class);
            String tipo_entrada = dataSnapshot.child("tipo_entrada").getValue(String.class);
            String hora_saida = dataSnapshot.child("hora_saida").getValue(String.class);

            RegistroEntrada registroEntrada = new RegistroEntrada();
            registroEntrada.setRa(ra);
            registroEntrada.setNome_aluno(nome_aluno);
            registroEntrada.setData_entrada(data_entrada);
            registroEntrada.setHora_entrada(hora_entrada);
            registroEntrada.setTipo_entrada(tipo_entrada);
            registroEntrada.setHora_saida(hora_saida);

            Log.d("LOG_FDP", nome_aluno);
            arrayRegistroEntrada.add(registroEntrada);
            keysArray.add(dataSnapshot.getKey());
            updateView();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            String changedKey = dataSnapshot.getKey();
            int changedIndex = keysArray.indexOf(changedKey);
            RegistroEntrada registroEntrada = dataSnapshot.getValue(RegistroEntrada.class);
            arrayRegistroEntrada.set(changedIndex,registroEntrada);
            Log.d("LOG_CHILD", arrayRegistroEntrada.get(0).getNome_aluno());
            updateView();
        }
        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String deletedKey = dataSnapshot.getKey();
            int removedIndex = keysArray.indexOf(deletedKey);
            keysArray.remove(removedIndex);
            arrayRegistroEntrada.remove(removedIndex);
            updateView();
        }
        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//            Nada por enquanto
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(ActHistorico.this,"Não foi possível atualizar.",Toast.LENGTH_SHORT).show();
            updateView();
        }
    };

    private class Wait extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            lstResultadoPesquisa.setVisibility(View.GONE);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException ie) {
                Log.d(TAG,ie.toString());
            }
            return(arrayRegistroEntrada.size()==0);
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            if(bool)
                updateView();
        }
    }

    public ArrayList<String> getKeysArray() {
        return keysArray;
    }

    public DatabaseReference getmDatabase() {
        return mDatabase;
    }

    public void addPerson(RegistroEntrada model) {
        RegistroEntrada re = new RegistroEntrada();
        re.setRa(model.getRa());
        re.setNome_aluno(model.getNome_aluno());
        re.setData_entrada(model.getData_entrada());
        re.setHora_entrada(model.getHora_entrada());
        re.setTipo_entrada(model.getTipo_entrada());
        re.setHora_saida(model.getHora_saida());

        String key = mDatabase.child("Entradas").push().getKey();
        Map<String, Object> postValues = re.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, postValues);
        mDatabase.updateChildren(childUpdates);
    }

    public void deletePerson(int position) {
        String clickedKey = keysArray.get(position);
        mDatabase.child(clickedKey).removeValue();
    }

    public void updatePersonDetails(final RegistroEntrada model, int position) {
        String clickedKey = keysArray.get(position);
        RegistroEntrada re = new RegistroEntrada();
        re.setRa(model.getRa());
        re.setNome_aluno(model.getNome_aluno());
        re.setData_entrada(model.getData_entrada());
        re.setHora_entrada(model.getHora_entrada());
        re.setTipo_entrada(model.getTipo_entrada());
        re.setHora_saida(model.getHora_saida());
        mDatabase.child(clickedKey).setValue(re);
    }
}
