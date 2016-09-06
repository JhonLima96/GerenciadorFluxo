package br.com.projeto.gerenciadorfluxo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import br.com.projeto.gerenciadorfluxo.model.RegistroEntrada;

public class ActEntradaDetalhes extends AppCompatActivity {

    private TextView txtRA, txtNomeAluno, txtDataEntrada, txtHoraEntrada, txtTipoEntrada, txtHoraSaida;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_entrada_detalhes);

        txtRA = (TextView) findViewById(R.id.txtRA);
        txtNomeAluno = (TextView) findViewById(R.id.txtNomeAluno);
        txtDataEntrada = (TextView) findViewById(R.id.txtDataEntrada);
        txtHoraEntrada = (TextView) findViewById(R.id.txtHoraEntrada);
        txtTipoEntrada = (TextView) findViewById(R.id.txtTipoEntrada);
        txtHoraSaida = (TextView) findViewById(R.id.txtHoraSaida);

        int position = getIntent().getIntExtra("Position", -1);
        searchPerson(position);

    }

    public void searchPerson(int position) {
        String clickedKey = ActHistorico.getInstance().getKeysArray().get(position);
        ActHistorico.getInstance().getmDatabase().child(clickedKey).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        RegistroEntrada registroEntradaModel = dataSnapshot.getValue(RegistroEntrada.class);
                        txtRA.setText(getString(R.string.lbl_RA,String.valueOf(registroEntradaModel.getRa())));
                        txtNomeAluno.setText(getString(R.string.lbl_Nome_Aluno,registroEntradaModel.getNome_aluno()));
                        txtDataEntrada.setText(getString(R.string.lbl_Data_Entrada,registroEntradaModel.getData_entrada()));
                        txtHoraEntrada.setText(getString(R.string.lbl_Hora_Entrada,registroEntradaModel.getHora_entrada()));
                        txtTipoEntrada.setText(getString(R.string.lbl_Tipo_Entrada,registroEntradaModel.getTipo_entrada()));
                        txtHoraSaida.setText(getString(R.string.lbl_Hora_Saida,registroEntradaModel.getHora_saida()));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }
}
