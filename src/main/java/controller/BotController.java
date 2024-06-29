package controller;

import bot.Bot;
import com.pengrad.telegrambot.model.User;
import common.Callback;
import dto.CommandHandler;
import dto.CreateAccountRequest;
import dto.UserCommand;
import exceptions.AccountExistException;
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
    private final CommandHandler handler = new CommandHandler();

    public BotController(Bot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void start() {
        System.out.println("Бот запущен");
        telegramBot.subscribeToNewEvents(new EventCallback<Event>() {
            @Override
            public void onNewEvent(Event event) throws Exception {
                System.out.println("Пришло новое событие " + event.toString());
                handleUserEvent(event);
            }
        });
    }

    private void handleUserEvent(Event event) throws Exception {
        if (event instanceof MessageEvent) {
            UserCommand command = handler.parseCommand(((MessageEvent) event).getMessage());
            switch (command) {
                case REGISTER -> {
                    startRegistration(event);
                }
                case CREATEACCOUNT -> {
                   startCreateAccount(event);
                }
                case UNKNOWN -> { //todo
                    System.out.println("test");
                }
            }
        }
    }

    private void successRegistrationMessage(Long chatId) {
        telegramBot.sendMessage(chatId, "Вы успешно зарегистрированы в Мини-Банке!");
    }

    private void successAccountCreating(Long chatId) {
        telegramBot.sendMessage(chatId, "Счет успешно открыт!");
    }

    private void sendAccountExistMessage(Long chatId) {
        telegramBot.sendMessage(chatId, "Счет уже создан");
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

    private void startCreateAccount(Event event) throws Exception {
        try {
            middleApiClient.createAccount(new CreateAccountRequest(event.getUserId()));
            successAccountCreating(event.getChatId());
        } catch (AccountExistException e) {
            sendAccountExistMessage(event.getChatId());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка создания счета. Повторите запрос позже");
        }
    }
}
