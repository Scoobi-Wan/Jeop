package com.company;

import java.io.IOException;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {




    public static void main(String[] args) {

        Controller controller = new Controller();
        controller.scrapeGames();

    }




}
