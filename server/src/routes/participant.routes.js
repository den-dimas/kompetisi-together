import express from "express";

import * as participantController from "../controllers/participant.controller.js";
import { requireAuth, requireParticipant } from "../middlewares/auth.middleware.js";

const router = express.Router();

/* === Route for Authentications === */
router.post("/login", participantController.login);
router.post("/logout", participantController.logout);
router.post("/register", participantController.register);

router.get("/:id", requireAuth, participantController.getParticipantById);
router.put("/:id", requireParticipant, participantController.changeParticipant);

router.get("/:id/kompetisi", requireParticipant, participantController.getParticipantKompetisi)

export default router;
