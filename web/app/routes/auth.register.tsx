import { FormEvent, useEffect, useState } from "react";
import { Link, useActionData } from "@remix-run/react";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { redirect } from "@remix-run/node";

import type { ActionFunctionArgs } from "@remix-run/node";

import { RegisterForm, registerSchema } from "~/constants/schemas";

import { useToast } from "~/hooks/use-toast";

import { register } from "~/services/auth.server";
import { cookie } from "~/services/sessions.server";

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
import { PARTICIPANT, PENYELENGGARA } from "~/constants";

export default function AuthRegister() {
  const { toast } = useToast();
  const error = useActionData<typeof action>();

  useEffect(() => {
    if (!!error) {
      toast({
        title: typeof error == "string" ? error : error + "",
        duration: 4000,
        variant: "destructive",
      });
    }
  }, []);

  const [asParticipant, setAsParticipant] = useState(true);

  const form = useForm<RegisterForm>({
    resolver: zodResolver(registerSchema),
    defaultValues: {
      nama: "",
      email: "",
      password: "",
    },
    mode: "onChange",
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
      <section
        id="left-side"
        className="w-[50%] h-full flex flex-col items-center justify-between gap-12 py-32 max-lg:hidden"
      >
        <h1 className="text-4xl">
          Find <i>Your</i> <b>Team</b>
        </h1>

        <img src={illust} className="" alt="Auth illustration" />

        <h1 className="text-4xl font-bold">
          Compete{" "}
          <span className="bg-dark-purple text-white px-2 pb-1">Together</span>
        </h1>
      </section>

      <section
        id="register-side"
        className="bg-dark-purple flex-1 h-full flex items-center justify-center"
      >
        <Card className="text-dark-purple px-8 py-4">
          <CardHeader>
            <CardTitle className="text-6xl font-bold tracking-tighter">
              {asParticipant ? "Participant" : "Organizer"}
              <br />
              Register
            </CardTitle>

            <CardDescription className="text-dark-purple opacity-70 text-lg cursor-text">
              {asParticipant
                ? "Register your account to participate in a competition."
                : "Register your account to organize a competition."}
            </CardDescription>
          </CardHeader>

          <CardContent>
            <Form {...form}>
              <form onSubmit={onSubmit} method="POST" className="grid gap-4">
                <FormField
                  control={form.control}
                  name="nama"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel className="text-lg cursor-text">
                        Nama Anda
                      </FormLabel>
                      <FormControl>
                        <Input
                          type="text"
                          placeholder="Agung Hapsah..."
                          className="border-dark-purple focus-visible:ring-dark-purple !text-lg h-12"
                          {...field}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />

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
                          className="border-dark-purple focus-visible:ring-dark-purple !text-lg h-12"
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
                          className="border-dark-purple focus-visible:ring-dark-purple !text-lg h-12"
                          {...field}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />

                <Checkbox
                  className="invisible absolute"
                  checked={asParticipant}
                  name="asParticipant"
                />

                <ButtonDark type="submit" className="w-full mt-4">
                  Register
                </ButtonDark>
              </form>
            </Form>

            <div className="mt-4 text-center text-lg cursor-text">
              Have an account?{" "}
              <Link to="/auth/login" className="underline">
                Login
              </Link>
            </div>

            <div
              className="mt-1 text-center text-lg cursor-pointer"
              onClick={() =>
                asParticipant ? setAsParticipant(false) : setAsParticipant(true)
              }
            >
              Register as{" "}
              <span className="font-bold underline">
                {asParticipant ? "organizer" : "participant"}?
              </span>
            </div>
          </CardContent>
        </Card>
      </section>
    </div>
  );
}

export async function action({ request }: ActionFunctionArgs) {
  const formData = await request.formData();

  const asParticipant = formData.get("asParticipant") === "on";
  const email = formData.get("email");
  const password = formData.get("password");
  const nama = formData.get("nama");

  try {
    const data = registerSchema.parse({
      nama,
      email,
      password,
    });

    const { token, error, id } = await register(
      data.nama,
      data.email,
      data.password,
      asParticipant ? false : true
    );

    if (!token) {
      return error;
    }

    const session = await cookie.getSession(request.headers.get("cookie"));

    session.set("token", token);
    session.set("role", asParticipant ? PARTICIPANT : PENYELENGGARA);
    session.set("id", id);

    let headers = new Headers({
      "Set-Cookie": await cookie.commitSession(session),
    });

    return redirect("/auth", { headers });
  } catch (error) {
    return error;
  }
}
