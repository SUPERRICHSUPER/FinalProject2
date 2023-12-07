package algonquin.cst2335.finalproject.sunrise;

public class SunIfo {
    private String date;
    private String sunrise;
    private String sunset;
    private String first_light;
    private String last_light;
    private String dawn;
    private String dusk;
    private String solar_noon;
    private String golden_hour;
    private String day_length;

    private String timezone;
    private String utc_offset;

    public SunIfo(String date, String sunrise, String sunset, String first_light, String last_light, String dawn, String dusk, String solar_noon, String golden_hour, String day_length, String timezone, String utc_offset) {
        this.date = date;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.first_light = first_light;
        this.last_light = last_light;
        this.dawn = dawn;
        this.dusk = dusk;
        this.solar_noon = solar_noon;
        this.golden_hour = golden_hour;
        this.day_length = day_length;
        this.timezone = timezone;
        this.utc_offset = utc_offset;
    }

    @Override
    public String toString() {
        return "SunIfo{" +
                "date='" + date + '\'' +
                ", sunrise='" + sunrise + '\'' +
                ", sunset='" + sunset + '\'' +
                ", first_light='" + first_light + '\'' +
                ", last_light='" + last_light + '\'' +
                ", dawn='" + dawn + '\'' +
                ", dusk='" + dusk + '\'' +
                ", solar_noon='" + solar_noon + '\'' +
                ", golden_hour='" + golden_hour + '\'' +
                ", day_length='" + day_length + '\'' +
                ", timezone='" + timezone + '\'' +
                ", utc_offset='" + utc_offset + '\'' +
                '}';
    }
}
