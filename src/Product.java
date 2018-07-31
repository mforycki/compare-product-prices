
/**
 * Product
 * 
 * Product and Sale information
 */
public class Product
{
    private String productName;
    private String retailer;
    private double regularPrice;
    private double salePrice;
    private String url;
    
    public Product(String pN, String r, double rP, double sP, String u)
    {
        productName = pN;
        retailer = r;
        regularPrice = rP;
        salePrice = sP;
        url = u;
    }
    
    public String toString(){
        return "Product: " + productName +
            "\nRetailer" + retailer +
            "\nRegular Price: " + regularPrice +
            "\nSale Price:" + salePrice+
            "\nYou save: "+ percentOff() + "%" +
            "\nDollars off: "+ dollarsOff();
    }
    
    public String getName(){
        return productName;
    }
    
    public String getRetailer(){
        return retailer;
    }
    
    public double getRegularPrice(){
        return regularPrice;
    }
    
    public double getSalePrice(){
        return salePrice;
    }
    
    public String getUrl(){
        return url;
    }
    
    public double amountSaved(){
        return regularPrice-salePrice;
    }
    
    public int percentOff(){
        int result;
        if (salePrice == 0){
            result = 0;
        }else{
            result = (int)((regularPrice-salePrice)/regularPrice*100);
        }
        return result;
    }
    
    public double dollarsOff(){
        return regularPrice-salePrice;
    }
}
