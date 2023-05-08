import java.util.ArrayList;
import java.util.List;

public class Query {
    private String name;
    private Integer minID;
    private Integer maxID;
    private Integer passengerClass;
    private String gender;
    private Integer sibPar;
    private Integer parCh;
    private String ticket;
    private Double fareMax;
    private Double fareMin;
    private String cabin;
    private String embarked;
    private Double ageMin;
    private Double ageMax;
    private boolean toCheckAge;

    public Query(){
        this.resetFilters();
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setMinID(Integer minID) {
        this.minID = minID;
    }

    public void setMaxID(Integer maxID) {
        this.maxID = maxID;
    }

    public void setPassengerClass(Integer passengerClass) {
        this.passengerClass = passengerClass;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setSibPar(Integer sibPar) {
        this.sibPar = sibPar;
    }

    public void setParCh(Integer parCh) {
        this.parCh = parCh;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public void setFareMax(Double fareMax) {
        this.fareMax = fareMax;
    }

    public void setFareMin(Double fareMin) {
        this.fareMin = fareMin;
    }

    public void setCabin(String cabin) {
        this.cabin = cabin;
    }

    public void setEmbarked(String embarked) {
        this.embarked = embarked;
    }
    public void setAgeMin(Double ageMin){
        this.ageMin = ageMin;
    }
    public void setAgeMax(Double ageMax){
        this.ageMax = ageMax;
    }
    public void statisticsAgeAnalyze(){
        this.toCheckAge=true;
    }
    public List<Passenger> filterPassengers(List<Passenger> passengerList){
        List<Passenger> filtered = new ArrayList<>();
        for (Passenger pass :
                passengerList) {
            if (pass.passengerClass(this.passengerClass) && pass.genderCheck(this.gender)
                    && pass.portCheck(this.embarked) && pass.passengerIdHigher(this.minID)
                    && pass.passengerIdLower(this.maxID) && pass.nameContains(this.name)
                    && pass.siblingsPartnersCheck(this.sibPar) && pass.childrenParentsCheck(this.parCh)
                    && pass.ticketCheck(this.ticket) && pass.fareHigherCheck(this.fareMin)
                    && pass.fareLowerCheck(this.fareMax) && pass.cabinCheck(this.cabin)
                    && pass.minAgeCheck(this.ageMin, this.toCheckAge) && pass.maxAgeCheck(this.ageMax, this.toCheckAge)) {
                filtered.add(pass);
            }
        }
        return filtered;
    }
    public void resetFilters(){
        this.cabin = Constants.EMPTY_STRING_FILTER;
        this.gender = Constants.EMPTY_STRING_FILTER;
        this.embarked = Constants.EMPTY_STRING_FILTER;
        this.name = Constants.EMPTY_STRING_FILTER;
        this.ticket = Constants.EMPTY_STRING_FILTER;
        this.fareMax = null;
        this.fareMin = null;
        this.maxID = null;
        this.minID = null;
        this.parCh = null;
        this.passengerClass = null;
        this.sibPar = null;
        this.ageMin = null;
        this.ageMax = null;
        this.toCheckAge = false;
    }
}
