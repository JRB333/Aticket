package com.clancy.aticket;

public class SurveyPassTable {

    int _id;
    String _route;
    String _passcount;
    String _laststreet;
    String _lastmeter;

    // constructors
    public SurveyPassTable() {
    }

    // construct
    public SurveyPassTable(String route, String street, String meter) {
        this._route = route;
        this._laststreet = street;
        this._lastmeter = meter;
    }

    // construct
    public SurveyPassTable(int id, String route, String street, String meter) {
        this._id = id;
        this._route = route;
        this._laststreet = street;
        this._lastmeter = meter;
    }

    // construct
    public SurveyPassTable(int id, String route, String street, String meter, String passcount) {
        this._id = id;
        this._route = route;
        this._passcount = passcount;
        this._laststreet = street;
        this._lastmeter = meter;
    }

    // setters
    public void setId(int id) { this._id = id; }

    public void setRoute(String route) { this._route = route;  }

    public void setStreet(String street) { this._laststreet = street; }

    public void setMeter(String meter) { this._lastmeter = meter; }

    public void setPassCount(String passcount) {
        this._passcount = passcount;
    }


    // getters
    public long getId() { return this._id; }

    public String getRoute() { return this._route; }

    public String getStreet() {
        return this._laststreet;
    }

    public String getMeter() {
        return this._lastmeter;
    }
}
