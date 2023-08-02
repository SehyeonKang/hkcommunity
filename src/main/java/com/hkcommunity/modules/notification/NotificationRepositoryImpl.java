package com.hkcommunity.modules.notification;

public class NotificationRepositoryImpl implements CustomNotificationRepository{

    @Override
    public void changeReadCondition(Notification notification, boolean checked) {
        notification.changeReadCondition(checked);
    }
}
