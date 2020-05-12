package ir.drax.ftp;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

import java.io.IOException;

public class FTP {
    private static FTP instance;
    private FTPClient client;

    public FTP getInstance(){
        if (instance==null)instance = new FTP();

        return instance;
    }

    private FTP(){
        this.client = new FTPClient();
    }

    void login(On on){
        try {
            client.connect(Constant.serverUrl);
            client.login(Constant.username,Constant.pass,"Drax");
            on.Connected();

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

    public void disconnect(){
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
}
