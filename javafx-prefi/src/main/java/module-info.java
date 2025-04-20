module aclcbukidnon.com.javafxactivity {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.scripting;


    opens aclcbukidnon.com.javafxactivity to javafx.fxml;
    opens aclcbukidnon.com.javafxactivity.controllers to javafx.fxml;
    exports aclcbukidnon.com.javafxactivity;
}