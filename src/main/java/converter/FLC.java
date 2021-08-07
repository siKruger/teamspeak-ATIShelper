package converter;

public class FLC {

    /**
     * TODO
     *  - all method into one
     */


    // units
    // conversions
    private static double kg2lb(double kg) {
        return (kg * 2.205);
    }

    private static double lb2kg(double lb) {
        return (lb / 2.205);
    }

    private static double kmh2kn(double kmh) {
        return (kmh / 1.852);
    }

    private static double kn2kmh(double kn) {
        return (kn * 1.852);
    }

    private static double km2nm(double km) {
        return (km / 1.852);
    }

    private static double nm2km(double nm) {
        return (nm * 1.852);
    }

    private static double m2ft(double meter) {
        return (meter * 3.281);
    }

    private static double ft2m(double feet) {
        return (feet / 3.281);
    }

    private static double c2f(double celsius) {
        return (((celsius * (9.0 / 5.0)) + 32));
    }

    private static double f2c(double fahrenheit) {
        return (((fahrenheit - 32) * (5.0 / 9.0)));
    }

    private static double hPa2inHg(double hPa) {
        return (hPa / 33.86388);
    }

    private static double inHg2hPa(double inHg) {
        return (inHg * 33.86388);
    }

    private static double h2min(double h) {
        return (h * 60);
    }

    private static double min2h(double min) {
        return (min / 60);
    }

    // temperature
    private static double ISA(double alt) {
        return (15 + (-6.5 * (alt / 3281)));
    } // alt in feet

    // multi value calculations
    // fuel consumption
    private static double endurance(double ffl, double fuel) {
        return (fuel / ffl);
    }

    private static double reqFuel(double ffl, double time) {
        return (ffl * time);
    }

    private static double ffl(double fuel, double time) { // returns ffl
        return (fuel * time);
    }

    // time and distance
    private static double timeForDist(double speed, double dist) {
        return (h2min((dist / speed)));
    }

    private static double distInTime(double speed, double time) {
        return (speed / time);
    }

    private static double speedByTimeForDist(double dist, double time) {
        return (dist / time);
    }

    // altitudes
    private static double pressureAlt(double hPa, double alt) {
        return ((((1013 - hPa) * 30) + alt));
    }

    private static double deltaTemp(double pAlt, double temp) {
        return ((temp - (15 - ((pAlt / 1000) * 2))));
    }

    private static double densityAlt(double dt, double pAlt) {
        return ((dt * 120 + pAlt));
    }

    private static double trueAlt(double hPa, double alt, double temp) {
        return (densityAlt(deltaTemp(pressureAlt(hPa, alt), temp), pressureAlt(hPa, alt)));
    }

    // speed
    private static double TAS(double speed, double alt) {
        return ((((((alt / 1000) * 2) / 100) + 1) * speed));
    }

    private static String castTo2TwoDecimals(String value) {
        return String.valueOf(Math.round(Double.parseDouble(value) * 100.0) / 100.0);
    }



    // calls calculation methods
    public static String calculations(String command, String value1, String value2, String value3) {
        Commands commandToExecute = Commands.valueOf(command);
        if (commandToExecute.name().equals("KG2LB")) {
            return("" + FLC.kg2lb(Double.parseDouble(value1)));
        } else if (commandToExecute.name().equals("LB2KG")) {
            return("" + FLC.lb2kg(Double.parseDouble(value1)));
        } else if (commandToExecute.name().equals("KMH2KN")) {
            return("" + FLC.kmh2kn(Double.parseDouble(value1)));
        } else if (commandToExecute.name().equals("KN2KMH")) {
            return("" + FLC.kn2kmh(Double.parseDouble(value1)));
        } else if (commandToExecute.name().equals("KM2NM")) {
            return("" + FLC.km2nm(Double.parseDouble(value1)));
        } else if (commandToExecute.name().equals("NM2KM")) {
            return("" + FLC.nm2km(Double.parseDouble(value1)));
        } else if (commandToExecute.name().equals("M2FT")) {
            return("" + FLC.m2ft(Double.parseDouble(value1)));
        } else if (commandToExecute.name().equals("FT2M")) {
            return("" + FLC.ft2m(Double.parseDouble(value1)));
        } else if (commandToExecute.name().equals("C2F")) {
            return("" + FLC.c2f(Double.parseDouble(value1)));
        } else if (commandToExecute.name().equals("F2C")) {
            return("" + FLC.f2c(Double.parseDouble(value1)));
        } else if (commandToExecute.name().equals("HPA2INHG")) {
            return("" + FLC.hPa2inHg(Double.parseDouble(value1)));
        } else if (commandToExecute.name().equals("INHG2HPA")) {
            return("" + FLC.inHg2hPa(Double.parseDouble(value1)));
        } else if (commandToExecute.name().equals("H2MIN")) {
            return("" + FLC.h2min(Double.parseDouble(value1)));
        } else if (commandToExecute.name().equals("MIN2H")) {
            return("" + FLC.min2h(Double.parseDouble(value1)));
        } else if (commandToExecute.name().equals("ISA")) {
            return("" + FLC.ISA(Double.parseDouble(value1)));
        } else if (commandToExecute.name().equals("ENDURANCE")) { // can cause problems
            return("" + FLC.endurance(Double.parseDouble(value1), Double.parseDouble(value2)));
        } else if (commandToExecute.name().equals("FUEL")) {
            return("" + FLC.reqFuel(Double.parseDouble(value1), Double.parseDouble(value2)));
        } else if (commandToExecute.name().equals("FUELFLOW")) {
            return("" + FLC.ffl(Double.parseDouble(value1), Double.parseDouble(value2)));
        } else if (commandToExecute.name().equals("TIME")) {
            return("" + FLC.timeForDist(Double.parseDouble(value1), Double.parseDouble(value2)));
        } else if (commandToExecute.name().equals("DISTANCE")) {
            return("" + FLC.distInTime(Double.parseDouble(value1), Double.parseDouble(value2)));
        } else if (commandToExecute.name().equals("SPEED")) {
            return("" + FLC.speedByTimeForDist(Double.parseDouble(value1), Double.parseDouble(value2)));
        } else if (commandToExecute.name().equals("PRESSUREALT")) {
            return("" + FLC.pressureAlt(Double.parseDouble(value1), Double.parseDouble(value2)));
        } else if (commandToExecute.name().equals("DELTATEMP")) {
            return("" + FLC.deltaTemp(Double.parseDouble(value1), Double.parseDouble(value2)));
        } else if (commandToExecute.name().equals("DENSITYALTITUDE")) {
            return("" + FLC.densityAlt((Double.parseDouble(value1)), Double.parseDouble(value2)));
        } else if (commandToExecute.name().equals("TRUEALTITUDE")) {
            return("" + FLC.trueAlt(Double.parseDouble(value1), Double.parseDouble(value2), Double.parseDouble(value3)));
        } else if (commandToExecute.name().equals("TAS")) {
            return("" + FLC.TAS(Double.parseDouble(value1), Double.parseDouble(value2)));
        }
        return "";
    }
}
