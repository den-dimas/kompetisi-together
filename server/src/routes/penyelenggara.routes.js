import express from "express";

import * as penyelenggaraController from "../controllers/penyelenggara.controller.js";

const router = express.Router();

/* === Route for Authentications === */
router.post("/login", penyelenggaraController.login);
router.post("/logout", penyelenggaraController.logout);
router.post("/register", penyelenggaraController.register);

export default router;
