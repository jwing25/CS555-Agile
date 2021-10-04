package GEDCOM;

import java.util.ArrayList;
import java.util.Date;

public class Individual{
    private String id;
    private String name;
    private String gender;
    private Date birthday;
    private int age;
    private Boolean is_alive;
    private Date death_date;
    private ArrayList<String> childof;
    private ArrayList<String> spouse;

    public Individual(String id) {
        this.id = id;
        childof = new ArrayList<String>();
        spouse = new ArrayList<String>();
    }

    public Individual(String id, String name, String gender, Date birthday, int age, 
                    Boolean is_alive, Date death_date, ArrayList<String> childof, ArrayList<String> spouse) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.age = age;
        this.is_alive = is_alive;
        this.death_date = death_date;
        this.childof = childof;
        this.spouse = spouse;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getGender() {
        return this.gender;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public int getAge() {
        return this.age;
    }

    public Boolean getAlive() {
        return this.is_alive;
    }

    public Date getDeath() {
        return this.death_date;
    }

    public ArrayList<String> getChildren() {
        return this.childof;
    }

    public ArrayList<String> getSpouse() {
        return this.spouse;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAlive(Boolean is_alive) {
        this.is_alive = is_alive;
    }

    public void setDeath(Date death_date) {
        this.death_date = death_date;
    }

    public void setChildOf(ArrayList<String> childof) {
        this.childof = childof;
    }

    public void addChildOf(String famId) {
        this.childof.add(famId);
    }

    public void setSpouse(ArrayList<String> spouse) {
        this.spouse = spouse;
    }

    public void addSpouse(String spouseId) {
        this.spouse.add(spouseId);
    }

    public boolean isTheMarriageBeforeDeath(Individual spouse){
        if(spouse == null || spouse.spouse == null || spouse.spouse.size() == 0){
            return false;
        }

        ArrayList<Family> families = GEDCOM.families;
        String husbandID = this.gender.equals("Male")?id: spouse.id;
        String wifeID = this.gender.equals("Female")?id: spouse.id;
        Family ourFamily = null;
        for (Family f : families) {
            if(f.getHusbandId().equals(husbandID) && f.getWifeId().equals(wifeID)){
                ourFamily = f;
                break;
            }
        }
        if(ourFamily == null){
            return false;
        }
        Date marriageDate = ourFamily.getMarriageDate();

        Date deathDateHusband;
        Date deathDateWife;
        if(husbandID.equals(id)){
            deathDateWife = spouse.death_date;
            deathDateHusband = this.death_date;
        }else{
            deathDateWife = this.death_date;
            deathDateHusband = spouse.death_date;

        }
        if(deathDateHusband == null && deathDateWife == null){
            return true;
        }
        if(deathDateHusband == null) {
            return marriageDate.before(deathDateWife);
        }
        if(deathDateWife == null){
            return marriageDate.before(deathDateHusband);
        }
        return marriageDate.before(deathDateHusband) && marriageDate.before(deathDateWife);
    }

    public boolean isTheMarriageBeforeDivorce(Individual spouse) {
        if (spouse == null || spouse.spouse == null || spouse.spouse.size() == 0) {
            return false;
        }

        ArrayList<Family> families = GEDCOM.families;
        String husbandID = this.gender.equals("Male") ? id : spouse.id;
        String wifeID = this.gender.equals("Female") ? id : spouse.id;
        Family ourFamily = null;
        for (Family f : families) {
            if (f.getHusbandId().equals(husbandID) && f.getWifeId().equals(wifeID)) {
                ourFamily = f;
                break;
            }
        }
        if (ourFamily == null) {
            return false;
        }
        Date marriageDate = ourFamily.getMarriageDate();
        Date divorceDate = ourFamily.getDivorceDate();

        if (divorceDate != null) {
            return marriageDate.before(divorceDate);
        } else {
            return true;
        }
    }

    public boolean isBirthBeforeDeath() {
        if (this.death_date == null) {
            return true;
        }
        if (this.birthday.equals(this.death_date)) {
            return true;
        }
        return this.birthday.before(this.death_date);
    }

    public boolean isDivorceBeforeDeath(Individual spouse){
        if(spouse == null || spouse.spouse == null || spouse.spouse.size()== 0){
            return false;
        }
    
        ArrayList<Family> families = GEDCOM.families;
        String husbandID = this.gender.equals("Male")?id: spouse.id;
        String wifeID = this.gender.equals("Female")?id: spouse.id;
        Family ourFamily = null;
        for (Family f : families) {
            if(f.getHusbandId().equals(husbandID) && f.getWifeId().equals(wifeID)){
                ourFamily = f;
                break;
            }
        }
        if(ourFamily == null){
            return false;
        }
        Date divorceDate = ourFamily.getDivorceDate();
    
        if(divorceDate == null){
            return false;
        }
        
        Date deathDateHusband;
        Date deathDateWife;
        if(husbandID.equals(id)){
            deathDateWife = spouse.death_date;
            deathDateHusband = this.death_date;
        }else{
            deathDateWife = this.death_date;
            deathDateHusband = spouse.death_date;
    
        }
        if(deathDateHusband == null && deathDateWife == null){
            return true;
        }
        if(deathDateHusband == null) {
            return divorceDate.before(deathDateWife);
        }
        if(deathDateWife == null){
            return divorceDate.before(deathDateHusband);
        }
        return divorceDate.before(deathDateHusband) && divorceDate.before(deathDateWife);
    }
}

