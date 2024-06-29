package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.*;
import exceptions.AccountExistException;
import exceptions.NoAccountFoundException;
import exceptions.UserExistException;
import exceptions.UserNotFoundException;
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
                .url("http://localhost:8080/api/v2/users/" + getCurrentBalanceRequest.getUserId() + "/accounts/balance")
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

}
