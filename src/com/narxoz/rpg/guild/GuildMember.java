package com.narxoz.rpg.guild;

import java.util.List;

/**
 * Base class for all guild officers that communicate through a mediator.
 */
public abstract class GuildMember {

    private final String name;
    private final GuildMediator mediator;

    protected GuildMember(String name, GuildMediator mediator) {
        this.name = name;
        this.mediator = mediator;
        mediator.register(this);
    }

    public String getName() {
        return name;
    }

    protected GuildMediator getMediator() {
        return mediator;
    }

    /**
     * Returns the list of topics this member subscribes to.
     * Subclasses override this to declare their topic interests.
     */
    public abstract List<String> getSubscribedTopics();

    public abstract void receive(String topic, GuildMember from, String payload);
}
