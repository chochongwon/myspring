package myspring.batch.model;

public class IpInfo {
    private String ip;

    private boolean filtered = false;
    
    public IpInfo(String ip) {
        this.ip = ip;
    }

    public void setFiltered(boolean filtered) {
        this.filtered = filtered;
    }

    public boolean isFiltered() {
        return filtered;
    }

    public String getIp() {
        return ip;
    }

    @Override
    public String toString() {
        return ip;
    }
}
