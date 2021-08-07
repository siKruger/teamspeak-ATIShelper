package converter;

public enum Commands {
    METAR("Type !METAR and an Airport ICAO- or IATA-Code to get the current METAR report."),
    ATIS("Type !ATIS and an Airport ICAO- or IATA-Code to get the current ATIS report."),
    TAF("Type !TAF and an Airport ICAO- or IATA-Code to get the current TAF report."),
    HELP(""), // fetch every name and print it
    KG2LB("To convert kilogram to pound type !KG2LB and a value in kilogram."),
    LB2KG("To convert pound to kilogram type !LB2KG and a value in pound."),
    KMH2KN("To convert kilometer per hour to knots type !KMH2KN and a value in kilometer per hour."),
    KN2KMH("To convert knots to kilometer per hour type !KN2KMH and a value in knots."),
    KM2NM("To convert kilometer to nautical mile type !KM2NM and a value in kilometer."),
    NM2KM("To convert nautical mile to kilometer type !NM2KM and a value in nautical miles."),
    M2FT("To convert meter to feet type !M2FT and a value in meter."),
    FT2M("To convert feet to meter type !FT2M and a value in feet."),
    C2F("To convert celsius to fahrenheit type !C2F and a value in celsius."),
    F2C("To convert fahrenheit to celsius type !F2C and a value in fahrenheit."),
    HPA2INHG("To convert hectopascal to inch of mercury type !HPA2INHG and a value in hectopascal."),
    INHG2HPA("To convert inch of mercury to hectopascal type !INHG2HPA and a value in inch of mercury."),
    H2MIN("To convert hour to minute type !H2MIN and a value in hour."),
    MIN2H("To convert minute to hour type !MIN2H and a value in minute."),
    ISA("To calculate the ISA temperature type !ISA and a altitude in thousands of feet."),
    ENDURANCE("To calculate the endurance type !ENDURANCE fuel flow and fuel."),
    FUEL("To calculate the required fuel type !FUEL fuel flow and time."),
    FUELFLOW("To calculate the fuel flow type !FUELFLOW fuel and time."),
    TIME("To calculate the time for a distance type !TIME speed and distance."),
    DISTANCE("To calculate the distance in a given time type !DISTANCE speed and time."),
    SPEED("To calculate the speed type !SPEED distance and time."),
    PRESSUREALT("To calculate the pressure altitude type !PRESSUREALT pressure in hPa and indicated altitude."),
    DELTATEMP("To calculate the denity altitude type !DENSITYALT the temperature difference and pressure altitude."),
    DENSITYALTITUDE("To calculate the denity altitude type !DENSITYALT the temperature difference and pressure altitude."),
    TRUEALTITUDE("To calculate the true altitude type !TRUEALTITUDE pressure in hPa, the indicated altitude and the static air temperature."),
    TAS("To calculate the true airspeed type !TAS speed and altitude.");


    private final String helpText;

    private Commands(String helpText) {
        this.helpText = helpText;
    }

    public String getHelpText(String name) { // I have no idea if this is necessary, but it returns the helpText
        return this.helpText;
    }
}
