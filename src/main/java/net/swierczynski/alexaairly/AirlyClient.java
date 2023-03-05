package net.swierczynski.alexaairly;

import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

class AirlyClient {

  private static final String ENDPOINT = "https://airapi.airly.eu/v2/measurements/nearest";
  private static final String API_KEY = "<your api key>";
  private static final String MAX_DISTANCE_KM = "1";

  AirQuality fetchAirQuality(String latitude, String longitude) {
    JsonNode response = getBody(latitude, longitude);
    JSONObject current = getCurrent(response);

    JSONObject indexes = current
        .getJSONArray("indexes")
        .getJSONObject(0);

    Iterator<Object> standards = current
        .getJSONArray("standards")
        .iterator();

    JSONObject pm25 = StreamSupport
        .stream(Spliterators.spliteratorUnknownSize(standards, Spliterator.ORDERED), false)
        .map(jsonObject -> ((JSONObject) jsonObject))
        .filter(standard -> standard.getString("pollutant").equalsIgnoreCase("PM25"))
        .findFirst()
        .orElseThrow();

    return new AirQuality(
        indexes.getString("description"),
        indexes.getDouble("value"),
        pm25.getDouble("percent")
    );
  }

  private JsonNode getBody(String latitude, String longitude) {
    return Unirest.get(ENDPOINT)
        .header("apikey", API_KEY)
        .queryString("lat", latitude)
        .queryString("lng", longitude)
        .queryString("maxDistanceKM", MAX_DISTANCE_KM)
        .asJson()
        .getBody();
  }

  private JSONObject getCurrent(JsonNode response) {
    return response.getObject()
        .getJSONObject("current");
  }
}
