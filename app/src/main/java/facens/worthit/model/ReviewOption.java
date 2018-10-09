package facens.worthit.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ReviewOption implements Serializable{

    public ArrayList<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<String> attributes) {
        this.attributes = attributes;
    }

    public String getPriceToString(){
        return "R$ " +
                String.valueOf(price);
    }

    public String getProductId() {
        return productId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private String category;

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float[] getRatings() {
        return ratings;
    }

    public void setRatings(float[] ratings) {
        this.ratings = ratings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private ArrayList<String> attributes;

    private String productId;

    private String name;

    private float price;

    private float[] ratings;

    private String image;

}
