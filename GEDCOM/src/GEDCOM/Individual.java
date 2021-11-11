package GEDCOM;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
//import java.util.Calendar;
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

    public void setSpouse(ArrayList<String> spouses) {
        for (String spouseID : spouses) {
            if(this.isSibling(getIndividual(spouseID))){
                return;
            }
        }
        this.spouse = spouses;
    }

    public void addSpouse(String spouseId) {
        if(!isSibling(getIndividual(spouseId))) {
            this.spouse.add(spouseId);
        }
    }

    public boolean isTheMarriageBeforeDeath(Individual spouse){
        if(spouse == null || spouse.spouse == null || spouse.spouse.size() == 0){
            return false;
        }

        ArrayList<Family> families = GEDCOM.families;
        String husbandID = this.gender.equals("Male") || this.gender.equals("M") ?id: spouse.id;
        String wifeID = this.gender.equals("Female") || this.gender.equals("F") ?id: spouse.id;
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
        String husbandID = this.gender.equals("Male") || this.gender.equals("M") ? id : spouse.id;
        String wifeID = this.gender.equals("Female") || this.gender.equals("F") ? id : spouse.id;
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

    public Individual getIndividual(String id) {
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
        String husbandID = this.gender.equals("Male") || this.gender.equals("M") ? id: spouse.id;
        String wifeID = this.gender.equals("Female") || this.gender.equals("F")  ? id: spouse.id;
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

    public boolean isMultipleBirth(){
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

    /**
     * US11: No bigamy
     * @return True if the individual has less than one spouse at a time, false otherwise
     */
    public Boolean checkBigamy() {
        if (this.spouse == null || this.spouse.size() <= 1) {
            return true;
        }
        // If an individual has had more than 1 spouse, check if they were married at the same time
        // Add all marriages to a list of pairs
        ArrayList<Marriage> marriages = new ArrayList<Marriage>();
        Family f;
        Individual s;
        for (int i = 0; i < spouse.size(); i++) {
        	try {
        		f = GEDCOM.getFamily(spouse.get(i));
        		if (this.gender.equals("Male") || this.gender.equals("M")) {
        			s = getIndividual(f.getWifeId());
        		} else {
        			s = getIndividual(f.getHusbandId());
        		}
        		if (f.getDivorceDate() != null) { // Check if divorced 
        			marriages.add(new Marriage("M" + i, f.getMarriageDate(), f.getDivorceDate()));
        		} else if (!s.getAlive()) { // Check if widow/widower
        			marriages.add(new Marriage("M" + i, f.getMarriageDate(), s.getDeath()));
        		} else {
        			marriages.add(new Marriage("M" + i, f.getMarriageDate(), null));
        		}
        	} catch (Exception e) {
        		System.out.println("ERROR: [US11] INDIVIDUAL " + this.getId() + " - is a spouse in an invalid family " + spouse.get(i) + ".");
        	}
        }

        // Check if any marriage overlaps
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        for (Marriage m1 : marriages) {
            for (Marriage m2 : marriages) {
                if (m1.getId() != m2.getId() && m1.during(m2.getStart())) {
                    System.out.println("ERROR: Individual " + this.getId() + " - Bigamy occurs with marriage on date " + formatter.format(m2.getStart()) + ".");
                    return false;
                }
            }
        }
        return true;
    }

    public boolean parentsTooOld(ArrayList<Individual> individuals){
        //ArrayList<Individual> Individual = GEDCOM.individuals;
        ArrayList<Family> Family = GEDCOM.families;

        for(Individual i : individuals){
    
            Individual husband;
            Individual wife;
            
            for(Family f: Family){
                String husbandID = f.getHusbandId();
                husband = new Individual(husbandID);
                String wifeID = f.getWifeId();
                wife = new Individual(wifeID);

                    if((((Individual)husband).age - i.getAge()) >= 80){
                        //return false
                        return false;
                    }
                    if((wife.getAge() - i.getAge()) >= 60){
                        //return false
                        return false;
                    }
            }
            return true;
        }
        return true;
    }

    public boolean isSpacedSiblings(){
        Family family = getBioMotherFamily();
        ArrayList<Date> listOfBirthDays = new ArrayList<>();
        for (String id : family.getChildren()) {
            Individual sibling = getIndividual(id);
            listOfBirthDays.add(sibling.getBirthday());
        }
        for (int i = 0; i < listOfBirthDays.size(); i++) {
            LocalDate date1 = Dates.convertToLocalDateViaInstant(listOfBirthDays.get(i));
            for (int j = i+1; j < listOfBirthDays.size(); j++) {
                LocalDate date2 = Dates.convertToLocalDateViaInstant(listOfBirthDays.get(j));
                long months = ChronoUnit.MONTHS.between(date1, date2);
                long days = ChronoUnit.DAYS.between(date1, date2);

                if(Math.abs(months) < 8 && Math.abs(days) > 2){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isBirthBeforeMarriage(Individual spouse){
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
        Date marriageDate = ourFamily.getMarriageDate();
        Date divorceDate = null;
        LocalDate divorceDateLocal = null;
        LocalDate divorceDateLocal9months = null;

        if(ourFamily.getDivorceDate() != null){
            divorceDate = ourFamily.getDivorceDate();
            divorceDateLocal = divorceDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            divorceDateLocal9months = divorceDateLocal.plusMonths(9);
        }

        ArrayList<String> children = ourFamily.getChildren();
        for (String c: children){
            Individual child = getIndividual(c);
            Date child_birthday = child.getBirthday();
            LocalDate child_birthday_local = child_birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if(child_birthday.before(marriageDate) || (divorceDate != null && child_birthday_local.isAfter(divorceDateLocal9months))){
                System.out.println("ERROR: Child " + child.id + " was born out of wedlock");
                return true;
            }
        }
        return false;
        }

    public boolean marriageAfterFourteen(Individual spouse){
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

        Date husbandBirthday = this.gender.equals("Male")?birthday: spouse.birthday;
        Date wifeBirthday = this.gender.equals("Female")?birthday: spouse.birthday;
        Date marriageDate = ourFamily.getMarriageDate();

        LocalDate husbandLocalBirthday = husbandBirthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate wifeLocalBirthday = wifeBirthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate marriageLocalDate = marriageDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if(ChronoUnit.YEARS.between(wifeLocalBirthday, marriageLocalDate) < 14 || ChronoUnit.YEARS.between(husbandLocalBirthday, marriageLocalDate) < 14 ){
            System.out.println("ERROR: Individual " + this.getId() + "and " + spouse.id + " were married before one or both of them were 14.");
            return false;
        }
        return true;

        }

    public boolean isSibling(Individual otherPerson){
        ArrayList<Family> families = GEDCOM.families;
        for (int i = 0; i < families.size(); i++) {
            if(families.get(i).getChildren().contains(name) &&
                    families.get(i).getChildren().contains(otherPerson.name)){
                return true;
            }
        }
        return false;
    }

    private ArrayList<String> result = new ArrayList<String>();

    public ArrayList<String> getResult() {
        return result;
    }

    public boolean notTooOld(){
      ArrayList<Individual> individual = GEDCOM.individuals;
          for(Individual i : individual){
              if(i.getAge() >= 150){
                  String tmp = "[Error]: with a birth date:" + i.getBirthday() + " , "
                          + i.getName() + " (" + i.getId() + "), "
                          + "lived longer than 150 years old" + ",as his/her death date is " + ""
                          + i.getDeath();

                  return result.add(tmp);
              }
          }
          return true;
      }

    @Override
    public String toString() {
        return "Individual:" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age ;
    }
}

