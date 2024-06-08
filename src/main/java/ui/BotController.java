package ui;

import bot.Bot;
import common.Callback;
import controller.UserController;
import interactor.CreateUserInteractor;
import model.RegisterUserDto;
import receive.EventCallback;
import userEvent.Event;
import userEvent.MessageEvent;

import java.util.HashMap;

public class BotController {
    Bot telegramBot;
    private final CreateUserInteractor createUserInteractor;
    private final HashMap<Long, Boolean> userList = new HashMap<>();

    public BotController(Bot telegramBot, CreateUserInteractor createUserInteractor) {
        this.telegramBot = telegramBot;
        this.createUserInteractor = createUserInteractor;
    }

    public void start() {
        System.out.println("Бот запущен");
        telegramBot.subscribeToNewEvents(new EventCallback<Event>() {
            @Override
            public void onNewEvent(Event event) {
                System.out.println("Пришло новое событие " + event.toString());
                handleUserEvent(event);
            }
        });
    }

    private void handleUserEvent(Event event) {
        if (event instanceof MessageEvent) {
            if (((MessageEvent) event).getMessage().equals("/register")) {
                if (isRegistrationExist(event.getUserId())) {
                    sendUserExistMessage(event.getChatId());
                } else {
                    startRegistration(event);

                }
            }
        }
    }

    private void successRegistrationMessage(Long chatId) {
        telegramBot.sendMessage(chatId, "Вы успешно зарегистрированы в Мини-Банке!", new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    private void startRegistration(Event event) {
        createUserInteractor.createUser(event.getUserId(), event.getUserName(), new base.Callback() {
            @Override
            public void onSuccess() {
                successRegistrationMessage(event.getChatId());
                userList.put(event.getUserId(), true);
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
    }

    private boolean isRegistrationExist(Long userId) {
        return userList.containsKey(userId);
    }

    private void sendUserExistMessage(Long chatId) {
        telegramBot.sendMessage(chatId, "Пользователь уже существует", new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }
}
