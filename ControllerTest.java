package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @Test
    void scrape1GameTest() {
        Controller controller = new Controller();
        controller.scrapeGames(6696, 6697);
        assertEquals("Found in Australia, the largest of all marsupials is the great gray species of this",
                controller.getClue(1, 1));
        assertEquals("In Swahili he's your baba; in Esperanto, your patro",
                controller.getClue(1, 5));
        assertEquals("In 1993 LHJ devoted a special issue to her, saying she's still \"the most intriguing woman in the world\"",
                controller.getClue(1, 16));
        assertEquals("Fashionable colonial men covered their faces with masks while this was done to their wigs",
                controller.getClue(2, 1));
        assertEquals("The \"Valley\" in california between Palo Alto & San Jose is nicknamed after this element",
                controller.getClue(2, 15));
        assertEquals("Headings in this 1854 work include \"Solitude\", \"Brute Neighbors\" & \"The Pond in Winter\"",
                controller.getClue(3, 1));
    }

    @Test
    void scrape2GamesTest() {

        Controller controller = new Controller();
        controller.scrapeGames(6696, 6698);
        assertEquals("\"D'oh!\"",
                controller.getClue(1, 2));
        assertEquals("Steph Curry is one of many athletes who tap their chests & then do this to thank God after a successful play",
                controller.getClue(1, 10));
        assertEquals("BLANK",
                controller.getClue(1, 5));
        assertEquals("\"A spot for handing out pink slips\", or a hearth at the base of a chimney",
                controller.getClue(2, 12));
        assertEquals("\"To break God's law until a prime time hour\", or to sparkle brightly",
                controller.getClue(2, 30));
        assertEquals("One of the first recorded autopsies was performed on this man & revealed 23 puncture marks",
                controller.getClue(3, 1));

    }


}
