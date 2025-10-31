package util;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {

    public static void main (String[] args){
        String ruta = new File("").getAbsolutePath()+"/target/";
        File report = new File(ruta+"ABCucumber");

        List<String> jsonList= new ArrayList<String>();
        jsonList.add(ruta+"cucumber-report.json");

        Configuration config = new Configuration(report,"AB");
        config.setBuildNumber("1.0");
        config.addClassifications("Owner","TestOwner");
        config.addClassifications("SO","Windows");

        ReportBuilder reportBuilder = new ReportBuilder(jsonList,config);
        reportBuilder.generateReports();
    }
}
