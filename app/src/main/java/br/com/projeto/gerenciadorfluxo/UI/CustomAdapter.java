package br.com.projeto.gerenciadorfluxo.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.projeto.gerenciadorfluxo.R;
import br.com.projeto.gerenciadorfluxo.model.RegistroEntrada;

/**
 * Created by Jonathan on 03/09/2016.
 */
public class CustomAdapter extends BaseAdapter {
    private ArrayList<RegistroEntrada> registroEntradas;
    private LayoutInflater mInflater;

    public CustomAdapter(Context c, ArrayList<RegistroEntrada> registroEntradas) {
        this.registroEntradas = registroEntradas;
        mInflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return registroEntradas.size();
    }

    @Override
    public Object getItem(int posicao) {
        return registroEntradas.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return posicao;
    }

    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {
        if(view==null){
            view= mInflater.inflate(R.layout.list_entrada, null);
        }
        final RegistroEntrada registro = (RegistroEntrada) this.getItem(posicao);

        TextView txtRA= (TextView) view.findViewById(R.id.txtRA);
        TextView txtNome= (TextView) view.findViewById(R.id.txtNome);
        TextView txtDataEntrada= (TextView) view.findViewById(R.id.txtDataEntrada);
        TextView txtHoraEntrada = (TextView) view.findViewById(R.id.txtHoraEntrada);
        TextView txtTipoEntrada = (TextView) view.findViewById(R.id.txtTipoEntrada);
        TextView txtHoraSaida = (TextView) view.findViewById(R.id.txtHoraSaida);

        return view;
    }

}
