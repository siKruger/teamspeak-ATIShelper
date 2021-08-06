package bot;

public class FLC {
    /**
     * Class for computation purposes like airspeed, height, FFL and units
     * <p>
     * ToDo
     * - Print two decimal places with "%.2f"
     * - move into package converter plz
     */

    // units
    // conversions
    public static double kg2lb(double kg) {
        return (kg * 2.205);
    }

    public static double lb2kg(double lb) {
        return (lb / 2.205);
    }

    public static double kmh2kn(double kmh) {
        return (kmh / 1.852);
    }

    public static double kn2kmh(double kn) {
        return (kn * 1.852);
    }

    public static double km2nm(double km) {
        return (km / 1.852);
    }

    public static double nm2km(double nm) {
        return (nm * 1.852);
    }

    public static double m2ft(double meter) {
        return (meter * 3.281);
    }

    public static double ft2m(double feet) {
        return (feet / 3.281);
    }

    public static double c2f(double celsius) {
        return (((celsius * (9.0 / 5.0)) + 32));
    }

    public static double f2c(double fahrenheit) {
        return (((fahrenheit - 32) * (5.0 / 9.0)));
    }

    public static double hPa2inHg(double hPa) {
        return (hPa / 33.86388);
    }

    public static double inHg2hPa(double inHg) {
        return (inHg * 33.86388);
    }

    public static double h2min(double h) {
        return (h * 60);
    }

    public static double min2h(double min) {
        return (min / 60);
    }

    // temperature
    public static double ISA(double alt) {
        return (15 + (-6.5 * (alt / 3281)));
    } // alt in feet

    // multi value calculations
    // fuel consumption
    public static double endurance(double ffl, double fuel) {
        return (fuel / ffl);
    }

    public static double reqFuel(double ffl, double time) {
        return (ffl * time);
    }

    public static double ffl(double fuel, double time) { // returns ffl
        return (fuel * time);
    }

    // time and distance
    public static double timeForDist(double speed, double dist) {
        return (h2min((dist / speed)));
    }

    public static double distInTime(double speed, double time) {
        return (speed / time);
    }

    public static double speedByTimeForDist(double dist, double time) {
        return (dist / time);
    }

    // altitudes
    public static double pressureAlt(double hPa, double alt) {
        return ((((1013 - hPa) * 30) + alt));
    }

    public static double deltaTemp(double pAlt, double temp) {
        return ((temp - (15 - ((pAlt / 1000) * 2))));
    }

    public static double densityAlt(double dt, double pAlt) {
        return ((dt * 120 + pAlt));
    }

    public static double trueAlt(double hPa, double alt, double temp) {
        return (densityAlt(deltaTemp(pressureAlt(hPa, alt), temp), pressureAlt(hPa, alt)));
    }

    // speed
    public static double TAS(double speed, double alt) {
        return ((((((alt / 1000) * 2) / 100) + 1) * speed));
    }

}
