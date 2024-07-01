package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.*;
import exceptions.*;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;

public class MiddleApiClient {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient
            .Builder()
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();

    ObjectMapper objectMapper = new ObjectMapper();

    public RegisterUserResponse createUser(RegisterUserRequest registerUserRequest) throws Exception {
        String body = objectMapper.writeValueAsString(registerUserRequest);
        System.out.println("Request body: " + body);
        // Логирование тела запроса
        Request request = new Request.Builder()
                .url("http://localhost:8080/api/v2/users")
                .post(RequestBody.create(body, JSON))
                .build();

        Response response = client.newCall(request).execute();
        String bodyString = response.body().string();
        System.out.println("Response body: " + bodyString); // Логирование тела ответа

        if (!response.isSuccessful()) {
            if (response.code() == 409) {
                throw new UserExistException("Conflict: User already exists.");

            } else {
                throw new Exception("Unexpected code " + response.code() + ": " + bodyString);
            }
        }

        RegisterUserResponse registerUserResponse = objectMapper.readValue(bodyString, RegisterUserResponse.class);
        System.out.println("Parsed response: " + registerUserResponse.toString());

        return registerUserResponse;
    }

    public CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest) throws Exception {
        String body = objectMapper.writeValueAsString(createAccountRequest);
        System.out.println("Request body " + body);

        Request request = new Request.Builder()
                .url("http://localhost:8080/api/v2/users/" + createAccountRequest.getUserId() + "/accounts")
                .post(RequestBody.create(body, JSON))
                .build();

        Response response = client.newCall(request).execute();
        String bodyString = response.body().string();
        System.out.println("Response body: " + bodyString); // Логирование тела ответа

        if (!response.isSuccessful()) {
            if (response.code() == 409) {
                throw new AccountExistException("Conflict: Account already exists.");
            } else {
                throw new Exception("Unexpected code " + response.code() + ": " + bodyString);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        CreateAccountResponse createAccountResponse = objectMapper.readValue(bodyString, CreateAccountResponse.class);
        System.out.println("Parsed response: " + createAccountResponse.toString());
        return createAccountResponse;

    }

    public GetCurrentBalanceResponse getCurrentBalance(GetCurrentBalanceRequest getCurrentBalanceRequest) throws Exception {
        // Логирование тела запроса
        System.out.println("Request for user ID: " + getCurrentBalanceRequest.getUserId());

        Request request = new Request.Builder()
                .url("http://localhost:8080/api/v2/users/" + getCurrentBalanceRequest.getUserId() + "/accounts")
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String bodyString = response.body().string();
        System.out.println("Response body: " + bodyString); // Логирование тела ответа

        if (!response.isSuccessful()) {
            switch (response.code()) {
                case 403:
                    throw new UserNotFoundException("User not found: User ID " + getCurrentBalanceRequest.getUserId());
                case 404:
                    throw new NoAccountFoundException("No accounts found for user ID " + getCurrentBalanceRequest.getUserId());
                default:
                    throw new Exception("Unexpected code " + response.code() + ": " + bodyString);
            }
        }

        GetCurrentBalanceResponse getCurrentBalanceResponse = objectMapper.readValue(bodyString, GetCurrentBalanceResponse.class);
        System.out.println("Parsed response: " + getCurrentBalanceResponse.toString());
        return getCurrentBalanceResponse;
    }

    public TransferResponse createTransfer(CreateTransferRequest createTransferRequest) throws IOException, NoAccountFoundException, UserNotFoundException, InsufficientFundsException {
        String body = objectMapper.writeValueAsString(createTransferRequest);
        System.out.println("Request body: " + body);

        Request request = new Request.Builder()
                .url("http://localhost:8080/api/v2/transfers")
                .post(RequestBody.create(body, JSON))
                .build();

        Response response = client.newCall(request).execute();
        String bodyString = response.body().string();
        System.out.println("Response body: " + bodyString); // Логирование тела ответа

        if (!response.isSuccessful()) {
            switch (response.code()) {
                case 403:
                    if (bodyString.contains("User not found")) {
                        throw new UserNotFoundException("User not found: " + createTransferRequest.getFrom());
                    } else if (bodyString.contains("Recipient user not found")) {
                        throw new UserNotFoundException("Recipient user not found: " + createTransferRequest.getTo());
                    }
                    break;
                case 404:
                    if (bodyString.contains("No account found for user")) {
                        throw new NoAccountFoundException("No account found for user: " + createTransferRequest.getFrom());
                    } else if (bodyString.contains("No account found for recipient user")) {
                        throw new NoAccountFoundException("No account found for recipient user: " + createTransferRequest.getTo());
                    }
                    break;
                case 400:
                    throw new InsufficientFundsException("Insufficient funds for user: " + createTransferRequest.getFrom());
                default:
                    throw new IOException("Unexpected code " + response.code() + ": " + bodyString);
            }
        }

        TransferResponse transferResponse = objectMapper.readValue(bodyString, TransferResponse.class);
        System.out.println("Parsed response: " + transferResponse.toString());
        return transferResponse;
    }

}
