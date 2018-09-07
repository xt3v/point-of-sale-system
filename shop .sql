-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Sep 07, 2018 at 06:52 PM
-- Server version: 10.1.31-MariaDB
-- PHP Version: 7.2.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `shop`
--

-- --------------------------------------------------------

--
-- Table structure for table `cashiers`
--

CREATE TABLE `cashiers` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `employee_id` varchar(255) NOT NULL,
  `admin` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cashiers`
--

INSERT INTO `cashiers` (`id`, `name`, `username`, `password`, `employee_id`, `admin`) VALUES
(20, 'Emmanuel Kadenge ', 'kadenge', 'password', 'kad123', 1),
(21, 'Carol Gakii', 'caro', 'password', 'caro123', 0),
(22, 'kevin wangombe', 'kevo', 'password', 'kevo233', 0);

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone` varchar(100) NOT NULL,
  `balance` float NOT NULL,
  `id_no` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`id`, `name`, `address`, `phone`, `balance`, `id_no`) VALUES
(12, 'Tin Boy', 'Amboseli', '0789098', 30, 12345),
(17, 'Stephen Oyeyo', 'Kisumu ', '0723293609', 173, 34518502),
(20, 'KAIBEI', 'RUWE', '0987654', -100, 123456789);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `sku` varchar(100) NOT NULL,
  `description` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` float NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `sku`, `description`, `name`, `price`, `quantity`) VALUES
(34, 'MIL868MIL', 'Ilara milk', 'Milk', 30, 0),
(35, 'PEN945PEN', 'Bic pen', 'Pen ', 10, 0),
(36, 'PEN932PEN', 'HB pencil', 'Pencil ', 5, 0),
(37, 'MAI54MAI', 'Quality Grade Floor', 'Maize Floor', 100, 0),
(38, 'WHE88WHE', 'Quality Wheat', 'Wheat Flour', 120, 0),
(39, 'SUG863SUG', 'Molo Sugar', 'Sugar 1kg', 180, 0),
(40, 'SLI230SLI', 'Cool slipper', 'Slippers ', 150, 0),
(41, 'EAR830EAR', 'samsung earphone', 'Earphones', 500, 0),
(43, 'IPH688IPH', 'Iphone 4', 'Iphone', 30000, 0),
(44, 'HP 708HP ', 'Core i5 laptop', 'HP Laptop', 60000, 0),
(45, 'SOA725SOA', 'Ushindi soap', 'Soap', 30, 0),
(46, 'KUK632KUK', 'lg', 'kuku', 10000, 0),
(47, 'TEL314TEL', 'Samsung TV', 'Television', 10000, 0),
(50, 'PIS313PIS', 'pishori rice 1kg', 'Pishori Rice', 50, 91),
(64, 'OMO41OMO', 'black', 'omo', 89, 1),
(65, 'WOO480WOO', 'Samsung woofer', 'Woofer', 8000, 0);

-- --------------------------------------------------------

--
-- Table structure for table `sales`
--

CREATE TABLE `sales` (
  `id` int(11) NOT NULL,
  `date` varchar(255) NOT NULL,
  `products` text NOT NULL,
  `total` float NOT NULL,
  `credited` float NOT NULL,
  `time` time NOT NULL,
  `sale_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sales`
--

INSERT INTO `sales` (`id`, `date`, `products`, `total`, `credited`, `time`, `sale_id`) VALUES
(8, '8/12/2018 ', '[{\"sku\":\"ror\",\"amount\":3}]', 60, 0, '02:27:44', 'T160C1'),
(9, '8/13/2018 ', '[{\"sku\":\"ror\",\"amount\":3}]', 60, 48, '15:35:31', 'T135C8'),
(10, '8/13/2018 ', '[{\"sku\":\"ror\",\"amount\":2}]', 40, 38, '06:40:06', 'T771C8'),
(11, '8/13/2018 ', '[{\"sku\":\"ror\",\"amount\":2}]', 40, 0, '01:49:43', 'T333C1'),
(12, '8/13/2018 ', '[{\"sku\":\"ror\",\"amount\":2}]', 40, 17, '20:13:31', 'T336C7'),
(13, '8/13/2018 ', '[{\"sku\":\"ror\",\"amount\":2}]', 40, 0, '18:02:51', 'T473C2'),
(14, '8/13/2018 ', '[{\"sku\":\"ror\",\"amount\":2}]', 40, 0, '02:58:23', 'T888C2'),
(15, '8/13/2018 ', '[{\"sku\":\"ror\",\"amount\":2}]', 40, 0, '15:29:31', 'T476C6'),
(16, '8/13/2018 ', '[{\"sku\":\"ror\",\"amount\":1}]', 20, 0, '22:49:11', 'T297C0'),
(17, '8/13/2018 ', '[{\"sku\":\"ror\",\"amount\":2}]', 40, 0, '02:19:13', 'T438C4'),
(18, '8/13/2018 ', '[{\"sku\":\"ror\",\"amount\":10}]', 200, 0, '10:37:36', 'T12C0'),
(19, '8/13/2018 ', '[{\"sku\":\"ror\",\"amount\":1}]', 20, 0, '20:25:29', 'T371C8'),
(20, '8/13/2018 ', '[{\"sku\":\"ror\",\"amount\":1}]', 20, 20, '12:05:22', 'T302C6'),
(21, '8/13/2018 ', '[{\"sku\":\"ror\",\"amount\":10},{\"sku\":\"ewe\",\"amount\":2}]', 2600, 0, '20:10:35', 'T120C1'),
(22, '8/13/2018 ', '[{\"sku\":\"ewe\",\"amount\":4}]', 4800, 0, '09:10:46', 'T806C1'),
(23, '8/15/2018 ', '[{\"sku\":\"WOO480WOO\",\"amount\":0}]', 0, 0, '02:58:30', 'T229C7'),
(24, '8/15/2018 ', '[{\"sku\":\"WOO480WOO\",\"amount\":0}]', 0, 0, '00:01:34', 'T272C4'),
(25, '8/15/2018 ', '[{\"sku\":\"WOO480WOO\",\"amount\":0}]', 0, 0, '00:53:29', 'T761C0'),
(26, '8/15/2018 ', '[{\"sku\":\"OMO41OMO\",\"amount\":100},{\"sku\":\"WOO480WOO\",\"amount\":1}]', 16900, 2900, '16:57:05', 'T545C0');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cashiers`
--
ALTER TABLE `cashiers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cashiers`
--
ALTER TABLE `cashiers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=66;

--
-- AUTO_INCREMENT for table `sales`
--
ALTER TABLE `sales`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
