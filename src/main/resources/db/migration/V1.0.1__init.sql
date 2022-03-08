create table card
(
	id bigserial not null constraint card_pkey primary key,
	card_number varchar not null,
	cvv varchar not null,
	expiration_year smallint not null,
	expiration_month smallint not null,
	state varchar not null,
    user_id bigint not null,
	created timestamp not null,
	updated timestamp not null
);

create unique index card_number_idx on card (card_number);