module example {
    requires com.google.gson;
    requires javafx.controls;
    requires javafx.fxml;

    opens example to javafx.fxml;
    opens Entity to com.google.gson;
    exports example;
}
