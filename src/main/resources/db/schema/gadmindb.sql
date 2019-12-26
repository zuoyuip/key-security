create table admin
(
    uid        varchar(36)                              not null comment '唯一ID',
    adminid    varchar(60)                              not null comment '登录ID',
    pwd        varchar(32)                              not null comment '密码',
    dopwd      varchar(32)                              not null comment '操作密码',
    name       varchar(255)                             null comment '名称',
    email      varchar(60)                              null comment '邮箱',
    mobile     varchar(60)                              null comment '手机',
    status     tinyint(1) default 1                     null comment '状态(0禁用1启用)',
    createtime timestamp  default '0000-00-00 00:00:00' not null comment '创建时间',
    updatetime timestamp  default CURRENT_TIMESTAMP     not null on update CURRENT_TIMESTAMP comment '最后更新时间',
    constraint adminid
        unique (adminid),
    constraint uid
        unique (uid)
);

alter table admin
    add primary key (uid);

create table adminauthority
(
    uid        varchar(36)                              not null comment '唯一ID',
    roleuid    varchar(36)                              not null comment '权限ID',
    roleid     varchar(255)                             not null comment '权限',
    url        varchar(255)                             not null comment 'URL,正则',
    name       varchar(255)                             null comment 'URL名称',
    `desc`     varchar(255)                             null comment '描述',
    status     tinyint(1) default 1                     null comment '状态(0禁用1启用)',
    createtime timestamp  default '0000-00-00 00:00:00' not null comment '创建时间',
    updatetime timestamp  default CURRENT_TIMESTAMP     not null on update CURRENT_TIMESTAMP comment '最后更新时间',
    constraint uid
        unique (uid)
);

alter table adminauthority
    add primary key (uid);

create table adminlog
(
    uid        char(32)                                not null comment '唯一UID',
    adminuid   char(32)                                not null comment '管理员UID',
    dataid     char(32)                                not null comment '操作对象UID',
    channel    int(11) unsigned                        not null comment '操作频道',
    operation  varchar(255)                            null comment '操作名称',
    data       varchar(1000)                           null comment '操作参数',
    `desc`     varchar(1000)                           not null comment '操作描述',
    createtime timestamp default '0000-00-00 00:00:00' not null comment '创建时间',
    constraint uid
        unique (uid)
);

alter table adminlog
    add primary key (uid);

create table adminrole
(
    uid        varchar(36)                              not null comment '唯一ID',
    adminuid   varchar(36)                              null comment '管理员UID',
    adminid    varchar(36)                              null comment '管理员ID',
    roleid     varchar(255)                             not null comment '权限',
    rolename   varchar(255)                             null comment '权限名',
    `desc`     varchar(255)                             null comment '描述',
    status     tinyint(1) default 1                     null comment '状态(0禁用1启用)',
    createtime timestamp  default '0000-00-00 00:00:00' not null comment '创建时间',
    updatetime timestamp  default CURRENT_TIMESTAMP     not null on update CURRENT_TIMESTAMP comment '最后更新时间',
    constraint uid
        unique (uid)
);

alter table adminrole
    add primary key (uid);

create table blackwhite
(
    uid        varchar(36)                             not null comment '唯一UID',
    types      tinyint(1)                              not null comment '名单类型',
    sourcename varchar(255)                            not null comment '来源名称',
    author     varchar(255)                            not null comment '作者',
    operator   varchar(255)                            not null comment '操作人',
    remarks    varchar(1000)                           not null comment '备注',
    createtime timestamp default '0000-00-00 00:00:00' not null comment '创建时间',
    constraint uid
        unique (uid)
);

alter table blackwhite
    add primary key (uid);

create table emaillog
(
    uid        varchar(36)                             not null comment '编号',
    adminid    varchar(36)                             not null comment '管理员ID',
    title      varchar(255)                            null comment '标题',
    content    mediumtext                              null comment '内容',
    receiver   varchar(1000)                           not null comment '接收人（json存储）',
    sender     varchar(1000)                           not null comment '发送人',
    createtime timestamp default '0000-00-00 00:00:00' not null comment '创建时间',
    updatetime timestamp default CURRENT_TIMESTAMP     not null on update CURRENT_TIMESTAMP comment '最后更新时间',
    constraint uid
        unique (uid)
);

alter table emaillog
    add primary key (uid);

create table workflow
(
    uid          varchar(36)                                       not null comment '唯一ID',
    workorderuid varchar(255)                                      null comment '工单UID',
    adminuid     varchar(36)                                       null comment '操作管理员uid',
    adminname    varchar(36)                                       null comment '操作管理员姓名',
    title        varchar(36)         default ''                    null comment '标题',
    `desc`       varchar(1000)                                     null comment '操作原因',
    remark       varchar(1000)                                     null comment '评论批复',
    datafile     varchar(255)                                      null comment '数据文件，备用',
    stageform    tinyint(1)          default 0                     null comment '更改前阶段',
    stageto      tinyint(1)          default 0                     null comment '更改后阶段',
    statusfrom   tinyint(1)          default 0                     not null comment '原状态',
    statusto     tinyint(1)          default 0                     not null comment '现状态',
    type         tinyint(1) unsigned default 0                     null comment '工单类型',
    status       tinyint(1)          default 0                     not null comment '状态',
    createtime   timestamp           default '0000-00-00 00:00:00' not null comment '创建时间',
    updatetime   timestamp           default CURRENT_TIMESTAMP     not null on update CURRENT_TIMESTAMP comment '最后更新时间',
    constraint uid
        unique (uid)
);

alter table workflow
    add primary key (uid);

create table workorder
(
    uid        varchar(36)                                       not null comment '唯一ID',
    sn         varchar(255)                                      null comment '工单号',
    adminuid   varchar(36)                                       null comment '操作管理员uid',
    adminname  varchar(36)                                       null comment '操作管理员姓名',
    title      varchar(36)         default ''                    null comment '标题',
    fromuid    varchar(36)                                       null comment '来自哪个对象的uid',
    name       varchar(36)         default ''                    null comment '对象名称',
    `desc`     varchar(1000)                                     null comment '操作原因',
    remark     varchar(1000)                                     null comment '评论批复',
    datafile   varchar(255)                                      null comment '数据文件',
    type       tinyint(1) unsigned default 0                     null comment '工单类型',
    channel    tinyint(1) unsigned default 0                     null comment '频道，备用',
    stageform  tinyint(1)          default 0                     null comment '更改前阶段',
    stageto    tinyint(1)          default 0                     null comment '更改后阶段',
    status     tinyint(1)          default 0                     not null comment '状态',
    createtime timestamp           default '0000-00-00 00:00:00' not null comment '创建时间',
    updatetime timestamp           default CURRENT_TIMESTAMP     not null on update CURRENT_TIMESTAMP comment '最后更新时间',
    constraint uid
        unique (uid)
);

alter table workorder
    add primary key (uid);


