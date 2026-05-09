package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.council.CouncilEngine;
import com.narxoz.rpg.council.CouncilRunResult;
import com.narxoz.rpg.guild.Captain;
import com.narxoz.rpg.guild.GuildHall;
import com.narxoz.rpg.guild.Healer;
import com.narxoz.rpg.guild.Loremaster;
import com.narxoz.rpg.guild.Quartermaster;
import com.narxoz.rpg.guild.Scout;
import com.narxoz.rpg.quest.Quest;
import com.narxoz.rpg.quest.QuestLog;
import com.narxoz.rpg.quest.QuestPriority;

import java.util.List;

/**
 * Entry point for Homework 10 — The Adventurers' Guild: Iterator + Mediator.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Homework 10 Demo: Iterator + Mediator ===\n");

        // 1. Create heroes
        Hero aragorn = new Hero("Aragorn", 120, 40, 80, 25, 500);
        Hero gandalf = new Hero("Gandalf", 80, 150, 30, 60, 15, 800);
        List<Hero> party = List.of(aragorn, gandalf);

        // 2. Build QuestLog with 5+ quests of mixed priority
        QuestLog questLog = new QuestLog();
        questLog.add(new Quest("Clear Goblin Caves",       QuestPriority.NORMAL, 200,  false));
        questLog.add(new Quest("Escort Merchant to Bree",  QuestPriority.LOW,    100,  false));
        questLog.add(new Quest("Destroy the One Ring",     QuestPriority.URGENT, 5000, true));
        questLog.add(new Quest("Investigate Cursed Ruins", QuestPriority.HIGH,   750,  false));
        questLog.add(new Quest("Rescue Captured Scouts",   QuestPriority.HIGH,   600,  true));
        questLog.add(new Quest("Collect Rare Herbs",       QuestPriority.LOW,    150,  false));
        questLog.add(new Quest("Defeat Dragon of Erebor",  QuestPriority.URGENT, 3000, true));

        // 3. Set up GuildHall mediator and register 5 guild members
        GuildHall hall = new GuildHall();
        Captain       captain       = new Captain("Boromir",            hall);
        Scout         scout         = new Scout("Legolas",              hall);
        Healer        healer        = new Healer("Elrond",              hall);
        Quartermaster quartermaster = new Quartermaster("Gimli",        hall);
        Loremaster    loremaster    = new Loremaster("Gandalf the Grey", hall);

        System.out.println("=== Guild members registered ===");
        System.out.println("  Captain: "       + captain.getName());
        System.out.println("  Scout: "         + scout.getName());
        System.out.println("  Healer: "        + healer.getName());
        System.out.println("  Quartermaster: " + quartermaster.getName());
        System.out.println("  Loremaster: "    + loremaster.getName());

        // 4 & 5. Run CouncilEngine: iterates quests and dispatches mediator messages
        CouncilEngine engine = new CouncilEngine();
        CouncilRunResult result = engine.runCouncil(party, questLog, hall);

        // 6. Extra mediator interactions to show routing
        System.out.println("\n=== Additional Mediator Demo ===");
        scout.reportRoute("ROUTE_REPORT", "Mordor approach through Emyn Muil is clear");
        captain.issueOrder("DANGER_ALERT", "Nazgul spotted near Weathertop!");
        loremaster.shareLore("CURSE_DETECTED", "Black Breath curse on Frodo");
        loremaster.shareLore("HISTORY_REQUEST", "Rings of Power");

        // 7. Print final CouncilRunResult
        System.out.println("\n=== Final Council Result ===");
        System.out.println(result);
    }
}
