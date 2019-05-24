package com.estg.masters.pedwm.smarthome.repository;


import com.estg.masters.pedwm.smarthome.model.notification.AbstractNotification;

public class NotificationRepository extends AbstractRepository<AbstractNotification> {

    private static NotificationRepository INSTANCE;

    private NotificationRepository() {
        super();
    }

    public static NotificationRepository getInstance() {
        if (INSTANCE == null)
            INSTANCE = new NotificationRepository();
        return INSTANCE;
    }
}
