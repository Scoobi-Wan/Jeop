package com.company;

import java.io.IOException;
import java.util.Arrays;

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
            Elements clueDivs = doc.getElementsByClass("clue");

            for (Element category: categories) {
                System.out.println(category.text());
            }

            for (Element clue: clues) {
                System.out.println(clue.text());
            }

            for (Element clueDiv: clueDivs) {
                String[] clueDivPieces = String.valueOf(clueDiv).split("correct_response&quot;>");
                if (clueDivPieces.length > 1) {
                    String correctResponse = clueDivPieces[1].replace("&lt;i&gt;", "");
                    correctResponse = correctResponse.replace("&lt;//i&gt;", "");
                    clueDivPieces = correctResponse.split("&lt;");
                    correctResponse = clueDivPieces[0].replace("&amp;", "&");
                    correctResponse = correctResponse.replace("&quot;", "\"");
                    correctResponse = correctResponse.replace("<i>", "");
                    correctResponse = correctResponse.replace("</i>", "");
                    clueDivPieces = correctResponse.split("</em>");
                    correctResponse = clueDivPieces[0];

                    System.out.println(correctResponse);
                }

            }




        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
