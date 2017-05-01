/*
Navicat MySQL Data Transfer

Source Server         : localhosy
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : zhkuas

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2015-04-07 20:28:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for zk_admin
-- ----------------------------
DROP TABLE IF EXISTS `zk_admin`;
CREATE TABLE `zk_admin` (
  `adminId` tinyint(4) NOT NULL AUTO_INCREMENT,
  `uId` int(11) NOT NULL,
  `adminName` varchar(25) NOT NULL,
  `adminPwd` varchar(32) NOT NULL,
  PRIMARY KEY (`adminId`),
  KEY `FK_Reference_24` (`uId`),
  CONSTRAINT `FK_Reference_uid` FOREIGN KEY (`uId`) REFERENCES `zk_user` (`uId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_campus
-- ----------------------------
DROP TABLE IF EXISTS `zk_campus`;
CREATE TABLE `zk_campus` (
  `campusId` int(11) NOT NULL,
  `campusName` varchar(250) NOT NULL,
  PRIMARY KEY (`campusId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_campus_class_rs
-- ----------------------------
DROP TABLE IF EXISTS `zk_campus_class_rs`;
CREATE TABLE `zk_campus_class_rs` (
  `campusClassRSId` int(11) NOT NULL AUTO_INCREMENT,
  `campusId` int(11) NOT NULL,
  `classNo` varchar(15) NOT NULL,
  PRIMARY KEY (`campusClassRSId`),
  UNIQUE KEY `classNo_2` (`classNo`),
  KEY `classNo` (`classNo`),
  KEY `FK_Reference_campusId` (`campusId`),
  CONSTRAINT `FK_Reference_campusId` FOREIGN KEY (`campusId`) REFERENCES `zk_campus` (`campusId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Reference_classNo` FOREIGN KEY (`classNo`) REFERENCES `zk_class` (`classNo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=572 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_class
-- ----------------------------
DROP TABLE IF EXISTS `zk_class`;
CREATE TABLE `zk_class` (
  `classId` int(11) NOT NULL AUTO_INCREMENT,
  `className` varchar(25) NOT NULL,
  `classNo` varchar(15) NOT NULL,
  `majorNo` varchar(10) DEFAULT NULL,
  `grade` varchar(4) DEFAULT NULL,
  PRIMARY KEY (`classId`),
  UNIQUE KEY `classNo` (`classNo`),
  KEY `FK_Reference_majorNo` (`majorNo`),
  CONSTRAINT `FK_Reference_majorNo` FOREIGN KEY (`majorNo`) REFERENCES `zk_major` (`majorNo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1148 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_classroom
-- ----------------------------
DROP TABLE IF EXISTS `zk_classroom`;
CREATE TABLE `zk_classroom` (
  `classroomId` int(11) NOT NULL AUTO_INCREMENT,
  `classroomName` varchar(250) NOT NULL,
  `classroomNo` varchar(10) NOT NULL,
  `schoolBuildingNo` varchar(10) NOT NULL,
  PRIMARY KEY (`classroomId`),
  KEY `FK_Reference_schoolBuildingNo_1` (`schoolBuildingNo`),
  KEY `classroomNo` (`classroomNo`),
  CONSTRAINT `FK_Reference_schoolBuildingNo_1` FOREIGN KEY (`schoolBuildingNo`) REFERENCES `zk_school_building` (`schoolBuildingNo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=307 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_class_course
-- ----------------------------
DROP TABLE IF EXISTS `zk_class_course`;
CREATE TABLE `zk_class_course` (
  `ccId` int(11) NOT NULL AUTO_INCREMENT,
  `classNo` varchar(15) NOT NULL,
  `termCourseId` int(11) NOT NULL,
  PRIMARY KEY (`ccId`),
  KEY `xqcid` (`termCourseId`),
  KEY `FK_Reference_classNo_1` (`classNo`),
  CONSTRAINT `FK_Reference_classNo_1` FOREIGN KEY (`classNo`) REFERENCES `zk_class` (`classNo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Reference_termCourseId_1` FOREIGN KEY (`termCourseId`) REFERENCES `zk_term_course` (`termCourseId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=28941 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_course
-- ----------------------------
DROP TABLE IF EXISTS `zk_course`;
CREATE TABLE `zk_course` (
  `cId` int(11) NOT NULL AUTO_INCREMENT,
  `cNo` varchar(10) NOT NULL,
  `cName` varchar(25) NOT NULL,
  `cEnglishName` varchar(200) DEFAULT NULL,
  `organizationNo` varchar(25) DEFAULT NULL,
  `cNameNo` varchar(10) NOT NULL,
  `courseType` varchar(25) DEFAULT NULL,
  `qualityHours` decimal(4,1) DEFAULT NULL,
  `cCredits` decimal(3,1) NOT NULL,
  `teachingHours` decimal(4,1) DEFAULT NULL,
  `experimentalHours` decimal(3,1) DEFAULT NULL,
  `computerClassHours` decimal(3,1) DEFAULT NULL,
  `otherClassHours` decimal(3,1) DEFAULT NULL,
  `teachingProgram` text,
  PRIMARY KEY (`cId`),
  UNIQUE KEY `cNo` (`cNo`),
  KEY `organizationNo` (`organizationNo`),
  CONSTRAINT `zk_course_ibfk_1` FOREIGN KEY (`organizationNo`) REFERENCES `zk_organization` (`organizationNo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6386 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_course_teacher_rs
-- ----------------------------
DROP TABLE IF EXISTS `zk_course_teacher_rs`;
CREATE TABLE `zk_course_teacher_rs` (
  `courseTeacherRSId` int(11) NOT NULL AUTO_INCREMENT,
  `cNo` varchar(10) NOT NULL,
  `tNo` varchar(10) NOT NULL,
  PRIMARY KEY (`courseTeacherRSId`),
  KEY `cNo` (`cNo`),
  KEY `tNo` (`tNo`),
  CONSTRAINT `zk_course_teacher_rs_ibfk_1` FOREIGN KEY (`cNo`) REFERENCES `zk_course` (`cNo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `zk_course_teacher_rs_ibfk_2` FOREIGN KEY (`tNo`) REFERENCES `zk_teacher` (`tNo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2102 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_electivetime
-- ----------------------------
DROP TABLE IF EXISTS `zk_electivetime`;
CREATE TABLE `zk_electivetime` (
  `electiveTimeId` int(11) NOT NULL AUTO_INCREMENT COMMENT '选课时间编号',
  `electiveTimeBegin` datetime NOT NULL COMMENT '选课开始时间',
  `electiveTimeEnd` datetime NOT NULL COMMENT '选课结束时间',
  `electiveTimeOrder` tinyint(4) NOT NULL COMMENT '选课批次',
  `electiveTimeDescript` varchar(250) NOT NULL COMMENT '选课时间描述',
  PRIMARY KEY (`electiveTimeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_electivetime_class_rs
-- ----------------------------
DROP TABLE IF EXISTS `zk_electivetime_class_rs`;
CREATE TABLE `zk_electivetime_class_rs` (
  `electiveTime_class_rs_id` int(11) NOT NULL,
  `electiveTimeId` int(11) NOT NULL,
  `classNo` varchar(15) NOT NULL,
  PRIMARY KEY (`electiveTime_class_rs_id`),
  KEY `electiveTimeId` (`electiveTimeId`),
  KEY `classNo` (`classNo`),
  CONSTRAINT `zk_electivetime_class_rs_ibfk_1` FOREIGN KEY (`electiveTimeId`) REFERENCES `zk_electivetime` (`electiveTimeId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `zk_electivetime_class_rs_ibfk_2` FOREIGN KEY (`classNo`) REFERENCES `zk_class` (`classNo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_major
-- ----------------------------
DROP TABLE IF EXISTS `zk_major`;
CREATE TABLE `zk_major` (
  `mId` int(11) NOT NULL AUTO_INCREMENT,
  `majorName` varchar(250) DEFAULT NULL,
  `majorNo` varchar(10) DEFAULT NULL,
  `academyNo` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`mId`),
  KEY `majorNo` (`majorNo`),
  KEY `academyNo` (`academyNo`),
  CONSTRAINT `zk_major_ibfk_1` FOREIGN KEY (`academyNo`) REFERENCES `zk_organization` (`organizationNo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_my_public_course
-- ----------------------------
DROP TABLE IF EXISTS `zk_my_public_course`;
CREATE TABLE `zk_my_public_course` (
  `mpcId` int(11) NOT NULL AUTO_INCREMENT,
  `uId` int(11) NOT NULL,
  `termNo` varchar(5) NOT NULL,
  `cNameNo` varchar(6) NOT NULL,
  `score` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`mpcId`),
  KEY `uId` (`uId`),
  KEY `termNo` (`termNo`),
  CONSTRAINT `zk_my_public_course_ibfk_1` FOREIGN KEY (`uId`) REFERENCES `zk_user` (`uId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `zk_my_public_course_ibfk_2` FOREIGN KEY (`termNo`) REFERENCES `zk_term` (`termNo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_my_schedule
-- ----------------------------
DROP TABLE IF EXISTS `zk_my_schedule`;
CREATE TABLE `zk_my_schedule` (
  `msdId` int(11) NOT NULL AUTO_INCREMENT,
  `uId` int(11) NOT NULL,
  `termNo` varchar(5) NOT NULL,
  `termCourseId` int(11) NOT NULL,
  `state` int(11) NOT NULL,
  PRIMARY KEY (`msdId`),
  KEY `uId` (`uId`),
  KEY `termCourseId` (`termCourseId`),
  KEY `termNo` (`termNo`),
  CONSTRAINT `zk_my_schedule_ibfk_1` FOREIGN KEY (`uId`) REFERENCES `zk_user` (`uId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `zk_my_schedule_ibfk_2` FOREIGN KEY (`termCourseId`) REFERENCES `zk_term_course` (`termCourseId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `zk_my_schedule_ibfk_3` FOREIGN KEY (`termNo`) REFERENCES `zk_term` (`termNo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_my_scheme
-- ----------------------------
DROP TABLE IF EXISTS `zk_my_scheme`;
CREATE TABLE `zk_my_scheme` (
  `msId` int(11) NOT NULL AUTO_INCREMENT,
  `cNo` varchar(10) NOT NULL,
  `uId` int(11) NOT NULL,
  `displayValue` varchar(250) NOT NULL,
  `formValue` varchar(250) NOT NULL,
  `termNo` varchar(5) NOT NULL,
  `orderNum` tinyint(4) DEFAULT '100',
  `schoolTime` varchar(100) NOT NULL,
  PRIMARY KEY (`msId`),
  UNIQUE KEY `cNo` (`cNo`,`uId`,`termNo`),
  KEY `uId` (`uId`),
  KEY `termNo` (`termNo`),
  CONSTRAINT `zk_my_scheme_ibfk_1` FOREIGN KEY (`cNo`) REFERENCES `zk_course` (`cNo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `zk_my_scheme_ibfk_2` FOREIGN KEY (`uId`) REFERENCES `zk_user` (`uId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `zk_my_scheme_ibfk_3` FOREIGN KEY (`termNo`) REFERENCES `zk_term` (`termNo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_notice
-- ----------------------------
DROP TABLE IF EXISTS `zk_notice`;
CREATE TABLE `zk_notice` (
  `noticeId` int(11) NOT NULL,
  `content` mediumtext NOT NULL,
  `termNo` varchar(5) NOT NULL,
  PRIMARY KEY (`noticeId`),
  KEY `zk_notice_termNo_fk` (`termNo`),
  CONSTRAINT `zk_notice_termNo_fk` FOREIGN KEY (`termNo`) REFERENCES `zk_term` (`termNo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_organization
-- ----------------------------
DROP TABLE IF EXISTS `zk_organization`;
CREATE TABLE `zk_organization` (
  `oId` int(11) NOT NULL AUTO_INCREMENT,
  `organizationName` varchar(250) NOT NULL,
  `organizationTypeId` tinyint(4) NOT NULL COMMENT '机构 类型 \r\n学院 为 1;\r\n学校 部门 为 2.',
  `organizationNo` varchar(25) NOT NULL,
  PRIMARY KEY (`oId`),
  KEY `organizationNo` (`organizationNo`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_public_course
-- ----------------------------
DROP TABLE IF EXISTS `zk_public_course`;
CREATE TABLE `zk_public_course` (
  `pcId` int(11) NOT NULL AUTO_INCREMENT,
  `termCourseId` int(11) NOT NULL,
  `campusId` int(11) NOT NULL,
  `availableNum` int(11) DEFAULT '0',
  PRIMARY KEY (`pcId`),
  UNIQUE KEY `xqcid` (`termCourseId`,`campusId`),
  KEY `campusId` (`campusId`),
  CONSTRAINT `zk_public_course_ibfk_1` FOREIGN KEY (`termCourseId`) REFERENCES `zk_term_course` (`termCourseId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `zk_public_course_ibfk_2` FOREIGN KEY (`campusId`) REFERENCES `zk_campus` (`campusId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=627 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_public_course_comments
-- ----------------------------
DROP TABLE IF EXISTS `zk_public_course_comments`;
CREATE TABLE `zk_public_course_comments` (
  `pccid` int(11) NOT NULL AUTO_INCREMENT,
  `parent_pccid` int(11) DEFAULT NULL,
  `comment_uid` int(11) NOT NULL,
  `cNo` varchar(10) NOT NULL,
  `content` text NOT NULL,
  `reply_uid` int(11) DEFAULT NULL,
  `comment_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `state` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`pccid`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_public_course_option
-- ----------------------------
DROP TABLE IF EXISTS `zk_public_course_option`;
CREATE TABLE `zk_public_course_option` (
  `pcoId` int(11) NOT NULL AUTO_INCREMENT,
  `termNo` varchar(5) NOT NULL,
  `campusId` int(11) NOT NULL,
  `cNo` varchar(10) NOT NULL,
  `optionHtml` text,
  PRIMARY KEY (`pcoId`),
  UNIQUE KEY `termNo` (`termNo`,`campusId`,`cNo`),
  KEY `campusId` (`campusId`),
  KEY `cNo` (`cNo`),
  CONSTRAINT `zk_public_course_option_ibfk_1` FOREIGN KEY (`termNo`) REFERENCES `zk_term` (`termNo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `zk_public_course_option_ibfk_2` FOREIGN KEY (`campusId`) REFERENCES `zk_campus` (`campusId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `zk_public_course_option_ibfk_3` FOREIGN KEY (`cNo`) REFERENCES `zk_course` (`cNo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=160 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_public_course_profiles
-- ----------------------------
DROP TABLE IF EXISTS `zk_public_course_profiles`;
CREATE TABLE `zk_public_course_profiles` (
  `pcpId` int(11) NOT NULL AUTO_INCREMENT,
  `cNo` varchar(10) NOT NULL,
  `pctId` tinyint(4) DEFAULT NULL,
  `recommend` varchar(2) DEFAULT NULL,
  `evaluation` text,
  `good_count` int(11) DEFAULT '0',
  `bad_count` int(11) DEFAULT '0',
  PRIMARY KEY (`pcpId`),
  UNIQUE KEY `cNo` (`cNo`)
) ENGINE=InnoDB AUTO_INCREMENT=384 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_public_course_type
-- ----------------------------
DROP TABLE IF EXISTS `zk_public_course_type`;
CREATE TABLE `zk_public_course_type` (
  `pctId` int(11) NOT NULL AUTO_INCREMENT,
  `publicCourseTypeName` varchar(25) NOT NULL,
  PRIMARY KEY (`pctId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_school_building
-- ----------------------------
DROP TABLE IF EXISTS `zk_school_building`;
CREATE TABLE `zk_school_building` (
  `sbId` int(11) NOT NULL AUTO_INCREMENT,
  `schoolBuildingName` varchar(250) NOT NULL,
  `campusId` int(11) NOT NULL DEFAULT '1',
  `schoolBuildingNo` varchar(10) NOT NULL,
  PRIMARY KEY (`sbId`),
  KEY `schoolBuildingNo` (`schoolBuildingNo`),
  KEY `campusId` (`campusId`),
  CONSTRAINT `zk_school_building_ibfk_1` FOREIGN KEY (`campusId`) REFERENCES `zk_campus` (`campusId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_student
-- ----------------------------
DROP TABLE IF EXISTS `zk_student`;
CREATE TABLE `zk_student` (
  `sId` int(11) NOT NULL AUTO_INCREMENT,
  `sNo` varchar(13) NOT NULL,
  `studentName` varchar(25) DEFAULT NULL,
  `studentSex` varchar(10) NOT NULL,
  `classNo` varchar(15) NOT NULL,
  PRIMARY KEY (`sId`),
  KEY `classNo` (`classNo`),
  KEY `sNo` (`sNo`),
  CONSTRAINT `zk_student_ibfk_1` FOREIGN KEY (`classNo`) REFERENCES `zk_class` (`classNo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1115 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_sys_account
-- ----------------------------
DROP TABLE IF EXISTS `zk_sys_account`;
CREATE TABLE `zk_sys_account` (
  `accountId` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(250) NOT NULL,
  `password` varchar(250) NOT NULL,
  `campusId` int(11) NOT NULL,
  PRIMARY KEY (`accountId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_teacher
-- ----------------------------
DROP TABLE IF EXISTS `zk_teacher`;
CREATE TABLE `zk_teacher` (
  `tId` int(11) NOT NULL AUTO_INCREMENT,
  `tNo` varchar(15) NOT NULL,
  `teacherName` varchar(25) NOT NULL,
  `teacherNameNo` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`tId`),
  KEY `tNo` (`tNo`)
) ENGINE=InnoDB AUTO_INCREMENT=12678 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_temp_course_type
-- ----------------------------
DROP TABLE IF EXISTS `zk_temp_course_type`;
CREATE TABLE `zk_temp_course_type` (
  `tctId` int(11) NOT NULL AUTO_INCREMENT,
  `cName` varchar(500) NOT NULL,
  `pctId` int(11) NOT NULL,
  PRIMARY KEY (`tctId`),
  KEY `pctId` (`pctId`),
  CONSTRAINT `zk_temp_course_type_ibfk_1` FOREIGN KEY (`pctId`) REFERENCES `zk_public_course_type` (`pctId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_term
-- ----------------------------
DROP TABLE IF EXISTS `zk_term`;
CREATE TABLE `zk_term` (
  `termId` int(11) NOT NULL AUTO_INCREMENT,
  `termName` varchar(250) NOT NULL,
  `termNo` varchar(10) NOT NULL,
  `beginDate` date NOT NULL,
  `endDate` date NOT NULL,
  `status` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`termId`),
  KEY `termNo` (`termNo`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_term_course
-- ----------------------------
DROP TABLE IF EXISTS `zk_term_course`;
CREATE TABLE `zk_term_course` (
  `termCourseId` int(11) NOT NULL AUTO_INCREMENT,
  `termNo` varchar(10) NOT NULL,
  `courseTeacherRSId` int(11) NOT NULL,
  `courseClassNo` varchar(4) NOT NULL,
  `studentNum` int(11) NOT NULL,
  `periods` varchar(50) NOT NULL COMMENT '用,号分开',
  `classWeek` tinyint(4) NOT NULL COMMENT '1-7 分别表示星期一到星期日',
  `classSection` varchar(25) NOT NULL COMMENT '用,号分开',
  `classroomNo` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`termCourseId`),
  KEY `termNo` (`termNo`),
  KEY `courseTeacherRSId` (`courseTeacherRSId`),
  KEY `classroomNo` (`classroomNo`),
  CONSTRAINT `zk_term_course_ibfk_1` FOREIGN KEY (`termNo`) REFERENCES `zk_term` (`termNo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `zk_term_course_ibfk_2` FOREIGN KEY (`courseTeacherRSId`) REFERENCES `zk_course_teacher_rs` (`courseTeacherRSId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `zk_term_course_ibfk_3` FOREIGN KEY (`classroomNo`) REFERENCES `zk_classroom` (`classroomNo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11898 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_up_down_record
-- ----------------------------
DROP TABLE IF EXISTS `zk_up_down_record`;
CREATE TABLE `zk_up_down_record` (
  `udId` int(11) NOT NULL AUTO_INCREMENT,
  `cNo` varchar(10) NOT NULL,
  `uId` int(11) NOT NULL,
  `remarkItem` tinyint(4) NOT NULL,
  PRIMARY KEY (`udId`),
  KEY `cNo` (`cNo`),
  KEY `uId` (`uId`),
  CONSTRAINT `zk_up_down_record_ibfk_1` FOREIGN KEY (`cNo`) REFERENCES `zk_course` (`cNo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `zk_up_down_record_ibfk_2` FOREIGN KEY (`uId`) REFERENCES `zk_user` (`uId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_user
-- ----------------------------
DROP TABLE IF EXISTS `zk_user`;
CREATE TABLE `zk_user` (
  `uId` int(11) NOT NULL AUTO_INCREMENT,
  `sNo` varchar(13) DEFAULT NULL,
  `nickName` varchar(25) NOT NULL,
  `email` varchar(250) DEFAULT NULL,
  `state` tinyint(4) DEFAULT '1',
  `avatorUrl` varchar(500) DEFAULT NULL,
  `isDIYAvater` tinyint(1) DEFAULT '0',
  `description` text,
  `sinaUid` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`uId`),
  KEY `sNo` (`sNo`),
  CONSTRAINT `zk_user_ibfk_1` FOREIGN KEY (`sNo`) REFERENCES `zk_student` (`sNo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1529 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_web_config
-- ----------------------------
DROP TABLE IF EXISTS `zk_web_config`;
CREATE TABLE `zk_web_config` (
  `wConfigId` tinyint(4) NOT NULL AUTO_INCREMENT,
  `siteName` varchar(250) NOT NULL,
  `domain` varchar(100) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `keyword` varchar(200) DEFAULT NULL,
  `webfooterInfo` varchar(200) DEFAULT NULL,
  `termNo` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`wConfigId`),
  KEY `termNo` (`termNo`),
  CONSTRAINT `zk_web_config_ibfk_1` FOREIGN KEY (`termNo`) REFERENCES `zk_term` (`termNo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_zx_courselist_page
-- ----------------------------
DROP TABLE IF EXISTS `zk_zx_courselist_page`;
CREATE TABLE `zk_zx_courselist_page` (
  `clpId` int(11) NOT NULL AUTO_INCREMENT,
  `majorNo` varchar(8) NOT NULL,
  `pageContent` mediumtext NOT NULL,
  PRIMARY KEY (`clpId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
