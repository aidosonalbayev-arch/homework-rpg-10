package com.narxoz.rpg.guild;

import java.util.List;

/**
 * Guild officer responsible for gear, supplies, and rewards.
 */
public class Quartermaster extends GuildMember {

    public Quartermaster(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    @Override
    public List<String> getSubscribedTopics() {
        return List.of("SUPPLY_REQUEST", "MISSION_ASSIGNED", "REWARD_CLAIMED");
    }

    public void requestSupplies(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        switch (topic) {
            case "MISSION_ASSIGNED":
                System.out.println("[Quartermaster:" + getName() + "] Packing supplies for mission: " + payload);
                getMediator().dispatch("SUPPLY_REQUEST", this, "Rations and gear for: " + payload);
                break;
            case "SUPPLY_REQUEST":
                System.out.println("[Quartermaster:" + getName() + "] Supply request from " + from.getName() + ": " + payload);
                break;
            case "REWARD_CLAIMED":
                System.out.println("[Quartermaster:" + getName() + "] Logging reward from " + from.getName() + ": " + payload);
                break;
            default:
                System.out.println("[Quartermaster:" + getName() + "] Received [" + topic + "] from " + from.getName());
        }
    }
}
