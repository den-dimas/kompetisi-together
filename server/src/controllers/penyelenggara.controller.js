import { db } from "../config/db.js";
import {
  APIResponse,
  BaseApiResponse,
  createToken,
  hashPassword,
  isValidEmail,
} from "../config/utils.js";

import bcrypt from "bcrypt";

/* =============================================
 * =====   AUTHENTICATION CONTROLLERS   ========
 * =============================================
 */

/* Login Controller */
export const login = async (req, res) => {
  const { email, password } = req.body;

  if (!isValidEmail(email))
    return APIResponse(res, 400, null, "Not a valid email");

  // prettier-ignore
  if (password.length < 8)
    return APIResponse(res, 400, null, "Password must have a minimum length of 8!");

  try {
    const result = await db.query(
      `SELECT * FROM penyelenggara 
      WHERE email = $1`,
      [email]
    );

    const data = result.rows[0];

    if (!data) return APIResponse(res, 400, null, "Account not found!");

    const isCorrectPassword = await bcrypt.compare(password, data.password);

    if (!isCorrectPassword)
      return APIResponse(res, 400, null, "Wrong password!");

    // If login succeed
    const token = createToken(data.id_penyelenggara, "penyelenggara");

    res.cookie("jwt", token, {
      maxAge: 30 * 24 * 60 * 60,
      secure: !(process.env.NODE_ENV === "development"),
    });

    return APIResponse(res, 202, data, "Login success!");
  } catch (error) {
    console.log(error);

    return res.status(500).json(error);
  }
};

/* Register Controller */
export const register = async (req, res) => {
  const { email, password, logo, nama, deskripsi } = req.body;

  if (nama.length < 3)
    return APIResponse(res, 400, null, "Name must have a minimum length of 3!");

  // prettier-ignore
  if (deskripsi.length < 8)
    return APIResponse(res, 400, null, "Description must have a minimum length of 8!");

  if (!isValidEmail(email))
    return APIResponse(res, 400, null, "Email not valid!");

  // prettier-ignore
  if (password.length < 8)
    return APIResponse(res, 400, null, "Password must have a minimum length of 8!");

  const hashedPassword = await hashPassword(password);

  try {
    const result = await db.query(
      `
    INSERT INTO penyelenggara (email, password, logo, nama, deskripsi) 
    VALUES ($1, $2, $3, $4, $5) RETURNING *
    `,
      [email, hashedPassword, logo, nama, deskripsi]
    );

    const data = result.rows[0];

    const token = createToken(data.id_penyelenggara, "penyelenggara");

    res.cookie("jwt", token, {
      maxAge: 30 * 24 * 60 * 60,
      secure: !(process.env.NODE_ENV === "development"),
    });

    return APIResponse(res, 200, data, "Success register!");
  } catch (error) {
    console.log(error);

    if (error.code === "23505")
      return res.status(400).json(BaseApiResponse(null, "Email already used!"));

    return res.status(500).json(error);
  }
};

/* Logout Controller */
export const logout = async (req, res) => {
  try {
    res.clearCookie("jwt", {
      maxAge: 30 * 24 * 60 * 60,
      secure: !(process.env.NODE_ENV === "development"),
    });

    return APIResponse(res, 200, null, "Success logout!");
  } catch (error) {
    console.log(error);

    return res.status(500).json(error);
  }
};
