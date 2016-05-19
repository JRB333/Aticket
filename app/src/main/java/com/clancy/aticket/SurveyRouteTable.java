package com.clancy.aticket;

public class SurveyRouteTable {

    int _id;
    String _route;
    String _street;
    String _meter;
    Integer _sequence;
    String _passData;
    String _last_update;

    // constructors
    public SurveyRouteTable() {
    }

    // construct
    public SurveyRouteTable(String route, String street, String meter, Integer sequence) {
        this._route = route;
        this._street = street;
        this._meter = meter;
        this._sequence = sequence;
    }

    // construct
    public SurveyRouteTable(int id, String route, String street, String meter, Integer sequence) {
        this._id = id;
        this._route = route;
        this._street = street;
        this._meter = meter;
        this._sequence = sequence;
    }

    // construct
    public SurveyRouteTable(int id, String route, String street, String meter, Integer sequence, String passdata) {
        this._id = id;
        this._route = route;
        this._street = street;
        this._meter = meter;
        this._sequence = sequence;
        this._passData = passdata;
    }

    // setters
    public void setId(int id) { this._id = id; }

    public void setRoute(String route) { this._route = route;  }

    public void setStreet(String street) { this._street = street; }

    public void setMeter(String meter) { this._meter = meter; }

    public void setSequence(Integer sequence) { this._sequence = sequence; }

    public void setPassData(String passdata) { this._passData = passdata; }

    public void setLastUpdate(String last_update) {
        this._last_update = last_update;
    }

    // getters
    public long getId() { return this._id; }

    public String getRoute() { return this._route; }

    public String getStreet() { return this._street; }

    public String getMeter() {
        return this._meter;
    }

    public Integer getSequence() {
        return this._sequence;
    }

    public String getPassData() {
        return this._passData;
    }

    public String getLastUpdate() {
        return this._last_update;
    }
}
