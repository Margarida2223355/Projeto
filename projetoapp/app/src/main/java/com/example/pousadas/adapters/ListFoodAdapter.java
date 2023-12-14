package com.example.pousadas.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pousadas.R;
import com.example.pousadas.databinding.ItemListBinding;
import com.example.pousadas.models.Food;

import java.util.ArrayList;

public class ListFoodAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Food> foods;
    private ItemListBinding binding;

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
        /* Se não existir inflater */
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        /* Se não existir convertView - view com a holderView atualizada */
        if (convertView == null) {
            binding = ItemListBinding.inflate(inflater);
            convertView = binding.getRoot();

            /* Definir listener para botões "increment" e "decrement" */
            binding.increment.setOnClickListener(onIncrementClick);
            binding.decrement.setOnClickListener(onDecrementClick);

            convertView.setTag(binding);
        }

        else {
            binding = (ItemListBinding) convertView.getTag();
        }

        /* Atualizar a viewHolder */
        update(foods.get(position));

        return convertView;
    }

    /* Método listener do botão Increment
     *
     * Incrementar na quantidade.
     * Quantidade inicial de 1un
     *
     */
    public View.OnClickListener onIncrementClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "Increment", Toast.LENGTH_SHORT).show();
        }
    };

    /* Método listener do botão Decrement
     *
     * Decrementar na quantidade.
     * Quantidade inicial de 1un
     *
     */
    public View.OnClickListener onDecrementClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "Decrement", Toast.LENGTH_SHORT).show();
        }
    };

    /* Método para atualizar os valores */
    public void update(Food food) {
        binding.description.setText(food.getName());
        binding.qty.setText(Integer.toString(food.getQty()));
    }
}
