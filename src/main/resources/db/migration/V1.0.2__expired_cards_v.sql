create or replace view expiring_cards_v as
select expiration_year, count(*) as cards_count from card
group by expiration_year