import jsonwebtoken from "jsonwebtoken";

import { APIResponse } from "../config/utils.js";
import { db } from "../config/db.js";

const jwt = jsonwebtoken;

export const requireAuth = async (req, res, next) => {
  const token = req.headers.cookie || req.headers.Cookie;

  console.log(req.cookies);

  if (!!token) {
    jwt.verify(token, process.env.JWT_SECRET, (err, decoded) => {
      if (!!err) {
        return APIResponse(res, 403, null, "You don't have the authorization!");
      } else {
        return next();
      }
    });
  } else {
    return APIResponse(res, 403, null, "You don't have the authorization!");
  }
};

export const requirePenyelenggara = async (req, res, next) => {
  const token = req.headers.cookie || req.headers.Cookie;

  if (!!token) {
    jwt.verify(token, process.env.JWT_SECRET, async (err, decoded) => {
      if (!!err) {
        return APIResponse(res, 403, null, "You don't have the authorization!");
      } else {
        const { id_user, role } = decoded;

        const check = await db.query(
          "SELECT * FROM penyelenggara WHERE id_penyelenggara = $1",
          [id_user]
        );

        // prettier-ignore
        if (role !== "penyelenggara" || !check.rows[0])
          return APIResponse(res, 403, null, "You're not authorized to access this route!")

        next();
      }
    });
  } else {
    return APIResponse(res, 403, null, "You don't have the authorization!");
  }
};

export const requireParticipant = async (req, res, next) => {
  const token = req.headers.cookie || req.headers.Cookie;

  if (!!token) {
    jwt.verify(token, process.env.JWT_SECRET, async (err, decoded) => {
      if (!!err) {
        return APIResponse(res, 403, null, "You don't have the authorization!");
      } else {
        const { id_user, role } = decoded;

        const check = await db.query(
          "SELECT * FROM participant WHERE id_participant = $1",
          [id_user]
        );

        // prettier-ignore
        if (role !== "participant" || !check.rows[0])
          return APIResponse(res, 403, null, "You're not authorized to access this route!")

        next();
      }
    });
  } else {
    return APIResponse(res, 403, null, "You don't have the authorization!");
  }
};
