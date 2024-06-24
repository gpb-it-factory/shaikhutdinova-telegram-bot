package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.UserExistException;
import dto.RegisterUserRequest;
import dto.RegisterUserResponse;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;

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
}
