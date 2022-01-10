drop table if exists user_answer CASCADE;

create table user_answer (id bigint generated by default as identity, created_at timestamp, updated_at timestamp, answer_id bigint, question_id bigint, user_quiz_id bigint, primary key (id));

alter table user_answer add constraint FKm321eamc0drwpxfkvyl9giypt foreign key (answer_id) references answer;
alter table user_answer add constraint FKpsk90eok3ounaet92hku3gny1 foreign key (question_id) references question;
alter table user_answer add constraint FK2saap9xe6vqrjo2tdclq120ii foreign key (user_quiz_id) references user_quiz;