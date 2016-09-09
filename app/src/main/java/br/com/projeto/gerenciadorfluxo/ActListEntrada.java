package br.com.projeto.gerenciadorfluxo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jonathan on 08/09/2016.
 */
public class ActListEntrada extends AppCompatActivity {
    private TextView txtNomeAluno,txtRaAluno;
    private ImageView btnDeletePerson, btnEditPerson;

    public ActListEntrada() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_entrada);

        txtNomeAluno = (TextView) findViewById(R.id.txtNome);
        txtRaAluno = (TextView) findViewById(R.id.txtRA);
        btnDeletePerson = (ImageView) findViewById(R.id.DeletePerson);
        btnEditPerson = (ImageView) findViewById(R.id.EditPerson);

    }
}
