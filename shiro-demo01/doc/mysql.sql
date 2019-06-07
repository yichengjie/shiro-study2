CREATE TABLE  USER(
	id              int (11) not null auto_increment,
    name            varchar(20) ,
	password        varchar(250),
	PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS role(
	id              int(11)    not null auto_increment,
    role_name       varchar(20) ,
    user_id         int(11) ,
    primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8; ;

CREATE TABLE IF NOT EXISTS permission(
	id  			int(11)    not null auto_increment ,
    permission      varchar(20) ,
    role_id         int(11)  ,
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
# 初始化数据

insert into user(name,password) values('test','123456') ;
insert into role(role_name,user_id) values('admin',1) ;
insert into permission(permission,role_id) values('create',1) ;
insert into permission(permission,role_id) values('update',1) ;