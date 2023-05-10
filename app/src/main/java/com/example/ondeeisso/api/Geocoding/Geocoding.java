package com.example.ondeeisso.api.Geocoding;

import java.util.ArrayList;

public class Geocoding {
    public ArrayList<Result> results;
    public String status;
}
class AddressComponent{
    public String long_name;
    public String short_name;
    public ArrayList<String> types;
}

class Northeast{
    public double lat;
    public double lng;
}

 class PlusCode{
    public String compound_code;
    public String global_code;
}

class Southwest{
    public double lat;
    public double lng;
}
 class Viewport{
    public Northeast northeast;
    public Southwest southwest;
}
