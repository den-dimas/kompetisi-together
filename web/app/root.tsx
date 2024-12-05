import {
  Links,
  Meta,
  Outlet,
  Scripts,
  ScrollRestoration,
} from "@remix-run/react";
import { LinksFunction, MetaFunction } from "@remix-run/node";

import "./root.css";

import NavBar from "./components/Navbar";
import Footer from "./components/Footer";

export const meta: MetaFunction = () => [{ title: "Kompetisi Together" }, {}];

export const links: LinksFunction = () => [
  { rel: "icon", href: "/assets/icon.svg", type: "image/svg" },
];

export function Layout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="en">
      <head>
        <meta charSet="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <Meta />
        <Links />
      </head>
      <body>
        <NavBar />

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
