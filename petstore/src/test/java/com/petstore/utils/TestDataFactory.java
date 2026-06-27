package com.petstore.utils;

import com.github.javafaker.Faker;
import com.petstore.models.request.PetRequest;
import com.petstore.models.request.StoreOrderRequest;
import com.petstore.models.request.UserRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TestDataFactory {

    private static final Faker faker = new Faker();

    private TestDataFactory() {}

    // ── PET ──────────────────────────────────────────────

    public static PetRequest availablePet() {
        return PetRequest.builder()
                .id(randomId())
                .name(faker.animal().name())
                .category(PetRequest.Category.builder().id(1L).name("Dogs").build())
                .photoUrls(Arrays.asList("https://example.com/photo/" + faker.number().digits(5) + ".jpg"))
                .tags(Arrays.asList(PetRequest.Tag.builder().id(1L).name("friendly").build()))
                .status("available")
                .build();
    }

    public static PetRequest pendingPet() {
        PetRequest pet = availablePet();
        pet.setStatus("pending");
        return pet;
    }

    public static PetRequest soldPet() {
        PetRequest pet = availablePet();
        pet.setStatus("sold");
        return pet;
    }

    public static PetRequest petWithoutName() {
        return PetRequest.builder()
                .photoUrls(Arrays.asList("https://example.com/photo.jpg"))
                .status("available")
                .build();
    }

    public static PetRequest petWithTags(String... tagNames) {
        PetRequest pet = availablePet();
        List<PetRequest.Tag> tags = new ArrayList<>();
        for (int i = 0; i < tagNames.length; i++) {
            tags.add(PetRequest.Tag.builder().id((long)(i + 1)).name(tagNames[i]).build());
        }
        pet.setTags(tags);
        return pet;
    }

    // ── STORE ─────────────────────────────────────────────

    public static StoreOrderRequest validOrder(long petId) {
        return StoreOrderRequest.builder()
                .id(ThreadLocalRandom.current().nextLong(1, 10))
                .petId(petId)
                .quantity(faker.number().numberBetween(1, 5))
                .shipDate(Instant.now().plusSeconds(86400).toString())
                .status("placed")
                .complete(false)
                .build();
    }

    // ── USER ──────────────────────────────────────────────

    public static UserRequest validUser() {
        String username = "user_" + faker.name().username().replaceAll("[^a-zA-Z0-9]", "").substring(0, 5)
                + faker.number().digits(4);
        return UserRequest.builder()
                .id(randomId())
                .username(username)
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .password("Pass@" + faker.number().digits(4))
                .phone(faker.phoneNumber().cellPhone())
                .userStatus(1)
                .build();
    }

    public static List<UserRequest> listOfUsers(int count) {
        List<UserRequest> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            users.add(validUser());
        }
        return users;
    }

    // ── Helpers ───────────────────────────────────────────

    public static long randomId() {
        return ThreadLocalRandom.current().nextLong(100000L, 999999L);
    }
}
