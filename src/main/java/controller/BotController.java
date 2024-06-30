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

import java.util.HashMap;


public class BotController {
    Bot telegramBot;
    private final MiddleApiClient middleApiClient = new MiddleApiClient();
    private final CommandHandler handler = new CommandHandler();
    private final HashMap<Long, CreateTransferRequest> currentUserTransactions = new HashMap<>();

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
                case CURRENTBALANCE -> {
                    startGetBalance(event);
                }
                case TRANSFER -> {
                    startTransfer((MessageEvent) event);
                }
                case UNKNOWN -> {
                    tryHandleUserTransaction((MessageEvent) event);
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

    private void successTransfer(Long chatId, TransferResponse response) {
        telegramBot.sendMessage(chatId, "Перевод осуществлен");
    }

    private void sendUserNotFoundException(Long chatId) {
        telegramBot.sendMessage(chatId, "Невозможно предоставить информацию по вашему балансу. Так как вы не зарегистрированы!");
    }

    private void sendAccountNotFoundException(Long chatId) {
        telegramBot.sendMessage(chatId, "Cчета не найдены");
    }

    private void sendUserExistMessage(Long chatId) {
        telegramBot.sendMessage(chatId, "Пользователь уже существует");
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

    private void startTransfer(MessageEvent event) {
        String message = "Введите имя получателя и сумму перевода в формате: to: alice, amount: 100.50";
        telegramBot.sendMessage(event.getChatId(), message);
        CreateTransferRequest request = new CreateTransferRequest();
        request.setFrom(event.getUserName());
        currentUserTransactions.put(event.getUserId(), request);
    }

    private void processNextUserInput(MessageEvent event) {
        try {
            CreateTransferRequest request = currentUserTransactions.get(event.getUserId());
            if (request == null) {
                throw new NullPointerException("Запрос не найден");
            }
            TransferResponse response = middleApiClient.createTransfer(request);
            successTransfer(event.getChatId(), response);
        } catch (UserNotFoundException e) {
            sendUserNotFoundException(event.getChatId());
        } catch (NoAccountFoundException e) {
            sendAccountNotFoundException(event.getChatId());
        } catch (Exception e) {
            e.printStackTrace();
//            sendError(event.getChatId(), "Ошибка выполнения перевода. Повторите запрос позже.");
        }
    }

    private void tryHandleUserTransaction(MessageEvent event) {
        CreateTransferRequest request = getUserTransaction(event.getUserId());
        if (request != null) { // пользователь начал процесс транзакции
            if (request.getFrom() == null) {
                throw new IllegalStateException("Не задано имя пользователя отправителя");
            }
            String receiverUsername = getReceiverUsername(event.getMessage());
            Double amount = getAmount(event.getMessage());
            request.setTo(receiverUsername);
            request.setAmount(amount);
            currentUserTransactions.put(event.getUserId(), request);
            processNextUserInput(event);
            currentUserTransactions.remove(event.getUserId());
        }
    }

    private CreateTransferRequest getUserTransaction(long userId) {
        return currentUserTransactions.get(userId);
    }

    // парсим сообщение пользователя в формате username,123234 и возвращаем username
    private String getReceiverUsername(String message) {
        // Разделяем строку по запятой
        String[] parts = message.trim().split(",");
        // Получаем имя получателя
        String userName = parts[0];
        if (userName.isBlank()) {
            throw new IllegalStateException("Не удалось спарсить сообщение " + message);
        }
        return userName;
    }

    // парсим сообщение пользователя в формате username,123234 и возвращаем amount
    private Double getAmount(String message) {
        // Разделяем строку по запятой
        String[] parts = message.trim().split(",");
        // Получаем имя получателя
        String amountString = parts[1].trim();
        return Double.parseDouble(amountString); // "amount: 100.50" -> 100.50
    }
}

