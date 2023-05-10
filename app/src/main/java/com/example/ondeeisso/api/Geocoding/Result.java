package com.example.ondeeisso.api.Geocoding;

import java.util.ArrayList;

public class Result {
    public ArrayList<AddressComponent> address_components;
    public String formatted_address;
    public Geometry geometry;
    public String place_id;
    public PlusCode plus_code;
    public ArrayList<String> types;
}
