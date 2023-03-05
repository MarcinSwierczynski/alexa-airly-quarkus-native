package net.swierczynski.alexaairly;

class AirQuality {

  private final String description;
  private final double caqi;
  private final double pm25Percent;

  AirQuality(String description, double caqi, double pm25Percent) {
    this.description = description;
    this.caqi = caqi;
    this.pm25Percent = pm25Percent;
  }

  String getDescription() {
    return description;
  }

  double getCaqi() {
    return caqi;
  }

  double getPm25Percent() {
    return pm25Percent;
  }
}
