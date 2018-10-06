package facens.worthit.model;

import android.media.Image;

import facens.worthit.ImageType;

public class UserOption {

    private String id;

    public UserOption(String id, String option, ImageType imageType){
        this.id = id;
        this.option = option;
        this.imageType = imageType;
    }

    public ImageType getImageType() {
        return imageType;
    }

    public void setImageType(ImageType imageType) {
        this.imageType = imageType;
    }

    private ImageType imageType;

    public UserOption(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    private String option;



}
