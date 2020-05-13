package ir.drax.ftp.ftp;

public class Command{
    public Enum command;
    public Object[] params;
    public On on;

    public Command(Enum command, Object[] params, On on) {
        this.command = command;
        this.params = params;
        this.on = on;
    }

    Command(Enum command, Object[] params) {
        this.command = command;
        this.params = params;
    }

    public Command(Enum command, On on) {
        this.command = command;
        this.on = on;
    }

    public Command(Enum command) {
        this.command = command;
        this.on = on;
    }
}
