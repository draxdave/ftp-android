package ir.drax.ftp.ftp;

import java.io.File;

public class Commands{
    public static enum AvailableCommands {
        LOGIN,
        DISCONNECT,
        LIST_DIR,
        LIST_UPPER_DIR,
        CURRENT_DIR,
        GET_FILE
    }
    public static Command Login(On on){ return new Command(AvailableCommands.LOGIN,on); }

    public static Command Disconnect(){ return new Command(AvailableCommands.DISCONNECT); }

    public static Command ListRoot(On on){ return new Command(AvailableCommands.LIST_DIR,new Object[]{"/"},on); }

    public static Command ListDir(String dirName,On on){ return new Command(AvailableCommands.LIST_DIR,new Object[]{dirName},on); }

    public static Command ListUpperDir(On on){ return new Command(AvailableCommands.LIST_UPPER_DIR,new Object[]{},on); }

    public static Command GetFile(String fileName, File destination, On on){ return new Command(AvailableCommands.GET_FILE,new Object[]{fileName,destination},on); }

    public static Command GetCurrentDir(On on){ return new Command(AvailableCommands.CURRENT_DIR,new Object[]{},on); }
}
