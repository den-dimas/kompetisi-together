-- create type tingkat as enum (
--     'SMA',
--     'UNIVERSITAS',
--     'ALL'
-- );

-- create type kategori as enum (
--     'GAME',
--     'ESAI',
--     'UI/UX',
--     'DEBAT',
--     'ROBOTIK',
--     'SOFTWARE',
--     'KESENIAN',
--     'KARYA ILMIAH',
--     'INTERNET OF THINGS'
-- );

-- create type status_anggota as enum (
--     'MENUNGGU',
--     'DITOLAK',
--     'DITERIMA'
-- );

-- List kompetisi yang ada
create table kompetisi (
    id_kompetisi        uuid unique not null 
                        default(gen_random_uuid()),
    
    foto                varchar(500),
    nama_kompetisi      varchar(100) not null,
    id_penyelenggara    uuid not null,

    pendaftaran_dari    timestamp,
    pendaftaran_sampai  timestamp,
    deskripsi           varchar(500),
    tutup_pendaftaran   bool not null
                        default(false),
    
    tingkat             tingkat,
    anggota_per_tim     int,
    kategori            kategori,
    is_paid_ad          bool,

    created_at          timestamp not null default(now()),
    updated_at          timestamp not null default(now()),

    primary key (id_kompetisi)
);

-- List user pembuat lomba
create table penyelenggara (
    id_penyelenggara    uuid unique not null 
                        default(gen_random_uuid()),
    
    logo                varchar(500),
    nama                varchar(100) unique not null,
    deskripsi           varchar(500),
    jumlah_kompetisi    int not null default(0),
    
    email               varchar(100) unique not null,
    password            varchar(100) not null,
    created_at          timestamp not null default(now()),
    updated_at          timestamp not null default(now()),

    primary key (id_penyelenggara)
);

-- List kelompok berdasarkan pembuat kelompok
create table kelompok (
    id_kelompok     uuid unique not null 
                    default(gen_random_uuid()),
    id_kompetisi    uuid not null,

    primary key (id_kompetisi, id_kelompok)
);

-- List anggota kelompok
create table anggota_kelompok (
    id_kelompok     uuid not null,
    id_participant  uuid not null,
    is_ketua        bool not null default(false),
    status_anggota  status_anggota not null default('MENUNGGU'),
    link_berkas     varchar(500),

    primary key (id_kelompok, id_participant)
);

-- List pendaftaran berdasarkan kelompok
create table list_pendaftaran (
    id_pendaftaran      uuid unique not null
                        default(gen_random_uuid()),
    id_kompetisi        uuid not null,
    id_kelompok         uuid not null,
    status_pembayaran   bool not null
                        default(false),
    status_pendaftaran  bool not null
                        default(false),
    note                varchar(500),

    primary key (id_pendaftaran)
);

-- List user pencari lomba
create table participant (
    id_participant      uuid unique not null 
                        default(gen_random_uuid()),

    nama                varchar(100) not null,
    angkatan            int,
    tanggal_lahir       date,
    sekolah             varchar(100),

    email               varchar(100) unique not null,
    password            varchar(32) not null,
    created_at          timestamp not null default(now()),
    updated_at          timestamp not null default(now()),

    level               int not null default(0),
    kemenangan          int not null default(0),
    jumlah_pendaftaran  int not null default(0),
    avg_rating          float not null default(0.0),

    primary key (id_participant)
);

-- Gamification
create table gelar (
    level       int unique not null,
    judul       varchar(100) not null,

    primary key (level)
);

create table achievements (
    kemenangan      int not null,
    judul           varchar(100) not null,

    primary key (kemenangan)
);

create table custom_achievements (
    id              uuid unique not null 
                    default(gen_random_uuid()),
    
    id_kompetisi    uuid,
    judul           varchar(100) not null,

    primary key (id)
);

create table participant_custom_achievements (
    id_participant      uuid not null,
    id_achievements     uuid unique not null,

    primary key (id_participant)
);


alter table kompetisi
    add foreign key (id_penyelenggara) references penyelenggara (id_penyelenggara);

alter table kelompok
    add foreign key (id_kompetisi) references kompetisi (id_kompetisi);

