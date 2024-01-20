package com.example.pousadas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.pousadas.databinding.ItemInvoiceListBinding;
import com.example.pousadas.databinding.ItemLineListBinding;
import com.example.pousadas.models.Invoice_line;

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

        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            binding = ItemInvoiceListBinding.inflate(inflater);
            convertView = binding.getRoot();
            myViewHolder = new MyViewHolder(binding);

            convertView.setTag(myViewHolder);
        }

        else {
            myViewHolder = (MyViewHolder) convertView.getTag();
            binding = myViewHolder.item;
        }

        /* Atualizar a viewHolder */
        myViewHolder.update(lines.get(position));

        return convertView;
    }
    public class MyViewHolder {
        private final ItemInvoiceListBinding item;

        public MyViewHolder(ItemInvoiceListBinding item) {
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

            item.price.setText(line.getUnit_price() + "€");
        }
    }

}
