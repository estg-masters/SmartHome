package com.estg.masters.pedwm.smarthome.repository;


import com.estg.masters.pedwm.smarthome.model.notification.AbstractNotification;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NotificationRepository extends AbstractRepository<AbstractNotification> {

    private static final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("notification");
    private static NotificationRepository INSTANCE;

    private NotificationRepository() {
        super(databaseReference);
    }

    public static NotificationRepository getInstance() {
        if (INSTANCE == null)
            INSTANCE = new NotificationRepository();
        return INSTANCE;
    }
}
