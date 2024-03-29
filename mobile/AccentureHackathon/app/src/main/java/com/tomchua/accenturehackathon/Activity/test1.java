package com.tomchua.accenturehackathon.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tomchua.accenturehackathon.Models.CartItemObject;
import com.tomchua.accenturehackathon.Models.ItemObject;
import com.tomchua.accenturehackathon.R;
import com.tomchua.accenturehackathon.adapter.CashoutListAdapter;
import com.tomchua.accenturehackathon.adapter.ProductListAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class test1 extends AppCompatActivity {

    private GridView mListMenu;
    private TextView mButtonClearOrders;
    private TextView mTextTotalAmount;
    private FloatingActionButton mButtonCart;

    private ProductListAdapter mAdapter;

    private List<CartItemObject> mCartItemObjects = new ArrayList<>();
    private Vector<Dialog> mDialogs = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        initializeVariables();


        mButtonClearOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearCart();
            }
        });

        mButtonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getCartTotalAmount() > 0.0) showCashoutDialog();
                else Toast.makeText(test1.this, "Sorry Cart is emptyx", Toast.LENGTH_SHORT).show();
            }
        });

        mButtonClearOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearCart();
            }
        });


    }

    private void initializeVariables() {
        mListMenu = (GridView) findViewById(R.id.list_items);
        mButtonClearOrders = (TextView) findViewById(R.id.btn_clearorder);
        mTextTotalAmount = (TextView) findViewById(R.id.text_totalamount);
        mButtonCart = (FloatingActionButton) findViewById(R.id.fab);

        setupAdapters();
    }

    // Method for setting up adapters for lists
    // Used in 'initializeVariables'
    private void setupAdapters() {
        String[] productName = {"Product 1", "Product 2", "Product 3", "Product 4", "Product 5", "Product 6", "Product 7", "Product 8"};
        String[] productCode = {"1", "2", "3", "4", "5", "6", "7", "8"};
        String[] productBarcode = {"1", "2", "3", "4", "5", "6", "7", "8"};
        Double[] productPrice = {10d, 20d, 30d, 40d, 50d, 60d, 70d, 80d};

        for (int x = 0; x < productName.length; x++) {
            ItemObject obj = new ItemObject();
            obj.setItemName(productName[x]);
            obj.setItemPrice(productPrice[x]);
            obj.setItemCode(productCode[x]);
            obj.setItemBarcode(productBarcode[x]);
            obj.setQuantity(50);
            CartItemObject cartItemObject = new CartItemObject();
            cartItemObject.setItemObject(obj);
            cartItemObject.setQty(0);
            cartItemObject.setTotalAmount(0d);
            mCartItemObjects.add(cartItemObject);
        }

//        mAdapter = new ProductListAdapter(this, mCartItemObjects);

        mAdapter.setOnMinusQtyListener(new ProductListAdapter.OnMinusQtyListener() {
            @Override
            public void onMinusQty(Integer position) {
                Integer qty = mCartItemObjects.get(position).getQty() - 1;
                Double price = mCartItemObjects.get(position).getItemObject().getItemPrice();
                mCartItemObjects.get(position).setQty(qty);
                mCartItemObjects.get(position).setTotalAmount(price * qty);
                updateTotalAmount();
                mAdapter.notifyDataSetChanged();
            }
        });

        mListMenu.setAdapter(mAdapter);

        mListMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Integer qty = mCartItemObjects.get(i).getItemObject().getQuantity() > mCartItemObjects.get(i).getQty() ? mCartItemObjects.get(i).getQty() + 1 : mCartItemObjects.get(i).getQty();
                Double price = mCartItemObjects.get(i).getItemObject().getItemPrice();
                mCartItemObjects.get(i).setQty(qty);
                mCartItemObjects.get(i).setTotalAmount(price * qty);
                updateTotalAmount();
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    // Method for updating total amount view (TextView)
    // Used in 'onCreate//mButtonClearOrders.OnClick', 'setupAdapters//mListMenu.OnItemClick', 'setupAdapters//mAdapter.setOnMinusQtyListener'
    private void updateTotalAmount() {
        Double totalAmount = getCartTotalAmount();
        mTextTotalAmount.setText(getString(R.string.peso) + new DecimalFormat("###,##0.00").format(totalAmount));
        if (totalAmount == 0.0) {
            mButtonClearOrders.setVisibility(View.GONE);
            findViewById(R.id.layout_overview).setVisibility(View.GONE);
        }
        else {
            mButtonClearOrders.setVisibility(View.VISIBLE);
            findViewById(R.id.layout_overview).setVisibility(View.VISIBLE);
        }
    }

    // Get total amount from cart
    private Double getCartTotalAmount() {
        Double totalAmount = 0.0;
        for (int x = 0; x < mCartItemObjects.size(); x++)
            totalAmount += mCartItemObjects.get(x).getTotalAmount();
        return totalAmount;
    }

    // Method to get all cart items intended for cashout
    private List<CartItemObject> getCartItemsForCashout() {
        List<CartItemObject> cartItemObjects = new ArrayList<>();
        for (CartItemObject cartItemObject : mCartItemObjects)
            if (cartItemObject.getQty() > 0)
                cartItemObjects.add(cartItemObject);

        return cartItemObjects;
    }
    // Method to clear cart
    private void clearCart() {
        for (CartItemObject obj : mCartItemObjects) {
            obj.setQty(0);
            obj.setTotalAmount(0d);
        }
        mAdapter.notifyDataSetChanged();
        updateTotalAmount();
    }

    // Method to show Cashout Dialog
    private void showCashoutDialog() {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_cashout);
        mDialogs.add(dialog);

        final Double totalAmount = getCartTotalAmount();

        ListView listCartItems = (ListView) dialog.findViewById(R.id.list_cartitems);
        final TextView textTotalAmount = (TextView) dialog.findViewById(R.id.text_totalamount);
        TextView btnProceed = (TextView) dialog.findViewById(R.id.btn_proceed);
        TextView btnCancel = (TextView) dialog.findViewById(R.id.btn_cancel);

        textTotalAmount.setText("Total Amount: " + getString(R.string.peso) + new DecimalFormat("###,##0.00").format(totalAmount));

        CashoutListAdapter adapter = new CashoutListAdapter(this, getCartItemsForCashout());
        listCartItems.setAdapter(adapter);

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEnterAmountDialog();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setCancelable(true);
        dialog.show();
    }

    // Method to show transaction dialog
    private void showEnterAmountDialog() {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_enteramount);
        mDialogs.add(dialog);

        TextView textTotalAmount = (TextView) dialog.findViewById(R.id.text_totalamount);
        TextView btnCancel = (TextView) dialog.findViewById(R.id.btn_cancel);
        TextView btnProceed = (TextView) dialog.findViewById(R.id.btn_proceed);
        final EditText inputAmount = (EditText) dialog.findViewById(R.id.text_cashamount);
        final TextView textChange = (TextView) dialog.findViewById(R.id.text_change);

        Double cartTotalAmount = getCartTotalAmount();
        final Double totalCashPayment = cartTotalAmount;
        textChange.setText("₱0.00");
        textTotalAmount.setText(getString(R.string.peso) + new DecimalFormat("###,##0.00").format(cartTotalAmount));

        inputAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Double change = Double.parseDouble(charSequence.toString().isEmpty() ? "0" : charSequence.toString()) - totalCashPayment;
                textChange.setText(getString(R.string.peso) + new DecimalFormat("###,##0.00").format(change < 0.0 ? 0.0 : change));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double change = Double.parseDouble(textChange.getText().toString().substring(1, textChange.getText().toString().length()));
                Double cashAmount = Double.parseDouble(inputAmount.getText().toString().isEmpty() ? "0" : inputAmount.getText().toString());
                if (cashAmount >= totalCashPayment) {
                    Toast.makeText(test1.this, "success", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(test1.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.setCancelable(true);
        dialog.show();
    }
}
