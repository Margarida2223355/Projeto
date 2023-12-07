package com.example.pousadas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pousadas.R;
import com.example.pousadas.databinding.ItemListFoodBinding;
import com.example.pousadas.models.Food;
import com.google.android.material.switchmaterial.SwitchMaterial;

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
        /* Se não existir inflater */
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        /* Se não existir convertView - view com a holderView atualizada */
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list_food, null);
        }

        ViewHolderList viewHolderList = (ViewHolderList) convertView.getTag();

        /* Se for nula, instanciar a classe */
        if (viewHolderList == null) {
            viewHolderList = new ViewHolderList(convertView);
            convertView.setTag(viewHolderList);
        }

        /* Atualizar a viewHolder */
        viewHolderList.update(foods.get(position));

        return convertView;
    }

    /* Classe privada onde serão declarados e atualizados os componentes da view
     * de cada item da lista.
     */
    private class ViewHolderList {
        private ItemListFoodBinding binding;

        /* Método para definir objetos da vista (construtor)
         *
         * Text view - descrição da comida
         * Switch - Botão de adicionar ou remover da lista
         */
        public ViewHolderList(View view) {
            binding = ItemListFoodBinding.inflate(inflater);
            binding.getRoot();

        }

        /* Método para atualizar os valores */
        public void update(Food food) {
            binding.foodDescription.setText(food.getName());
        }
    }
}
