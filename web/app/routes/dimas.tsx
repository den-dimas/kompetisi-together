import { LoaderFunctionArgs, redirect } from "@remix-run/node";
import { getPenyelenggaraByID } from "~/api/penyelenggara";
import { isLoggedIn } from "~/services/auth.server";

export default function Profile() {
  return (
    <div className="w-screen h-screen bg-dark-purple">
      <h1 className="">Profile</h1>

      <div></div>
    </div>
  );
}

export async function loader({ request }: LoaderFunctionArgs) {
  const user = await isLoggedIn(request, true);

  if (!user?.token) return redirect("/auth");

  const data = await getPenyelenggaraByID(user.id);

  console.log(data);

  return null;
}
