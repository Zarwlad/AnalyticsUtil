select e.type,
       e.reg_status,
       count(*) as events_in_status
from event e
where e.created_date between timestamp '2020-06-01 00:00:00' and timestamp '2020-06-30 23:59:59'
group by e.type, e.reg_status

union
select '_TOTAL_',
       e.reg_status,
       count(*) as events_in_status
from event e
where e.created_date between timestamp '2020-06-01 00:00:00' and timestamp '2020-06-30 23:59:59'
group by e.reg_status
