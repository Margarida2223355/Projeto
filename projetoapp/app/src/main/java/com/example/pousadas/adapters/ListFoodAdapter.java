package com.example.pousadas.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.pousadas.R;
import com.example.pousadas.databinding.ItemListBinding;
import com.example.pousadas.fragments.FoodClientFragment;
import com.example.pousadas.models.Food;

import java.util.ArrayList;

public class ListFoodAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Food> foods;

    public ListFoodAdapter(Context context, ArrayList<Food> foods) {
        this.context = context;
        this.foods = foods;
    }

    @Override
    public int getCount() {
        return foods.size();
    }

    @Override
    public Object getItem(int position) {
        return foods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return foods.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ItemListBinding binding;
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
            binding = ItemListBinding.inflate(inflater); //Definir binding através da ItemListBinding
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

        /* Define TAG e método OnClickListener dos botões
         *
         * Increment e Decrement
         */
        binding.increment.setTag(position);
        binding.increment.setOnClickListener(incrementButton);
        binding.decrement.setTag(position);
        binding.decrement.setOnClickListener(decrementButton);

        /* Atualizar a viewHolder */
        myViewHolder.update(foods.get(position));

        return convertView;
    }


    private View.OnClickListener incrementButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int position = (Integer) v.getTag();

            View parentView = (View) ((View) ((View) ((View) v.getParent()).getParent()).getParent()).getParent();

            // Retrieve the MyViewHolder instance from the tag
            MyViewHolder myViewHolder = (MyViewHolder) parentView.getTag();

            if (myViewHolder != null) {
                Food food = foods.get(position);
                food.addQty();
                myViewHolder.update(food);
            }
        }
    };

    private View.OnClickListener decrementButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int position = (Integer) v.getTag();

            View parentView = (View) ((View) ((View) ((View) v.getParent()).getParent()).getParent()).getParent();

            // Retrieve the MyViewHolder instance from the tag
            MyViewHolder myViewHolder = (MyViewHolder) parentView.getTag();

            if (myViewHolder != null) {
                Food food = foods.get(position);
                food.remQty();
                myViewHolder.update(food);
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
        public void update(Food food) {
            item.description.setText(food.getName());
            item.qty.setText(Integer.toString(food.getQty()));
            Log.i("TAG", "--> " + item.qty.getText());
        }
    }
}
