package ir.drax.ftp;

import android.app.Activity;
import android.util.Log;
import ir.drax.ftp.ftp.Command;
import ir.drax.ftp.ftp.Commands;
import ir.drax.ftp.ftp.Constant;
import ir.drax.ftp.ftp.Init;
import it.sauronsoftware.ftp4j.*;

import java.io.File;
import java.io.IOException;

public class FTP implements Runnable{


    private Init init;
    private Command command;
    private static FTP instance;
    private FTPClient client;
    private Activity activity;

    public static FTP getInstance(Activity activity, Init init){
        if (instance==null)instance = new FTP(activity,init);

        return instance;
    }

    private FTP(Activity activity,Init init){
        this.client = new FTPClient();
        this.activity = activity;
        this.init= init;

    }

    private void login(){
        try {
            client.connect(Constant.serverUrl);
            client.login(Constant.username,Constant.pass,"Drax");

            activity.runOnUiThread(() -> command.on.done(true));

        } catch (IOException e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> command.on.fail(e.getLocalizedMessage()));

        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> command.on.fail(e.getLocalizedMessage()));

        } catch (FTPException e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> command.on.fail(e.getLocalizedMessage()));
        }
    }

    private void listDirectory(){
        try {
            String dirName = (String) command.params[0];
            client.changeDirectory(dirName);
            FTPFile[] dirs = client.list();

            activity.runOnUiThread(() -> command.on.done(dirs));

        } catch (IOException e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> command.on.fail(e.getLocalizedMessage()));
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> command.on.fail(e.getLocalizedMessage()));
        } catch (FTPException e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> command.on.fail(e.getLocalizedMessage()));
        } catch (FTPDataTransferException e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> command.on.fail(e.getLocalizedMessage()));
        } catch (FTPAbortedException e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> command.on.fail(e.getLocalizedMessage()));
        } catch (FTPListParseException e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> command.on.fail(e.getLocalizedMessage()));
        }
    }

    private void listUpperDirectory(){
        try {
            String currentPath = client.currentDirectory();
            String path = currentPath.substring(0, currentPath.lastIndexOf("/")+1);
            Log.e("eee",path);
            client.changeDirectory(path);
            FTPFile[] dirs = client.list();

            activity.runOnUiThread(() -> command.on.done(dirs));

        } catch (IOException e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> command.on.fail(e.getLocalizedMessage()));
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> command.on.fail(e.getLocalizedMessage()));
        } catch (FTPException e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> command.on.fail(e.getLocalizedMessage()));
        } catch (FTPDataTransferException e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> command.on.fail(e.getLocalizedMessage()));
        } catch (FTPAbortedException e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> command.on.fail(e.getLocalizedMessage()));
        } catch (FTPListParseException e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> command.on.fail(e.getLocalizedMessage()));
        }
    }

    private void getFile(){
        try {
            String dirName = (String) command.params[0];
            File destination = (File) command.params[1];
            client.download(dirName, destination, new FTPDataTransferListener() {
                @Override
                public void started() {

                }

                @Override
                public void transferred(int i) {
                    activity.runOnUiThread(() -> command.on.progress(i));
                }

                @Override
                public void completed() {
                    activity.runOnUiThread(() -> command.on.done(true));
                }

                @Override
                public void aborted() {
                    activity.runOnUiThread(() -> command.on.done(false));
                }

                @Override
                public void failed() {
                    activity.runOnUiThread(() -> command.on.done(false));
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> command.on.fail(e.getLocalizedMessage()));
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> command.on.fail(e.getLocalizedMessage()));
        } catch (FTPException e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> command.on.fail(e.getLocalizedMessage()));
        } catch (FTPDataTransferException e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> command.on.fail(e.getLocalizedMessage()));
        } catch (FTPAbortedException e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> command.on.fail(e.getLocalizedMessage()));
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
        if (command.command.equals(Commands.AvailableCommands.LOGIN))
            login();
        else if (command.command.equals(Commands.AvailableCommands.DISCONNECT))
            disconnect();
        else if (command.command.equals(Commands.AvailableCommands.LIST_DIR))
            listDirectory();
        else if (command.command.equals(Commands.AvailableCommands.GET_FILE))
            getFile();
        else if (command.command.equals(Commands.AvailableCommands.LIST_UPPER_DIR))
            listUpperDirectory();
        else if (command.command.equals(Commands.AvailableCommands.CURRENT_DIR))
            currentDirectory();

    }

    public void Go(Command command){
        init.loading(true);
        this.command = command;
        new Thread(this).start();
    }

    public String getAccount(){
        return client.getUsername();
    }
    public void currentDirectory(){
        try {
            String currentDir = client.currentDirectory();

            activity.runOnUiThread(() -> command.on.done(currentDir));

        } catch (IOException e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> command.on.fail(e.getLocalizedMessage()));
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> command.on.fail(e.getLocalizedMessage()));
        } catch (FTPException e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> command.on.fail(e.getLocalizedMessage()));
        }
    }
}

