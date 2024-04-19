module be.anb.rimex.m2mconnect.view {
	requires be.anb.rimex.m2mconnect.common;
	requires be.anb.rimex.m2mconnect.service;
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires org.apache.logging.log4j;
	requires java.prefs;
	opens be.anb.rimex.m2mconnect.view  to javafx.fxml;
    exports be.anb.rimex.m2mconnect.view ;
	exports be.anb.rimex.m2mconnect.view.entity ;
	opens be.anb.rimex.m2mconnect.view.entity  to javafx.fxml;
	exports be.anb.rimex.m2mconnect.view.service;
	opens be.anb.rimex.m2mconnect.view.service to javafx.fxml;
}