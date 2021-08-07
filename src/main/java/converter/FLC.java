package converter;

public class FLC {

    /**
     * TODO
     * - all method into one
     * - UnitConverter
     * - you are bad
     */


    // units
    // conversions
    public static String kg2lb(String kg) {
        return castTo2TwoDecimals(String.valueOf((Double.parseDouble(kg) * 2.205)));
    }

    public static String lb2kg(String lb) {
        return castTo2TwoDecimals(String.valueOf((Double.parseDouble(lb) / 2.205)));
    }

    public static String kmh2kn(String kmh) {
        return castTo2TwoDecimals(String.valueOf((Double.parseDouble(kmh) / 1.852)));
    }

    public static String kn2kmh(String kn) {
        return castTo2TwoDecimals(String.valueOf((Double.parseDouble(kn) * 1.852)));
    }

    public static String km2nm(String km) {
        return castTo2TwoDecimals(String.valueOf((Double.parseDouble(km) / 1.852)));
    }

    public static String nm2km(String nm) {
        return castTo2TwoDecimals(String.valueOf((Double.parseDouble(nm) * 1.852)));
    }

    public static String m2ft(String meter) {
        return castTo2TwoDecimals(String.valueOf((Double.parseDouble(meter) * 3.281)));
    }

    public static String ft2m(String feet) {
        return castTo2TwoDecimals(String.valueOf((Double.parseDouble(feet) / 3.281)));
    }

    public static String c2f(String celsius) {
        return castTo2TwoDecimals(String.valueOf((((Double.parseDouble(celsius) * (9.0 / 5.0)) + 32))));
    }

    public static String f2c(String fahrenheit) {
        return castTo2TwoDecimals(String.valueOf((((Double.parseDouble(fahrenheit) - 32) * (5.0 / 9.0)))));
    }

    public static String hPa2inHg(String hPa) {
        return castTo2TwoDecimals(String.valueOf((Double.parseDouble(hPa) / 33.86388)));
    }

    public static String inHg2hPa(String inHg) {
        return castTo2TwoDecimals(String.valueOf((Double.parseDouble(inHg) * 33.86388)));
    }

    public static String h2min(String h) {
        return castTo2TwoDecimals(String.valueOf((Double.parseDouble(h) * 60)));
    }

    public static String min2h(String min) {
        return castTo2TwoDecimals(String.valueOf((Double.parseDouble(min) / 60)));
    }

    // temperature
    public static String ISA(String alt) {
        return castTo2TwoDecimals(String.valueOf((15 + (-6.5 * (Double.parseDouble(alt) / 3281)))));
    } // alt in feet

    // multi value calculations
    // fuel consumption
    public static String endurance(String ffl, String fuel) {
        return castTo2TwoDecimals(String.valueOf((Double.parseDouble(fuel) / Double.parseDouble(ffl))));
    }

    public static String reqFuel(String ffl, String time) {
        return castTo2TwoDecimals(String.valueOf((Double.parseDouble(ffl) * Double.parseDouble(time))));
    }

    public static String ffl(String fuel, String time) { // returns ffl
        return castTo2TwoDecimals(String.valueOf((Double.parseDouble(fuel) * Double.parseDouble(time))));
    }

    // time and distance
    public static String timeForDist(String speed, String dist) {
        return castTo2TwoDecimals(h2min(String.valueOf(Double.parseDouble(dist) / Double.parseDouble(speed))));
    }

    public static String distInTime(String speed, String time) {
        return castTo2TwoDecimals(String.valueOf((Double.parseDouble(speed) / Double.parseDouble(time))));
    }

    public static String speedByTimeForDist(String dist, String time) {
        return castTo2TwoDecimals(String.valueOf((Double.parseDouble(dist) / Double.parseDouble(time))));
    }

    // altitudes
    public static String pressureAlt(String hPa, String alt) {
        return castTo2TwoDecimals(((1013 - Double.parseDouble(hPa)) * 30) + alt);
    }

    public static String deltaTemp(String pAlt, String temp) {
        return castTo2TwoDecimals(String.valueOf(((Double.parseDouble(temp) - (15 - ((Double.parseDouble(pAlt) / 1000) * 2))))));
    }

    public static String densityAlt(String dt, String pAlt) {
        return castTo2TwoDecimals(Double.parseDouble(dt) * 120 + pAlt);
    }

    public static String trueAlt(String hPa, String alt, String temp) {
        double pAlt = Double.parseDouble(pressureAlt(hPa, alt));

        double dt = Double.parseDouble(deltaTemp(String.valueOf(pAlt), temp));

        return castTo2TwoDecimals(densityAlt(String.valueOf(dt), String.valueOf(pAlt)));
    }


    public static String TAS(String speed, String alt) {
        return castTo2TwoDecimals(String.valueOf(((((((Double.parseDouble(alt) / 1000) * 2) / 100) + 1) * Double.parseDouble(speed)))));
    }

    private static String castTo2TwoDecimals(String value) {
        return String.valueOf(Math.round(Double.parseDouble(value) * 100.0) / 100.0);
    }

}
