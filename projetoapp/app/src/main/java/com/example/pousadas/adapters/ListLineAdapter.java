package com.example.pousadas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.pousadas.databinding.ItemLineListBinding;
import com.example.pousadas.databinding.ItemListBinding;
import com.example.pousadas.databinding.ItemRoomListBinding;
import com.example.pousadas.models.Invoice_line;
import com.example.pousadas.models.Reservation;

import java.util.ArrayList;

public class ListLineAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Invoice_line> lines;

    public ListLineAdapter(Context context, ArrayList<Invoice_line> lines) {
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
        ItemLineListBinding binding;
        MyViewHolder myViewHolder;

        /* Se não existir inflater */
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            binding = ItemLineListBinding.inflate(inflater); //Definir binding através da ItemListBinding
            convertView = binding.getRoot(); //Definir convertView
            myViewHolder = new MyViewHolder(binding); //Instanciar ViewHolder

            //Definir tag da convertView - ViewHolder
            convertView.setTag(myViewHolder);
        }

        else {
            //Definir ViewHolder - através da tag da convertView
            myViewHolder = (ListLineAdapter.MyViewHolder) convertView.getTag();
            //Definir binding através da ViewHolder
            binding = myViewHolder.item;
        }

        myViewHolder.update(lines.get(position));

        return convertView;
    }

    /* Classe privada onde serão guardadas informações de cada item da lista
     *
     * Utilizamos binding
     *
     */
    public class MyViewHolder {
        private final ItemLineListBinding item;

        public MyViewHolder(ItemLineListBinding item) {
            this.item = item;
        }

        /* Método para atualizar os valores */
        public void update(Invoice_line line) {
            if (line.getService() != null) {
                item.description.setText(line.getService().getDescription());
                item.qty.setText(String.valueOf(line.getService().getQty()));
            }

            else if (line.getFood() != null) {
                item.description.setText(line.getFood().getName());
                item.qty.setText(String.valueOf(line.getFood().getQty()));
            }
        }
    }
}
