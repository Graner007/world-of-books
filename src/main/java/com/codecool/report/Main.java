package com.codecool.report;

import com.codecool.report.config.DatabaseConnection;
import com.codecool.report.dao.LocationDao;
import com.codecool.report.dao.MarketplaceDao;
import com.codecool.report.dao.PlantDao;
import com.codecool.report.dao.StatusDao;
import com.codecool.report.dao.jdbc.LocationDaoJdbc;
import com.codecool.report.dao.jdbc.MarketplaceDaoJdbc;
import com.codecool.report.dao.jdbc.PlantDaoJdbc;
import com.codecool.report.dao.jdbc.StatusDaoJdbc;
import com.codecool.report.menu.InputHandler;
import com.codecool.report.menu.ViewMenu;
import com.codecool.report.service.LocationService;
import com.codecool.report.service.MarketplaceService;
import com.codecool.report.service.PlantService;
import com.codecool.report.service.StatusService;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, IOException, ParseException {
        DatabaseConnection db = new DatabaseConnection();
        Connection conn = db.connect();

        PlantDao plantDao = new PlantDaoJdbc(conn);
        LocationDao locationDao = new LocationDaoJdbc(conn);
        StatusDao statusDao = new StatusDaoJdbc(conn);
        MarketplaceDao marketplaceDao = new MarketplaceDaoJdbc(conn);

        StatusService statusService = new StatusService(statusDao);
        MarketplaceService marketplaceService = new MarketplaceService(marketplaceDao);
        LocationService locationService = new LocationService(locationDao);
        PlantService plantService = new PlantService(plantDao, locationDao, marketplaceDao, statusDao);

        Operation operation = new Operation(statusService, marketplaceService, locationService, plantService);

        ViewMenu.welcomeMessage();

        while (true) {
            ViewMenu.displayMenu();
            int option = InputHandler.getIntInput();

            switch (option) {
                case 1:
                    operation.fillUpDatabase();
                    break;
                case 2:
                    operation.updateDatabase();
                    break;
                case 3:
                    //TODO remove db and after fill up db
                    break;
                case 4:
                    operation.createReport();
                    break;
                case 5:
                    //TODO upload json file to ftp server
                    break;
                case 6:
                    operation.exitProgram();
            }
        }
    }
}
