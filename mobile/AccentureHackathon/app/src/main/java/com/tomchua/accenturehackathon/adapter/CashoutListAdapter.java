package com.tomchua.accenturehackathon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tomchua.accenturehackathon.Models.CartItemObject;
import com.tomchua.accenturehackathon.R;

import java.util.List;


public class CashoutListAdapter extends BaseAdapter {
    private List<CartItemObject> mCartItemObjects;

    private LayoutInflater mInflater;
    private Context mContext;

    public CashoutListAdapter(Context context, List<CartItemObject> objects) {
        this.mContext = context;
        this.mCartItemObjects = objects;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mCartItemObjects.size();
    }

    @Override
    public CartItemObject getItem(int i) {
        return mCartItemObjects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = mInflater.inflate(R.layout.item_cashout, null);

        TextView textName = (TextView) view.findViewById(R.id.text_itemname);
        TextView textPrice = (TextView) view.findViewById(R.id.text_itemprice);
        TextView textQty = (TextView) view.findViewById(R.id.text_qty);
        TextView textTotalAmount = (TextView) view.findViewById(R.id.text_totalamount);

        textName.setText(mCartItemObjects.get(i).getItemObject().getItemName());
        textPrice.setText(mContext.getString(R.string.peso) + mCartItemObjects.get(i).getItemObject().getItemPrice());
        textQty.setText(mContext.getString(R.string.qty) + mCartItemObjects.get(i).getQty());
        textTotalAmount.setText(mContext.getString(R.string.peso) + mCartItemObjects.get(i).getTotalAmount());

        return view;
    }
}
