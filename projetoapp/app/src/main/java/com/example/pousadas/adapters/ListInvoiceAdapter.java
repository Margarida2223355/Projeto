package com.example.pousadas.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.pousadas.activities.ReservationDetailActivity;
import com.example.pousadas.databinding.ItemInvoiceListBinding;
import com.example.pousadas.databinding.ItemLineListBinding;
import com.example.pousadas.databinding.ItemRoomListBinding;
import com.example.pousadas.models.Invoice_line;
import com.example.pousadas.models.Reservation;

import java.util.ArrayList;

public class ListInvoiceAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Invoice_line> lines;

    public ListInvoiceAdapter(Context context, ArrayList<Invoice_line> lines) {
        this.context = context;
        this.lines = lines;
    }

    @Override
    public int getCount() {
        return lines.size();
    }

    @Override
    public Object getItem(int position) {
        return lines.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lines.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ItemInvoiceListBinding binding;
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
            binding = ItemInvoiceListBinding.inflate(inflater); //Definir binding através da ItemListBinding
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

        /* Atualizar a viewHolder */
        myViewHolder.update(lines.get(position));

        return convertView;
    }

    /* Classe privada onde serão guardadas informações de cada item da lista
     *
     * Utilizamos binding
     *
     */
    public class MyViewHolder {
        private final ItemInvoiceListBinding item;

        public MyViewHolder(ItemInvoiceListBinding item) {
            this.item = item;
        }

        /* Método para atualizar os valores */
        public void update(Invoice_line line) {
            if (line.getFood() != null) {
                item.description.setText(line.getFood().getName());
            }

            else {
                item.description.setText(line.getService().getDescription());
            }

            item.qty.setText("" + line.getQty());
            item.price.setText(line.getUnit_price() + "€");
        }
    }

}
