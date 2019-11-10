/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50537
Source Host           : localhost:3306
Source Database       : bysjgl

Target Server Type    : MYSQL
Target Server Version : 50537
File Encoding         : 65001

Date: 2017-05-26 17:02:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tongzhi
-- ----------------------------
DROP TABLE IF EXISTS `tongzhi`;
CREATE TABLE `tongzhi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `contents` varchar(2000) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `uname` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tongzhi
-- ----------------------------
INSERT INTO `tongzhi` VALUES ('1', '本周答疑时间', '3.14日晚在3.4教室', '6', '谭浩强');
INSERT INTO `tongzhi` VALUES ('3', '中期检查', '2017.3.17下午14：30在系楼三层报告厅', '6', '谭浩强');
INSERT INTO `tongzhi` VALUES ('4', '中期检查', '3月27号早上九点', '6', '谭浩强');

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `userId` int(11) NOT NULL,
  `userName` varchar(66) DEFAULT NULL,
  `userPw` varchar(55) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES ('1', 'test', 'test');
INSERT INTO `t_admin` VALUES ('2', 'tom', 'tom');
INSERT INTO `t_admin` VALUES ('3', 'admin', 'admin');
INSERT INTO `t_admin` VALUES ('4', '赵艳茹', '1');

-- ----------------------------
-- Table structure for t_doc
-- ----------------------------
DROP TABLE IF EXISTS `t_doc`;
CREATE TABLE `t_doc` (
  `id` int(11) NOT NULL,
  `sdate` varchar(50) DEFAULT NULL,
  `edate` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_doc
-- ----------------------------
INSERT INTO `t_doc` VALUES ('1', '2017-05-01', '2017-05-24');

-- ----------------------------
-- Table structure for t_gonggao
-- ----------------------------
DROP TABLE IF EXISTS `t_gonggao`;
CREATE TABLE `t_gonggao` (
  `ggId` int(11) NOT NULL DEFAULT '1' COMMENT '编号',
  `ggTitle` varchar(50) NOT NULL DEFAULT '' COMMENT '标题',
  `ggContent` varchar(255) NOT NULL DEFAULT '' COMMENT '内容',
  `ggTime` varchar(50) NOT NULL DEFAULT '' COMMENT '创建时间',
  `ggDel` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ggId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_gonggao
-- ----------------------------
INSERT INTO `t_gonggao` VALUES ('1', '中期检查', '2017/93', '', 'yes');
INSERT INTO `t_gonggao` VALUES ('2', '3333', '333', '2017-05-02 17:23:41', 'yes');
INSERT INTO `t_gonggao` VALUES ('3', 'dewwe', 'eferferf', '2017-05-02 17:35:50', 'yes');
INSERT INTO `t_gonggao` VALUES ('4', '2017级本科毕业生中期检查', '  计算机与信息技术学院的本科中期检查时间是2017年3.14。\r\n在外实训的同学，在各基地进行检查，具体分组见通知。', '2017-05-12 11:06:39', 'no');
INSERT INTO `t_gonggao` VALUES ('5', '教务处：关于本科生毕业设计（论文）选题录入本科教务管理系统的通知 ', '各院系：\r\n\r\n按照学校2016-2017-2学期本科教学计划安排，本科生毕业设计（论文）选题录入本科教务管理系统工作已经启动，现将相关注意事项通知如下：\r\n\r\n1、“题目类型”限定为理论研究、调研报告、工程设计、其他。\r\n\r\n2、“题目来源”限定为科研项目、社会（生产）实际、自拟。\r\n\r\n3、题目“英文名称”与“内容与要求”两项要求全部录入。\r\n教务处', '2017-05-12 11:07:15', 'no');

-- ----------------------------
-- Table structure for t_keti
-- ----------------------------
DROP TABLE IF EXISTS `t_keti`;
CREATE TABLE `t_keti` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(300) DEFAULT NULL,
  `tea_id` int(11) DEFAULT NULL,
  `teaname` varchar(30) DEFAULT NULL,
  `ktnd` varchar(50) DEFAULT NULL,
  `ktfx` varchar(100) DEFAULT NULL,
  `xxrs` int(11) DEFAULT '0',
  `ktyq` varchar(500) DEFAULT NULL,
  `yxrs` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_keti
-- ----------------------------
INSERT INTO `t_keti` VALUES ('1', '毕业设计管理系统', '6', '谭浩强', '中等', '设计作品', '10000', '基于JSP毕业设计管理系统的设计与实现cs', '0');
INSERT INTO `t_keti` VALUES ('3', '基于jsp的网上零食销售系统', '6', '谭浩强', '中等', '工程实践', '10000', '富有创新,采用SSH框架', '0');
INSERT INTO `t_keti` VALUES ('4', '门诊管理系统', '6', '谭浩强', '一般', '工程实践', '10000', '基于jsp技术', '0');
INSERT INTO `t_keti` VALUES ('5', '基于jsp的博客管理系统', '6', '谭浩强', '中等', '专业实践', '10000', '采用SSM框架进行开发', '0');
INSERT INTO `t_keti` VALUES ('6', '粒子分离算法的设计与实现', '6', '谭浩强', '较难', '理论研究', '10000', '在基于理论知识的基础上进行代码编写', '0');

-- ----------------------------
-- Table structure for t_shiti
-- ----------------------------
DROP TABLE IF EXISTS `t_shiti`;
CREATE TABLE `t_shiti` (
  `id` int(11) NOT NULL,
  `mingcheng` varchar(66) DEFAULT NULL,
  `fujian` varchar(55) DEFAULT NULL,
  `fujianYuanshiming` varchar(50) DEFAULT NULL,
  `shijian` varchar(50) DEFAULT NULL,
  `del` varchar(50) DEFAULT NULL,
  `teaid` int(11) DEFAULT '0',
  `replay` varchar(500) DEFAULT NULL,
  `replaydate` varchar(50) DEFAULT NULL,
  `state` varchar(20) DEFAULT NULL,
  `stuid` int(11) DEFAULT '0',
  `stuname` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_shiti
-- ----------------------------
INSERT INTO `t_shiti` VALUES ('1', '开题报告', '/upload/1489464072963.docx', '徐石耳_开题报告.docx', '2017-03-14', 'no', '6', null, null, '未批复', '19', '徐石耳');
INSERT INTO `t_shiti` VALUES ('2', '开题报告', '/upload/1489587634343.txt', '新建文本文档.txt', '2017-03-15', 'yes', '6', null, null, '未批复', '8', '王鹏举');
INSERT INTO `t_shiti` VALUES ('5', '开题报告', '/upload/1490539777936.doc', '2013242190赵艳茹开题报告.doc', '2017-03-26', 'yes', '6', null, null, '未批复', '8', '王鹏举');
INSERT INTO `t_shiti` VALUES ('6', '中期汇报', '/upload/1490540054022.docx', '系统模块图.docx', '2017-03-26', 'yes', '6', null, null, '未批复', '8', '王鹏举');
INSERT INTO `t_shiti` VALUES ('8', '开题报告', '/upload/1490541025976.docx', '王鹏举_开题报告.docx', '2017-03-26', 'no', '6', null, null, '未批复', '8', '王鹏举');
INSERT INTO `t_shiti` VALUES ('9', '论文', '/upload/1493886953179.docx', 'java interview.docx', '2017-05-04', 'yes', '6', null, null, '未批复', '8', '王鹏举');
INSERT INTO `t_shiti` VALUES ('11', '开题报告', '/upload/1493951485845.docx', 'webServer.docx', '2017-05-05', 'yes', '6', null, null, '未批复', '8', '王鹏举');
INSERT INTO `t_shiti` VALUES ('12', '中期汇报', '/upload/1493967116352.pdf', 'Java程序员面试宝典.pdf', '2017-05-05', 'yes', '6', null, null, '未批复', '8', '王鹏举');
INSERT INTO `t_shiti` VALUES ('14', '论文', '/upload/1493968290578.pdf', 'MyEclipse_UML建模.pdf', '2017-05-05', 'yes', '6', null, null, '未批复', '8', '王鹏举');
INSERT INTO `t_shiti` VALUES ('25', '开题报告', '/upload/1494379198750.docx', '代码bug说明书.docx', '2017-05-10', 'yes', '6', null, null, '未批复', '8', '王鹏举');
INSERT INTO `t_shiti` VALUES ('26', '论文', '/upload/1494564440434.doc', '2017190_赵磨叽论文.doc', '2017-05-12', 'no', '6', null, null, '未批复', '10', '赵磨叽');
INSERT INTO `t_shiti` VALUES ('27', '开题报告', '/upload/1494564775505.doc', '赵磨叽_开题报告.doc', '2017-05-12', 'no', '6', null, null, '未批复', '10', '赵磨叽');
INSERT INTO `t_shiti` VALUES ('28', '中期汇报', '/upload/1494564849139.doc', '赵磨叽_中期汇报.doc', '2017-05-12', 'no', '6', null, null, '未批复', '10', '赵磨叽');
INSERT INTO `t_shiti` VALUES ('29', '中期汇报', '/upload/1494564912829.doc', '王鹏举_中期汇报.doc', '2017-05-12', 'no', '6', null, null, '未批复', '8', '王鹏举');
INSERT INTO `t_shiti` VALUES ('30', '论文', '/upload/1494564928041.doc', '王鹏举_论文.doc', '2017-05-12', 'no', '6', null, null, '未批复', '8', '王鹏举');
INSERT INTO `t_shiti` VALUES ('31', '中期汇报', '/upload/1494564965470.doc', '徐石耳_中期汇报.doc', '2017-05-12', 'no', null, null, null, '未批复', '19', '徐石耳');
INSERT INTO `t_shiti` VALUES ('32', '论文', '/upload/1494564975003.doc', '徐石耳_论文.doc', '2017-05-12', 'no', null, null, null, '未批复', '19', '徐石耳');

-- ----------------------------
-- Table structure for t_stu
-- ----------------------------
DROP TABLE IF EXISTS `t_stu`;
CREATE TABLE `t_stu` (
  `stu_id` int(11) NOT NULL,
  `stu_xuehao` varchar(66) DEFAULT NULL,
  `stu_realname` varchar(50) DEFAULT NULL,
  `stu_sex` varchar(50) DEFAULT NULL,
  `stu_age` varchar(55) DEFAULT NULL,
  `login_pw` varchar(50) DEFAULT NULL,
  `zhuangtai` varchar(50) DEFAULT NULL,
  `del` varchar(50) DEFAULT NULL,
  `teaid` int(11) DEFAULT '0',
  `zhuanye` varchar(100) DEFAULT NULL,
  `nianji` varchar(50) DEFAULT NULL,
  `telphone` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`stu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_stu
