package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Controller {


    /*
    FUNCTION: scrapeGames
    PARAMETERS: None (For now)
    RETURN: boolean - true if successful scrape, false if any errors
    PURPOSE: Connects to j-Archive.com and downloads Jeopardy! game show information
             including categories, clues, correct responses, dollar amount, show date.
             Stores clue information in a database using JDBC
     */
    public boolean scrapeGames() {

        // try used to connect to j-Archive using jsoup library
        try {
            Document doc = Jsoup.connect("https://www.j-archive.com/showgame.php?game_id=6695").get();

            // Parse page's title to get show number for debugging purposes
            String showNum = doc.title().split("#")[1].split(",")[0];

            // System.out.printf("Title: %s\n", doc.title());

            // These 3 lines get the elements containing each round of the game
            Element jeopardyRound = doc.getElementById("jeopardy_round");
            Element doubleJeopardyRound = doc.getElementById("double_jeopardy_round");
            Element finalJeopardyRound = doc.getElementById("final_jeopardy_round");

            // These 3 lines get the categories, clues and clueDivs for the regular Jeopardy round
            // jeopardyClueDivs is the <div> containing the correct response which is hidden in JS code
            Elements jeopardyCategories = jeopardyRound.getElementsByClass("category_name");
            Elements jeopardyClues = jeopardyRound.getElementsByClass("clue_text");
            Elements jeopardyClueDivs = jeopardyRound.getElementsByClass("clue");

            // These 3 lines get the categories, clues and clueDivs for the double Jeopardy round
            // doubleJeopardyClueDivs is the <div> containing the correct response which is hidden in JS code
            Elements doubleJeopardyCategories = doubleJeopardyRound.getElementsByClass("category_name");
            Elements doubleJeopardyClues = doubleJeopardyRound.getElementsByClass("clue_text");
            Elements doubleJeopardyClueDivs = doubleJeopardyRound.getElementsByClass("clue");

            System.out.println("Jeopardy Round Categories Found " + jeopardyCategories.size());
            System.out.println("Jeopardy Round Clues Found " + jeopardyClues.size());
            System.out.println("Jeopardy Round ClueDivs Found " + jeopardyClueDivs.size());
            System.out.println("Double Jeopardy Round Categories Found " + doubleJeopardyCategories.size());
            System.out.println("Double Jeopardy Round Clues Found " + doubleJeopardyClues.size());
            System.out.println("Double Jeopardy Round ClueDivs Found " + doubleJeopardyClueDivs.size());

            if (jeopardyCategories.size() < 6 || doubleJeopardyCategories.size() < 6) {
                System.out.println("ERROR: Missing categories in show #" + showNum);
                return false;
            }
            /*


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

           */


        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;

    }

}
