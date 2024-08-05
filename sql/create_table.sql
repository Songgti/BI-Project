
-- 创建库
create database if not exists songgt_bi;

-- 切换库
use songgt_bi;

-- 用户表
create table user
(
    id           bigint auto_increment comment 'id'
        primary key,
    userAccount  varchar(256)                                                                                                 not null comment '账号',
    userPassword varchar(512)                                                                                                 not null comment '密码',
    userName     varchar(256)                                                                                                 null comment '用户昵称',
    userAvatar   varchar(1024) default 'https://songgti.oss-cn-chengdu.aliyuncs.com/99f96455-9bb9-4391-ae1a-cadfea63bc24.jpg' not null comment '用户头像',
    userRole     varchar(256)  default 'user'                                                                                 not null comment '用户角色：user/admin/ban',
    createTime   datetime      default CURRENT_TIMESTAMP                                                                      not null comment '创建时间',
    updateTime   datetime      default CURRENT_TIMESTAMP                                                                      not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint       default 0                                                                                      not null comment '是否删除'
)
    comment '用户' collate = utf8mb4_unicode_ci;

create index idx_userAccount
    on user (userAccount);

-- 图表表
create table chart
(
    id          bigint auto_increment comment 'id'
        primary key,
    goal        text                                   null comment '分析目标信息',
    chartData   text                                   null comment '图表信息',
    chartType   varchar(128)                           null comment '图表类型',
    genchart    text                                   null comment '生成的图表数据',
    genResult   text                                   null comment '生成的分析结论',
    userid      bigint                                 null comment '创建用户id',
    createTime  datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete    tinyint      default 0                 not null comment '是否删除',
    name        varchar(128)                           null comment '图标名称',
    execMessage text                                   null comment '执行信息',
    status      varchar(256) default 'wait'            not null comment '图表生成状态'
)
    comment '图表数据表' collate = utf8mb4_unicode_ci;

