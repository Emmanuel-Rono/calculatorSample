package com.emmanuel_rono.shoppingapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;

public class ConfirmPurchaseDialog extends Dialog {

    public ConfirmPurchaseDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_layout);

        Button no = findViewById(R.id.cancelButton);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void SetDetails(final int itemIndex, final int itemId, final LinearLayout parentLayout) {
        Button yes = findViewById(R.id.purchaseButton);
        yes.setOnClickListener(new View.OnClickListener() {
            private int index = itemIndex;
            private int id = itemId;
            private LinearLayout layout = parentLayout;

            @Override
            public void onClick(View view) {
                ShopData item = ShopDataManager.data.get(index);
                if (Cart.money >= item.cost) { // If sufficient funds
                    Cart.AddMoney(-item.cost); // Deduct money
                    item.purchased = true; // Mark item as purchased
                    for (int i = 0; i < layout.getChildCount(); ++i) {
                        View v = layout.getChildAt(i);
                        TextView hidden = v.findViewById(R.id.hiddenId);
                        if (hidden != null) {
                            int layoutID = Integer.parseInt("" + hidden.getText());
                            if (layoutID == id) {
                                layout.removeViewAt(i);
                                break;
                            }
                        } else {
                            Toast.makeText(getContext(), "null", Toast.LENGTH_LONG).show();
                        }
                    }
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Insufficient Funds", Toast.LENGTH_LONG).show();
                }
            }
        });

        TextView title = findViewById(R.id.nameText);
        title.setText(ShopDataManager.data.get(itemIndex).name);

        TextView desc = findViewById(R.id.description);
        desc.setText(ShopDataManager.data.get(itemIndex).description);

        TextView price = findViewById(R.id.cost);
        price.setText("$" + ShopDataManager.data.get(itemIndex).cost);

        ImageButton image = findViewById(R.id.itemImage);
        image.setImageResource(ShopDataManager.data.get(itemIndex).imageResource);
    }
}
