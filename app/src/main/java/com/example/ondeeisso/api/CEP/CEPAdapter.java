package com.example.ondeeisso.api.CEP;
import com.example.ondeeisso.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.ondeeisso.R;

import java.util.List;

public class CEPAdapter extends ArrayAdapter<CEP> {
    private int layout;

    public CEPAdapter(@NonNull Context context, int resource, @NonNull List<CEP> ceps) {
        super(context, resource, ceps);
        this.layout = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {   if (convertView == null){
        LayoutInflater inflater = (LayoutInflater)
                getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(this.layout,parent,false);
    }
        TextView rua= (TextView) convertView.findViewById(R.id.tvLog);
        TextView bairro= (TextView)convertView.findViewById(R.id.tvBairro);
        TextView uf= (TextView)convertView.findViewById(R.id.tvUf);
        TextView txcep= (TextView)convertView.findViewById(R.id.tvCep);


        CEP cep = (CEP) this.getItem(position);
        rua.setText(""+cep.getLogradouro());
        bairro.setText("" + cep.getBairro());
        uf.setText("" + uf.getText());
        txcep.setText("" + cep.getCep());

        convertView.setOnClickListener(e -> {
            System.out.println("aaaaaaaaaa");
            System.out.println(cep.getLogradouro());
        });

        return convertView;
    }

}
