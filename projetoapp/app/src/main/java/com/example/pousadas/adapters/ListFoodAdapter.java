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

            convertView.setTag(position);

            /* Propriedade para guardar a convertView
             *
             * Depois no método onClick
             * vamos buscar o tag (getItemId(position))
             *
             * Deste modo, conseguimos ter acesso ao item escolhido.
             * Ou seja, se clicarmos no botão do primeiro item
             * será a quantidade desse a ser alterado.
             *
             */
            View finalConvertView = convertView;

            /* Definir listener para botões "increment" e "decrement" */
            binding.increment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Food food = foods.get(Integer.parseInt(finalConvertView.getTag().toString()));
                    Log.i("TAG", "--> " + food.getName() + " - id " + food.getId());
                    Toast.makeText(context, "Increment", Toast.LENGTH_SHORT).show();
                }
            });
            binding.decrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Decrement", Toast.LENGTH_SHORT).show();
                }
            });

        }

        else {
            binding = (ItemListBinding) convertView.getTag();
        }

        /* Atualizar a viewHolder */
        update(foods.get(position));

        return convertView;
    }

    /* Método para atualizar os valores */
    public void update(Food food) {
        binding.description.setText(food.getName());
        binding.qty.setText(Integer.toString(food.getQty()));
    }
}
