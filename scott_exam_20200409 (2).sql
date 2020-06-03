create table boarde(

    num number(30) not null primary key,
    writer varchar(10) not null,
    email varchar(30),
    subject varchar(50),
    password varchar(12) not null,
    reg_date timestamp not null,
    readcount number default 0,
    ref number(20) not null,
    re_step number(10) not null,
    re_level number(10) not null,
    content varchar(3000) not null,
    ip varchar(20) not null
    );

create table LOGIN (
ID varchar2(15) primary key,
PASSWORD varchar2(12),
DATE_NUMBER varchar2(15),
EMAIL varchar2(30),
ADDRESS varchar2(25),
TEL varchar2(13),
NAME varchar2(15) ,
REG_DATE TIMESTAMP 
);

desc login;
desc boarde;

ALTER TABLE BOARDE
ADD CONSTRAINT fk_writer 
foreign key (writer) 
references login(id)
on delete cascade;

ALTER TABLE BOARDE DROP CONSTRAINT fk_writer;

DROP table BOARDE;


CREATE OR REPLACE PROCEDURE PRO_INSERT_ID

    is
        v_password login.password%type:='aaa';
        v_name login.name%type;
        v_cnt number := 0;
    begin
    
    for e in (select writer from boarde group by writer)
    LOOP
    select count(*) into v_cnt from login where id=e.writer;
    if v_cnt=0 then
    DBMS_OUTPUT.PUT_LINE ('writer:'|| e.writer);
    insert into login (id, password, name, reg_date) values (e.writer, v_password, e.writer, current_timestamp);
    end if;
    
    END LOOP;
    
    
    COMMIT;
    
    end;
    /
    
    execute pro_insert_id;