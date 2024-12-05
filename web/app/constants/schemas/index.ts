import { z } from "zod";

export const loginSchema = z.object({
  email: z
    .string({
      required_error: "Email tidak boleh kosong!",
      invalid_type_error: "Email harus string.",
    })
    .email("Format email salah!")
    .min(5, "Minimal 5 karakter!")
    .max(255, "Email tidak boleh lebih dari 255 karakter!")
    .refine((email) => email.includes("."), {
      message: "Email tidak memiliki domain yang valid!",
    }),

  password: z
    .string({
      required_error: "Password tidak boleh kosong",
      invalid_type_error: "Password harus string.",
    })
    .min(8, "Minimal password adalah 8 karakter!")
    .max(32, "Maksimal password adalah 32 karakter!"),

  asParticipant: z.boolean().default(false).optional(),
});

export type LoginForm = z.infer<typeof loginSchema>;

export const registerSchema = z.object({
  nama: z
    .string({
      required_error: "Nama tidak boleh kosong!",
      invalid_type_error: "Nama harus string.",
    })
    .min(3, "Minimal nama adalah 3 karakter!")
    .max(32, "Maksimal nama adalah 32 karakter!"),

  email: z
    .string({
      required_error: "Email tidak boleh kosong!",
      invalid_type_error: "Email harus string.",
    })
    .email("Format email salah!")
    .min(5, "Minimal 5 karakter!")
    .max(255, "Email tidak boleh lebih dari 255 karakter!")
    .refine((email) => email.includes("."), {
      message: "Email tidak memiliki domain yang valid!",
    }),

  password: z
    .string({
      required_error: "Password tidak boleh kosong!",
      invalid_type_error: "Password harus string.",
    })
    .min(8, "Minimal password adalah 8 karakter!")
    .max(32, "Maksimal password adalah 32 karakter!"),
});

export type RegisterForm = z.infer<typeof registerSchema>;
