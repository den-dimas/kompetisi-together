import axios from "~/services/axios.server";
import { Authenticator } from "remix-auth";
import { FormStrategy } from "remix-auth-form";

import { cookie } from "~/services/sessions.server";

import { loginSchema } from "~/constants/schemas";
import { redirect } from "@remix-run/node";

export const authenticator = new Authenticator<string>();

authenticator.use(
  new FormStrategy(async ({ form }) => {
    const email = form.get("email") as string;
    const password = form.get("password") as string;
    const asParticipant = form.get("asParticipant") === "on";

    try {
      const data = loginSchema.parse({
        email,
        password,
        asParticipant,
      });

      const token = await login(
        data.email,
        data.password,
        !data.asParticipant ? true : false
      );

      if (!token) return "";

      return token as string;
    } catch (error) {
      return "";
    }
  }),
  "login"
);

export async function login(
  email: string,
  password: string,
  asParticipant: boolean
): Promise<{ token?: string; error?: any }> {
  try {
    const res = await axios.post(
      asParticipant ? "/participant/login" : "/penyelenggara/login",
      {
        email,
        password,
      }
    );

    const { token } = await res.data;

    return token;
  } catch (error) {
    return null as any;
  }
}

export async function isLoggedIn(request: Request, getToken?: boolean) {
  const session = await cookie.getSession(request.headers.get("cookie"));

  const token = session.get("token");

  if (token) return getToken ? token : null;

  return redirect("/auth/");
}

export async function LogOut(request: Request) {
  const session = await cookie.getSession(request.headers.get("cookie"));

  await cookie.destroySession(session);
}
