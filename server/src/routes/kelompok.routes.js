import express from "express";
import * as kelompokController from "../controllers/kelompok.controller.js";
import { requireAuth, requireParticipant } from "../middlewares/auth.middleware.js";

const router = express.Router();

router.use(requireAuth)

router.get("/", kelompokController.getAllKelompok);
router.post("/", requireParticipant, kelompokController.createKelompok);

router.get("/:id", kelompokController.getKelompokById);
router.post("/:id", requireParticipant, kelompokController.changePendaftaranKelompok);
router.post("/:id/apply", requireParticipant, kelompokController.daftarKelompok);
router.post("/:id/berkas", requireParticipant, kelompokController.uploadLinkBerkas);

export default router;
