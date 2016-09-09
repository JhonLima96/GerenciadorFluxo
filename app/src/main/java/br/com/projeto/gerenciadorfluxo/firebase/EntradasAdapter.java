package br.com.projeto.gerenciadorfluxo.firebase;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import br.com.projeto.gerenciadorfluxo.ActHistorico;
import br.com.projeto.gerenciadorfluxo.ActInserirAtualizar;
import br.com.projeto.gerenciadorfluxo.R;
import br.com.projeto.gerenciadorfluxo.model.RegistroEntrada;

/**
 * Created by Jonathan on 02/09/2016.
 */
public class EntradasAdapter extends BaseAdapter {
    private DatabaseReference databaseReference;
    private Boolean saved=null;
    private LayoutInflater inflater;
    private ArrayList<RegistroEntrada> arrayRegistroEntradas;
    private Context context;


    public EntradasAdapter(Context context, ArrayList<RegistroEntrada> arrayRegistroEntradas) {
        this.context = context;
        this.arrayRegistroEntradas = arrayRegistroEntradas;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayRegistroEntradas.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayRegistroEntradas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        Holder holder;
        if (v == null) {
            v = inflater.inflate(R.layout.list_entrada, null);
            holder = new Holder();
            holder.NomeAluno = (TextView) v.findViewById(R.id.txtNome);
            holder.RA_Aluno = (TextView) v.findViewById(R.id.txtRA);
            holder.EditPerson = (ImageView) v.findViewById(R.id.EditPerson);
            holder.DeletePerson = (ImageView) v.findViewById(R.id.DeletePerson);
            v.setTag(holder);
        } else {
            holder = (Holder) v.getTag();
        }

//        try{
            //        Log.d("LOG_EVENTO", arrayRegistroEntradas.get(0).getNome_aluno());
            holder.NomeAluno.setText(arrayRegistroEntradas.get(position).getNome_aluno());
            holder.RA_Aluno.setText(String.valueOf(arrayRegistroEntradas.get(position).getRa()));
            holder.EditPerson.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,ActInserirAtualizar.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("Position", position);
                    context.getApplicationContext().startActivity(intent);
                }
            });
            holder.DeletePerson.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowConfirmDialog(context, position);
                }
            });

//        }catch (Exception e){
//            Toast.makeText(context, "Deu erro! arrume!", Toast.LENGTH_SHORT);
//        }
        return v;
    }

    class Holder {
        TextView NomeAluno,RA_Aluno;
        ImageView DeletePerson, EditPerson;
    }

    public static void ShowConfirmDialog(Context context, final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder
                .setMessage("Are you sure you want to delete this entry?")
                .setCancelable(true)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ActHistorico.getInstance().deletePerson(position);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    //Insert Values
    public Boolean salvar(RegistroEntrada leitura){
        if(arrayRegistroEntradas ==null){
            saved=false;
        }else{
            try {
                databaseReference.child("Entradas").push().setValue(arrayRegistroEntradas);
                saved=true;
            }catch (DatabaseException e){
                e.printStackTrace();
                saved=false;
            }
        }
        return saved;
    }

    //Read Values
    public ArrayList<RegistroEntrada> lerValores(){
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return arrayRegistroEntradas;
    }

    private void fetchData(DataSnapshot dataSnapshot){
        arrayRegistroEntradas.clear();

        //for (DataSnapshot ds: dataSnapshot.getChildren()){
           try {
               arrayRegistroEntradas.clear();
              RegistroEntrada registroEntrada = dataSnapshot.getValue(RegistroEntrada.class);
               arrayRegistroEntradas.add(registroEntrada);
              Log.d("Child Event,Verse", "onChildChanged: " + registroEntrada.getNome_aluno() +
                        registroEntrada.getData_entrada() + registroEntrada.getHora_entrada() + arrayRegistroEntradas.get(0).getNome_aluno() );
            }catch (DatabaseException e){
               e.printStackTrace();
            }
        //}
    }
}



