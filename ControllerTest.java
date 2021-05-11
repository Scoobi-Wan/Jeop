package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @Test
    void scrape1GameTest() {
        Controller controller = new Controller();
        controller.scrapeGames();
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

}
