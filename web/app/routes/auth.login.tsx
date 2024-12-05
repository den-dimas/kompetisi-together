import { Link } from "@remix-run/react";
import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import type { ActionFunctionArgs } from "@remix-run/node";
import { redirect } from "@remix-run/node";
import { FormEvent } from "react";

import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "~/components/ui/card";
import { Input } from "~/components/ui/input";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "~/components/ui/form";
import { Checkbox } from "~/components/ui/checkbox";
import { ButtonDark } from "~/components/ui/Button";

import illust from "../../assets/authIllustration.svg";

import { loginSchema as authSchema, LoginForm } from "~/constants/schemas";
import { authenticator } from "~/services/auth.server";
import { cookie } from "~/services/sessions.server";

export default function AuthLogin() {
  const form = useForm<LoginForm>({
    resolver: zodResolver(authSchema),
    defaultValues: {
      email: "",
      password: "",
      asParticipant: false,
    },
  });

  async function onSubmit(e: FormEvent<HTMLFormElement>) {
    e.preventDefault();

    const valid = await form.trigger();

    if (!!valid) {
      (e.target as any).submit();
    }
  }

  return (
    <div
      id="login-page"
      className="flex w-screen h-screen items-center justify-between"
    >
      <div className="w-[50%] h-full flex flex-col items-center justify-between gap-12 py-32 max-lg:hidden">
        <h1 className="text-4xl">
          Find <i>Your</i> <b>Team</b>
        </h1>

        <img src={illust} className="" alt="Auth illustration" />

        <h1 className="text-4xl font-bold">
          Compete{" "}
          <span className="bg-dark-purple text-white px-2 pb-1">Together</span>
        </h1>
      </div>

      <section
        id="login-section"
        className="bg-dark-purple flex-1 h-full flex items-center justify-center"
      >
        <Card className="text-dark-purple px-8 py-12">
          <CardHeader>
            <CardTitle className="text-6xl font-bold tracking-tighter">
              Login
            </CardTitle>

            <CardDescription className="text-dark-purple opacity-70 text-lg">
              Enter your email below to login to your account
            </CardDescription>
          </CardHeader>

          <CardContent>
            <Form {...form}>
              <form onSubmit={onSubmit} method="POST" className="grid gap-4">
                <FormField
                  control={form.control}
                  name="email"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel className="text-lg cursor-text">
                        Email
                      </FormLabel>
                      <FormControl>
                        <Input
                          placeholder="m@example.com"
                          {...field}
                          className="border-dark-purple !text-lg h-12"
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />

                <FormField
                  control={form.control}
                  name="password"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel className="text-lg cursor-text">
                        Password
                      </FormLabel>
                      <FormControl>
                        <Input
                          type="password"
                          placeholder="password123..."
                          className="border-dark-purple !text-lg h-12"
                          {...field}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />

                <FormField
                  control={form.control}
                  name="asParticipant"
                  render={({ field }) => (
                    <FormItem className="flex flex-row items-center justify-end space-x-3 space-y-0">
                      <FormLabel className="text-lg cursor-text">
                        Login as organizer?
                      </FormLabel>

                      <FormControl>
                        <Checkbox
                          name="asParticipant"
                          checked={field.value}
                          onCheckedChange={field.onChange}
                          className="data-[state=checked]:bg-dark-purple"
                        />
                      </FormControl>
                    </FormItem>
                  )}
                />

                <ButtonDark type="submit" className="w-full mt-4">
                  Login
                </ButtonDark>
              </form>
            </Form>

            <div className="mt-4 text-center text-lg">
              Don&apos;t have an account?{" "}
              <Link to="/auth/register" className="underline">
                Register
              </Link>
            </div>
          </CardContent>
        </Card>
      </section>
    </div>
  );
}

export async function action({ request }: ActionFunctionArgs) {
  const res = await authenticator.authenticate("login", request);

  if (!res) return null;

  const session = await cookie.getSession(request.headers.get("cookie"));

  session.set("session", res);
  let headers = new Headers({
    "Set-Cookie": await cookie.commitSession(session),
  });

  return redirect("/", { headers });
}
