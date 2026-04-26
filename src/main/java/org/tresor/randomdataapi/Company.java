package org.tresor.randomdataapi;

import org.bson.Document;

import java.util.List;

public class Company {

    private String name;
    private String permalink;
    private String crunchbase_url;
    private String homepage_url;
    private String blog_url;
    private String twitter_username;
    private String category_code;
    private int number_of_employees;
    private int founded_year;
    private int founded_month;
    private int founded_day;
    private String description;
    private String created_at;
    private String overview;
    private List<Document> products;
    private List<Document> relationships;
    private List<Document> competitions;
    private List<Document> providership;
    private String total_money_raise;
    private List<Document> investments;
    private List<Document> offices;
    private List<Document> milestones;

    public Company(String name, String permalink, String crunchbase_url, String homepage_url,
                   String blog_url, String twitter_username, String category_code,
                   int number_of_employees, int founded_year, int founded_month, int founded_day,
                   String description, String created_at, String overview, List<Document> products,
                   List<Document> relationships, List<Document> competitions, List<Document> providership,
                   String total_money_raise, List<Document> investments, List<Document> offices,
                   List<Document> milestones) {

        this.name = name;
        this.permalink = permalink;
        this.crunchbase_url = crunchbase_url;
        this.homepage_url = homepage_url;
        this.blog_url = blog_url;
        this.twitter_username = twitter_username;
        this.category_code = category_code;
        this.number_of_employees = number_of_employees;
        this.founded_year = founded_year;
        this.founded_month = founded_month;
        this.founded_day = founded_day;
        this.description = description;
        this.created_at = created_at;
        this.overview = overview;
        this.products = products;
        this.relationships = relationships;
        this.competitions = competitions;
        this.providership = providership;
        this.total_money_raise = total_money_raise;
        this.investments = investments;
        this.offices = offices;
        this.milestones = milestones;
    }

    public Company(){}

    public String getName() {
        return name;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getCrunchbaseUrl() {
        return crunchbase_url;
    }

    public String getHomepageUrl() {
        return homepage_url;
    }

    public String getBlogUrl() {
        return blog_url;
    }

    public String getTwitterUsername() {
        return twitter_username;
    }

    public String getCategoryCode() {
        return category_code;
    }

    public int getNumberOfEmployees() {
        return number_of_employees;
    }

    public int getFoundedYear() {
        return founded_year;
    }

    public int getFoundedMonth() {
        return founded_month;
    }

    public int getFoundedDay() {
        return founded_day;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public String getOverview() {
        return overview;
    }

    public List<Document> getProducts() {
        return products;
    }

    public List<Document> getRelationships() {
        return relationships;
    }

    public List<Document> getCompetitions() {
        return competitions;
    }

    public List<Document> getProvidership() {
        return providership;
    }

    public String getTotalMoneyRaise() {
        return total_money_raise;
    }

    public List<Document> getInvestments() {
        return investments;
    }

    public List<Document> getOffices() {
        return offices;
    }

    public List<Document> getMilestones() {
        return milestones;
    }

}
