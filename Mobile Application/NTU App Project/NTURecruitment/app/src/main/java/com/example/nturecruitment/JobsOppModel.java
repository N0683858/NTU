package com.example.nturecruitment;

public class JobsOppModel {
    private String
            job_title,
            company_name,
            job_desc,
            location;

    public JobsOppModel() {

    }
    public JobsOppModel(String job_title, String company_name, String job_desc, String location) {
        this.job_title = job_title;
        this.company_name = company_name;
        this.job_desc = job_desc;
        this.location = location;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getJob_desc() {
        return job_desc;
    }

    public void setJob_desc(String job_desc) {
        this.job_desc = job_desc;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
