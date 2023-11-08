package com.emmanuel_rono.shoppingapp;

import android.content.SharedPreferences;

public class Cart {
    static SharedPreferences preferences = null;
    static public int money = 0; //in cents
    final static private String MoneyID = "MONEY";

    static public void InitCart(SharedPreferences pref)
    {
        preferences = pref;
        money = preferences.getInt(MoneyID, 10000); //Default $100
    }

    static public void AddMoney(int cents)
    {
        money += cents;
        SaveMoney();
    }
    // Save Money
    static public void SaveMoney() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(MoneyID, money);
        editor.commit();
    }

    static public void SpendMoney(int cents) {
        money -= cents;
        SaveMoney();
    }


}
