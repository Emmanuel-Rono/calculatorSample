package com.emmanuel_rono.shoppingapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static ConfirmPurchaseDialog confirmPurchaseDialog = null;
    public static SharedPreferences preferences = null;
    Button resetPurchasesButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Cart.InitCart(preferences);

        confirmPurchaseDialog = new ConfirmPurchaseDialog(this);

        ShopDataManager.loadPurchases();  // Load purchased items first
        ShopDataManager.InitShopItems(findViewById(R.id.mainLayout), this);

        // Add a reset purchases button to the MainActivity
        resetPurchasesButton = new Button(this);
        resetPurchasesButton.setText("Reset Purchases");
        resetPurchasesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopDataManager.resetPurchases();
                recreate(); // Refresh the activity to reflect changes
            }
        });

        LinearLayout mainLayout = findViewById(R.id.mainLayout);
        mainLayout.addView(resetPurchasesButton); // Add the button to your main layout

            resetPurchasesButton = findViewById(R.id.resetPurchasesButton);
            resetPurchasesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShopDataManager.resetPurchases();
                    recreate(); // Refresh the activity to reflect changes
                }
            });

        // Inside onCreate
        Button addMoneyButton = findViewById(R.id.addMoneyButton);
        addMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add a fixed amount, say 1000 cents ($10). You could make this dynamic with an input dialog.
                Cart.AddMoney(1000);
                Toast.makeText(MainActivity.this, "$10 added", Toast.LENGTH_SHORT).show();
            }
        });

        Button resetPurchasesButton = findViewById(R.id.resetPurchasesButton);
        resetPurchasesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset the purchases
                SharedPreferences.Editor editor = preferences.edit();
                for (ShopData item : ShopDataManager.data) {
                    editor.putBoolean("PURCHASED_" + item.id, false);
                }
                editor.apply();

                // Refresh shop items
                LinearLayout layout = findViewById(R.id.mainLayout);
                layout.removeAllViews();
                ShopDataManager.InitShopItems(layout, MainActivity.this);
            }
        });

    }

    }

