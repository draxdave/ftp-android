package ir.drax.ftp.service.ftp;

public abstract class On {
    Init init;
    public On(){}
    public On(Init init){
        this.init=init;
    }
    public void done(Object o){
        if (init!=null)init.loading(false);
    }
    public void fail(String message){
        if (init!=null)init.loading(false);
    }
    public void progress(int progress){}
}
