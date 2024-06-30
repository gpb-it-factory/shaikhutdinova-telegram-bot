package controller;

import bot.Bot;
import dto.*;
import exceptions.AccountExistException;
import exceptions.NoAccountFoundException;
import exceptions.UserExistException;
import api.MiddleApiClient;
import exceptions.UserNotFoundException;
import receive.EventCallback;
import userEvent.Event;
import userEvent.MessageEvent;


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
                }case CURRENTBALANCE -> {
                    startGetBalance(event);
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

    private void successGetCurrentBalance(Long chatId, GetCurrentBalanceResponse response) {
        telegramBot.sendMessage(chatId, "Баланс вашего счета " + response.getAccountName() + " составляет " + response.getBalance() + " рублей.");
    }

    private void sendUserNotFoundException(Long chatId) {
        telegramBot.sendMessage(chatId, "Невозможно предоставить информацию по вашему балансу. Так как вы не зарегистрированы!");
    }

    private void sendAccountNotFoundException(Long chatId) {
        telegramBot.sendMessage(chatId, "Cчета не найдены");
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

    private void startGetBalance(Event event) throws Exception {
        try {
            GetCurrentBalanceResponse response = middleApiClient.getCurrentBalance(new GetCurrentBalanceRequest(event.getUserId()));
            successGetCurrentBalance(event.getChatId(), response);
        } catch (UserNotFoundException e) {
            sendUserNotFoundException(event.getChatId());
        } catch (NoAccountFoundException e) {
            sendAccountNotFoundException(event.getChatId());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка получения баланса. Повторите запрос позже");
        }
    }
}