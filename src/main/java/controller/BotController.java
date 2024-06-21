package controller;

import bot.Bot;
import common.Callback;
import exceptions.UserExistException;
import api.MiddleApiClient;
import dto.RegisterUserRequest;
import receive.EventCallback;
import userEvent.Event;
import userEvent.MessageEvent;
import java.util.HashMap;

public class BotController {
    Bot telegramBot;
    private final MiddleApiClient middleApiClient = new MiddleApiClient();

    public BotController(Bot telegramBot) {
        this.telegramBot = telegramBot;
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
                startRegistration(event);
            }
        }
    }

    private void successRegistrationMessage(Long chatId) {
        telegramBot.sendMessage(chatId, "Вы успешно зарегистрированы в Мини-Банке!");
    }

    private void startRegistration(Event event) {
        try {
            middleApiClient.createUser(new RegisterUserRequest(event.getUserId(), event.getUserName()));
            successRegistrationMessage(event.getChatId());
        } catch (UserExistException e) {
            sendUserExistMessage(event.getChatId());
        } catch (Exception e) {
            System.out.println("Ошибка регистрации. Повторите запрос позже");
        }
    }

    private void sendUserExistMessage(Long chatId) {
        telegramBot.sendMessage(chatId, "Пользователь уже существует");
    }
}
