package in.campanion.campuscompanion;

/**
 * Created by aftab on 28-12-2016.
 */

public class DataSet {
    private String name;
    private String briefDescription;

    public void DataSet(String name,String brief_description){
        this.setName(name);
        this.setBriefDescription(brief_description);
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
