package com.jerry.jingdong.entity;

import java.util.List;

/**
 * Created by MiKoKatie on 2016/3/5 14:30.
 *
 * @ 描述  ${TODO}
 * @ 版本  $Rev$
 * @ 更新者  $Author$
 * @ 更新时间  $Date$
 */
public class CartNewBean {

    public ProductEntity product;

    public String        response;

    public ProductEntity getProduct() {
        return product;
    }

    public String getResponse() {
        return response;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public static class ProductEntity {
        public boolean isAvailable() {
            return available;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public int getId() {
            return id;
        }

        public String getInventoryArea() {
            return inventoryArea;
        }

        public int getLeftTime() {
            return leftTime;
        }

        public int getScore() {
            return score;
        }

        public List<String> getBigPic() {
            return bigPic;
        }

        public String getName() {
            return name;
        }

        public int getBuyLimit() {
            return buyLimit;
        }



        public boolean      available;
        public int          buyLimit;
        public int          commentCount;
        public int          id;
        public String       inventoryArea;
        public int          leftTime;
        public int          limitPrice;
        public int          marketPrice;
        public String       name;
        public int          price;
        public int          score;
        public List<String> bigPic;
        public List<String> pics;



        public int getMarketPrice() {
            return marketPrice;
        }

        public int getPrice() {
            return price;
        }

        public int getLimitPrice() {
            return limitPrice;
        }

        public List<String> getPics() {
            return pics;
        }

        public List<ProductBeanEntity> productProperty;

        public List<ProductBeanEntity> getProductProperty() {
            return productProperty;
        }

        public static class ProductBeanEntity {
            public int getId() {
                return id;
            }

            public String getK() {
                return k;
            }

            public String getV() {
                return v;
            }

            public int    id;
            public String k;
            public String v;
        }
    }
}
