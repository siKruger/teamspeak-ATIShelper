package bot;

@Deprecated
public class CommandList {
    /**
     * A CommandList class for all static values
     * <p>
     * ToDo
     * - Reorganize structure
     */

    public static String command[][] = new String[30][2]; // not dynamic

    private CommandList() {
        // base commands

        command[0][1] = "Type !METAR and an Airport ICAO- or IATA-Code to get the current METAR report.";
        command[1][0] = "ATIS";
        command[1][1] = "Type !ATIS and an Airport ICAO- or IATA-Code to get the current ATIS report.";
        command[2][0] = "TAF";
        command[2][1] = "Type !TAF and an Airport ICAO- or IATA-Code to get the current TAF report.";
        command[3][0] = "HELP";
        // command[3][1] is mandatory to be last

        // FLC unit commands
        command[4][0] = "KG2LB";
        command[4][1] = "To convert kilogram to pound type !KG2LB and a value in kilogram.";
        command[5][0] = "LB2KG";
        command[5][1] = "To convert pound to kilogram type !LB2KG and a value in pound.";
        command[6][0] = "KMH2KN";
        command[6][1] = "To convert kilometer per hour to knots type !KMH2KN and a value in kilometer per hour.";
        command[7][0] = "KN2KMH";
        command[7][1] = "To convert knots to kilometer per hour type !KN2KMH and a value in knots.";
        command[8][0] = "KM2NM";
        command[8][1] = "To convert kilometer to nautical mile type !KM2NM and a value in kilometer.";
        command[9][0] = "NM2KM";
        command[9][1] = "To convert nautical mile to kilometer type !NM2KM and a value in nautical miles.";
        command[10][0] = "M2FT";
        command[10][1] = "To convert meter to feet type !M2FT and a value in meter.";
        command[11][0] = "FT2M";
        command[11][1] = "To convert feet to meter type !FT2M and a value in feet.";
        command[12][0] = "C2F";
        command[12][1] = "To convert celsius to fahrenheit type !C2F and a value in celsius.";
        command[13][0] = "F2C";
        command[13][1] = "To convert fahrenheit to celsius type !F2C and a value in fahrenheit.";
        command[14][0] = "HPA2INHG";
        command[14][1] = "To convert hectopascal to inch of mercury type !HPA2INHG and a value in hectopascal.";
        command[15][0] = "INHG2HPA";
        command[15][1] = "To convert inch of mercury to hectopascal type !INHG2HPA and a value in inch of mercury.";
        command[16][0] = "H2MIN";
        command[16][1] = "To convert hour to minute type !H2MIN and a value in hour.";
        command[17][0] = "MIN2H";
        command[17][1] = "To convert minute to hour type !MIN2H and a value in minute.";
        command[18][0] = "ISA";
        command[18][1] = "To calculate the ISA temperature type !ISA and a altitude in thousands of feet.";

        // FLC multi value commands
        command[19][0] = "ENDURANCE";
        command[19][1] = "To calculate the endurance type !ENDURANCE fuel flow and fuel.";
        command[20][0] = "FUEL";
        command[20][1] = "To calculate the required fuel type !FUEL fuel flow and time.";
        command[21][0] = "FUELFLOW";
        command[21][1] = "To calculate the fuel flow type !FUELFLOW fuel and time.";

        command[22][0] = "TIME";
        command[22][1] = "To calculate the time for a distance type !TIME speed and distance.";
        command[23][0] = "DISTANCE";
        command[23][1] = "To calculate the distance in a given time type !DISTANCE speed and time.";
        command[24][0] = "SPEED";
        command[24][1] = "To calculate the speed type !SPEED distance and time.";

        // maybe we will just use trueAlt
        command[25][0] = "PRESSUREALT"; // maybe wrong
        command[25][1] = "To calculate the pressure altitude type !PRESSUREALT pressure in hPa and indicated altitude.";
        command[26][0] = "DELTATEMP";
        command[26][1] = "To calculate the denity altitude type !DENSITYALT the temperature difference and pressure altitude.";
        command[27][0] = "DENSITYALT";
        command[27][1] = "To calculate the denity altitude type !DENSITYALT the temperature difference and pressure altitude.";

        // trueAlt combines pAlt, dTemp and dAlt - returns the same as dAlt - just use this alone?
        command[28][0] = "TRUEALTITUDE";
        command[28][1] = "To calculate the true altitude type !TRUEALTITUDE pressure in hPa, the indicated altitude and the static air temperature.";

        command[29][0] = "TAS";
        command[29][1] = "To calculate the true airspeed type !TAS speed and altitude.";


        // mandatory be at the bottom else it doesn't know each command
        command[3][1] = "The available commands are:\n";
        for (int x = 0; x < command.length; x++) {
            command[3][1] += "!" + command[x][0] + "\n";
        }
        command[3][1] += "For command specific help type the command without value.\n Note: Every command with a '2' is a conversion between units."; //\nUse the same unit type for calculation for example: Speed in kmh and distance in km.";

    }

    public static void DBcreate() {
        new CommandList();
    }
}
