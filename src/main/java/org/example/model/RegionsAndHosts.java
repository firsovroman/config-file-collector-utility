package org.example.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;


@Component
public class RegionsAndHosts {

    public HashMap<String, String> map;

    public RegionsAndHosts() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        this.map = mapper.readValue(new URL("file:src/main/resources/regions.json"), HashMap.class);
    }

    public HashMap<String, String> getMap() {
        return map;
    }

    public void setMap(HashMap<String, String> map) {
        this.map = map;
    }
}
