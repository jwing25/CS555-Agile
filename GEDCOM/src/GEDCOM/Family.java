package GEDCOM;

import java.util.ArrayList;
import java.util.Date;

public class Family {
    private String id;
    private Date marriage_date;
    private Date divorce_date;
    private String husband_id;
    private String husband_name;
    private String wife_id;
    private String wife_name;
    private ArrayList<String> children;

    public Family(String id) {
        this.id = id;
        this.children = new ArrayList<String>();
     }

    public Family(String id, Date marriage_date, Date divorce_date, String husband_id, 
                String husband_name, String wife_id, String wife_name, ArrayList<String> children) {
        this.id = id;
        this.marriage_date = marriage_date;
        this.divorce_date = divorce_date;
        this.husband_id = husband_id;
        this.husband_name = husband_name;
        this.wife_id = wife_id;
        this.wife_name = wife_name;
        this.children = children;
    }

    public String getId() {
        return this.id;
    }

    public Date getMarriageDate() {
        return this.marriage_date;
    }

    public Date getDivorceDate() {
        return this.divorce_date;
    }

    public String getHusbandId() {
        return this.husband_id;
    }

    public String getHusbandName() {
        return this.husband_name;
    }

    public String getWifeId() {
        return this.wife_id;
    }

    public String getWifeName() {
        return this.wife_name;
    }

    public ArrayList<String> getChildren() {
        return this.children;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMarriageDate(Date marriage_date) {
        this.marriage_date = marriage_date;
    }

    public void setDivorceDate(Date divorce_date) {
        this.divorce_date = divorce_date;
    }

    public void setHusbandId(String husband_id) {
        this.husband_id = husband_id;
    }

    public void setHusbandName(String husband_name) {
        this.husband_name = husband_name;
    }

    public void setWifeId(String wife_id) {
        this.wife_id = wife_id;
    }

    public void setWifeName(String wife_name) {
        this.wife_name = wife_name;
    }

    public void setChildren(ArrayList<String> children) {
        this.children = children;
    }

    public void addChild(String childId) {
        this.children.add(childId);
    }

    // US15: Checks if there are fewer than 15 siblings
    public Boolean fewerThanFifteenSiblings() {
        if (this.children == null || this.children.isEmpty()) {
            return true;
        } else if (this.children.size() < 15) {
            return true;
        } else {
            System.out.println("ERROR: FAMILY " + this.getId() + " - does not have fewer than 15 siblings.");
            return false;
        }
    }
}
