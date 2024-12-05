import { NavLink, useLocation } from "react-router-dom";
import { navLinks } from "~/constants";

import { cn } from "~/lib/utils";

interface navBarArgs {}

export default function NavBar({}: navBarArgs) {
  const path = useLocation();

  if (path.pathname.includes("auth")) return null;

  return (
    <nav
      id="navigation-bar"
      className="flex items-center justify-between min-h-20 py-2 px-4 sticky top-0 bg-white border-b-2 border-dark-purple z-10"
    >
      <img src="../../assets/logo.svg" className="w-14 h-14" />

      <ul id="nav-list-parent" className="flex items-center gap-2">
        {navLinks.map((p, i) => (
          <li key={i}>
            <NavLink
              to={p.rute}
              className={({ isActive, isTransitioning, isPending }) =>
                cn(
                  "text-dark-purple font-black text-2xl px-2 py-1 -tracking-wider relative text-center",
                  "hover:bg-light-purple hover:text-dark-purple ease-in-out duration-300",
                  isActive ? "bg-dark-purple text-white" : "",
                  isPending ? "bg-black text-white" : ""
                )
              }
            >
              {p.nama}
            </NavLink>
          </li>
        ))}
      </ul>

      <NavLink
        to={"/auth"}
        className={({ isActive, isTransitioning, isPending }) =>
          cn(
            "bg-light-purple font-bold text-2xl px-2 py-1 ml-12 -tracking-wider relative text-center",
            "hover:bg-black hover:text-light-purple ease-in-out duration-300",
            isActive ? "bg-dark-purple text-white" : "",
            isPending ? "bg-black text-white" : ""
          )
        }
      >
        Account
      </NavLink>
    </nav>
  );
}
