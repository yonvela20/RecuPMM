package com.example.yon_pc.recupmmv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Yon-PC on 30/05/2018.
 */

public class ViajesArrayAdapter extends ArrayAdapter<Viajes> {
    Context context;
    Viajes[] viajes;

    private TextView tvOrigen, tvDestino, tvPrecio, tvId;

    public ViajesArrayAdapter(Context context, Viajes[] viajes) {
        super(context, R.layout.spinner_helper, viajes);
        this.context = context;
        this.viajes= viajes ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null){
            view = inflater.inflate(R.layout.spinner_helper,null);
        }
        tvPrecio = view.findViewById(R.id.precio);
        tvOrigen =  view.findViewById(R.id.origen);
        tvDestino =  view.findViewById(R.id.destino);

        String cast = Float.toString(viajes[position].getPrecio());

        String cast2 = Integer.toString(viajes[position].getId());

        tvPrecio.setText(cast);
        tvOrigen.setText(viajes[position].getOrigen());
        tvDestino.setText(viajes[position].getDestino());

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position,convertView,parent);
    }
}

