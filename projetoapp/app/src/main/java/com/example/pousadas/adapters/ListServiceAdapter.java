package com.example.pousadas.adapters;

import android.content.Context;
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
        /* Se não existir inflater */
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        /* Se não existir convertView - view com a holderView atualizada */
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list, null);
        }

        ViewHolderList viewHolderList = (ViewHolderList) convertView.getTag();

        /* Se for nula, instanciar a classe */
        if (viewHolderList == null) {
            viewHolderList = new ViewHolderList(convertView);
            convertView.setTag(viewHolderList);
        }

        /* Atualizar a viewHolder */
        viewHolderList.update(services.get(position));

        return convertView;
    }

    /* Classe privada onde serão declarados e atualizados os componentes da view
     * de cada item da lista.
     */
    private class ViewHolderList {
        private ItemListBinding binding;

        /* Método para definir objetos da vista (construtor)
         *
         * Text view - descrição da comida
         * Switch - Botão de adicionar ou remover da lista
         */
        public ViewHolderList(View view) {
            binding = ItemListBinding.inflate(inflater);
            binding.getRoot();
        }

        /* Método para atualizar os valores */
        public void update(Service service) {
            binding.description.setText(service.getName());
        }
    }
}
