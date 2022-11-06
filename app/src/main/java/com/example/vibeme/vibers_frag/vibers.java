package com.example.vibeme.vibers_frag;

public class vibers {

    private String image , Status , name , id;


    public vibers(String image, String status, String name, String id) {
        this.image = image;
        Status = status;
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public vibers(String image, String status, String name) {
        this.image = image;
        this.Status = status;
        this.name = name;
    }

    public vibers() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
