package com.narxoz.rpg.guild;

import java.util.List;

/**
 * Guild officer responsible for route reports and reconnaissance.
 */
public class Scout extends GuildMember {

    public Scout(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    @Override
    public List<String> getSubscribedTopics() {
        return List.of("MISSION_ASSIGNED", "ROUTE_REPORT", "DANGER_ALERT");
    }

    public void reportRoute(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        switch (topic) {
            case "MISSION_ASSIGNED":
                System.out.println("[Scout:" + getName() + "] Scouting route for: " + payload);
                getMediator().dispatch("ROUTE_REPORT", this, "Path to " + payload + " is clear. No ambushes detected.");
                break;
            case "ROUTE_REPORT":
                System.out.println("[Scout:" + getName() + "] Route intel from " + from.getName() + ": " + payload);
                break;
            case "DANGER_ALERT":
                System.out.println("[Scout:" + getName() + "] DANGER from " + from.getName() + "! " + payload);
                break;
            default:
                System.out.println("[Scout:" + getName() + "] Received [" + topic + "] from " + from.getName());
        }
    }
}
