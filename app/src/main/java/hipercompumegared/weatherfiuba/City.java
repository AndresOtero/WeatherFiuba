package hipercompumegared.weatherfiuba;

/**
 * Created by german on 8/27/2017.
 */

public class City {
    public City(){

    }
    public City(String name, String id, String country) {
        this.name = name;
        this.id = id;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    private String name;
    private String id;
    private String country;
}