alter table anggota_kelompok
    add foreign key (id_kelompok) references kelompok (id_kelompok);
alter table anggota_kelompok
    add foreign key (id_participant) references participant (id_participant);

alter table list_pendaftaran
    add foreign key (id_kompetisi) references kompetisi (id_kompetisi);
alter table list_pendaftaran
    add foreign key (id_kelompok) references kelompok (id_kelompok);

alter table participant_custom_achievements
    add foreign key (id_participant) references participant (id_participant);
alter table participant_custom_achievements
    add foreign key (id_achievements) references custom_achievements (id);

alter table participant
    add foreign key (level) references gelar (level);
alter table participant
    add foreign key (kemenangan) references gelar (level);

-- Insert dummy data into penyelenggara table
INSERT INTO penyelenggara (logo, nama, deskripsi, email, password)
VALUES
    ('', 'Penyelenggara1', 'Deskripsi penyelenggara 1', 'penyelenggara1@example.com', '$2b$10$0v1ChzQmBzOe/8Wm0ZzGmeHVs0XXn0SChwWdhcTn7EodfOhTPyFiK'),
    ('', 'Penyelenggara2', 'Deskripsi penyelenggara 2', 'penyelenggara2@example.com', '$2b$10$OJQkgB7pWblZBrXh/fcmvO8nvqCETrP7pQ5d/zZyTllRAFYj4iwH6'),
    ('', 'Penyelenggara3', 'Deskripsi penyelenggara 3', 'penyelenggara3@example.com', '$2b$10$Xyk/RRm.F5bV58.f5L5GQuOaU8ciNZ5hr8bPpCOECL8ueK4J3PBVW'),
    ('', 'Penyelenggara4', 'Deskripsi penyelenggara 4', 'penyelenggara4@example.com', '$2b$10$GDJ39UTNiH9yMSeUQkRDSu3GpUHi4g1oRPLz43A5xPdFQkReRDrzS'),
    ('', 'Penyelenggara5', 'Deskripsi penyelenggara 5', 'penyelenggara5@example.com', '$2b$10$YAjmS06YO1XKZcrsXOhYneT/E0p9a1FMdpGJ5lfEg1hP6F9tY6v7y'),
    ('', 'Penyelenggara6', 'Deskripsi penyelenggara 6', 'penyelenggara6@example.com', '$2b$10$UlM6HS6qBzBlgoF6QrrFVO79VVy15yd/VwLLZa8EK5PVYtvM6zfqK'),
    ('', 'Penyelenggara7', 'Deskripsi penyelenggara 7', 'penyelenggara7@example.com', '$2b$10$h8tEokb1.yNtzx9h0ldDHeibTDYgtl5iaY2COhz8yJfoKT4I2nF7a'),
    ('', 'Penyelenggara8', 'Deskripsi penyelenggara 8', 'penyelenggara8@example.com', '$2b$10$tV7QYrE2cfiF9Xc5BPONVOTnSfa4ABaBbl6KshGyJ7ZZNkGoIwAVe'),
    ('', 'Penyelenggara9', 'Deskripsi penyelenggara 9', 'penyelenggara9@example.com', '$2b$10$ZqBpWBcDpJJUPrk8VgL2jO3reDBl78Fj/Be5nph.x2yNC/7jEsrm2'),
    ('', 'Penyelenggara10', 'Deskripsi penyelenggara 10', 'penyelenggara10@example.com', '$2b$10$wl1q2S1jb2r0EC1tPyIkHe4trxbQ0ofBJYzMNBmiJFGkXZdtuw/Ti');

