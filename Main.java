package com.company;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

    public static void main(String[] args) {

        try {
            Document doc = Jsoup.connect("https://www.j-archive.com/showgame.php?game_id=6695").get();

            System.out.printf("Title: %s\n", doc.title());

            Elements categories = doc.getElementsByClass("category_name");
            Elements clues = doc.getElementsByClass("clue_text");
            Elements responses = doc.getElementsContainingText("correct_response");

            for (Element category: categories) {
                System.out.println(category.text());
            }

            for (Element clue: clues) {
                System.out.println(clue.text());
            }

            for (Element response: responses) {
                System.out.println(response.text());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
