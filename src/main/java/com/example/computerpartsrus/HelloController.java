// Programmer: Ethan Olivieri
// Date: 3/7/23 to 3/31/23
// Purpose: Create an order form to track Computer Parts R-Us purchases from an ordering system, setup a GUI panel-type form, enter each set of given test data, perform appropriate calculations, display results in a table on the GUI panel, and output a report

package com.example.computerpartsrus;

// Import Statements
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.UnaryOperator;

public class HelloController
{
    @FXML
    private Button btnInformation;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtCity;
    @FXML
    private TextField txtState;
    @FXML
    private TextField txtZip;
    @FXML
    private TextField txtDate;
    @FXML
    private TextField txtQuantity;
    @FXML
    private ComboBox<String> cbxItem;
    @FXML
    private ComboBox<String> cbxType;
    @FXML
    private Button btnItems;
    @FXML
    private Label lblOutput;
    @FXML
    private Button btnFinish;
    @FXML
    private Label lblDaysOutput;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnPrint;
    @FXML
    private Button btnClose;
    @FXML
    private CheckBox chbxMember;
    public static final double TAX = 0.06;
    public static final double NONMEMBERFEE = 0.015;
    public static boolean member = false;
    public static String name = "";
    public static String address = "";
    public static String city = "";
    public static String state = "";
    public static String zip = "";
    public static Text print;
    public static int quantity = 0;
    public static String item = "";
    public static String type = "";
    public static int x = 0;
    public static int y = 0;
    public static int z = 0;
    public static int fontSize = 12;
    public static int totalDaysQuantity = 0;
    public static double totalDaysProfit = 0.0;
    public static double unitCost = 0.0;
    public static double retailPrice = 0.0;
    public static double profit = 0.0;
    public static double subTotal = 0.0;
    public static double totalProfit = 0.0;
    public static double totalPreTax = 0.0;
    public static double salesTax = 0.0;
    public static double orderWithTax = 0.0;
    public static double processingFees = 0.00;
    public static double orderTotal = 0.0;
    public static DecimalFormat df = new DecimalFormat("#0.00");
    public static String[] items = { "ACME Laptop PC", "ACME Desktop PC", "ACME Monitor", "Hard Drive(s) 500 GB", "Wacky Wireless Mouse", "Monster Memory Sticks", "GForce Graphics Cards", "Monster Motherboards" };
    public static String[][] types = { { " ", " ", " ", "IDE", "Blue", "32GB", "Extreme", "Elite Series" }, { " ", " ", " ", "SATA", "Black", "64GB", " ", " " }, { " ", " ", " ", "SSD", "Pink", "128GB", " ", " " } };
    public static double[][] unitCosts = { { 454.54, 818.17, 159.09, 32.71, 13.45, 63.63, 145.45, 109.08 }, { 0.0, 0.0, 0.0, 37.64, 12.17, 107.75, 0.0, 0.0 }, { 0.0, 0.0, 0.0, 54.54, 13.22, 261.82, 0.0, 0.0 } };
    public static double[][] retailPrices = { { 499.99, 899.99, 175.00, 35.98, 14.79, 69.99, 159.99, 119.99 }, { 0.0, 0.0, 0.0, 41.40, 13.39, 118.52, 0.0, 0.0 }, { 0.0, 0.0, 0.0, 59.99, 14.54, 288.00, 0.0, 0.0 } };
    public static double[][] profits = { { 45.45, 81.82, 15.91, 3.27, 1.34, 6.36, 14.54, 10.91 }, { 3.76, 1.22, 0.0, 0.0, 0.0, 10.77, 0.0, 0.0 }, { 0.0, 0.0, 0.0, 5.45, 1.32, 26.18, 0.0, 0.0 } };
    public void onbtnInformationClick()
    {
        txtQuantity.setDisable(false);
        cbxItem.setDisable(false);
        txtName.setDisable(true);
        txtAddress.setDisable(true);
        txtCity.setDisable(true);
        txtState.setDisable(true);
        txtZip.setDisable(true);
        btnInformation.setDisable(true);
        chbxMember.setDisable(true);
        if(chbxMember.isSelected())
        {
            member = true;
        }
        name = txtName.getText();
        address = txtAddress.getText();
        city = txtCity.getText();
        state = txtState.getText();
        zip = txtZip.getText();
    }
    public void onbtnItemsClick()
    {
        quantity = Integer.parseInt(txtQuantity.getText());
        item = cbxItem.getValue();
        type = cbxType.getValue();
        for(x = 0; x < items.length; ++x)
        {
            if(item.equals(items[x]))
            {
                if(x < 3)
                {
                    unitCost = unitCosts[0][x];
                    retailPrice = retailPrices[0][x];
                    profit = profits[0][x];
                }
                else
                {
                    for (y = 0; y < types.length; ++y)
                    {
                        for (z = 0; z < types[y].length; ++z)
                        {
                            if (types[y][z].equals(type))
                            {
                                unitCost = unitCosts[y][z];
                                retailPrice = retailPrices[y][z];
                                profit = profits[y][z];
                            }
                        }
                    }
                }
            }
        }
        profit *= quantity;
        subTotal = quantity * retailPrice;
        totalProfit += profit;
        totalPreTax += subTotal;
        totalDaysQuantity += quantity;
        totalDaysProfit += profit;
        if(type == null)
        {
            lblOutput.setText(lblOutput.getText() + "Item: " + item + "      Quantity: " + quantity + "      Type: None\nUnit Cost: $" + (df.format(unitCost)) + "      Retail Price: $" + (df.format(retailPrice)) + "      Profit: $" + (df.format(profit)) + "     Sub-Total: $" + (df.format(subTotal) + "\n\n"));
        }
        else
        {
            lblOutput.setText(lblOutput.getText() + "Item: " + item + "      Quantity: " + quantity + "      Type: " + type + "\nUnit Cost: $" + (df.format(unitCost)) + "      Retail Price: $" + (df.format(retailPrice)) + "      Profit: $" + (df.format(profit)) + "     Sub-Total: $" + (df.format(subTotal) + "\n\n"));
        }
        btnFinish.setDisable(false);
        txtQuantity.setText("");
        btnItems.setDisable(true);
        chbxMember.setDisable(true);
    }
    public void onbtnFinishClick()
    {
        salesTax = totalPreTax * TAX;
        orderWithTax = totalPreTax + salesTax;
        if(!(totalPreTax < 50) && !(member))
        {
            processingFees = totalPreTax * NONMEMBERFEE;
        }
        orderTotal = orderWithTax + processingFees;
        lblOutput.setText(lblOutput.getText() + "\n\n\n\nTotals: \nTotal Profit: $" + (df.format(totalProfit)) + "      Total: $" + (df.format(totalPreTax)) + "\nSales Tax: $" + (df.format(salesTax)) + "      Order With Tax: $" + (df.format(orderWithTax)) + "      Processing Fees: $" + (df.format(processingFees)) + "      Order Total: $" + (df.format(orderTotal)));
        lblDaysOutput.setText("Cumulative Day's Quantity: " + totalDaysQuantity + "      Cumulative Day's Profit: $" + (df.format(totalDaysProfit)));
        btnPrint.setDisable(false);
        btnClear.setDisable(false);
        btnFinish.setDisable(true);
        txtQuantity.setDisable(true);
        cbxItem.setDisable(true);
        cbxType.setDisable(true);
        chbxMember.setDisable(false);
    }
    public void onbtnPrintClick()
    {
        if(member)
        {
            print = new Text("\n" + txtDate.getText() + "\n\n\n\nName: " + name + "      Address: " + address + "\nCity: " + city + "      State: " + state + "      Zip: " + zip + "      Member: Yes\n\n\n\n" + lblOutput.getText() + "\n\n\n\n" + lblDaysOutput.getText());
        }
        else
        {
            print = new Text("\n" + txtDate.getText() + "\n\n\n\nName: " + name + "      Address: " + address + "\nCity: " + city + "      State: " + state + "      Zip: " + zip + "      Member: No\n\n\n\n" + lblOutput.getText() + "\n\n\n\n" + lblDaysOutput.getText());
        }
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null)
        {
            boolean success = job.printPage(print);
            if (success)
            {
                job.endJob();
            }
        }
    }
    public void onbtnClearClick()
    {
        String name = "";
        String address = "";
        String city = "";
        String state = "";
        String zip = "";
        String print = "";
        int quantity = 0;
        String item = "";
        String type = "";
        int x = 0;
        int y = 0;
        int z = 0;
        int totalDaysQuantity = 0;
        double totalDaysProfit = 0.0;
        double unitCost = 0.0;
        double retailPrice = 0.0;
        double profit = 0.0;
        double subTotal = 0.0;
        double totalProfit = 0.0;
        double totalPreTax = 0.0;
        double salesTax = 0.0;
        double orderWithTax = 0.0;
        double processingFees = 0.0;
        double orderTotal = 0.0;
        lblOutput.setText("");
        lblDaysOutput.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtCity.setText("");
        txtState.setText("");
        txtZip.setText("");
        txtName.setDisable(false);
        txtAddress.setDisable(false);
        txtCity.setDisable(false);
        txtState.setDisable(false);
        txtZip.setDisable(false);
        btnPrint.setDisable(true);
        btnClear.setDisable(true);
        cbxItem.getSelectionModel().select(null);
        cbxType.getSelectionModel().select(null);
    }
    public void onbtnCloseClick()
    {
        System.exit(0);
    }
    public void oncbxItemEnter()
    {
        cbxType.getItems().setAll();
        item = cbxItem.getValue();
        for(x = 0; x < items.length; ++x)
        {
            if(items[x].equals(item))
            {
                if(x >= 6)
                {
                    cbxType.setDisable(false);
                    cbxType.getItems().addAll(types[0][x]);
                    cbxType.getSelectionModel().select(types[0][x]);
                    cbxType.setEditable(false);
                    break;
                }
                else if(x < 6 && x >= 3)
                {
                    cbxType.setDisable(false);
                    for(y = 0; y <= 2; ++y)
                    {
                        cbxType.getItems().addAll(types[y][x]);
                    }
                    break;
                }
                else if(x < 3)
                {
                    break;
                }
            }
        }
        if((cbxType.getValue() != null || x == 0 || x == 1 || x == 2) && cbxItem.getValue() != null && txtQuantity.getText() != "")
        {
            btnItems.setDisable(false);
        }
        else
        {
            btnItems.setDisable(true);
        }
    }
    public void oncbxTypeEnter()
    {
        if((cbxType.getValue() != null || x == 0 || x == 1 || x == 2) && cbxItem.getValue() != null && txtQuantity.getText() != "")
        {
            btnItems.setDisable(false);
        }
        else
        {
            btnItems.setDisable(true);
        }
    }
    public void ontxtNameClick()
    {
        if(txtName.getText() != "" && txtAddress.getText() != "" && txtCity.getText() != "" && txtState.getText() != "" && txtZip.getText() != "")
        {
            btnInformation.setDisable(false);
        }
        else
        {
            btnInformation.setDisable(true);
        }
    }
    public void ontxtAddressClick()
    {
        if(txtName.getText() != "" && txtAddress.getText() != "" && txtCity.getText() != "" && txtState.getText() != "" && txtZip.getText() != "")
        {
            btnInformation.setDisable(false);
        }
        else
        {
            btnInformation.setDisable(true);
        }
    }
    public void ontxtCityClick()
    {
        if(txtName.getText() != "" && txtAddress.getText() != "" && txtCity.getText() != "" && txtState.getText() != "" && txtZip.getText() != "")
        {
            btnInformation.setDisable(false);
        }
        else
        {
            btnInformation.setDisable(true);
        }
    }
    public void ontxtStateClick()
    {
        if(txtName.getText() != "" && txtAddress.getText() != "" && txtCity.getText() != "" && txtState.getText() != "" && txtZip.getText() != "")
        {
            btnInformation.setDisable(false);
        }
        else
        {
            btnInformation.setDisable(true);
        }
    }
    public void ontxtZipClick()
    {
        if(txtName.getText() != "" && txtAddress.getText() != "" && txtCity.getText() != "" && txtState.getText() != "" && txtZip.getText() != "")
        {
            btnInformation.setDisable(false);
        }
        else
        {
            btnInformation.setDisable(true);
        }
    }
    public void ontxtQuantityClick()
    {
        if(txtQuantity.getLength() != 0)
        {
            UnaryOperator<TextFormatter.Change> filter10 = c -> {
                String newText = c.getControlNewText();
                if (newText.matches("[0-9]{0,2}")) {
                    c.setText(newText);
                    c.setRange(0, txtQuantity.getText().length());
                    return c ;
                } else if (newText.isEmpty()) {
                    return c ;
                }
                return null ;
            };
            txtQuantity.setTextFormatter(new TextFormatter<String>(filter10));
        }
        else
        {
            UnaryOperator<TextFormatter.Change> filter10 = c -> {
                String newText = c.getControlNewText();
                if (newText.matches("[1-9]{0,2}")) {
                    c.setText(newText);
                    c.setRange(0, txtQuantity.getText().length());
                    return c ;
                } else if (newText.isEmpty()) {
                    return c ;
                }
                return null ;
            };
            txtQuantity.setTextFormatter(new TextFormatter<String>(filter10));
        }
        if((cbxType.getValue() != null || x == 0 || x == 1 || x == 2) && cbxItem.getValue() != null && txtQuantity.getText() != "")
        {
            btnItems.setDisable(false);
        }
        else
        {
            btnItems.setDisable(true);
        }
    }
    public void initialize() throws IOException
    {
        cbxItem.getItems().addAll(items);
        UnaryOperator<TextFormatter.Change> filter = c -> {
            String newText = c.getControlNewText();
            if (newText.matches("[A-Za-z]{0,2}")) {
                c.setText(newText.toUpperCase());
                c.setRange(0, txtState.getText().length());
                return c ;
            } else if (newText.isEmpty()) {
                return c ;
            }
            return null ;
        };
        txtState.setTextFormatter(new TextFormatter<String>(filter));
        txtZip.textProperty().addListener(new ChangeListener<String>()
        {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                String newString = "";
                if (!newValue.matches("\\d*"))
                {
                    txtZip.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if(txtZip.getText().length() > 5)
                {
                    newString = txtZip.getText().substring(0, txtZip.getText().length() - 1);
                    txtZip.setText(newString);
                }
            }
        });
        UnaryOperator<TextFormatter.Change> filter4 = c -> {
            String newText = c.getControlNewText();
            if (newText.matches("[1-9]{0,2}")) {
                c.setText(newText);
                c.setRange(0, txtQuantity.getText().length());
                return c ;
            } else if (newText.isEmpty()) {
                return c ;
            }
            return null ;
        };
        txtQuantity.setTextFormatter(new TextFormatter<String>(filter4));
        UnaryOperator<TextFormatter.Change> filter2 = c -> {
            String newText = c.getControlNewText();
            if (newText.matches("[A-Za-z ]{0,30}")) {
                c.setText(newText);
                c.setRange(0, txtName.getText().length());
                return c ;
            } else if (newText.isEmpty()) {
                return c ;
            }
            return null ;
        };
        txtName.setTextFormatter(new TextFormatter<String>(filter2));
        UnaryOperator<TextFormatter.Change> filter3 = c -> {
            String newText = c.getControlNewText();
            if (newText.matches("[A-Za-z]{0,20}")) {
                c.setText(newText);
                c.setRange(0, txtCity.getText().length());
                return c ;
            } else if (newText.isEmpty()) {
                return c ;
            }
            return null ;
        };
        txtName.setTextFormatter(new TextFormatter<String>(filter2));
        UnaryOperator<TextFormatter.Change> filter6 = c -> {
            String newText = c.getControlNewText();
            if (newText.matches("[A-Za-z ]{0,20}")) {
                c.setText(newText);
                c.setRange(0, txtCity.getText().length());
                return c ;
            } else if (newText.isEmpty()) {
                return c ;
            }
            return null ;
        };
        txtCity.setTextFormatter(new TextFormatter<String>(filter6));
        UnaryOperator<TextFormatter.Change> filter5 = c -> {
            String newText = c.getControlNewText();
            if (newText.matches("[A-Za-z 0-9]{0,50}")) {
                c.setText(newText);
                c.setRange(0, txtAddress.getText().length());
                return c ;
            } else if (newText.isEmpty()) {
                return c ;
            }
            return null ;
        };
        txtAddress.setTextFormatter(new TextFormatter<String>(filter5));
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
                txtDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
        ),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
}