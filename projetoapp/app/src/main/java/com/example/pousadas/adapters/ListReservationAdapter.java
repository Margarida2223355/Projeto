package com.example.pousadas.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.pousadas.activities.RoomDetailActivity;
import com.example.pousadas.databinding.ItemRoomListBinding;
import com.example.pousadas.models.Reservation;

import java.util.ArrayList;

public class ListReservationAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Reservation> reservations;

    public ListReservationAdapter(Context context, ArrayList<Reservation> reservations) {
        this.context = context;
        this.reservations = reservations;
    }

    @Override
    public int getCount() {
        return reservations.size();
    }

    @Override
    public Object getItem(int position) {
        return reservations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return reservations.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ItemRoomListBinding binding;
        MyViewHolder myViewHolder;

        /* Se não existir inflater */
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        /* Se não existir convertView:
         *
         * Adapter criada inicialmente.
         * Definir binding para colocar informações do item
         * na ViewHolder.
         *
         */
        if (convertView == null) {
            binding = ItemRoomListBinding.inflate(inflater); //Definir binding através da ItemListBinding
            convertView = binding.getRoot(); //Definir convertView
            myViewHolder = new MyViewHolder(binding); //Instanciar ViewHolder

            //Definir tag da convertView - ViewHolder
            convertView.setTag(myViewHolder);
        }

        /* Caso já exista convertView:
         *
         * Ou seja, basicamente para atualizações
         * botões de quantidade.
         *
         */
        else {
            //Definir ViewHolder - através da tag da convertView
            myViewHolder = (MyViewHolder) convertView.getTag();
            //Definir binding através da ViewHolder
            binding = myViewHolder.item;
        }

        binding.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), RoomDetailActivity.class));
            }
        });

        /* Atualizar a viewHolder */
        myViewHolder.update(reservations.get(position));

        return convertView;
    }

    /* Classe privada onde serão guardadas informações de cada item da lista
     *
     * Utilizamos binding
     *
     */
    public class MyViewHolder {
        private final ItemRoomListBinding item;

        public MyViewHolder(ItemRoomListBinding item) {
            this.item = item;
        }

        /* Método para atualizar os valores */
        public void update(Reservation reservation) {
            item.description.setText(reservation.getRoom().getDescricao());
        }
    }
}
