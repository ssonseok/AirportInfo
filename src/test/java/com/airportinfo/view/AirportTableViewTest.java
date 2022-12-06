package com.airportinfo.view;

import com.airportinfo.controller.AirportController;
import com.airportinfo.controller.UserController;
import com.airportinfo.model.Airport;
import com.airportinfo.util.AirportWikiCrawler;
import com.airportinfo.util.Translator;
import com.airportinfo.view.airport.AirportTableView;
import org.jsoup.HttpStatusException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AirportTableViewTest {
    public static void main(String[] args) {
        AirportFrame mainFrame = new AirportFrame();
        mainFrame.showFrame();
        mainFrame.load();
        AirportController airportController = mainFrame.getAirportController();
        UserController userController = mainFrame.getUserController();
        TestContentView contentView = new TestContentView();
        AirportTableView airportTableView = new AirportTableView();
        contentView.setComponent(airportTableView);

        mainFrame.addContentView("AirportTableView", contentView);
        mainFrame.setContentView("AirportTableView");

        airportTableView.setAirports(List.of(airportController.getAirports()));
        airportTableView.addMouseClickAction((mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2) {
                Airport selected = airportTableView.getSelectedAirport();
                userController.addBookmark(selected);
                crawlAirportInfo(selected);
            }
        });
    }

    private static void crawlAirportInfo(Airport airport) {
        try {
            AirportWikiCrawler crawler = new AirportWikiCrawler(airport);
            String info = crawler.getInformation();
            String[] images = crawler.getImages(3);
            System.out.println(info);
            System.out.println(Arrays.toString(images));
        } catch (HttpStatusException e) {
            System.out.println(Translator.getBundleString("not_found"));
        } catch (IOException e) {
            System.out.println(Translator.getBundleString("connection_failed"));
        }
    }
}
