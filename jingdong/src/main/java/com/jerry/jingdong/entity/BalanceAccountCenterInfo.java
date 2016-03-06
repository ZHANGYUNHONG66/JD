package com.jerry.jingdong.entity;

import java.util.List;

/**
 * 项目名:    JingDong
 * 包名:      com.jerry.jingdong.entity
 * 文件名:    BalanceAccountCenterInfo
 * 创建者:    任洛仟
 * 创建时间:  2016/03/05 下午 2:51
 * 描述:      TODO
 */
public class BalanceAccountCenterInfo {
    public CheckoutAddup checkoutAddup;//	Object
    public List<String> checkoutProm;//Array
    public List<DelveryList> deliveryList;//Array
    public List<PaymentList> paymentList;// Array
    public List<ProductList> productList;// Array
   public String response;// checkOut

    public class CheckoutAddup {
        public int freight;//10
        public int totalCount;//5
        public int totalPoint;//30
        public int totalPrice;//450
    }

    public class DelveryList{
       public String des;//	双休日及公众假期送货
        public int type;//	2
    }

    public class PaymentList{
        public String des;//支付宝
        public int type;//1
    }

    public class ProductList{
        public int prodNum;//3
        public Product product;//Object
    }
    public class Product{
       public int id;//	1
        public String name;//韩版时尚蕾丝裙
        public String pic;///images/product/detail/c3.jpg
        public long price;//	350
        public List<ProductProperty> productProperty;	//Array
    }

    public  class ProductProperty{
        public int id;//	1
        public String k	;//    颜色
        public String v ;//	红色
    }
}
