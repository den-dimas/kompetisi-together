import express from "express";

import * as kompetisiController from "../controllers/kompetisi.controller.js";

import {
  requireAuth,
  requireParticipant,
  requirePenyelenggara,
} from "../middlewares/auth.middleware.js";

const router = express.Router();

router.use(requireAuth);

/* === Route for Kompetisi === */
router.get("/", kompetisiController.getAllKompetisi);
router.post("/", requirePenyelenggara, kompetisiController.createKompetisi);

/* === Route for Kompetisi Details === */
router.get("/:id", kompetisiController.getKompetisiById);
router.put("/:id", kompetisiController.changeKompetisiDetail);


/* === Route for Pendaftaran kompetisi === */
// Get All Pendaftaran By Kompetisi ID
router.get("/:id/list-pendaftaran", requirePenyelenggara, kompetisiController.getAllPendaftaran);
// Create Pendaftaran To a Competition
router.post("/:id/list-pendaftaran/", requireParticipant, kompetisiController.createPendaftaran);
// Get Pendaftaran Details by Pendaftaran ID
router.get("/:id/list-pendaftaran/:id_pendaftaran", kompetisiController.getPendaftaranById);
// Change Pendaftaran Details by Pendaftaran ID
router.post("/:id/list-pendaftaran/:id_pendaftaran", requirePenyelenggara, kompetisiController.changePendaftaranDetails);

export default router;
