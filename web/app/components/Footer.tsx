import { footerLinks } from "~/constants";
import { NavLink, useLocation } from "@remix-run/react";

import KomporLogo from "./Logo";

export default function Footer() {
  const path = useLocation();

  if (path.pathname.includes("auth")) return null;

  return (
    <footer className="flex items-start justify-between gap-24 min-h-20 py-12 px-4 sticky bottom-0 bg-dark-purple text-white -z-10 border-t-2 border-white">
      <div id="logo-description" className="flex flex-col flex-1 gap-4">
        <KomporLogo color="#ffffff" className="w-24 h-24" />

        <p className="">
          Discover, host, and dominate competitions like never before. Whether
          you're an organizer creating the next big event or a competitor
          finding your perfect match, KOMPOR has the tools you need to ignite
          success.
        </p>
      </div>

      <div id="links" className="flex-1">
        <h1 className="font-black mb-4 text-xl bg-white text-dark-purple px-2 w-fit">
          LINKS
        </h1>

        <ul className="flex flex-col gap-2">
          {footerLinks.map((link, i) => (
            <li key={i}>
              <NavLink to={link.rute} className="underline">
                {link.nama}
              </NavLink>
            </li>
          ))}
        </ul>
      </div>

      <div id="contacts" className="flex-1">
        <h1 className="font-black mb-4 text-xl bg-white text-dark-purple px-2 w-fit">
          CONTACT US
        </h1>

        <ul className="flex flex-col gap-2">
          <li>
            <NavLink to="https://instagram.com/dimass.dn" className="underline">
              Instagram
            </NavLink>
          </li>

          <li>
            <NavLink to="mailto:dimass.drm@gmail.com" className="underline">
              Gmail
            </NavLink>
          </li>
        </ul>
      </div>
    </footer>
  );
}
