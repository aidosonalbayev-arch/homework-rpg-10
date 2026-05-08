package com.narxoz.rpg.guild;

import java.util.List;

/**
 * Guild officer responsible for lore, curses, and historical records.
 * Open/closed proof: new colleague added without modifying existing classes.
 */
public class Loremaster extends GuildMember {

    public Loremaster(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    @Override
    public List<String> getSubscribedTopics() {
        return List.of("LORE_UPDATE", "CURSE_DETECTED", "HISTORY_REQUEST");
    }

    public void shareLore(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        switch (topic) {
            case "LORE_UPDATE":
                System.out.println("[Loremaster:" + getName() + "] Recording lore from " + from.getName() + ": " + payload);
                break;
            case "CURSE_DETECTED":
                System.out.println("[Loremaster:" + getName() + "] CURSE from " + from.getName() + "! Researching countermeasures: " + payload);
                getMediator().dispatch("LORE_UPDATE", this, "Curse identified: " + payload + " — ancient ritual required.");
                break;
            case "HISTORY_REQUEST":
                System.out.println("[Loremaster:" + getName() + "] " + from.getName() + " requests history: " + payload);
                getMediator().dispatch("LORE_UPDATE", this, "Historical records on " + payload + " retrieved.");
                break;
            default:
                System.out.println("[Loremaster:" + getName() + "] Received [" + topic + "] from " + from.getName());
        }
    }
}
