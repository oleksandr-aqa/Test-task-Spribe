**CREATE /player/create/{editor} Test Cases:**
To avoid describing each test case in detail, it was decided to simply write a combination of data for negative and
positive checks

**PS: For better readability, please open this file in full screen mode.**

**Positive data combination:**

**| Age| Editor | Gender | Login | Password | User Role | Screen Name |**

| -- | ---------- | ------ | ------------- | --------------- | --------- | ------------------ |

| 16 | Supervisor | Male | uniqueLogin1 | Qwerty123 | Admin | uniqueScreenName1 |

| 59 | Supervisor | Female | uniqueLogin2 | Abcdef123 | User | uniqueScreenName2 |

| 30 | Admin | Female | uniqueLogin4 | Pas1234 | User | uniqueScreenName4 |

| 46 | Supervisor | Male | uniqueLogin5 | Qwerty1234 | Admin | uniqueScreenName5 |

| 60 | Supervisor | Female | uniqueLogin6 | Qwerty456 | User | uniqueScreenName6 |

| 17 | Admin | Female | uniqueLogin8 | Abcdef789 | User | uniqueScreenName8 |

| 18 | Supervisor | Male | uniqueLogin9 | 1Password234 | Admin | uniqueScreenName9 |

| 49 | Admin | Female | uniqueLogin10 | Qwerty987 | User | uniqueScreenName10 |

| 25 | Supervisor | Female | uniqueLogin14 | Password1234567 | Admin | uniqueScreenName14 |

**Negative data combination**: (the invalid data is bold)

| **Age** | **Editor**     | **Created role** | **Login**         | **ScreenName**         **| Password | Gender |**

| --- | ---------- | ------------ | ------------- | ------------------ | ---------------- | -------------- |

| 17 | Supervisor | Admin | uniqueLogin15 | uniqueScreenName15 | Pass123 | **Invalid Gender** |

| 17 | Supervisor | Admin | uniqueLogin16 | uniqueScreenName16 | **short**            | Male |

| 17 | Supervisor | Admin | uniqueLogin17 | uniqueScreenName17 | **Qwerty1234512345** | Male |

| **15**  | Supervisor | Admin | uniqueLogin18 | uniqueScreenName18 | Qwerty123 | Female |

| **61**  | Supervisor | Admin | uniqueLogin19 | uniqueScreenName19 | Qwerty123 | Male |

| 25 | **Admin**      | **Admin**        | uniqueLogin23 | uniqueScreenName20 | Qwerty123 | Female |

| 25 | **Admin**      | **Supervisor**   | uniqueLogin23 | uniqueScreenName21 | Qwerty123 | Female |

| 25 | **User**       | **Admin**        | uniqueLogin23 | uniqueScreenName22 | Qwerty123 | Male |

| 17 | Supervisor | Admin | uniqueLogin25 | uniqueScreenName23 | **ТестПароль**       | Female |

**Unique field data combination**:(the non unique data is bold)

**| Age | Editor | Created Role | Login | Screen Name | Password | Gender |**

| --- | ---------- | ------------ | ------------- | ------------------ | ----------- | ------ |

| 16 | Supervisor | Admin | uniqueLogin | uniqueScreenName | Qwerty123 | Male |

| 17 | Supervisor | User | **uniqueLogin**   | uniqueScreenName20 | Password123 | Male |

| 25 | Supervisor | Admin | **uniqueLogin**   | uniqueScreenName21 | Password123 | Female |

| 25 | Supervisor | Admin | uniqueLogin22 | **uniqueScreenName**   | Password123 | Male |

| 25 | Supervisor | User | uniqueLogin22 | **uniqueScreenName**   | Password123 | Male |

//Required field tests also can be added (for example password field is not required (bug))

**Test-cases for other endpoint:  (not all TC`s were automated)**

**DELETE /player/delete/{editor}**

1. supervisor delete admin - positive
2. supervisor delete user - positive
3. admin delete admin (himself) - positive

4\. admin delete user - positive

5\. user delete user (himself) - negative

6. admin delete admin (not himself) - negative

7\. admin delete supervisor - negative

8\. user delete user (not himself) - negative

9\. user delete admin - negative

10\. user delete supervisor - negative

**POST /player/get:**

1\. get data of supervisor role player - positive

2. get data of admin role player -positive

3\. get data of user role player -positive

4\. get data of unexisted player -negative

**GET /player/get/all:**

1.Get all players including user, admin, supervisor roles users

**PATCH /player/update/{editor}/{id}: (not all TC`s were automated)**

1\. supervisor edit admin - positive

2. supervisor edit user - positive
3. admin edit admin (himself) - positive

4\. admin edit user - positive

5\. user edit user (himself) - positive

6. admin edit admin (not himself) - negative

7\. admin edit supervisor - negative

8\. user edit user (not himself) - negative

9\. user edit admin - negative

10\. user edit supervisor - negative

