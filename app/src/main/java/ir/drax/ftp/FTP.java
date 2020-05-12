package ir.drax.ftp;

import ir.drax.ftp.ftp.Constant;
import ir.drax.ftp.ftp.On;
import it.sauronsoftware.ftp4j.*;

import java.io.IOException;

public class FTP implements Runnable{
     public static enum Commands{
        LOGIN,
        DISCONNECT,
        LIST_DIR
    }

    private Enum Command;
    private static FTP instance;
    private FTPClient client;
    private On on;

    public static FTP getInstance(){
        if (instance==null)instance = new FTP();

        return instance;
    }

    private FTP(){
        this.client = new FTPClient();
    }

    private void login(){
        try {
            client.connect(Constant.serverUrl);
            client.login(Constant.username,Constant.pass,"Drax");
            on.done(true);

        } catch (IOException e) {
            e.printStackTrace();
            on.fail(e.getLocalizedMessage());

        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
            on.fail(e.getLocalizedMessage());

        } catch (FTPException e) {
            e.printStackTrace();
            on.fail(e.getLocalizedMessage());
        }
    }

    private void listDirectory(){
        try {
            on.done(client.listNames());

        } catch (IOException e) {
            e.printStackTrace();
            on.fail(e.getLocalizedMessage());
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
            on.fail(e.getLocalizedMessage());
        } catch (FTPException e) {
            e.printStackTrace();
            on.fail(e.getLocalizedMessage());
        } catch (FTPDataTransferException e) {
            e.printStackTrace();
            on.fail(e.getLocalizedMessage());
        } catch (FTPAbortedException e) {
            e.printStackTrace();
            on.fail(e.getLocalizedMessage());
        } catch (FTPListParseException e) {
            e.printStackTrace();
            on.fail(e.getLocalizedMessage());
        }
    }

    private void disconnect(){
        try {
            client.disconnect(true);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        } catch (FTPException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        if (Command.equals(Commands.LOGIN))
            login();
        else if (Command.equals(Commands.DISCONNECT))
            disconnect();
        else if (Command.equals(Commands.LIST_DIR))
            listDirectory();

    }

    public void Go(Enum command){
        Go(command, new On() {
            @Override
            public void done(Object o) {

            }

            @Override
            public void fail(String message) {

            }
        });
    }
    public void Go(Enum command,On on){
        this.Command = command;
        this.on = on;
        new Thread(this).start();
    }
}
