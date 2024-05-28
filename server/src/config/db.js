import dotenv from "dotenv";
import fs from "fs";
import pg from "pg";
import pgs from "pg-connection-string";

const { Pool } = pg;

dotenv.config();

const { DBURL, SSLPATH } = process.env;

const config = pgs.parse(DBURL);

export const db = new Pool({
  host: config.host,
  database: config.database,
  user: config.user,
  password: config.password,
  port: config.port,
  ssl: {
    ca: fs.readFileSync(SSLPATH).toString(),
  },
});
