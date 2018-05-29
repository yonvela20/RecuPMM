package com.example.yon_pc.recupmm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentDetalles.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class FragmentDetalles extends Fragment {

    Integer[] id;
    Button aceptar, cancelar, comprar;
    Activity activity;
    RelativeLayout layout;
    TextView origen, destino, precio;
    TextView clase, extra;
    DataBaseHelper DbHelper;

    private OnFragmentInteractionListener mListener;

    public FragmentDetalles() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_detalles, container, false);

        aceptar = (Button) view.findViewById(R.id.buttonConfirmar);
        cancelar = (Button) view.findViewById(R.id.buttonCancelar);
        comprar = (Button) view.findViewById(R.id.buttonCompra);
        layout = (RelativeLayout) view.findViewById(R.id.layout_fragment_detalles);

        origen = (TextView) view.findViewById(R.id.viajeOrigen);
        destino = (TextView) view.findViewById(R.id.viajeDestino);
        precio = (TextView) view.findViewById(R.id.viajePrecio);

        clase = (TextView) view.findViewById(R.id.resultadoClase);
        extra = (TextView) view.findViewById(R.id.resultadoExtra);

        final EditText usuario = (EditText) view.findViewById(R.id.usuarioCorrecto);

        final Bundle mibundle = this.getArguments();
        final Viajes viajes = (Viajes) mibundle.getSerializable("informacion");

        origen.setText("Origen: " + viajes.getOrigen());
        destino.setText("Destino: " + viajes.getDestino());
        precio.setText("Precio: " + viajes.getPrecio() + " €");

        extra.setText("Extras: ");

        if (this.getArguments().getBoolean("boolean1") == true) {
            extra.setText(extra.getText() + this.getArguments().getString("preferente"));
        }
        if (this.getArguments().getBoolean("boolean2") == true) {
            extra.setText(extra.getText() + "  " + this.getArguments().getString("souvenir"));
        }
        if (this.getArguments().getBoolean("boolean3") == true) {
            extra.setText(extra.getText() + "  " + this.getArguments().getString("menu"));
        }

        clase.setText(this.getArguments().getString("grupo"));

        //boton aceptar
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activity = getActivity();
                Toast.makeText(activity, "Gracias por su compra", Toast.LENGTH_LONG).show();


                layout.setVisibility(View.INVISIBLE);

                DbHelper = new DataBaseHelper(getActivity().getApplicationContext(), "Usuarios", null, 1);

                SQLiteDatabase bd = DbHelper.getWritableDatabase();

                Cursor cursor = bd.rawQuery("SELECT id FROM Usuarios where usuario= '" + mibundle.getString("usuario") + "';", null);

                id = new Integer[cursor.getCount()];

                //empezamos a recorrer desde el principio
                if (cursor.moveToFirst()) {
                    do {
                        String ids = cursor.getString(0);
                        id[0] = Integer.parseInt(ids);
                    } while (cursor.moveToNext());
                    try {
                        bd.execSQL("INSERT INTO Billetes (usuarios,Origen,Destino,Precio,Clase, Extras) VALUES" +
                                " ('" + id[0] + "','" + viajes.getOrigen() + "','" + viajes.getDestino() + "','" + viajes.getPrecio() + "','" + clase.getText() + "','" + extra.getText() + "')");

                        Toast.makeText(getActivity().getApplicationContext(), "REGISTRO COMPLETADO", Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        e.getMessage();
                    }
                }

                Intent factura = new Intent(FragmentDetalles.this.getActivity(), Factura.class);
                startActivity(factura);
            }
        });

        //boton cancelar
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setVisibility(View.INVISIBLE);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
