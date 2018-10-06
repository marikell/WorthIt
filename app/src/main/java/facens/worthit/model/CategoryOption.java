package facens.worthit.model;

public class CategoryOption {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private float rating;

    public CategoryOption(String id, String name, float rating){
        this.id = id;
        this.name = name;
        this.rating = rating;
    }



}
