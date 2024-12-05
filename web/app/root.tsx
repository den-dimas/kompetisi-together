import {
  Links,
  Meta,
  Outlet,
  Scripts,
  ScrollRestoration,
  useLoaderData,
} from "@remix-run/react";
import {
  LinksFunction,
  LoaderFunctionArgs,
  MetaFunction,
} from "@remix-run/node";

import "./root.css";

import NavBar from "./components/Navbar";
import Footer from "./components/Footer";
import { isLoggedIn } from "./services/auth.server";

export const meta: MetaFunction = () => [{ title: "Kompetisi Together" }, {}];

export const links: LinksFunction = () => [
  { rel: "icon", href: "/assets/icon.svg", type: "image/svg" },
];

export async function loader({ request }: LoaderFunctionArgs) {
  const user = await isLoggedIn(request);

  if (!user) return null;

  return user.role;
}

export function Layout({ children }: { children: React.ReactNode }) {
  const role = useLoaderData<typeof loader>();

  return (
    <html lang="en">
      <head>
        <meta charSet="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <Meta />
        <Links />
      </head>

      <body className="max-w-[100vw] overflow-x-hidden">
        <NavBar role={role} />

        {children}

        <Footer />

        <ScrollRestoration />
        <Scripts />
      </body>
    </html>
  );
}

export default function App() {
  return <Outlet />;
}
