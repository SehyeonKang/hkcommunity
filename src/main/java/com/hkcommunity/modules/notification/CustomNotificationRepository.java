package com.hkcommunity.modules.notification;

public interface CustomNotificationRepository {

    void changeReadCondition(Notification notification, boolean checked);
}
