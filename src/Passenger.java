public class Passenger{
    private int passengerId;
    private boolean survived;
    private int pClass;
    private String name;
    private String sex;
    private Double age;
    private int sibSp;
    private int parCh;
    private String ticket;
    private double fare;
    private String cabin;
    private String embarked;
    public Passenger(){}
    public Passenger(String line){
        String[] passengerDetails = line.split(Constants.SEPARATOR);
        if (passengerDetails.length == 12){
            passengerDetails = embarkedMissingInfo(passengerDetails);
        }
        this.passengerId = Integer.valueOf(passengerDetails[0]);
        if (Integer.valueOf(passengerDetails[1]) == Constants.SURVIVED) {
            this.survived = true;
        } else {
            this.survived = false;
        }
        //TODO: constants
        this.pClass = Integer.valueOf(passengerDetails[2]);
        this.name = removeQuotes(passengerDetails[3] + "," + passengerDetails[4]);
        this.sex = passengerDetails[5];
        if (passengerDetails[6].equals("")){
            this.age = null;
        }else{
            this.age = Double.parseDouble(passengerDetails[6]);
        }
        this.sibSp = Integer.valueOf(passengerDetails[7]);
        this.parCh = Integer.valueOf(passengerDetails[8]);
        this.ticket = passengerDetails[9];
        this.fare = Double.parseDouble(passengerDetails[10]);
        this.cabin = passengerDetails[11];
        this.embarked = passengerDetails[12];
    }
    private String[] embarkedMissingInfo(String[] info){
        String[] fixed = new String[info.length + 1];
        System.arraycopy(info, 0, fixed, 0, info.length);
        fixed[info.length] = Constants.EMBARKED_MISSING_INFO;
        return fixed;
    }
    //TODO: constants
    public String getFormattedName(){
        String formattedName = this.name.substring(this.name.indexOf(Constants.PERIOD) + 2);
        formattedName += " " + this.name.substring(0, this.name.indexOf(Constants.SEPARATOR));
        return formattedName;
    }
    public boolean nameContains(String name){
        return name.length() < 1 || this.name.toLowerCase().contains(name.toLowerCase());
    }
    public boolean passengerClass (Integer passengerClass){
        return passengerClass == null || passengerClass == Constants.PASSENGER_CLASS_ALL || this.pClass == passengerClass;
    }
    public boolean passengerIdHigher(Integer idMin){
        return idMin == null || this.passengerId >= idMin;
    }
    public boolean passengerIdLower(Integer idMax){
        return idMax == null || this.passengerId <= idMax;
    }
    public boolean genderCheck(String gender){
        return gender.length() < 1 || gender.equals(Constants.COMBO_BOX_NO_FILTER) || this.sex.equals(gender);
    }
    public boolean portCheck(String embarkedAt){
        return embarkedAt.length() < 1 || embarkedAt.equals(Constants.COMBO_BOX_NO_FILTER) || this.embarked.equals(embarkedAt);
    }
    public boolean siblingsPartnersCheck(Integer sibPar){
        return sibPar == null || this.sibSp == sibPar;
    }
    public boolean childrenParentsCheck(Integer parCh){
        return parCh == null || this.parCh == parCh;
    }
    public boolean ticketCheck(String ticket){
        return ticket.length() < 1 || this.ticket.contains(ticket);
    }
    public boolean fareHigherCheck(Double minFare){
        return minFare == null || this.fare >= minFare;
    }
    public boolean fareLowerCheck(Double maxFare){
        return maxFare == null || this.fare <= maxFare;
    }
    public boolean cabinCheck(String cabin){
        return cabin.length() < 1 || this.cabin.contains(cabin);
    }
    public boolean minAgeCheck(Double ageMin, boolean toCheck){
        boolean fits = !toCheck;
        if (toCheck){
            if (ageMin == null || (this.age != null && this.age >= ageMin)){
                fits = true;
            }
        }
        return fits;
    }
    public boolean maxAgeCheck(Double ageMax, boolean toCheck){
        boolean fits = !toCheck;
        if (toCheck){
            if (ageMax == null || (this.age != null && this.age <= ageMax)){
                fits = true;
            }
        }
        return fits;
    }
    public boolean equals(Passenger other){
        return this.passengerId == other.passengerId;
    }
    public boolean hasSurvived(){
        return this.survived;
    }
    public String toString(){
        String output =  this.passengerId + Constants.SEPARATOR + (this.survived? 1:0) + Constants.SEPARATOR + this.pClass + Constants.SEPARATOR + this.getFormattedName() + Constants.SEPARATOR
                + this.sex + Constants.SEPARATOR + this.age + Constants.SEPARATOR + this.sibSp + Constants.SEPARATOR + this.parCh + Constants.SEPARATOR + this.ticket + Constants.SEPARATOR
                + this.fare + Constants.SEPARATOR + this.cabin + Constants.SEPARATOR + this.embarked + "\n";
        return output;
    }
    private String removeQuotes(String string){
        String result = string.replace(Constants.QUOTE, Constants.ASTRIX);
        result = result.replace(Constants.ASTRIX + "", "");
        result = result.replace("(", "").replace(")", "");
        return result;
    }
}

