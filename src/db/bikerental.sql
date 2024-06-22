-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 22, 2024 at 04:41 PM
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
-- Database: `bikerental`
--

-- --------------------------------------------------------

--
-- Table structure for table `bikes`
--

CREATE TABLE `bikes` (
  `BikeId` varchar(6) NOT NULL,
  `Brand` varchar(20) DEFAULT NULL,
  `Model` varchar(20) DEFAULT NULL,
  `BasePricePerDay` int(11) DEFAULT NULL,
  `IsAvailable` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bikes`
--

INSERT INTO `bikes` (`BikeId`, `Brand`, `Model`, `BasePricePerDay`, `IsAvailable`) VALUES
('b001', 'Kawasaki', 'ninja h2', 80, 1),
('b002', 'Suzuki', 'Hayabusa', 70, 1),
('b003', 'Royal Enfield', 'GT 650', 150, 1),
('b004', 'TVS', 'RR310', 60, 1);

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `CustomerId` varchar(10) NOT NULL,
  `CustomerName` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`CustomerId`, `CustomerName`) VALUES
('CUS1', 'Ashok'),
('CUS2', 'jinju'),
('CUS3', 'yesu');

-- --------------------------------------------------------

--
-- Table structure for table `rental`
--

CREATE TABLE `rental` (
  `Rental` int(11) NOT NULL,
  `CustomerId` varchar(10) DEFAULT NULL,
  `CustomerName` varchar(20) DEFAULT NULL,
  `BikeId` varchar(6) DEFAULT NULL,
  `Brand` varchar(20) DEFAULT NULL,
  `Model` varchar(20) DEFAULT NULL,
  `Days` int(11) DEFAULT NULL,
  `Amount` int(11) DEFAULT NULL,
  `Rent_Date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rental`
--

INSERT INTO `rental` (`Rental`, `CustomerId`, `CustomerName`, `BikeId`, `Brand`, `Model`, `Days`, `Amount`, `Rent_Date`) VALUES
(30, 'CUS1', 'Ashok', 'b002', 'Suzuki', 'Hayabusa', 3, 210, '2024-06-05 17:01:20'),
(31, 'CUS2', 'jinju', 'b002', 'Suzuki', 'Hayabusa', 2, 140, '2024-06-05 17:01:20'),
(32, 'CUS3', 'yesu', 'b004', 'TVS', 'RR310', 60, 3600, '2024-06-05 17:04:55');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bikes`
--
ALTER TABLE `bikes`
  ADD PRIMARY KEY (`BikeId`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`CustomerId`);

--
-- Indexes for table `rental`
--
ALTER TABLE `rental`
  ADD PRIMARY KEY (`Rental`),
  ADD KEY `CustomerId` (`CustomerId`),
  ADD KEY `BikeId` (`BikeId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `rental`
--
ALTER TABLE `rental`
  MODIFY `Rental` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `rental`
--
ALTER TABLE `rental`
  ADD CONSTRAINT `rental_ibfk_1` FOREIGN KEY (`CustomerId`) REFERENCES `customers` (`CustomerId`),
  ADD CONSTRAINT `rental_ibfk_2` FOREIGN KEY (`BikeId`) REFERENCES `bikes` (`BikeId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
