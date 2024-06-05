create type tingkat as enum (
    'SMA',
    'UNIVERSITAS',
    'ALL'
);

create type kategori as enum (
    'GAME',
    'ESAI',
    'UI/UX',
    'DEBAT',
    'ROBOTIK',
    'SOFTWARE',
    'KESENIAN',
    'KARYA ILMIAH',
    'INTERNET OF THINGS'
);

create type status_anggota as enum (
    'MENUNGGU',
    'DITOLAK',
    'DITERIMA'
);

-- List kompetisi yang ada
create table kompetisi (
    id_kompetisi        uuid unique not null 
                        default(gen_random_uuid()),
    
    foto                varchar(500),
    nama_kompetisi      varchar(100) not null,
    id_penyelenggara    uuid not null,

    pendaftaran_dari    timestamp not null,
    pendaftaran_sampai  timestamp not null,
    deskripsi           varchar(500) not null,
    tutup_pendaftaran   bool not null
                        default(false),
    
    tingkat             tingkat not null,
    anggota_per_tim     int not null,
    kategori            kategori not null,
    is_paid_ad          bool not null,

    created_at          timestamp not null default(now()),
    updated_at          timestamp not null default(now()),

    primary key (id_kompetisi)
);

-- List user pembuat lomba
create table penyelenggara (
    id_penyelenggara    uuid unique not null 
                        default(gen_random_uuid()),
    
    logo                varchar(500) not null,
    nama                varchar(100) not null,
    deskripsi           varchar(500) not null,
    jumlah_kompetisi    int not null default(0),
    
    email               varchar(100) unique not null,
    password            varchar(200) not null,
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

    nama                varchar(50) not null,
    angkatan            int not null,
    tanggal_lahir       date not null,
    sekolah             varchar(50) not null,

    email               varchar(100) unique not null,
    password            varchar(200) not null,
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
    judul       varchar(50) not null,

    primary key (level)
);

create table achievements (
    kemenangan      int not null,
    judul           varchar(50) not null,

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