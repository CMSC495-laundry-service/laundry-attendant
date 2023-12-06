drop table if exists ticket;
drop table if exists user;
drop type if exists type;
drop type if exists status;
drop type if exists gender;
create type type AS ENUM('RAYON',
      'SILK',
      'LEATHER',
      'SUEDE',
      'VELVET',
      'OTHERS');
create type status AS ENUM ('PENDING', 'PROCESSING', 'COMPLETED','ACCEPTED');
create type gender AS ENUM ('MALE','FEMALE','UNKNOWN');

create table
  ticket (
    orderid integer not null,
    price numeric not null,
    datareceived date not null,
    name character varying(50) not null,
    phonenum character varying(10) not null,
    type public.type null,
    dataextimated date not null,
    status public.status null default 'PENDING'::status,
    username character varying(15) not null,
    comment character varying(100),
    constraint ticket_pkey primary key (orderid)
  ) tablespace pg_default;

create table
  user (
    id integer not null,
    username character varying(15) not null,
    password character varying(25) not null,
    isadmin boolean not null,
    firstname character varying(25) not null,
    lastname character varying(25) not null,
    email character varying(45) not null,
    dob date not null,
    gender public.gender not null,
    phonenum character varying(10) not null,
    address character varying(50) not null,
    securityquestion character varying(50) not null,
    securityanswer character varying(50) not null,
    cvv character varying(3) not null,
    constraint users_pkey primary key (id)
  ) tablespace pg_default;