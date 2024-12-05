import { cn } from "~/lib/utils";

interface KomporLogoProperties {
  width?: number;
  height?: number;
  strokeWidth?: number;
  color?: string;
  className?: string;
}

export default function KomporLogo({
  strokeWidth = 24,
  color = "#473765",
  className = "",
}: KomporLogoProperties) {
  return (
    <svg
      width="253"
      height="259"
      viewBox="0 0 253 259"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      className={cn("", className)}
    >
      <path d="M241 18.5L9 250.5" stroke={color} strokeWidth={strokeWidth} />
      <path d="M15 151.5L104 240.5" stroke={color} strokeWidth={strokeWidth} />
      <path
        d="M203.5 8C203.5 8 251.484 72.8376 237.5 114C215.265 179.449 67.194 45.2021 60.5 114C56.63 153.774 111 203 111 203"
        stroke={color}
        strokeWidth={strokeWidth}
      />
    </svg>
  );
}
