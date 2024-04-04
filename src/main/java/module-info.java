module org.groupwork.donation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires mysql.connector.j;
    requires org.kordamp.ikonli.javafx;

    opens org.groupwork.donation to javafx.fxml;
    opens org.groupwork.donation.Controllers.Auth to javafx.fxml;

    exports org.groupwork.donation;
    exports org.groupwork.donation.Models;
    exports org.groupwork.donation.Views;
    exports org.groupwork.donation.Controllers.Admin;
    exports org.groupwork.donation.Controllers.Donor;
    exports org.groupwork.donation.Controllers.Auth;
    exports org.groupwork.donation.Controllers.Recipient;
}