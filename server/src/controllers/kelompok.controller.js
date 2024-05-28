import { db } from "../config/db.js";
import { APIResponse } from "../config/utils.js";

export const getAllKelompok = async (req, res) => {
  const { id_kompetisi } = req.body;

  try {
    const result = await db.query(
      `SELECT * FROM kelompok WHERE id_kompetisi = $1`,
      [id_kompetisi]
    );

    const data = result.rows || [];

    return APIResponse(res, 200, data, "Success get all groups!");
  } catch (error) {
    if (error.code === "22P02")
      return APIResponse(res, 400, null, "Wrong competition id!");

    return res.status(500).json(error);
  }
};

export const createKelompok = async (req, res) => {
  const { id_kompetisi, id_pembuat } = req.body;

  try {
    let result = await db.query(
      "SELECT * FROM anggota_kelompok NATURAL JOIN kelompok WHERE id_kompetisi = $1 AND id_participant = $2 AND is_ketua = true",
      [id_kompetisi, id_pembuat]
    );

    // prettier-ignore
    if (!!result.rows[0])
      return APIResponse(res, 400, null, "You already created a group for this competition!");

    result = await db.query(
      `
      INSERT INTO kelompok (id_kompetisi)
      VALUES ($1)
      RETURNING *
      `,
      [id_kompetisi]
    );

    const data = result.rows[0];

    result = await db.query(
      `
      INSERT INTO anggota_kelompok (id_kelompok, id_participant, status_anggota, link_berkas, is_ketua)
      VALUES ($1, $2, 'DITERIMA', '', true)
      RETURNING *
    `,
      [data.id_kelompok, id_pembuat]
    );

    if (!result.rows[0]) return APIResponse(res, 400, null, "Wrong insertion!");

    return APIResponse(res, 200, data, "Success create a group!");
  } catch (error) {
    console.log(error);

    if (error.code === "22P02")
      return APIResponse(res, 400, null, "Wrong inputs!");

    return res.status(500).json(error);
  }
};

export const getKelompokById = async (req, res) => {
  const { id } = req.params;

  try {
    const result = await db.query(
      `
      SELECT id_participant, nama, is_ketua, angkatan, sekolah, tanggal_lahir, level, status_anggota, link_berkas
      FROM anggota_kelompok
      NATURAL JOIN participant
      WHERE id_kelompok = $1
      `,
      [id]
    );

    const data = result.rows;

    return APIResponse(res, 200, data, "Success get group details!");
  } catch (error) {
    if (error.code === "22P02")
      return APIResponse(res, 400, null, "Wrong competition id!");

    return res.status(500).json(error);
  }
};

export const changePendaftaranKelompok = async (req, res) => {
  const { id } = req.params;
  const { id_ketua, id_participant, status_anggota } = req.body;

  if (id_ketua === id_participant)
    return APIResponse(res, 400, null, "Cannot accept or reject yourself!");

  if (status_anggota === "MENUNGGU")
    return APIResponse(res, 400, null, "Cannot change status to MENUNGGU!");

  try {
    let result = await db.query(
      "SELECT * FROM anggota_kelompok WHERE id_kelompok = $1 AND id_participant = $2",
      [id, id_ketua]
    );

    // prettier-ignore
    if (!!result.rows[0] && !result.rows[0].is_ketua)
      return APIResponse(res, 400, null, "You're not the group leader!");

    if (status_anggota === "DITOLAK")
      result = await db.query(
        `DELETE FROM anggota_kelompok
        WHERE id_participant = $1 AND id_kelompok = $2
        RETURNING *
        `,
        [id_participant, id]
      );
    else
      result = await db.query(
        `UPDATE anggota_kelompok
        SET status_anggota = $1
        WHERE id_participant = $2 AND id_kelompok = $3
        RETURNING *
        `,
        [status_anggota, id_participant, id]
      );

    const data = result.rows[0];

    if (!result.rows[0]) return APIResponse(res, 400, null, "Wrong insertion!");

    return APIResponse(res, 200, data, "Success change member applications!");
  } catch (error) {
    console.log(error);

    if (error.code === "22P02")
      return APIResponse(res, 400, null, "Wrong inputs!");

    return res.status(500).json(error);
  }
};

export const daftarKelompok = async (req, res) => {
  const { id } = req.params;
  const { id_participant } = req.body;

  try {
    let result = await db.query(
      "SELECT id_participant, is_ketua FROM anggota_kelompok NATURAL JOIN kelompok WHERE id_participant = $1 AND id_kelompok = $2",
      [id_participant, id]
    );

    if (!!result.rows[0])
      return APIResponse(res, 400, null, "You already apply for the group!");

    result = await db.query(
      `INSERT INTO anggota_kelompok (id_kelompok, id_participant, is_ketua, status_anggota)
      VALUES ($1, $2, false, 'MENUNGGU')
      RETURNING *
      `,
      [id, id_participant]
    );

    const data = result.rows[0];

    if (!data) return APIResponse(res, 400, null, "Wrong insertion");

    return APIResponse(res, 200, data, "Success apply group!");
  } catch (error) {
    console.log(error);

    if (error.code === "22P02")
      return APIResponse(res, 400, null, "Wrong competition id!");

    return res.status(500).json(error);
  }
};

export const uploadLinkBerkas = async (req, res) => {
  const { id } = req.params;
  const { id_participant, link_berkas } = req.body;

  try {
    let result = await db.query(
      "UPDATE anggota_kelompok SET link_berkas = $1 WHERE id_kelompok = $2 AND id_participant = $3 RETURNING *",
      [link_berkas, id, id_participant]
    );

    const data = result.rows[0];

    if (!result.rows[0]) return APIResponse(res, 400, null, "Wrong insertion!");

    return APIResponse(res, 200, data, "Success updating link berkas!");
  } catch (error) {
    console.log(error);

    if (error.code === "22P02")
      return APIResponse(res, 400, null, "Wrong inputs!");

    return res.status(500).json(error);
  }
};
