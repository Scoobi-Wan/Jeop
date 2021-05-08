package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    String[] jeopardyCategoriesArray;
    String[] doubleJeopardyCategoriesArray;
    ArrayList<String> jeopardyCluesAL;
    ArrayList<String> jeopardyResponsesAL;
    ArrayList<String> doubleJeopardyCluesAL;
    ArrayList<String> doubleJeopardyResponsesAL;
    Element jeopardyRound;
    Element doubleJeopardyRound;
    Element finalJeopardyRound;
    Elements jeopardyCategories;
    Elements jeopardyClues;
    Elements jeopardyClueDivs;
    Elements doubleJeopardyCategories;
    Elements doubleJeopardyClues;
    Elements doubleJeopardyClueDivs;
    Elements finalJeopardyCategory;
    Element finalJeopardyClue;
    Elements finalJeopardyClueDiv;


    /*
    FUNCTION: scrapeGames
    @param: None (For now)
    @return: boolean - true if successful scrape, false if any errors
    PURPOSE: Connects to j-Archive.com and downloads Jeopardy! game show information
             including categories, clues, correct responses, dollar amount, show date.
             Stores clue information in a database using JDBC
     */
    public boolean scrapeGames() {

        // try used to connect to j-Archive using jsoup library
        try {
            Document doc = Jsoup.connect("https://www.j-archive.com/showgame.php?game_id=6696").get();

            // Parse page's title to get show number for debugging purposes
            String showNum = doc.title().split("#")[1].split(",")[0];

            // System.out.printf("Title: %s\n", doc.title());

            // These 3 lines get the elements containing each round of the game
            jeopardyRound = doc.getElementById("jeopardy_round");
            doubleJeopardyRound = doc.getElementById("double_jeopardy_round");
            finalJeopardyRound = doc.getElementById("final_jeopardy_round");

            // These 3 lines get the categories, clues and clueDivs for the regular Jeopardy round
            // jeopardyClueDivs is the <div> containing the correct response which is hidden in JS code
            jeopardyCategories = jeopardyRound.getElementsByClass("category_name");
            jeopardyClues = jeopardyRound.getElementsByClass("clue_text");
            jeopardyClueDivs = jeopardyRound.getElementsByClass("clue");

            // These 3 lines get the categories, clues and clueDivs for the double Jeopardy round
            // doubleJeopardyClueDivs is the <div> containing the correct response which is hidden in JS code
            doubleJeopardyCategories = doubleJeopardyRound.getElementsByClass("category_name");
            doubleJeopardyClues = doubleJeopardyRound.getElementsByClass("clue_text");
            doubleJeopardyClueDivs = doubleJeopardyRound.getElementsByClass("clue");

            finalJeopardyCategory = finalJeopardyRound.getElementsByClass("category_name");
            finalJeopardyClue = finalJeopardyRound.getElementById("clue_FJ");
            finalJeopardyClueDiv = finalJeopardyRound.getElementsByClass("category");
            jeopardyCategoriesArray = new String[6];
            doubleJeopardyCategoriesArray = new String[6];
            jeopardyCluesAL = new ArrayList<String>();
            jeopardyResponsesAL = new ArrayList<String>();
            doubleJeopardyCluesAL = new ArrayList<String>();
            doubleJeopardyResponsesAL = new ArrayList<String>();

            // LOOP: Adds category names to array, removing HTML
            int catIndex = 0;
            for (Element el: jeopardyCategories) {
                jeopardyCategoriesArray[catIndex++] = el.text();
            } catIndex = 0;
            for (Element el: doubleJeopardyCategories) {
                doubleJeopardyCategoriesArray[catIndex++] = el.text();
            }

            //Mainly used for debugging + to see if missing anything in scrape

            if (jeopardyCategories.size() < 6 || doubleJeopardyCategories.size() < 6) {
                System.out.println("ERROR: Missing categories in show #" + showNum);
                return false;
            }


            populateArrays();

            cleanClues(jeopardyCluesAL);
            cleanClues(doubleJeopardyCluesAL);
            padClues(jeopardyCluesAL, jeopardyResponsesAL);
            padClues(doubleJeopardyCluesAL, doubleJeopardyResponsesAL);

            printRound(3);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;

    }

    private void printRound(int roundNum) {

        int clueIndex = 0;
        String currentCategory = "";

        switch(roundNum) {
            case 1:
                for (String clue: jeopardyCluesAL) {
                    currentCategory = jeopardyCategoriesArray[clueIndex % 6];
                    System.out.println("CATEGORY: " + currentCategory + "  CLUE: " + (clueIndex + 1) + " " + clue);
                    System.out.println("CORRECT RESPONSE: " + jeopardyResponsesAL.get(clueIndex++));
                    System.out.println();
                }
                break;
            case 2:
                for (String clue: doubleJeopardyCluesAL) {
                    currentCategory = doubleJeopardyCategoriesArray[clueIndex % 6];
                    System.out.println("CATEGORY: " + currentCategory + "  CLUE: " + (clueIndex + 1) + " " + clue);
                    System.out.println("CORRECT RESPONSE: " + doubleJeopardyResponsesAL.get(clueIndex++));
                    System.out.println();
                } break;
            case 3:
                System.out.println("CATEGORY: " + finalJeopardyCategory.text());
                System.out.println("CLUE: " + finalJeopardyClue.text());
                System.out.println("CORRECT RESPONSE: " + findResponse(finalJeopardyClueDiv.last()));
                System.out.println(finalJeopardyClueDiv.last());
        }
    }

    private void populateArrays() {
        for (Element clue: jeopardyClues) {
            jeopardyCluesAL.add(String.valueOf(clue));
        }

        for (Element clue: doubleJeopardyClues) {
            doubleJeopardyCluesAL.add(String.valueOf(clue));
        }

        for (Element clueDiv: jeopardyClueDivs) {
            jeopardyResponsesAL.add(findResponse(clueDiv));
        }

        for (Element clueDiv: doubleJeopardyClueDivs) {
            doubleJeopardyResponsesAL.add(findResponse(clueDiv));
        }

    }


    private void checkData() {
        System.out.println("Jeopardy Round Categories Found " + jeopardyCategories.size());
        System.out.println("Jeopardy Round Clues Found " + jeopardyClues.size());
        System.out.println("Jeopardy Round ClueDivs Found " + jeopardyClueDivs.size());
        System.out.println("Double Jeopardy Round Categories Found " + doubleJeopardyCategories.size());
        System.out.println("Double Jeopardy Round Clues Found " + doubleJeopardyClues.size());
        System.out.println("Double Jeopardy Round ClueDivs Found " + doubleJeopardyClueDivs.size());
    }



    /*
    FUNCTION: findResponse
    @param: Element clueDiv - containing the correct response as a JS string within
    @return: String - the parsed correct response
     */
    private String findResponse(Element clueDiv) {

        String correctResponse = "";
        String[] clueDivPieces = String.valueOf(clueDiv).split("correct_response&quot;>");
        if (clueDivPieces.length > 1) {
            correctResponse = clueDivPieces[1].replace("&lt;i&gt;", "");
            correctResponse = correctResponse.replace("&lt;//i&gt;", "");
            correctResponse = correctResponse.replace("&amp;", "&");
            correctResponse = correctResponse.replace("&quot;", "\"");
            correctResponse = correctResponse.replace("<i>", "");
            correctResponse = correctResponse.replace("</i>", "");
            clueDivPieces = correctResponse.split("&lt;");
            clueDivPieces = clueDivPieces[0].split("</em>");
            correctResponse = clueDivPieces[0];
        }
        return correctResponse;
    }

    private void padClues(ArrayList<String> clueArray, ArrayList<String> responseArray) {
        if (clueArray.size() == 30) {
            return;
        } else {
            int index = 0;
            while (index < 30) {
                if (responseArray.get(index).equals("")) {
                    clueArray.add(index, "BLANK");
                }
                index++;
            }
        }
    }

    private void cleanClues(ArrayList<String> clueArray) {
        int index = 0;
        while (index < clueArray.size()) {
            String clue = clueArray.get(index);
            clue = clue.replace("<span class=\"nobreak\">", "");
            clue = clue.replace("</span>", "");
            clue = clue.replace("&amp;", "&");

            // remove clue if it contains an anchor link - set to BLANK
            if (clue.contains("a href")) {
                clueArray.set(index, "BLANK");
                index++;
                continue;
            }
            String[] splitClue = clue.split(">");
            clue = splitClue[1];
            splitClue = clue.split("</");
            clue = splitClue[0];
            clueArray.set(index, clue);
            index++;
        }
    }


}
