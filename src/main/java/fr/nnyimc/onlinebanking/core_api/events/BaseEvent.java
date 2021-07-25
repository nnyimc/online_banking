package fr.nnyimc.onlinebanking.core_api.events;

import lombok.Getter;

public abstract class BaseEvent<T> {
    @Getter
    private T id;

    public BaseEvent(T id) {
        this.id = id;
    }
}
