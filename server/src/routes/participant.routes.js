import express from "express";

import * as participantController from "../controllers/participant.controller.js";

const router = express.Router();

/* === Route for Authentications === */
router.post("/login", participantController.login);
router.post("/logout", participantController.logout);
router.post("/register", participantController.register);

export default router;
