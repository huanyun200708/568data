/*
Navicat MySQL Data Transfer

Source Server         : LocalDB
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : huangqidb

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-04-12 21:53:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `onboardinfo`
-- ----------------------------
DROP TABLE IF EXISTS `onboardinfo`;
CREATE TABLE `onboardinfo` (
  `id` varchar(10) NOT NULL,
  `accountid` varchar(20) DEFAULT NULL,
  `appointtime` varchar(30) DEFAULT NULL,
  `onboardtime` varchar(30) DEFAULT NULL,
  `onboardaddress` varchar(4000) DEFAULT NULL,
  `appointstatus` varchar(1) DEFAULT NULL,
  `onboardstatus` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of onboardinfo
-- ----------------------------
INSERT INTO `onboardinfo` VALUES ('ob82947', 'u0001', null, null, '济南', null, null);
INSERT INTO `onboardinfo` VALUES ('ob_30716', 'u0001', null, null, '济南', null, null);
INSERT INTO `onboardinfo` VALUES ('ob_7499', 'u0001', null, null, '济南', null, null);