-- ----------------------------
INSERT INTO `t_stu` VALUES ('5', '2017001', '张三', '男', '19', '123456', 'a', 'no', '2', '计算机', '大三', '15565656665');
INSERT INTO `t_stu` VALUES ('6', '2017002', '张明明', '男', '19', '000000', 'a', 'yes', '2', '软件工程', '2013级', '1234567890');
INSERT INTO `t_stu` VALUES ('7', '2017003', '张明发', '男', '19', '000000', 'a', 'no', '2', '计算机', '大二', '15525252252');
INSERT INTO `t_stu` VALUES ('8', '2017004', '王鹏举', '男', '20', '000', 'a', 'no', '0', '计算机', '大四', '1558989777');
INSERT INTO `t_stu` VALUES ('10', '2017190', '赵磨叽', '女', '19', '123', 'a', 'no', '0', '软件工程', '2013级', '18861732');
INSERT INTO `t_stu` VALUES ('11', '2017005', '李四', '男', '20', '123', 'a', 'no', '0', '软件工程', '2013级', '1234567789');
INSERT INTO `t_stu` VALUES ('12', '2017006', '王五', '女', '20', '123', 'a', 'no', '0', '软件工程', '2013级', '1234567890');
INSERT INTO `t_stu` VALUES ('13', '2017007', '赵六', '女', '19', '123', 'a', 'no', '0', '软件工程', '2013级', '1234567890');
INSERT INTO `t_stu` VALUES ('14', '2017008', '唐琪', '男', '19', '123', 'a', 'no', '0', '软件工程', '2013级', '1234567890');
INSERT INTO `t_stu` VALUES ('15', '2017009', '宋玖', '女', '18', '123', 'a', 'no', '0', '软件工程', '2013级', '1234567890');
INSERT INTO `t_stu` VALUES ('16', '2017010', '郭巴', '女', '19', '123', 'a', 'no', '0', '软件工程', '2013级', '1234567890');
INSERT INTO `t_stu` VALUES ('17', '2017011', '伍拾', '男', '20', '123', 'a', 'no', '0', '软件工程', '2013级', '1234567890');
INSERT INTO `t_stu` VALUES ('18', '2017012', '陈十一', '男', '21', '123', 'a', 'no', '0', '软件工程', '2013级', '1234567890');
INSERT INTO `t_stu` VALUES ('19', '2017013', '徐石耳', '女', '20', '123', 'a', 'no', '0', '软件工程', '2013级', '1234567890');

