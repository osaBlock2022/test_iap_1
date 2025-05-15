package com.example.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@RestController
public class HelloController {

    private static final String API_URL = "https://devapi.samsungapps.com/iap/v6/applications/com.example.bookspot/items?page=1&size=20";
    private static final String ACCESS_TOKEN = "0DjT9yzrYUKDoGbVUlOnCUgQ";
    private static final String SERVICE_ACCOUNT_ID = "85412253-21b2-4d84-8ff5-4b0b6d86ad6e";

    private final OkHttpClient client = new OkHttpClient();

    @CrossOrigin(origins = "*")
    @GetMapping("/get")
    public ResponseEntity<String> getRequest() {
        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + ACCESS_TOKEN)
                .addHeader("service-account-id", SERVICE_ACCOUNT_ID)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String error = response.body() != null ? response.body().string() : "Unknown error";
                return ResponseEntity.status(response.code()).body("Failed: " + error);
            }

            String json = response.body() != null ? response.body().string() : "{}";
            return ResponseEntity.ok()
                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                    .body(json);

        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    private static final String CREATE_API_URL = "https://devapi.samsungapps.com/iap/v6/applications/com.example.bookspot/items";

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/create", consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createItem(@org.springframework.web.bind.annotation.RequestBody String requestBody) {
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, requestBody); // <-- FIXED ARG ORDER

        Request request = new Request.Builder()
                .url(CREATE_API_URL)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + ACCESS_TOKEN)
                .addHeader("service-account-id", SERVICE_ACCOUNT_ID)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseString = response.body() != null ? response.body().string() : "No response body";
            return ResponseEntity.status(response.code()).body(responseString);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    private static final String BASE_URL = "https://devapi.samsungapps.com/iap/v6/applications/com.example.bookspot/items/";

    @CrossOrigin(origins = "*")
    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable String itemId) {
        String deleteUrl = BASE_URL + "/" + itemId;

        Request request = new Request.Builder()
                .url(deleteUrl)
                .delete()
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + ACCESS_TOKEN)
                .addHeader("service-account-id", SERVICE_ACCOUNT_ID)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseString = response.body() != null ? response.body().string() : "No response body";
            return ResponseEntity.status(response.code()).body(responseString);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PutMapping(value = "/update", consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> updateItem(@RequestBody String requestBody) {
    okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
    okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, requestBody);

    Request request = new Request.Builder()
            .url("https://devapi.samsungapps.com/iap/v6/applications/com.example.bookspot/items")
            .put(body)
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer " + ACCESS_TOKEN)
            .addHeader("service-account-id", SERVICE_ACCOUNT_ID)
            .build();

    try (Response response = client.newCall(request).execute()) {
        String responseString = response.body() != null ? response.body().string() : "No response body";
        return ResponseEntity.status(response.code()).body(responseString);
    } catch (IOException e) {
        return ResponseEntity.status(500).body("Error: " + e.getMessage());
    }
}


}
