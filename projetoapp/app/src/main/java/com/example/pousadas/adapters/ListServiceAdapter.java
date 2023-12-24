package com.example.pousadas.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.pousadas.R;
import com.example.pousadas.databinding.ItemListBinding;
import com.example.pousadas.models.Food;
import com.example.pousadas.models.Service;

import java.util.ArrayList;

public class ListServiceAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Service> services;

    public ListServiceAdapter(Context context, ArrayList<Service> services) {
        this.context = context;
        this.services = services;
    }

    @Override
    public int getCount() {
        return services.size();
    }

    @Override
    public Object getItem(int position) {
        return services.get(position);
    }

    @Override
    public long getItemId(int position) {
        return services.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ItemListBinding binding;
        ListServiceAdapter.MyViewHolder myViewHolder;

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
            binding = ItemListBinding.inflate(inflater); //Definir binding através da ItemListBinding
            convertView = binding.getRoot(); //Definir convertView
            myViewHolder = new ListServiceAdapter.MyViewHolder(binding); //Instanciar ViewHolder

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
            myViewHolder = (ListServiceAdapter.MyViewHolder) convertView.getTag();
            //Definir binding através da ViewHolder
            binding = myViewHolder.item;
        }

        /* Define TAG e método OnClickListener dos botões
         *
         * Increment e Decrement
         */
        binding.increment.setTag(position);
        binding.increment.setOnClickListener(incrementButton);
        binding.decrement.setTag(position);
        binding.decrement.setOnClickListener(decrementButton);

        /* Atualizar a viewHolder */
        myViewHolder.update(services.get(position));

        return convertView;
    }


    private View.OnClickListener incrementButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int position = (Integer) v.getTag();

            View parentView = (View) ((View) ((View) ((View) v.getParent()).getParent()).getParent()).getParent();

            // Retrieve the MyViewHolder instance from the tag
            ListServiceAdapter.MyViewHolder myViewHolder = (ListServiceAdapter.MyViewHolder) parentView.getTag();

            if (myViewHolder != null) {
                Service service = services.get(position);
                service.addQty();
                myViewHolder.update(service);
            }
        }
    };

    private View.OnClickListener decrementButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int position = (Integer) v.getTag();

            View parentView = (View) ((View) ((View) ((View) v.getParent()).getParent()).getParent()).getParent();

            // Retrieve the MyViewHolder instance from the tag
            ListServiceAdapter.MyViewHolder myViewHolder = (ListServiceAdapter.MyViewHolder) parentView.getTag();

            if (myViewHolder != null) {
                Service service = services.get(position);
                service.remQty();
                myViewHolder.update(service);
            }
        }
    };


    /* Classe privada onde serão guardadas informações de cada item da lista
     *
     * Utilizamos binding
     *
     */
    public class MyViewHolder {
        private final ItemListBinding item;

        public MyViewHolder(ItemListBinding item) {
            this.item = item;
        }

        /* Método para atualizar os valores */
        public void update(Service service) {
            item.description.setText(service.getName());
            item.qty.setText(Integer.toString(service.getQty()));
            Log.i("TAG", "--> " + item.qty.getText());
        }
    }
}
