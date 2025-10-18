# Cloudeagle Dropbox OAuth2 Demo

## Tech Stack

- Java 21
- Spring Boot
- Dropbox SDk

## Dev Setup

> Assuming you have java 21 installed

- Clone repository
- Open Project into IDE
- Configure Environment Variables
- Run the project (https://www.jetbrains.com/help/idea/your-first-spring-application.html#add-greeting-method)

```sh
# env variables
# add the following env variables
OAUTH_DROPBOX_CLIENT_ID=
OAUTH_DROPBOX_CLIENT_SECRET=
OAUTH_DROPBOX_REDIRECT_URL=
```

## How to use this?

Server will be started on port `8080` by default. When you'll visit `http://localhost:8080`, you'll get to see a button(`Authorize with Dropbox`).

You'll be taken to dropbox oauth page, when you click on that button. After authorization, dropbox will return to callback url endpoint. Callback backend api will generate access/refresh token, saves it into memory and redirect to `/dropbox/members/list` url.

`/dropbox/members/list` have implemented list members API, it'll fetch members of any team/org and show them.



https://github.com/user-attachments/assets/6f35ebe0-7f7e-40ae-8a06-f5fa8df25724

## UML Diagram

<img width="1325" height="1108" alt="dropbox-demo drawio" src="https://github.com/user-attachments/assets/7587c907-694d-41f8-a9b8-56a51a48843b" />
