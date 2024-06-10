# Kompetisi Together (Kompor)

![Kompor Logo](/etc/img/header.png)

Kompetisi Together, disingkat Kompor, adalah aplikasi yang dirancang untuk mendukung proses kolaborasi dalam mengikuti kompetisi. Aplikasi ini membantu siswa dan mahasiswa menemukan kompetisi yang sedang berlangsung atau akan datang, mencari teman atau partner untuk berkompetisi, dan fitur-fitur lainnya yang mendukung pengalaman kompetisi secara menyeluruh.

## 📖 Latar Belakang

Mengikuti kompetisi sering kali memerlukan kolaborasi dan persiapan yang matang. Banyak peserta kompetisi mengalami kesulitan dalam menemukan informasi kompetisi yang relevan atau menemukan anggota tim yang tepat. **Kompor** hadir untuk menjawab kebutuhan tersebut dengan menyediakan platform yang memungkinkan pengguna untuk:

- Menemukan kompetisi sesuai minat dan tingkat keahlian.
- Mencari partner atau tim untuk berkolaborasi dalam kompetisi.
- Mengelola dan mempersiapkan diri untuk kompetisi yang akan diikuti.

## ✨ Fitur Utama

### 1. Pencarian Kompetisi

Pengguna dapat mencari dan menemukan kompetisi berdasarkan kategori, tingkat, dan jadwal pendaftaran.

![Screenshot 3](/etc/img/dashboard_screen.png)

### 2. Pencarian Partner atau Tim

Fitur ini memungkinkan pengguna untuk mencari dan bergabung dengan tim atau partner yang sesuai dengan kebutuhan mereka dalam kompetisi.

![Screenshot 2](/etc/img/home_screen.png)

### 3. Pengelolaan Kompetisi

Pengguna dapat mengelola kompetisi yang diikuti, termasuk melihat detail kompetisi, jadwal, dan anggota tim.

![Screenshot Pengelolaan Kompetisi](/etc/img/kompetisi_details_screen.png)

### 4. Participant dan Penyelenggara

Pengguna dapat login sebagai Participant untuk mengikuti dan mencari kompetisi atau sebagai Penyelenggara untuk membuat dan melihat data kompetisi.

![Screenshot Pengelolaan](/etc/img/welcome_screen.png)

## 🛠️ Struktur Backend

Backend dari **Kompor** dibangun menggunakan Node.js dengan beberapa dependensi utama seperti Express untuk framework web, Cloudinary untuk manajemen media, dan PostgreSQL sebagai database. Berikut adalah struktur direktori dan cara menjalankan backend:

### Postman Dokumentasi

## [Kompor API Dokumentasi](https://documenter.getpostman.com/view/31059748/2sA3XLG4sn)

### Struktur Direktori

```
server/
│
├── src/
│   ├── controllers/
│   ├── middlewares/
│   ├── config/
│   ├── routes/
│   └── index.js
│
├── .env
├── package.json
└── README.md
```

### Konfigurasi dan Instalasi

1. **Instalasi Dependensi**

   ```bash
   cd server
   npm install
   ```

2. **Menjalankan Server dalam Mode Pengembangan**

   ```bash
   npm run dev
   ```

3. **Konfigurasi File `.env`**
   Buat file `.env` di root project dengan isi sebagai berikut:
   ```
   PORT=5000
   DATABASE_URL=postgres://user:password@localhost:5432/kompetisi_db
   CLOUDINARY_URL=cloudinary://api_key:api_secret@cloud_name
   JWT_SECRET=your_jwt_secret
   ```

## 🖥️ Struktur Frontend

Frontend dari **Kompor** dibangun menggunakan Android Studio dengan Java. Struktur aplikasi Android adalah sebagai berikut:

### Struktur Direktori

```
app/
│
├── src/
│   ├── main/
│   │   ├── java/com/kompor/
│   │   ├── res/
│   │   └── AndroidManifest.xml
│   │
│   └── test/
│
└── build.gradle
```

### Fitur dan Desain

- **MainActivity**: Aktivitas utama yang mengelola navigasi utama di aplikasi.
- **Fragments**: Menggunakan fragment untuk menampilkan layar-layar seperti pencarian kompetisi, detail kompetisi, dan profil pengguna.
- **View Binding**: Memanfaatkan View Binding untuk mengelola tampilan dengan lebih efektif dan aman.

### Cara Menjalankan

1. **Import Project ke Android Studio**

   - Buka Android Studio.
   - Pilih `Import Project` dan arahkan ke direktori `app`.

2. **Sync Gradle**

   - Setelah project terbuka, lakukan sinkronisasi Gradle.

3. **Run Aplikasi**
   - Klik tombol `Run` atau gunakan emulator untuk menjalankan aplikasi di perangkat Android.

---

## 💖 Struktur Program

### 1. Entity Relationship Diagram

![Screenshot 100](/etc/img/ERD.jpg)

### 2. API Flow

1. **Participant**

   ![Screenshot 122](/etc/img/participant_api.png)

2. **Penyelenggara**

   ![Screenshot 123](/etc/img/penyelenggara_api.png)

### 2. Program Flow

1. **Participant**

   ![Screenshot 124](/etc/img/flow_partcipant.jpg)

2. **Penyelenggara**

   ![Screenshot 125](/etc/img/flow_penyelenggara.jpg)

## 📄 Lisensi

Aplikasi ini dilisensikan di bawah [MIT License](LICENSE).

---
