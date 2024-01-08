package com.example.pousadas.adapters;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.view.inputmethod.InputMethodManager;


import com.example.pousadas.databinding.ItemLineListBinding;
import com.example.pousadas.models.Invoice_line;
import com.example.pousadas.models.Singleton;

import java.util.ArrayList;

public class ListLineAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Invoice_line> lines;
    int oldQty;

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

        binding.qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                oldQty = lines.get(position).getQty();
                if ((!s.toString().isEmpty()) && (!(Integer.parseInt(s.toString()) == oldQty))) {

                    Invoice_line line = lines.get(position);
                    line.setQty(Integer.parseInt(myViewHolder.item.qty.getText().toString()));

                    Singleton.getInstance(context).editLineAPI(line, context);

                    myViewHolder.item.qty.clearFocus();
                    ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(binding.qty.getWindowToken(), 0);
                }
            }
        });
        binding.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Invoice_line line = lines.get(position);
                line.setSelected(isChecked);
            }
        });

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
                item.qty.setText(String.valueOf(line.getQty()));
            }

            else if (line.getFood() != null) {
                item.description.setText(line.getFood().getName());
                item.qty.setText(String.valueOf(line.getQty()));
            }
        }
    }
}
