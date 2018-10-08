package facens.worthit.model;

public class ProductOption {

    private String image;

    public ProductOption(String id, String name, String image, float price, float rating){
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.rating = rating;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getImage(){
        return image;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getPriceToString(){
        return "R$ " +
                String.valueOf(price);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    private String id;

    private String name;

    private float rating;

    private float price;


}
