package GEDCOM;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        ourFamily = getFamily(families, husbandID, wifeID, ourFamily);
        if(ourFamily == null){
            return false;
        }
        Date marriageDate = ourFamily.getMarriageDate();
        boolean ans = marriageAndaDeathCompare(spouse, husbandID, marriageDate);

        return ans;
    }

    private boolean marriageAndaDeathCompare(Individual spouse, String husbandID, Date marriageDate) {
        Date deathDateHusband;
        Date deathDateWife;
        if(husbandID.equals(id)){
            deathDateWife = spouse.death_date;
            deathDateHusband = this.death_date;
        }else{
            deathDateWife = this.death_date;
            deathDateHusband = spouse.death_date;

        }
        boolean ans ;
        if(deathDateHusband == null && deathDateWife == null){
            return true;
        }
        if(deathDateHusband == null) {
            ans = marriageDate.before(deathDateWife);
            if(!ans){
                System.out.println("[ERROR] isTheMarriageBeforeDeath: Marriage Does not occur before death for : " + this.id + " and " + spouse.id + " on marriage date " + marriageDate.toString()  + " (wife death) and on death date " + deathDateHusband );
            }
            return ans;
        }
        if(deathDateWife == null){
            ans = marriageDate.before(deathDateHusband);
            if(!ans){
                System.out.println("[ERROR] isTheMarriageBeforeDeath: Marriage Does not occur before death for : " + this.id + " and " + spouse.id + " on marriage date " + marriageDate.toString()  + " (husband death) and on death date " + deathDateHusband );
            }
            return ans;

        }
        ans = marriageDate.before(deathDateHusband) && marriageDate.before(deathDateWife);
        if(!ans){
            System.out.println("[ERROR] isTheMarriageBeforeDeath: Marriage Does not occur before death for : " + this.id + " and " + spouse.id + " on marriage date " + marriageDate.toString()  + " (both death) and on death date " + deathDateHusband );
        }
        return ans;
    }

    private Family getFamily(ArrayList<Family> families, String husbandID, String wifeID, Family ourFamily) {
        for (Family f : families) {
            if (f.getHusbandId().equals(husbandID) && f.getWifeId().equals(wifeID)) {
                ourFamily = f;
                break;
            }
        }
        return ourFamily;
    }

    public boolean isTheMarriageBeforeDivorce(Individual spouse) {
        if (spouse == null || spouse.spouse == null || spouse.spouse.size() == 0) {
            return false;
        }

        Family ourFamily = getFamily(spouse);
        if (ourFamily == null) {
            return false;
        }
        Date marriageDate = ourFamily.getMarriageDate();
        Date divorceDate = ourFamily.getDivorceDate();

        if (divorceDate != null) {
            boolean ans = marriageDate.before(divorceDate);
            if(!ans) {
                System.out.println("[ERROR] isTheMarriageBeforeDivorce: Marriage Does not occur before divorce for: " + this.id + " and " + spouse.id + " on maraiage date " + marriageDate.toString()  + " and on divorce date " + divorceDate.toString() );
            }
            return ans;
        } else {
            return true;
        }
    }

    private Family getFamily(Individual spouse) {
        ArrayList<Family> families = GEDCOM.families;
        String husbandID = this.gender.equals("Male") ? id : spouse.id;
        String wifeID = this.gender.equals("Female") ? id : spouse.id;
        Family ourFamily = null;
        ourFamily = getFamily(families, husbandID, wifeID, ourFamily);
        return ourFamily;
    }

    private Family getBioMotherFamily() {
        ArrayList<Family> families = GEDCOM.families;
        String personID = id;
        Family ourFamily = null;
        for (Family f : families) {
            // is child and is a mother
            if (f.getChildren().contains(personID) && f.getHusbandId() != null ) {
                ourFamily = f;
                break;
            }
        }
        return ourFamily;
    }

    private Individual getIndividual(String id) {
        ArrayList<Individual> individuals = GEDCOM.individuals;
        for (Individual i : individuals) {
            // is child and is a mother
            if (i.id.equals(id)) {
                return i;
            }
        }
        return null;
    }
    public boolean isBirthBeforeDeath() {
        if (this.death_date == null) {
            return true;
        }
        if (this.birthday.equals(this.death_date)) {
            return true;
        }
        boolean ans = this.birthday.before(this.death_date);
        if(!ans){
            System.out.println("[ERROR] isBirthBeforeDeath: Birth Does not occur before Death for: " + this.id + " on birthday date " + birthday.toString()  + " and on death date " + death_date.toString() );
        }
        return ans;
    }

    public boolean uniqueNameAndBirthDate() {
        //base case checking individual and husband and wife
        ArrayList<Individual> individual = GEDCOM.individuals;

        for (Individual id : individual) {
            String personName = id.name;
            Date personBirthDate = id.birthday;
            
            boolean ans = id.getName().equals(personName);
            boolean ans2 = id.getBirthday().equals(personBirthDate);
            if (ans == true && ans2 == true) {
                System.out.println("[ERROR] uniqueNameAndBirthDate: Individual " + this.id + " whose Name: " + personName + ", Birth Date: " + personBirthDate + " has the same name and birth date with Individual: " + this.id + " whose Name: " + personName + ", Birth Date: " + personBirthDate);
            }
            return ans;
        }
        return true;
    }

    public boolean isDivorceBeforeDeath(Individual spouse){
        if(spouse == null || spouse.spouse == null || spouse.spouse.size()== 0){
            return false;
        }
    
        ArrayList<Family> families = GEDCOM.families;
        String husbandID = this.gender.equals("Male")?id: spouse.id;
        String wifeID = this.gender.equals("Female")?id: spouse.id;
        Family ourFamily = null;
        ourFamily = getFamily(families, husbandID, wifeID, ourFamily);
        if(ourFamily == null){
            return false;
        }
        Date divorceDate = ourFamily.getDivorceDate();
    
        if(divorceDate == null){
            return false;
        }
        boolean ans = marriageAndaDeathCompare(spouse, husbandID, divorceDate);
        if (!ans){
            System.out.println("[ERROR] isDivorceBeforeDeath: Divorce Does not occur before Death for: " + this.id + " on divorce date " + divorceDate.toString()  + " and on death date " + death_date.toString() );
        }
        return ans;
    }

    public boolean isSpacedSiblings(){
        Family family = getBioMotherFamily();
        HashMap<Date,ArrayList<String>> mapOfBDayAndName = new HashMap<>();
        for (String id : family.getChildren()) {
            Individual sibling = getIndividual(id);
            if(!mapOfBDayAndName.containsKey(sibling.getBirthday())){
                mapOfBDayAndName.put(sibling.getBirthday(),new ArrayList<>());
            }
            mapOfBDayAndName.get(sibling.getBirthday()).add(sibling.name);
        }
        for (Map.Entry<Date, ArrayList<String>> entry : mapOfBDayAndName.entrySet()) {
            if(entry.getValue().size() >= 5){
                return false;
            }
        }
        return true;
    }

}

