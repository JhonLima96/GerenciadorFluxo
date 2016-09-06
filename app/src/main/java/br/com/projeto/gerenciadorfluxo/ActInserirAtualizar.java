package br.com.projeto.gerenciadorfluxo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.plus.model.people.Person;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import br.com.projeto.gerenciadorfluxo.model.RegistroEntrada;

/**
 * Created by Jonathan on 05/09/2016.
 */
public class ActInserirAtualizar extends AppCompatActivity implements View.OnClickListener{
    Button btnOK,btnCancelar;
    RegistroEntrada registroEntrada;
    int position;
    EditText edtRA, edtNomeAluno,edtDataEntrada,edtHoraEntrada,edtTipoEntrada, edtHoraSaida;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_inserir_atualizar);

        position = getIntent().getIntExtra("Position", -1);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.cdlayout);

        edtRA = (EditText) findViewById(R.id.edtRA);
        edtNomeAluno = (EditText) findViewById(R.id.edtNomeAluno);
        edtDataEntrada = (EditText) findViewById(R.id.edtDataEntrada);
        edtHoraEntrada = (EditText) findViewById(R.id.edtHoraEntrada);
        edtTipoEntrada = (EditText) findViewById(R.id.edtTipoEntrada);
        edtHoraSaida = (EditText) findViewById(R.id.edtHoraSaida);

        btnOK = (Button) findViewById(R.id.btnOK);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);

        if(position != -1){
            getSupportActionBar().setTitle("Editar Entrada");
            procurarEntrada(position);
            registroEntrada = new RegistroEntrada();
        }else{
            getSupportActionBar().setTitle("Adicionar Entrada");
            registroEntrada = null;
        }

    }

    private void procurarEntrada(int position) {
        String clickedKey = ActHistorico.getInstance().getKeysArray().get(position);
        ActHistorico.getInstance().getmDatabase().child(clickedKey).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        RegistroEntrada registroEntrada  = dataSnapshot.getValue(RegistroEntrada.class);
                        edtRA.setText(Long.toString(registroEntrada.getRa()));
                        edtNomeAluno.setText(registroEntrada.getNome_aluno());
                        edtDataEntrada.setText(registroEntrada.getData_entrada());
                        edtHoraEntrada.setText(registroEntrada.getHora_entrada());
                        edtTipoEntrada.setText(registroEntrada.getTipo_entrada());
                        edtHoraSaida.setText(registroEntrada.getHora_saida());

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }


    @Override
    public void onClick(View view) {
        if (view == btnOK){
            if (edtRA.getText().toString().trim().equals("") || edtNomeAluno.getText().toString().trim().equals("") ||
                    edtHoraEntrada.getText().toString().trim().equals("") || edtDataEntrada.getText().toString().trim().equals("") ||
                    edtTipoEntrada.getText().toString().trim().equals("") || edtHoraSaida.getText().toString().trim().equals("")){
                final Snackbar snackBar = Snackbar.make(coordinatorLayout, "Todos os campos são necessários.", Snackbar.LENGTH_LONG);
                snackBar.setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackBar.dismiss();
                    }
                });
                snackBar.show();
            }
            else {
                RegistroEntrada re = new RegistroEntrada();
                re.setRa(Long.parseLong(edtRA.getText().toString()));
                re.setNome_aluno(edtNomeAluno.getText().toString());
                re.setData_entrada(edtDataEntrada.getText().toString());
                re.setHora_entrada(edtHoraEntrada.getText().toString());
                re.setTipo_entrada(edtTipoEntrada.getText().toString());
                re.setHora_saida(edtHoraSaida.getText().toString());
                if (registroEntrada == null)
                    ActHistorico.getInstance().addPerson(re);
                else
                    ActHistorico.getInstance().updatePersonDetails(re, position);
                finish();
            }

        }else if(view == btnCancelar){
            finish();
        }
    }
}
