import type { MetaFunction } from "@remix-run/node";
import LandingPage from "~/components/page/LandingPage";

export const meta: MetaFunction = () => [
  { title: "Kompetisi Together" },
  { name: "description", content: "Welcome to Kompetisi Together!" },
];

export default function Index() {
  const isLoggedIn = false;

  return isLoggedIn ? (
    <div className="flex h-screen items-center justify-center"></div>
  ) : (
    <LandingPage />
  );
}
