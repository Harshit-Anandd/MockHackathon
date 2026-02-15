package utils;

import models.User;

public final class TestDataFactory {

    private TestDataFactory() {
    }

    public static User buildUniqueUser() {
        long timestamp = System.currentTimeMillis();
        String name = "AceUser" + timestamp;
        String email = "ace.user." + timestamp + "@mailinator.com";

        return new User(
                name,
                email,
                "Test@12345",
                "Ace",
                "Tester",
                "Team ACE",
                "42 Test Street",
                "Suite 100",
                "India",
                "Tamil Nadu",
                "Chennai",
                "600001",
                "9876543210"
        );
    }
}
