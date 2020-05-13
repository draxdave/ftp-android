package ir.drax.ftp.ftp;

import java.util.Date;

public class Content {
    private String name;
    private Long size;
    private String path;
    private int type;
    private Date modified;

    public Content(String name, Long size, String path, int type, Date modifiedDate) {
        this.name = name;
        this.size = size;
        this.path = path;
        this.type = type;
        this.modified = modifiedDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
}