-- ----------------------------
-- Table structure for t_tea
-- ----------------------------
DROP TABLE IF EXISTS `t_tea`;
CREATE TABLE `t_tea` (
  `tea_id` int(11) NOT NULL,
  `tea_bianhao` varchar(66) DEFAULT NULL,
  `tea_realname` varchar(55) DEFAULT NULL,
  `tea_sex` varchar(50) DEFAULT NULL,
  `tea_age` varchar(50) DEFAULT NULL,
  `login_name` varchar(50) DEFAULT NULL,
  `login_pw` varchar(50) DEFAULT NULL,
  `del` varchar(50) DEFAULT NULL,
  `jszc` varchar(100) DEFAULT NULL,
  `telphone` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`tea_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_tea
-- ----------------------------
INSERT INTO `t_tea` VALUES ('1', '1', '刘老师', '男', '28', '0001', '000000', 'yes', null, null);
INSERT INTO `t_tea` VALUES ('2', '001', '张老师', '男', '30', '001', '123456', 'no', null, null);
INSERT INTO `t_tea` VALUES ('3', '002', '王老师', '女', '23', '002', '1', 'yes', '教授', '');
INSERT INTO `t_tea` VALUES ('4', '002', '李老师', '男', '24', '002', '123', 'yes', null, null);
INSERT INTO `t_tea` VALUES ('5', '009', '张明小', '男', '52', '009', '000000', 'yes', null, null);
INSERT INTO `t_tea` VALUES ('6', '8', '谭浩强', '男', '58', 'test', 'test', 'no', '硕士', '111');
INSERT INTO `t_tea` VALUES ('7', '001', '梁吉业', '男', '55', '001', '001', 'no', '博士生导师', '0351-7018176');
INSERT INTO `t_tea` VALUES ('8', '002', '李德玉', '男', '52', '002', '002', 'no', '博士生导师', '0351-7018775');
INSERT INTO `t_tea` VALUES ('9', '003', '王文剑 ', '女', '49', '003', '003', 'no', '博士生导师', '0351-7017566');
INSERT INTO `t_tea` VALUES ('10', '004', '李茹', '女', '54', '004', '004', 'no', '博士生导师', '0351-7017566');
INSERT INTO `t_tea` VALUES ('11', '005', '钱宇华', '男', '39', '005', '005', 'no', '博士生导师', '00351-7010566');
INSERT INTO `t_tea` VALUES ('12', '006', '王素格', '男', '53', '006', '006', 'no', '博士生导师', '0351-7010566');
INSERT INTO `t_tea` VALUES ('13', '007', '李继宏', '男', '52', '007', '007', 'no', '博士生导师', '0351-7010566');
INSERT INTO `t_tea` VALUES ('14', '008', '王世英', '男', '41', '008', '008', 'no', '博士生导师', '0351-7010566');
INSERT INTO `t_tea` VALUES ('15', '009', '王晓凯', '男', '47', '009', '009', 'no', '博士生导师', '0351-7010566');
INSERT INTO `t_tea` VALUES ('16', '010', '王春年', '男', '39', '010', '010', 'no', '讲师', '0351-7010566');
INSERT INTO `t_tea` VALUES ('17', '011', '庞继芳', '女', '37', '011', '011', 'no', '讲师', '0351-7010566');
INSERT INTO `t_tea` VALUES ('18', '012', '马千里', '女', '40', '012', '012', 'no', '副教授', '0351-7010566');
INSERT INTO `t_tea` VALUES ('19', '013', '孙敏', '女', '41', '013', '013', 'no', '副教授', '0351-7010566');
INSERT INTO `t_tea` VALUES ('20', '014', '弓剑锋', '男', '40', '014', '014', 'no', '讲师', '0351-7010566');

-- ----------------------------
-- Table structure for xuanti
-- ----------------------------
DROP TABLE IF EXISTS `xuanti`;
CREATE TABLE `xuanti` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) DEFAULT NULL,
  `sname` varchar(50) DEFAULT NULL,
  `ktid` int(11) DEFAULT NULL,
  `ktname` varchar(150) DEFAULT NULL,
  `tid` int(11) DEFAULT NULL,
  `tname` varchar(50) DEFAULT NULL,
  `shzt` varchar(10) DEFAULT NULL,
  `ktbg` varchar(10) DEFAULT NULL,
  `lunwen` varchar(10) DEFAULT NULL,
  `fenshu` varchar(10) DEFAULT NULL,
  `jieguo` varchar(10) DEFAULT NULL,
  `zqhb` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xuanti
-- ----------------------------
INSERT INTO `xuanti` VALUES ('3', '10', '赵磨叽', '2', '粒子算法的设计与实现', '6', '谭浩强', '已确认', '是', '是', '60', '及格', '是');
INSERT INTO `xuanti` VALUES ('7', '8', '王鹏举', '3', '基于jsp的网上零食销售系统', '6', '谭浩强', '已确认', null, '是', '90', '优秀', '是');