-- Insert dummy data into kompetisi table
INSERT INTO kompetisi (foto, nama_kompetisi, id_penyelenggara, pendaftaran_dari, pendaftaran_sampai, deskripsi, tingkat, anggota_per_tim, kategori)
VALUES
    ('', 'Kompetisi1', (SELECT id_penyelenggara FROM penyelenggara ORDER BY RANDOM() LIMIT 1), '2024-06-01 09:00:00', '2024-06-30 23:59:59', 'Deskripsi kompetisi 1', (SELECT unnest FROM unnest(enum_range(NULL::tingkat)) ORDER BY RANDOM() LIMIT 1), 5, (SELECT unnest FROM unnest(enum_range(NULL::kategori)) ORDER BY RANDOM() LIMIT 1)),
    ('', 'Kompetisi2', (SELECT id_penyelenggara FROM penyelenggara ORDER BY RANDOM() LIMIT 1), '2024-07-01 09:00:00', '2024-07-30 23:59:59', 'Deskripsi kompetisi 2', (SELECT unnest FROM unnest(enum_range(NULL::tingkat)) ORDER BY RANDOM() LIMIT 1), 3, (SELECT unnest FROM unnest(enum_range(NULL::kategori)) ORDER BY RANDOM() LIMIT 1)),
    ('', 'Kompetisi3', (SELECT id_penyelenggara FROM penyelenggara ORDER BY RANDOM() LIMIT 1), '2024-08-01 09:00:00', '2024-08-30 23:59:59', 'Deskripsi kompetisi 3', (SELECT unnest FROM unnest(enum_range(NULL::tingkat)) ORDER BY RANDOM() LIMIT 1), 4, (SELECT unnest FROM unnest(enum_range(NULL::kategori)) ORDER BY RANDOM() LIMIT 1)),
    ('', 'Kompetisi4', (SELECT id_penyelenggara FROM penyelenggara ORDER BY RANDOM() LIMIT 1), '2024-09-01 09:00:00', '2024-09-30 23:59:59', 'Deskripsi kompetisi 4', (SELECT unnest FROM unnest(enum_range(NULL::tingkat)) ORDER BY RANDOM() LIMIT 1), 2, (SELECT unnest FROM unnest(enum_range(NULL::kategori)) ORDER BY RANDOM() LIMIT 1)),
    ('', 'Kompetisi5', (SELECT id_penyelenggara FROM penyelenggara ORDER BY RANDOM() LIMIT 1), '2024-10-01 09:00:00', '2024-10-30 23:59:59', 'Deskripsi kompetisi 5', (SELECT unnest FROM unnest(enum_range(NULL::tingkat)) ORDER BY RANDOM() LIMIT 1), 6, (SELECT unnest FROM unnest(enum_range(NULL::kategori)) ORDER BY RANDOM() LIMIT 1)),
    ('', 'Kompetisi6', (SELECT id_penyelenggara FROM penyelenggara ORDER BY RANDOM() LIMIT 1), '2024-11-01 09:00:00', '2024-11-30 23:59:59', 'Deskripsi kompetisi 6', (SELECT unnest FROM unnest(enum_range(NULL::tingkat)) ORDER BY RANDOM() LIMIT 1), 5, (SELECT unnest FROM unnest(enum_range(NULL::kategori)) ORDER BY RANDOM() LIMIT 1)),
    ('', 'Kompetisi7', (SELECT id_penyelenggara FROM penyelenggara ORDER BY RANDOM() LIMIT 1), '2024-12-01 09:00:00', '2024-12-30 23:59:59', 'Deskripsi kompetisi 7', (SELECT unnest FROM unnest(enum_range(NULL::tingkat)) ORDER BY RANDOM() LIMIT 1), 4, (SELECT unnest FROM unnest(enum_range(NULL::kategori)) ORDER BY RANDOM() LIMIT 1)),
    ('', 'Kompetisi8', (SELECT id_penyelenggara FROM penyelenggara ORDER BY RANDOM() LIMIT 1), '2024-01-01 09:00:00', '2024-01-30 23:59:59', 'Deskripsi kompetisi 8', (SELECT unnest FROM unnest(enum_range(NULL::tingkat)) ORDER BY RANDOM() LIMIT 1), 3, (SELECT unnest FROM unnest(enum_range(NULL::kategori)) ORDER BY RANDOM() LIMIT 1)),
    ('', 'Kompetisi9', (SELECT id_penyelenggara FROM penyelenggara ORDER BY RANDOM() LIMIT 1), '2024-02-01 09:00:00', '2024-02-28 23:59:59', 'Deskripsi kompetisi 9', (SELECT unnest FROM unnest(enum_range(NULL::tingkat)) ORDER BY RANDOM() LIMIT 1), 5, (SELECT unnest FROM unnest(enum_range(NULL::kategori)) ORDER BY RANDOM() LIMIT 1)),
    ('', 'Kompetisi10', (SELECT id_penyelenggara FROM penyelenggara ORDER BY RANDOM() LIMIT 1), '2024-03-01 09:00:00', '2024-03-30 23:59:59', 'Deskripsi kompetisi 10', (SELECT unnest FROM unnest(enum_range(NULL::tingkat)) ORDER BY RANDOM() LIMIT 1), 2, (SELECT unnest FROM unnest(enum_range(NULL::kategori)) ORDER BY RANDOM() LIMIT 1)),
    ('', 'Kompetisi11', (SELECT id_penyelenggara FROM penyelenggara ORDER BY RANDOM() LIMIT 1), '2024-04-01 09:00:00', '2024-04-30 23:59:59', 'Deskripsi kompetisi 11', (SELECT unnest FROM unnest(enum_range(NULL::tingkat)) ORDER BY RANDOM() LIMIT 1), 6, (SELECT unnest FROM unnest(enum_range(NULL::kategori)) ORDER BY RANDOM() LIMIT 1)),
    ('', 'Kompetisi12', (SELECT id_penyelenggara FROM penyelenggara ORDER BY RANDOM() LIMIT 1), '2024-05-01 09:00:00', '2024-05-30 23:59:59', 'Deskripsi kompetisi 12', (SELECT unnest FROM unnest(enum_range(NULL::tingkat)) ORDER BY RANDOM() LIMIT 1), 5, (SELECT unnest FROM unnest(enum_range(NULL::kategori)) ORDER BY RANDOM() LIMIT 1)),
    ('', 'Kompetisi13', (SELECT id_penyelenggara FROM penyelenggara ORDER BY RANDOM() LIMIT 1), '2024-06-01 09:00:00', '2024-06-30 23:59:59', 'Deskripsi kompetisi 13', (SELECT unnest FROM unnest(enum_range(NULL::tingkat)) ORDER BY RANDOM() LIMIT 1), 4, (SELECT unnest FROM unnest(enum_range(NULL::kategori)) ORDER BY RANDOM() LIMIT 1)),
    ('', 'Kompetisi14', (SELECT id_penyelenggara FROM penyelenggara ORDER BY RANDOM() LIMIT 1), '2024-07-01 09:00:00', '2024-07-30 23:59:59', 'Deskripsi kompetisi 14', (SELECT unnest FROM unnest(enum_range(NULL::tingkat)) ORDER BY RANDOM() LIMIT 1), 3, (SELECT unnest FROM unnest(enum_range(NULL::kategori)) ORDER BY RANDOM() LIMIT 1)),
    ('', 'Kompetisi15', (SELECT id_penyelenggara FROM penyelenggara ORDER BY RANDOM() LIMIT 1), '2024-08-01 09:00:00', '2024-08-30 23:59:59', 'Deskripsi kompetisi 15', (SELECT unnest FROM unnest(enum_range(NULL::tingkat)) ORDER BY RANDOM() LIMIT 1), 2, (SELECT unnest FROM unnest(enum_range(NULL::kategori)) ORDER BY RANDOM() LIMIT 1));

insert into gelar (level, judul)
values 
  (0, 'Pemula'),
  (1, 'Pemula'),
  (2, 'Pemula'),
  (3, 'Pemula'),
  (4, 'Amatir'),
  (5, 'Amatir'),
  (6, 'Amatir'),
  (7, 'Amatir'),
  (8, 'Amatir'),
  (9, 'Jago'),
  (10, 'Jago'),
  (11, 'Jago'),
  (12, 'Jago'),
  (13, 'Jago'),
  (14, 'Sepuh'),
  (15, 'Sepuh'),
  (16, 'Sepuh'),
  (17, 'Sepuh'),
  (18, 'Sepuh'),
  (19, 'Sepuh'),
  (20, 'Sepuh'),
  (21, 'GOD'),
  (22, 'GOD'),
  (23, 'GOD'),
  (24, 'GOD'),
  (25, 'GOD'),
  (26, 'GOD'),
  (27, 'GOD'),
  (28, 'GOD'),
  (29, 'GOD'),
  (30, 'Pemenang');