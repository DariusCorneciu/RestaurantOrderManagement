package com.example.restaurantordermanagement.controllers;

import com.example.restaurantordermanagement.models.*;
import com.example.restaurantordermanagement.service.*;
import com.example.restaurantordermanagement.utils.AppContext;
import com.example.restaurantordermanagement.utils.ElementNotFoundException;
import com.example.restaurantordermanagement.utils.EmptyStringException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TabelMateController {

    @FXML
    private Pane tablePane;

    @FXML
    private HBox hBoxOrders;

    private TableService tableService;
    private EmployeeService employeeService;
   private ClockingService clockingService;
   private OrderService orderService;
   private ProductOrderService productOrderService;
   private ProductService productService;

    @FXML
    public void initialize() {
        clockingService = AppContext.getClockingService();
        employeeService = AppContext.getEmployeeService();
       tableService = AppContext.getTableService();
        orderService = AppContext.getOrderService();
        productOrderService = AppContext.getProductOrderService();
        productService = AppContext.getProductService();


        List<Table> tables = tableService.getAllTable();
        for(Table t:tables){
            addMasaButton(t.getName(),t.getxPercent(),t.getyPercent(),t.getId());
        }
        refreshOrders();
    }

    public void refreshOrders(){
        hBoxOrders.getChildren().clear();
        List<Order> orderList = orderService.getAllActiveOrders();
        for(Order o:orderList){
            addOrderVbox(o);
        }
    }
    private void addMasaButton(String label, double xPercent, double yPercent, int tableId) {
        Button tableButton = new Button(label);

        int orderid = tableService.hasActiveOrder(tableId);
        if(orderid != -1){
            tableButton.setStyle(" -fx-background-color: red");
            tableButton.setOnAction(e->handleClickReserved(tableButton,tableId,orderid));

        }else{
            tableButton.setStyle(" -fx-background-color: limegreen");
            tableButton.setOnAction(e->handleClicknotReserved(tableButton,tableId));

        }

        tableButton.layoutXProperty().bind(tablePane.widthProperty().multiply(xPercent));
        tableButton.layoutYProperty().bind(tablePane.heightProperty().multiply(yPercent));

        tableButton.setPrefSize(80, 40);
        tablePane.getChildren().add(tableButton);
    }

    private void handleClickReserved(Button tableButton,int tableId,int orderId){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Table: " + tableButton.getText());
        dialog.setHeaderText("Finish order!");
        ButtonType checkCode = new ButtonType("Finish Order");
        ButtonType cancel = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);

        dialog.getDialogPane().getButtonTypes().addAll(checkCode, cancel);
        dialog.showAndWait().ifPresent(type -> {


            if (type == checkCode) {
                tableButton.setStyle("-fx-background-color:limegreen");
                orderService.closeOrder(orderId);
                refreshOrders();
                tableButton.setOnAction(e->handleClicknotReserved(tableButton,tableId));

                String code = java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 8);
                AppContext.getReceiptService().create(new Receipt(-1,orderId,code, LocalDate.now(),0));

            } else if (type == cancel) {
               }
        });
    }

    private void handleClicknotReserved(Button tableButton,int tableId) {

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Table: " + tableButton.getText());
            dialog.setHeaderText("Employee authentification required!");

            VBox box =new VBox(10);
            Label fieldName = new Label("Employee Code");
            Label errorText = new Label("");
            errorText.setStyle("-fx-text-fill: red");

            TextField employeeCode = new TextField();
            employeeCode.setPromptText("Enter the code");
            box.getChildren().addAll(errorText,fieldName,employeeCode);
            dialog.getDialogPane().setContent(box);

            ButtonType checkCode = new ButtonType("Check Code");
            ButtonType cancel = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);

            dialog.getDialogPane().getButtonTypes().addAll(checkCode, cancel);

            Button check =(Button) dialog.getDialogPane().lookupButton(checkCode);




        AtomicInteger orderIdHolder = new AtomicInteger(-1);

        check.addEventFilter(ActionEvent.ACTION, event -> {
            try {
                String employeeIdString = employeeCode.getText();
                if (employeeIdString.isBlank()) {
                    throw new EmptyStringException("Employee Code is empty!");
                }

                int employeeId = Integer.parseInt(employeeIdString);
                employeeService.findEmployeeById(employeeId);

                if (!clockingService.alreadyClockedIn(employeeId)) {
                    throw new ElementNotFoundException("Employee not clocked in!");
                }

                Order newOrder = new Order(-1, tableId, employeeId, true);
                orderService.create(newOrder);

                orderIdHolder.set(newOrder.getId());

            } catch (Exception e) {
                errorText.setText(e.getMessage());
                event.consume();
            }
        });



        dialog.showAndWait().ifPresent(type -> {
        String codeText = employeeCode.getText();

        if (type == checkCode) {
            tableButton.setStyle("-fx-background-color:red");
            refreshOrders();



            tableButton.setOnAction(e->handleClickReserved(tableButton,tableId,orderIdHolder.get()));
            System.out.println("Add order for " + tableButton.getText() + " by employee " + codeText);

        } else if (type == cancel) {
            System.out.println("View order for " + tableButton.getText() + " by employee " + codeText);
        }
    });
        }

        private void addOrderVbox(Order order){
        VBox box = new VBox(10);
        box.setAlignment(Pos.TOP_CENTER);
        box.setPrefSize(150, 300);

        box.setStyle(
                "-fx-border-color: #cccccc;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 6;" +
                        "-fx-background-color: #f9f9f9;" +
                        "-fx-background-radius: 6;"
        );
        box.setPadding(new Insets(10, 10, 10, 10));

        Label title = new Label(order.getTable().getName());
        title.setTextAlignment(TextAlignment.CENTER);
        title.setWrapText(true);

        Label by = new Label("By " + order.getEmployee().getFullName());
        by.setWrapText(true);

        Separator sep = new Separator();
        sep.setPrefWidth(200);

        Button addButton = new Button("Add Something");
        addButton.setOnAction(e->handleAddProductToOrder(order));

        box.getChildren().addAll(title, by, sep, addButton);

        hBoxOrders.getChildren().add(box);
    }

    private void handleAddProductToOrder(Order order){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add Products to Order:");
        dialog.setHeaderText("Products");
        ButtonType checkCode = new ButtonType("Complete Add!");
        ButtonType cancel = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
        Map<Product, Integer> quantityMap = new HashMap<>();
        VBox box = createProductList(order,quantityMap);
        ///  sus trimit referinta de la quantityMap-ul meu deci daca se face update isi face si el in asta singur
        dialog.getDialogPane().setContent(box);

        dialog.getDialogPane().getButtonTypes().addAll(checkCode, cancel);
        dialog.showAndWait().ifPresent(type -> {


            if (type == checkCode) {
                productOrderService.adjustProductQuantities(order,quantityMap);

            } else if (type == cancel) {
            }
        });
    }

    private VBox createProductList(Order order, Map<Product, Integer> quantityMap){
        VBox box =new VBox(10);
        Label errorText = new Label("");
        errorText.setStyle("-fx-text-fill: red");
        List<ProductOrder> list = order.getProducts();
        List<Product> products = productService.getAllProducts();
        System.out.println("Intru iar aici");
        for (Product product:products){

            int counter = list.stream()
                    .filter(e -> e.getProductId() == product.getId())
                    .mapToInt(ProductOrder::getQuantity)
                    .sum();

            quantityMap.put(product, counter);

            HBox hBox = new HBox(10);
            Button leftButton = new Button("-");
            Button rightButton = new Button("+");
            Label item = new Label(product.getProductName());
            String counterString = Integer.toString(counter);
            Label quantity = new Label(counterString);

            leftButton.setOnAction(e -> {
                int qty = quantityMap.get(product);
                if (qty > 0){
                    qty--;
                };
                quantityMap.put(product, qty);
                quantity.setText(String.valueOf(qty));
            });

            rightButton.setOnAction(e -> {
                int qty = quantityMap.get(product) + 1;
                quantityMap.put(product, qty);
                quantity.setText(String.valueOf(qty));
            });

            hBox.getChildren().addAll(item, leftButton, quantity, rightButton);
            box.getChildren().add(hBox);
        }

        box.getChildren().add(new Separator());
        return box;
    }

    }

