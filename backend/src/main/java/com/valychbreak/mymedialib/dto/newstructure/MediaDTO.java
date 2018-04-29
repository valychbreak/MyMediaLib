package com.valychbreak.mymedialib.dto.newstructure;

public class MediaDTO extends BasicMediaDTO {
    /*private String imdbId;
    private String homepageLink;
    private Integer runtime;
    private Double populiarity;
    private String backdropImage;
    private String releaseDate;
    private Movie */

    //public Collection belongs_to_collection;
    private Integer budget;
    private String homepage;
    private String imdbId;
    //public List<BaseCompany> production_companies;
    //public List<Country> production_countries;
    private Integer revenue;
    private Integer runtime;
    //public List<SpokenLanguage> spoken_languages;
    //public Status status;
    private String tagline;
    //public AlternativeTitles alternative_titles;
    /*public Changes changes;
    public Keywords keywords;
    public ListResultsPage lists;
    public Images images;
    public Translations translations;
    public Credits credits;
    public ReleaseDatesResults release_dates;
    public MovieResultsPage similar;
    public MovieResultsPage recommendations;
    public ReviewResultsPage reviews;
    public Videos videos;*/

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }
}
