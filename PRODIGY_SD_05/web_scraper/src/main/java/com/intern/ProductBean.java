package com.intern;

public class ProductBean {          //Contains Getters and Setters
    private String url;
    private String rating;
    private String name;
    private String price;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    /*@Override                             //Method Used to print the data
    public String toString() {
        return "{ \"url\":\"" + url + "\", "
                + " \"rating\": \"" + rating+ "\", "
                + "\"name\":\"" + name + "\", "
                + "\"price\": \"" + price + "\" }";
    }*/
}
