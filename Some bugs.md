1. GET request is used to create a player (should be POST)

2\. POST request is used to get a player (should be GET)

3\. Response of player creation contains null fields (password, screenName, gender, age, role) — should not be null

4\. User with role "USER" can delete a player with role "ADMIN" (should not have such permission)

5\. getAllPlayers endpoint (/player/get/all) does not return the role field (should include it)

6\. Player with role "ADMIN" cannot edit a player with role "USER" (should have such permission)

7\. Creating a user with an existing login returns status code 200 and keeps the same user ID (should return an error
and message "User already exists")

8\. screenName field is not unique (should be unique, same as login)

9\. Creating a user with age 16 returns status code 400 (should return 200, valid age range is 16–60)

10\. Fields gender and password fail validation (password must contain Latin letters and digits, 7–15 chars; gender can
only be male or female)

Some critical bugs were covered with automated tests.

