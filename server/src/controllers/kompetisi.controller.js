import { db } from "../config/db.js";
import { APIResponse, BaseApiResponse } from "../config/utils.js";

export const getAllKompetisi = async (req, res) => {
  const {
    pendaftaran_dari,
    pendaftaran_sampai,
    tingkat,
    anggota_per_tim,
    kategori,
  } = req.body;

  try {
    let query = `
    SELECT * FROM kompetisi
    WHERE 1=1
  `;

    const result = await db.query(query);

    const data = result.rows || [];

    return APIResponse(res, 200, data, "Successfully get all competitions");
  } catch (error) {
    console.log(error);

    return res.status(500).json(error);
  }
};

export const getKompetisiByKategori = async (req, res) => {
  const { kategori } = req.body;

  try {
    const result = await db.query(
      "SELECT * FROM kompetisi WHERE kategori = $1",
      [kategori]
    );

    const data = result?.rows;

    // prettier-ignore
    return APIResponse(res, 200, data, "Success get competition in category " + kategori);
  } catch (err) {
    console.log(err);

    return res.status(500).json(err);
  }
};

export const getPaidKompetisi = async (req, res) => {
  try {
    const result = await db.query(`
      SELECT * FROM kompetisi
      WHERE is_paid_ad = true
    `);

    const data = result.rows || [];

    return APIResponse(res, 200, data, "Successfully get all competitions");
  } catch (error) {
    console.log(error);

    return res.status(500).json(error);
  }
};

export const createKompetisi = async (req, res) => {
  const {
    id_penyelenggara,
    nama_kompetisi,
    pendaftaran_dari,
    pendaftaran_sampai,
    deskripsi,
    tutup_pendaftaran,
    tingkat,
    anggota_per_tim,
    kategori,
  } = req.body;

  try {
    const result = await db.query(
      `
      INSERT INTO kompetisi (id_penyelenggara, nama_kompetisi, pendaftaran_dari, pendaftaran_sampai, deskripsi, tutup_pendaftaran, tingkat, anggota_per_tim, kategori)
      VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9)
      RETURNING *
      `,
      // prettier-ignore
      [id_penyelenggara, nama_kompetisi, pendaftaran_dari, pendaftaran_sampai, deskripsi, tutup_pendaftaran, tingkat, anggota_per_tim, kategori]
    );

    const data = result.rows[0];

    // prettier-ignore
    if (!data)
      return APIResponse(res, 400, null, "Failed to create competition!\nCheck again your inputs!");

    return APIResponse(res, 200, data, "Success create a competition!");
  } catch (error) {
    if (error.code === "22P02")
      return res.status(400).json(BaseApiResponse(null, "Wrong types!"));

    return res.status(500).json(error);
  }
};

export const getKompetisiById = async (req, res) => {
  const { id } = req.params;

  try {
    const result = await db.query(
      `
      SELECT * FROM kompetisi
      WHERE id_kompetisi=$1
    `,
      [id]
    );

    const data = result.rows[0];

    if (!data) return APIResponse(res, 404, null, "No competition found!");

    return APIResponse(res, 200, data, "Successfully get all competitions");
  } catch (error) {
    return res.status(500).json(error);
  }
};

export const changeKompetisiDetail = async (req, res) => {
  const { id } = req.params;
  const {
    nama_kompetisi,
    pendaftaran_dari,
    pendaftaran_sampai,
    deskripsi,
    tutup_pendaftaran,
    tingkat,
    anggota_per_tim,
    kategori,
  } = req.body;

  try {
    const result = await db.query(
      `
      UPDATE kompetisi 
      SET nama_kompetisi = $2, 
          pendaftaran_dari = $3,
          pendaftaran_sampai = $4, 
          deskripsi = $5, 
          tutup_pendaftaran = $6, 
          tingkat = $7,
          anggota_per_tim = $8, 
          kategori = $9
      WHERE id_kompetisi = $1
      RETURNING *
      `,
      // prettier-ignore
      [id, nama_kompetisi, pendaftaran_dari, pendaftaran_sampai, deskripsi, tutup_pendaftaran, tingkat, anggota_per_tim, kategori]
    );

    const data = result.rows[0];

    // prettier-ignore
    if (!data)
      return APIResponse(res, 400, null, "Failed to change competition!\nCheck the competition ID!");

    return APIResponse(res, 200, data, "Success change competition details!");
  } catch (error) {
    if (error.code === "22P02")
      return res.status(400).json(BaseApiResponse(null, "Wrong types!"));

    return res.status(500).json(error);
  }
};

