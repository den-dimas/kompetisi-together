import express from "express";

import * as penyelenggaraController from "../controllers/penyelenggara.controller.js";
import { requireAuth, requirePenyelenggara } from "../middlewares/auth.middleware.js";

const router = express.Router();

/* === Route for Authentications === */
router.post("/login", penyelenggaraController.login);
router.post("/logout", penyelenggaraController.logout);
router.post("/register", penyelenggaraController.register);

router.get("/:id", requireAuth, penyelenggaraController.getPenyelenggaraById);
router.put("/:id", requirePenyelenggara, penyelenggaraController.changePenyelenggara);


router.get("/:id/kompetisi", requirePenyelenggara, penyelenggaraController.getPenyelenggaraKompetisi)

export default router;
