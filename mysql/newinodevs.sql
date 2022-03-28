-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Tempo de geração: 28-Mar-2022 às 19:52
-- Versão do servidor: 8.0.27
-- versão do PHP: 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `newinodevs`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `concessionaria`
--

DROP TABLE IF EXISTS `concessionaria`;
CREATE TABLE IF NOT EXISTS `concessionaria` (
  `codigo` int NOT NULL,
  `nome` varchar(80) NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- --------------------------------------------------------

--
-- Estrutura da tabela `conta`
--

DROP TABLE IF EXISTS `conta`;
CREATE TABLE IF NOT EXISTS `conta` (
  `codigo` int NOT NULL,
  `nome` varchar(80) NOT NULL,
  `consumo` varchar(80) NOT NULL,
  `desconto` varchar(80) NOT NULL,
  `data_de_criacao` date NOT NULL,
  `data_de_lancamento` date NOT NULL,
  `data_de_vencimento` date NOT NULL,
  `dias` varchar(80) NOT NULL,
  `dados_adicionais` varchar(80) NOT NULL,
  `valor` float NOT NULL,
  `valor_total` float NOT NULL,
  `base_calculo` int NOT NULL,
  `tipo_conta` enum('agua','energia','gas') NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- --------------------------------------------------------

--
-- Estrutura da tabela `contrato`
--

DROP TABLE IF EXISTS `contrato`;
CREATE TABLE IF NOT EXISTS `contrato` (
  `codigo` int NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- --------------------------------------------------------

--
-- Estrutura da tabela `endereco`
--

DROP TABLE IF EXISTS `endereco`;
CREATE TABLE IF NOT EXISTS `endereco` (
  `cep` int NOT NULL,
  `municipio` varchar(80) NOT NULL,
  `bairro` varchar(80) NOT NULL,
  `rua` varchar(80) NOT NULL,
  `numero` int NOT NULL,
  `complemento` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`cep`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- --------------------------------------------------------

--
-- Estrutura da tabela `unidade`
--

DROP TABLE IF EXISTS `unidade`;
CREATE TABLE IF NOT EXISTS `unidade` (
  `nome` varchar(80) NOT NULL,
  `cnpj` int NOT NULL,
  `telefone` varchar(14) NOT NULL DEFAULT '(00)00000-0000',
  `endereco` int NOT NULL,
  PRIMARY KEY (`cnpj`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
