package vsc.cli.eol;

public enum Ending {
    MAC,
    DOS,
    UNIX;

    @Override
    public String toString() {
        switch(this) {
            case MAC:
                return "\r";
            case DOS:
                return "\r\n";
            case UNIX:
            default:
                return "\n";
        }
    }

    public static Ending getEnum(String ending) {
        switch(ending) {
            case "mac":
                return MAC;
            case "dos":
                return DOS;
            case "unix":
            default:
                return UNIX;
        }
    }
}
