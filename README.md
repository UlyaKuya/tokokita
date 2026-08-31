# To-Do List

Fitur To-Do List pada aplikasi TokoKita digunakan untuk membuat dan mengelola daftar tugas secara lokal menggunakan Room Database.

## Fitur To-Do List

### 1. Tambah Tugas

Pengguna dapat menambahkan tugas baru dengan memasukkan judul tugas.

Contoh:

- Mengerjakan Praktikum 5
- Membuat laporan
- Mengumpulkan tugas

### 2. Menampilkan Tugas

Semua tugas yang tersimpan akan ditampilkan pada halaman To-Do List.

### 3. Checklist Tugas

Setiap tugas memiliki checkbox untuk menandai status tugas.

- ☐ Belum selesai
- ☑ Selesai

Status checklist disimpan ke dalam database Room.

### 4. Update Status

Pengguna dapat mengubah status tugas dengan menekan checkbox.

Perubahan status akan disimpan ke database.

### 5. Hapus Tugas

Pengguna dapat menghapus tugas yang sudah tidak diperlukan.

### 6. Penyimpanan Lokal

Data To-Do List disimpan menggunakan **Room Database**, sehingga data tetap tersedia meskipun aplikasi ditutup dan dibuka kembali.

## Database

Nama database:

`tokokita_database`

Tabel:

`todos`

Struktur tabel:

| Kolom | Tipe | Keterangan |
|---|---|---|
| id | Integer | ID tugas |
| judul | String | Judul tugas |
| selesai | Boolean | Status selesai |

## Arsitektur Room

```text
TodoFragment
     │
     ▼
TodoViewModel
     │
     ▼
TodoRepository
     │
     ▼
TodoDao
     │
     ▼
AppDatabase
     │
     ▼
todos
