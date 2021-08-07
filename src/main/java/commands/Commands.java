package commands;

import api.AvwxRequests;
import atis.Atis;
import bot.AtisBot;
import converter.FLC;
import org.json.JSONObject;


public enum Commands {
    HELP(null) {
        @Override
        public String commandAction(String[] params) {

            StringBuilder helpText = new StringBuilder("Available commands:\n");

            for (Commands commands : Commands.values()) {
                helpText.append("!").append(commands.name()).append("\n");
            }

            helpText.append("Note:\nTo get command specific help type the command without values.\nSome calculations require the same unit type for example kmh and km.");


            return helpText.toString();
        }
    },

    METAR("Type !METAR and an Airport ICAO- or IATA-Code to get the current METAR report.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 2 || !params[1].matches("[\\w]{3,4}")) return METAR.helpText;

            try {
                JSONObject getter = AvwxRequests.getMetar(params[1]);
                return String.valueOf(getter.get("sanitized"));
            } catch (Exception e) {
                return failMessage;
            }
        }
    },

    ATIS("Type !ATIS and an Airport ICAO- or IATA-Code to get the current ATIS report.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 2 || !params[1].matches("[\\w]{3,4}")) return ATIS.helpText;


            try {
                Atis.generateAtis(params[1], AtisBot.sink);
                return null;
            } catch (Exception e) {
                return failMessage;
            }
        }
    },

    TAF("Type !TAF and an Airport ICAO- or IATA-Code to get the current TAF report.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 2 || !params[1].matches("[\\w]{3,4}")) return TAF.helpText;


            try {
                JSONObject getter = AvwxRequests.getTAF(params[1]);
                return (String.valueOf(getter.get("sanitized")));
            } catch (Exception e) {
                return failMessage;
            }
        }
    },

    KG2LB("To convert kilogram to pound type !KG2LB and a value in kilogram.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 2 || !params[1].matches("[\\d]*")) return KG2LB.helpText;

            return FLC.kg2lb(params[1]);
        }
    },
    LB2KG("To convert pound to kilogram type !LB2KG and a value in pound.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 2 || !params[1].matches("[\\d]*")) return LB2KG.helpText;

            return FLC.lb2kg(params[1]);
        }
    },
    KMH2KN("To convert kilometer per hour to knots type !KMH2KN and a value in kilometer per hour.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 2 || !params[1].matches("[\\d]*")) return KMH2KN.helpText;

            return FLC.kmh2kn(params[1]);
        }
    },
    KN2KMH("To convert knots to kilometer per hour type !KN2KMH and a value in knots.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 2 || !params[1].matches("[\\d]*")) return KN2KMH.helpText;

            return FLC.kn2kmh(params[1]);
        }
    },
    KM2NM("To convert kilometer to nautical mile type !KM2NM and a value in kilometer.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 2 || !params[1].matches("[\\d]*")) return KM2NM.helpText;

            return FLC.km2nm(params[1]);
        }
    },
    NM2KM("To convert nautical mile to kilometer type !NM2KM and a value in nautical miles.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 2 || !params[1].matches("[\\d]*")) return NM2KM.helpText;

            return FLC.nm2km(params[1]);
        }
    },
    M2FT("To convert meter to feet type !M2FT and a value in meter.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 2 || !params[1].matches("[\\d]*")) return M2FT.helpText;

            return FLC.m2ft(params[1]);
        }
    },
    FT2M("To convert feet to meter type !FT2M and a value in feet.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 2 || !params[1].matches("[\\d]*")) return FT2M.helpText;

            return FLC.ft2m(params[1]);
        }
    },
    C2F("To convert celsius to fahrenheit type !C2F and a value in celsius.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 2 || !params[1].matches("[\\d]*")) return C2F.helpText;

            return FLC.c2f(params[1]);
        }
    },
    F2C("To convert fahrenheit to celsius type !F2C and a value in fahrenheit.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 2 || !params[1].matches("[\\d]*")) return F2C.helpText;

            return FLC.f2c(params[1]);
        }
    },
    HPA2INHG("To convert hectopascal to inch of mercury type !HPA2INHG and a value in hectopascal.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 2 || !params[1].matches("[\\d]*")) return HPA2INHG.helpText;

            return FLC.hPa2inHg(params[1]);
        }
    },
    INHG2HPA("To convert inch of mercury to hectopascal type !INHG2HPA and a value in inch of mercury.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 2 || !params[1].matches("[\\d]*")) return INHG2HPA.helpText;

            return FLC.inHg2hPa(params[1]);
        }
    },
    H2MIN("To convert hour to minute type !H2MIN and a value in hour.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 2 || !params[1].matches("[\\d]*")) return H2MIN.helpText;

            return FLC.h2min(params[1]);
        }
    },
    MIN2H("To convert minute to hour type !MIN2H and a value in minute.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 2 || !params[1].matches("[\\d]*")) return MIN2H.helpText;

            return FLC.min2h(params[1]);
        }
    },
    ISA("To calculate the ISA temperature type !ISA and a altitude in thousands of feet.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 2 || !params[1].matches("[\\d]*")) return ISA.helpText;

            return FLC.ISA(params[1]);
        }
    },
    ENDURANCE("To calculate the endurance type !ENDURANCE fuel flow and fuel.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 3 || !params[1].matches("[\\d]*") || !params[2].matches("[\\d]*"))
                return ENDURANCE.helpText;

            return FLC.endurance(params[1], params[2]);
        }
    },
    FUEL("To calculate the required fuel type !FUEL fuel flow and time.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 3 || !params[1].matches("[\\d]*") || !params[2].matches("[\\d]*"))
                return FUEL.helpText;

            return FLC.reqFuel(params[1], params[2]);
        }
    },
    FUELFLOW("To calculate the fuel flow type !FUELFLOW fuel and time.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 3 || !params[1].matches("[\\d]*") || !params[2].matches("[\\d]*"))
                return FUELFLOW.helpText;

            return FLC.ffl(params[1], params[2]);
        }
    },
    TIME("To calculate the time for a distance type !TIME speed and distance.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 3 || !params[1].matches("[\\d]*") || !params[2].matches("[\\d]*"))
                return TIME.helpText;

            return FLC.timeForDist(params[1], params[2]);
        }
    },
    DISTANCE("To calculate the distance in a given time type !DISTANCE speed and time.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 3 || !params[1].matches("[\\d]*") || !params[2].matches("[\\d]*"))
                return DISTANCE.helpText;

            return FLC.distInTime(params[1], params[2]);
        }
    },
    SPEED("To calculate the speed type !SPEED distance and time.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 3 || !params[1].matches("[\\d]*") || !params[2].matches("[\\d]*"))
                return SPEED.helpText;

            return FLC.speedByTimeForDist(params[1], params[2]);
        }
    },
    PRESSUREALT("To calculate the pressure altitude type !PRESSUREALT pressure in hPa and indicated altitude.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 3 || !params[1].matches("[\\d]*") || !params[2].matches("[\\d]*"))
                return PRESSUREALT.helpText;

            return FLC.pressureAlt(params[1], params[2]);
        }
    },
    DELTATEMP("To calculate the denity altitude type !DENSITYALT the temperature difference and pressure altitude.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 3 || !params[1].matches("[\\d]*") || !params[2].matches("[\\d]*"))
                return DELTATEMP.helpText;

            return FLC.deltaTemp(params[1], params[2]);
        }
    },
    DENSITYALTITUDE("To calculate the denity altitude type !DENSITYALT the temperature difference and pressure altitude.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 3 || !params[1].matches("[\\d]*") || !params[2].matches("[\\d]*"))
                return DENSITYALTITUDE.helpText;

            return FLC.densityAlt(params[1], params[2]);
        }
    },
    TRUEALTITUDE("To calculate the true altitude type !TRUEALTITUDE pressure in hPa, the indicated altitude and the static air temperature.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 4 || !params[1].matches("[\\d]*") || !params[2].matches("[\\d]*") || !params[3].matches("[\\d]*"))
                return TRUEALTITUDE.helpText;

            return FLC.trueAlt(params[1], params[2], params[3]);
        }
    },
    TAS("To calculate the true airspeed type !TAS speed and altitude.") {
        @Override
        public String commandAction(String[] params) {
            if (params.length != 3 || !params[1].matches("[\\d]*") || !params[2].matches("[\\d]*")) return TAS.helpText;


            return FLC.TAS(params[1], params[2]);
        }
    };


    private static final String failMessage = "There was a problem executing this command.";
    private final String helpText;

    private Commands(String helpText) {
        this.helpText = helpText;
    }

    public abstract String commandAction(String[] params);

    public String getHelpText() { // I have no idea if this is necessary, but it returns the helpText
        return this.helpText;
    }
}
