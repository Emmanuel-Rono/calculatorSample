package com.emmanuel_rono.shoppingapp;

public class ShopData {
        public String name;
        public int imageResource;
        public String description;
        public int cost;
        public boolean purchased;
        public int id;
        private static int nextId = -1;

        ShopData(String nm, int res, String desc, int cst)
        {
            name = nm;
            imageResource = res;
            description = desc;
            cost = cst;
            id = nextId++;
        }
    public String getPurchaseKey() {
        return "PURCHASED_" + id;
    }


}
