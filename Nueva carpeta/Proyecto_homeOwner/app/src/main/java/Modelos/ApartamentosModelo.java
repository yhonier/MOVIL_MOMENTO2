package Modelos;

public class ApartamentosModelo {

    private String country;
    private String city;
    private String adress;
    private String bedrooms;
    private String priceNight;
    private String description;
    private String owner;
    private String state;

    private ApartamentosModelo (){ }

    public ApartamentosModelo(String country, String city, String adress, String bedrooms, String price_night,
                              String description, String owner, String state) {
        this.country = country;
        this.city = city;
        this.adress = adress;
        this.bedrooms = bedrooms;
        this.priceNight = price_night;
        this.description = description;
        this.owner = owner;
        this.state = state;
    }



    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

    public String getPriceNight() {
        return priceNight;
    }

    public void setPrice_night(String price_night) {
        this.priceNight = price_night;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }






}
