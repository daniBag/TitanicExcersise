import java.io.*;
import java.util.*;

public class PassengersData {
    public static Query query;
    private List<Passenger> passengersList;
    private int searchesCounter;
    private String columnNames;
    private List<Passenger> filteredList;
    public PassengersData(File file){
        query = new Query();
        this.passengersList = new ArrayList<>();
        this.filteredList = new ArrayList<>();
        readPassengers(file);
        this.searchesCounter = 0;
    }
    private void readPassengers(File file){
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            line = br.readLine();
            if (line != null){
                this.columnNames = line;
            }
            do {
                line = br.readLine();
                if (line != null){
                        this.passengersList.add(new Passenger(line));
                }
            }while (line != null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String filteringProcess(){
        String result = "No results found.";
        submitFilters();
        if (!this.filteredList.isEmpty()){
            int survivedCount = survivorsCount(this.filteredList);
            nameSort();
            filteredListPrepareToWrite();
            result = "Total Rows: " + this.filteredList.size() + "(" + survivedCount + " survived, " + (this.filteredList.size() - survivedCount) + " did not)";
        }
        return result;
    }
    private void submitFilters(){
        this.filteredList = query.filterPassengers(this.passengersList);
    }
    private int survivorsCount(List<Passenger> toCount){
        int result = 0;
        if (!toCount.isEmpty()){
            for (Passenger pass: toCount){
                if (pass.hasSurvived()){
                    result++;
                }
            }
        }
        return result;
    }
    private void writeToFile(File file, List<String> data){
        try {
            if (file.createNewFile()){
                PrintWriter printWriter = new PrintWriter(new FileWriter(file));
                for (String line : data) {
                    printWriter.write(line);
                }
                printWriter.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void filteredListPrepareToWrite(){
        if (!this.filteredList.isEmpty()){
            this.searchesCounter++;
            File filteredResults = new File(Constants.PATH_TO_FILE + this.searchesCounter + Constants.CSV_ENDING);
            List<String> dataToWrite = new ArrayList<>();
            dataToWrite.add(this.columnNames);
            for (Passenger passenger: this.filteredList){
                dataToWrite.add(passenger.toString());
            }
            this.writeToFile(filteredResults, dataToWrite);
        }
    }
    private void statisticsPrepareToWrite(List<String> statistics){
        File statisticsFile = new File(Constants.PATH_TO_FILE + Constants.STATISTICS_FILE);
        this.writeToFile(statisticsFile, statistics);
    }
    private void nameSort(){
        this.filteredList.sort(Comparator.comparing(Passenger::getFormattedName));
    }
    public void getStatistics(){
        submitFilters();
        List<String> statistics = new ArrayList<>();
        statistics.add(calculateClassStatistics());
        statistics.add(calculateGenderStatistics());
        statistics.add(calculateAgeGroupsStatistics());
        statistics.add(calculateFamilyOnBoardStatistics());
        statistics.add(calculateTicketCostStatistics());
        statistics.add(calculatePortStatistics());
        statisticsPrepareToWrite(statistics);
    }
    private String percentageTextGenerate (double percentage){
        String result;
        if (percentage == Constants.NO_PASSENGERS){
            result = Constants.NO_PASSENGERS_MESSAGE;
        }else {
            result = percentage + "%\n";
        }
        return result;
    }
    private double calculateSurvivalPercentage(int totalAmount, int survivorsAmount) {
        double result = Constants.NO_PASSENGERS;
        double survivors = survivorsAmount;
        double amount = totalAmount;
        if (totalAmount > 0) {
            result = (survivors / amount) * 100;
        }
        return result;
    }
    private String calculateClassStatistics(){
        String result = "Survived percentage by class: \n";
        result += "First Class: " + percentageTextGenerate(getClassStatistics(Constants.PASSENGER_CLASS_1ST));
        result += "Second Class: " + percentageTextGenerate(getClassStatistics(Constants.PASSENGER_CLASS_2ND));
        result += "Third Class: " + percentageTextGenerate(getClassStatistics(Constants.PASSENGER_CLASS_3RD));
        result += "*****************\n";
        return result;
    }
    private String calculateGenderStatistics(){
        String result = "Survived by Gender: \n";
        result += "Males: " + percentageTextGenerate(getGenderStatistics(Constants.SEX_OPTIONS[1]));
        result += "Females: " + percentageTextGenerate(getGenderStatistics(Constants.SEX_OPTIONS[2]));
        result += "*****************\n";
        return result;
    }
    private String calculateAgeGroupsStatistics(){
        String result = "Survived percentage by age groups: \n";
        result += "0-10: " + percentageTextGenerate(getAgeGroupStatistics(Constants.FIRST_AGE_GROUP_MIN, Constants.FIRST_AGE_GROUP_MAX));
        result += "11-20: " + percentageTextGenerate(getAgeGroupStatistics(Constants.SECOND_AGE_GROUP_MIN, Constants.SECOND_AGE_GROUP_MAX));
        result += "21-30: " + percentageTextGenerate(getAgeGroupStatistics(Constants.THIRD_AGE_GROUP_MIN, Constants.THIRD_AGE_GROUP_MAX));
        result += "31-40: " + percentageTextGenerate(getAgeGroupStatistics(Constants.FOURTH_AGE_GROUP_MIN, Constants.FOURTH_AGE_GROUP_MAX));
        result += "41-50: " + percentageTextGenerate(getAgeGroupStatistics(Constants.FIFTH_AGE_GROUP_MIN, Constants.FIFTH_AGE_GROUP_MAX));
        result += "51+: " + percentageTextGenerate(getAgeGroupStatistics(Constants.SIXTH_AGE_GROUP_MIN, Constants.SIXTH_AGE_GROUP_MAX));
        result += "*****************\n";
        return result;
    }

    private String calculateFamilyOnBoardStatistics(){
        String statistics = "Survived percentage with/without family on board: \n";
        int noFamily = 0;
        query.resetFilters();
        query.setSibPar(noFamily);
        query.setParCh(noFamily);
        List<Passenger> filterBySibPar = query.filterPassengers(this.filteredList);
        statistics += "Without family: " + percentageTextGenerate(calculateSurvivalPercentage(filterBySibPar.size(), survivorsCount(filterBySibPar)));
        List<Passenger> withFamilyList = deductFromList(this.filteredList, filterBySibPar);
        statistics += "With family on board: " + percentageTextGenerate(calculateSurvivalPercentage(withFamilyList.size(), survivorsCount(withFamilyList)));
        return statistics;
    }
    private String calculateTicketCostStatistics(){
        String result = "Survived percentage by ticket cost: \n";
        result += "0-10: " + percentageTextGenerate(getTicketCostStatistics(Constants.LOW_COST_MIN, Constants.LOW_COST_MAX));
        result += "11-30: " + percentageTextGenerate(getTicketCostStatistics(Constants.MID_COST_MIN, Constants.MID_COST_MAX));
        result += "30+: " + percentageTextGenerate(getTicketCostStatistics(Constants.HIGH_COST_MIN, Constants.HIGH_COST_MAX));
        result += "*****************\n";
        return result;
    }
    private String calculatePortStatistics(){
        String result = "Survived percentage by Port: \n";
        for (int i = 1; i < Constants.PORT_OPTIONS.length; i++){
            result += Constants.PORT_OPTIONS[i] + ": " + percentageTextGenerate(getPortStatistics(Constants.PORT_TO_CHAR[i]));
        }
        result += "*****************\n";
        return result;
    }
    private double getClassStatistics(int passengerClass){
        query.resetFilters();
        query.setPassengerClass(passengerClass);
        List<Passenger> filterByClass = query.filterPassengers(this.filteredList);
        return calculateSurvivalPercentage(filterByClass.size(), survivorsCount(filterByClass));
    }
    private double getGenderStatistics(String gender){
        query.resetFilters();
        query.setGender(gender);
        List<Passenger> filterByGender = query.filterPassengers(this.filteredList);
        return calculateSurvivalPercentage(filterByGender.size(), survivorsCount(filterByGender));
    }
    private double getAgeGroupStatistics(Double min, Double max){
        query.resetFilters();
        query.statisticsAgeAnalyze();
        query.setAgeMin(min);
        query.setAgeMax(max);
        List<Passenger> filterByAgeGroup = query.filterPassengers(this.filteredList);
        return calculateSurvivalPercentage(filterByAgeGroup.size(), survivorsCount(filterByAgeGroup));
    }
    public boolean statisticsFileAlreadyExists(){
        File file = new File(Constants.PATH_TO_FILE+Constants.STATISTICS_FILE);
        return file.exists();
    }
    private List<Passenger> deductFromList(List<Passenger> target, List<Passenger> toDeduct){
        List<Passenger> updated = new ArrayList<>();
        for (Passenger pass : target){
            if (!toDeduct.contains(pass)){
                updated.add(pass);
            }
        }
        return updated;
    }
    private double getTicketCostStatistics(Double min, Double max){
        query.resetFilters();
        query.setFareMin(min);
        query.setFareMax(max);
        List<Passenger> filterByTicketCost = query.filterPassengers(this.filteredList);
        return calculateSurvivalPercentage(filterByTicketCost.size(), survivorsCount(filterByTicketCost));
    }
    private double getPortStatistics(String port){
        query.resetFilters();
        query.setEmbarked(port);
        List<Passenger> filterByPort = query.filterPassengers(this.filteredList);
        double percent = calculateSurvivalPercentage(filterByPort.size(), survivorsCount(filterByPort));
        return percent;
    }
}
