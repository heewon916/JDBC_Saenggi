use saengji;

insert into user(userId, userPw, phoneNumber, birthday, created, userName) values('dankook1106','dblove1106','010-1111-1111','2000-09-27','2022-11-06','KimDankook');

insert into account(accountNum, bank, amount, checkCredit, userId, mainAccount) values('20000927','hana',200000,'check',1,1);
insert into account(accountNum, bank, amount, checkCredit, userId, mainAccount) values('20221106','kakao',150000,'credit',1,0);
insert into account(accountNum, bank, amount, checkCredit, userId, mainAccount) values('20221106','toss',50000,'check',1,0);

insert into expense(userId, expense, expenseDate, content, category, accountId) values(1, 12000, '2022-11-1', 'starbucks', 'cafe', 1);
insert into expense(userId, expense, expenseDate, content, category, accountId) values(1, 17000, '2022-11-1', 'lunch', 'food', 3);
insert into expense(userId, expense, expenseDate, content, category, accountId) values(1, 20000, '2022-11-1', 'modernpocha', 'alcohol', 1);
insert into expense(userId, expense, expenseDate, content, category, accountId) values(1, 78000, '2022-11-1', 'online shopping', 'shopping', 1);
insert into expense(userId, expense, expenseDate, content, category, accountId) values(1, 2000, '2022-11-2', 'mega coffee', 'cafe', 1);
insert into expense(userId, expense, expenseDate, content, category, accountId) values(1, 4000, '2022-11-2', 'e-mart', 'life', 1);
insert into expense(userId, expense, expenseDate, content, category, accountId) values(1, 4000, '2022-11-2', 'starbucks', 'cafe', 1);
insert into expense(userId, expense, expenseDate, content, category, accountId) values(1, 9000, '2022-11-2', 'lunch', 'food', 1);
insert into expense(userId, expense, expenseDate, content, category, accountId) values(1, 20000, '2022-11-3', 'e-mart', 'life', 1);
insert into expense(userId, expense, expenseDate, content, category, accountId) values(1, 60000, '2022-11-3', 'phone', 'life', 1);
insert into expense(userId, expense, expenseDate, content, category, accountId) values(1, 80000, '2022-11-3', 'bus and subway', 'transportation', 1);
insert into expense(userId, expense, expenseDate, content, category, accountId) values(1, 40000, '2022-11-3', 'Izakaya', 'alcohol', 1);
insert into expense(userId, expense, expenseDate, content, category, accountId) values(1, 2000, '2022-11-4', 'mega coffee', 'cafe', 2);
insert into expense(userId, expense, expenseDate, content, category, accountId) values(1, 5000, '2022-11-4', 'starbucks', 'cafe', 1);
insert into expense(userId, expense, expenseDate, content, category, accountId) values(1, 50000, '2022-11-4', 'olive young', 'shopping', 1);
insert into expense(userId, expense, expenseDate, content, category, accountId) values(1, 8000, '2022-11-4', 'dinner', 'food', 1);
insert into expense(userId, expense, expenseDate, content, category, accountId) values(1, 5000, '2022-11-4', 'CU', 'life', 1);
insert into expense(userId, expense, expenseDate, content, category, accountId) values(1, 9000, '2022-11-5', 'daiso', 'life', 2);
insert into expense(userId, expense, expenseDate, content, category, accountId) values(1, 5000, '2022-11-5', 'starbucks', 'cafe', 1);
insert into expense(userId, expense, expenseDate, content, category, accountId) values(1, 12000, '2022-11-5', 'beer', 'alcohol', 1);
insert into expense(userId, expense, expenseDate, content, category, accountId) values(1, 5000, '2022-11-6', 'starbucks', 'cafe', 3);

insert into income(userId, income, incomeDate, content, category, accountId) values(1, 50000, '2022-11-1', 'father yongdon', 'gift', 2);
insert into income(userId, income, incomeDate, content, category, accountId) values(1, 500000, '2022-11-2', 'school award', 'award', 1);
insert into income(userId, income, incomeDate, content, category, accountId) values(1, 600000, '2022-11-3', 'part-time', 'salary', 1);

insert into goal(userId, goalAmount, goalStartDate, goalEndDate) values(1, 1500000, '2022-11-06','2022-11-06');