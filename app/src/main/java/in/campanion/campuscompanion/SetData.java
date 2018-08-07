package in.campanion.campuscompanion;

/**
 * Created by aftab on 24-12-2016.
 */

public class SetData {
    private String name;
    private String brief;
    private String id;
    //private String locality;

    public SetData(String id, String name, String brief){
        this.setId(id);
        this.setName(name);
        this.setBrief(brief);
        //this.setLocality(locality);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public String getLocality() {
//        return locality;
//    }
//
//    public void setLocality(String locality) {
//        this.locality = locality;
//    }
}
