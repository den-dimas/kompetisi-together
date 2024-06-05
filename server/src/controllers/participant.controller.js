import { db } from "../config/db.js";
import {
  APIResponse,
  BaseApiResponse,
  createToken,
  hashPassword,
  isValidEmail,
} from "../config/utils.js";

import bcrypt from "bcrypt";

/* User Login Controller */
export const login = async (req, res) => {
  const { email, password } = req.body;

  if (!isValidEmail(email))
    return APIResponse(res, 400, null, "Invalid email!");

  // prettier-ignore
  if (password.length < 8)
    return APIResponse(res, 400, null, "Minimum password is 8!");

  try {
    const result = await db.query(
      `SELECT * FROM participant 
      WHERE email = $1`,
      [email]
    );

    const data = result.rows[0];

    if (!data) return APIResponse(res, 404, null, "Account not found!");

    const isCorrectPassword = await bcrypt.compare(password, data.password);

    if (!isCorrectPassword)
      return APIResponse(res, 400, null, "Wrong password!");

    // IF login succeed
    const token = createToken(data.id_participant, "participant");

    res.cookie("jwt", token, {
      maxAge: 30 * 24 * 60 * 60,
      secure: !(process.env.NODE_ENV === "development"),
    });

    return res
      .status(202)
      .json({ ...BaseApiResponse(data, "Login Success!"), token });
  } catch (error) {
    console.log(error);
    return res.status(500).json(error);
  }
};

export const register = async (req, res) => {
  const { email, password, nama, angkatan, tanggal_lahir, sekolah } = req.body;

  if (!isValidEmail(email))
    return res.status(400).json(BaseApiResponse(null, "Email not valid!"));

  if (password.length < 8)
    return res
      .status(400)
      .json(BaseApiResponse(null, "Password must have a minimum length of 8!"));

  const hashedPassword = await hashPassword(password);

  try {
    const result = await db.query(
      `
    INSERT INTO participant (email, password, nama, angkatan, tanggal_lahir, sekolah) 
    VALUES ($1, $2, $3, $4, $5, $6) RETURNING *
    `,
      [email, hashedPassword, nama, angkatan, tanggal_lahir, sekolah]
    );

    const data = result.rows[0];

    if (!data)
      return res.status(400).json(BaseApiResponse(null, "Account not found!"));

    if (!hashedPassword)
      return res.status(400).json(BaseApiResponse(null, "Wrong password!"));

    return res.status(202).json(BaseApiResponse(data, "Login Success!"));
  } catch (error) {
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