export const getAllPendaftaran = async (req, res) => {
  const { id } = req.params;

  try {
    const result = await db.query(
      `
      SELECT * FROM list_pendaftaran
      WHERE id_kompetisi=$1
    `,
      [id]
    );

    const data = result.rows || [];

    return APIResponse(res, 200, data, "Successfully get all registrations");
  } catch (error) {
    // prettier-ignore
    if (error.code === "22P02")
      return APIResponse(res, 404, null, "Failed to get registrations, check again your input!")

    return res.status(500).json(error);
  }
};

export const createPendaftaran = async (req, res) => {
  const { id } = req.params;
  const { id_kelompok, id_participant } = req.body;

  try {
    let result = await db.query(
      `SELECT * FROM anggota_kelompok 
      NATURAL JOIN kelompok
      WHERE kelompok.id_kompetisi = $1 AND kelompok.id_kelompok = $2 AND id_participant = $3`,
      [id, id_kelompok, id_participant]
    );

    if (!result.rows[0])
      return APIResponse(res, 404, null, "No such group on this competition!");

    if (!result.rows[0].is_ketua)
      return APIResponse(res, 400, null, "You're not the group leader!");

    result = await db.query(
      "SELECT anggota_per_tim FROM kompetisi WHERE id_kompetisi = $1",
      [id]
    );

    const minimumAnggota = result.rows[0].anggota_per_tim;

    result = await db.query(
      'SELECT COUNT(*) AS "jumlah_anggota" FROM anggota_kelompok WHERE id_kelompok = $1',
      [id_kelompok]
    );

    const jumlah_anggota = result.rows[0].jumlah_anggota;

    // prettier-ignore
    if (parseInt(jumlah_anggota) !== parseInt(minimumAnggota))
      return APIResponse(res, 400, null, `You need ${minimumAnggota} members to apply for this competition!`);

    result = await db.query(
      "INSERT INTO list_pendaftaran (id_kompetisi, id_kelompok) VALUES ($1, $2) RETURNING *",
      [id, id_kelompok]
    );

    const data = result.rows[0];

    if (!result.rows[0]) return APIResponse(res, 400, null, "Wrong insertion!");

    return APIResponse(res, 200, data, "Success register competition!");
  } catch (error) {
    console.log(error);

    if (error.code === "22P02")
      return APIResponse(res, 400, null, "Wrong inputs!");

    return res.status(500).json(error);
  }
};

export const getPendaftaranById = async (req, res) => {
  const { id, id_pendaftaran } = req.params;

  try {
    let result = await db.query(
      `SELECT * FROM list_pendaftaran 
      WHERE id_kompetisi = $1 AND id_pendaftaran = $2`,
      [id, id_pendaftaran]
    );

    const data = result.rows[0];

    if (!data)
      return APIResponse(res, 404, null, "No such registration found!");

    return APIResponse(res, 200, data, "Success get registration details!");
  } catch (error) {
    return res.status(500).json(error);
  }
};

export const changePendaftaranDetails = async (req, res) => {
  const { id, id_pendaftaran } = req.params;
  const { status_pembayaran, status_pendaftaran, note } = req.body;

  try {
    const result = await db.query(
      `UPDATE list_pendaftaran
      SET status_pembayaran = $1,
          status_pendaftaran = $2,
          note = $3
      WHERE id_kompetisi = $4 AND id_pendaftaran = $5
      RETURNING *`,
      [status_pembayaran, status_pendaftaran, note, id, id_pendaftaran]
    );

    const data = result?.rows[0];

    if (!data) return APIResponse(res, 400, null, "Wrong insertions!");

    return APIResponse(res, 200, data, "Success change registration details!");
  } catch (error) {
    console.log(error);

    return APIResponse(res, 500, null, error);
  }
};
