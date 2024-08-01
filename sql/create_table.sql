# 数据库初始化
# @author <a href="https://github.com/liyupi">程序员鱼皮</a>
# @from <a href="https://yupi.icu">编程导航知识星球</a>

-- 创建库
create database if not exists my_db;

-- 切换库
use my_db;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    index idx_userAccount (userAccount)
    ) comment '用户' collate = utf8mb4_unicode_ci;

-- 帖子表
create table if not exists chart
(
    id           bigint auto_increment comment 'id' primary key,
    `name`          varchar(128) null comment '图标名称',
    goal				 text null comment '分析目标信息',
    chartData    text null comment '图表信息',
    chartType    varchar(128) null comment '图表类型',
    genchart 		 text null comment '生成的图表数据',
    genResult 	 text null comment '生成的分析结论',
    status      ENUM('wait','running','succeed','failed') not null default 'wait' comment '图表生成状态',
    execMessage  text  null comment '执行信息',
    userid           bigint null comment '创建用户id',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除'
    ) comment '图表数据表' collate = utf8mb4_unicode_ci;

