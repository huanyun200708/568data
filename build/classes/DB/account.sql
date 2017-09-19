/*
Navicat MySQL Data Transfer

Source Server         : LocalDB
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : huangqidb

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-04-12 19:07:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `account`
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `accountid` varchar(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  `age` int(3) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `message` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('76071', 'aa', '8461', '0', 'null', 'null', 'null', 'aa');
INSERT INTO `account` VALUES ('34361', 'bb', '97901', '0', 'null', 'null', 'null', 'bb');
INSERT INTO `account` VALUES ('53320', 'cc', '55070', '0', 'null', 'null', 'null', 'cc');
INSERT INTO `account` VALUES ('20306', 'dd', '14729', '0', 'null', 'null', 'null', 'dd');
INSERT INTO `account` VALUES ('85988', 'ee', '10325', '0', 'null', 'null', 'null', 'ee');
INSERT INTO `account` VALUES ('14781', 'ff', '70793', '0', 'null', 'null', 'null', 'ff');
INSERT INTO `account` VALUES ('51777', 'gg', '54105', '0', 'null', 'null', 'null', 'gg');
INSERT INTO `account` VALUES ('91057', 'hh', '40400', '0', 'null', 'null', 'null', 'hh');
INSERT INTO `account` VALUES ('36727', 'ii', '64229', '0', 'null', 'null', 'null', 'ii');
INSERT INTO `account` VALUES ('16650', 'jj', '5891', '0', 'null', 'null', 'null', 'jj');
INSERT INTO `account` VALUES ('41544', 'kk', '79600', '0', 'null', 'null', 'null', 'kk');
INSERT INTO `account` VALUES ('87324', 'll', '54363', '0', 'null', 'null', 'null', 'null');
INSERT INTO `account` VALUES ('95537', 'ddd', '30909', '0', 'null', 'null', 'null', 'null');
INSERT INTO `account` VALUES ('339', 'aaa', '26558', '0', 'null', 'null', 'null', 'null');
INSERT INTO `account` VALUES ('38266', 'ddd', '49658', '0', 'null', 'null', 'null', 'null');
INSERT INTO `account` VALUES ('23154', 'aaaa', '45542', '0', 'null', 'null', 'null', 'aaa');
