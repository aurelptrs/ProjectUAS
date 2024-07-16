-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3307
-- Generation Time: Jul 16, 2024 at 09:32 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `aplikasimahasiswa`
--

-- --------------------------------------------------------

--
-- Table structure for table `dosen`
--

CREATE TABLE `dosen` (
  `nid` varchar(20) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nama_lengkap` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `dosen`
--

INSERT INTO `dosen` (`nid`, `password`, `nama_lengkap`) VALUES
('D11.00001', '123', 'Prof. Lionel Messi'),
('D11.00002', '123', 'Dr. Cristiano Ronaldo'),
('D11.00003', '123', 'Dr. Neymar Jr.');

-- --------------------------------------------------------

--
-- Table structure for table `dosen_matkul`
--

CREATE TABLE `dosen_matkul` (
  `nid` varchar(20) NOT NULL,
  `kode_matkul` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `dosen_matkul`
--

INSERT INTO `dosen_matkul` (`nid`, `kode_matkul`) VALUES
('D11.00001', 'CS01'),
('D11.00001', 'CS02'),
('D11.00002', 'CS03'),
('D11.00002', 'CS04'),
('D11.00003', 'CS05'),
('D11.00003', 'CS06');

-- --------------------------------------------------------

--
-- Table structure for table `mahasiswa`
--

CREATE TABLE `mahasiswa` (
  `id` int(11) NOT NULL,
  `nim` varchar(20) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nama_lengkap` varchar(100) NOT NULL,
  `jumlah_sks` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `mahasiswa`
--

INSERT INTO `mahasiswa` (`id`, `nim`, `password`, `nama_lengkap`, `jumlah_sks`) VALUES
(38, 'A11.2022.00001', '123', 'Leonardo DiCaprio', 6),
(39, 'A11.2022.00002', '123', 'Scarlett Johansson', 6),
(40, 'A11.2022.00003', '123', 'Tom Hanks', 9),
(41, 'A11.2022.00004', '123', 'Jennifer Lawrence', 6),
(42, 'A11.2022.00005', '123', 'Brad Pitt', 6),
(43, 'A11.2022.00006', '123', 'Angelina Jolie', 6),
(44, 'A11.2022.00007', '123', 'Robert Downey Jr.', 6),
(45, 'A11.2022.00008', '123', 'Emma Watson', 6),
(46, 'A11.2022.00009', '123', 'Will Smith', 6),
(47, 'A11.2022.00010', '123', 'Chris Hemsworth', 12),
(48, 'A11.2022.00011', '123', 'Agung Setiawan', 3),
(49, 'A11.2022.00012', '123', 'Hasto', 0),
(50, 'A11.2022.00013', '123', 'Ayu Nur', 6);

-- --------------------------------------------------------

--
-- Table structure for table `matkul`
--

CREATE TABLE `matkul` (
  `kode_matkul` varchar(10) NOT NULL,
  `nama_matkul` varchar(100) NOT NULL,
  `jumlah_sks` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `matkul`
--

INSERT INTO `matkul` (`kode_matkul`, `nama_matkul`, `jumlah_sks`) VALUES
('CS01', 'Pemrograman Java', 3),
('CS02', 'Basis Data', 3),
('CS03', 'Struktur Data', 3),
('CS04', 'Jaringan Komputer', 3),
('CS05', 'Kecerdasan Buatan', 3),
('CS06', 'Sistem Operasi', 3);

-- --------------------------------------------------------

--
-- Table structure for table `nilai`
--

CREATE TABLE `nilai` (
  `id` int(11) NOT NULL,
  `nim` varchar(20) DEFAULT NULL,
  `kode_matkul` varchar(10) DEFAULT NULL,
  `nilai_tugas` float DEFAULT NULL,
  `nilai_uts` float DEFAULT NULL,
  `nilai_uas` float DEFAULT NULL,
  `nilai_akhir` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `nilai`
--

INSERT INTO `nilai` (`id`, `nim`, `kode_matkul`, `nilai_tugas`, `nilai_uts`, `nilai_uas`, `nilai_akhir`) VALUES
(50, 'A11.2022.00001', 'CS02', 70, 85, 80, 'B'),
(51, 'A11.2022.00002', 'CS01', 81, 95, 90, 'A'),
(52, 'A11.2022.00002', 'CS02', 75, 80, 85, 'B'),
(53, 'A11.2022.00003', 'CS03', 90, 90, 90, 'A'),
(54, 'A11.2022.00003', 'CS04', 85, 80, 90, 'A'),
(55, 'A11.2022.00004', 'CS03', 70, 65, 80, 'B'),
(56, 'A11.2022.00004', 'CS04', 75, 70, 75, 'B'),
(57, 'A11.2022.00005', 'CS05', 60, 55, 70, 'C'),
(58, 'A11.2022.00005', 'CS06', 65, 60, 65, 'C'),
(59, 'A11.2022.00006', 'CS05', 85, 80, 90, 'A'),
(60, 'A11.2022.00006', 'CS06', 80, 85, 90, 'A'),
(61, 'A11.2022.00007', 'CS01', 75, 70, 80, 'B'),
(62, 'A11.2022.00007', 'CS02', 70, 75, 80, 'B'),
(63, 'A11.2022.00008', 'CS03', 65, 60, 70, 'C'),
(64, 'A11.2022.00008', 'CS04', 60, 55, 65, 'C'),
(65, 'A11.2022.00009', 'CS05', 95, 90, 95, 'A'),
(66, 'A11.2022.00009', 'CS06', 90, 85, 90, 'A'),
(67, 'A11.2022.00010', 'CS01', 55, 50, 60, 'D'),
(69, 'A11.2022.00010', 'CS03', 75, 70, 80, 'B'),
(70, 'A11.2022.00010', 'CS04', 80, 85, 90, 'A'),
(71, 'A11.2022.00003', 'CS01', 81, 98, 88, 'A'),
(72, 'A11.2022.00010', 'CS01', 100, 100, 100, 'A'),
(73, 'A11.2022.00011', 'CS01', 81, 81, 81, 'B'),
(74, 'A11.2022.00013', 'CS02', 89, 96, 100, 'A'),
(75, 'A11.2022.00013', 'CS01', 88, 99, 88, 'A');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `dosen`
--
ALTER TABLE `dosen`
  ADD PRIMARY KEY (`nid`);

--
-- Indexes for table `dosen_matkul`
--
ALTER TABLE `dosen_matkul`
  ADD PRIMARY KEY (`nid`,`kode_matkul`),
  ADD KEY `kode_matkul` (`kode_matkul`);

--
-- Indexes for table `mahasiswa`
--
ALTER TABLE `mahasiswa`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nim` (`nim`);

--
-- Indexes for table `matkul`
--
ALTER TABLE `matkul`
  ADD PRIMARY KEY (`kode_matkul`);

--
-- Indexes for table `nilai`
--
ALTER TABLE `nilai`
  ADD PRIMARY KEY (`id`),
  ADD KEY `nim` (`nim`),
  ADD KEY `kode_matkul` (`kode_matkul`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `mahasiswa`
--
ALTER TABLE `mahasiswa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT for table `nilai`
--
ALTER TABLE `nilai`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=76;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `dosen_matkul`
--
ALTER TABLE `dosen_matkul`
  ADD CONSTRAINT `dosen_matkul_ibfk_1` FOREIGN KEY (`nid`) REFERENCES `dosen` (`nid`),
  ADD CONSTRAINT `dosen_matkul_ibfk_2` FOREIGN KEY (`kode_matkul`) REFERENCES `matkul` (`kode_matkul`);

--
-- Constraints for table `nilai`
--
ALTER TABLE `nilai`
  ADD CONSTRAINT `nilai_ibfk_1` FOREIGN KEY (`nim`) REFERENCES `mahasiswa` (`nim`),
  ADD CONSTRAINT `nilai_ibfk_2` FOREIGN KEY (`kode_matkul`) REFERENCES `matkul` (`kode_matkul`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
