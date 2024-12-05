import * as React from "react";
import { cn } from "~/lib/utils";

interface ButtonProperties {
  variant?: "light" | "dark";
}

export const ButtonLight = React.forwardRef<
  HTMLButtonElement,
  React.ButtonHTMLAttributes<HTMLButtonElement>
>(({ className, ...props }, ref) => (
  <button
    ref={ref}
    className={cn(
      "text-dark-purple font-black text-2xl px-2 py-1 -tracking-wider relative text-center",
      "hover:bg-light-purple hover:text-dark-purple ease-in-out duration-300",
      className
    )}
    {...props}
  />
));

export const ButtonDark = React.forwardRef<
  HTMLButtonElement,
  React.ButtonHTMLAttributes<HTMLButtonElement>
>(({ className, ...props }, ref) => (
  <button
    ref={ref}
    className={cn(
      "bg-light-purple font-bold text-2xl px-2 py-1 -tracking-wider relative text-center",
      "hover:bg-black hover:text-light-purple ease-in-out duration-300",
      className
    )}
    {...props}
  />
));
