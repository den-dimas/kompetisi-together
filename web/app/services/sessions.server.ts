import { createCookieSessionStorage } from "@remix-run/node";

const sessionSecret = process.env.SESSION_SECRET as string;

export const cookie = createCookieSessionStorage({
  cookie: {
    name: "_session",
    secrets: [sessionSecret],
    httpOnly: true,
    maxAge: 60 * 60 * 24 * 7, // 1 Week
    path: "/",
    sameSite: "lax",
    secure: process.env.NODE_ENV === "production",
  },
});
