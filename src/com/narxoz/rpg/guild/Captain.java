package com.narxoz.rpg.guild;

import java.util.List;

/**
 * Guild officer responsible for orders and mission coordination.
 */
public class Captain extends GuildMember {

    public Captain(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    @Override
    public List<String> getSubscribedTopics() {
        return List.of("ROUTE_REPORT", "SUPPLY_REQUEST", "HEAL_REQUEST", "DANGER_ALERT");
    }

    public void issueOrder(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        switch (topic) {
            case "ROUTE_REPORT":
                System.out.println("[Captain:" + getName() + "] Route confirmed by " + from.getName() + ": " + payload);
                break;
            case "SUPPLY_REQUEST":
                System.out.println("[Captain:" + getName() + "] Approving supply request from " + from.getName() + ": " + payload);
                break;
            case "HEAL_REQUEST":
                System.out.println("[Captain:" + getName() + "] Noted medical prep from " + from.getName() + ": " + payload);
                break;
            case "DANGER_ALERT":
                System.out.println("[Captain:" + getName() + "] !! DANGER ALERT from " + from.getName() + ": " + payload + " - Ordering defensive formation!");
                break;
            default:
                System.out.println("[Captain:" + getName() + "] Received [" + topic + "] from " + from.getName());
        }
    }
}
