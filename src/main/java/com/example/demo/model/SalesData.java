package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "shopifyOrders")
public class SalesData {
    @Id
    private String id;
    private String email;
    private String gateway;
    private String total_price;
    private String subtotal_price;
    private Integer total_weight;
    private String total_tax;
    private Boolean taxes_included;
    private String currency;
    private String financial_status;
    private Boolean confirmed;
    private String processing_method;
    private String presentment_currency;
    private TotalLineItemsPriceSet total_line_items_price_set;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getSubtotal_price() {
        return subtotal_price;
    }

    public void setSubtotal_price(String subtotal_price) {
        this.subtotal_price = subtotal_price;
    }

    public Integer getTotal_weight() {
        return total_weight;
    }

    public void setTotal_weight(Integer total_weight) {
        this.total_weight = total_weight;
    }

    public String getTotal_tax() {
        return total_tax;
    }

    public void setTotal_tax(String total_tax) {
        this.total_tax = total_tax;
    }

    public Boolean getTaxes_included() {
        return taxes_included;
    }

    public void setTaxes_included(Boolean taxes_included) {
        this.taxes_included = taxes_included;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFinancial_status() {
        return financial_status;
    }

    public void setFinancial_status(String financial_status) {
        this.financial_status = financial_status;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getProcessing_method() {
        return processing_method;
    }

    public void setProcessing_method(String processing_method) {
        this.processing_method = processing_method;
    }

    public String getPresentment_currency() {
        return presentment_currency;
    }

    public void setPresentment_currency(String presentment_currency) {
        this.presentment_currency = presentment_currency;
    }

    public TotalLineItemsPriceSet getTotal_line_items_price_set() {
        return total_line_items_price_set;
    }

    public void setTotal_line_items_price_set(TotalLineItemsPriceSet total_line_items_price_set) {
        this.total_line_items_price_set = total_line_items_price_set;
    }

    // Constructors
    public SalesData() {
    }

    public SalesData(String id, String email, String gateway, String total_price, String subtotal_price, Integer total_weight, 
                     String total_tax, Boolean taxes_included, String currency, String financial_status, Boolean confirmed, 
                     String processing_method, String presentment_currency, TotalLineItemsPriceSet total_line_items_price_set) {
        this.id = id;
        this.email = email;
        this.gateway = gateway;
        this.total_price = total_price;
        this.subtotal_price = subtotal_price;
        this.total_weight = total_weight;
        this.total_tax = total_tax;
        this.taxes_included = taxes_included;
        this.currency = currency;
        this.financial_status = financial_status;
        this.confirmed = confirmed;
        this.processing_method = processing_method;
        this.presentment_currency = presentment_currency;
        this.total_line_items_price_set = total_line_items_price_set;
    }

    public static class TotalLineItemsPriceSet {
        private ShopMoney shop_money;
        private PresentmentMoney presentment_money;

        // Getters and Setters
        public ShopMoney getShop_money() {
            return shop_money;
        }

        public void setShop_money(ShopMoney shop_money) {
            this.shop_money = shop_money;
        }

        public PresentmentMoney getPresentment_money() {
            return presentment_money;
        }

        public void setPresentment_money(PresentmentMoney presentment_money) {
            this.presentment_money = presentment_money;
        }

        // Constructors
        public TotalLineItemsPriceSet() {
        }

        public TotalLineItemsPriceSet(ShopMoney shop_money, PresentmentMoney presentment_money) {
            this.shop_money = shop_money;
            this.presentment_money = presentment_money;
        }

        public static class ShopMoney {
            private String amount;
            private String currency_code;

            // Getters and Setters
            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getCurrency_code() {
                return currency_code;
            }

            public void setCurrency_code(String currency_code) {
                this.currency_code = currency_code;
            }

            // Constructors
            public ShopMoney() {
            }

            public ShopMoney(String amount, String currency_code) {
                this.amount = amount;
                this.currency_code = currency_code;
            }
        }

        public static class PresentmentMoney {
            private String amount;
            private String currency_code;

            // Getters and Setters
            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getCurrency_code() {
                return currency_code;
            }

            public void setCurrency_code(String currency_code) {
                this.currency_code = currency_code;
            }

            // Constructors
            public PresentmentMoney() {
            }

            public PresentmentMoney(String amount, String currency_code) {
                this.amount = amount;
                this.currency_code = currency_code;
            }
        }
    }
}
