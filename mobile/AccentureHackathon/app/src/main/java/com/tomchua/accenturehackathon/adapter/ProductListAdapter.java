package com.tomchua.accenturehackathon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tomchua.accenturehackathon.Models.CartItemObject;
import com.tomchua.accenturehackathon.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;



public class ProductListAdapter extends BaseAdapter implements Filterable {
    private List<CartItemObject> mProductList;
    private List<CartItemObject> mFilteredProductItems;

    private LayoutInflater mInflater;
    private ItemFilter mFilter = new ItemFilter();

    public ProductListAdapter(Context context, List<CartItemObject> productList) {
        this.mInflater = LayoutInflater.from(context);
        this.mProductList = productList;
        this.mFilteredProductItems = productList;
    }

    @Override
    public int getCount() {
        return mFilteredProductItems.size();
    }

    @Override
    public CartItemObject getItem(int i) {
        return mFilteredProductItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = mInflater.inflate(R.layout.item_menuitem, null);

        LinearLayout layoutMenu = (LinearLayout) view.findViewById(R.id.layout_menu);
        TextView textName = (TextView) view.findViewById(R.id.text_itemname);
        TextView textPrice = (TextView) view.findViewById(R.id.text_itemprice);
        TextView textQty = (TextView) view.findViewById(R.id.text_qty);
        TextView textTotalAmount = (TextView) view.findViewById(R.id.text_totalamount);
        TextView btnMinus = (TextView) view.findViewById(R.id.btn_minus);

        textName.setText(mFilteredProductItems.get(i).getItemObject().getItemName());
        textPrice.setText("₱" + new DecimalFormat("###,###,###.00").format(mFilteredProductItems.get(i).getItemObject().getItemPrice()));
        textTotalAmount.setText("₱" + new DecimalFormat("###,###,###.00").format(mFilteredProductItems.get(i).getTotalAmount()));
        textQty.setText("x" + mFilteredProductItems.get(i).getQty());

        if (mFilteredProductItems.get(i).getQty() == 0) {
            textQty.setVisibility(View.INVISIBLE);
            btnMinus.setVisibility(View.GONE);
            textTotalAmount.setVisibility(View.GONE);
            layoutMenu.setBackgroundResource(R.drawable.menu_item_background);
        }
        else {
            textQty.setVisibility(View.VISIBLE);
            btnMinus.setVisibility(View.VISIBLE);
            textTotalAmount.setVisibility(View.VISIBLE);
            layoutMenu.setBackgroundResource(R.drawable.menu_item_background_inverse);
        }

        final int position = i;
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnMinusQtyListener != null) mOnMinusQtyListener.onMinusQty(position);
            }
        });

        return view;
    }

    public interface OnMinusQtyListener {
        void onMinusQty(Integer position);
    }

    private OnMinusQtyListener mOnMinusQtyListener;

    public void setOnMinusQtyListener(OnMinusQtyListener listener) {
        this.mOnMinusQtyListener = listener;
    }


    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();
            final List<CartItemObject> list = mProductList;

            int count = list.size();
            final ArrayList<CartItemObject> nlist = new ArrayList<>(count);

            String category, name, code;
            for (int i = 0; i < count; i++) {
                name = list.get(i).getItemObject().getItemName();
                category = list.get(i).getItemObject().getItemCategory();
                code = list.get(i).getItemObject().getItemCode();
                if (name.toLowerCase().contains(filterString))
                    nlist.add(mProductList.get(i));
                else if (category.toLowerCase().contains(filterString))
                    nlist.add(mProductList.get(i));
                else if (code.toLowerCase().contains(filterString))
                    nlist.add(mProductList.get(i));
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mFilteredProductItems = (ArrayList<CartItemObject>) results.values;
            notifyDataSetChanged();
        }
    }
}
