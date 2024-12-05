import { LoaderFunction, redirect } from "@remix-run/node";
import { isLoggedIn } from "~/services/auth.server";

export const loader: LoaderFunction = async ({ request }) => {
  /* Check if logged in or not */
  const user = await isLoggedIn(request);

  return redirect("/auth/login");
};

export default function Index() {
  return <div></div>;
}
