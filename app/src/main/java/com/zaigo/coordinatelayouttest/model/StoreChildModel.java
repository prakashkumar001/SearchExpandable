package com.zaigo.coordinatelayouttest.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StoreChildModel {

    /**
     * status_code : 200
     * store_list : [{"products":[{"CONTENT":"Free Range, Grass Fed, Halal Beef","ID":"92","IMAGES":"https://organicfoodsandcafe.com/nr_butchery/wp-content/uploads/2019/06/IMG_10821_nw.png","mQuantity":0,"Price":"44.00","Title":"Gourmet Beef Burgers - Australia (Perkg)"},{"CONTENT":"Free Range, Grass Fed, Halal Beef","ID":"85","IMAGES":"https://organicfoodsandcafe.com/nr_butchery/wp-content/uploads/2019/06/IMG_10821_nw.png","mQuantity":0,"Price":"90.00","Title":"Ribeye Steak - Australia (Perkg)"},{"CONTENT":"Free Range, Grass Fed, Halal Beef","ID":"86","IMAGES":"https://organicfoodsandcafe.com/nr_butchery/wp-content/uploads/2019/06/IMG_10821_nw.png","mQuantity":0,"Price":"135.00","Title":"Tenderloin Steak - Australia (Perkg)"},{"CONTENT":"Free Range, Grass Fed, Halal Beef","ID":"87","IMAGES":"https://organicfoodsandcafe.com/nr_butchery/wp-content/uploads/2019/06/IMG_10821_nw.png","mQuantity":0,"Price":"80.00","Title":"Striploin Steak - Australia (Perkg)"},{"CONTENT":"Free Range, Grass Fed, Halal Beef","ID":"88","IMAGES":"https://organicfoodsandcafe.com/nr_butchery/wp-content/uploads/2019/06/IMG_10821_nw.png","mQuantity":0,"Price":"58.00","Title":"Rump Steak - Australia (Perkg)"},{"CONTENT":"Free Range, Grass Fed, Halal Beef","ID":"89","IMAGES":"https://organicfoodsandcafe.com/nr_butchery/wp-content/uploads/2019/06/IMG_10821_nw.png","mQuantity":0,"Price":"65.00","Title":"Wagyu Burgers - Australia (Perkg)"},{"CONTENT":"Free Range, Grass Fed, Halal Beef","ID":"90","IMAGES":"https://organicfoodsandcafe.com/nr_butchery/wp-content/uploads/2019/06/IMG_10821_nw.png","mQuantity":0,"Price":"55.00","Title":"Muscle Burgers - Australia (Perkg)"},{"CONTENT":"Free Range, Grass Fed, Halal Beef","ID":"91","IMAGES":"https://organicfoodsandcafe.com/nr_butchery/wp-content/uploads/2019/06/IMG_10821_nw.png","mQuantity":0,"Price":"44.00","Title":"Gourmet Beef Burgers - Australia (Perkg)"},{"CONTENT":"Free Range, Grass Fed, Halal Beef","ID":"93","IMAGES":"https://organicfoodsandcafe.com/nr_butchery/wp-content/uploads/2019/06/1019.png","mQuantity":0,"Price":"46.00","Title":"Beef Steak Lean Mince - Australia (Perkg)"},{"CONTENT":"Free Range, Grass Fed, Halal Beef","ID":"95","IMAGES":"https://organicfoodsandcafe.com/nr_butchery/wp-content/uploads/2019/06/IMG_10821_nw.png","mQuantity":0,"Price":"46.30","Title":"Topside Roast - Australia (Perkg)"},{"CONTENT":"Free Range, Grass Fed, Halal Beef","ID":"104","IMAGES":"https://organicfoodsandcafe.com/nr_butchery/wp-content/uploads/2019/06/IMG_10821_nw.png","mQuantity":0,"Price":"55.70","Title":"Beef Stir Fry - Australia (Perkg)"},{"CONTENT":"Free Range, Grass Fed, Halal Beef","ID":"105","IMAGES":"https://organicfoodsandcafe.com/nr_butchery/wp-content/uploads/2019/06/1171.png","mQuantity":0,"Price":"45.00","Title":"Beef Boerwors - Australia (Perkg)"},{"CONTENT":"Free Range, Grass Fed, Halal Beef","ID":"108","IMAGES":"https://organicfoodsandcafe.com/nr_butchery/wp-content/uploads/2019/06/IMG_10821_nw.png","mQuantity":0,"Price":"52.00","Title":"Beef Brisket - Australia (Perkg)"},{"CONTENT":"Free Range, Grass Fed, Halal Beef","ID":"109","IMAGES":"https://organicfoodsandcafe.com/nr_butchery/wp-content/uploads/2019/06/IMG_10821_nw.png","mQuantity":0,"Price":"55.70","Title":"Beef Cubes - Australia (Perkg)"},{"CONTENT":"Free Range, Grass Fed, Halal Beef","ID":"110","IMAGES":"https://organicfoodsandcafe.com/nr_butchery/wp-content/uploads/2019/06/IMG_10821_nw.png","mQuantity":0,"Price":"86.00","Title":"Beef Short Ribs - Australia (Perkg)"},{"CONTENT":"Free Range, Grass Fed, Halal Beef","ID":"111","IMAGES":"https://organicfoodsandcafe.com/nr_butchery/wp-content/uploads/2019/06/IMG_10821_nw.png","mQuantity":0,"Price":"180.00","Title":"Tomahawk Steak - Australia (Perkg)"},{"CONTENT":"Free Range, Grass Fed, Halal Beef","ID":"112","IMAGES":"https://organicfoodsandcafe.com/nr_butchery/wp-content/uploads/2019/06/IMG_10821_nw.png","mQuantity":0,"Price":"33.45","Title":"Brisket Beef Bacon - Australia (500g)"}]}]
     */



    private String status_code;
    private List<StoreListBean> store_list;

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public List<StoreListBean> getStore_list() {
        return store_list;
    }

    public void setStore_list(List<StoreListBean> store_list) {
        this.store_list = store_list;
    }

    public static class StoreListBean {
        private List<ProductsBean> products;

        public List<ProductsBean> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsBean> products) {
            this.products = products;
        }

        public static class ProductsBean {



            private String CONTENT;
            private String ID;
            private String IMAGES;
            private int mQuantity;
            private String Price;
            private String Title;

            public String getCONTENT() {
                return CONTENT;
            }

            public void setCONTENT(String CONTENT) {
                this.CONTENT = CONTENT;
            }

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getIMAGES() {
                return IMAGES;
            }

            public void setIMAGES(String IMAGES) {
                this.IMAGES = IMAGES;
            }

            public int getMQuantity() {
                return mQuantity;
            }

            public void setMQuantity(int mQuantity) {
                this.mQuantity = mQuantity;
            }

            public String getPrice() {
                return Price;
            }

            public void setPrice(String Price) {
                this.Price = Price;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }
        }
    }
}
