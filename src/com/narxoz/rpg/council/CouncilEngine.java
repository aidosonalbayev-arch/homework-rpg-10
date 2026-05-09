package com.narxoz.rpg.council;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.guild.GuildMediator;
import com.narxoz.rpg.quest.Quest;
import com.narxoz.rpg.quest.QuestIterator;
import com.narxoz.rpg.quest.QuestLog;
import com.narxoz.rpg.quest.QuestPriority;

import java.util.List;

/**
 * Orchestrates a planning session that uses both Iterator and Mediator.
 */
public class CouncilEngine {

    public CouncilRunResult runCouncil(List<Hero> party, QuestLog questLog, GuildMediator hall) {
        int questsTraversed = 0;
        int messagesRouted = 0;

        System.out.println("\n=== WAR COUNCIL: Ordered Traversal ===");
        QuestIterator ordered = questLog.ordered();
        while (ordered.hasNext()) {
            Quest q = ordered.next();
            questsTraversed++;
            System.out.println("  Planning quest: " + q.getTitle()
                    + " [" + q.getPriority() + "] reward=" + q.getRewardGold() + "g");
            hall.dispatch("MISSION_ASSIGNED", null, q.getTitle());
            messagesRouted++;
        }

        System.out.println("\n=== WAR COUNCIL: HIGH+ Priority Only ===");
        QuestIterator priority = questLog.priorityAtLeast(QuestPriority.HIGH);
        while (priority.hasNext()) {
            Quest q = priority.next();
            questsTraversed++;
            System.out.println("  URGENT planning: " + q.getTitle()
                    + " [" + q.getPriority() + "] reward=" + q.getRewardGold() + "g");
            if (q.isUrgent()) {
                hall.dispatch("DANGER_ALERT", null, q.getTitle() + " is marked URGENT!");
                messagesRouted++;
            }
        }

        System.out.println("\n=== WAR COUNCIL: Reward-Sorted Traversal ===");
        com.narxoz.rpg.quest.RewardSortedQuestIterator rewardSorted =
                new com.narxoz.rpg.quest.RewardSortedQuestIterator(questLog);
        while (rewardSorted.hasNext()) {
            Quest q = rewardSorted.next();
            questsTraversed++;
            System.out.println("  Reward priority: " + q.getTitle() + " => " + q.getRewardGold() + "g");
        }

        System.out.println("\n=== WAR COUNCIL: Party Summary ===");
        for (Hero hero : party) {
            System.out.println("  Hero: " + hero);
        }

        // Count approximate members notified (each dispatch notifies subscribers)
        int membersNotified = messagesRouted * 2;

        return new CouncilRunResult(questsTraversed, messagesRouted, membersNotified);
    }
}
