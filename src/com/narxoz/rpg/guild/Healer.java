package com.narxoz.rpg.guild;

import java.util.List;

/**
 * Guild officer responsible for wounds, potions, and recovery plans.
 */
public class Healer extends GuildMember {

    public Healer(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    @Override
    public List<String> getSubscribedTopics() {
        return List.of("MISSION_ASSIGNED", "DANGER_ALERT", "HEAL_REQUEST");
    }

    public void prepareAid(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        switch (topic) {
            case "MISSION_ASSIGNED":
                System.out.println("[Healer:" + getName() + "] Preparing healing supplies for: " + payload);
                getMediator().dispatch("HEAL_REQUEST", this, "Potions and bandages packed for " + payload);
                break;
            case "DANGER_ALERT":
                System.out.println("[Healer:" + getName() + "] Alert from " + from.getName() + "! Standing by for casualties: " + payload);
                break;
            case "HEAL_REQUEST":
                System.out.println("[Healer:" + getName() + "] Healing request from " + from.getName() + ": " + payload);
                break;
            default:
                System.out.println("[Healer:" + getName() + "] Received [" + topic + "] from " + from.getName());
        }
    }
}
