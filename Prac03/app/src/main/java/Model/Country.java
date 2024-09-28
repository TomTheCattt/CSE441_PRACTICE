package Model;

public class Country {
    private String countryName, countryCapital, countryPopulation, countryArea, countryDensity, worldShare;
    private int countryFlag;

    public Country(String countryName, String countryCapital, String countryPopulation, String countryArea, String countryDensity, String worldShare, int countryFlag) {
        this.countryName = countryName;
        this.countryCapital = countryCapital;
        this.countryPopulation = countryPopulation;
        this.countryArea = countryArea;
        this.countryDensity = countryDensity;
        this.worldShare = worldShare;
        this.countryFlag = countryFlag;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCapital() {
        return countryCapital;
    }

    public void setCountryCapital(String countryCapital) {
        this.countryCapital = countryCapital;
    }

    public String getCountryPopulation() {
        return countryPopulation;
    }

    public void setCountryPopulation(String countryPopulation) {
        this.countryPopulation = countryPopulation;
    }

    public String getCountryArea() {
        return countryArea;
    }

    public void setCountryArea(String countryArea) {
        this.countryArea = countryArea;
    }

    public String getCountryDensity() {
        return countryDensity;
    }

    public void setCountryDensity(String countryDensity) {
        this.countryDensity = countryDensity;
    }

    public String getWorldShare() {
        return worldShare;
    }

    public void setWorldShare(String worldShare) {
        this.worldShare = worldShare;
    }

    public int getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(int countryFlag) {
        this.countryFlag = countryFlag;
    }
}
