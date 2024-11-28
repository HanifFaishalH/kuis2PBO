-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 28 Nov 2024 pada 07.22
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `toko_komputer`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `kategori`
--

CREATE TABLE `kategori` (
  `id_kategori` int(11) NOT NULL,
  `nama_kategori` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `kategori`
--

INSERT INTO `kategori` (`id_kategori`, `nama_kategori`) VALUES
(1, 'Hardware'),
(2, 'Software'),
(3, 'Aksesoris'),
(4, 'Komponen PC'),
(5, 'Gaming Gear'),
(6, 'Networking'),
(7, 'Laptop');

-- --------------------------------------------------------

--
-- Struktur dari tabel `penjualan`
--

CREATE TABLE `penjualan` (
  `id_penjualan` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `total_harian` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `penjualan`
--

INSERT INTO `penjualan` (`id_penjualan`, `tanggal`, `total_harian`) VALUES
(1, '2024-11-28', '2024-11-28 14:02:04'),
(2, '2024-11-27', '2024-11-27 10:25:36'),
(3, '2024-11-26', '2024-11-26 16:30:00'),
(4, '2024-11-25', '2024-11-25 09:15:00'),
(5, '2024-11-24', '2024-11-24 12:45:00');

-- --------------------------------------------------------

--
-- Struktur dari tabel `penjualan_detail`
--

CREATE TABLE `penjualan_detail` (
  `id_detail` int(11) NOT NULL,
  `id_penjualan` int(11) NOT NULL,
  `id_barang` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `harga_barang` decimal(12,2) NOT NULL,
  `subtotal` decimal(12,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `penjualan_detail`
--

INSERT INTO `penjualan_detail` (`id_detail`, `id_penjualan`, `id_barang`, `qty`, `harga_barang`, `subtotal`) VALUES
(1, 1, 1, 2, 450000.00, 900000.00),
(2, 1, 2, 1, 850000.00, 850000.00),
(3, 2, 4, 3, 250000.00, 750000.00),
(4, 2, 5, 1, 1500000.00, 1500000.00),
(5, 3, 6, 1, 4000000.00, 4000000.00),
(6, 3, 10, 2, 1200000.00, 2400000.00),
(7, 4, 11, 1, 3500000.00, 3500000.00),
(8, 4, 9, 1, 4500000.00, 4500000.00),
(9, 5, 13, 3, 450000.00, 1350000.00),
(10, 5, 14, 5, 150000.00, 750000.00);

-- --------------------------------------------------------

--
-- Struktur dari tabel `stock`
--

CREATE TABLE `stock` (
  `id_barang` int(11) NOT NULL,
  `nama_barang` varchar(100) NOT NULL,
  `merek` varchar(50) NOT NULL,
  `kapasitas` varchar(20) NOT NULL,
  `id_kategori` int(11) NOT NULL,
  `harga` decimal(12,2) NOT NULL,
  `stok_tersedia` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `stock`
--

INSERT INTO `stock` (`id_barang`, `nama_barang`, `merek`, `kapasitas`, `id_kategori`, `harga`, `stok_tersedia`) VALUES
(1, 'RAM 8GB DDR4', 'Kingston', '8GB', 1, 450000.00, 20),
(2, 'SSD 256GB', 'Samsung', '256GB', 1, 850000.00, 15),
(3, 'Windows 11 Pro', 'Microsoft', '-', 2, 2200000.00, 10),
(4, 'Mouse Wireless', 'Logitech', '-', 3, 250000.00, 50),
(5, 'Keyboard Mechanical', 'Razer', '-', 3, 1500000.00, 30),
(6, 'VGA GTX 1660', 'NVIDIA', '6GB', 4, 4000000.00, 10),
(7, 'Router Wi-Fi 6', 'TP-Link', '-', 6, 1200000.00, 20),
(8, 'Monitor 24 inch', 'LG', '-', 4, 2500000.00, 15),
(9, 'Gaming Chair', 'DXRacer', '-', 5, 4500000.00, 5),
(10, 'Headset Gaming', 'HyperX', '-', 5, 1200000.00, 25),
(11, 'Processor i5', 'Intel', '-', 4, 3500000.00, 8),
(12, 'Hard Drive 1TB', 'Seagate', '1TB', 1, 700000.00, 30),
(13, 'Antivirus Premium', 'Kaspersky', '-', 2, 450000.00, 50),
(14, 'USB Flash Drive 64GB', 'Sandisk', '64GB', 3, 150000.00, 100),
(15, 'Laptop ASUS ROG', 'ASUS', '16GB RAM, 1TB SSD', 7, 25000000.00, 5),
(16, 'Laptop Dell Inspiron', 'Dell', '8GB RAM, 512GB SSD', 7, 14000000.00, 10),
(17, 'Laptop Lenovo LOQ', 'Lenovo', '8GB RAM, 1TB HDD', 7, 8500000.00, 12),
(18, 'Laptop HP Pavilion', 'HP', '4GB RAM, 256GB SSD', 7, 12000000.00, 8),
(19, 'Laptop MacBook Air', 'Apple', '8GB RAM, 512GB SSD', 7, 18000000.00, 6);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `kategori`
--
ALTER TABLE `kategori`
  ADD PRIMARY KEY (`id_kategori`);

--
-- Indeks untuk tabel `penjualan`
--
ALTER TABLE `penjualan`
  ADD PRIMARY KEY (`id_penjualan`);

--
-- Indeks untuk tabel `penjualan_detail`
--
ALTER TABLE `penjualan_detail`
  ADD PRIMARY KEY (`id_detail`),
  ADD KEY `id_penjualan` (`id_penjualan`,`id_barang`),
  ADD KEY `id_barang` (`id_barang`);

--
-- Indeks untuk tabel `stock`
--
ALTER TABLE `stock`
  ADD PRIMARY KEY (`id_barang`),
  ADD KEY `id_kategori` (`id_kategori`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `kategori`
--
ALTER TABLE `kategori`
  MODIFY `id_kategori` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT untuk tabel `penjualan`
--
ALTER TABLE `penjualan`
  MODIFY `id_penjualan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT untuk tabel `penjualan_detail`
--
ALTER TABLE `penjualan_detail`
  MODIFY `id_detail` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT untuk tabel `stock`
--
ALTER TABLE `stock`
  MODIFY `id_barang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `penjualan_detail`
--
ALTER TABLE `penjualan_detail`
  ADD CONSTRAINT `penjualan_detail_ibfk_1` FOREIGN KEY (`id_barang`) REFERENCES `stock` (`id_barang`),
  ADD CONSTRAINT `penjualan_detail_ibfk_2` FOREIGN KEY (`id_penjualan`) REFERENCES `penjualan` (`id_penjualan`);

--
-- Ketidakleluasaan untuk tabel `stock`
--
ALTER TABLE `stock`
  ADD CONSTRAINT `stock_ibfk_1` FOREIGN KEY (`id_kategori`) REFERENCES `kategori` (`id_kategori`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
