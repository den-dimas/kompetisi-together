import os from "os";
import express from "express";

import dotenv from "dotenv";
import helmet from "helmet";
import cors from "cors";
import cookieParser from "cookie-parser";

import { db } from "./config/db.js";

import participantRoutes from "./routes/participant.routes.js";
import kompetisiRoutes from "./routes/kompetisi.routes.js";
import kelompokRoutes from "./routes/kelompok.routes.js";
import penyelenggaraRoutes from "./routes/penyelenggara.routes.js";

dotenv.config();

const app = express();

/* === Server PORT === */
const PORT = process.env.PORT;

/* ===================================== */
/* ===========  Middlewares  =========== */
/* ===================================== */
/* Parsing Cookies */
app.use(cookieParser());
/* Allow Cross Origin Request */
app.use(
  cors({
    credentials: true,
    origin: "http://localhost:3000",
    allowedHeaders: ["Content-Type", "Authorization"],
  })
);
/* All request is JSON based */
app.use(express.json({ limit: "25mb" }));
/* All request is encoded with x-www-form-urlencoded */
app.use(express.urlencoded({ extended: true }));
/* Extra protection */
app.use(helmet());

/* ===================================== */
/* ============   Routes    ============ */
/* ===================================== */

app.use("/participant", participantRoutes);
app.use("/penyelenggara", penyelenggaraRoutes);
app.use("/kompetisi", kompetisiRoutes);
app.use("/kelompok", kelompokRoutes);

/* ======================================
 ** ========= Server connection =========
 ** ===================================== */
// prettier-ignore
const ipAddress = os.networkInterfaces()['Wi-Fi'].find(i => i.family === "IPv4").address;

app.listen(PORT, ipAddress, () => {});
app.listen(PORT, async () => {
  try {
    /* === Connect to Database === */
    const connection = await db.connect();

    /* === Connection information === */
    process.stdout.write("\x1Bc");
    console.log(
      `=== Welcome to Kompetisi Together ===\n=====    Backend Development    =====`
    );
    console.log(`\nServer\t : \x1b[4m%s\x1b[0m`, `http://localhost:${PORT}`);
    console.log(`Network\t : \x1b[4m%s\x1b[0m`, `http://${ipAddress}:${PORT}`);
    console.log(`Database : \x1b[32m%s\x1b[0m`, "https://" + connection.host);
    console.log("\nEnjoy developing!");
  } catch (error) {
    console.log(error);
  }
});
