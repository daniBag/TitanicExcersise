import javax.swing.*;
import java.io.File;

public class ManageScreen extends JPanel {
    private JComboBox<String> classComboBox;
    private JComboBox<String> sexComboBox;
    private JComboBox<String> embarkedInComboBox;
    private JTextField minIDFilter;
    private JTextField maxIDFilter;
    private JTextField nameFilter;
    private TextInputOneDigit siblingsPartnersFilter;
    private TextInputOneDigit childrenParentsFilter;
    private TextInputOneDigit ticketFilter;
    private JTextField fareMinFilter;
    private JTextField fareMaxFilter;
    private TextInputOneDigit cabinFilter;

    public ManageScreen(int x, int y, int width, int height) {
        File file = new File(Constants.PATH_TO_DATA_FILE);
        if (file.exists()) {
            Titanic.passengersData = new PassengersData(file);
            this.setLayout(null);
            this.setBounds(x, y + Constants.MARGIN_FROM_TOP, width, height);
            addFilters(x, y);
            JButton filter = new JButton(Constants.FILTER_BUTTON_NAME);
            filter.setBounds(width - (Constants.COMBO_BOX_WIDTH *2), this.cabinFilter.getY() + Constants.COMBO_BOX_HEIGHT*2, Constants.COMBO_BOX_WIDTH, Constants.COMBO_BOX_HEIGHT);
            filter.addActionListener((e)-> {
                getFiltered();
                filterButtonPressed();
            });
            this.add(filter);
            JButton statistics = new JButton(Constants.STATISTICS_BUTTON_NAME);
            statistics.setBounds(Constants.COMBO_BOX_WIDTH, this.cabinFilter.getY() + Constants.COMBO_BOX_HEIGHT*2, Constants.COMBO_BOX_WIDTH, Constants.COMBO_BOX_HEIGHT);
            statistics.addActionListener((e)-> {
                int selection = Constants.CONTINUE;
                if (Titanic.passengersData.statisticsFileAlreadyExists()){
                    selection = statisticsFileOverwriteWarning();
                }
                if (selection == Constants.CONTINUE){
                    getFiltered();
                    Titanic.passengersData.getStatistics();
                }
            });
            this.add(statistics);
        }
    }
    private void addFilters(int x, int y){
        JLabel classLabel = new JLabel("Passenger class: ");
        classLabel.setBounds(x + Constants.MARGIN_FROM_LEFT, y, Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
        this.add(classLabel);
        this.classComboBox = new JComboBox<>(Constants.PASSENGER_CLASS_OPTIONS);
        this.classComboBox.setBounds(classLabel.getX() + classLabel.getWidth() + 1, classLabel.getY(), Constants.COMBO_BOX_WIDTH, Constants.COMBO_BOX_HEIGHT);
        this.add(this.classComboBox);
        JLabel genderLabel = new JLabel("Gender: ");
        genderLabel.setBounds(x + Constants.MARGIN_FROM_LEFT, classLabel.getY() + classLabel.getHeight(), Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
        this.add(genderLabel);
        this.sexComboBox = new JComboBox<>(Constants.SEX_OPTIONS);
        this.sexComboBox.setBounds(genderLabel.getX() + genderLabel.getWidth() + 1, genderLabel.getY(), Constants.COMBO_BOX_WIDTH, Constants.COMBO_BOX_HEIGHT);
        this.add(this.sexComboBox);
        JLabel portLabel = new JLabel("Embarked at: ");
        portLabel.setBounds(x + Constants.MARGIN_FROM_LEFT, genderLabel.getY() + genderLabel.getHeight(), Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
        this.add(portLabel);
        this.embarkedInComboBox = new JComboBox<>(Constants.PORT_OPTIONS);
        this.embarkedInComboBox.setBounds(portLabel.getX() + portLabel.getWidth() + 1, portLabel.getY(), Constants.COMBO_BOX_WIDTH, Constants.COMBO_BOX_HEIGHT);
        this.add(this.embarkedInComboBox);
        JLabel minIdLabel = new JLabel("Passenger ID min: ");
        minIdLabel.setBounds(x + Constants.MARGIN_FROM_LEFT, portLabel.getY() + portLabel.getHeight(), Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
        this.add(minIdLabel);
        this.minIDFilter = new JTextField();
        this.minIDFilter.setBounds(minIdLabel.getX() + minIdLabel.getWidth() + 1, minIdLabel.getY(), Constants.COMBO_BOX_WIDTH, Constants.COMBO_BOX_HEIGHT);
        this.add(this.minIDFilter);
        JLabel maxIdLabel = new JLabel("Passenger ID max: ");
        maxIdLabel.setBounds(x + Constants.MARGIN_FROM_LEFT, minIdLabel.getY() + minIdLabel.getHeight(), Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
        this.add(maxIdLabel);
        this.maxIDFilter = new JTextField();
        this.maxIDFilter.setBounds(maxIdLabel.getX() + maxIdLabel.getWidth() + 1, maxIdLabel.getY(), Constants.COMBO_BOX_WIDTH, Constants.COMBO_BOX_HEIGHT);
        this.add(this.maxIDFilter);
        JLabel nameFilterLabel = new JLabel("Name contains: ");
        nameFilterLabel.setBounds(x + Constants.MARGIN_FROM_LEFT, maxIdLabel.getY() + maxIdLabel.getHeight(), Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
        this.add(nameFilterLabel);
        this.nameFilter = new JTextField();
        this.nameFilter.setBounds(nameFilterLabel.getX() + nameFilterLabel.getWidth() + 1, nameFilterLabel.getY(), Constants.COMBO_BOX_WIDTH, Constants.COMBO_BOX_HEIGHT);
        this.add(this.nameFilter);
        JLabel sibParLabel = new JLabel("Siblings/ partners amount on board: ");
        sibParLabel.setBounds(x + Constants.MARGIN_FROM_LEFT, nameFilterLabel.getY() + nameFilterLabel.getHeight(), Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
        this.add(sibParLabel);
        this.siblingsPartnersFilter = new TextInputOneDigit(sibParLabel.getX() + sibParLabel.getWidth() + 1, sibParLabel.getY());
        this.add(this.siblingsPartnersFilter);
        JLabel childrenParLabel = new JLabel("Children/ parents amount on board: ");
        childrenParLabel.setBounds(x + Constants.MARGIN_FROM_LEFT, sibParLabel.getY() + sibParLabel.getHeight(), Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
        this.add(childrenParLabel);
        this.childrenParentsFilter = new TextInputOneDigit(childrenParLabel.getX() + childrenParLabel.getWidth() + 1, childrenParLabel.getY());
        this.add(this.childrenParentsFilter);
        JLabel ticketLabel = new JLabel("Ticket contains: ");
        ticketLabel.setBounds(x + Constants.MARGIN_FROM_LEFT, childrenParLabel.getY() + childrenParLabel.getHeight(), Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
        this.add(ticketLabel);
        this.ticketFilter = new TextInputOneDigit(ticketLabel.getX() + ticketLabel.getWidth() + 1, ticketLabel.getY());
        this.add(this.ticketFilter);
        JLabel fareMinLabel = new JLabel("Ticket cost at least: ");
        fareMinLabel.setBounds(x + Constants.MARGIN_FROM_LEFT, ticketLabel.getY() + ticketLabel.getHeight(), Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
        this.add(fareMinLabel);
        this.fareMinFilter = new JTextField();
        this.fareMinFilter.setBounds(fareMinLabel.getX() + fareMinLabel.getWidth() + 1, fareMinLabel.getY(), Constants.COMBO_BOX_WIDTH, Constants.COMBO_BOX_HEIGHT);
        this.add(this.fareMinFilter);
        JLabel fareMaxLabel = new JLabel("Ticket cost less than: ");
        fareMaxLabel.setBounds(x + Constants.MARGIN_FROM_LEFT, fareMinLabel.getY() + fareMinLabel.getHeight(), Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
        this.add(fareMaxLabel);
        this.fareMaxFilter = new JTextField();
        this.fareMaxFilter.setBounds(fareMaxLabel.getX() + fareMaxLabel.getWidth() + 1, fareMaxLabel.getY(), Constants.COMBO_BOX_WIDTH, Constants.COMBO_BOX_HEIGHT);
        this.add(this.fareMaxFilter);
        JLabel cabinLabel = new JLabel("Cabin contains: ");
        cabinLabel.setBounds(x + Constants.MARGIN_FROM_LEFT, fareMaxLabel.getY() + fareMaxLabel.getHeight(), Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
        this.add(cabinLabel);
        this.cabinFilter = new TextInputOneDigit(cabinLabel.getX() + cabinLabel.getWidth() + 1, cabinLabel.getY());
        this.add(this.cabinFilter);
    }

    private void getFiltered(){
        PassengersData.query.setEmbarked(Constants.PORT_TO_CHAR[this.embarkedInComboBox.getSelectedIndex()]);
        PassengersData.query.setCabin(this.cabinFilter.getText());
        PassengersData.query.setFareMax(extractFromTextFieldToDouble(this.fareMaxFilter));
        PassengersData.query.setFareMin(extractFromTextFieldToDouble(this.fareMinFilter));
        PassengersData.query.setTicket(this.ticketFilter.getText());
        PassengersData.query.setParCh(extractFromTextField(this.childrenParentsFilter));
        PassengersData.query.setSibPar(extractFromTextField(this.siblingsPartnersFilter));
        PassengersData.query.setName(this.nameFilter.getText());
        PassengersData.query.setMaxID(extractFromTextField(this.maxIDFilter));
        PassengersData.query.setMinID(extractFromTextField(this.minIDFilter));
        PassengersData.query.setPassengerClass(this.classComboBox.getSelectedIndex());
        PassengersData.query.setGender(Constants.SEX_OPTIONS[this.sexComboBox.getSelectedIndex()]);
    }
    private void filterButtonPressed(){
        JLabel resultSummery = new JLabel();
        resultSummery.setText(Titanic.passengersData.filteringProcess());
        JOptionPane.showMessageDialog(resultSummery, resultSummery.getText());
    }
    private int statisticsFileOverwriteWarning(){
        JLabel overwriteWarning = new JLabel();
        overwriteWarning.setText(Constants.OVERWRITE_WARNING_TEXT);
        return JOptionPane.showOptionDialog(overwriteWarning, overwriteWarning.getText(), Constants.OVERWRITE_WARNING_TITLE, JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE, null, Constants.OVERWRITE_OPTIONS, Constants.OVERWRITE_OPTIONS[Constants.CONTINUE]);
    }
    private Integer extractFromTextField(JTextField textField){
        Integer result;
        if (textField.getText().length() < 1){
            result = null;
        }else{
            result = Integer.valueOf(textField.getText());
        }
        return result;
    }
    private Double extractFromTextFieldToDouble(JTextField textField){
        Double result;
        if (textField.getText().length() < 1){
            result = null;
        }else{
            result = Double.valueOf(textField.getText());
        }
        return result;
    }
}
