package converter;

public enum NatoAlphabet {
    A("Alpha"),
    B("Bravo"),
    C("Charlie"),
    D("Delta"),
    E("Echo"),
    F("Foxtrot"),
    G("Golf"),
    H("Hotel"),
    I("India"),
    J("Juliet"),
    K("Kilo"),
    L("Lima"), // leihmah -> LIMA || wrong pronunciation
    M("Mike"),
    N("November"),
    O("Oskar"),
    P("Papa"),
    Q("Quebec"),
    R("Romeo"),
    S("Sierra"),
    T("Tango"),
    U("Uniform"),
    V("Victor"),
    W("Whiskey"),
    X("Xray"),
    Y("Yankee"),
    Z("Zulu");


    final String spoken;

    NatoAlphabet(String spoken) {
        this.spoken = spoken;
    }

    public String getSpoken() {
        return this.spoken;
    }
}
