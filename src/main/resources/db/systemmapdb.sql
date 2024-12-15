-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 14, 2024 at 12:39 PM
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
-- Database: `systemmapdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `blotter_records`
--

CREATE TABLE blotter_records (
  id BIGINT PRIMARY KEY,
  case_number VARCHAR(255) NOT NULL,
  complainant VARCHAR(255),
  date VARCHAR(255),
  description VARCHAR(255),
  issued VARCHAR(255),
  location VARCHAR(255),
  recorded_by VARCHAR(255),
  respondent VARCHAR(255),
  status VARCHAR(255),
  CONSTRAINT "UK_case_number" UNIQUE (case_number)
);

-- --------------------------------------------------------

--
-- Table structure for table `business`
--

CREATE TABLE business (
  id BIGINT PRIMARY KEY,
  address VARCHAR(255),
  lat VARCHAR(255),
  lng VARCHAR(255),
  name VARCHAR(255),
  owner VARCHAR(255),
  start_date VARCHAR(255)
);

-- Table structure for table "church"
CREATE TABLE church (
  id BIGINT PRIMARY KEY,
  lat DOUBLE PRECISION,
  lng DOUBLE PRECISION,
  name VARCHAR(255)
);

-- --------------------------------------------------------

--
-- Table structure for table `establishment`
--

CREATE TABLE establishment (
  id BIGINT PRIMARY KEY,
  description VARCHAR(255),
  lat DOUBLE PRECISION NOT NULL,
  lng DOUBLE PRECISION NOT NULL,
  name VARCHAR(255)
);

-- Table structure for table "house"
CREATE TABLE house (
  id BIGINT PRIMARY KEY,
  address VARCHAR(255),
  house_number VARCHAR(255),
  lat DOUBLE PRECISION,
  lng DOUBLE PRECISION,
  name VARCHAR(255)
);

-- Table structure for table "house_residents"
CREATE TABLE house_residents (
  house_id BIGINT NOT NULL,
  resident_id BIGINT NOT NULL,
  PRIMARY KEY (house_id, resident_id),
  CONSTRAINT fk_house FOREIGN KEY (house_id) REFERENCES house(id),
  CONSTRAINT fk_resident FOREIGN KEY (resident_id) REFERENCES residents(id)
);

-- Table structure for table "otps"
CREATE TABLE otps (
  id BIGINT PRIMARY KEY,
  code VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  expiry_date TIMESTAMP(6) NOT NULL,
  verified BOOLEAN NOT NULL
);

-- Table structure for table "residents"
CREATE TABLE residents (
  id BIGINT PRIMARY KEY,
  age INT NOT NULL,
  birth_date VARCHAR(255),
  birth_place VARCHAR(255),
  certificate_of_indigency_count INT NOT NULL,
  civil_status VARCHAR(255),
  clearance_count INT NOT NULL,
  first_name VARCHAR(255),
  first_time_job_seeker_count INT NOT NULL,
  gender VARCHAR(255),
  is_deceased BOOLEAN,
  last_name VARCHAR(255),
  latitude DOUBLE PRECISION,
  longitude DOUBLE PRECISION,
  middle_name VARCHAR(255),
  mobile_no VARCHAR(255),
  nationality VARCHAR(255),
  occupation VARCHAR(255),
  permanent_address VARCHAR(255),
  photo BYTEA,
  pwd BOOLEAN,
  suffix VARCHAR(255),
  telephone_no VARCHAR(255),
  temporary_address VARCHAR(255),
  voting_eligibility VARCHAR(255)
);

-- Table structure for table "role"
CREATE TABLE role (
  id BIGINT PRIMARY KEY,
  name VARCHAR(255)
);

-- Table structure for table "school"
CREATE TABLE school (
  id BIGINT PRIMARY KEY,
  lat DOUBLE PRECISION,
  lng DOUBLE PRECISION,
  name VARCHAR(255)
);

-- Table structure for table "user"
CREATE TABLE "user" (
  id BIGINT PRIMARY KEY,
  email VARCHAR(255) NOT NULL,
  enabled BOOLEAN NOT NULL,
  first_name VARCHAR(255),
  last_name VARCHAR(255),
  otp VARCHAR(255),
  otp_expiry TIMESTAMP(6),
  password VARCHAR(255),
  CONSTRAINT "UK_email" UNIQUE (email)
);

-- Table structure for table "user_roles"
CREATE TABLE user_roles (
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  PRIMARY KEY (user_id, role_id),
  CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "user"(id),
  CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES role(id)
);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `blotter_records`
--
ALTER TABLE `blotter_records`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK65tk6i38wi70yrj3nmdv02ok` (`case_number`);

--
-- Indexes for table `business`
--
ALTER TABLE `business`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `church`
--
ALTER TABLE `church`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `establishment`
--
ALTER TABLE `establishment`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `house`
--
ALTER TABLE `house`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `house_residents`
--
ALTER TABLE `house_residents`
  ADD KEY `FKkijvx1dmyq96aorq5abcjbjy7` (`resident_id`),
  ADD KEY `FK6ujxu4sdytkxk02xt0rqlw5pd` (`house_id`);

--
-- Indexes for table `otps`
--
ALTER TABLE `otps`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `residents`
--
ALTER TABLE `residents`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `school`
--
ALTER TABLE `school`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`);

--
-- Indexes for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD KEY `FKrhfovtciq1l558cw6udg0h0d3` (`role_id`),
  ADD KEY `FK55itppkw3i07do3h7qoclqd4k` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `blotter_records`
--
ALTER TABLE `blotter_records`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `business`
--
ALTER TABLE `business`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `church`
--
ALTER TABLE `church`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `establishment`
--
ALTER TABLE `establishment`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `house`
--
ALTER TABLE `house`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `otps`
--
ALTER TABLE `otps`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `residents`
--
ALTER TABLE `residents`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `school`
--
ALTER TABLE `school`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `house_residents`
--
ALTER TABLE `house_residents`
  ADD CONSTRAINT `FK6ujxu4sdytkxk02xt0rqlw5pd` FOREIGN KEY (`house_id`) REFERENCES `house` (`id`),
  ADD CONSTRAINT `FKkijvx1dmyq96aorq5abcjbjy7` FOREIGN KEY (`resident_id`) REFERENCES `residents` (`id`);

--
-- Constraints for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKrhfovtciq1l558cw6udg0h0d3` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
